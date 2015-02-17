package application;

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;

import db.ShoppingAppDataSource;
import db.UserDAO;
import exceptions.ShoppingDbFailure;
import model.User;

public class RegisterServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String userId = request.getParameter("user_id");
		String password = request.getParameter("passcode");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String emailId = request.getParameter("email_id");
		String userStreet = request.getParameter("street");
		String aptNo = request.getParameter("apt_no");
		String userCity = request.getParameter("city");
		String userState = request.getParameter("state");
		String zip = request.getParameter("zip");

		int zipCode = 0;

		if (userId == null || userId.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter a user id.</font></div>");

		} else if (password == null || password.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter a password.</font></div>");

		} else if (firstName == null || firstName.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter First Name.</font></div>");

		} else if (lastName == null || lastName.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter last name.</font></div>");

		} else if (emailId == null || emailId.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter email Id.</font></div>");

		} else if (userStreet == null || userStreet.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter street.</font></div>");

		} else if (userCity == null || userCity.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter city.</font></div>");

		} else if (userState == null || userState.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter state.</font></div>");
		} else if (zip == null || zip.isEmpty()) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/register.html");

			requestDispatcher.include(request, response);
			out.println("<div align=center><font color=red> Please "
					+ "enter pincode.</font></div>");

			try {
				zipCode = Integer.parseInt(request.getParameter("zip"));
			} catch (NumberFormatException e1) {
				out.println("<div align=center><font color=red> Please "
						+ "enter valid zip code.</font></p>");
			}
		} else {
			// 1.Get Connection object
			DataSource dataSource = ShoppingAppDataSource.setupDataSource();

			// 2. Create User object
			User user = new User(firstName, lastName, emailId, password,
					userId, userStreet, aptNo, userCity, userState, zipCode);

			// 3. Call UserDAO insert method passing user and connection

			Connection dbConn = null;
			try {
				dbConn = dataSource.getConnection();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

			try {
				UserDAO.insertUserDetailsWithConn(user, dbConn);
				
				HttpSession session = request.getSession();
				session.setAttribute("userid", userId);
				
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("/homepage.jsp");
				out.println("<div> Welcome " + userId + "! </div>");
				requestDispatcher.include(request, response);

			} catch (ShoppingDbFailure se) {
				if (se.getFailureReason() == ShoppingDbFailure.DUPLICATE_USER) {
					RequestDispatcher requestDispatcher = getServletContext()
							.getRequestDispatcher("/register.html");

					requestDispatcher.include(request, response);
					out.println("<div align=center><font color=red> "
							+ "User id already exists, choose another id </font></div>");
				}

			} catch (SQLException e) {
				
				out.println("System Error, Contact Admin");
			}finally{
				if(dbConn != null){
					try {
						dbConn.close();
					} catch (SQLException e) {
						out.println("System Error, Contact System Admin");
					}
				}
			}

		}

	
	}

}