package Server;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginValidator
 */
@WebServlet(name = "ServerLoginValidator", urlPatterns = { "/ServerLoginValidator" })
public class LoginValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginValidator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		
		String uid,pass,type,name="";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/time_table?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			
			uid=request.getParameter("uid");
			pass=request.getParameter("password");
			type=request.getParameter("type");
			
			if(type.equals("admin"))
			{
				ps = con.prepareStatement("select fname,lname from admin where uid=? and password=?");
				ps.setString(1, uid);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				if(rs.next())
				{
					name=rs.getString(1)+" "+rs.getString(2);
				}
			}
			
			else
			{
				ps = con.prepareStatement("select name from teacher where uid=? and password=?");
				ps.setString(1, uid);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				if(rs.next())
				{
					name=rs.getString(1);
				}
			}
			
			if(!name.equals(""))
			{
				HttpSession session = request.getSession(true);
				session.setAttribute("SessionUser", name);
				response.sendRedirect("userLogged.jsp");
//				response.getWriter().append("Logged In Successfully!").append(name);
			}
			
			else
			{
				response.getWriter().append("Wrong User ID or Password!");
			}
			
			response.getWriter().append("Served at: ").append(request.getContextPath());
		} catch (Exception e) {e.printStackTrace();}
	}

}
