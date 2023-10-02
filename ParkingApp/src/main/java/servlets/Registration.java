package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/register")
public class Registration extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    PrintWriter pw;
    ServletContext sc;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			sc=getServletContext();
			pw=response.getWriter();
			String uname=request.getParameter("username");
			String dob =request.getParameter("dob");
			String gender = request.getParameter("gender");
			String licenceNo=request.getParameter("license");
			String mobileNo=request.getParameter("mobile");
			String email = request.getParameter("email");
			String pword=request.getParameter("password");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/treuetech", "root" ,"admin");
			
			PreparedStatement ps=con.prepareStatement("insert into parkingusers values(?,?,?,?,?,?,?)");
			
			ps.setString(1, uname);
			ps.setString(2, dob);
			ps.setString(3, gender);
			ps.setString(4, licenceNo);
			ps.setString(5, mobileNo);
			ps.setString(6, email);
			ps.setString(7, pword);
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


