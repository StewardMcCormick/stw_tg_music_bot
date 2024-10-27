package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.dto.TgPlaylistDTO;
import com.mccormick.stw_music_bot_api.dto.TgUserDTO;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.model.TgUser;
import com.mccormick.stw_music_bot_api.service.TgPlaylistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TgPlaylistRestControllerTest {

	@Mock
	private TgPlaylistService tgPlaylistService;

	@Mock
	private ModelMapper modelMapper;

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

		ResponseEntity<TgPlaylistDTO> response = this.controller.handleGetPlaylist("550e8400-e29b-41d4-a716-446655440000");

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(tgPlaylistDTO, response.getBody());
		Assertions.assertEquals(tgPlaylistDTO.getTgUserDTO(), response.getBody().getTgUserDTO());
	}

	@Test
	void handleSavePlaylist() {
	}
}