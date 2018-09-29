package Android;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateTables
 */
@WebServlet("/CreateTables")
public class CreateTables extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTables() {
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
		
		int day;
		
		String date,chkDate="";
		
		ArrayList<Integer> lec;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime time = LocalDateTime.now();
        date=dtf.format(time);
        
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        day=calendar.get(Calendar.DAY_OF_WEEK)-1;
        
        try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TEAattendance?allowPublicKeyRetrieval=true&useSSL=false","root","rajat1998");
			
			ps = con.prepareStatement("select * from chkKey;");
			rs = ps.executeQuery();
//			System.out.println("Hello1 : "+String.valueOf(rs));
			
			/*if(rs==null)
			{
				ps = con.prepareStatement("insert into checkKey values (?);");
				System.out.println("Hello11");
				ps.setString(1, date);
				System.out.println("Hello12");
				ps.executeUpdate();
				System.out.println("Hello2");
			}*/
			
			
//				System.out.println("Hello21");
			{
				if(rs.next())
				{
					chkDate = rs.getString(1);
//					System.out.println("Hello22");
					
				}
				else
				{
					response.getWriter().append("No Entry for chkKey!");
					ps = con.prepareStatement("insert into chkKey values (?);");
					ps.setString(1, date);
					ps.executeUpdate();
					
				}
			}
//				System.out.println("Hello23");
				if(!date.equals(chkDate))
				{
					response.getWriter().append(date);
					response.getWriter().append("New Day!").append(chkDate);
//					System.out.println("create table `"+date+"` (student_id int unique, s1 varchar(1) default 'A', s2 varchar(1) default 'A',s3 varchar(1) default 'A',s4 varchar(1) default 'A',s5 varchar(1) default 'A',s6 varchar(1) default 'A',s7 varchar(1) default 'A');");
					ps = con.prepareStatement("create table `"+date+"` (student_id int unique, s1 varchar(1) default 'A', s2 varchar(1) default 'A',s3 varchar(1) default 'A',s4 varchar(1) default 'A',s5 varchar(1) default 'A',s6 varchar(1) default 'A',s7 varchar(1) default 'A');");
					ps.executeUpdate();
//					System.out.println("Hello3");
					
					ps = con.prepareStatement("insert into `"+date+"`(student_id) select uid from `studentdb`.`studtable`;");
					ps.executeUpdate();
					
//					ps = con.prepareStatement("create table "+date+"()");
					
					ps = con.prepareStatement("truncate table chkKey;");
					ps.executeUpdate();
					ps = con.prepareStatement("insert into chkKey values (?);");
					ps.setString(1, date);
					ps.executeUpdate();
//					System.out.println("Hello4");
					
					response.getWriter().append("Query Complete!");
				}
				
				else {
					//Display Notification on webpage.
					System.out.println("Table Already created for today!");
				}
			
			
			
        }catch(Exception e) {e.printStackTrace();}
	}

}
