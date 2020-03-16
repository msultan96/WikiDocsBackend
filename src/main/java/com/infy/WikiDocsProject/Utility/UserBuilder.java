//package com.infy.WikiDocsProject.Utility;
//
//import com.infy.WikiDocsProject.Model.Article;
//import com.infy.WikiDocsProject.Model.User;
//import com.infy.WikiDocsProject.enums.Role;
//import org.bson.types.ObjectId;
//import java.util.ArrayList;
//import java.util.List;
///**
// *
// * User Builder Class
// * Desc: Utility to create articles with ease
// * using the Builder pattern.
// *
// */
//public class UserBuilder {
//
//    private ObjectId id;
//    private String email;
//    private String password;
//    private String name;
//    private List<Article> articles = new ArrayList<Article>();
//    private Role role;
//
//    public UserBuilder(ObjectId id, String email, String password, String name, List<Article> articles, Role role) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.articles = articles;
//        this.role = role;
//    }
//
//    public UserBuilder(){}
//
//    public UserBuilder id(ObjectId id) {
//        this.id = id;
//        return this;
//    }
//
//    public UserBuilder email(String email) {
//        this.email = email;
//        return this;
//    }
//
//    public UserBuilder password(String password) {
//        this.password = password;
//        return this;
//    }
//
//    public UserBuilder name(String name) {
//        this.name = name;
//        return this;
//    }
//    /**
//     * @name articles
//     * @Desciption add all articles to local article then return current instance of userBuild
//     * @param list of articles
//     * @return userBuild object
//     */
//    public UserBuilder articles(List<Article> articles) {
//    	// Set local attribute with param object
//        this.articles.addAll(articles);
//        // return current instance of userBuild
//        return this;
//    }
//    /**
//     * @name articles
//     * @Desciption add article to local article list then return current instance of userBuild
//     * @param single article
//     * @return userBuild object
//     */
//    public UserBuilder articles(Article article){
//    	// Set local attribute with param object
//        this.articles.add(article);
//        // return current instance of userBuild
//        return this;
//    }
//
//    public UserBuilder role(Role role) {
//        this.role = role;
//        return this;
//    }
//
//    /**
//     * @name build
//     * @Desciption Build user with attributes in params
//     * @return user object
//     */
//    public User build(){
//        return new User(id, email, password, name, articles, role);
//    }
//}
