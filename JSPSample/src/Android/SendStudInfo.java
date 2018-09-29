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


import org.json.JSONObject;

import java.sql.*;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class SendStudInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendStudInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		try {
		InputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line="";
		String result="";
		while((line=bufferedReader.readLine())!=null)
		{
			result+=line;
		}
//		System.out.println("JSON1 : "+result);
		
		JSONObject jobj = new JSONObject(result);
//		System.out.println("JSON2 : "+pobj.toString());
			
		String uid="",pass="";
		
		uid = (String) jobj.get("user_id");
		pass = (String) jobj.get("password");
		
		JSONObject obj = new JSONObject();
		PrintWriter pw = response.getWriter();
		
		//try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps = con.prepareStatement("select f_name,l_name,address,phone_no,classn,divsn from studtable where uid=? and password=?;");
			ps.setString(1, uid);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				obj.put("result", "success");
//				obj.put("uid", rs.getString(1));
				obj.put("fname", rs.getString(1));
				obj.put("lname", rs.getString(2));
				obj.put("addr", rs.getString(3));
				obj.put("phno", rs.getString(4));
				obj.put("cls", rs.getString(5));
				obj.put("div", rs.getString(6));
				pw.println(obj);
			}
			
			else
			{
				obj.put("result", "fail");
//				obj.put("uid", "");
				pw.println(obj);
			}
			
			con.close();
			ps.close();
			rs.close();
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}