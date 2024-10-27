package com.mccormick.stw_music_bot_api.controller;

import com.mccormick.stw_music_bot_api.dto.TgUserDTO;
import com.mccormick.stw_music_bot_api.model.TgUser;
import com.mccormick.stw_music_bot_api.service.TgUserService;
import com.mccormick.stw_music_bot_api.util.ErrorResponse;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TgUserRestControllerTest {

	@Mock
	TgUserService tgUserService;

	@Mock
	ModelMapper modelMapper;

	@Mock
	BindingResult bindingResult;

	@InjectMocks
	TgUserRestController controller;

	private TgUser tgUser;

	private TgUserDTO tgUserDTO;

	@BeforeTestClass
	public void prepareTestData() {
		TgUserDTO tgUserDTO = new TgUserDTO(1, "Steward");
		TgUser tgUser = new TgUser(1, "Steward");
	}

	@Test
	void getUser_ReturnsValidResponseEntity() {
		// given
		Mockito.when(tgUserService.findById(1)).thenReturn(tgUser);
		Mockito.when(modelMapper.map(tgUser, TgUserDTO.class)).thenReturn(tgUserDTO);

		// when
		ResponseEntity<TgUserDTO> responseEntity = this.controller.handleGetUser(1);

		// then
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(tgUserDTO, responseEntity.getBody());
	}


	@Test
	@DisplayName("POST /api/v1/tg_user returns HTTP 200 because saved entity is valid")
	void saveUser_EntityIsValid_ReturnsValidResponseEntity() {
		// given
		Mockito.when(modelMapper.map(tgUserDTO, TgUser.class)).thenReturn(tgUser);
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);

		// when
		ResponseEntity<?> responseEntity = this.controller.handleSaveUser(tgUserDTO, bindingResult);

		// then
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		verify(this.tgUserService).save(tgUser);
	}

	@Test
	@DisplayName("POST /api/v1/tg_user returns HTTP status 400 because entity is not valid")
	void saveUser_EntityIsNotValid_ReturnsBadRequestResponse() {
		//given
		tgUserDTO = new TgUserDTO();
		ErrorResponse errorResponse = new ErrorResponse("id - User id must not ba null; firstname - Firstname must not be null; ");
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getFieldErrors()).thenReturn(
			List.of(
					new FieldError("tgUserDTO", "id", "User id must not ba null"),
					new FieldError("tgUserDTO", "firstname", "Firstname must not be null")
			)
		);

		// when
		ResponseEntity<?> responseEntity = this.controller.handleSaveUser(tgUserDTO, bindingResult);

		// then
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		Assertions.assertEquals(errorResponse, responseEntity.getBody());

		verify(this.tgUserService, never()).save(tgUser);
	}
}