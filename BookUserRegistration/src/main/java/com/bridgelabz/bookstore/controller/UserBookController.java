package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.exception.UserBookRegistrationException;
import com.bridgelabz.bookstore.dto.ForgotPwdDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UpdatePwdDto;
import com.bridgelabz.bookstore.dto.UpdateUserDto;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.IUserBookService;



@RestController
public class UserBookController {
	
	@Autowired
	private IUserBookService userbookService;
	
	@PostMapping("/registerBook")
	public ResponseEntity<Response>registerBookDetails(@RequestBody UserDto bookDetails,BindingResult result){
		Response response=userbookService.registerBookDetails(bookDetails);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping("/verifyemail/{token}")
	public ResponseEntity<Response> verifyemail(@PathVariable("token") String token)
	{
		return new ResponseEntity<Response>(new Response("email verified",userbookService.verify(token),201,"true"),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/loginuser")
	public ResponseEntity<Response> loginUser(@RequestBody LoginDto dto,BindingResult result)
	{
		Response respDTO =userbookService.loginUser(dto);
		return new ResponseEntity<Response>(respDTO, HttpStatus.OK);
	}
	@GetMapping("/getAllusers")
	public ResponseEntity<Response>getAllUsers(){
		Response respDTO = userbookService.getAllUser();
		return new ResponseEntity<Response>(respDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getuserbyid/{id}")
	public ResponseEntity<Response> getUserById(@PathVariable Long id) {
		 User user=userbookService.getUserById(id);
			return new ResponseEntity<Response>(new Response("welcome",userbookService.getUserById(id),200,"true"),HttpStatus.OK);
		}
	@PutMapping("/update/{id}/{token}")
	public ResponseEntity<Response> updateUserById(@PathVariable Long id,@PathVariable  String token, @RequestBody  UpdateUserDto dto) {
		Response respDTO = userbookService.updateUserById(token,id, dto);
		return new ResponseEntity<Response>(respDTO,HttpStatus.OK);
	}
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPwd(@RequestBody  ForgotPwdDto forgotdto) {
		Response respDTO = userbookService.forgotPwd(forgotdto);
		return new ResponseEntity<Response>(respDTO, HttpStatus.OK);
	}
	@PostMapping("/updatepassword")
	public ResponseEntity<Response> updatePassword(@RequestBody UpdatePwdDto pwddto,BindingResult result)
	{
		return new ResponseEntity<Response>(new Response("password updated successfully", userbookService.updatepwd(pwddto),200,"true"),HttpStatus.OK);
	}
	
	@GetMapping("/sendotp/{token}")
	public Response sendotp(@PathVariable String token) {
		return userbookService.sendotp(token);
	}
	

	@GetMapping("/verifyotp/{token}")
	public Response verifyotp(@PathVariable String token, @RequestParam int otp) throws UserBookRegistrationException {
		return userbookService.verifyOtp(token, otp);
	}
	
	@GetMapping("/checkuser/{token}")
	public User check(@PathVariable String token) {
		return userbookService.check(token);
	}

	
	@DeleteMapping("/deleteuser/{token}/{id}")
	public ResponseEntity<Response> deleteuser(@PathVariable String token, @PathVariable Long id) {
		Response respDTO = userbookService.delete(token, id);
		return new ResponseEntity<Response>(respDTO, HttpStatus.OK);
	}
	@GetMapping("/purchase/{token}")
	public ResponseEntity<Response>purchaseBook(@PathVariable String token){
		Response respDTO = userbookService.purchaseBook(token);
		return new ResponseEntity<Response>(respDTO, HttpStatus.OK);
	}
	@GetMapping("/expiry/{token}")
	public ResponseEntity<Response>expiry(@PathVariable String token){
		Response respDTO = userbookService.expiry(token);
		return new ResponseEntity<Response>(respDTO, HttpStatus.OK);
	
	}
}



