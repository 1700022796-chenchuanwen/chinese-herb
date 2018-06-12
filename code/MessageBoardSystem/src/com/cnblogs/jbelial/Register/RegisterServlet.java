package com.cnblogs.jbelial.Register;

import java.io.IOException; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.User;

import com.cnblogs.jbelial.DBServlet.DB;

public class RegisterServlet extends HttpServlet {

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
			throws ServletException, IOException 
	{
//		�����ַ�������
		request.setCharacterEncoding("UTF-8") ; 
		response.setContentType("UTF-8");  
//		�ж��Ƿ���������¼��
		if (request.getParameter("login") != null)
		{
			response.sendRedirect("login.jsp") ;
			return ; 
		}
		DB db = new DB() ; 
		String username  = null ;
//		����resultҳ��Ҫ��ת��ҳ��
		String page = "login.jsp" ;
		try
		{ 
//			��ȡ������û������������
		    username = request.getParameter("username") ; 
			String password = request.getParameter("password") ;
			String validationCode = request.getParameter("validationCode") ; 
//			�˶���֤��
			if (!db.checkValidationCode(request, validationCode))  
				return ;   
			User user = db.checkUser(username,password) ; 
			if (user != null) 
			{
				request.setAttribute("info", username + "�ѱ�ʹ�ã�") ;
				page = "register.jsp" ; 
			}
			else if (db.insertUser(username , password)) 
			{
//				����result.jsp��ʹ�õ���Ϣ
				request.setAttribute("info" , "�û�ע��ɹ�") ;
			} 
			request.setAttribute("page", page) ; 
		}
		catch(Exception e) { }
		finally
		{ 
//			��ת��result.jsp 
			RequestDispatcher rd = request.getRequestDispatcher("/result.jsp") ; 
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
