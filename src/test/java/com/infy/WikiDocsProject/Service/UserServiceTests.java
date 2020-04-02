package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.RegistrationException;
import com.infy.WikiDocsProject.Exception.UserAlreadyExistsException;
import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Role;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.RoleRepository;
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
import java.util.Map;
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
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    @Spy //Needed to mock methods that call class methods
            CustomUserDetailsService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<User> users;
    private List<User> admins;
    private List<Optional<User>> optionalUsers;
    private Map<String, Role> roles;
    

    @Before
    public void populateUsers(){
        users = TestDataCreator.createUsersWithRole("user");
        admins = TestDataCreator.createUsersWithRole("admin");
        
        optionalUsers = TestDataCreator.createOptionalUsers();
        
        roles = TestDataCreator.createRoles();
    }

    @Test
    public void testFindByEmail() {
        Optional<User> expectedUser = optionalUsers.get(0);

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
    public void testRegister_User(){
        User user = users.get(0);
        Optional<User> optionalUser = Optional.empty();
        user.setPassword("Pa$$w0rd");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(optionalUser);

        when(roleRepository.findByRole(anyString()))
                .thenReturn(roles.get("USER"));

        userService.register(user);

        verify(roleRepository, times(2)).findByRole(anyString());

        verify(bCryptPasswordEncoder, times(1)).encode(anyString());

        verify(userRepository, times(1)).insert(any(User.class));
    }

    @Test
    public void testRegister_Admin(){
        User user = users.get(0);
        Optional<User> optionalUser = Optional.empty();

        user.setEmail("muhammad.sultan96@gmail.com");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(optionalUser);

        when(roleRepository.findByRole(anyString()))
                .thenReturn(roles.get("ADMIN"));

        userService.register(user);

        verify(roleRepository, times(2)).findByRole(anyString());

        verify(bCryptPasswordEncoder, times(1)).encode(anyString());

        verify(userRepository, times(1)).insert(any(User.class));
    }

    @Test(expected = RegistrationException.class)
    public void testRegister_InvalidEmail(){
        User user = users.get(0);
        user.setEmail("1nv4l1d");

        userService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void testRegister_InvalidPassword(){
        User user = users.get(0);
        user.setPassword("invalid");

        userService.register(user);

    }

    @Test(expected = RegistrationException.class)
    public void testRegister_InvalidName(){
        User user = users.get(0);
        user.setName("1nv4l1d");

        userService.register(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testRegister_EmailExists(){
        User user = users.get(0);
        Optional<User> optionalUser = optionalUsers.get(0);

        when(userRepository.findByEmail(anyString()))
                .thenReturn(optionalUser);

        userService.register(user);
    }

    @Test
    public void getNameByEmail(){
        User expectedUser = users.get(0);

        doReturn(expectedUser)
                .when(userService).findByEmail(anyString());

        User actualUser = userService.findByEmail("john@gmail.com");

        assertEquals(expectedUser, actualUser);
    }
}
