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
	private ObjectId id;
	private String email;
	private String name;
	private String password;
	/**
	 * Reference to Article in database
	 */
	@DBRef(db = "article")
	private List<Article> articles;
	private Role role;

	/**
	 * All Args constructor
	 *
	 */
	public User(ObjectId id, String email, String password, String name, List<Article> articles, Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.articles = articles;
		this.role = role;
	}

	/**
	 * No Args constructor
	 */
	public User() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
