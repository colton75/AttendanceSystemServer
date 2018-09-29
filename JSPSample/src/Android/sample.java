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

/**
 * Servlet implementation class sample
 */
@WebServlet("/sample")
public class sample extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sample() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");
		String uid,pass;
		
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String result="";
			String line="";
			while((line=bufferedReader.readLine())!=null)
			{
				result += line;
			}
			
			JSONObject jobj = new JSONObject(result);
			uid=jobj.getString("uid");
			pass=jobj.getString("pass");
			
			String fname="Rajat",lname="Dangat",addr="Pune",phno="1234567890",cls="TE",div="A";
			
			JSONObject respObj = new JSONObject();
			respObj.put("uid", uid);
			respObj.put("pass", pass);
			respObj.put("fname", fname);
			respObj.put("lname", lname);
			respObj.put("addr", addr);
			respObj.put("phno", phno);
			respObj.put("cls", cls);
			respObj.put("div", div);
			
			PrintWriter pw = response.getWriter();
			
			pw.print(respObj.toString());
			
		}catch(Exception e) {e.printStackTrace();}
	}

}
