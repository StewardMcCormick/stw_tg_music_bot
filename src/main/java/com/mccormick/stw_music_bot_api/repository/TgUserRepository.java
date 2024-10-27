package com.mccormick.stw_music_bot_api.repository;

import com.mccormick.stw_music_bot_api.model.TgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TgUserRepository extends JpaRepository<TgUser, Integer> {
}
