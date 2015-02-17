package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.User;
import db.ShoppingAppDataSource;
import db.UserDAO;
import exceptions.ShoppingDbFailure;

public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String userId = request.getParameter("user_id");
		String password = request.getParameter("passcode");

		DataSource dataSource = ShoppingAppDataSource.setupDataSource();

		Connection dbConn = null;
		try {
			dbConn = dataSource.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			User userFromDb = UserDAO.authenticateUserWithConn(userId,
					password, dbConn);

			if (userFromDb != null) {
				
				HttpSession session = request.getSession();
				session.setAttribute("userid", userId);
				
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/homepage.jsp");
				out.println("<div> Welcome " + userId + "! </div>");
				requestDispatcher.include(request, response);

			}
		}

		catch (ShoppingDbFailure se) {
			if (se.getFailureReason() == ShoppingDbFailure.INVALID_USER) {
				RequestDispatcher requestDispatcher = getServletContext()
						.getRequestDispatcher("/userlogin.html");

				requestDispatcher.include(request, response);
				out.println("<p align=center><font color=red> Please "
						+ "enter a valid username and password.</font></p>");
			}

			else {
				out.println("System Error, Contact System Admin");
			}

		} catch (SQLException e) {
			out.println("System Error, Contact Admin");
		} finally{
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					out.println("System Error, Contact Admin");
				}
			}
			out.close();
		}


	}
}