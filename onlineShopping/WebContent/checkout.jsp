<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="db.ShoppingAppDataSource"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="db.CategoryProductDAO"%>
<%@page import="model.Category"%>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout Page</title>

<style type="text/css" media="Screen">
div {
	color: #8A0829;
}

.carddetails {
	float: left;
	width: 28%;
	margin: 0 0 0 9%;
}

.summary {
	float: left;
	width: 40%;
	margin: 0 0 0 9%;
	text-align: center;
	width: 300px;
	padding: 20px;
	border-top: 5px solid #000;
	border-bottom: 5px solid #ccc;
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#000),
		to(#ccc));
	background-image: -webkit-linear-gradient(#000, #ccc);
	background-image: -moz-linear-gradient(#000, #ccc),
		-moz-linear-gradient(#000, #ccc);
	background-image: -o-linear-gradient(#000, #ccc),
		-o-linear-gradient(#000, #ccc);
	background-image: linear-gradient(#000, #ccc),
		linear-gradient(#000, #ccc);
	-moz-background-size: 5px 100%;
	background-size: 5px 100%;
	background-position: 0 0, 100% 0;
	background-repeat: no-repeat;
}

h1,h3 {
	text-decoration: underline;
	color: #8a0829;
}

label {
	display: table-cell;
	text-align: left;
}

input {
	display: table-cell;
}

form {
	display: table;
}

p {
	display: table-row;
}

.gradient-top-to-bottom {
	
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
<body style="background-color: #FFE4E1; font-family: helvetica;">

	<div>
		<a class="btn"
			style="width: 50px; float: right; margin: 45px 2px 20px 0; background: #FFF0F5; font-size: 16px; border-radius: 28px;"
			href="logout">Logout</a> <a class="btn"
			style="width: 50px; float: right; margin: 45px 2px 20px 0; background: #FFF0F5; border-radius: 28px; font-size: 16px;"
			href="userprofile.jsp">Profile</a> <a class="btn"
			style="width: 70px; float: right; margin: 45px 2px 20px 0; border-radius: 28px; background: #FFF0F5; font-size: 16px;"
			href="homepage.jsp">HomePage</a>


		<h1 align="center"
			style="background-color: #F6CECE; color: #8a0829; text-decoration: none;">Frugal
			Apparel</h1>
		<div class="summary">
			<h3>Summary</h3>
			<%
				String userid = (String) session.getAttribute("userid");

				String products = request.getParameter("products");

				DataSource dataSource = ShoppingAppDataSource.setupDataSource();
				Connection dbConn = null;
				dbConn = dataSource.getConnection();

				List<Product> productsFromDB = CategoryProductDAO
						.getProductByProductIds(products, dbConn);
				double totalPrice = 0.0;

				for (Product p : productsFromDB) {
					out.println(p.getProudctName());

					out.println("---------- " + "  $ " + (p.getProductPrice()));
			%>
			<br /> <br />
			<%
				totalPrice = totalPrice + p.getProductPrice();
			%>
			<%
				}
			%>
			<hr>
			<b style="font-size: 20px;"> <%
 	out.println("Order Total --------" + "  $ " + totalPrice);
 %>
			</b>
		</div>
		<div class="carddetails">
			<h3>Card Details</h3>
			<form action="confirm" method="post" style="color: #8A0829;">
				<p>
					<label for="cardnumber">Card Number: </label> <input id="d"
						name="cardnumber" type="text" size="35"><br /> <br />
				</p>
				<p>
					<label for="cvvcode">CVV Code: </label> <input id="d"
						name="cvvcode" type="text" size="35"><br /> <br />
				</p>
				<p>
					<label for="cardtype">Card Type: </label> <select name="cardtype"
						style="width: 200px;">
						<option value="Visa">Visa</option>
						<option value="American Express">American Express</option>
						<option value="Master Card">Master Card</option>

					</select><br /> <br />
				</p>
				<p>
					<label for="expirationdate">Expiration Date: <br />(MMYYYY)
					</label> <input id="b" name="expirationdate" type="text" size="35"><br />
					<br />
				</p>

				<input type="hidden" id="d" name="products" type="text" size="35"
					value="<%=products%>" /><br /> <br /> <input class="btn"
					type="submit" style="float: left;" value="Confirm Order"> <input
					type="hidden" id="d" name="orderAmount" type="text" size="35"
					value="<%=totalPrice%>" /><br /> <br />
			</form>
		</div>
	</div>
</body>
</html>