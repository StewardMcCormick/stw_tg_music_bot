package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.dto.TgPlaylistDTO;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.service.TgPlaylistService;
import com.mccormick.stw_music_bot_api.util.EntityMapper;
import com.mccormick.stw_music_bot_api.util.ErrorResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api/v1/tg_playlist")
public class TgPlaylistRestController {

	private final TgPlaylistService tgPlaylistService;

	@Autowired
	public TgPlaylistRestController(TgPlaylistService tgPlaylistService) {
		this.tgPlaylistService = tgPlaylistService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<TgPlaylistDTO> handleGetPlaylist(@PathVariable String id) {
		TgPlaylist tgPlaylist = tgPlaylistService.findById(UUID.fromString(id));
		return ResponseEntity.ok()
				.contentType(APPLICATION_JSON)
				.body(convertPlaylistToDTO(tgPlaylist));
	}

	@PostMapping("/")
	public ResponseEntity<?> handleSavePlaylist(@RequestBody @Valid TgPlaylistDTO tgPlaylistDTO,
												BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ErrorResponse.getErrorResponse(bindingResult);
		}

		tgPlaylistService.save(convertPlaylistDtoToPlaylist(tgPlaylistDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	private TgPlaylistDTO convertPlaylistToDTO(TgPlaylist tgPlaylist) {
		TgPlaylistDTO tgPlaylistDTO = EntityMapper.getDto(tgPlaylist);
		tgPlaylistDTO.setTgUserDTO(EntityMapper.getDto(tgPlaylist.getTgUser()));
		return tgPlaylistDTO;
	}

	private TgPlaylist convertPlaylistDtoToPlaylist(TgPlaylistDTO tgPlaylistDTO) {
		TgPlaylist tgPlaylist = EntityMapper.getEntity(tgPlaylistDTO);
		tgPlaylist.setTgUser(EntityMapper.getEntity(tgPlaylistDTO.getTgUserDTO()));
		return tgPlaylist;
	}
}
