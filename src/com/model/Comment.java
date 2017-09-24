package com.model;

import java.util.Date;

public class Comment {
	private int commentId;
	private int newsId=-1;
	private String content;
	private String userIP;
	private Date commentDate;
	private String newsTitle;
	
	public Comment(int newsId, String content, String userIP) {
		super();
		this.newsId = newsId;
		this.content = content;
		this.userIP = userIP;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	
	
	

}
