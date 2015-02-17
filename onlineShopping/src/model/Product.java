package model;

public class Product {
	int productId;
	String proudctName;
	double productPrice;
	String productDescription;
	int categoryId;
	int available;
	int version;
	String product_img_loc;
	
	public String getProduct_img_loc() {
		return product_img_loc;
	}
	public void setProduct_img_loc(String product_img_loc) {
		this.product_img_loc = product_img_loc;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProudctName() {
		return proudctName;
	}
	public void setProudctName(String proudctName) {
		this.proudctName = proudctName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
		
}
