package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/park")
public class Park extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Park() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			ServletContext sc=getServletContext();
			UserDetails ud=(UserDetails) sc.getAttribute("userdetails");
			String fullname=ud.getUname();
			String mobile=ud.getMobile();
			String vehiclenumber=request.getParameter("vNumber");
			String vehicletype=request.getParameter("vType");
			
			LocalTime now = LocalTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String parkTime= now.format(formatter);
			
			LocalDate currentDate = LocalDate.now();
			String parkdate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
								
				PreparedStatement ps=con.prepareStatement("insert into parkedvehicle values(?,?,?,?,?,?,?)");
				ps.setString(1, fullname);
				ps.setString(2, mobile);
				ps.setString(3, vehiclenumber);
				ps.setString(4, vehicletype);
				ps.setString(5, parkTime);
				ps.setString(6, parkdate);
				ps.setString(7, null);
				ps.execute();
				
			 String Message = "Your vehicle parked successfully. Thanks for using our parking system.";
			 response.setContentType("text/html");
			 PrintWriter out = response.getWriter();
			 
			 	out.println("<!DOCTYPE html>");
		        out.println("<html>");
		        out.println("<head>");
		        out.println("<meta charset=\"UTF-8\">");
		        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		        out.println("<title>Parking Success</title>");
		        out.println("</head>");
		        out.println("<body>");
		        out.println("<div style=\"margin: 3rem auto; background-color: #b7b7b7; border: 2px solid black; border-radius: 10px;"
		        		+ " height: max-content; width: 90vw; padding: 20px 32px; text-align: center;\">");
		        out.println("<h2>" + Message + "</h2>");
		        out.println("<a href='Homepage.html'>BACK TO HOMEPAGE</a>");
		        out.println("</div>");
		        out.println("</body>");
		        out.println("</html>");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}

}
