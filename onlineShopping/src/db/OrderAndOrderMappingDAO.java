package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.UserOrder;

import com.mysql.jdbc.Statement;

import exceptions.ShoppingDbFailure;

public class OrderAndOrderMappingDAO {

	public static int insertOrderWithConn(double orderAmount, String userId,
			Connection dbConn) throws ShoppingDbFailure, SQLException {

		String insertOrderSql = "INSERT INTO orders"
				+ "(order_date, order_amt, user_id) VALUES" + "(?,?,?)";

		int orderId = -1;

		try (PreparedStatement insertOrderStmt = dbConn.prepareStatement(
				insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {

			insertOrderStmt.setDate(1, new Date(Calendar.getInstance()
					.getTimeInMillis()));
			insertOrderStmt.setDouble(2, orderAmount);
			insertOrderStmt.setString(3, userId);

			insertOrderStmt.executeUpdate();

			ResultSet rs = insertOrderStmt.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}
		}

		return orderId;
	}

	public static void insertOrderProductMappingWithConn(int orderId,
			List<Integer> productIds, Connection dbConn)
			throws ShoppingDbFailure, SQLException {

		String insertOrderProductMappingSql = "INSERT INTO order_product_mapping"
				+ "(order_id, product_id) VALUES" + "(?,?)";

		for (int productId : productIds) {
			try (PreparedStatement insertOrderProductMappingStmt = dbConn
					.prepareStatement(insertOrderProductMappingSql,
							Statement.RETURN_GENERATED_KEYS)) {

				insertOrderProductMappingStmt.setInt(1, orderId);
				insertOrderProductMappingStmt.setInt(2, productId);

				insertOrderProductMappingStmt.executeUpdate();

			}
		}
	}

	public static UserOrder getOrderByOrderId(int orderId, Connection dbConn)
			throws SQLException, ShoppingDbFailure {
		String getOrderDetailsSql = "select o.order_no, o.order_amt, o.order_date "
				+ "from orders o " + "where o.order_no = ? ";

		UserOrder uo = null;
		try (PreparedStatement getOrderDetailsStmt = dbConn
				.prepareStatement(getOrderDetailsSql)) {
			uo = getOrder(getOrderDetailsStmt, orderId);
		}

		return uo;
	}

	private static UserOrder getOrder(PreparedStatement getOrderDetailsStmt,
			int orderId) throws SQLException, ShoppingDbFailure {

		getOrderDetailsStmt.setInt(1, orderId);

		UserOrder uo = null;
		
		// Get the order info
		try (ResultSet rs = getOrderDetailsStmt.executeQuery();) {
			while (rs.next()) {
				uo = new UserOrder();
				uo.setOrderId(rs.getInt("order_no"));
				uo.setOrderAmount(rs.getDouble("order_amt"));
				uo.setOrderDate(rs.getDate("order_date"));
			}
		}

		return uo;
	}

	public static List<UserOrder> getAllOrdersForUserWithConn(String userId,
			Connection dbConn) throws SQLException, ShoppingDbFailure {

		String getOrderDetailsSql = "select o.order_no, o.order_date, o.order_amt, p.product_id, p.product_name, p.product_price "
				+ "from orders o, product p, order_product_mapping op "
				+ "where o.user_id = ? "
				+ "and o.order_no = op.order_id "
				+ "and op.product_id = p.product_id";

		List<UserOrder> userOrders = null;

		try (PreparedStatement getOrderDetailsStmt = dbConn
				.prepareStatement(getOrderDetailsSql)) {
			userOrders = getOrders(getOrderDetailsStmt, userId);
		}

		return userOrders;
	}

	private static List<UserOrder> getOrders(
			PreparedStatement getOrderDetailsStmt, String userId)
			throws SQLException, ShoppingDbFailure {

		List<UserOrder> userOrders = new ArrayList<UserOrder>();
		getOrderDetailsStmt.setString(1, userId);

		// Get the orders info
		try (ResultSet rs = getOrderDetailsStmt.executeQuery();) {
			while (rs.next()) {
				UserOrder uo = new UserOrder();
				uo.setOrderId(rs.getInt("order_no"));
				uo.setOrderAmount(rs.getDouble("order_amt"));
				uo.setProductName(rs.getString("product_name"));
				uo.setProductId(rs.getInt("product_id"));
				uo.setProductPrice(rs.getDouble("product_price"));
				uo.setOrderDate(rs.getDate("order_date"));
				userOrders.add(uo);
			}
		}

		return userOrders;
	}

}
