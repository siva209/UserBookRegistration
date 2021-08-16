package com.bridgelabz.bookstore.service;

import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.ForgotPwdDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UpdatePwdDto;
import com.bridgelabz.bookstore.dto.UpdateUserDto;
import com.bridgelabz.bookstore.exception.UserBookRegistrationException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.response.Response;

@Service
public interface IUserBookService {
	public Response registerBookDetails(BookDto bookDetails)throws UserBookRegistrationException ;
	public User verify(String token);
	public Response loginUser(LoginDto dto)throws UserBookRegistrationException ;
	public Response getAllUser();
	public User getUserById(Long id) throws UserBookRegistrationException;
       public Response updateUserById(Long id,UpdateUserDto dto)throws UserBookRegistrationException;
       public Response forgotPwd(ForgotPwdDto forgotdto);
       public  User updatepwd(UpdatePwdDto pwddto);

}

