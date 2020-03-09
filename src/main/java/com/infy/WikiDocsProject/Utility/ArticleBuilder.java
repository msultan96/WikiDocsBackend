package com.infy.WikiDocsProject.Utility;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;

public class ArticleBuilder {

    private ObjectId id;
    private ObjectId userId;
    private String channelId;
    private String name;
    private Status status;
    private int rejectedCount;
    private boolean editable;

    public ArticleBuilder(ObjectId id, ObjectId userId, String channelId, String name, Status status, int rejectedCount, boolean editable) {
        this.id = id;
        this.userId = userId;
        this.channelId = channelId;
        this.name = name;
        this.status = status;
        this.rejectedCount = rejectedCount;
        this.editable = editable;
    }

    public ArticleBuilder() {}

    public ArticleBuilder id(ObjectId id) {
        this.id = id;
        return this;
    }

    public ArticleBuilder userId(ObjectId userId) {
        this.userId = userId;
        return this;
    }

    public ArticleBuilder channelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public ArticleBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ArticleBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public ArticleBuilder rejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
        return this;
    }

    public ArticleBuilder editable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public Article build(){
        return new Article(id, userId, channelId, name, status, rejectedCount, editable);
    }
}
