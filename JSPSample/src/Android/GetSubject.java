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
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class GetSubject1
 */
@WebServlet("/GetSubject1")
public class GetSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSubject() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private static String batch;
	private static String cls;
	private static String div;
	private static int day = 0;
	int subId = 0;
	int lecId=0;
	String subName = "", teacherName = "", bNamespace = "", bInstance="";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		
		String subject = null;
		
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String result="";
			String line="";
			while((line=br.readLine())!=null)
			{
				result+=line;
			}
			JSONObject jobj = new JSONObject(result);
			batch = jobj.getString("batch");
			cls = jobj.getString("cls");
			div = jobj.getString("div");
			
			jobj = new JSONObject();
//			System.out.println("Class : "+cls+"\nDivision : "+div+"\nBatch : "+batch);
			
			LocalTime time = LocalTime.of(10, 30, 45);
			
			Date now = new Date();
			
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(now);
	        day = calendar.get(Calendar.DAY_OF_WEEK)-1;
	        
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/time_table?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps;
			ResultSet rs;
			
			//Start function 1....................
			ps = con.prepareStatement("select id from time where ? between startTime and endTime;");
			ps.setString(1, String.valueOf(time));
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				subId = rs.getInt(1);
			}
			subId=subId+2;
			//End function 1........................
			
			//Start function 2....................
			ps = con.prepareStatement("select * from tt where day=?;");
//			ps.setInt(1, day);
			ps.setInt(1, 4);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
//				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9));
//				subject=calc(rs);
				lecId = rs.getInt(subId);
			}
			//End function 2....................
			
			//Start function 3....................
			if(isBetween(lecId,100,200))
			{
				ps = con.prepareStatement("select s.subject_name, t.name, b.namespace, b.instance from lecture l "
						+ "join subjects s on s.id = l.sub_id "
						+ "join teacher t on t.id = l.teacher_id "
						+ "join beacons b on b.id = l.class_id where l.id=?");
				ps.setInt(1, lecId);
				rs = ps.executeQuery();
				while(rs.next())
				{ 
					subName = rs.getString(1);
					teacherName = rs.getString(2);
					bNamespace = rs.getString(3);
					bInstance = rs.getString(4);
				}
			}
			//End function 3....................
			
			//Start function 4....................
			if(isBetween(lecId,1000,1100))
			{
				ps = con.prepareStatement("call returnSubjectFromBatch(?,?);");
				ps.setString(1, batch);
				ps.setInt(2, lecId);
				rs = ps.executeQuery();
				while(rs.next())
				{ 
					subName = rs.getString(1);
					teacherName = rs.getString(2);
					bNamespace = rs.getString(3);
					bInstance = rs.getString(4);
				}
			}
			//End function 4....................
			
			jobj = new JSONObject();
			jobj.put("subject",subName );
			jobj.put("teacher_name", teacherName);
			jobj.put("namespace", bNamespace);
			jobj.put("instance", bInstance);
			PrintWriter pw = response.getWriter();
			pw.write(jobj.toString());
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public boolean isBetween(int x, int lower, int upper)
	{
		return lower<=x && upper>=x;
	}
	
	/*public String calc(ResultSet rs)
	{
		int subId;
		String subject = null;
		try {
//			LocalTime time = LocalTime.now();
			LocalTime time = LocalTime.of(12, 30, 45);
//			t1=20;
//			t2=15;
	        
	        switch(t1)
	        {
	        	case 20:
	        		if(isBetween(t2,0,60))
	        		{
	        			if(isBetween(rs.getInt(6),0,15))
	        				subject=returnSubject(rs.getInt(6));
	        			else
	        				subject=returnSubjectFromBatch(rs.getInt(6),batch,day);
	        		}
	        		break;
	        }
			subId = getSubjectID(time);
			
			if(isBetween(rs.getInt(subId),0,15))
				subject = returnSubject(rs.getInt(subId));
			
			else if(isBetween(rs.getInt(subId),100,115))
				subject = returnSubjectFromBatch(rs.getInt(subId),batch);
			
			else subject=null;
			
			
	        return subject;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
    }
	
	public String returnSubject(int x)
	{
		String sub = "";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/time_table?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps;
			ResultSet rs;
			
			ps = con.prepareStatement("select subject_name from subjects where id=?");
			ps.setInt(1, x);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				sub = rs.getString(1);
			}
			
			return sub;
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public String returnSubjectFromBatch(int x, String batch)
	{
		String query = "select";
		String sub = "";
		int subid=0;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/time_table?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps;
			ResultSet rs;
			
			switch(batch)
			{
				case "A1":
					query+=" s1 from batches where id="+x;
					break;
					
				case "A2":
					query+=" s2 from batches where id="+x;
					break;
					
				case "A3":
					query+=" s3 from batches where id="+x;
					break;
			}
			
			ps = con.prepareStatement(query+";");
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				subid = rs.getInt(1);
			}
			
			ps = con.prepareStatement("select subject_name from subjects where id=?");
			ps.setInt(1, subid);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				sub = rs.getString(1);
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		return sub;
	}
	
	public int getSubjectID(LocalTime time)
	{
		int id = 0;
		try {
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/time_table?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps;
			ResultSet rs;
			
			ps = con.prepareStatement("select id from time where ? between startTime and endTime;");
			ps.setString(1, String.valueOf(time));
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				id = rs.getInt(1);
			}
			
			return id+2;
			
		}catch(Exception e) {e.printStackTrace();}
		return 0;
	}*/

}