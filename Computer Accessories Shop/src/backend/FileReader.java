package backend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import backend.User.Permission;

public class FileReader {
	
	// return list of users from database info
	public static List<User> return_accounts() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/backend/UserAccounts.txt"));
		List<User> users = new ArrayList<User>();
		String line;
		String[] line_split;
		Permission permission;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			line_split = line.split(", ");
			String user_id = line_split[0];
			String username = line_split[1];
			String name = line_split[2];
			String house_number = line_split[3];
			String post_code = line_split[4];
			String city = line_split[5];
			
			if (line_split[6].equals("customer")) {
				permission = Permission.CUSTOMER;
			} else {
				permission = Permission.ADMIN;
			}
			
			// create list of user objects
			users.add(new User(permission, user_id, username, name, house_number, post_code, city));
		}
		scanner.close();
		return users;		
	}
	
	// return list of products from database info
	public static List<Product> return_stock_information() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/backend/Stock.txt"));
		List<Product> products = new ArrayList<Product>();
		String line;
		String[] line_split;

		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			line_split = line.split(", ");
			Integer barcode = Integer.parseInt(line_split[0]);
			String device_name = line_split[1];
			String device_type = line_split[2];
			String brand = line_split[3];
			String colour = line_split[4];
			String connectivity = line_split[5];
			Integer quantity = Integer.parseInt(line_split[6]);
			Float original_cost = Float.parseFloat(line_split[7]);
			Float retail_price = Float.parseFloat(line_split[8]);
			products.add(new Product(barcode, device_name, device_type, brand, colour, connectivity, quantity, original_cost, retail_price, line_split[9]));
		}
		scanner.close();
		return products;
	}

	/**
	 * @param basket_items
	 * @throws IOException
	 */
	
	// update the database quantities depending on how many were checked out
	public static void update_database_using_basket(Dictionary<Product, Integer> basket_items) throws IOException {
		List<Product> stock_info = FileReader.return_stock_information();
		Enumeration<Product> basket_item_keys_enum = basket_items.keys();
		List<Product> basket_item_keys = new ArrayList<Product>();
        while (basket_item_keys_enum.hasMoreElements()) {
        	basket_item_keys.add(basket_item_keys_enum.nextElement());
        }
        
		for(Integer i = 0; i < stock_info.size(); i++) {
			for (Product basket_item : basket_item_keys) {
				if (stock_info.get(i).getBarcode().equals(basket_item.getBarcode())) {
					stock_info.get(i).setQuantity(basket_items.get(basket_item));
				}
			}
		}
		
		String database = "";
		for (Product product: stock_info) {
			database = database+product.getBarcode()+", "+product.getDevice_name()+", "+product.getDevice_type()+", "+product.getBrand()+", "+product.getColour()+", "+product.getConnectivity()+", "+product.getQuantity()+", "+product.getOriginal_costStr()+", "+product.getRetail_priceStr()+", "+product.getAdditional_informationStr()+"\n";
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/backend/Stock.txt"));
			database = database.trim();
			writer.write(database);
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//System.out.print(database);
		
		
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	// add product to database
	public static void add_product_to_database(Integer barcode, String device_name, String device_type, String brand,
			String colour, String connectivity, Integer quantity, Float original_cost, Float retail_price,
			String additional_information) throws FileNotFoundException {
		
		String database = "";
		Boolean new_entry_needed = false;
		List<Product> stock_info = FileReader.return_stock_information();
		Product new_product = new Product (barcode, device_name, device_type, brand, colour, connectivity, quantity, original_cost, retail_price, additional_information);
		for (Product product: stock_info) {
			database = database+product.getBarcode()+", "+product.getDevice_name()+", "+product.getDevice_type()+", "+product.getBrand()+", "+product.getColour()+", "+product.getConnectivity()+", "+product.getQuantity()+", "+product.getOriginal_costStr()+", "+product.getRetail_priceStr()+", "+product.getAdditional_informationStr()+"\n";
		}
		
		System.out.println(new_product.getBarcode());
		System.out.println(new_product.getBrand());
		System.out.println(new_product.getColour());
		System.out.println(new_product.getDevice_name());
		System.out.println(new_product.getDevice_type());
		System.out.println(new_product.getConnectivity());
		System.out.println(new_product.getOriginal_cost());
		System.out.println(new_product.getRetail_price());
		
		for (Product item : stock_info) {
			if (item.getBarcode().equals(new_product.getBarcode())) {
				item.setQuantity(item.getQuantity()+1);
				database = "";
				for (Product product: stock_info) {
					// values in database have changed, need to be re-instantiated
					database = database+product.getBarcode()+", "+product.getDevice_name()+", "+product.getDevice_type()+", "+product.getBrand()+", "+product.getColour()+", "+product.getConnectivity()+", "+product.getQuantity()+", "+product.getOriginal_costStr()+", "+product.getRetail_priceStr()+", "+product.getAdditional_informationStr()+"\n";
				}
			} else {
				new_entry_needed = true;
			}
		}
		if (new_entry_needed) {
			database = database+new_product.getBarcode()+", "+new_product.getDevice_name()+", "+new_product.getDevice_type()+", "+new_product.getBrand()+", "+new_product.getColour()+", "+new_product.getConnectivity()+", "+new_product.getQuantity()+", "+new_product.getOriginal_costStr()+", "+new_product.getRetail_priceStr()+", "+new_product.getAdditional_informationStr()+"\n";
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/backend/Stock.txt"));
			System.out.println(database);
			writer.write(database);
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
}
