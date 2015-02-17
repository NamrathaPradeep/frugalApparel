package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import exceptions.ShoppingDbFailure;

public class UserDAO {

	public static User getUserDetailsWithConn(String userId, Connection dbConn)
			throws ShoppingDbFailure, SQLException {

		String readCurBalSql = "SELECT first_name, last_name,"
				+ " email_id, user_id, street, apt_no, city, state, zip "
				+ "FROM user " + "WHERE user_id = ? ";

		User user = null;
		try (PreparedStatement getUserStmt = dbConn
				.prepareStatement(readCurBalSql)) {

			user = getUserDetails(getUserStmt, userId);
		}

		return user;
	}

	private static User getUserDetails(PreparedStatement getUserStmt,
			String userId) throws SQLException, ShoppingDbFailure {

		getUserStmt.setString(1, userId);
		User user = new User();

		try (ResultSet rs = getUserStmt.executeQuery();) {
			if (!rs.next()) {
				throw new ShoppingDbFailure(ShoppingDbFailure.INVALID_USER,
						"Unknown user id: " + userId);
			} else {
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmailId(rs.getString("email_id"));
				user.setUserId(rs.getString("user_id"));
				user.setStreet(rs.getString("street"));
				user.setAptNo(rs.getString("apt_no"));
				user.setCity(rs.getString("city"));
				user.setState(rs.getString("state"));
				user.setZipCode(rs.getInt("zip"));
			}
		}
		return user;
	}

	public static User authenticateUserWithConn(String userId, String password,
			Connection dbConn) throws ShoppingDbFailure, SQLException {

		String readCurBalSql = "SELECT first_name, last_name, email_id, "
				+ "user_id, street, apt_no, city, state, zip " + "FROM user "
				+ "WHERE user_id = ? and passcode = ?";

		User user = new User();

		try (PreparedStatement readCurBalStmt = dbConn
				.prepareStatement(readCurBalSql)) {

			readCurBalStmt.setString(1, userId);
			readCurBalStmt.setString(2, password);

			ResultSet rs = readCurBalStmt.executeQuery();
			if (!rs.next()) {
				throw new ShoppingDbFailure(ShoppingDbFailure.INVALID_USER,
						"Unknown user id: " + userId);
			} else {
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmailId(rs.getString("email_id"));
				user.setUserId(rs.getString("user_id"));
				user.setStreet(rs.getString("street"));
				user.setAptNo(rs.getString("apt_no"));
				user.setCity(rs.getString("city"));
				user.setState(rs.getString("state"));
				user.setZipCode(rs.getInt("zip"));
			}
		}

		return user;

	}

	public static void insertUserDetailsWithConn(User user, Connection dbConn)
			throws ShoppingDbFailure, SQLException {

		String insertUserDetailsSql = "INSERT INTO user"
				+ "(first_name, last_name, email_id, passcode,"
				+ " user_id, street, apt_no, city, state, zip) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement insertUserDetailsStmt = dbConn
				.prepareStatement(insertUserDetailsSql)) {

			insertUserDetailsStmt.setString(1, user.getFirstName());
			insertUserDetailsStmt.setString(2, user.getLastName());
			insertUserDetailsStmt.setString(3, user.getEmailId());
			insertUserDetailsStmt.setString(4, user.getPasscode());
			insertUserDetailsStmt.setString(5, user.getUserId());
			insertUserDetailsStmt.setString(6, user.getStreet());
			insertUserDetailsStmt.setString(7, user.getAptNo());
			insertUserDetailsStmt.setString(8, user.getCity());
			insertUserDetailsStmt.setString(9, user.getState());
			insertUserDetailsStmt.setInt(10, user.getZipCode());

			insertUserDetailsStmt.executeUpdate();
		} catch (SQLException e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				throw new ShoppingDbFailure(ShoppingDbFailure.DUPLICATE_USER,
						"User already exists : " + user.getUserId());
			}
			throw e;
		}

	}

	public static void updateUserDetailsWithConn(User user, Connection dbConn)
			throws ShoppingDbFailure, SQLException {

		String updateUserDetailsSql = "UPDATE user SET first_name = ?,"
				+ " last_name = ?, email_id = ?,passcode = ?, "
				+ "user_id = ?, street = ?, apt_no = ?,city = ?,state = ?,zip = ?"
				+ " where user_id = ?;";

		try (PreparedStatement updateUserDetailsStmt = dbConn
				.prepareStatement(updateUserDetailsSql)) {

			updateUserDetailsStmt.setString(1, user.getFirstName());
			updateUserDetailsStmt.setString(2, user.getLastName());
			updateUserDetailsStmt.setString(3, user.getEmailId());
			updateUserDetailsStmt.setString(4, user.getPasscode());
			updateUserDetailsStmt.setString(5, user.getUserId());
			updateUserDetailsStmt.setString(6, user.getStreet());
			updateUserDetailsStmt.setString(7, user.getAptNo());
			updateUserDetailsStmt.setString(8, user.getCity());
			updateUserDetailsStmt.setString(9, user.getState());
			updateUserDetailsStmt.setInt(10, user.getZipCode());
			updateUserDetailsStmt.setString(11, user.getUserId());

			int rowsUpdated = updateUserDetailsStmt.executeUpdate();

			if (rowsUpdated != -1) {
				throw new ShoppingDbFailure(ShoppingDbFailure.STMT_FAILED,
						"User update failed for user  : " + user.getUserId());
			}

		}

	}

	public static void deleteUserDetails(String user_id, Connection dbConn)
			throws ShoppingDbFailure, SQLException {

		String deleteUserDetailsSql = "DELETE  FROM user "
				+ "WHERE user_id = ?";

		try (PreparedStatement deleteUserDetailsStmt = dbConn
				.prepareStatement(deleteUserDetailsSql)) {

			deleteUserDetailsStmt.setString(1, user_id);

			int rowsDeleted = deleteUserDetailsStmt.executeUpdate();

			if (rowsDeleted != -1) {
				throw new ShoppingDbFailure(ShoppingDbFailure.STMT_FAILED,
						"User deletion failed for user  : " + user_id);
			}
		}

	}

}
