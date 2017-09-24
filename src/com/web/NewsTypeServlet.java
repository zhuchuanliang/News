package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.dao.NewsDao;
import com.dao.NewsTypeDao;
import com.model.NewsType;
import com.util.DbUtil;
import com.util.NavUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

public class NewsTypeServlet extends HttpServlet{

	/**
	 * 
	 */
	DbUtil dbUtil=new DbUtil();
	NewsTypeDao newsTypeDao=new NewsTypeDao();
	NewsDao newsDao=new NewsDao();
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resposne)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, resposne);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resposne)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("preSave".equals(action)){
			this.newsTypePreSave(request,resposne);
		}else if("save".equals(action)){
			this.newsTypeSave(request,resposne);
		}else if("backList".equals(action)){
			this.newsTypeBackList(request,resposne);
		}else if("delete".equals(action)){
			this.newsTypeDelete(request,resposne);
		}
	}


	private void newsTypePreSave(HttpServletRequest request,
			HttpServletResponse resposne) throws ServletException, IOException{
		String newsTypeId=request.getParameter("newsTypeId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(newsTypeId)){
				NewsType newsType=newsTypeDao.getNewsTypeById(con, newsTypeId);
				request.setAttribute("newsType", newsType);
			}
			if(StringUtil.isNotEmpty(newsTypeId)){
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("新闻类别管理", "新闻类别修改"));
			}else{
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("新闻类别管理", "新闻类别添加"));
			}
			request.setAttribute("mainPage", "/background/newsType/newsTypeSave.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, resposne);
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
	
	
	private void newsTypeSave(HttpServletRequest request,
			HttpServletResponse resposne) throws ServletException, IOException{
		// TODO Auto-generated method stub
		Connection con=null;
		String newsTypeId=request.getParameter("newsTypeId");
		String typeName=request.getParameter("typeName");
		NewsType newsType=new NewsType(typeName);
		if(StringUtil.isNotEmpty(newsTypeId)){
			newsType.setNewsTypeId(Integer.parseInt(newsTypeId));
	    }
		try{ 
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(newsTypeId)){
				newsTypeDao.newsTypeUpdate(con, newsType);
			}else{
				newsTypeDao.newsTypeAdd(con, newsType);
			}
			request.getRequestDispatcher("/newsType?action=backList").forward(request, resposne);
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
	private void newsTypeBackList(HttpServletRequest request,
			HttpServletResponse resposne) throws ServletException, IOException{
		Connection con=null;
		try{
			con=dbUtil.getCon();
			List<NewsType> newsTypeBackList=newsTypeDao.newsTypeList(con);
			request.setAttribute("newsTypeBackList", newsTypeBackList);
			request.setAttribute("navCode",  NavUtil.genNewsManageNavigation("新闻类别管理", "新闻类别维护"));
			request.setAttribute("mainPage", "/background/newsType/newsTypeList.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, resposne);
			
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
	
	private void newsTypeDelete(HttpServletRequest request,
			HttpServletResponse resposne) throws ServletException, IOException{
		String newsTypeId=request.getParameter("newsTypeId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			boolean exist=newsDao.existNewsWithNewsTypeId(con,newsTypeId);
			if(exist){
				result.put("errorMsg", "该新闻类别下有新闻，不能删除此新闻类别");
			}else{
				int delNums=newsTypeDao.newsTypeDelete(con, newsTypeId);
				if(delNums>0){
					result.put("success", true);
				}else{
					result.put("errorMsg", "删除失败");
				}
			}
			
			ResponseUtil.write(result, resposne);
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
