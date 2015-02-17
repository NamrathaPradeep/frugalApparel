package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.CardInfo;
import model.Product;
import model.User;
import db.CardDAO;
import db.CategoryProductDAO;
import db.OrderAndOrderMappingDAO;
import db.ShoppingAppDataSource;
import db.UserDAO;
import exceptions.ShoppingDbFailure;

public class ConfirmationServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userId = null;
		HttpSession session = request.getSession(false);
		if(session != null){
			userId = (String)session.getAttribute("userid");
		}

		String productsIdsInRequest = request.getParameter("products");
		String orderAmount = request.getParameter("orderAmount");
		String cardnumber = request.getParameter("cardnumber");
		String cvvcode = request.getParameter("cvvcode");
		String cardtype = request.getParameter("cardtype");
		String expirationdate = request.getParameter("expirationdate");
		
		CardInfo cardInfo = new CardInfo(cardnumber, cardtype, expirationdate, userId, Integer.parseInt(cvvcode));
		
		Double amount = Double.parseDouble(orderAmount);
		DataSource dataSource = ShoppingAppDataSource.setupDataSource();				 
		int orderId = 0;
		
		
		try {
			orderId = placeOrder(dataSource, productsIdsInRequest, amount, cardInfo, userId);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//Redirect to order confirmation page
		RequestDispatcher rd = request.getRequestDispatcher("confirmation.jsp");
		request.setAttribute("orderId", new Integer(orderId));
		rd.forward(request, response);

	}

	private int placeOrder(DataSource dataSource, String productsIdsInRequest,
			double amount, CardInfo cardInfo, String userId)
			throws SQLException {
		Connection dbConn = null;
		int orderId = 0;

		try {
			dbConn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbConn.setAutoCommit(false);

		List<Integer> productIds = new ArrayList<Integer>();

		for (String p : productsIdsInRequest.split(",")) {
			productIds.add(Integer.parseInt(p));
		}

		try {
			List<Product> productsFromDB = CategoryProductDAO
					.getProductByProductIds(productsIdsInRequest, dbConn);

			List<CardInfo> userCardInfo = CardDAO.getAllCardsForUserWithConn(
					userId, dbConn);
			boolean cardPresent = false;

			if (userCardInfo != null) {
				for (CardInfo c : userCardInfo) {
					if (c.getCardNo().equals(cardInfo.getCardNo())) {
						cardPresent = true;
						break;

					}
				}
			}

			if (!cardPresent) {
				CardDAO.insertCardDetailsWithConn(cardInfo, dbConn);
			}

			// create order
			orderId = OrderAndOrderMappingDAO.insertOrderWithConn(amount,
					userId, dbConn);

			// 4. insert order/product mapping
			OrderAndOrderMappingDAO.insertOrderProductMappingWithConn(orderId,
					productIds, dbConn);

			// 5. update product availability
			CategoryProductDAO.updateProductAvailabilty(productsFromDB, dbConn);

			// commit
			dbConn.commit();

		} catch (SQLException e) {
			System.out.println("Database operation failure: " + e);

			if (dbConn != null) {
				dbConn.rollback();
			}
		} catch (ShoppingDbFailure e) {
			System.out.println("Failure with Database operation: "
					+ e.getMessage());

			if (dbConn != null) {
				dbConn.rollback();
			}
		} finally {
			dbConn.close();
		}

		return orderId;
	}
}