package com.mccormick.stw_music_bot_api.service;

import com.mccormick.stw_music_bot_api.exception.EntityNotFoundException;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.repository.TgPlaylistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TgPlaylistService {

	private final TgPlaylistRepository tgPlaylistRepository;

	@Autowired
	public TgPlaylistService(TgPlaylistRepository tgPlaylistRepository) {
		this.tgPlaylistRepository = tgPlaylistRepository;
	}

	public TgPlaylist findById(UUID id) {
		Optional<TgPlaylist> tgPlaylist = tgPlaylistRepository.findById(id);
		if (tgPlaylist.isPresent()) return tgPlaylist.get();
		throw new EntityNotFoundException("Playlist was not found");
	}

	@Transactional
	public void save(TgPlaylist tgPlaylist) {
		tgPlaylistRepository.save(tgPlaylist);
	}
}
