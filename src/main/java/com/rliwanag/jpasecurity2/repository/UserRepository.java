package com.rliwanag.jpasecurity2.repository;

import com.rliwanag.jpasecurity2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByFirstnameAndLastname(String firstname, String lastname);

}
