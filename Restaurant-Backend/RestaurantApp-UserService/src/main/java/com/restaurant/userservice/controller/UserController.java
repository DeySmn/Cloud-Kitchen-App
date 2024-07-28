package com.restaurant.userservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.userservice.constants.UserServiceConstants;
import com.restaurant.userservice.exception.InvalidUsernameOrPasswordException;
import com.restaurant.userservice.exception.UserAlreadyExistsException;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.model.BlobImage;
import com.restaurant.userservice.model.CartItemsInfo;
import com.restaurant.userservice.model.ResetPwdObject;
import com.restaurant.userservice.model.UserDetails;
import com.restaurant.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RestController
public class UserController {
	
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = UserServiceConstants.REGISTERNEWUSER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerUser(@RequestBody UserDetails user) throws UserAlreadyExistsException{
		log.info("inside user service to get all users");
        return userService.addNewUser(user);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEALLUSERS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllUsers() throws UserNotFoundException{
		log.info("inside user update service to get all users");		
		return userService.getAllUsers();
	}
	
	@PutMapping(value = UserServiceConstants.UPDATEUSER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUser(@RequestBody UserDetails user) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.updateUser(user);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEUSERBYEMAIL_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserByEmail(@PathVariable("email") String email ) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.getUserByEmail(email);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEUSERBYUSERID_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserByUserId(@PathVariable("userId") String userId ) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.getUserByUserId(userId);
	}
	
	@PutMapping(value = UserServiceConstants.UPDATECARTITEMS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUserCart(@PathVariable("userId") String userId, @RequestBody List<CartItemsInfo> cartItems) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.updateUserCart(userId, cartItems);
	}
	
	@GetMapping(value = UserServiceConstants.ISUSEREXISTS)
	public String isUserExists(@PathVariable("email") String email) throws UserNotFoundException{
		return userService.checkUser(email);
	}
	
	@PostMapping(value = UserServiceConstants.UPLOADUSERIMAGE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadUserImage(@PathVariable("userId") String userId, @RequestParam("imageFile") MultipartFile file) throws IOException, UserNotFoundException {
		return userService.uploadUserImage(userId,file);
	}
	
	@PostMapping(value = UserServiceConstants.RETRIEVEUSERIMAGE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserImage(@RequestBody BlobImage image) throws IOException, DataFormatException {
		return userService.getUserImage(image);
	}
	
	@PutMapping(value = UserServiceConstants.RESETPASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resetPassword(@RequestBody ResetPwdObject resetPwdObj) throws UserNotFoundException {
		return userService.resetPassword(resetPwdObj);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEALLCUSTOMERS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllCustomers() throws UserNotFoundException {
		return userService.getAllCustomers();
	}
}
