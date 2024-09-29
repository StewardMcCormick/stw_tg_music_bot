package com.mccormick.stw_music_bot_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tg_user")
@Getter
@Setter
@NoArgsConstructor
public class TgUser {

	@Id
	private UUID id = UUID.randomUUID();

	@Column(name = "firstname")
	@NotNull(message = "Firstname must be not null")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "username")
	private String username;

	@OneToMany(mappedBy = "tgUser")
	private Set<TgPlaylist> tgPlaylistSet;
}
