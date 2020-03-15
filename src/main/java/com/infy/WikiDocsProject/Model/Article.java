package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Article model class
 *
 */
@Document
@Getter @Setter @NoArgsConstructor
public class Article {

	@Id
	private ObjectId id;
	private String emailId;
	private String channelId;
	private String name;
	private String content;
	private Status status;
	private int rejectedCount;
	private boolean editable;

	/**
	 * All Args Constructor
	 */
	public Article(ObjectId id, String emailId, String channelId, String name, String content, Status status, int rejectedCount, boolean editable) {
		this.id = id;
		this.emailId = emailId;
		this.channelId = channelId;
		this.name = name;
		this.content = content;
		this.status = status;
		this.rejectedCount = rejectedCount;
		this.editable = editable;
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
