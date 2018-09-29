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
 * Servlet implementation class ReturnTimeTable
 */
@WebServlet("/ReturnTimeTable")
public class ReturnTimeTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnTimeTable() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    String cls,batch;
    int day,clsID;
//    String[] subjects = new String[10];
    ArrayList<String> subject = new ArrayList<String>();
//    String[] teachers = new String[10];
    ArrayList<String> teacher = new ArrayList<String>();
//    int [] subID = new int[10];
    ArrayList<Integer> subID = new ArrayList<Integer>();
    ArrayList<String> time = new ArrayList<String>();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String result="";
			String line="";
			while((line=br.readLine())!=null)
			{
				result+=line;
			}
			
			JSONObject jsonObject = new JSONObject(result);
			cls=jsonObject.getString("cls");
//			div=jsonObject.getString("div");
			batch=jsonObject.getString("batch");
			day=jsonObject.getInt("day");
			
			subject.clear();
			teacher.clear();
			time.clear();
			subID.clear();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/time_table?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			PreparedStatement ps;
			ResultSet rs;
			
			ps=con.prepareStatement("Select time_format(startTime, '%h:%i') startTime,"
					+ "time_format(endTime, '%h:%i') endTime from time");
			rs = ps.executeQuery();
			while(rs.next())
			{
				time.add(rs.getString(1)+"-"+rs.getString(2));
			}
			
			ps=con.prepareStatement("Select id from year_div where class=?");
			ps.setString(1, cls);
			rs = ps.executeQuery();
			while(rs.next())
			{
				clsID = rs.getInt(1);
				
			}
//			System.out.println("Class ID : "+clsID);
			
			ps = con.prepareStatement("Select * from tt where day=? and class=?");
			ps.setInt(1, day);
			ps.setInt(2, clsID);
			rs=ps.executeQuery();
			
//			getSubjects(rs);
			
			int i = 3;
//			System.out.println("i : "+i);
			while(rs.next())
			{
//				subID[i]=rs.getInt(i);
//				System.out.println("Getting individual subject id");
				while(i<=9)
				{
//					subID[i-3]=rs.getInt(i);
					subID.add(rs.getInt(i));
//					System.out.println(rs.getInt(i));
					i++;
				}
			}
			
			int len=0;
			int val;
//			System.out.println("Len : "+len);
			while(len<subID.size())
			{
				val = subID.get(len);
				if(val>=100 && val<200 || val==0)
				{
					ps = con.prepareStatement(" select s.acronym,t.name from lecture l join teacher t on t.id=l.teacher_id join subjects s on s.id=l.sub_id where l.id=?;");
					ps.setInt(1, val);
					rs = ps.executeQuery();
					
					while(rs.next())
					{
//						subjects[len]=rs.getString(1);
						subject.add(rs.getString(1));
//						teachers[len]=rs.getString(2);
						teacher.add(rs.getString(2));
					}
				}
				else
				{
					ps = con.prepareStatement("call returnSubjectAcro(?,?);");
					ps.setString(1, batch);
					ps.setInt(2, val);
					rs = ps.executeQuery();
					
					while(rs.next())
					{
//						subjects[len]=rs.getString(1);
						subject.add(rs.getString(1));
//						teachers[len]=rs.getString(2);
						teacher.add(rs.getString(2));
						
					}
				}
				len++;
			}
			
			
			jsonObject = new JSONObject();
			jsonObject.put("time", time);
			jsonObject.put("subject",subject);
			jsonObject.put("teachers",teacher);
			
			PrintWriter pw = response.getWriter();
			pw.write(jsonObject.toString());
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	/*public void getSubjects(ResultSet rs)
	{
		try{
			while(rs.next())
			{
				
			}
		}catch(Exception e) {e.printStackTrace();}
	}*/

}
