package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.dao.NewsDao;
import com.dao.NewsTypeDao;
import com.model.News;
import com.model.NewsType;
import com.util.DbUtil;
import com.util.ResponseUtil;



/**
 * @author Administrator
 *这个Servlet里面有新闻类别，最新新闻，热门新闻这三种信息，因为别的页面也需要这些信息，所以封装在application中
 *这个Servlet要设置为服务器启动的时候就要加载
 */
public class InitServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	NewsDao newsDao=new NewsDao();
	NewsTypeDao newsTypeDao=new NewsTypeDao();

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext application=config.getServletContext();
		this.refreshSystem(application);
	}
	
	
	
	private void refreshSystem(ServletContext application){
		Connection con=null;
		try {
			con=dbUtil.getCon();
			//类别
			List<NewsType> newsTypeList=newsTypeDao.newsTypeList(con);
			application.setAttribute("newsTypeList", newsTypeList);
			
			//最新新闻
			String sql="select * from t_news order by publishDate desc limit 0,8 ";
			List<News> newestNewsList=newsDao.newsList(con, sql);
			application.setAttribute("newestNewsList", newestNewsList);
			
			//热门新闻（根据点击数来排序）
			sql="select * from t_news order by click desc limit 0,8";
			List<News> hotNewsList=newsDao.newsList(con, sql);
			application.setAttribute("hotNewsList", hotNewsList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		ServletContext application=session.getServletContext();
		this.refreshSystem(application);
		JSONObject result=new JSONObject();
		result.put("success", true);
		try {
			ResponseUtil.write(result, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
