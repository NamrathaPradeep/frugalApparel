package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CardInfo;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import exceptions.ShoppingDbFailure;

public class CardDAO {

	public static void insertCardDetailsWithConn(CardInfo cardInfo,
			Connection dbConn) throws ShoppingDbFailure {

		String insertOrderSql = "INSERT INTO cardinfo"
				+ "(card_no, card_type, expiry, security_code, user_id) VALUES"
				+ "(?,?,?,?,?)";

		try (PreparedStatement insertOrderStmt = dbConn
				.prepareStatement(insertOrderSql)) {

			insertOrderStmt.setString(1, cardInfo.getCardNo());
			insertOrderStmt.setString(2, cardInfo.getCardType());
			insertOrderStmt.setString(3, cardInfo.getExpiryMonthYear());
			insertOrderStmt.setInt(4, cardInfo.getSecurityCode());
			insertOrderStmt.setString(5, cardInfo.getUserId());

			insertOrderStmt.executeUpdate();

		} catch (SQLException e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				throw new ShoppingDbFailure(ShoppingDbFailure.DUPLICATE_CARD,
						"Card already exists");
			}
		}

	}

	public static List<CardInfo> getAllCardsForUserWithConn(String userId,
			Connection dbConn) throws SQLException {

		String getOrderDetailsSql = "select cardinfo_id, card_no, card_type, expiry, security_code "
				+ "from cardinfo " + "where user_id = ? ";

		List<CardInfo> cards = null;

		try (PreparedStatement getCardInfoStmt = dbConn
				.prepareStatement(getOrderDetailsSql)) {

			cards = getCards(getCardInfoStmt, userId);
		}

		return cards;
	}

	private static List<CardInfo> getCards(PreparedStatement getCardInfoStmt,
			String userId) throws SQLException {

		List<CardInfo> cards = new ArrayList<CardInfo>();

		getCardInfoStmt.setString(1, userId);

		// Get the cards info
		try (ResultSet rs = getCardInfoStmt.executeQuery();) {
			while (rs.next()) {
				CardInfo ci = new CardInfo();
				ci.setCardInfoId((rs.getInt("cardinfo_id")));
				ci.setCardNo(rs.getString("card_no"));
				ci.setCardType(rs.getString("card_type"));
				ci.setExpiryMonthYear(rs.getString("expiry"));
				ci.setSecurityCode(rs.getInt("security_code"));

				cards.add(ci);
			}
		}

		return cards;
	}

	public static void deleteCardDetails(int cardInfoId, Connection dbConn)
			throws ShoppingDbFailure, SQLException {

		String deleteCardSql = "DELETE  FROM cardinfo "
				+ "WHERE cardinfo_id = ?";

		try (PreparedStatement deleteUserDetailsStmt = dbConn
				.prepareStatement(deleteCardSql)) {

			deleteUserDetailsStmt.setInt(1, cardInfoId);

			int rowsDeleted = deleteUserDetailsStmt.executeUpdate();

			if (rowsDeleted != 1) {
				throw new ShoppingDbFailure(ShoppingDbFailure.STMT_FAILED,
						"Card deletion failed for cardInfoId  : " + cardInfoId);
			}
		}

	}

}
