package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.Comment;
import com.model.PageBean;
import com.util.DateUtil;
import com.util.StringUtil;

public class CommentDao {
	public List<Comment> commentList(Connection con,Comment s_comment,PageBean pageBean,String bCommentDate,String aCommentDate) throws Exception{
		List<Comment> commentList=new ArrayList<>();
		StringBuffer sb=new StringBuffer("select * from t_comment t1,t_news t2 where t1.newsId=t2.newsId");
		if(s_comment.getNewsId()!=-1){
			sb.append(" and t1.newsId="+s_comment.getNewsId());
		}
		if(StringUtil.isNotEmpty(bCommentDate)){
			sb.append(" and TO_DAYS(t1.commentDate)>=TO_DAYS('"+bCommentDate+"')");
		}
		if(StringUtil.isNotEmpty(aCommentDate)){
			sb.append(" and TO_DAYS(t1.commentDate)<=TO_DAYS('"+aCommentDate+"')");
		}
		
		sb.append(" order by t1.commentDate desc");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Comment comment=new Comment();
			comment.setCommentId(rs.getInt("commentId"));
			comment.setNewsId(rs.getInt("newsId"));
			comment.setNewsTitle(rs.getString("title"));
			comment.setUserIP(rs.getString("userIP"));
			comment.setContent(rs.getString("content"));
			comment.setCommentDate(DateUtil.formatString(rs.getString("commentDate"), "yyyy-MM-dd HH:mm:ss"));
			commentList.add(comment);
		}
		return commentList;
	}
	
	public int commentAdd(Connection con,Comment comment) throws Exception{
		String sql="insert into t_comment values(null,?,?,?,now())";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, comment.getNewsId());
		pstmt.setString(2,comment.getContent());
		pstmt.setString(3, comment.getUserIP());
		return pstmt.executeUpdate();
	}
	
	//pageBeanÒªÓÃµ½
	public int commentCount(Connection con,Comment s_comment,String bCommentDate,String aCommentDate) throws  Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_comment");
		if(s_comment.getNewsId()!=-1){
			sb.append(" and newsId="+s_comment.getNewsId());
		}
		if(StringUtil.isNotEmpty(bCommentDate)){
			sb.append(" and TO_DAYS(commentDate)>=TO_DAYS('"+bCommentDate+"')");
		}
		if(StringUtil.isNotEmpty(aCommentDate)){
			sb.append(" and TO_DAYS(commentDate)<=TO_DAYS('"+aCommentDate+"')");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else
			return 0;
	}
	
	public int commentDelete(Connection con,String commentIds) throws Exception{
		String sql="delete from t_comment where commentId in ("+commentIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

}
