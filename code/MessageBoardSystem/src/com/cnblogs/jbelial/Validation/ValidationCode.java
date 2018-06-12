package com.cnblogs.jbelial.Validation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.* ; 
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class ValidationCode extends HttpServlet
{
//	ͼ����֤����ַ����ϣ�ϵͳͨ���������Щ�ַ�����ѡ��һЩ�ַ���Ϊ��֤��
	private static String codeChars = 
		"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTYVWXYZ" ; 
//	����һ�������ɫ
	private static Color getRandomColor(int minColor , int maxColor)
	{
		Random random = new Random() ;
		if (minColor > 255)
			minColor = 255 ; 
		if (maxColor > 255)
			maxColor = 255 ; 
//		��ȡ��ɫ�����ֵ
		int red = minColor + random.nextInt(maxColor - minColor) ; 
		int green = minColor + random.nextInt(maxColor - minColor) ; 
		int blue = minColor + random.nextInt(maxColor - minColor) ; 
		return new Color(red,green,blue) ;  
	}
	public void doGet (HttpServletRequest request ,
			HttpServletResponse response) throws IOException
	{
		
		
//   	��ȡ��֤�뼯�ϵĳ��ȡ�
		int charsLength = codeChars.length() ; 
		
		response.setHeader("ragma", "No-cache") ; 
		response.setHeader("Cache-Control", "no-cache") ;
		response.setDateHeader("Expires", 0) ; 
		response.setContentType("image/jpeg");
		
//		����ͼ����֤��ĳ��Ϳ��
		int width = 90 ;
		int height = 20 ;
//		����ͼ�λ�����
		BufferedImage image = new BufferedImage(width , height,
				BufferedImage.TYPE_INT_RGB) ; 
//		��ȡ����������ֵ�Graphics����
		Graphics graphics = image.getGraphics() ; 
		
		Random random = new Random() ; 
		
//		����Ҫ������ɫ
		graphics.setColor(getRandomColor(180 , 250)) ; 
//		���ͼ�α���
		graphics.fillRect(0, 0, width, height) ; 
//		���ó�ʼ�������ɫ
		graphics.setFont(new Font("Time New Roman" , Font.ITALIC, height)) ; 
		graphics.setColor(getRandomColor(120,180)) ; 
		
//		������֤��
		StringBuilder validationCode = new StringBuilder() ; 
//		��֤����������
		String[] fontNames = {"Times New Roman" , "Book antiqua" , "Arial" } ;
//		���������֤��
		for (int i = 0 ; i < 4 ; ++ i)
		{
//			���õ�ǰ��֤���ַ�������
			graphics.setFont(new Font(fontNames[random.nextInt(3)] , Font.ITALIC ,
					height)) ; 
//			��������֤����ַ�
			char codeChar = codeChars.charAt(random.nextInt(charsLength)) ;
			validationCode.append(codeChar) ; 
			graphics.setColor(getRandomColor(20,120)) ; 
//			��ͼ���������֤���ַ�
			graphics.drawString(String.valueOf(codeChar), 16*i+random.nextInt(7), 
					height - random.nextInt(6) ) ; 
		} 
//		���Session���󣬲�����Session����Ϊ3����
		HttpSession session = request.getSession(); 
		session.setMaxInactiveInterval(5*60) ; 
//		����֤�����session������.
		session.setAttribute("validationCode",validationCode.toString() ) ; 
		
		
//		�ر�graphics����
		graphics.dispose() ;  
//		��ͻ��˷���ͼ����֤��
		ImageIO.write(image,"JPEG" ,response.getOutputStream()) ; 
		
	
		
	}
	public void doPost (HttpServletRequest request ,
			HttpServletResponse response) throws IOException
	{
			doGet(request , response) ; 
	}
	
}
