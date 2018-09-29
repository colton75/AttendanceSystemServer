package Android;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class sendStudentInfo
 */
@WebServlet("/sendStudentInfo")
public class sendStudentInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendStudentInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String fname="",lname="",addr="",phno="",cls="",div="";
		
		response.setContentType("application/json");
		
		try {
			
			request.getInputStream();
			
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String result="";
			String line="";
			while((line=bufferedReader.readLine())!=null)
			{
				result += line;
			}
			
			JSONObject jsonObj = new JSONObject(result);
			String uid = jsonObj.getString("user_id");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps = con.prepareStatement("select f_name,l_name,address,phone_no,classn,divsn from studtable where uid=?");
			ps.setString(1, uid);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				fname = rs.getString(1);
				lname = rs.getString(2);
				addr = rs.getString(3);
				phno = rs.getString(4);
				cls = rs.getString(5);
				div = rs.getString(6);
			}
			
			PrintWriter pw = response.getWriter();
//			OutputStream outputStream = response.getOutputStream();
//			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
			JSONObject respObj = new JSONObject();
			respObj.put("fname", fname);
			respObj.put("lname", lname);
			respObj.put("addr", addr);
			respObj.put("phno", phno);
			respObj.put("cls", cls);
			respObj.put("div", div);
			
//			bufferedWriter.write(respObj.toString());
			pw.write(respObj.toString());
//			System.out.println(respObj.toString());
			con.close();
			ps.close();
			rs.close();
			
			
		}catch(Exception e) {e.printStackTrace();}
	}

}
