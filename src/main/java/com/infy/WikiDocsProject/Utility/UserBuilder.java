package com.infy.WikiDocsProject.Utility;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.enums.Role;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * User Builder Class
 *
 */
public class UserBuilder {

	// Class attributes declared
    private ObjectId id;
    private String email;
    private String name;
    private List<Article> articles = new ArrayList<Article>();
    private Role role;

    /**
     * Constructor
     * @param id
     * @param email
     * @param name
     * @param articles
     * @param role
     */
    public UserBuilder(ObjectId id, String email, String name, List<Article> articles, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.articles = articles;
        this.role = role;
    }
    /**
     * Default constructor
     */
    public UserBuilder(){}

    /**
     * @name id 
     * @Desciption set local id attribute then return current instance of userBuild
     * @param id
     * @return userBuild object
     */
    public UserBuilder id(ObjectId id) {
    	// Set local attribute with param object
        this.id = id;
        // return current instance of userBuild
        return this;
    }
    /**
     * @name email
     * @Desciption set local email attribute then return current instance of userBuild
     * @param email
     * @return userBuild object
     */
    public UserBuilder email(String email) {
    	// Set local attribute with param object
        this.email = email;
     // return current instance of userBuild
        return this;
    }
    /**
     * @name name
     * @Desciption set local name attribute then return current instance of userBuild
     * @param name
     * @return userBuild object
     */
    public UserBuilder name(String name) {
    	// Set local attribute with param object
        this.name = name;
     // return current instance of userBuild
        return this;
    }
    /**
     * @name articles
     * @Desciption add all article to local article then return current instance of userBuild
     * @param list of article
     * @return userBuild object
     */
    public UserBuilder articles(List<Article> articles) {
    	// Set local attribute with param object
        this.articles.addAll(articles);
     // return current instance of userBuild
        return this;
    }
    /**
     * @name articles
     * @Desciption add article to local article then return current instance of userBuild
     * @param list of article
     * @return userBuild object
     */
    public UserBuilder articles(Article article){
    	// Set local attribute with param object
        this.articles.add(article);
     // return current instance of userBuild
        return this;
    }

    /**
     * @name role
     * @Desciption set local role attribute then return current instance of userBuild
     * @param role
     * @return userBuild object
     */
    public UserBuilder role(Role role) {
    	// Set local attribute with param object
        this.role = role;
     // return current instance of userBuild
        return this;
    }
    /**
     * @name build
     * @Desciption Build article with attributes in params
     * @return user object
     */
    public User build(){
        return new User(id, email, name, articles, role);
    }
}
