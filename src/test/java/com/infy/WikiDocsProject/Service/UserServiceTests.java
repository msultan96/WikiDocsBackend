package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.PasswordIncorrectException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.ArticleBuilder;
import com.infy.WikiDocsProject.Utility.UserBuilder;
import com.infy.WikiDocsProject.enums.Role;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
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
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {

    // Mock repository and service objects declared
    @Mock
    UserRepository userRepository;

    @Mock
    ArticleRepository articleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // Inject mock services
    @InjectMocks
    UserServiceImpl userService;
    // Set Expected Exception rule
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * @Test
     * @Name testFindUserByEmail
     * @Desciption Test find user by given email to be valid
     * @throws Exception
     */
    @Test
    public void testFindUserByEmail() throws Exception {
        // Generate expectedUser using UserBuilder()
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .email("John@gmail.com")
                .build());

        // when findUserByEmail() is called with any string param form userRepository
        // then return expectedUser
        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(expectedUser);

        // Actual call to findUserByEmail() with "John@gmail.com" param from userService
        // receive  back actualUser
        User actualUser = userService.findUserByEmail("John@gmail.com");

        // compare expectedUser.get() and actualUser
        assertEquals(expectedUser.get(), actualUser);
    }
    /**
     * @Test
     * @Name testFindUserByEmail_UserNotFound
     * @Desciption Test find user by given email to be invalid
     * @throws Exception
     */
    @Test(expected = UserNotFoundException.class)
    public void testFindUserByEmail_UserNotFound() throws Exception {
        // create expectedUser and set it to empty
        Optional<User> expectedUser = Optional.empty();

        // when findUserByEmail() is called with any string param from userRepository
        // then return expectedUser
        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(expectedUser);

        // actual call to findUserByEmail() with "noUser@gmail.com" param
        userService.findUserByEmail("john@gmail.com");
    }

    @Test
    public void findUserByEmailAndPassword() throws Exception {
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .email("John@gmail.com")
                .password("$2a$10$TefPIRcSjTwrUVuNCbBik.L9khixW3zjr0lbF7J5tekMMg9ISrP1C")
                .build());

        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(expectedUser);

        when(bCryptPasswordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        User actualUser = userService.findUserByEmailAndPassword("John@gmail.com", "johnsPassword");

        assertEquals(expectedUser.get(), actualUser);
    }

    @Test(expected= UserNotFoundException.class)
    public void findUserByEmailAndPassword_InvalidEmail() throws Exception {
        Optional<User> expectedUser = Optional.empty();

        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(expectedUser);

        userService.findUserByEmailAndPassword("John@gmail.com", "johnsPassword");
    }

    @Test(expected= PasswordIncorrectException.class)
    public void findUserByEmailAndPassword_InvalidPassword() throws Exception {
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .email("John@gmail.com")
                .password("$2a$10$YX.SjCQvt83xlHVhZJcb2uOsUxkaPf1OPKVEsePVIo8frR3dLQjs6")
                .build());

        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(expectedUser);

        when(bCryptPasswordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        userService.findUserByEmailAndPassword("John@gmail.com", "johnsPassword");
    }
}
