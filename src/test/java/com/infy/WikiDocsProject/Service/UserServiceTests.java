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
     * @Name testFindUserByName
     * @Desciption Test find user by given name to be valid
     * @throws Exception
     */
    @Test
    public void testFindUserByName() throws Exception {
    	// Generate expectedArticle using UserBuilder()
        Optional<User> expectedUser = Optional.of(new UserBuilder()
        		// attribute set
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());

        // when findUserByName() is called with any string param form userRepository
        // then return expectedUser
        when(userRepository.findUserByName(anyString()))
                .thenReturn(expectedUser);
        // Actual call to findUserByName() with "John" param from userService
        // receive  back actualUser
        User actualUser = userService.findUserByName("John");
        // compare expectedUser.get() and actualUser
        assertEquals(expectedUser.get(), actualUser);
    }
    /**
     * @Test
     * @Name testFindUserByName_UserNotFound
     * @Desciption Test find user by given name to be invalid
     * @throws Exception
     */

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByName_UserNotFound() throws Exception {
    	// create expectedUser and set it to empty
        Optional<User> expectedUser = Optional.empty();
        // when findUserByName() is called with any string param from userRepository
        // then return expectedUser
        when(userRepository.findUserByName(anyString()))
                .thenReturn(expectedUser);
        // actual call to findUserByName() with "Invalid" param
        userService.findUserByName("Invalid");
    }

    /**
     * @Test
     * @Name testCreateArticleByUser
     * @Desciption Test create article by user to be valid
     * @throws Exception
     */
    @Test
    public void testCreateArticleByUser() throws Exception {
    	// Generate expectedArticle using UserBuilder()
        Optional<User> expectedUser = Optional.of(new UserBuilder()
        		// attribute set
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());
     // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = new ArticleBuilder()
        		// attribute set
                .id(new ObjectId())
                .userId(expectedUser.get().getId())
                .status(Status.INITIAL)
                .channelId("testArticle")
                .editable(true)
                .build();

        // create a new list named expectedArticles
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(expectedArticle);
        // when findUserByName() is called with any string param
        // then return expectedUser
        when(userRepository.findUserByName(anyString()))
                .thenReturn(expectedUser);
     // when findAllArticlesByUserId() is called with any param
        // then return expectedArticles
        when(articleRepository.findAllArticlesByUserId(any()))
                .thenReturn(expectedArticles);
        // actual call to createArticleByUser with "John","testArticle" params 
        Article actualArticle = userService.createArticleByUser("John","testArticle");
        
        // compare expectedArticle.getUserId() and actualArticle.getUserId()
        assertEquals(expectedArticle.getUserId(), actualArticle.getUserId());
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
    public void testCreateArticleByUser_UserNotFound() throws Exception{
    	// when findUserByName() is called with any string param from userService
        // then throw User Not Found Exception
        when(userService.findUserByName(anyString()))
                .thenThrow(UserNotFoundException.class);
        // actual call to createArticleByUser() with "John"," " param
        userService.createArticleByUser("John", "");

    }
}
