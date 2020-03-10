package com.infy.WikiDocsProject.Utility;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
/**
 * 
 * Article Builder Class
 *
 */
public class ArticleBuilder {

	// Class attributes declared
    private ObjectId id;
    private ObjectId userId;
    private String channelId;
    private String name;
    private Status status;
    private int rejectedCount;
    private boolean editable;

    /**
     * Constructor
     * @param id
     * @param userId
     * @param channelId
     * @param name
     * @param status
     * @param rejectedCount
     * @param editable
     */
    public ArticleBuilder(ObjectId id, ObjectId userId, String channelId, String name, Status status, int rejectedCount, boolean editable) {
        this.id = id;
        this.userId = userId;
        this.channelId = channelId;
        this.name = name;
        this.status = status;
        this.rejectedCount = rejectedCount;
        this.editable = editable;
    }

    /**
     * Default constructor
     */
    public ArticleBuilder() {}

    /**
     * @name id
     * @Desciption set local id attribute then return current instance of articleBuild
     * @param id
     * @return articleBuilder object
     */
    public ArticleBuilder id(ObjectId id) {
    	// Set local attribute with param object
        this.id = id;
        // return current instance of articleBuild
        return this;
    }

    /**
     * @name userId
     * @Desciption set local userId attribute then return current instance of articleBuild
     * @param userId
     * @return articleBuilder object
     */
    public ArticleBuilder userId(ObjectId userId) {
    	// Set local attribute with param object
        this.userId = userId;
        // return current instance of articleBuild
        return this;
    }

    /**
     * @name channelId
     * @Desciption set local channelId attribute then return current instance of articleBuild
     * @param channelId
     * @return articleBuilder object
     */
    public ArticleBuilder channelId(String channelId) {
    	// Set local attribute with param object
        this.channelId = channelId;
        // return current instance of articleBuild
        return this;
    }

    /**
     * @name name
     * @Desciption set local name attribute then return current instance of articleBuild
     * @param name
     * @return articleBuilder object
     */
    public ArticleBuilder name(String name) {
    	// Set local attribute with param object
        this.name = name;
        // return current instance of articleBuild
        return this;
    }
    /**
     * @name status
     * @Desciption set local status attribute then return current instance of articleBuild
     * @param status
     * @return articleBuilder object
     */
    public ArticleBuilder status(Status status) {
    	// Set local attribute with param object
        this.status = status;
        // return current instance of articleBuild
        return this;
    }
    /**
     * @name rejectedCount 
     * @Desciption set local rejectedCount attribute then return current instance of articleBuild
     * @param rejectedCount
     * @return articleBuilder object
     */
    public ArticleBuilder rejectedCount(int rejectedCount) {
    	// Set local attribute with param object
        this.rejectedCount = rejectedCount;
        // return current instance of articleBuild
        return this;
    }
    /**
     * @name editable
     * @Desciption set local editable attribute then return current instance of articleBuild
     * @param editable
     * @return articleBuilder object
     */
    public ArticleBuilder editable(boolean editable) {
    	// Set local attribute with param object
        this.editable = editable;
        // return current instance of articleBuild
        return this;
    }
    /**
     * @name build
     * @Desciption Build article with attributes in params
     * @return article object
     */
    public Article build(){
        return new Article(id, userId, channelId, name, status, rejectedCount, editable);
    }
}
