package com.infy.WikiDocsProject.Service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.infy.WikiDocsProject.Exception.*;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.TestDataCreator;
import com.infy.WikiDocsProject.enums.Status;
import net.gjerull.etherpad.client.EPLiteClient;
import org.bson.types.ObjectId;
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
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * Article Service Tests Class
 *
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ArticleServiceTests {

    @Mock
    ArticleRepository articleRepository;

    @Mock
    UserRepository  userRepository;

    @Mock
    UserService userService;

    @Mock
    EPLiteClient epLiteClient;

    @InjectMocks
    @Spy //Needed to mock methods that call class methods
    ArticleServiceImpl articleService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Test Data
     */
    private List<User> users;
    private List<Optional<User>> optionalUsers;
    private List<Article> articles;
    private List<Optional<Article>> optionalArticles;

    /**
     * Populates test data.
     * Special note for articles and optionalArticles:
     *
     * Articles.get(x) | Status(x)
     *      0            APPROVED
     *      1            BETA
     *      2            INITIAL
     *      3            REJECTED
     *      4            DISCARDED
     * @see TestDataCreator Class
     */
    @Before
    public void populateTestData(){
        users = TestDataCreator.createUsers();
        articles = users.get(0).getArticles();

        optionalUsers = TestDataCreator.createOptionalUsers();
        optionalArticles = TestDataCreator.createOptionalArticles();
    }

    /**
     * Test validity of getAllArticlesByEmail
     */
    @Test
    public void testGetAllArticlesByEmail(){
        Optional<User> expectedUser = optionalUsers.get(0);
        // get all articles from expectedUser and set it to a list of expectedArticles
        List<Article> expectedArticles = expectedUser.get().getArticles();

        // when findUserByEmailId() is called with any string param then return expectedUser
        when(userService.findByEmail(anyString()))
                .thenReturn(expectedUser.get());
        // when findAllArticlesByEmailIdId() is called with any param then return expectedArticles
        when(articleRepository.findAllArticlesByEmailId(anyString()))
                .thenReturn(expectedArticles);

        // Actual call to getAllArticlesByEmail() from articleService class give name = ""John"
        // receive back an actual list of articles
        List<Article> actualArticles = articleService.getAllArticlesByEmailId("john@gmail.com");
        // compare expectedArticles and actualArticles
        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * Test to make sure UserNotFoundException
     * gets thrown
     */
    @Test(expected = UserNotFoundException.class)
    public void testGetAllArticlesByEmailId_UserNotFound(){
        // when findUserByEmailId() is called with any string param from userService
        // then throw UserNotFoundException()
        when(userService.findByEmail(anyString()))
                .thenThrow(UserNotFoundException.class);

        // Actual call to getAllArticlesByEmailId() from articleService class with given email
        // should throw expected exception
        articleService.getAllArticlesByEmailId("john@gmail.com");
    }

//    /**
//     * Test validity of getApprovedArticles
//     */
//    @Test
//    public void testGetApprovedArticles(){
//
//        List<Article> expectedArticles = articles;
//
//        // Set expectedArticles status to APPROVED
//        expectedArticles.forEach(article -> article.setStatus(Status.APPROVED));
//
//        // when findArticlesByStatus() is called from articleRepository class given any param
//        // return expectedArticles
//        when(articleRepository.findArticlesByStatus(any()))
//                .thenReturn(expectedArticles);
//
//        // Actual call to getApprovedArticles() from articleService class
//        // Receive a list of actualArticles
//        List<Article> actualArticles = articleService.getApprovedArticles();
//
//        // compare expectedArticles and actualArticles
//        assertEquals(expectedArticles, actualArticles);
//    }
//
//    /**
//     * Test validity of getBetaArticles
//     */
//    @Test
//    public void testGetBetaArticles(){
//        List<Article> expectedArticles = articles;
//        // Set expectedArticles status to BETA
//        expectedArticles.forEach(article -> article.setStatus(Status.BETA));
//
//        // when findArticlesByStatus() is called from articleRepository class given any param
//        // return expectedArticles
//        when(articleRepository.findArticlesByStatus(any(Status.class)))
//                .thenReturn(expectedArticles);
//
//        // Actual call to getBetaArticles() from articleService class
//        // Receive a list of actualArticles
//        List<Article> actualArticles = articleService.getBetaArticles();
//
//        // compare expectedArticles and actualArticles
//        assertEquals(expectedArticles, actualArticles);
//
//    }

    /**
     * Test validity of finding articles by their ID
     */
    @Test
    public void testFindById(){
        Optional<Article> expectedArticle = optionalArticles.get(0);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);

        // actual call to getArticleByChannelId() from articleService class with given param "testArticle"
        // receive back actualArticle
        Article actualArticle = articleService.findById(new ObjectId());

        // compare expectedArticle and actualArticle
        assertEquals(expectedArticle.get(), actualArticle);
    }

    /**
     * Test validity of not finding articles with given ID
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testFindById_ArticleNotFound(){
        // set expectedArticle to be empty
        Optional<Article> expectedArticle = Optional.empty();

        // when findArticleByChannelId() is called from articleRepository class with any string param
        // then return expectedArticle
        when(articleRepository.findById((any(ObjectId.class))))
                .thenReturn(expectedArticle);

        // actual call getArticleByChannelId() with "Invalid" param from articleService class
        articleService.findById(new ObjectId());
    }

    /**
     * Test submitArticle with an initial article given
     */
    @Test
    public void testSubmitArticle_InitialArticle(){
        Article expectedArticle = articles.get(2);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        //actual call to submitArticle() from articleService with "Invalid" param
        Article actualArticle = articleService.submitArticle(new ObjectId().toHexString());

        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * Test submit article with reject article given
     */
    @Test
    public void testSubmitArticle_RejectedArticle(){
        //given
        Article expectedArticle = articles.get(3);
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle(new ObjectId().toHexString());
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * Test submit article with approved article given
     * throws SubmittingArticleIsApprovedException
     */
    @Test(expected = SubmittingArticleIsApprovedException.class)
    public void testSubmitArticle_ArticleApproved(){
        Article expectedArticle = articles.get(0);
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        //actual call to submitArticle() from articleService with "testArticle" param
        articleService.submitArticle(new ObjectId().toHexString());
    }

    /**
     * Test submit article with beta article given
     * throws SubmittingArticleIsBetaException
     */
    @Test(expected = SubmittingArticleIsBetaException.class)
    public void testSubmitArticle_BetaArticle(){
        Article expectedArticle = articles.get(1);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        //actual call to submitArticle() from articleService with "testArticle" param
        articleService.submitArticle(new ObjectId().toHexString());
    }

    /**
     * Test submit article with discarded article given
     * throws SubmittingArticleIsDiscardedException
     */
    @Test(expected = SubmittingArticleIsDiscardedException.class)
    public void testSubmitArticle_ArticleDiscarded(){
        Article expectedArticle = articles.get(4);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        //actual call to submitArticle() from articleService with "testArticle" param
        articleService.submitArticle(new ObjectId().toHexString());
    }

    /**
     * Test submit article will
     * throw ArticleNotFoundException
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testSubmitArticle_ArticleNotFound(){
        // when findArticleByChannelId() with any string param from articleRepository
        // then return expectedArticle
        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(any(ObjectId.class));

        // actual submitArticle() with "Invalid" param from articleService
        articleService.submitArticle(new ObjectId().toHexString());
    }

    /**
     * Test approve article with beta article given
     */
    @Test
    public void testApproveArticle_BetaArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(1);

        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId().toHexString());
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * Test approve article with rejected article given
     * throws ApprovingArticleIsStillRejectedException
     */
    @Test(expected = ApprovingArticleIsStillRejectedException.class)
    public void testApproveArticle_RejectedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(3);

        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId().toHexString());
        // compare Status.REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * Test approve article with initial article given
     * throws ApprovingArticleIsInitialException
     */
    @Test(expected = ApprovingArticleIsInitialException.class)
    public void testApproveArticle_InitialArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(2);
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId().toHexString());
        // compare Status.INITIALand actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * Test approve article with approve article given
     * throws ApprovingArticleIsApprovedException
     */
    @Test(expected = ApprovingArticleIsApprovedException.class)
    public void testApproveArticle_ApprovedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(0);
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId().toHexString());
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * Test approve article with discarded article given
     * throws ApprovingArticleIsDiscardedException
     */
    @Test(expected = ApprovingArticleIsDiscardedException.class)
    public void testApproveArticle_DiscardedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(4);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId().toHexString());
        // compare Status.DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * Test approve article
     * will throw ArticleNotFoundException
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testApproveArticle_ArticleNotFound(){
        // when findArticleByChannelId() is called with any string param from articleRepository class
        // then return expectedArticle
        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(any(ObjectId.class));
        // actual call to approveArticle() with "Invalid" param from articleService
        articleService.approveArticle(new ObjectId().toHexString());
    }

    /**
     * Test reject article with beta article given
     */
    @Test
    public void testRejectArticle_BetaArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(1);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId().toHexString());
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }


    /**
     * Test reject article with beta article given
     * becomes discarded after more than 3 edits
     */
    @Test
    public void testRejectArticle_BetaArticleBecomesDiscarded(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(1);
        expectedArticle.setRejectedCount(3);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId().toHexString());
        // compare Status DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * Test reject article with initial article given
     * throws RejectingArticleIsInitialException
     */
    @Test(expected = RejectingArticleIsInitialException.class)
    public void testRejectArticle_InitialArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(2);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId().toHexString());
        // compare Status INITIAL and actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * Test reject article with approved article given
     * throws RejectingArticleIsApprovedException
     */
    @Test(expected = RejectingArticleIsApprovedException.class)
    public void testRejectArticle_ApprovedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(0);

        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId().toHexString());
        // compare Status APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * Test reject article with rejected article given
     * throws RejectingArticleIsStillRejectedException
     */
    @Test(expected = RejectingArticleIsStillRejectedException.class)
    public void testRejectArticle_RejectedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(3);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId().toHexString());
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * Test reject article with discarded article given
     * throws RejectingArticleIsDiscardedException
     */
    @Test(expected = RejectingArticleIsDiscardedException.class)
    public void testRejectArticle_DiscardedArticle(){
        Article expectedArticle = articles.get(4);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId().toHexString());
        // compare Status RDISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * Test reject article
     * will throw ArticleNotFoundException
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testRejectArticle_ArticleNotFound(){
        // when findArticleByChannelId() is called with any string param from articleRepository
        // then return expectedArticle
        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(any(ObjectId.class));
        // actual call rejectArticle() from articleService with "Invalid" param
        articleService.rejectArticle(new ObjectId().toHexString());
    }

//    /**
//     * Test getAllApprovedArticlesByEmailId
//     */
//    @Test
//    public void testGetAllApprovedArticlesByEmailId(){
//        User expectedUser = users.get(0);
//
//        List<Article> expectedArticles = articles.stream()
//                .filter(article -> article.getStatus() == Status.APPROVED)
//                .collect(Collectors.toList());
//
//        doReturn(expectedUser)
//                .when(userService).findByEmail(anyString());
//
//        when(articleRepository.findAllArticlesByEmailIdAndStatus(anyString(), any(Status.class), anyInt(), anyInt()))
//                .thenReturn(expectedArticles);
//
//        List<Article> actualArticles =
//                articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.APPROVED);
//
//        assertEquals(expectedArticles, actualArticles);
//    }
//
//    /**
//     * Test getAllApprovedArticlesByEmailId
//     * throws UserNotFoundException
//     */
//    @Test(expected = UserNotFoundException.class)
//    public void testGetAllApprovedArticlesByEmailId_Invalid(){
//
//        when(userService.findByEmail(anyString()))
//                .thenThrow(new UserNotFoundException());
//
//        articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.APPROVED);
//    }
//
//    /**
//     * Test getAllBetaArticlesByEmailId
//     */
//    @Test
//    public void testGetAllBetaArticlesByEmailId(){
//        User expectedUser = users.get(0);
//
//        List<Article> expectedArticles = articles.stream()
//                .filter(article -> article.getStatus() == Status.BETA)
//                .collect(Collectors.toList());
//
//        doReturn(expectedUser)
//                .when(userService).findByEmail(anyString());
//
//        when(articleRepository.findAllArticlesByEmailIdAndStatus(anyString(), any(Status.class)))
//                .thenReturn(expectedArticles);
//
//        List<Article> actualArticles =
//                articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.BETA);
//
//        assertEquals(expectedArticles, actualArticles);
//    }
//
//    /**
//     * Test getAllBetaArticlesByEmailId
//     * throws UserNotFoundException
//     */
//    @Test(expected = UserNotFoundException.class)
//    public void testGetAllBetaArticlesByEmailId_Invalid(){
//
//        when(userService.findByEmail(anyString()))
//                .thenThrow(new UserNotFoundException());
//
//        articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.BETA);
//    }
//
//    /**
//     * Test testGetAllInitialArticlesByEmailId
//     */
//    @Test
//    public void testGetAllInitialArticlesByEmailId(){
//        Optional<User> expectedUser = optionalUsers.get(0);
//        List<Article> expectedArticles = articles.stream()
//                .filter(article -> article.getStatus() == Status.INITIAL)
//                .collect(Collectors.toList());
//
//        when(userService.findByEmail(anyString()))
//                .thenReturn(expectedUser.get());
//
//        when(articleRepository.findAllArticlesByEmailIdAndStatus(anyString(), any(Status.class)))
//                .thenReturn(expectedArticles);
//
//        List<Article> actualArticles =
//                articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.INITIAL);
//
//        assertEquals(expectedArticles, actualArticles);
//    }
//
//    /**
//     * Test testGetAllInitialArticlesByEmailId
//     * throws UserNotFoundException
//     */
//    @Test(expected = UserNotFoundException.class)
//    public void testGetAllInitialArticlesByEmailId_Invalid(){
//
//        when(userService.findByEmail(anyString()))
//                .thenThrow(new UserNotFoundException());
//
//        articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.INITIAL);
//    }
//
//    /**
//     * Test testGetRejectedArticlesByEmailId
//     */
//    @Test
//    public void testGetRejectedArticlesByEmailId(){
//        Optional<User> expectedUser = optionalUsers.get(0);
//
//        List<Article> expectedArticles = articles.stream()
//                .filter(article -> article.getStatus() == Status.REJECTED)
//                .collect(Collectors.toList());
//
//        when(userService.findByEmail(anyString()))
//                .thenReturn(expectedUser.get());
//
//        when(articleRepository.findAllArticlesByEmailIdAndStatus(anyString(), any(Status.class)))
//                .thenReturn(expectedArticles);
//
//        List<Article> actualArticles =
//                articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.REJECTED);
//
//        assertEquals(expectedArticles, actualArticles);
//    }
//
//    /**
//     * Test testGetRejectedArticlesByEmailId
//     * throws UserNotFoundException
//     */
//    @Test(expected = UserNotFoundException.class)
//    public void testGetAllRejectedArticlesByEmailId_Invalid(){
//
//        when(userService.findByEmail(anyString()))
//                .thenThrow(new UserNotFoundException());
//
//        articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.REJECTED);
//    }
//
//    /**
//     * Test testGetAllDiscardedArticlesByEmailId
//     */
//    @Test
//    public void testGetAllDiscardedArticlesByEmailId(){
//        Optional<User> expectedUser = optionalUsers.get(0);
//        List<Article> expectedArticles = articles.stream()
//                .filter(article -> article.getStatus() == Status.BETA)
//                .collect(Collectors.toList());
//
//        when(userService.findByEmail(anyString()))
//                .thenReturn(expectedUser.get());
//
//        when(articleRepository.findAllArticlesByEmailIdAndStatus(anyString(), any(Status.class)))
//                .thenReturn(expectedArticles);
//
//        List<Article> actualArticles =
//                articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.DISCARDED);
//
//        assertEquals(expectedArticles, actualArticles);
//    }
//
//    /**
//     * Test testGetAllDiscardedArticlesByEmailId
//     * throws UserNotFoundException
//     */
//    @Test(expected = UserNotFoundException.class)
//    public void testGetAllDiscardedArticlesByEmailId_Invalid(){
//
//        when(userService.findByEmail(anyString()))
//                .thenThrow(new UserNotFoundException());
//
//        articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.DISCARDED);
//    }

    /**
     * Test createArticleByEmail
     */
    @Test
    public void testCreateArticleByEmail() {
        // Generate expectedUser using UserBuilder()
        Optional<User> expectedUser = optionalUsers.get(0);

        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = articles.get(2);

        expectedArticle.setEmailId(expectedUser.get().getEmail());

        // when findUserByEmail() is called with any string param
        // then return expectedUser
        when(userService.findByEmail(anyString()))
                .thenReturn(expectedUser.get());

        when(articleRepository.findAllArticlesByEmailId(anyString()))
                .thenReturn(expectedUser.get().getArticles());

        doNothing().when(epLiteClient).createPad(anyString());

        // actual call to createArticleByEmail with "John@gmail.com","testArticle" params
        Article actualArticle = articleService.createArticleByEmail("John@gmail.com");

        // compare expectedArticle.getEmailId() and actualArticle.getEmailId()
        assertEquals(expectedArticle.getEmailId(), actualArticle.getEmailId());
        // compare expectedArticle.getStatus(), actualArticle.getStatus()
        assertEquals(expectedArticle.getStatus(), actualArticle.getStatus());
    }

    /**
     * Test createArticleByEmail
     * throws UserNotFoundException
     */
    @Test(expected = UserNotFoundException.class)
    public void testCreateArticleByEmail_UserNotFound(){
        // when findUserByEmail() is called with any string param from userService
        // then throw User Not Found Exception
        when(userService.findByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        // actual call to createArticleByEmail() with "John@gmail.com", "Invalid" param
        articleService.createArticleByEmail("John@gmail.com");
    }



}