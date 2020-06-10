package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.util.RegisterRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User save(RegisterRequest userForm) {
		User user = new User();
		user.setUserName(userForm.getUserName());
		user.setPassword(passwordEncoder.encode(userForm.getPassword()));
		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setBirthDay(userForm.getBirthDay());
		user.setGender(userForm.getGender());
		user.setAddress(userForm.getAddress());
		return userRepository.save(user);
	}

}
