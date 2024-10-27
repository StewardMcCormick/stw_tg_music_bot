package com.mccormick.stw_music_bot_api.repository;

import com.mccormick.stw_music_bot_api.model.TgMusicFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TgMusicFileRepository extends JpaRepository<TgMusicFile, Integer> {

	@Query("SELECT f FROM TgMusicFile f WHERE f.tgPlaylist.id = ?1")
	List<TgMusicFile> getAllByTgPlaylistId(UUID tgPlaylist_id);
}
