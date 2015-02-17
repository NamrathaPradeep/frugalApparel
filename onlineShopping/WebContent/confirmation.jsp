<%@page import="model.Product"%>
<%@page import="model.UserOrder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="db.ShoppingAppDataSource"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="db.CategoryProductDAO"%>
<%@page import="db.OrderAndOrderMappingDAO"%>
<%@page import="model.Category"%>
<%@ page import="java.util.List"%>
<%@ page import="db.UserDAO"%>
<%@ page import="model.User"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Confirmation Page</title>
<style type="text/css" media="Screen">
body {
	font-family: helvetica;
}

h3 {
	font-style: oblique;
	color: #8a0829;
	margin: 0 158px 0 0;
}

h1 {
	color: #8a0829;
	margin: 0 230px;
}

p {
	font-size: 18px;
	color: #8a0829;
	text-align: center;
	color: #8a0829;
}

.btn,.btn:hover {
	background: #FFE4D4;
	background-image: -webkit-linear-gradient(top, #FFE4E1, pink);
	background-image: -moz-linear-gradient(top, #FFE4E1, pink);
	background-image: -ms-linear-gradient(top, #FFE4E1, pink);
	background-image: -o-linear-gradient(top, #FFE4E1, pink);
	background-image: linear-gradient(to bottom, #FFE4E1, pink);
	-webkit-border-radius: 28;
	-moz-border-radius: 28;
	border-radius: 48px;
	text-shadow: 0px 1px 0px #666666;
	font-family: Courier New, monospace;
	color: #8a0829;
	font-size: 12px;
	padding: 10px 20px 10px 20px;
	text-decoration: none;
	width: 170px;
	margin-top: 8px;
	border: 4px solid #ddd;
}
</style>
</head>

<%
	String userid = (String) session.getAttribute("userid");

	Integer orderId = (Integer) request.getAttribute("orderId");

	DataSource dataSource = ShoppingAppDataSource.setupDataSource();
	Connection dbConn = null;
	dbConn = dataSource.getConnection();

	UserOrder userOrder = OrderAndOrderMappingDAO.getOrderByOrderId(
			orderId, dbConn);
	User userDet = UserDAO.getUserDetailsWithConn(userid, dbConn);
%>

<body style="background-color: #FFE4E1;">
	<div>
		<div>
			<a class="btn"
				style="width: 50px; float: right; margin: 45px 2px 20px 0; background: #FFF0F5; font-size: 16px; border-radius: 28px;"
				href="logout">Logout</a> <a class="btn"
				style="width: 50px; float: right; margin: 45px 2px 20px 0; background: #FFF0F5; border-radius: 28px; font-size: 16px;"
				href="userprofile.jsp">Profile</a> <a class="btn"
				style="width: 70px; float: right; margin: 45px 2px 20px 0; border-radius: 28px; background: #FFF0F5; font-size: 16px;"
				href="homepage.jsp">HomePage</a>
		</div>
		<div style="background-color: #F6CECE;">
			<h1 align="center">Frugal Apparel</h1>
		</div>
		<div style="margin-top:100px;">
			<p>Your Order has been successfully placed!</p>
			<p>Order Id : ${orderId}</p>
			<p>
				Order Amount:
				<fmt:formatNumber value="${param.orderAmount}" type="currency"
					currencyCode="USD" />
			</p>
			<c:set var="orderdte" value="<%=new java.util.Date()%>" />
			<c:set var="weekfromnow"
				value="<%=new java.util.Date(
					(new java.util.Date().getTime() + 60 * 60 * 168 * 1000))%>" />
			<p>
				Order Date:
				<fmt:formatDate type="date" value="${orderdte}" />
			</p>
			<p>
				Your Order will be delivered on:
				<fmt:formatDate type="date" value="${weekfromnow}" />
			</p>
			<p style="font-style: oblique; font-size: 22px;">Thanks for the
				purchase ${userid} ! please come back again!</p>
		</div>
	</div>
</body>
</html>