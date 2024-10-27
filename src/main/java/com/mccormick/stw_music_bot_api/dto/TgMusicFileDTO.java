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
public class TgMusicFileDTO {

	@NotNull(message = "File id must not be null")
	private Integer id;

	@NotNull(message = "Playlist must not be null")
	private TgPlaylistDTO tgPlaylistDTO;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TgMusicFileDTO that = (TgMusicFileDTO) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
