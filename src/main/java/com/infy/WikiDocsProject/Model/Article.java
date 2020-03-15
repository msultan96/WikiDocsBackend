package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Article model class
 *
 */
@Document
public class Article {

	@Id
	private ObjectId id;
	private String emailId;
	private String name;
	private String content;
	private Status status;
	private int rejectedCount;
	private boolean readOnly;

	/**
	 * All Args Constructor
	 */
	public Article(ObjectId id, String emailId, String name, String content, Status status, int rejectedCount, boolean readOnly) {
		this.id = id;
		this.emailId = emailId;
		this.name = name;
		this.content = content;
		this.status = status;
		this.rejectedCount = rejectedCount;
		this.readOnly = readOnly;
	}

	/**
	 * No Args Constructor
	 */
	public Article(){}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public int getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(int rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public String toString() {
		return "Article{" +
				"id='" + id + '\'' +
				", emailId='" + emailId + '\'' +
				", name='" + name + '\'' +
				", status=" + status +
				", rejectedCount=" + rejectedCount +
				", readOnly=" + readOnly +
				'}';
	}
}
