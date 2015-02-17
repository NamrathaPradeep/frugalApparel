<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="db.UserDAO"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="db.ShoppingAppDataSource"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="model.User"%>
<%@page import="model.Category"%>
<%@page import="model.Product"%>
<%@page import="java.sql.Connection"%>
<%@page import="db.CategoryProductDAO"%>
<%@page import="model.Product"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Iterator"%>

<HTML>
<HEAD>
<TITLE>Frugal Apparel</TITLE>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<script>
	var productsAdded = [];
</script>

<script>
	function addToCartClick(clickedId) {
		document.getElementById(clickedId).disabled = true;

		productsAdded.push(clickedId);

	}

	function checkOut() {
		var products = "";
		

		var productsSize = productsAdded.length;
		for ( var i in productsAdded) {
			products = products + productsAdded[i];
			if (i != productsSize - 1) {
				products = products + ",";
			}
		}

		if(products.length == 0){
			alert("add products to cart to checkout");
			return;
		}

		
		document.location.href = "checkout.jsp?products=" + products;
	}
</script>

<style type="text/css" media="Screen">
div {
	color: #8A0829;
}

#tabs ul {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

#tabs h3 {
	font-family: helvetica;
	text-decoration: underline;
}

#tabs a,input {
	text-decoration: none;
	display: block;
	padding: 3px 30px 3px 3px;
	background-color: #F6CECE;
	color: #8a0829;
	border: 4px solid #ddd;
	text-align: center;
	font-size: 20px;
}

#tabs a:active {
	padding: 2px 13px 4px 7px;
	background-color: pink;
}

#tabs li li a {
	text-decoration: none;
	padding: 3px 3px 3px 17px;
	background-color: pink;
	color: pink;
}

.homepagelinks {
	width: 25px;
	text-decoration: none;
	float: right;
	margin: 56px 30px 0 0;
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
}

.btn:hover,input {
	background: #ffe4e1;
	text-decoration: none;
}

ul#display-inline-block,ul#display-inline-block li {
	/* Setting a common base */
	/* margin: 10px 10px 0; */
	padding: 0 26px 0 0;
}

ul#display-inline-block li {
	display: inline-block;
}
</style>

<script>
	$(function() {

		$("#tabs").tabs();

	});
</script>
</HEAD>

<BODY style="background-color: #FFE4E1;">
	<div id=tabs>
		<a onclick="checkOut()" class="btn"
			style="width: 70px; float: right; margin: 45px 2px 20px 0; border-radius: 28px; background: #FFF0F5; font-size: 16px;"
			href="#">Checkout</a> <a class="btn"
			style="width: 50px; float: right; margin: 45px 2px 20px 0; background: #FFF0F5; border-radius: 28px; font-size: 16px;"
			href="userprofile.jsp">Profile</a> <a class="btn"
			style="width: 50px; float: right; margin: 45px 2px 20px 0; background: #FFF0F5; font-size: 16px; border-radius: 28px;"
			href="logout">Logout</a>

		<div style="background-color: #F6CECE;">
			<h1 align="center" style="margin-left: 70px;">Frugal Apparel</h1>
		</div>

		<div style="float: left; width: 20%; margin-right: 2%;">
			<%
				String userid = (String) session.getAttribute("userid");

				DataSource dataSource = ShoppingAppDataSource.setupDataSource();
				Connection dbConn = null;
				dbConn = dataSource.getConnection();

				List<Category> categoryFromDb = CategoryProductDAO
						.getCategories(dbConn);

				Map<Integer, List<Product>> categoryProductMap = new HashMap<Integer, List<Product>>();

				for (Category c : categoryFromDb) {
					List<Product> products = CategoryProductDAO
							.getProductForCategory(c.getCategoryId(), dbConn);
					categoryProductMap.put(c.getCategoryId(), products);
				}
			%>


			<h3>Categories:</h3>

			<ul>
				<%
					Iterator<Category> categoryIterator = categoryFromDb.iterator();
				%>
				<li><a href="#tabs1"> <%
 	out.println(categoryIterator.next().getCategoryName());
 %>
				</a></li>

				<li><a href="#tabs2"> <%
 	out.println(categoryIterator.next().getCategoryName());
 %>
				</a></li>

				<li><a href="#tabs3"> <%
 	out.println(categoryIterator.next().getCategoryName());
 %>
				</a></li>

				<li><a href="#tabs4"> <%
 	out.println(categoryIterator.next().getCategoryName());
 %>
				</a></li>
			</ul>
		</div>

		<div>


			<div id="tabs1" style="float: left;">

				<ul id="display-inline-block">
					<%
						List<Product> product1 = categoryProductMap.get(101);
						for (Product p : product1) {
					%>

					<li><img src="<%out.println(p.getProduct_img_loc());%>">
						<div align=center style="text-decoration: underline;"><%=p.getProudctName()%></div>
						<div style="width: 180px; font-style: italic;"><%=p.getProductDescription()%></div>
						<div align=center>
							<b>$<%=p.getProductPrice()%></b>
						</div> <input type="submit" value="Add to Cart"
						id="<%=p.getProductId()%>" class="btn"
						onclick="addToCartClick(id); this.disabled=true; this.style.background ='#FFF0F5' ;
						this.value='Added to the Cart!';this.form.input();"></li>

					<%
						}
					%>
				</ul>
			</div>

			<div id="tabs2" style="float: left;">
				<ul id="display-inline-block">
					<%
						List<Product> product2 = categoryProductMap.get(201);
						for (Product p : product2) {
					%>

					<li><img src="<%out.println(p.getProduct_img_loc());%>">
						<div align=center style="text-decoration: underline;"><%=p.getProudctName()%></div>
						<div style="width: 180px; font-style: italic;"><%=p.getProductDescription()%></div>
						<div align=center>
							<b>$<%=p.getProductPrice()%></b>
						</div> <input type="submit" value="Add to Cart"
						id="<%=p.getProductId()%>" class="btn"
						onclick="addToCartClick(id); this.disabled=true; this.style.background ='#FFF0F5' ;
						this.value='Added to the Cart!';this.form.input();"></li>

					<%
						}
					%>
				</ul>
			</div>

			<div id="tabs3" style="float: left;">
				<ul id="display-inline-block">
					<%
						List<Product> product3 = categoryProductMap.get(301);
						for (Product p : product3) {
					%>

					<li><img src="<%out.println(p.getProduct_img_loc());%>">
						<div align=center style="text-decoration: underline;"><%=p.getProudctName()%></div>
						<div style="width: 180px; font-style: italic;"><%=p.getProductDescription()%></div>
						<div align=center>
							<b>$<%=p.getProductPrice()%></b>
						</div> <input type="submit" value="Add to Cart"
						id="<%=p.getProductId()%>" class="btn"
						onclick="addToCartClick(id); this.disabled=true; this.style.background ='#FFF0F5' ;
						this.value='Added to the Cart!';this.form.input();"></li>


					<%
						}
					%>
				</ul>
			</div>


			<div id="tabs4" style="float: left;">
				<ul id="display-inline-block">
					<%
						List<Product> product4 = categoryProductMap.get(401);
						for (Product p : product4) {
					%>

					<li><img src="<%out.println(p.getProduct_img_loc());%>">
						<div align=center style="text-decoration: underline;"><%=p.getProudctName()%></div>
						<div style="width: 180px; font-style: italic;"><%=p.getProductDescription()%></div>
						<div align=center>
							<b>$<%=p.getProductPrice()%></b>
						</div> <input type="submit" value="Add to Cart"
						id="<%=p.getProductId()%>" class="btn"
						onclick="addToCartClick(id); this.disabled=true; this.style.background ='#FFF0F5' ;
						this.value='Added to the Cart!';this.form.input();"></li>


					<%
						}
					%>
				</ul>

			</div>

		</div>



	</div>

</BODY>
</HTML>

