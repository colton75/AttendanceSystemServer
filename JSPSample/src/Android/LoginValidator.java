package Android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import org.json.JSONObject;

/**
 * Servlet implementation class LoginValidator
 */
@WebServlet("/LoginValidator")
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("Application/json");
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String result="";
			String line= "";
			while((line=bufferedReader.readLine())!=null)
			{
				result += line;
			}
			
			JSONObject jobj = new JSONObject(result);
			String uid;
			String pass;
			String name="";
			uid=jobj.getString("uid");
			pass=jobj.getString("pass");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps = con.prepareStatement("select name from stable where uid=? and pass=?;");
			ps.setString(1, uid);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				name=rs.getString(1);
			}
			
			PrintWriter pw = response.getWriter();
			
			JSONObject j1obj = new JSONObject();
			j1obj.put("uid", uid);
			j1obj.put("pass", pass);
			j1obj.put("name", name);
			pw.println(j1obj.toString());
			
			
		}catch(Exception e) {e.printStackTrace();}
		
		
	}

}
