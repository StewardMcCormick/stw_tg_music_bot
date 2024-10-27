package com.mccormick.stw_music_bot_api.repository;

import com.mccormick.stw_music_bot_api.model.TgMusicFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TgMusicFileRepository extends JpaRepository<TgMusicFile, Integer> {
}
