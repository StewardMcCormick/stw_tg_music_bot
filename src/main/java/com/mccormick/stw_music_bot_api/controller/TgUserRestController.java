package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.util.ErrorResponse;
import com.mccormick.stw_music_bot_api.dto.TgUserDTO;
import com.mccormick.stw_music_bot_api.model.TgUser;
import com.mccormick.stw_music_bot_api.service.TgUserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/v1/tg_user")
public class TgUserRestController {

	private final TgUserService tgUserService;

	private final ModelMapper modelMapper;

	@Autowired
	public TgUserRestController(TgUserService tgUserService, ModelMapper modelMapper) {
		this.tgUserService = tgUserService;
		this.modelMapper = modelMapper;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TgUserDTO> handleGetUser(@PathVariable Integer id) {
		TgUser user = tgUserService.findById(id);
		return ResponseEntity.ok()
				.contentType(APPLICATION_JSON)
				.body(convertTgUserToDTO(user));
	}

	@PostMapping()
	public ResponseEntity<?> handleSaveUser(@RequestBody @Valid TgUserDTO tgUserDTO,
											BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ErrorResponse.getErrorResponse(bindingResult);
		}

		tgUserService.save(convertDTOToTgUser(tgUserDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	private TgUserDTO convertTgUserToDTO(TgUser tgUser){
		return modelMapper.map(tgUser, TgUserDTO.class);
	}

	private TgUser convertDTOToTgUser(TgUserDTO tgUserDTO) {
		return modelMapper.map(tgUserDTO, TgUser.class);
	}
}
