package com.mccormick.stw_music_bot_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = "tg_music_file")
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
public class TgMusicFile {

	@Id
	@Column(unique = true)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "playlist_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TgPlaylist tgPlaylist;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TgMusicFile that = (TgMusicFile) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
