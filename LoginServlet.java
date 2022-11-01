package com.akash.index;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pswd");
		HttpSession ses = request.getSession();
		RequestDispatcher dis =null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://INBOOK_X1_SLIM;databaseName =schoolData;integratedSecurity=true;encrypt=false");
			
			PreparedStatement pt =  con.prepareStatement("select * from userInfo where uemail = ? and upwd = ?");
			pt.setString(1, uemail);
			pt.setString(2, upwd);
			ResultSet rs = pt.executeQuery();
			
			if(rs.next()) {
				ses.setAttribute("name",rs.getString("uname"));
				dis = request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status","failed");
				dis = request.getRequestDispatcher("login.jsp");
				
			}
			dis.forward(request, response);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
