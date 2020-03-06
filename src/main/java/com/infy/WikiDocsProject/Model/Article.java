package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Article {

	@Id
	private String id;
	private String channelId;
	private String name;
	private Status status;
	private int rejectedCount;
	private boolean editable;

	public Article(String id, String channelId, String name, Status status, int rejectedCount, boolean editable) {
		this.id = id;
		this.channelId = channelId;
		this.name = name;
		this.status = status;
		this.rejectedCount = rejectedCount;
		this.editable = editable;
	}

	public Article(){

	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
