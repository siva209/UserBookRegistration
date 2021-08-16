package com.bridgelabz.bookstore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.ForgotPwdDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.UpdatePwdDto;
import com.bridgelabz.bookstore.dto.UpdateUserDto;
import com.bridgelabz.bookstore.exception.UserBookRegistrationException;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repository.UserBookRepository;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.util.Jms;
import com.bridgelabz.bookstore.util.JwtToken;

@Service
public class UserBookServiceImpl implements IUserBookService{
	
	@Autowired
	private UserBookRepository userbookrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private BCryptPasswordEncoder pwdencoder;
	
	@Autowired
	private JwtToken jwt;
	@Autowired
	private Jms jms;

	@Override
	public Response registerBookDetails(BookDto bookDetailsDto) {
		Optional<User> isUserPresent = userbookrepo.findAllByemail(bookDetailsDto.getEmail());
		if (!isUserPresent.isPresent()) {
			User user = modelmapper.map(bookDetailsDto, User.class);
			user.setPassword(pwdencoder.encode(user.getPassword()));
			user.setRegisteredDate(LocalDateTime.now());
			user.setPurchaseDate(LocalDateTime.now());;
			user.setExpiryDate(LocalDateTime.now());
			user.setUpdatedDate(LocalDateTime.now());
			userbookrepo.save(user);
			String body="http://localhost:9090/verifyemail/"+jwt.jwtToken(user.getId());
			System.out.println(body);
			jms.sendEmail(user.getEmail(),"verification email",body);
			return new Response("regitration sucess",user,201,"true");
		} else {
			throw new UserBookRegistrationException("invalid bank details", null, 400, "true");
		}
		}

	@Override
	public User verify(String token) {
		long id=jwt.parseJWT(token);
		
		User user=userbookrepo.isIdExists(id).orElseThrow(() -> new UserBookRegistrationException("user not exists",HttpStatus.OK,id,"false"));
		user.setVerifyEmail(true);
		userbookrepo.save(user);
		return user;
	}

	@Override
	public Response loginUser(LoginDto dto) {
		User  user=userbookrepo.findByEmail(dto.getEmail()).orElseThrow(() -> new UserBookRegistrationException("login failed",HttpStatus.OK,null,"false"));
		boolean ispwd=pwdencoder.matches(dto.getPassword(),user.getPassword());
		if(ispwd==false) {
			throw new UserBookRegistrationException("login failed",HttpStatus.OK,null,"false");
		} else {
			String token=jwt.jwtToken(user.getId());
			return new Response(" Successfully login user ", user, 200, token);
			
		}
		
		
	}

	@Override
	public Response getAllUser() {
		List<User> user = userbookrepo.findAll();
		System.out.println(user);
		return new Response("users are",user,200,"true");
	}

	@Override
	public User getUserById(Long id) throws UserBookRegistrationException {
		return userbookrepo.getUserById(id).orElseThrow(() -> new UserBookRegistrationException("user not exists",HttpStatus.OK,id,"false"));
	}
	

	@Override
	public Response updateUserById(Long id, UpdateUserDto dto) throws UserBookRegistrationException {
		Optional<User> isUserPresent = userbookrepo.findById(id);
		if (isUserPresent.isPresent()) {
			isUserPresent.get().setFirstName(dto.getFirstName());
			isUserPresent.get().setLastName(dto.getLastName());
			isUserPresent.get().setDataOfBirth(dto.getDataOfBirth());
			isUserPresent.get().setEmail(dto.getEmail());
			isUserPresent.get().setPassword(dto.getPassword());
			isUserPresent.get().setUpdatedDate(LocalDateTime.now());
			userbookrepo.save(isUserPresent.get());
			return new Response("Update user sucesssfully",isUserPresent,200,"true");
		} else {

			throw new UserBookRegistrationException("invalid bank details", null, 400, "true");
		}
		
	}

	@Override
	public Response forgotPwd(ForgotPwdDto forgotdto) {
//		User user=getUserByEmail(forgotdto.getEmail());
		Optional<User> isUserPresent = userbookrepo.findByEmail(forgotdto.getEmail());
		String body="http://localhost:4200/resetpassword/"+jwt.jwtToken(isUserPresent.get().getId());
		jms.sendEmail(isUserPresent.get().getEmail(),"Reset Password",body);
		return new Response("Rest password ",body,200,"true");
	}

	@Override
	public User updatepwd(UpdatePwdDto pwddto) {
		long id=jwt.parseJWT(pwddto.getToken());
	    User user=getUserById(id);
		user.setPassword(pwdencoder.encode(pwddto.getNewpassword()));
		userbookrepo.save(user);
		return user;
		
}

}

	