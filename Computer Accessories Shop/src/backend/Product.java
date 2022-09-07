package backend;

import java.text.DecimalFormat;

public class Product {
	
	private static final DecimalFormat dfZero = new DecimalFormat("0.00");
	
	private Integer barcode;
	private String device_name; 
	private String device_type; 
	private String brand;
	private String colour;
	private String connectivity;
	private Integer quantity;
	private Float original_cost;
	private Float retail_price;
	private String additional_information;
	
	public Product(Integer barcode, String device_name, String device_type, String brand, String colour, String connectivity, Integer quantity, Float original_cost, Float retail_price, String additional_information) {
		this.barcode = barcode;
		this.device_name = device_name;
		this.device_type = device_type;
		this.brand = brand;
		this.colour = colour;
		this.connectivity = connectivity;
		this.quantity = quantity;
		this.additional_information = additional_information;
		this.original_cost = original_cost;
		this.retail_price = retail_price;
	}
	
	// set quantity
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the barcode
	 */
	public Integer getBarcode() {
		return barcode;
	}

	/**
	 * @return the device_name
	 */
	public String getDevice_name() {
		return device_name;
	}

	/**
	 * @return the device_type
	 */
	public String getDevice_type() {
		return device_type;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * @return the connectivity
	 */
	public String getConnectivity() {
		return connectivity;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @return the original_cost
	 */
	public Currency getOriginal_cost() {
		return new Currency(original_cost);
	}

	/**
	 * @return the retail_price
	 */
	public Currency getRetail_price() {
		return new Currency(retail_price);
	}

	/**
	 * @return the additional_information to populate tableview
	 */
	public String getAdditional_information() {
		if (this.additional_information.matches("UK||US")) {
			return additional_information+" layout";
		} else {
			return additional_information+" keys";
		}
	}
	
	// return in stock/out of stock, will use return values to populate TableView
	public String getIn_stock() {
		if (this.quantity > 0) {
			return "In stock";
		} else {
			return "Out of stock";
		}
	}

	// Get original cost as a string...not as a currency
	public String getOriginal_costStr() {
		
		return (dfZero.format(this.original_cost));
	}
	
	// Get retail price as a string
	public String getRetail_priceStr() {
		return (dfZero.format(this.retail_price));
	}

	// return just the value of additional information, dont need the extra words for tableview
	public String getAdditional_informationStr() {
		return additional_information;
	}


}