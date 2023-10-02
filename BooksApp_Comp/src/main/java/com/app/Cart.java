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


@WebServlet("/cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter pw;
	ServletContext sc;
       
   
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			sc=getServletContext();
			pw=response.getWriter();

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
			
			PreparedStatement ps=con.prepareStatement("insert into bookcart values(?,?,?,?)");
			
			User ud=(User) sc.getAttribute("userdetails");
			if(ud!=null) {
			String mobile=ud.getMobile();
			ps.setString(1,mobile);
			ps.setString(2,request.getParameter("title"));
			ps.setString(3,request.getParameter("author"));
			ps.setString(4,request.getParameter("price"));
			ps.execute();
			
			
			ps.close();
			con.close();
			
			PreparedStatement ps2=con.prepareStatement("select bookname,authorname,bookprice from bookcart where mobile=?");
			ps2.setString(1, mobile);
			ResultSet rs=ps2.executeQuery();
			
			pw.println("<html><head> <link rel=stylesheet href=cart.css> <head><body>");
			pw.println("<h1>WELCOME TO THE CART "+ud.getName()+"</h1>");
			pw.println("<div><table id='data-table'><tr><th>SR NO</th><th class='long'>BOOK NAME</th><th class='long'>AUTHOR NAME</th><th>PRICE</th><th>ACTION</th>");
			int count=1;
				while(rs.next()) {
					pw.println("<tr><td>"+ count +"</td><td class='long'>"+rs.getString(1)+"</td><td class='long'>"+rs.getString(2) +"</td><td>"+rs.getString(3)+"</td><br>"+" <a href='checkout.html'>CHECKOUT</a></td>");
					count++;
				}
				pw.println("</div></table></body><html>");
			}
			else {
				pw.println("<html><body><p>PLEASE LOGIN TO ADD BOOK TO THE CART<p></body><html>");
				RequestDispatcher rd=request.getRequestDispatcher("/login.html");
				rd.include(request, response);
			}
		}catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
