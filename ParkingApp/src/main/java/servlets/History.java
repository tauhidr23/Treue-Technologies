package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/history")
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public History() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter pw=response.getWriter();
			ServletContext sc= getServletContext();

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
			
			UserDetails ud =(UserDetails) sc.getAttribute("userdetails");
			String fullname=ud.getUname();
			String query="select * from parkedvehicle where fullname=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, fullname);
			ResultSet rs=ps.executeQuery();
			    
			pw.print("<html><head><title>Mirrors Parking System</title><style>"
					+ ".container{display:flex; align-items:center;justify-content:center;margin-inline:auto;}"
					+ "  div{padding:10px 30px; background-color:#677eff;text-align:center;border-radius:5px;width:70vw;}"
					+ "body{background-image: url(images/3.jpg); background-size:cover; background-repeat: no-repeat; }"
					+ "h1{color:black;} td { text-align: center;}</style></head><body>"
					+"");
			pw.print("<center><h1 color=#fff>PARKING HISTORY</h1></center>");
			pw.print("<div class=\"container\">");
			pw.print("<div><table border=2px>");
			pw.print("<tr><th>DATE</th><th>VEHICLE NO</th><th>VEHICLE TYPE</th><th>CHECK IN TIME</th><th>CHECK OUT TIME</th></tr>");
			while(rs.next()) {
				String vnum=rs.getString(3);
				String pdate=rs.getString(6);
				String vtype=rs.getString(4);
				String pt=rs.getString(5);
				String ct=rs.getString(7);
				
				pw.print("<tr><td>"+ pdate +"</td><td>"+ vnum +"</td><td>"+vtype+"</td><td>"+pt+"</td><td>"+ ct+"</td></tr>");
			}
			pw.print("</table>");
			pw.print("<center><a href='Homepage.html'>BACK TO HOMEPAGE</a></center></div>");
			pw.print("</body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
