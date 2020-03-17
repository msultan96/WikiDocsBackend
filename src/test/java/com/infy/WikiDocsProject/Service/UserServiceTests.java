package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.PasswordIncorrectException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.TestDataCreator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 *
 * User Service Tests Class
 *
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    @Spy //Needed to mock methods that call class methods
    UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<User> users;
    private List<Optional<User>> optionals;

    @Before
    public void populateUsers(){
        users = TestDataCreator.createUsers();
        optionals = TestDataCreator.createOptionalUsers();
    }

    @Test
    public void testFindByEmail() {
        Optional<User> expectedUser = optionals.get(0);

        when(userRepository.findByEmail(anyString()))
                .thenReturn(expectedUser);

        User actualUser = userService.findByEmail("john@gmail.com");

        assertEquals(expectedUser.get(), actualUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindByEmail_UserNotFound() {
        Optional<User> expectedUser = Optional.empty();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(expectedUser);

        userService.findByEmail("john@gmail.com");
    }

    @Test
    public void findUserByEmailAndPassword() {
        User expectedUser = users.get(0);

        doReturn(expectedUser)
                .when(userService).findByEmail(anyString());

        when(bCryptPasswordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        User actualUser = userService.findByEmailAndPassword("John@gmail.com", "johnsPassword");

        assertEquals(expectedUser, actualUser);
    }

    @Test(expected= PasswordIncorrectException.class)
    public void findUserByEmailAndPassword_InvalidPassword() {
        User expectedUser = users.get(0);

        doReturn(expectedUser)
                .when(userService).findByEmail(anyString());

        when(bCryptPasswordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        userService.findByEmailAndPassword("John@gmail.com", "johnsPassword");
    }
}
