package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.util.RegisterRequest;

public interface UserService {
	
	User save(RegisterRequest userForm);
}
