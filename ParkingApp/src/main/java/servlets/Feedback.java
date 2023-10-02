package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/feedback")
public class Feedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Feedback() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter pw=response.getWriter();

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
			
			String username=request.getParameter("name");
			String email=request.getParameter("email");
			String subject=request.getParameter("subject");
			String message=request.getParameter("message");
			String query="insert into parkingfeedback values(?,?,?,?)";
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3,subject);
			ps.setString(4, message);
			ps.executeUpdate();
			
			pw.print("<html><head> <style> div{margin:200px 150px; background-color:#677eff; border:4px solid grey; border-radius:8px; height:150px;width:80vw; text-align:center;}"
					+ "body{background-image: url(images/1.jpeg);background-size:cover;background-repeat: no-repeat;}</style></head><body><div>");
			pw.print("<h1>THANKS! YOUR RESPONSE HAS BEEN SAVED SUCCESSFULLY..</h1>");
			pw.print("<p>Our team will look into it shortly..</p>");
			pw.print("<a href='Homepage.html'>HOMEPAGE</a>");
			pw.print("</div></body></html>");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
