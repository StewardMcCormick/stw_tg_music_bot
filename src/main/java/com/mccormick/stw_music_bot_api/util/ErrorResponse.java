package com.mccormick.stw_music_bot_api.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"message='" + message + '\'' +
				'}';
	}

	public static ResponseEntity<ErrorResponse> getErrorResponse(BindingResult bindingResult) {
		StringBuilder errorMessage = new StringBuilder();
		for (FieldError f : bindingResult.getFieldErrors()) {
			errorMessage.append(f.getField())
					.append(" - ").append(f.getDefaultMessage())
					.append("; ");
		}
		return ResponseEntity.badRequest()
				.contentType(APPLICATION_JSON)
				.body(new ErrorResponse(errorMessage.toString()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ErrorResponse that = (ErrorResponse) o;
		return Objects.equals(message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(message);
	}
}
