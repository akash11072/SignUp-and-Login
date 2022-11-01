package com.akash.index;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		
		String uname = request.getParameter("txt");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pswd");
		
//		PrintWriter out = response.getWriter();
//		out.print(uname);
		RequestDispatcher dis=null;
		Connection con = null;
		
		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://INBOOK_X1_SLIM;databaseName =schoolData;integratedSecurity=true;encrypt=false");
			
			PreparedStatement pt = con.prepareStatement("insert into userInfo(uname,uemail,upwd) values(?,?,?)");
			pt.setString(1,uname);
			pt.setString(2,uemail);
			pt.setString(3,upwd);
			
			
			
			
			int rCount = pt.executeUpdate();
			dis = request.getRequestDispatcher("signup.jsp");
			if(rCount>0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			
			dis.forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
