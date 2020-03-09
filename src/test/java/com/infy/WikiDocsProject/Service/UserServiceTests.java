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

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFindUserByName() throws Exception {
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());

        when(userRepository.findUserByName(anyString()))
                .thenReturn(expectedUser);

        User actualUser = userService.findUserByName("John");

        assertEquals(expectedUser.get(), actualUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByName_UserNotFound() throws Exception {
        Optional<User> expectedUser = Optional.empty();

        when(userRepository.findUserByName(anyString()))
                .thenReturn(expectedUser);

        userService.findUserByName("Invalid");
    }

    @Test
    public void testCreateArticleByUser() throws Exception {

        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());

        Article expectedArticle = new ArticleBuilder()
                .id(new ObjectId())
                .userId(expectedUser.get().getId())
                .status(Status.INITIAL)
                .channelId("testArticle")
                .editable(true)
                .build();

        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(expectedArticle);

        when(userRepository.findUserByName(anyString()))
                .thenReturn(expectedUser);

        when(articleRepository.findAllArticlesByUserId(any()))
                .thenReturn(expectedArticles);

        Article actualArticle = userService.createArticleByUser("John","testArticle");

        assertEquals(expectedArticle.getUserId(), actualArticle.getUserId());
        assertEquals(expectedArticle.getStatus(), actualArticle.getStatus());
        assertEquals(expectedArticle.getChannelId(), actualArticle.getChannelId());
        assertEquals(expectedArticle.isEditable(), actualArticle.isEditable());

    }

    @Test(expected = UserNotFoundException.class)
    public void testCreateArticleByUser_UserNotFound() throws Exception{

        when(userService.findUserByName(anyString()))
                .thenThrow(UserNotFoundException.class);

        userService.createArticleByUser("John", "");

    }
}
