package Android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class RegisterAttendance
 */
@WebServlet("/RegisterAttendance")
public class RegisterAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterAttendance() {
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
		
		String cls,div,dbname,subCode;
		String date;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime time = LocalDateTime.now();
        date=dtf.format(time);
        
        
		
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String result="";
			String line="";
			while((line=bufferedReader.readLine())!=null)
			{
				result += line;
			}
			
			JSONObject jsonObject = new JSONObject(result);
			cls=jsonObject.getString("cls");
			div=jsonObject.getString("div");
			dbname=jsonObject.getString("dbname");
			subCode=jsonObject.getString("subCode");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname+"?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			ps = con.prepareStatement("");
			
		}catch(Exception e) {e.printStackTrace();}
	}

}
