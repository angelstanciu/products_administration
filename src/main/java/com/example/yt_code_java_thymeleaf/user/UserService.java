package com.example.yt_code_java_thymeleaf.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Integer id) throws NoUserFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new NoUserFoundException("No user found with ID " + id);
    }

    public void deleteUserById(Integer id) throws NoUserFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoUserFoundException("No user found !");
        }
        userRepository.deleteById(id);
    }
}
