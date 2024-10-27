package com.mccormick.stw_music_bot_api.service;

import com.mccormick.stw_music_bot_api.exception.EntityNotFoundException;
import com.mccormick.stw_music_bot_api.model.TgMusicFile;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.repository.TgMusicFileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TgMusicFileService {

	private final TgMusicFileRepository tgMusicFileRepository;

	private final TgPlaylistService tgPlaylistService;

	@Autowired
	public TgMusicFileService(TgMusicFileRepository musicFileRepository, TgPlaylistService tgPlaylistService) {
		this.tgMusicFileRepository = musicFileRepository;
		this.tgPlaylistService = tgPlaylistService;
	}

	public List<TgMusicFile> findByPlaylistId(TgPlaylist tgPlaylist) {
		return tgMusicFileRepository.getAllByTgPlaylistId(tgPlaylist.getId());
	}

	public List<TgMusicFile> findByPlaylistId(UUID id) {
		tgPlaylistService.findById(id);
		return tgMusicFileRepository.getAllByTgPlaylistId(id);
	}

	public TgMusicFile findById(Integer id) {
		Optional<TgMusicFile> tgMusicFile = tgMusicFileRepository.findById(id);
		if (tgMusicFile.isEmpty()) throw new EntityNotFoundException("Music File was not found");
		return tgMusicFile.get();
	}

	@Transactional
	public void save(TgMusicFile tgMusicFile) {
		tgMusicFileRepository.save(tgMusicFile);
	}

	@Transactional
	public void save(List<TgMusicFile> list) {
		tgMusicFileRepository.saveAll(list);
	}
}
