package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkOut")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		PrintWriter out = response.getWriter();
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
				
		UserDetails ud = (UserDetails) sc.getAttribute("userdetails");
		String mobile = ud.getMobile();
		String vn = request.getParameter("vehicleNumber");
		
			String sql = "UPDATE `parkedvehicle` SET `checkouttime` = ? WHERE `vehiclenumber` = ? AND `mobile` = ?;";
			PreparedStatement ps = con.prepareStatement(sql);

			LocalTime now = LocalTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String checkout = now.format(formatter);
						
			ps.setString(1, checkout);
			ps.setString(2, vn);
			ps.setString(3, mobile);
				
			ps.executeUpdate();
			
			String sql2 = "SELECT TIMEDIFF(checkouttime, parkTime) AS totalTime FROM parkedvehicle where vehiclenumber=?";
			
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, vn);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                String totalTime = rs2.getString("totalTime");
                
                String[] timeP = totalTime.split(":");
				double money=(Integer.parseInt(timeP[0]) * 10) + (Integer.parseInt(timeP[1]) * 0.5);
				
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset=\"UTF-8\">");
				out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
				out.println("<title>Mirrors Parking System</title>");
				out.println("</head>");
				out.println(
						"<body style=\"display:flex; align-items:center; justify-content:center; height:100vh; width:100vw; background-image: url(images/1.jpeg);background-size:cover;background-repeat: no-repeat;\" >");
				out.println("<div style=\"padding-top: 30px; background-color:#677eff; border-radius:10px;width:80vw; text-align:center;\">");
				out.println("<h2>YOU CAN TAKE YOUR VEHICLE.</br> THANKS FOR USING MIRROR PARKING SERVICE.</br>VISIT AGAIN</h2>");
				out.println("<a style=\"text-decoration:none; color:#fff; font-size:18px\" href='Homepage.html'>CLICK HERE TO HOMEPAGE</a>");
				out.println("<p style=\"color:green;\"> Total Parked Time is "+totalTime+"</p>");
				out.println("<h4 style=\"color:black;\"> Total Amount is "+money+"$</h4>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");
			} else {
				out.println("<html><body ><h3 text=red><center>VEHICLE IS NOT PARKED.</center></h3>");
				out.println("</body></html>");
				RequestDispatcher rd = sc.getRequestDispatcher("/checkout.html");
				rd.include(request, response);
			}
		
			con.close();
			rs2.close();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
 			e.printStackTrace();
		}

	}

}
