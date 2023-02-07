package com.rliwanag.jpasecurity2;

import com.rliwanag.jpasecurity2.model.User;
import com.rliwanag.jpasecurity2.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Jpasecurity2ApplicationTests {

	@Autowired
	UserService userService;

	@BeforeAll
	public void setUp() throws Exception {
		User user1 = new User();
		user1.setUsername("nuzamaki");
		user1.setPassword("password");
		user1.setFirstname("Naruto");
		user1.setLastname("Uzamaki");
		user1.setRoles("ROLE_USER");
		this.userService.createUser(user1);

		User user2 = new User();
		user2.setUsername("hhyuga");
		user2.setPassword("password");
		user2.setRoles("ROLE_USER");
		user2.setFirstname("Hinata");
		user2.setLastname("Hyuga");
		this.userService.createUser(user2);
	}

	@Test
	public void shouldGetAUserByUsername() {
		Optional<User> userDto = this.userService.findUserByUsername("nuzamaki");

		assertThat(userDto.isPresent());
		assertThat(userDto.get().getUsername().equals("nuzamaki"));
	}

	@Test
	public void shouldGetAllUsersSortedByLastname() {
		List<User> users = this.userService.getAllUsers();

		assertThat(users != null);
		assertThat(users.size() > 0);
		assertThat(users.get(0).getUsername().equals("hhyug"));
	}

	@Test
	public void shouldCreateNewUser() throws Exception {
		List<User> userDtosBefore = this.userService.getAllUsers();

		User user = new User();
		user.setUsername("sharuno");
		user.setPassword("password");
		user.setRoles("ROLE_USER");
		user.setFirstname("Haruno");
		user.setLastname("Sakura");
		this.userService.createUser(user);

		List<User> userDtosAfter = this.userService.getAllUsers();

		assertThat(userDtosBefore.size() < userDtosAfter.size());
	}

	@Test
	public void shouldNotCreateNewUserWithSameFirstnameLastnameCombo() {
		try {
			User user = new User();
			user.setUsername("nuzamaki");
			user.setPassword("password");
			user.setRoles("ROLE_USER");
			user.setFirstname("Naruto");
			user.setLastname("Uzamaki");
			this.userService.createUser(user);
		} catch (Exception e) {
			assertThat(e.getMessage().equals("The username should be unique. The firstname and lastname combination should also be unique."));
		}

	}
}
