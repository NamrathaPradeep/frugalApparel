package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Product;
import model.UserOrder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import db.OrderAndOrderMappingDAO;
import db.ShoppingAppDataSource;
import exceptions.ShoppingDbFailure;

public class JsonOrderDetailsServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userId = null;
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		if(session != null){
			userId = (String)session.getAttribute("userid");
		}
		
		int orderIdFromRequest = Integer.parseInt(request.getParameter("orderid"));

		DataSource dataSource = ShoppingAppDataSource.setupDataSource();
		Connection dbConn = null;
		try {
			dbConn = dataSource.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		Map<Integer, List<UserOrder>> orderProductMap = new HashMap<Integer, List<UserOrder>>();
		Map<Integer, Double> orderAmountMap = new HashMap<Integer, Double>();
		
		try {		
			//Orders
			List<UserOrder> orders = OrderAndOrderMappingDAO.getAllOrdersForUserWithConn(userId, dbConn);
			
			if(orders != null){
				for(UserOrder order : orders){
					int orderId = order.getOrderId();
					if(orderProductMap.get(orderId)== null){
						 List<UserOrder> productList = new ArrayList<UserOrder>();
						 
						 productList.add(order);
						 orderProductMap.put(orderId, productList);
						 
						 orderAmountMap.put(orderId, order.getOrderAmount());
					}else{
						orderProductMap.get(orderId).add(order);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShoppingDbFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(dbConn != null){
				try {
					dbConn.close();
				} catch (SQLException e) {
					out.println("System Error, Contact System Admin");
				}
			}
		}
		
		List<UserOrder> ordersForOrdersId = orderProductMap.get(orderIdFromRequest);
		List<Product> products = new ArrayList<>();
		
		if(ordersForOrdersId != null){
			for(UserOrder uo : ordersForOrdersId){
				Product p = new Product();
				p.setProudctName(uo.getProductName());
				p.setProductPrice(uo.getProductPrice());
				
				
				products.add(p);
			}
		}
		
		
		OrderDetails od = new OrderDetails();
		od.setProducts(products);
		
		String responseJson= "";
		try {
			 responseJson = writeObjectAsJson(od);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("application/json");

		out.println(responseJson);
	}
	
	public static String writeObjectAsJson(Object object) throws Exception  {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writer(new SimpleFilterProvider());
			return writer.writeValueAsString(object);
		} catch (Exception e) {

			throw e;
		}
	}
	
	public class OrderDetails{
		
		List<Product> products;
		
		public OrderDetails(){
			
		}

		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}
		
	}

}
