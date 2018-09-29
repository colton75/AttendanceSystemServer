package Android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ReturnAttendance
 */
@WebServlet("/ReturnAttendance")
public class ReturnAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnAttendance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("Application/json");
		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		ResultSet rs2;
		ArrayList<String> subject = new ArrayList<String>();
		ArrayList<String> status = new ArrayList<String>();
		
		String date;
		int uid;
		
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String result="",line="";
			while((line=br.readLine())!=null)
			{
				result+=line;
			}
			
			JSONObject jsonobject = new JSONObject(result);
			date = jsonobject.getString("date");
			uid = jsonobject.getInt("uid");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+"TEAattendance"+"?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			
			ps = con.prepareStatement("select s.acronym from lecture l join `time_table`.`subjects` s on s.id = l.s_id where date=?;");
			ps.setString(1, date);
			rs = ps.executeQuery();
			int i=1;
			while(rs.next())
			{
				subject.add(rs.getString(1));
				ps = con.prepareStatement("call returnSubjectWithStatus(?,?,?);");
				ps.setInt(1, uid);
				ps.setString(2,"s"+i);
				ps.setString(3, date);
				rs2=ps.executeQuery();
				if(rs2.next())
				{
					status.add(rs2.getString(1));
				}
				i++;
			}
			
			jsonobject = new JSONObject();
			jsonobject.put("subject", subject);
			jsonobject.put("status", status);
			
			PrintWriter pw = response.getWriter();
			pw.write(String.valueOf(jsonobject));
			
			
		}catch(Exception e) {e.printStackTrace();}
	}

}
