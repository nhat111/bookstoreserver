package com.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;
import com.bookstore.util.MessagerResponse;
import com.bookstore.util.RegisterRequest;
import com.bookstore.util.SigninRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("")
public class UserController {
	
	private String userNameLogin;
	private Boolean isLogin = false;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository usersrepository;
	
	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
		if (usersrepository.existsByUserName(registerRequest.getUserName())) {
			return new ResponseEntity<>(new MessagerResponse("user exists"), HttpStatus.BAD_REQUEST);
		}	
		
		userService.save(registerRequest);

		return new ResponseEntity<>(new MessagerResponse("register success"), HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> sigin(@Valid @RequestBody SigninRequest signinRequest) {

		if (!usersrepository.existsByUserName(signinRequest.getUserName())) {
			return new ResponseEntity<>(new MessagerResponse("user not found"), HttpStatus.BAD_REQUEST);

		}

		User user = usersrepository.findUserByUserName(signinRequest.getUserName());

		String pass = user.getPassword();

		if (passwordEncoder.matches(signinRequest.getPassword(), pass)){

			isLogin = true;
			userNameLogin = user.getUserName();

			return new ResponseEntity<>(new MessagerResponse("login success"), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new MessagerResponse("password error"), HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/userlogin")
	public ResponseEntity<?> getUserInfo(){
		if (isLogin) {
			User user = usersrepository.findUserByUserName(userNameLogin);
			return ResponseEntity.ok().body(user);// return Object
		
		}

		return new ResponseEntity<>(new MessagerResponse("user not login"), HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return usersrepository.findAll();
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) 
			throws Exception {
		User users = usersrepository.findById(userId)
				.orElseThrow(() -> new Exception("user not found"));
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping("/user")
	public User addUser(@RequestBody User users) {
		return usersrepository.save(users);
	}
	
	@PutMapping("user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") 
	 Long userId, @Valid @RequestBody User userDetail) throws Exception{
		
		User users = usersrepository.findById(userId)
				.orElseThrow(()-> new Exception("user not found"));
		
		users.setFirstName(userDetail.getFirstName());
		users.setLastName(userDetail.getLastName());
		
		final User userUpdate = usersrepository.save(users);
		
		return ResponseEntity.ok().body(userUpdate);
		
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable(value = "id")Long userId) throws Exception{		
		User users = usersrepository.findById(userId)
				.orElseThrow(() -> new Exception("user not found"));
		
		usersrepository.delete(users);
		
	}

}
