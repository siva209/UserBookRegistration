package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.User;


@Repository
public interface UserBookRepository  extends JpaRepository<User, Long>{

	Optional<User>findAllByemail(String email);

	@Query(value = "select * from user  where id=?1",nativeQuery = true)
	Optional<User> isIdExists(long id);

	Optional<User> findByEmail(String email);

	Optional<User> getUserById(Long id);
	
	Optional<User> getUserByEmail(String email);
	


	


}
