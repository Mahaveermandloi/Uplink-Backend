package com.linkup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkup.config.JwtProvider;
import com.linkup.exceptions.UserException;
import com.linkup.models.User;
import com.linkup.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getEmail());
		newUser.setId(user.getId());

		User savedUser = userRepository.save(newUser);
		return savedUser;
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with userId " + userId);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
		User reqUser = findUserById(reqUserId);
		User user2 = findUserById(userId2);
//
//		user2.getFollowers().add(reqUser.getId());
//		user2.getFollowings().add(user2.getId());

		userRepository.save(reqUser);
		userRepository.save(user2);

		return reqUser;
	}

	
	@Override
	public User updateUser(User user, Integer userId) throws UserException {
		
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserException("User not found with id " + userId);
		}

		User existingUser = optionalUser.get();

		if (user.getFirstName() != null) {
			existingUser.setFirstName(user.getFirstName());
		}

		if (user.getLastName() != null) {
			existingUser.setLastName(user.getLastName());
		}

		if (user.getEmail() != null) {
			existingUser.setEmail(user.getEmail());
		}
		if (user.getGender() != null) {
			existingUser.setGender(user.getGender());
		}

		return userRepository.save(existingUser);
	}

	
	
	
	@Override
	public List<User> searchUser(String query) {
		return userRepository.searchUser(query);
	}

	
	
	
	
	@Override
	public User findUserByJwt(String jwt) {

		String email = JwtProvider.getEmailFromJwtToken(jwt);

		User user = userRepository.findByEmail(email);

		return user;

	}

}