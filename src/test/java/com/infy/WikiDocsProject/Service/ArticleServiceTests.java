package com.infy.WikiDocsProject.Service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.infy.WikiDocsProject.Exception.*;
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

    // Inject mock services
    @InjectMocks
    ArticleServiceImpl articleService;

    // Set Expected Exception rule
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * @Test
     * @Name testGetAllArticlesByEmail
     * @Desciption Test get all articles of given user to be valid
     * @throws Exception
     */
    @Test
    public void testGetAllArticlesByEmail() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());
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
     * @throws Exception
     */
    @Test(expected = UserNotFoundException.class)
    public void testGetAllArticlesByEmailId_UserNotFound() throws Exception{
        // when findUserByEmailId() is called with any string param from userService
        // then throw UserNotFoundException()
        when(userService.findUserByEmail(anyString()))
                .thenThrow(new UserNotFoundException());
        // Actual call to getAllArticlesByEmailId() from articleService class with given email
        // should throw expected exception
        articleService.getAllArticlesByEmailId("john@gmail.com");
    }

    /**
     * @Test
     * @Name testGetApprovedArticles
     * @Desciption Test get all approved articles to be valid
     * @throws Exception
     */
    @Test
    public void testGetApprovedArticles(){
        // create new list of article named expectedArticles
        List<Article> expectedArticles = new ArrayList<>();
        // Set expectedArticles status to APPROVED
        expectedArticles.add(new ArticleBuilder()
                .status(Status.APPROVED)
                .build());

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
     * @throws Exception
     */
    @Test
    public void testGetBetaArticles(){
        // create new list of article named expectedArticles
        List<Article> expectedArticles = new ArrayList<>();
        // Set expectedArticles status to BETA
        expectedArticles.add(new ArticleBuilder()
                .status(Status.BETA)
                .build());

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
     * @throws Exception
     */
    @Test
    public void testGetArticleByChannelId() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call to getArticleByChannelId() from articleService class with given param "testArticle"
        // receive back actualArticle
        Article actualArticle = articleService.getArticleByChannelId("testArticle");
        // compare expectedArticle and actualArticle
        assertEquals(expectedArticle.get(), actualArticle);
    }

    /**
     * @Test
     * @Name testGetArticleByChannelId_ArticleNotFound
     * @Desciption Test get all articles by channelId to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testGetArticleByChannelId_ArticleNotFound() throws Exception{
        // set expectedArticle to be empty
        Optional<Article> expectedArticle = Optional.empty();

        // when findArticleByChannelId() is called from articleRepository class with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId((anyString())))
                .thenReturn(expectedArticle);
        // actual call getArticleByChannelId() with "Invalid" param from articleService class
        articleService.getArticleByChannelId("Invalid");
    }

    /**
     * @Test
     * @Name testSubmitArticle_InitialArticle
     * @Desciption Test submit and initial article to be valid
     * @throws Exception
     */
    @Test
    public void testSubmitArticle_InitialArticle() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to INITIAL
                .status(Status.INITIAL)
                .editable(true)
                .build());

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        //actual call to submitArticle() from articleService with "Invalid" param
        Article actualArticle = articleService.submitArticle("Invalid");

        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_BetaArticle
     * @Desciption Test submit and beta article to be valid
     * @throws Exception
     */
    @Test
    public void testSubmitArticle_BetaArticle() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to INITIAL
                .status(Status.INITIAL)
                .editable(true)
                .build());

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle("testArticle");
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testSubmitArticle_RejectedArticle
     * @Desciption Test submit and rejected article to be valid
     * @throws Exception
     */
    @Test
    public void testSubmitArticle_RejectedArticle() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to REJECTED
                .status(Status.REJECTED)
                .editable(true)
                .build());
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle("testArticle");
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testSubmitArticle_ArticleApproved
     * @Desciption Test submit and approved article to be valid
     * @throws Exception
     */
    @Test(expected = SubmittingArticleIsApprovedException.class)
    public void testSubmitArticle_ArticleApproved() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to APPROVED
                .status(Status.APPROVED)
                .editable(true)
                .build());
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle("Invalid");
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_ArticleDiscarded
     * @Desciption Test submit and discarded article to be valid
     * @throws Exception
     */
    @Test(expected = SubmittingArticleIsDiscardedException.class)
    public void testSubmitArticle_ArticleDiscarded() throws Exception{
        // Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to DISCARDED
                .status(Status.DISCARDED)
                .editable(true)
                .build());

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle("Invalid");
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_ArticleNotFound
     * @Desciption Test submit article to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testSubmitArticle_ArticleNotFound() throws Exception{
        // Create expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() with any string param from articleRepository
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual submitArticle() with "Invalid" param from articleService
        articleService.submitArticle("Invalid");
    }

    /**
     * @Test
     * @Name testApproveArticle_BetaArticle
     * @Desciption Test approved-beta article to be valid
     * @throws Exception
     */
    @Test
    public void testApproveArticle_BetaArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                // set attributes
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.BETA)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_RejectedArticle
     * @Desciption Test approved-reject article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsStillRejectedException.class)
    public void testApproveArticle_RejectedArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                // set attributes
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.REJECTED)
                .editable(true)
                .build());

        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @NametestApproveArticle_InitialArticle
     * @Desciption Test approved-initial article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsInitialException.class)
    public void testApproveArticle_InitialArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                // set attributes
                .id(new ObjectId()  )
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.INITIALand actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_ApprovedArticle
     * @Desciption Test approved-approved article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsApprovedException.class)
    public void testApproveArticle_ApprovedArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_DiscardedArticle
     * @Desciption Test approved-discarded article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsDiscardedException.class)
    public void testApproveArticle_DiscardedArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.DISCARDED)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testApproveArticle_ArticleNotFound
     * @Desciption Test approved article article not found to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testApproveArticle_ArticleNotFound() throws Exception{
        // create expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() is called with any string param from articleRepository class
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call to approveArticle() with "Invalid" param from articleService
        articleService.approveArticle("Invalid");
    }


    /**
     * @Test
     * @Name testRejectArticle_BetaArticle
     * @Desciption Test rejected article and beta article to be valid
     * @throws Exception
     */
    @Test
    public void testRejectArticle_BetaArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.BETA)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }


    /**
     * @Test
     * @Name testRejectArticle_BetaArticle
     * @Desciption Test rejected article and beta article becomes discarded to be valid
     * @throws Exception
     */
    @Test
    public void testRejectArticle_BetaArticleBecomesDiscarded() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                // set attributes
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(3)
                .status(Status.BETA)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testRejectArticle_InitialArticle
     * @Desciption Test rejected article and initial article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsInitialException.class)
    public void testRejectArticle_InitialArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status INITIAL and actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_ApprovedArticle
     * @Desciption Test rejected article and approved article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsApprovedException.class)
    public void testRejectArticle_ApprovedArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());

        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_RejectedArticle
     * @Desciption Test rejected article and rejected article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsStillRejectedException.class)
    public void testRejectArticle_RejectedArticle() throws Exception{
        // Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(2)
                .status(Status.REJECTED)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testRejectArticle_RejectedArticle
     * @Desciption Test rejected article and discarded article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsDiscardedException.class)
    public void testRejectArticle_DiscardedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .emailId("john@gmail.com")
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.DISCARDED)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status RDISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_ArticleNotFound
     * @Desciption Test rejected article article not found to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testRejectArticle_ArticleNotFound() throws Exception{
        //create  expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() is called with any string param from articleRepository
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() from articleService with "Invalid" param
        articleService.rejectArticle("Invalid");
    }

    @Test
    public void testGetAllApprovedArticlesByEmailId() throws Exception{
        Article dummyArticle1 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.APPROVED)
                .build();
        Article dummyArticle2 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.REJECTED)
                .build();
        List<Article> userArticles = new ArrayList<>();
        userArticles.add(dummyArticle1);
        userArticles.add(dummyArticle2);

        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(userArticles)
                .role(Role.USER)
                .build());

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.APPROVED)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllApprovedArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllApprovedArticlesByEmailId_Invalid() throws Exception{
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
                .build());

        when(userService.findUserByEmail(expectedUser.get().getEmail()))
                .thenThrow(new UserNotFoundException());

        articleService.getAllApprovedArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetAllBetaArticlesByEmailId() throws Exception{
        Article dummyArticle1 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.BETA)
                .build();
        Article dummyArticle2 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.REJECTED)
                .build();
        List<Article> userArticles = new ArrayList<>();
        userArticles.add(dummyArticle1);
        userArticles.add(dummyArticle2);

        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(userArticles)
                .role(Role.USER)
                .build());

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.BETA)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllBetaArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllBetaArticlesByEmailId_Invalid() throws Exception{
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
                .build());

        when(userService.findUserByEmail(expectedUser.get().getEmail()))
                .thenThrow(new UserNotFoundException());

        articleService.getAllBetaArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetAllInitialArticlesByEmailId() throws Exception{
        Article dummyArticle1 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.INITIAL)
                .build();
        Article dummyArticle2 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.REJECTED)
                .build();
        List<Article> userArticles = new ArrayList<>();
        userArticles.add(dummyArticle1);
        userArticles.add(dummyArticle2);

        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(userArticles)
                .role(Role.USER)
                .build());

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.INITIAL)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllInitialArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllInitialArticlesByEmailId_Invalid() throws Exception{
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
                .build());

        when(userService.findUserByEmail(expectedUser.get().getEmail()))
                .thenThrow(new UserNotFoundException());

        articleService.getAllInitialArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetRejectedArticlesByEmailId() throws Exception{
        Article dummyArticle1 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.BETA)
                .build();
        Article dummyArticle2 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.REJECTED)
                .build();
        List<Article> userArticles = new ArrayList<>();
        userArticles.add(dummyArticle1);
        userArticles.add(dummyArticle2);

        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(userArticles)
                .role(Role.USER)
                .build());

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.REJECTED)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllRejectedArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllRejectedArticlesByEmailId_Invalid() throws Exception{
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
                .build());

        when(userService.findUserByEmail(expectedUser.get().getEmail()))
                .thenThrow(new UserNotFoundException());

        articleService.getAllRejectedArticlesByEmailId("John@gmail.com");
    }

    @Test
    public void testGetAllDiscardedArticlesByEmailId() throws Exception{
        Article dummyArticle1 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.DISCARDED)
                .build();
        Article dummyArticle2 = new ArticleBuilder()
                .id(new ObjectId())
                .status(Status.REJECTED)
                .build();
        List<Article> userArticles = new ArrayList<>();
        userArticles.add(dummyArticle1);
        userArticles.add(dummyArticle2);

        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(userArticles)
                .role(Role.USER)
                .build());

        List<Article> expectedArticles = userArticles.stream()
                .filter(article -> article.getStatus() == Status.BETA)
                .collect(Collectors.toList());

        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        List<Article> actualArticles =
                articleService.getAllDiscardedArticlesByEmailId("John@gmail.com");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllDiscardedArticlesByEmailId_Invalid() throws Exception{
        Optional<User> expectedUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .role(Role.USER)
                .build());

        when(userService.findUserByEmail(expectedUser.get().getEmail()))
                .thenThrow(new UserNotFoundException());

        articleService.getAllDiscardedArticlesByEmailId("John@gmail.com");
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
                .email("John@gmail.com")
                .articles(new ArrayList<>())
                .build());

        // Generate expectedArticle using ArticleBuilder()
        Article expectedArticle = new ArticleBuilder()
                .id(new ObjectId())
                .emailId(expectedUser.get().getEmail())
                .channelId("testArticle")
                .status(Status.INITIAL)
                .build();

        // when findUserByEmail() is called with any string param
        // then return expectedUser
        when(userService.findUserByEmail(anyString()))
                .thenReturn(expectedUser.get());

        when(articleRepository.findAllArticlesByEmailId(anyString()))
                .thenReturn(expectedUser.get().getArticles());

        // actual call to createArticleByEmail with "John@gmail.com","testArticle" params
        Article actualArticle = articleService.createArticleByEmail("John@gmail.com","testArticle");

        // compare expectedArticle.getEmailId() and actualArticle.getEmailId()
        assertEquals(expectedArticle.getEmailId(), actualArticle.getEmailId());
        assertEquals(expectedArticle.getChannelId(), actualArticle.getChannelId());
        // compare expectedArticle.getStatus(), actualArticle.getStatus()
        assertEquals(expectedArticle.getStatus(), actualArticle.getStatus());
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
        articleService.createArticleByEmail("John@gmail.com", "invalid");
    }



}
