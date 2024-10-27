package com.mccormick.stw_music_bot_api.util;

import com.mccormick.stw_music_bot_api.dto.TgMusicFileDTO;
import com.mccormick.stw_music_bot_api.dto.TgPlaylistDTO;
import com.mccormick.stw_music_bot_api.dto.TgUserDTO;
import com.mccormick.stw_music_bot_api.model.TgMusicFile;
import com.mccormick.stw_music_bot_api.model.TgPlaylist;
import com.mccormick.stw_music_bot_api.model.TgUser;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

	private static ModelMapper modelMapper;

	private final ModelMapper notStaticModelMapper;

	@Autowired
	public EntityMapper(ModelMapper notStaticModelMapper) {
		this.notStaticModelMapper = notStaticModelMapper;
	}

	public static TgUserDTO getDto(TgUser tgUser) {
		return modelMapper.map(tgUser, TgUserDTO.class);
	}

	public static TgPlaylistDTO getDto(TgPlaylist tgPlaylist) {
		return modelMapper.map(tgPlaylist, TgPlaylistDTO.class);
	}

	public static TgMusicFileDTO getDto(TgMusicFile musicFile) {
		return modelMapper.map(musicFile, TgMusicFileDTO.class);
	}

	public static TgUser getEntity(TgUserDTO tgUserDTO) {
		return modelMapper.map(tgUserDTO, TgUser.class);
	}

	public static TgPlaylist getEntity(TgPlaylistDTO tgPlaylistDTO) {
		return modelMapper.map(tgPlaylistDTO, TgPlaylist.class);
	}

	public static TgMusicFile getEntity(TgMusicFileDTO tgMusicFileDTO) {
		return modelMapper.map(tgMusicFileDTO, TgMusicFile.class);
	}

	@PostConstruct
	private void init() {
		EntityMapper.modelMapper = notStaticModelMapper;
	}
}
