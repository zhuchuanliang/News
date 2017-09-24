package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.dao.CommentDao;
import com.model.Comment;
import com.model.PageBean;
import com.util.DbUtil;
import com.util.NavUtil;
import com.util.PageUtil;
import com.util.PropertiesUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

public class CommentServlet extends HttpServlet{
	DbUtil dbUtil=new DbUtil();
	CommentDao commentDao=new CommentDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("save".equals(action)){
			commentSave(request,response);
		}else if("backList".equals(action)){
			commentBackList(request,response);
		}else if("delete".equals(action)){
			commentDelete(request,response);
		}
		
	}

	
	private void commentSave(HttpServletRequest request,
			HttpServletResponse response) {
		String newsId=request.getParameter("newsId");
		String content=request.getParameter("content");
		String userIP=request.getRemoteAddr();
		Comment comment=new Comment(Integer.parseInt(newsId),content,userIP);
		Connection con=null;
		try{
			con=dbUtil.getCon();
			commentDao.commentAdd(con, comment);
			request.getRequestDispatcher(("news?action=show&newsId="+newsId)).forward(request, response);
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
	
	private void commentBackList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
		String s_bCommentDate=request.getParameter("s_bCommentDate");
		String s_aCommentDate=request.getParameter("s_aCommentDate");
	    String page=request.getParameter("page");
	    HttpSession session=request.getSession();
	    //因为开始page默认是空的
	    if(StringUtil.isEmpty(page)){
	    	page="1";
	    	session.setAttribute("s_bCommentDate", s_bCommentDate);
	    	session.setAttribute("s_aCommentDate", s_aCommentDate);
	    }else{
	    	s_bCommentDate=(String)session.getAttribute("s_bCommentDate");
	        s_aCommentDate=(String)session.getAttribute("s_aCommentDate");
	    }
	    Connection con=null;
		try{
			con=dbUtil.getCon();
			//获取总页数
			int total=commentDao.commentCount(con, new Comment(), s_bCommentDate, s_aCommentDate);
			String pageCode=PageUtil.getPagation(request.getContextPath()+"/comment?action=backList", total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			List<Comment> commentBackList=commentDao.commentList(con, new Comment(),pageBean,s_bCommentDate,s_aCommentDate);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("commentBackList", commentBackList);
			request.setAttribute("navCode", NavUtil.genNewsManageNavigation("新闻评论管理", "新闻评论维护"));
			request.setAttribute("mainPage", "/background/comment/commentList.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
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
	
	private void commentDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String commentIds=request.getParameter("commentIds");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=commentDao.commentDelete(con, commentIds);
			if(delNums>0){
				result.put("success", true);
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(result, response);
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
