package com.example.demo;


import com.example.demo.application.UserService;
import com.example.demo.domain.exceptions.UserException;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("plainPassword");
    }

    @Test
    public void testCreateUser() throws UserException {
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);


        User createdUser = userService.createUser(user);


        assertEquals(encodedPassword, createdUser.getPassword());
        assertEquals(user.getUsername(), createdUser.getUsername());
        verify(userRepository).save(user);
    }



    @Test
    public void testGetUsers() {

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("testUser2");
        List<User> users = Arrays.asList(user, user2);

        when(userRepository.findAll()).thenReturn(users);


        List<User> retrievedUsers = userService.getUsers();


        assertEquals(2, retrievedUsers.size());
        assertEquals(user.getUsername(), retrievedUsers.get(0).getUsername());
        assertEquals(user2.getUsername(), retrievedUsers.get(1).getUsername());
    }
}
