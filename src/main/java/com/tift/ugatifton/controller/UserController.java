package com.tift.ugatifton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.tift.ugatifton.entity.User;
import com.tift.ugatifton.repository.UserRepository;
import com.tift.ugatifton.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value="id") long userId){
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found with id :" +userId));
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) {
				return this.userRepository.save(user);
	}
	
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user,@PathVariable ("id") long userId) {
		User exsistingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found with id :" + userId));
		exsistingUser.setFirstName(user.getFirstName());
		exsistingUser.setLastName(user.getLastName());
		exsistingUser.setEmail(user.getEmail());
		return this.userRepository.save(exsistingUser);		 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
		User exsistingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found with id :" + userId));
		this.userRepository.delete(exsistingUser);
		return ResponseEntity.ok().build();
	}

}
