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
	// Declare article's id as ID or primary key
	private ObjectId id;
	// User's id associate with this article 
	private ObjectId userId;
	// Channel id of article
	private String channelId;
	// Name of article
	private String name;
	// Article's status
	private Status status;
	// Article's rejected count
	private int rejectedCount;
	// Editable status
	private boolean editable;

	/**
	 * Contructor 
	 * @param id
	 * @param userId
	 * @param channelId
	 * @param name
	 * @param status
	 * @param rejectedCount
	 * @param editable
	 */
	public Article(ObjectId id, ObjectId userId, String channelId, String name, Status status, int rejectedCount, boolean editable) {
		// Initialize all local attributes with params
		this.id = id;
		this.userId = userId;
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
	/**
	 * Id getter
	 * @return
	 */
	public ObjectId getId() {
		return id;
	}
	/**
	 * Id setter
	 * @param id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	/**
	 * User Id getter
	 * @return
	 */
	public ObjectId getUserId() {
		return userId;
	}
	/**
	 * User Id setter
	 * @param userId
	 */
	public void setUserId(ObjectId userId) {
		this.userId = userId;
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
	 * Status getter
	 * @return
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * Status setter
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * Rejected count getter
	 * @return
	 */
	public int getRejectedCount() {
		return rejectedCount;
	}
	/**
	 * Rejected count setter
	 * @param rejectedCount
	 */
	public void setRejectedCount(int rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	/**
	 * Editable status getter
	 * @return
	 */
	public boolean isEditable() {
		return editable;
	}
	/**
	 * Editable status setter
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	/**
	 * Channel Id getter
	 * @return
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * Channel Id setter
	 * @param channelId
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * Method name toString
	 * Add all attribute into the string
	 * return a string
	 */
	@Override
	public String toString() {
		return "Article{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", channelId='" + channelId + '\'' +
				", name='" + name + '\'' +
				", status=" + status +
				", rejectedCount=" + rejectedCount +
				", editable=" + editable +
				'}';
	}
}
