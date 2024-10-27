package com.mccormick.stw_music_bot_api.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TgMusicFileDTO {

	@NotNull(message = "File id must not be null")
	private Integer id;

	@NotNull(message = "Playlist id must not be null")
	private String playlistId;
}
