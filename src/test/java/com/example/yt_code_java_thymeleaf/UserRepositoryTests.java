package com.example.yt_code_java_thymeleaf;

import com.example.yt_code_java_thymeleaf.user.User;
import com.example.yt_code_java_thymeleaf.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUserTest() {
        User user = new User();
        user.setFirstName("Stevenson");
        user.setLastName("Alex");
        user.setEmail("alex.stevenson@test.com");
        user.setPassword("stevenson1234");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isPositive();
    }

    @Test
    public void updateUserTest() {
        Optional<User> optionalUser = userRepository.findById(3);
        User user = optionalUser.orElseThrow();
        user.setPassword("updatedPassword");
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getPassword()).isEqualTo("updatedPassword");
    }

    @Test
    public void listAllTest() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void getTest() {
        Optional<User> user = userRepository.findById(4);
        assertThat(user).isPresent();
    }

    @Test
    public void deleteTest() {
        userRepository.deleteById(3);

        Optional<User> optionalUser = userRepository.findById(3);

        assertThat(optionalUser).isNotPresent();
    }
}
