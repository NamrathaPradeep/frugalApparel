package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import exceptions.ShoppingDbFailure;
import model.Category;
import model.Product;

public class CategoryProductDAO {
	
	public static final int AVAILABLE = 1;
	public static final int NOT_AVAILABLE = 2;

	public static List<Category> getCategories(Connection dbConn) throws SQLException{
		List<Category> categories = null;
		
		String getCategoriesSql = 
				"select category_id, category_name, category_desc " +
				"from category";

		try (PreparedStatement getCategoryDetailsStmt = dbConn
					.prepareStatement(getCategoriesSql)) {

			categories = getCategoryDetails(getCategoryDetailsStmt);
		}
		
		return categories;
	}
	
	
	private static  List<Category> getCategoryDetails(
			PreparedStatement getCategoryDetailsStmt) throws 
			SQLException {
		List<Category> categories = new ArrayList<Category>();
		
		
		try (ResultSet results = getCategoryDetailsStmt.executeQuery();) {
			while(results.next()){
				Category category = new Category();
				category.setCategoryId(results.getInt("category_id"));
				category.setCategoryName(results.getString("category_name"));
				category.setCategoryDesc(results.getString("category_desc"));	
				
				categories.add(category);
			}
		}

		return categories;
	}
	
	
	
	public static List<Product> getProductForCategory(int categoryId, Connection dbConn) throws SQLException{
		List<Product> products = null;
		
		String getProductsSql = 
				"select product_id, product_name, product_desc, product_price, category_id, version, product_img_loc " +
				"from product where category_id =  ? and available = 1 ";

		try (PreparedStatement getCategoryDetailsStmt = dbConn
					.prepareStatement(getProductsSql)) {

			products = getProducts(getCategoryDetailsStmt, categoryId);
		}
		
		return products;
	}
	
	
	private static  List<Product> getProducts(
			PreparedStatement getProductsStmt, int categoryId) throws 
			SQLException {
		
		List<Product> products = new ArrayList<Product>();
		
		getProductsStmt.setInt(1, categoryId);
		
		
		try (ResultSet results = getProductsStmt.executeQuery();) {
			while(results.next()){
				Product product = new Product();
				product.setProductId(results.getInt("product_id"));
				product.setProudctName(results.getString("product_name"));
				product.setProductDescription(results.getString("product_desc"));		
				product.setProductPrice(results.getDouble("product_price"));
				product.setCategoryId(results.getInt("category_id"));
				product.setVersion(results.getInt("version"));
				product.setProduct_img_loc(results.getString("product_img_loc"));
				products.add(product);
			}
		}

		return products;
	}
	
	public static List<Product> getProductByProductIds(String productIds, Connection dbConn) throws SQLException{
		List<Product> products = null;
		String[] productsIdsSplit = productIds.split(",");
		
		StringBuilder builder = new StringBuilder();

		for( int i = 0 ; i < productsIdsSplit.length; i++ ) {
		    builder.append("?,");
		}
		
		String getProductsSql = 
				"select product_id, product_name, product_desc, product_price, category_id, version, product_img_loc " +
				"from product where product_id in (" + builder.deleteCharAt( builder.length() -1 ).toString() + ")";

		try (PreparedStatement getCategoryDetailsStmt = dbConn
					.prepareStatement(getProductsSql)) {

			products = getProducts(getCategoryDetailsStmt, productsIdsSplit);
		}
		
		return products;
	}	
	
	private static  List<Product> getProducts(
			PreparedStatement getProductsStmt, String[] productIds) throws 
			SQLException {
		
		List<Product> products = new ArrayList<Product>();
		
		int index = 1;
		for( Object o : productIds ) {
			getProductsStmt.setObject(  index++, o ); // or whatever it applies 
		}
		
		
		try (ResultSet results = getProductsStmt.executeQuery();) {
			while(results.next()){
				Product product = new Product();
				product.setProductId(results.getInt("product_id"));
				product.setProudctName(results.getString("product_name"));
				product.setProductDescription(results.getString("product_desc"));		
				product.setProductPrice(results.getDouble("product_price"));
				product.setCategoryId(results.getInt("category_id"));
				product.setVersion(results.getInt("version"));
				product.setProduct_img_loc(results.getString("product_img_loc"));
				products.add(product);
			}
		}

		return products;
	}
	
	
	public static void updateProductAvailabilty(List<Product> products, Connection dbConn)
			throws SQLException, ShoppingDbFailure{
		String updatepProductSql = "UPDATE product SET available = ?, version = ?"
				+ " where product_id = ? AND version = ? ";
		
		
		for(Product proudct : products){
			
			// Update the product
			try (PreparedStatement updateProductStmt = dbConn
					.prepareStatement(updatepProductSql)) {
				
				updateProductStmt.setInt(1, NOT_AVAILABLE);
				updateProductStmt.setInt(2, (proudct.getVersion() + 1));
				updateProductStmt.setInt(3, proudct.getProductId());
				updateProductStmt.setInt(4, proudct.getVersion());			
				
				int rowsAffected = updateProductStmt.executeUpdate();
				
				if (rowsAffected != 1) { /* Exactly one row should have been updated */
						throw new ShoppingDbFailure(ShoppingDbFailure.STMT_FAILED);

				}
			}				
		}
		
	
	}
	
	

}
