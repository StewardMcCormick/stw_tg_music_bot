package com.mccormick.stw_music_bot_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tg_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TgUser {

	@Id
	private Integer id;

	@Column(name = "firstname")
	@NotNull(message = "Firstname must be not null")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "username")
	private String username;

	@OneToMany(mappedBy = "tgUser")
	private Set<TgPlaylist> tgPlaylistSet;

	public TgUser(Integer id, String firstname) {
		this.id = id;
		this.firstname = firstname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TgUser tgUser = (TgUser) o;
		return Objects.equals(id, tgUser.id) && Objects.equals(firstname, tgUser.firstname) && Objects.equals(lastname, tgUser.lastname) && Objects.equals(username, tgUser.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstname, lastname, username);
	}
}
