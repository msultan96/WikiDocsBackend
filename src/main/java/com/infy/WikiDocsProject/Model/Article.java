package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Article {

	@Id
	private String id;
	private User author;
	private List<User> currentCollaborators;
	private String name;
	private String content;
	private Status status;
	private Status[] edits;
	private boolean editable;

	public Article(String id, User author, List<User> currentCollaborators, String name, String content, Status status, Status[] edits, boolean editable) {
		this.id = id;
		this.author = author;
		this.currentCollaborators = currentCollaborators;
		this.name = name;
		this.content = content;
		this.status = status;
		this.edits = edits;
		this.editable = editable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getCurrentCollaborators() {
		return currentCollaborators;
	}

	public void setCurrentCollaborators(List<User> currentCollaborators) {
		this.currentCollaborators = currentCollaborators;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status[] getEdits() {
		return edits;
	}

	public void setEdits(Status[] edits) {
		this.edits = edits;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
