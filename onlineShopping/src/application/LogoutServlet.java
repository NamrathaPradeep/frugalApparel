package application;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet  extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        request.getRequestDispatcher("/userlogin.html").include(request, response);  
          
        HttpSession session=request.getSession();  
		String userid = (String)session.getAttribute("userid");
        
        session.invalidate();  
        
		out.println("<p align=center><font color=red> " + userid 
				+ ", You are successfully logged out! </font></p>");
          
        out.close();  		
		
	}

}
