package com.mccormick.stw_music_bot_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tg_playlist")
@Getter
@Setter
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
public class TgPlaylist {

	@Id
	private UUID id = UUID.randomUUID();

	@Column(name = "image_id")
	private String imageId;

	@OneToMany(mappedBy = "tgPlaylist")
	private List<TgMusicFile> tgMusicFileList;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TgUser tgUser;

	public TgPlaylist(UUID id, String imageId, TgUser tgUser) {
		this.id = id;
		this.imageId = imageId;
		this.tgUser = tgUser;
	}
}
