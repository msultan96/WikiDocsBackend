package com.infy.WikiDocsProject.Service;

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
import org.springframework.boot.test.context.SpringBootTest;
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
                // attribute set
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
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
        userService.findUserByEmail("noUser@gmail.com");
    }

    /**
     * @Test
     * @Name testCreateArticleByEmail
     * @Desciption Test create article by Email to be valid
     * @throws Exception
     */
    @Test
    public void testCreateArticleByEmail() throws Exception {
        // Generate expectedUser using UserBuilder()
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                // attribute set
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
                .build());

        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = new ArticleBuilder()
                // attribute set
                .id(new ObjectId())
                .emailId(expectedUser.get().getEmail())
                .status(Status.INITIAL)
                .channelId("testArticle")
                .editable(true)
                .build();

        // when findUserByEmail() is called with any string param
        // then return expectedUser
        when(userRepository.findUserByEmail(anyString()))
                .thenReturn(expectedUser);

        // actual call to createArticleByEmail with "John@gmail.com","testArticle" params
        Article actualArticle = userService.createArticleByEmail("John@gmail.com","testArticle");

        // compare expectedArticle.getEmailId() and actualArticle.getEmailId()
        assertEquals(expectedArticle.getEmailId(), actualArticle.getEmailId());
        // compare expectedArticle.getName() and actualArticle.getName()
        assertEquals(expectedArticle.getName(), actualArticle.getName());
        // compare expectedArticle.getContent() and actualArticle.getContent()
        assertEquals(expectedArticle.getContent(), actualArticle.getContent());
        // compare expectedArticle.getStatus(), actualArticle.getStatus()
        assertEquals(expectedArticle.getStatus(), actualArticle.getStatus());
        // compare expectedArticle.getChannelId(), actualArticle.getChannelId()
        assertEquals(expectedArticle.getChannelId(), actualArticle.getChannelId());
        // compare expectedArticle.isEditable(), actualArticle.isEditable()
        assertEquals(expectedArticle.isEditable(), actualArticle.isEditable());

    }
    /**
     * @Test
     * @Name testCreateArticleByUser_UserNotFound
     * @Desciption Test create article by user to be invalid
     * @throws Exception
     */

    @Test(expected = UserNotFoundException.class)
    public void testCreateArticleByEmail_UserNotFound() throws Exception{
        // when findUserByEmail() is called with any string param from userService
        // then throw User Not Found Exception
        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException());

        // actual call to createArticleByEmail() with "John@gmail.com", "Invalid" param
        userService.createArticleByEmail("John@gmail.com", "invalid");

    }
}
