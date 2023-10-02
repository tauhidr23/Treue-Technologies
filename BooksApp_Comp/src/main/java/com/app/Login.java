package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    PrintWriter out;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mobile=request.getParameter("mobile");
		String pass=request.getParameter("password");
		ServletContext sc=getServletContext();
		
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treueTech", "root" ,"admin");
		
		PreparedStatement ps=con.prepareStatement("select * from parkingusers where mobile=? and password=?");
		ps.setString(1, mobile);
		ps.setString(2, pass);
		ResultSet rs=ps.executeQuery();
		
		out = response.getWriter();
		if(rs.next()) {
			User ud=new User();
			
			ud.setEmailid(rs.getString(1));
			ud.setPword(rs.getString(2));
			sc.setAttribute("userdetails", ud);
			response.sendRedirect("home.html");
		}
		else {
			out.println("<html><body ><h3 color=#ff00ff><center>INVALID CREDENTIALS..</center></h3></body></html>");
			RequestDispatcher rd=sc.getRequestDispatcher("/login.html");
			rd.include(request, response);    
		}
		rs.close();
		ps.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	}
}