package com.mccormick.stw_music_bot_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tg_playlist")
@Getter
@Setter
@NoArgsConstructor
public class TgPlaylist {

	@Id
	private UUID id = UUID.randomUUID();

	@Column(name = "image_id")
	private String imageId;

	@OneToMany(mappedBy = "tgPlaylist")
	private List<TgMusicFile> tgMusicFileList;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private TgUser tgUser;
}
