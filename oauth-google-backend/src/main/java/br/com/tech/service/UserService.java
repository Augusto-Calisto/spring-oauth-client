package br.com.tech.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tech.entity.User;
import br.com.tech.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findBySubAndRegistrationId(String sub, String registrationId) {
		return userRepository.findBySubAndRegistrationId(sub, registrationId);
	}
	
	public User saveUser(User userSave) {
		return userRepository.saveAndFlush(userSave);
	}
	
	@Transactional
	public void updateLastLoginDate(String id, LocalDateTime loginDateTime) {
		userRepository.updateLastLoginDateTime(id, loginDateTime);
	}
}