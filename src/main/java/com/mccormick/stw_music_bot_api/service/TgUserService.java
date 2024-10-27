package com.mccormick.stw_music_bot_api.service;

import com.mccormick.stw_music_bot_api.exception.EntityNotFoundException;
import com.mccormick.stw_music_bot_api.model.TgUser;
import com.mccormick.stw_music_bot_api.repository.TgUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TgUserService {

	private final TgUserRepository tgUserRepository;

	@Autowired
	public TgUserService(TgUserRepository tgUserRepository) {
		this.tgUserRepository = tgUserRepository;
	}

	public TgUser findById(Integer id) {
		Optional<TgUser> tgUser = tgUserRepository.findById(id);
		if (tgUser.isPresent()) return tgUser.get();
		throw new EntityNotFoundException(
				String.format(
						"User with id %s was not found",
						id
				)
		);
	}

	@Transactional
	public void save(TgUser user){
		tgUserRepository.save(user);
	}
}
