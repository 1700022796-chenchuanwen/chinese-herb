package com.cnblogs.jbelial.Login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.User;

import com.cnblogs.jbelial.DBServlet.DB;

public class LoginServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
//		�����ַ�������
		request.setCharacterEncoding("UTF-8") ; 
		response.setContentType("UTF-8");  
//		�ж��Ƿ�������ע�᡿
		if (request.getParameter("register") != null)
		{
			response.sendRedirect("register.jsp") ;
			return ; 
		}
		DB db = new DB() ;
//		������ת����
		String page = "login.jsp" ;  
		String username = null ;
		try
		{
//			��ȡ����ҳ��Ĳ���
			username = request.getParameter("username") ; 
			String password = request.getParameter("password") ; 
			String validationCode = request.getParameter("validationCode") ; 
//			��֤����
			if (!db.checkValidationCode(request, validationCode))
					return ; 
			User user = db.checkUser(username,password) ; 
			if (user == null)
				request.setAttribute("userError", "�û������������") ; 
			if (user != null)
			{
//				������ݼ�飬user��Ϊ�գ���ʾ�û�����ȷ��������ȷ��������һ��������
				ArrayList arrayList = new ArrayList() ; 
				arrayList = db.findLyInfo() ; 
				request.setAttribute("arrayList", arrayList) ;
//				������ת��������
				page = "main.jsp" ;
				request.getSession().setAttribute("user", user) ; 
			}
	
			
		}
		catch(Exception e){}
		finally
		{
			request.setAttribute("username", username) ; 
			RequestDispatcher rd = request.getRequestDispatcher("/"+page) ; 
			rd.forward(request, response) ; 
		}
	}
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response) ; 
	}

}
