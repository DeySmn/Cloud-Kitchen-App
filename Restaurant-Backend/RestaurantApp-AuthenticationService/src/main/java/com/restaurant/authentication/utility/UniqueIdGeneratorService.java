package com.restaurant.authentication.utility;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restaurant.authentication.model.UserDetails;
import com.restaurant.authentication.repository.UserRepository;

@Component
public class UniqueIdGeneratorService {
	
	@Autowired
	private UserRepository userRepository;

	public String generateUniqueUsedId(String firstName) {
		Random rand = new Random();
        int randomNumber = rand.nextInt(1000);
		String username = firstName + randomNumber;
		Optional<UserDetails> user = userRepository.findById(username);
		if(!user.isEmpty()) {
			return generateUniqueUsedId(firstName);
		}
		return username;
	}
}
