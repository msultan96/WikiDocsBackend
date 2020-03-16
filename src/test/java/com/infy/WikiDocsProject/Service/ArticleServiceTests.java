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
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * Article Service Tests Class
 *
 */
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
    EtherPadService etherPadService;

    // Inject mock services
    @InjectMocks
    ArticleServiceImpl articleService;

    // Set Expected Exception rule
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<User> users;
    private List<Optional<User>> optionalUsers;
    private List<Article> articles;
    private List<Optional<Article>> optionalArticles;

    @Before
    public void populateUsers(){
        users = TestDataCreator.createUsers();
        optionalUsers = TestDataCreator.createOptionalUsers();

        articles = TestDataCreator.createArticles(users.get(0).getEmail());
        optionalArticles = TestDataCreator.createOptionalArticles();
    }

    /**
     * @Test
     * @Name testGetAllArticlesByEmail
     * @Desciption Test get all articles of given user to be valid
     *
     */
    @Test
    public void testGetAllArticlesByEmail() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<User> expectedUser = optionalUsers.get(0);
        // get all articles from expectedUser and set it to a list of expectedArticles
        List<Article> expectedArticles = expectedUser.get().getArticles();

        // when findUserByEmailId() is called with any string param then return expectedUser
        when(userService.findUserByEmail(anyString()))
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
     * @Test
     * @Name testGetAllArticlesByEmailId_UserNotFound
     * @Desciption Test get all articles of given user to be invalid
     *
     */
    @Test(expected = UserNotFoundException.class)
    public void testGetAllArticlesByEmailId_UserNotFound(){
        // when findUserByEmailId() is called with any string param from userService
        // then throw UserNotFoundException()
        when(userService.findUserByEmail(anyString()))
                .thenReturn(null);
        // Actual call to getAllArticlesByEmailId() from articleService class with given email
        // should throw expected exception
        articleService.getAllArticlesByEmailId("john@gmail.com");
    }

    /**
     * @Test
     * @Name testGetApprovedArticles
     * @Desciption Test get all approved articles to be valid
     */
    @Test
    public void testGetApprovedArticles(){
        // create new list of article named expectedArticles
        List<Article> expectedArticles = articles;
        // Set expectedArticles status to APPROVED
        expectedArticles.forEach(article -> {article.setStatus(Status.APPROVED);});

        // when findArticlesByStatus() is called from articleRepository class given any param
        // return expectedArticles
        when(articleRepository.findArticlesByStatus(any()))
                .thenReturn(expectedArticles);

        // Actual call to getApprovedArticles() from articleService class
        // Receive a list of actualArticles
        List<Article> actualArticles = articleService.getApprovedArticles();

        // compare expectedArticles and actualArticles
        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * @Test
     * @Name testGetBetaArticles
     * @Desciption Test get all beta articles to be valid
     */
    @Test
    public void testGetBetaArticles(){
        // create new list of article named expectedArticles
        List<Article> expectedArticles = articles;
        // Set expectedArticles status to BETA
        expectedArticles.forEach(article -> {article.setStatus(Status.BETA);});

        // when findArticlesByStatus() is called from articleRepository class given any param
        // return expectedArticles
        when(articleRepository.findArticlesByStatus(any()))
                .thenReturn(expectedArticles);

        // Actual call to getBetaArticles() from articleService class
        // Receive a list of actualArticles
        List<Article> actualArticles = articleService.getBetaArticles();

        // compare expectedArticles and actualArticles
        assertEquals(expectedArticles, actualArticles);

    }

    /**
     * @Test
     * @Name testGetArticleByChannelId
     * @Desciption Test get all articles by channelId to be valid
     */
    @Test
    public void testGetArticleById(){
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = optionalArticles.get(0);
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call to getArticleByChannelId() from articleService class with given param "testArticle"
        // receive back actualArticle
        Article actualArticle = articleService.getArticleById("5e6eae198b743a023913779a");
        // compare expectedArticle and actualArticle
        assertEquals(expectedArticle.get(), actualArticle);
    }

    /**
     * @Test
     * @Name testGetArticleByChannelId_ArticleNotFound
     * @Desciption Test get all articles by channelId to be invalid
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testGetArticleByChannelId_ArticleNotFound(){
        // set expectedArticle to be empty
        Optional<Article> expectedArticle = Optional.empty();

        // when findArticleByChannelId() is called from articleRepository class with any string param
        // then return expectedArticle
        when(articleRepository.findById((any(ObjectId.class))))
                .thenReturn(expectedArticle);
        // actual call getArticleByChannelId() with "Invalid" param from articleService class
        articleService.getArticleById("5e6eae198b743a023913779a");
    }

    /**
     * @Test
     * @Name testSubmitArticle_InitialArticle
     * @Desciption Test submit and initial article to be valid
     */
    @Test
    public void testSubmitArticle_InitialArticle(){
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = optionalArticles.get(2);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);

        //actual call to submitArticle() from articleService with "Invalid" param
        Article actualArticle = articleService.submitArticle(new ObjectId());

        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_BetaArticle
     * @Desciption Test submit and beta article to be valid
     */
    @Test
    public void testSubmitArticle_BetaArticle(){
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = optionalArticles.get(1);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle(new ObjectId());
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testSubmitArticle_RejectedArticle
     * @Desciption Test submit and rejected article to be valid
     */
    @Test
    public void testSubmitArticle_RejectedArticle(){
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = optionalArticles.get(3);
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle(new ObjectId());
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testSubmitArticle_ArticleApproved
     * @Desciption Test submit and approved article to be valid
     *
     */
    @Test(expected = SubmittingArticleIsApprovedException.class)
    public void testSubmitArticle_ArticleApproved(){
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = optionalArticles.get(0);
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle(new ObjectId());
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_ArticleDiscarded
     * @Desciption Test submit and discarded article to be valid
     *
     */
    @Test(expected = SubmittingArticleIsDiscardedException.class)
    public void testSubmitArticle_ArticleDiscarded(){
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = optionalArticles.get(4);

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle(new ObjectId());
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_ArticleNotFound
     * @Desciption Test submit article to be invalid
     *
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testSubmitArticle_ArticleNotFound(){
        // Create expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() with any string param from articleRepository
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual submitArticle() with "Invalid" param from articleService
        articleService.submitArticle(new ObjectId());
    }

    /**
     * @Test
     * @Name testApproveArticle_BetaArticle
     * @Desciption Test approved-beta article to be valid
     *
     */
    @Test
    public void testApproveArticle_BetaArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(1);
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId());
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_RejectedArticle
     * @Desciption Test approved-reject article to be valid
     *
     */
    @Test(expected = ApprovingArticleIsStillRejectedException.class)
    public void testApproveArticle_RejectedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(3);

        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId());
        // compare Status.REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @NametestApproveArticle_InitialArticle
     * @Desciption Test approved-initial article to be valid
     *
     */
    @Test(expected = ApprovingArticleIsInitialException.class)
    public void testApproveArticle_InitialArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(2);
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId());
        // compare Status.INITIALand actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_ApprovedArticle
     * @Desciption Test approved-approved article to be valid
     *
     */
    @Test(expected = ApprovingArticleIsApprovedException.class)
    public void testApproveArticle_ApprovedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(0);
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId());
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_DiscardedArticle
     * @Desciption Test approved-discarded article to be valid
     *
     */
    @Test(expected = ApprovingArticleIsDiscardedException.class)
    public void testApproveArticle_DiscardedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(4);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle(new ObjectId());
        // compare Status.DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testApproveArticle_ArticleNotFound
     * @Desciption Test approved article article not found to be invalid
     *
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testApproveArticle_ArticleNotFound(){
        // create expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() is called with any string param from articleRepository class
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call to approveArticle() with "Invalid" param from articleService
        articleService.approveArticle(new ObjectId());
    }


    /**
     * @Test
     * @Name testRejectArticle_BetaArticle
     * @Desciption Test rejected article and beta article to be valid
     *
     */
    @Test
    public void testRejectArticle_BetaArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(1);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId());
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }


    /**
     * @Test
     * @Name testRejectArticle_BetaArticle
     * @Desciption Test rejected article and beta article becomes discarded to be valid
     *
     */
    @Test
    public void testRejectArticle_BetaArticleBecomesDiscarded(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(1);
        expectedArticle.get().setRejectedCount(3);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId());
        // compare Status DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testRejectArticle_InitialArticle
     * @Desciption Test rejected article and initial article to be valid
     *
     */
    @Test(expected = RejectingArticleIsInitialException.class)
    public void testRejectArticle_InitialArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(2);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId());
        // compare Status INITIAL and actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_ApprovedArticle
     * @Desciption Test rejected article and approved article to be valid
     *
     */
    @Test(expected = RejectingArticleIsApprovedException.class)
    public void testRejectArticle_ApprovedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(0);

        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId());
        // compare Status APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_RejectedArticle
     * @Desciption Test rejected article and rejected article to be valid
     *
     */
    @Test(expected = RejectingArticleIsStillRejectedException.class)
    public void testRejectArticle_RejectedArticle(){
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = optionalArticles.get(3);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId());
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testRejectArticle_RejectedArticle
     * @Desciption Test rejected article and discarded article to be valid
     *
     */
    @Test(expected = RejectingArticleIsDiscardedException.class)
    public void testRejectArticle_DiscardedArticle(){
        Optional<Article> expectedArticle = optionalArticles.get(4);
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle(new ObjectId());
        // compare Status RDISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_ArticleNotFound
     * @Desciption Test rejected article article not found to be invalid
     *
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testRejectArticle_ArticleNotFound(){
        //create  expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() is called with any string param from articleRepository
        // then return expectedArticle
        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() from articleService with "Invalid" param
        articleService.rejectArticle(new ObjectId());
    }

    @Test
    public void testGetAllApprovedArticlesByEmailId(){
        Optional<User> expectedUser = optionalUsers.get(0);
        List<Article> expectedArticles = articles.stream()
                .filter(article -> article.getStatus() == Status.APPROVED)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllApprovedArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllApprovedArticlesByEmailId_Invalid(){
        Optional<User> expectedUser = optionalUsers.get(0);

        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        articleService.getAllApprovedArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetAllBetaArticlesByEmailId(){
        List<Article> userArticles = articles;

        Optional<User> expectedUser = optionalUsers.get(0);

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.BETA)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllBetaArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllBetaArticlesByEmailId_Invalid(){
        Optional<User> expectedUser = optionalUsers.get(0);

        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        articleService.getAllBetaArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetAllInitialArticlesByEmailId(){
        List<Article> userArticles = articles;

        Optional<User> expectedUser = optionalUsers.get(0);

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.INITIAL)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllInitialArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllInitialArticlesByEmailId_Invalid(){
        Optional<User> expectedUser = optionalUsers.get(0);

        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        articleService.getAllInitialArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetRejectedArticlesByEmailId(){
        List<Article> userArticles = articles;

        Optional<User> expectedUser = optionalUsers.get(0);

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.REJECTED)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllRejectedArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllRejectedArticlesByEmailId_Invalid(){
        Optional<User> expectedUser = optionalUsers.get(0);

        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        articleService.getAllRejectedArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetAllDiscardedArticlesByEmailId(){
        List<Article> userArticles = articles;

        Optional<User> expectedUser = optionalUsers.get(0);
        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.BETA)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllDiscardedArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllDiscardedArticlesByEmailId_Invalid(){
        Optional<User> expectedUser = optionalUsers.get(0);

        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        articleService.getAllDiscardedArticlesByEmailId("John@gmail.com");
    }

    /**
     * @Test
     * @Name testCreateArticleByEmail
     * @Desciption Test create article by Email to be valid
     *
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
        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        doNothing().when(etherPadService).createPad(anyString());

        when(articleRepository.findAllArticlesByEmailId(anyString()))
                .thenReturn(expectedUser.get().getArticles());

        // actual call to createArticleByEmail with "John@gmail.com","testArticle" params
        Article actualArticle = articleService.createArticleByEmail("John@gmail.com");

        // compare expectedArticle.getEmailId() and actualArticle.getEmailId()
        assertEquals(expectedArticle.getEmailId(), actualArticle.getEmailId());
        // compare expectedArticle.getStatus(), actualArticle.getStatus()
        assertEquals(expectedArticle.getStatus(), actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testCreateArticleByUser_UserNotFound
     * @Desciption Test create article by user to be invalid
     *
     */

    @Test(expected = UserNotFoundException.class)
    public void testCreateArticleByEmail_UserNotFound(){
        // when findUserByEmail() is called with any string param from userService
        // then throw User Not Found Exception
        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        // actual call to createArticleByEmail() with "John@gmail.com", "Invalid" param
        articleService.createArticleByEmail("John@gmail.com");
    }



}