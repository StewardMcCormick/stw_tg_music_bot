package com.mccormick.stw_music_bot_api.service;

import com.mccormick.stw_music_bot_api.repository.TgMusicFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TgMusicFileService {

	private final TgMusicFileRepository musicFileRepository;

	@Autowired
	public TgMusicFileService(TgMusicFileRepository musicFileRepository) {
		this.musicFileRepository = musicFileRepository;
	}
}
