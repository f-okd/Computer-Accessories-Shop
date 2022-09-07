package backend;

// create new class, so we can make a list of this class to populate the basket table view
public class Basket_item {
	private Currency retail_price;
	private Integer barcode;
	
	//constructor
	public Basket_item(Product product) {
		this.retail_price = product.getRetail_price();
		this.barcode = product.getBarcode();
	}

	/**
	 * @return the barcode
	 */
	public Integer getBarcode() {
		return barcode;
	}

	/**
	 * @return the retail_price
	 */
	public Currency getRetail_price() {
		return retail_price;
	}


	
	
}
