package com.example.yt_code_java_thymeleaf.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void listAllUsersTest() {
        // given
        User user1 = new User("user1@email.com", "pass1", "first1", "last1", false);
        User user2 = new User("user2@email.com", "pass2", "first2", "last2", true);
        List<User> usersList = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(usersList);

        // when
        List<User> actualUsers = userService.findAllUsers();

        // then
        assertThat(actualUsers).hasSize(2);
        assertThat(usersList).isEqualTo(actualUsers);
    }

    @Test
    void addUserTest() {
        // given
        User userToBeSaved = new User(1, "t@email.com", "pass", "first", "last", false);
        when(userRepository.save(userToBeSaved)).thenReturn(userToBeSaved);

        // when
        User actualUser = userService.addUser(userToBeSaved);

        // then
        verify(userRepository).save(userToBeSaved);
        assertThat(userToBeSaved).isEqualTo(actualUser);
    }

    @Test
    void getUserByIdTest() throws NoUserFoundException {
        // given
        User userToBeFound = new User(10, "test@email.com", "password", "firstName", "lastName", false);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userToBeFound));

        // when
        User actualUser = userService.getUserById(anyInt());

        // then
        assertThat(actualUser).isEqualTo(userToBeFound);
    }

    @Test
    void deleteUserByIdTest() throws NoUserFoundException {
        User userToBeFound = new User(1,
                "test@email.com",
                "password",
                "firstName",
                "lastName",
                false);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userToBeFound));

        userService.deleteUserById(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}