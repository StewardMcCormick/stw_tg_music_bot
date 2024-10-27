package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.dto.TgMusicFileDTO;
import com.mccormick.stw_music_bot_api.dto.TgPlaylistDTO;
import com.mccormick.stw_music_bot_api.model.TgMusicFile;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.service.TgMusicFileService;
import com.mccormick.stw_music_bot_api.util.ErrorResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api/v1/tg_music_file")
public class TgMusicFileController {

	private final TgMusicFileService tgMusicFileService;

	private final ModelMapper modelMapper;

	@Autowired
	public TgMusicFileController(TgMusicFileService tgMusicFileService, ModelMapper modelMapper) {
		this.tgMusicFileService = tgMusicFileService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/playlists/{playlist_id}")
	public ResponseEntity<List<TgMusicFileDTO>> handleGetAllMusicFilesInPlaylist(@PathVariable String playlist_id){
		List<TgMusicFileDTO> fileList = getDto(
				tgMusicFileService.findByPlaylistId(UUID.fromString(playlist_id))
		);

		return ResponseEntity.ok()
				.contentType(APPLICATION_JSON)
				.body(fileList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TgMusicFileDTO> handleGetMusicFileById(@PathVariable Integer id) {
		TgMusicFile tgMusicFile = tgMusicFileService.findById(id);

		return ResponseEntity.ok()
				.contentType(APPLICATION_JSON)
				.body(getDto(tgMusicFile));
	}

	@PostMapping("/")
	public ResponseEntity<?> handleSaveMusicFile(@RequestBody @Valid TgMusicFileDTO tgMusicFileDTO,
												 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ErrorResponse.getErrorResponse(bindingResult);
		}

		tgMusicFileService.save(getEntity(tgMusicFileDTO));

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping("/{playlist_id}")
	public ResponseEntity<?> handleSaveListOfMusicFiles(@PathVariable String playlist_id,
														@RequestBody @Valid List<TgMusicFileDTO> tgMusicFileDTOList,
														BindingResult bindingResult) {
		if (bindingResult.hasErrors()) return ResponseEntity.badRequest().contentType(APPLICATION_JSON)
												.body(new ErrorResponse("Invalid entities in request body"));

		tgMusicFileService.save(getEntity(tgMusicFileDTOList));

		return ResponseEntity.ok(HttpStatus.OK);
	}

	private List<TgMusicFileDTO> getDto(List<TgMusicFile> tgMusicFileList) {
		List<TgMusicFileDTO> dtoList = new ArrayList<>();

		for (TgMusicFile f : tgMusicFileList) {
			dtoList.add(modelMapper.map(f, TgMusicFileDTO.class));
		}

		return dtoList;
	}

	private TgMusicFileDTO getDto(TgMusicFile tgMusicFile) {
		TgMusicFileDTO tgMusicFileDTO = modelMapper.map(tgMusicFile, TgMusicFileDTO.class);
		tgMusicFileDTO.setTgPlaylistDTO(modelMapper.map(
				tgMusicFile.getTgPlaylist(),
				TgPlaylistDTO.class)
		);

		return tgMusicFileDTO;
	}

	private TgMusicFile getEntity(TgMusicFileDTO tgMusicFileDTO) {
		TgMusicFile tgMusicFile = modelMapper.map(tgMusicFileDTO, TgMusicFile.class);
		tgMusicFile.setTgPlaylist(modelMapper.map(
				tgMusicFileDTO.getTgPlaylistDTO(),
				TgPlaylist.class
		));

		return tgMusicFile;
	}

	private List<TgMusicFile> getEntity(List<TgMusicFileDTO> tgMusicFileDTOList) {
		List<TgMusicFile> tgMusicFileList = new ArrayList<>();

		for (TgMusicFileDTO f : tgMusicFileDTOList) {
			tgMusicFileList.add(modelMapper.map(f, TgMusicFile.class));
		}

		return tgMusicFileList;
	}
}
