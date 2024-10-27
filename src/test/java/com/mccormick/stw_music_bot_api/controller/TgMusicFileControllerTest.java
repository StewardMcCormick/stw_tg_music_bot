package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.dto.TgMusicFileDTO;
import com.mccormick.stw_music_bot_api.dto.TgPlaylistDTO;
import com.mccormick.stw_music_bot_api.dto.TgUserDTO;
import com.mccormick.stw_music_bot_api.model.TgMusicFile;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.model.TgUser;
import com.mccormick.stw_music_bot_api.service.TgMusicFileService;
import com.mccormick.stw_music_bot_api.util.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TgMusicFileControllerTest {

	@Mock
	private TgMusicFileService tgMusicFileService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private TgMusicFileController controller;

	private UUID id;

	private TgMusicFile tgMusicFile;

	private TgMusicFileDTO tgMusicFileDTO;

	private TgPlaylist tgPlaylist;

	private TgPlaylistDTO tgPlaylistDTO;

	private List<TgMusicFile> tgMusicFileList;

	private List<TgMusicFileDTO> tgMusicFileDTOList;

	@BeforeEach
	void setUp() {
		id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		tgPlaylist = new TgPlaylist(id, null, new TgUser());
		tgPlaylistDTO = new TgPlaylistDTO(id, null, new TgUserDTO());
		tgMusicFile = new TgMusicFile(1, tgPlaylist);
		tgMusicFileDTO = new TgMusicFileDTO(1, tgPlaylistDTO);

		tgMusicFileList = List.of(
				new TgMusicFile(1, tgPlaylist),
				new TgMusicFile(1, tgPlaylist),
				new TgMusicFile(1, tgPlaylist)
		);
		tgMusicFileDTOList = List.of(
				new TgMusicFileDTO(1, tgPlaylistDTO),
				new TgMusicFileDTO(1, tgPlaylistDTO),
				new TgMusicFileDTO(1, tgPlaylistDTO)
		);
	}

	@Test
	void handleGetAllMusicFilesInPlaylist_ReturnsValidResponseEntity() {
		Mockito.when(tgMusicFileService.findByPlaylistId(id)).thenReturn(tgMusicFileList);
		Mockito.when(modelMapper.map(tgMusicFile, TgMusicFileDTO.class)).thenReturn(tgMusicFileDTO);
		Mockito.when(modelMapper.map(tgMusicFile.getTgPlaylist(), TgPlaylistDTO.class)).thenReturn(tgPlaylistDTO);

		ResponseEntity<List<TgMusicFileDTO>> response = this.controller.handleGetAllMusicFilesInPlaylist(
				"550e8400-e29b-41d4-a716-446655440000");

		Assertions.assertNotNull(response);
		Assertions.assertEquals(tgMusicFileDTOList, response.getBody());
	}

	@Test
	void handleGetMusicFileById_ReturnsValidResponseEntity() {
		Mockito.when(tgMusicFileService.findById(1)).thenReturn(tgMusicFile);
		Mockito.when(modelMapper.map(tgMusicFile, TgMusicFileDTO.class)).thenReturn(tgMusicFileDTO);
		Mockito.when(modelMapper.map(tgMusicFile.getTgPlaylist(), TgPlaylistDTO.class)).thenReturn(tgPlaylistDTO);

		ResponseEntity<TgMusicFileDTO> response = this.controller.handleGetMusicFileById(1);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(tgMusicFileDTO, response.getBody());
		Assertions.assertEquals(tgPlaylistDTO, Objects.requireNonNull(response.getBody()).getTgPlaylistDTO());
	}

	@Test
	@DisplayName("POST /api/v1/tg_music_file/{playlist_id} returns HTTP 200 because entities is valid")
	void handleSaveMusicFiles_EntitiesIsValid_ReturnsValidResponseEntity() {
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		Mockito.when(modelMapper.map(tgMusicFileDTO, TgMusicFile.class)).thenReturn(tgMusicFile);
		Mockito.when(modelMapper.map(tgPlaylistDTO, TgPlaylist.class)).thenReturn(tgPlaylist);

		ResponseEntity<?> response = this.controller.handleSaveMusicFiles("550e8400-e29b-41d4-a716-446655440000",
				tgMusicFileDTOList, bindingResult);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(tgMusicFileService).save(tgMusicFileList);
	}

	@Test
	@DisplayName("POST /api/v1/tg_music_file/{playlist_id} returns HTTP 400 because entities is not valid")
	void handleSaveMusicFiles_EntitiesIsNotValid_ReturnsValidResponseEntity() {
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);

		ResponseEntity<?> response = this.controller.handleSaveMusicFiles("550e8400-e29b-41d4-a716-446655440000",
				tgMusicFileDTOList, bindingResult);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(new ErrorResponse("Invalid entities in request body"), response.getBody());
		verify(tgMusicFileService, never()).save(tgMusicFileList);
	}
}