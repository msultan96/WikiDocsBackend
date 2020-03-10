package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Role;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
public class User {

	@Id
	// Declare ObjectId as the primary key
	private ObjectId id;
	// User's email
	private String email;
	// User's name
	private String name;
	
	@DBRef(db = "article")
	//Reference to Article in database
	private List<Article> articles;
	// User's role
	private Role role;

	/**
	 * Constructor
	 * @param id
	 * @param email
	 * @param name
	 * @param articles
	 * @param role
	 */
	public User(ObjectId id, String email, String name, List<Article> articles, Role role) {
		// Initialize all local attributes with params
		this.id = id;
		this.email = email;
		this.name = name;
		this.articles = articles;
		this.role = role;
	}
	/**
	 * Default constructor
	 */
	public User() { }
	
	/**
	 * Object Id getter
	 * @return
	 */
	public ObjectId getId() {
		return id;
	}
	/**
	 * Object Id setter
	 * @param id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	/**
	 * Email getter
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Email setter
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Name getter
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Articles getter
	 * @return
	 */
	public List<Article> getArticles() {
		return articles;
	}
	/**
	 * Article setter
	 * @param articles
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	/**
	 * Role getter
	 * @return
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * Role setter
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Method name toString
	 * Add all attribute into the string
	 * return a string
	 */
	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", email='" + email + '\'' +
				", name='" + name + '\'' +
				", articles=" + articles +
				", role=" + role +
				'}';
	}
}
