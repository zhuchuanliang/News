package com.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.News;
import com.model.PageBean;
import com.util.DateUtil;
import com.util.PropertiesUtil;
import com.util.StringUtil;

public class NewsDao {
	public List<News> newsList(Connection con,String sql) throws Exception{
		List<News> newsList=new ArrayList<News>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			News news=new News();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"),"yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;
	}
	
	public List<News> newsList(Connection con,News s_news,PageBean pageBean,String s_bPublishDate,String s_aPublishDate) throws Exception{
		List<News> newsList=new ArrayList<News>();
		StringBuffer sb=new StringBuffer("select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId");
		if(s_news.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_news.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_news.getTitle())){
			sb.append(" and t1.title like '%"+s_news.getTitle()+"%'");
		}
		if(StringUtil.isNotEmpty(s_bPublishDate)){
			sb.append(" and TO_DAYS(t1.publishDate)>=TO_DAYS('"+s_bPublishDate+"')");
		}
		if(StringUtil.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(t1.publishDate)<=TO_DAYS('"+s_aPublishDate+"')");
		}
		sb.append(" order by t1.publishDate desc");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			News news=new News();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"),"yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeName(rs.getString("typeName"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;	
	}
	
	public int newsCount(Connection con,News s_news,String s_bPublishDate,String s_aPublishDate) throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_news");
		if(s_news.getTypeId()!=-1){
			sb.append(" and typeId="+s_news.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_news.getTitle())){
			sb.append(" and title like '%"+s_news.getTitle()+"%'");
		}
		if(StringUtil.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(publishDate)>=TO_DAYS('"+s_bPublishDate+"')");
		}
		if(StringUtil.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(publishDate)<=TO_DAYS('"+s_aPublishDate+"')");
		}
		
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public News getNewsById(Connection con,String newsId) throws Exception{
		String sql="select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId and t1.newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		ResultSet rs=pstmt.executeQuery();
		News news=new News();
		if(rs.next()){
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeName(rs.getString("typeName"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
		}
		return news;
	}
	
	public int newsClick(Connection con,String newsId) throws Exception{
		String sql="update t_news set click=click+1 where newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		return pstmt.executeUpdate();	
	}
	
	public List<News> getUpAndDownPage(Connection con,String newsId) throws Exception{
		List<News> upAndDownPage=new ArrayList<>();
		String sql="select * from t_news where newsId<? order by newsId desc limit 1";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new News(rs.getInt("newsId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new News(-1,""));
		}
		sql="select * from t_news where newsId>? order by newsId asc limit 1";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new News(rs.getInt("newsId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new News(-1,""));
		}
		return upAndDownPage;
		
	}
	
	
	public boolean existNewsWithNewsTypeId(Connection con,String typeId) throws Exception{
		String sql="select * from t_news where typeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, typeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else
			return false;
	}
	
	public int newsAdd(Connection con,News news) throws Exception{
		String sql="insert into t_news values(null,?,?,now(),?,?,0,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, news.getTitle());
		pstmt.setString(2, news.getContent());
		pstmt.setString(3, news.getAuthor());
		pstmt.setInt(4, news.getTypeId());
		pstmt.setInt(5, news.getIsHead());
		pstmt.setInt(6, news.getIsImage());
		pstmt.setString(7, news.getImageName());
		pstmt.setInt(8, news.getIsHot());
		return pstmt.executeUpdate();
	}
	public int newDelete(Connection con,String newsId) throws Exception{
		String sql="delete from t_news where newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		return pstmt.executeUpdate();
	}

}
