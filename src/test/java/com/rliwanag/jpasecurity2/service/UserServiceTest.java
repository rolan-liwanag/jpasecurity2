package com.rliwanag.jpasecurity2.service;

import com.rliwanag.jpasecurity2.model.User;
import com.rliwanag.jpasecurity2.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    //create a mock object for UserRepository
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    //create a mock object for PasswordEncoder
    PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

    //this is the class we want to test and NOT the UserRepository and NOT the PasswordEncoder
    UserService underTest;

    @BeforeAll
    public void setUp() throws Exception {
        underTest = new UserService(userRepository, encoder);
    }

    @Test
    public void testGetAll() {
        //set the expectation the userRepository.findAll() will be invoked and we want it to return data
        Mockito.when(userRepository.findAll()).thenReturn(
                Arrays.asList(new User("username", "password", "roles", "firstname", "lastname"))
        );
        //perform the call to the method we are testing
        List<User> users = underTest.getAllUsers();
        //assert the resulting data
        assertEquals(1,users.size());
        //verify the the mock object userRepository.findAll() method was actually invoked
        Mockito.verify(userRepository).findAll();
    }
}
