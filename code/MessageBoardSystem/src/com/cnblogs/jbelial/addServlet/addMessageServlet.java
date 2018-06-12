package com.cnblogs.jbelial.addServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.LeaveMessageTable;
import Common.User;

import com.cnblogs.jbelial.DBServlet.DB;

public class addMessageServlet extends HttpServlet {

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
		doPost(request,response) ; 
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
		// �����������
		request.setCharacterEncoding("UTF-8");
		// ������Ӧ����
		response.setContentType("UTF-8");
		// ��ȡtitle����
		String title = request.getParameter("title");
		// ��ȡcontent����
		String content = request.getParameter("content");
		// ��session��ȡ����ǰ�û�����
		User user=(User) request.getSession().getAttribute("user");
		// �������Ա��ӦJavaBean���󣬰����ݷ�װ��ȥ
		LeaveMessageTable ly = new LeaveMessageTable();
		ly.setUserId(user.getId());
		// ����Ϊ��ȡ�ĵ�ǰʱ��
		ly.setDate(new Date(System.currentTimeMillis()));
		ly.setTitle(title);
		ly.setContent(content);
		// ��DB���еķ����ж��Ƿ����ɹ�
		if(new DB().addInfo(ly)){
			response.sendRedirect("main.jsp");
		}
	}

}
