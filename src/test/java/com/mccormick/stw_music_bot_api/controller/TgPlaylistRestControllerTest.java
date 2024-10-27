package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.dto.TgPlaylistDTO;
import com.mccormick.stw_music_bot_api.dto.TgUserDTO;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.model.TgUser;
import com.mccormick.stw_music_bot_api.service.TgPlaylistService;
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
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TgPlaylistRestControllerTest {

	@Mock
	private TgPlaylistService tgPlaylistService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private TgPlaylistRestController controller;

	private UUID id;

	private TgPlaylist tgPlaylist;

	private TgUser tgUser;

	private TgPlaylistDTO tgPlaylistDTO;

	private TgUserDTO tgUserDTO;

	@BeforeEach
	void setUp() {
		id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		tgUser = new TgUser(1, "Steward");
		tgPlaylist = new TgPlaylist(id, null, tgUser);
		tgUserDTO = new TgUserDTO(1, "Steward");
		tgPlaylistDTO = new TgPlaylistDTO(id, null, tgUserDTO);
	}

	@Test
	void handleGetPlaylist_ReturnsValidResponseEntity() {
		Mockito.when(tgPlaylistService.findById(id)).thenReturn(tgPlaylist);
		Mockito.when(modelMapper.map(tgPlaylist, TgPlaylistDTO.class)).thenReturn(tgPlaylistDTO);
		Mockito.when(modelMapper.map(tgPlaylist.getTgUser(), TgUserDTO.class)).thenReturn(tgUserDTO);

		ResponseEntity<TgPlaylistDTO> response = this.controller.handleGetPlaylist("550e8400-e29b-41d4-a716-446655440000");

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(tgPlaylistDTO, response.getBody());
		Assertions.assertEquals(tgPlaylistDTO.getTgUserDTO(), Objects.requireNonNull(response.getBody()).getTgUserDTO());
	}

	@Test
	@DisplayName("POST /api/v1/tg_playlist/ returns HTTP 200 because entity is valid")
	void handleSavePlaylist_EntityIsValid_ReturnsValidResponseEntity() {
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		Mockito.when(modelMapper.map(tgPlaylistDTO, TgPlaylist.class)).thenReturn(tgPlaylist);
		Mockito.when(modelMapper.map(tgUserDTO, TgUser.class)).thenReturn(tgUser);

		ResponseEntity<?> response = this.controller.handleSavePlaylist(tgPlaylistDTO, bindingResult);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(this.tgPlaylistService).save(tgPlaylist);
	}

	@Test
	@DisplayName("POST /api/v1/tg_playlist/ return HTTP 400 because entity is not valid")
	void handleSavePlaylist_EntityIsNotValid_ReturnsValidResponseEntity() {
		ErrorResponse errorResponse = new ErrorResponse("id - Id must not be null; tgUserDTO - User must be null; ");

		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getFieldErrors()).thenReturn(
				List.of(
						new FieldError("tgPlaylistDTO", "id",
								"Id must not be null"),
						new FieldError("tgPlaylistDTO", "tgUserDTO",
								"User must be null")
				)
		);

		ResponseEntity<?> response = this.controller.handleSavePlaylist(tgPlaylistDTO, bindingResult);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertEquals(errorResponse, response.getBody());

		verify(tgPlaylistService, never()).save(tgPlaylist);
	}
}