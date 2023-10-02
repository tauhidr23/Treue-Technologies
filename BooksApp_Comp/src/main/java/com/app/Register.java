package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter pw;
	ServletContext sc;
       
   
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try{
				sc=getServletContext();
				pw=response.getWriter();
				String name=request.getParameter("username");
				String dob =request.getParameter("dob");
				String mobile=request.getParameter("mobile");
				String email = request.getParameter("email");
				String pword=request.getParameter("password");
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
				
				PreparedStatement ps=con.prepareStatement("insert into bookuser values(?,?,?,?,?)");
				
				ps.setString(1,name);
				ps.setString(2,dob);
				ps.setString(3,mobile);
				ps.setString(4,email);
				ps.setString(5,pword);
				ps.execute();
				
				response.sendRedirect("account_created.html");
				
				ps.close();
				con.close();
			}catch(SQLIntegrityConstraintViolationException ex){
				pw.println("<html><body ><h3 text=red><center>EMAID-ID ALREADY EXIST </center><h3><br><h2><center>USE ANOTHER EMAIL-ID</center></h2></body></html>");
				RequestDispatcher rd=sc.getRequestDispatcher("/register.html");
				rd.include(request, response);    				
			}catch(Exception e){
				System.err.println(e);
				e.printStackTrace();
			}
		}

}