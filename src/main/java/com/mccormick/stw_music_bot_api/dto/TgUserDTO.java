package com.mccormick.stw_music_bot_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TgUserDTO {

	@NotNull(message = "User id must not ba null")
	private Integer id;

	@NotNull(message = "Firstname must not be null")
	private String firstname;

	private String lastname;

	private String username;

	public TgUserDTO(Integer id, String firstname) {
		this.id = id;
		this.firstname = firstname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TgUserDTO tgUserDTO = (TgUserDTO) o;
		return Objects.equals(id, tgUserDTO.id) && Objects.equals(firstname, tgUserDTO.firstname) && Objects.equals(lastname, tgUserDTO.lastname) && Objects.equals(username, tgUserDTO.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstname, lastname, username);
	}
}
