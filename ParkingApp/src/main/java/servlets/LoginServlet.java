package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    PrintWriter out;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String email=request.getParameter("email");
			String pass=request.getParameter("password");
			ServletContext sc=getServletContext();
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treueTech", "root" ,"admin");
			
			PreparedStatement ps=con.prepareStatement("select * from parkingusers where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet rs=ps.executeQuery();
			
			out = response.getWriter();
			if(rs.next()) {
				UserDetails ud=new UserDetails();
				
				ud.setEmail(rs.getString(1));
				ud.setPassword(rs.getString(2));
				sc.setAttribute("user", ud);
				response.sendRedirect("Homepage.html");
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
