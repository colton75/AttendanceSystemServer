package Android;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DayofWeek {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		        Date now = new Date();
		 
		        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
		        System.out.println(simpleDateformat.format(now));
		 
		        simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
		        System.out.println(simpleDateformat.format(now));
		 
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(now);
		        System.out.println(calendar.get(Calendar.DAY_OF_WEEK)-1); // the day of the week in numerical format
		        
		        System.out.println(now.getTime());
//		        System.out.println(now.getDay());
		 
		        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		        LocalDateTime date = LocalDateTime.now();
		        System.out.println(dtf.format(date));

	}

}
