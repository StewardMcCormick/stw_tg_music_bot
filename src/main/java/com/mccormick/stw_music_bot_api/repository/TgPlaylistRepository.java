package com.mccormick.stw_music_bot_api.repository;

import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TgPlaylistRepository extends JpaRepository<TgPlaylist, UUID> {
}
