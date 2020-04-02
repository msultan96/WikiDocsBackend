package com.infy.WikiDocsProject.Service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.infy.WikiDocsProject.Exception.*;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.Role;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.RoleRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;

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
    CustomUserDetailsService customUserDetailsService;

    @Mock
    ArticleRepository articleRepository;

    @Mock
    UserRepository  userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    EPLiteClient epLiteClient;

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    @Spy
    ArticleServiceImpl articleService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Test Data
     */
    private List<User> users;
    private List<User> admins;
    private List<Optional<User>> optionalUsers;
    private Map<Status, Article> articles;
    private List<Optional<Article>> optionalArticles;
    private Page<Article> page;
    private Map<Status, Page<Article>> pages;
    private ObjectId objectId;
    private String stringId;
    private Map<String, Role> roles;

    /**
     * Populates test data.
     * Should change data structure to Map.
     * Special note for articles, optionalArticles, and pages:
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
        users = TestDataCreator.createUsersWithRole("user");
        admins = TestDataCreator.createUsersWithRole("admin");

        articles = TestDataCreator.createArticles("john@gmail.com");
        pages = TestDataCreator.createPages();

        optionalUsers = TestDataCreator.createOptionalUsers();
        optionalArticles = TestDataCreator.createOptionalArticles();

        page = pages.get(Status.APPROVED);

        objectId = TestDataCreator.createObjectId();
        stringId = objectId.toHexString();

        roles = TestDataCreator.createRoles();
    }

    /**
     * Test validity of getAllArticlesByEmail
     */
    @Test
    public void testGetAllArticlesByEmail(){
        User expectedUser = users.get(0);
        Page<Article> expectedPage = page;
        List<Article> expectedArticles = expectedPage.getContent();

        when(customUserDetailsService.findByEmail(anyString()))
                .thenReturn(expectedUser);
        when(articleRepository.findAllArticlesByEmailId(anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);
        List<Article> actualArticles = articleService.getAllArticlesByEmailId("john@gmail.com",0,5);
        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * Test to make sure UserNotFoundException
     * gets thrown
     */
    @Test(expected = UserNotFoundException.class)
    public void testGetAllArticlesByEmailId_UserNotFound(){

        when(customUserDetailsService.findByEmail(anyString()))
                .thenThrow(UserNotFoundException.class);

        articleService.getAllArticlesByEmailId("john@gmail.com",0,0);
    }

    /**
     * Test validity of getAllArticlesByStatus
     */
    @Test
    public void testGetAllArticlesByStatus_APPROVED(){
        Page<Article> expectedPage = pages.get(Status.APPROVED);
        List<Article> expectedArticles = expectedPage.getContent();

        when(articleRepository.findArticlesByStatus(any(Status.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        List<Article> actualArticles = articleService.getAllArticlesByStatus(Status.APPROVED, 0, 5);

        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * Test validity of getBetaArticles
     */
    @Test
    public void testGetBetaArticles_BETA(){
        Page<Article> expectedPage = pages.get(Status.BETA);
        List<Article> expectedArticles = expectedPage.getContent();

        when(articleRepository.findArticlesByStatus(any(Status.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        List<Article> actualArticles = articleService.getAllArticlesByStatus(Status.APPROVED, 0, 5);

        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * Test validity of finding articles by their ID
     */
    @Test
    public void testFindById(){
        Optional<Article> expectedArticle = optionalArticles.get(0);

        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.findById(new ObjectId());

        assertEquals(expectedArticle.get(), actualArticle);
    }

    /**
     * Test validity of not finding articles with given ID
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testFindById_ArticleNotFound(){
        Optional<Article> expectedArticle = Optional.empty();

        when(articleRepository.findById(any(ObjectId.class)))
                .thenReturn(expectedArticle);

        articleService.findById(new ObjectId());
    }

    /**
     * Test submitArticle with an initial article given
     */
    @Test
    public void testSubmitArticle_InitialArticle(){
        Article expectedArticle = articles.get(Status.INITIAL);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        when(roleRepository.findByRole(anyString()))
                .thenReturn(roles.get("ADMIN"));

        when(userRepository.findAll())
                .thenReturn(admins);

        Article actualArticle = articleService.submitArticle(stringId);

        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * Test submit article with reject article given
     */
    @Test
    public void testSubmitArticle_RejectedArticle(){
        Article expectedArticle = articles.get(Status.REJECTED);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        when(roleRepository.findByRole(anyString()))
                .thenReturn(roles.get("ADMIN"));

        when(userRepository.findAll())
                .thenReturn(admins);

        Article actualArticle = articleService.submitArticle(stringId);

        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * Test submit article with approved article given
     * throws SubmittingArticleIsApprovedException
     */
    @Test(expected = SubmittingArticleIsApprovedException.class)
    public void testSubmitArticle_ArticleApproved(){
        Article expectedArticle = articles.get(Status.APPROVED);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        articleService.submitArticle(stringId);
    }

    /**
     * Test submit article with beta article given
     * throws SubmittingArticleIsBetaException
     */
    @Test(expected = SubmittingArticleIsBetaException.class)
    public void testSubmitArticle_BetaArticle(){
        Article expectedArticle = articles.get(Status.BETA);

        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        articleService.submitArticle(stringId);
    }

    /**
     * Test submit article with discarded article given
     * throws SubmittingArticleIsDiscardedException
     */
    @Test(expected = SubmittingArticleIsDiscardedException.class)
    public void testSubmitArticle_ArticleDiscarded(){
        Article expectedArticle = articles.get(Status.DISCARDED);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        articleService.submitArticle(stringId);
    }

    /**
     * Test submit article will
     * throw ArticleNotFoundException
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testSubmitArticle_ArticleNotFound(){
        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(anyString());

        articleService.submitArticle(stringId);
    }

    /**
     * Test approve article with beta article given
     */
    @Test
    public void testApproveArticle_BetaArticle(){
        Article expectedArticle = articles.get(Status.BETA);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        Article actualArticle = articleService.approveArticle(stringId);

        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * Test approve article with rejected article given
     * throws ApprovingArticleIsStillRejectedException
     */
    @Test(expected = ApprovingArticleIsStillRejectedException.class)
    public void testApproveArticle_RejectedArticle(){
        Article expectedArticle = articles.get(Status.REJECTED);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        Article actualArticle = articleService.approveArticle(stringId);

        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * Test approve article with initial article given
     * throws ApprovingArticleIsInitialException
     */
    @Test(expected = ApprovingArticleIsInitialException.class)
    public void testApproveArticle_InitialArticle(){
        Article expectedArticle = articles.get(Status.INITIAL);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        Article actualArticle = articleService.approveArticle(stringId);

        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * Test approve article with approve article given
     * throws ApprovingArticleIsApprovedException
     */
    @Test(expected = ApprovingArticleIsApprovedException.class)
    public void testApproveArticle_ApprovedArticle(){
        Article expectedArticle = articles.get(Status.APPROVED);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        Article actualArticle = articleService.approveArticle(stringId);

        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * Test approve article with discarded article given
     * throws ApprovingArticleIsDiscardedException
     */
    @Test(expected = ApprovingArticleIsDiscardedException.class)
    public void testApproveArticle_DiscardedArticle(){

        Article expectedArticle = articles.get(Status.DISCARDED);


        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));


        Article actualArticle = articleService.approveArticle(stringId);

        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * Test approve article
     * will throw ArticleNotFoundException
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testApproveArticle_ArticleNotFound(){


        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(anyString());

        articleService.approveArticle(stringId);
    }

    /**
     * Test reject article with beta article given
     */
    @Test
    public void testRejectArticle_BetaArticle(){

        Article expectedArticle = articles.get(Status.BETA);


        doReturn(expectedArticle)
                .when(articleService).findById(anyString());


        Article actualArticle = articleService.rejectArticle(stringId);

        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }


    /**
     * Test reject article with beta article given
     * becomes discarded after more than 3 edits
     */
    @Test
    public void testRejectArticle_BetaArticleBecomesDiscarded(){

        Article expectedArticle = articles.get(Status.BETA);
        expectedArticle.setRejectedCount(3);


        doReturn(expectedArticle)
                .when(articleService).findById(anyString());


        Article actualArticle = articleService.rejectArticle(stringId);

        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * Test reject article with initial article given
     * throws RejectingArticleIsInitialException
     */
    @Test(expected = RejectingArticleIsInitialException.class)
    public void testRejectArticle_InitialArticle(){

        Article expectedArticle = articles.get(Status.INITIAL);


        doReturn(expectedArticle)
                .when(articleService).findById(anyString());


        Article actualArticle = articleService.rejectArticle(stringId);

        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * Test reject article with approved article given
     * throws RejectingArticleIsApprovedException
     */
    @Test(expected = RejectingArticleIsApprovedException.class)
    public void testRejectArticle_ApprovedArticle(){

        Article expectedArticle = articles.get(Status.APPROVED);



        doReturn(expectedArticle)
                .when(articleService).findById(anyString());


        Article actualArticle = articleService.rejectArticle(stringId);

        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * Test reject article with rejected article given
     * throws RejectingArticleIsStillRejectedException
     */
    @Test(expected = RejectingArticleIsStillRejectedException.class)
    public void testRejectArticle_RejectedArticle(){

        Article expectedArticle = articles.get(Status.REJECTED);


        doReturn(expectedArticle)
                .when(articleService).findById(anyString());


        Article actualArticle = articleService.rejectArticle(stringId);

        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * Test reject article with discarded article given
     * throws RejectingArticleIsDiscardedException
     */
    @Test(expected = RejectingArticleIsDiscardedException.class)
    public void testRejectArticle_DiscardedArticle(){
        Article expectedArticle = articles.get(Status.DISCARDED);


        doReturn(expectedArticle)
                .when(articleService).findById(anyString());


        Article actualArticle = articleService.rejectArticle(stringId);

        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * Test reject article
     * will throw ArticleNotFoundException
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testRejectArticle_ArticleNotFound(){


        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(anyString());

        articleService.rejectArticle(stringId);
    }

    /**
     * Test getAllArticlesByEmailIdAndStatus
     */
    @Test
    public void testGetAllArticlesByEmailIdAndStatus(){
        User expectedUser = users.get(0);
        Page<Article> expectedPage = page;
        List<Article> expectedArticles = expectedPage.getContent();

        doReturn(expectedUser)
                .when(customUserDetailsService).findByEmail(anyString());

        when(articleRepository.findAllArticlesByEmailIdAndStatus(anyString(), any(Status.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        List<Article> actualArticles =
                articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.APPROVED, 0, 5);

        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * Test getAllArticlesByEmailIdAndStatus
     * throws UserNotFoundException
     */
    @Test(expected = UserNotFoundException.class)
    public void testGetAllApprovedArticlesByEmailId_Invalid(){

        when(customUserDetailsService.findByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));

        articleService.getAllArticlesByEmailIdAndStatus("John@gmail.com", Status.APPROVED, 0, 5);
    }

    /**
     * Test createArticleByEmail
     */
    @Test
    public void testCreateArticleByEmail() {

        Optional<User> expectedUser = optionalUsers.get(0);

        Article expectedArticle = articles.get(Status.INITIAL);

        expectedArticle.setEmailId(expectedUser.get().getEmail());

        when(customUserDetailsService.findByEmail(anyString()))
                .thenReturn(expectedUser.get());

        when(articleRepository.findAllArticlesByEmailId(anyString()))
                .thenReturn(expectedUser.get().getArticles());

        doNothing().when(epLiteClient).createPad(anyString());


        Article actualArticle = articleService.createArticleByEmail("John@gmail.com", "Article Name");


        assertEquals(expectedArticle.getEmailId(), actualArticle.getEmailId());

        assertEquals(expectedArticle.getStatus(), actualArticle.getStatus());
    }

    /**
     * Test createArticleByEmail
     * throws UserNotFoundException
     */
    @Test(expected = UserNotFoundException.class)
    public void testCreateArticleByEmail_UserNotFound(){


        when(customUserDetailsService.findByEmail(anyString()))
                .thenThrow(new UserNotFoundException("UserService.USER_NOT_FOUND"));


        articleService.createArticleByEmail("John@gmail.com", "Article Name");
    }

    @Test
    public void testGetEtherPadUrl_editable(){
        Article expectedArticle = articles.get(Status.INITIAL);
        String expectedUrl = "http://localhost:9001/p/editableId?";

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        String actualUrl = articleService.getEtherPadUrl("editableId");

        assertEquals(expectedUrl, actualUrl);

    }

    @Test
    public void testGetEtherPadUrl_readOnly(){
        Article expectedArticle = articles.get(Status.APPROVED);
        String expectedUrl = "http://localhost:9001/p/readOnlyId?showControls=false";
        String appendingId = "readOnlyId";
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("readOnlyID", appendingId);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        when(epLiteClient.getReadOnlyID(anyString()))
                .thenReturn(expectedMap);

        String actualUrl = articleService.getEtherPadUrl(stringId);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testGetEtherPadUrl_ArticleNotFound(){

        doThrow(new ArticleNotFoundException("ArticleService.INVALID_ID"))
                .when(articleService).findById(anyString());

        articleService.getEtherPadUrl(stringId);
    }

    @Test
    public void testGetAllInvitedArticlesByEmail(){
        User expectedUser = users.get(0);
        Article expectedArticle = articles.get(Status.INITIAL);
        List<Article> expectedArticles = Arrays.asList(expectedArticle);

        users.get(0).setCollaboratingArticles(Arrays.asList(expectedArticle.getId()));

        when(customUserDetailsService.findByEmail(anyString()))
                .thenReturn(expectedUser);

        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        List<Article> actualArticles = articleService.getAllInvitedArticlesByEmail("john@gmail.com", 0, 5);

        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    public void testInviteUserToCollaborateByEmail(){
        Map<String, String> map = new HashMap<>();
        map.put("email", "john@gmail.com");
        map.put("articleId", stringId);

        User expectedUser = users.get(0);
        String expectedName = expectedUser.getName();
        Article expectedArticle = articles.get(Status.INITIAL);

        when(customUserDetailsService.findByEmail(anyString()))
                .thenReturn(expectedUser);

        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        String actualName = articleService.inviteUserToCollaborateByEmail(map);

        assertEquals(expectedName, actualName);
    }

    @Test(expected=UserAlreadyInvitedException.class)
    public void testInviteUserToCollaborateByEmail_UserAlreadyInvited(){
        Map<String, String> map = new HashMap<>();
        map.put("email", "john@gmail.com");
        map.put("articleId", stringId);

        User expectedUser = users.get(0);
        Article expectedArticle = articles.get(Status.INITIAL);
        expectedUser.setCollaboratingArticles(Arrays.asList(objectId));

        when(customUserDetailsService.findByEmail(anyString()))
                .thenReturn(expectedUser);

        doReturn(expectedArticle)
                .when(articleService).findById(any(ObjectId.class));

        articleService.inviteUserToCollaborateByEmail(map);
    }

    @Test
    public void saveArticle(){
        Article expectedArticle = articles.get(Status.INITIAL);
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("text", "Article Edited");

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        when(epLiteClient.getText(anyString()))
                .thenReturn(expectedMap);

        Article actualArticle = articleService.saveArticle(stringId);

        assertEquals(expectedArticle.getContent(), actualArticle.getContent());
    }

    @Test(expected = SavingArticleIsSubmittedException.class)
    public void saveArticle_Exception(){
        Article expectedArticle = articles.get(Status.APPROVED);

        doReturn(expectedArticle)
                .when(articleService).findById(anyString());

        articleService.saveArticle(stringId);
    }
}