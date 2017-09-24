package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.LinkDao;
import com.dao.NewsDao;
import com.dao.NewsTypeDao;
import com.model.Link;
import com.model.News;
import com.model.NewsType;
import com.util.DbUtil;
import com.util.StringUtil;




public class IndexServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	NewsDao newsDao=new NewsDao();
	NewsTypeDao newsTypeDao=new NewsTypeDao();
	LinkDao linkDao=new LinkDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			//新闻类别
			List<NewsType> newsTypeList=newsTypeDao.newsTypeList(con);
			request.setAttribute("newsTypeList", newsTypeList);
			
			//新闻
			//图片新闻，按照日期降序排序（取五条）
			String sql="select * from t_news where isImage=1 order by publishDate desc limit 0,5";
			List<News> imageNewsList=newsDao.newsList(con, sql);
			request.setAttribute("imageNewsList", imageNewsList);
			
			//头条新闻，按照日期降序排序（取一条）
			sql="select * from t_news where isHead=1 order by publishDate desc limit 0,1";
			List<News> headNewsList=newsDao.newsList(con, sql);
			News headNews=headNewsList.get(0);
			headNews.setContent(StringUtil.Html2Text(headNews.getContent()));
			request.setAttribute("headNews", headNews);
			
			//最近更新新新闻，按照日期降序排序（取8条）
			sql="select * from t_news order by publishDate desc limit 0,8 ";
			List<News> newestNewsList=newsDao.newsList(con, sql);
			request.setAttribute("newestNewsList", newestNewsList);
			
			//热点新闻，按照日期降序排序（取8条）
			sql="select * from t_news where isHot=1 order by publishDate desc limit 0,8 ";
			List<News> hotSpotNewsList=newsDao.newsList(con, sql);
			request.setAttribute("hotSpotNewsList", hotSpotNewsList);
			
			List allIndexNewsList=new ArrayList();
			if(newsTypeList!=null&&newsTypeList.size()!=0){
				for(int i=0;i<newsTypeList.size();i++){
					NewsType newsType=newsTypeList.get(i);
					sql="select * from t_news,t_newsType where typeId=newsTypeId and typeId="+newsType.getNewsTypeId()+" order by publishDate desc limit 0,8";
					List<News> oneSubList=newsDao.newsList(con, sql);
					allIndexNewsList.add(oneSubList);
				}
			}
			request.setAttribute("allIndexNewsList", allIndexNewsList);
			
			//链接
			sql="select * from t_link order by orderNum ";
			List<Link> linkList=linkDao.linkList(con, sql);
			request.setAttribute("linkList", linkList);
			
			//转发，
			request.getRequestDispatcher("index.jsp").forward(request, response);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
