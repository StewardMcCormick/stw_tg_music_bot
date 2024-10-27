package com.mccormick.stw_music_bot_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TgMusicFileDTO {

	@NotNull(message = "File id must not be null")
	private Integer id;

	@NotNull(message = "Playlist must not be null")
	private TgPlaylistDTO tgPlaylistDTO;
}
