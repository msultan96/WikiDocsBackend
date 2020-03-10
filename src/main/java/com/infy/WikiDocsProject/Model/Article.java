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
	private String channelId;
	private String name;
	private Status status;
	private int rejectedCount;
	private boolean editable;

	/**
	 * All Args Constructor
	 */
	public Article(ObjectId id, String emailId, String channelId, String name, Status status, int rejectedCount, boolean editable) {
		this.id = id;
		this.emailId = emailId;
		this.channelId = channelId;
		this.name = name;
		this.status = status;
		this.rejectedCount = rejectedCount;
		this.editable = editable;
	}
	/**
	 * Default contructor
	 */

	public Article(){

	}

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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@Override
	public String toString() {
		return "Article{" +
				"id='" + id + '\'' +
				", emailId='" + emailId + '\'' +
				", channelId='" + channelId + '\'' +
				", name='" + name + '\'' +
				", status=" + status +
				", rejectedCount=" + rejectedCount +
				", editable=" + editable +
				'}';
	}
}
