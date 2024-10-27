package com.mccormick.stw_music_bot_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tg_music_file")
@Getter
@Setter
@NoArgsConstructor(force = true)
public class TgMusicFile {

	@Id
	@Column(unique = true)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "playlist_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TgPlaylist tgPlaylist;
}
