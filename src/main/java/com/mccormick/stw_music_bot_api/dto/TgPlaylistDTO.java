package com.mccormick.stw_music_bot_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TgPlaylistDTO {

	@NotNull(message = "Id must not be null")
	private UUID id;

	private String imageId;

	@NotNull(message = "User must be null")
	private TgUserDTO tgUserDTO;

	public TgPlaylistDTO(UUID id, String imageId, TgUserDTO tgUserDTO) {
		this.id = id;
		this.imageId = imageId;
		this.tgUserDTO = tgUserDTO;
	}
}
