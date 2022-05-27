package com.example.yt_code_java_thymeleaf.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
            "test@email.com",
            "password",
            "firstName",
            "lastName",
            false);
    }

    @Test
    public void addUserTest() {
        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isPositive();
    }

    @Test
    public void updateUserTest() {
        // given
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User databaseUser = optionalUser.orElseThrow();

        // when
        databaseUser.setPassword("updatedPassword");
        User updatedUser = userRepository.save(user);

        // then
        assertThat(updatedUser.getPassword()).isEqualTo("updatedPassword");
    }

    @Test
    public void findAllUsersTest() {
        // given
        userRepository.save(user);

        // when
        Iterable<User> users = userRepository.findAll();

        // then
        assertThat(users).hasSizeGreaterThan(0);
    }

    @Test
    public void findUserByIdTest() {
        // given
        userRepository.save(user);

        // when
        Optional<User> userOptional = userRepository.findById(user.getId());

        // then
        assertThat(userOptional).isPresent();
    }

    @Test
    public void deleteUserByIdTest() {
        // given
        userRepository.save(user);
        Integer userId = user.getId();

        // when
        userRepository.deleteById(userId);

        // then
        assertThat(userRepository.findById(userId)).isEmpty();
    }
}
