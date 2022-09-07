package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import backend.Currency;
import backend.FileReader;
import backend.Product;
import backend.User.Permission;
import gui.SceneController.scenes;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StoreController {
	private static Scene store_scene;
	
	private static ObservableList<Product> stock_info = FXCollections.observableArrayList();
	
	private static ObservableList<Product> basket_items = FXCollections.observableArrayList();
	
	private static Dictionary<Product, Integer> item_quantities = new Hashtable<Product, Integer>();
	
	private static TableView<Product> basket, store_tableview;
	
	private  static Spinner<Integer> spinner, quantity_spinner, number_of_keys_spinner;;
	
	private static Integer num_keys_query = 0;
	
	private static TextField brand_textfield, card_number_textfield, secuirity_code_textfield, email_address_textfield, barcode_textfield, add_brand_textfield, colour_textfield, original_cost_textfield, retail_price_textfield, device_type_textfield;

	private static Button sign_out1, sign_out2, sign_out3, sign_out4, add_to_basket, remove_from_basket, clear_basket, card_checkout_button, paypal_checkout_button, add_item_button;
	
	private static Label quantity_error_message, card_payment_error_label, card_payment_confirmation_label, paypal_error_label, email_checkout_confirmation_label, additional_information_label, add_item_confirmation_label;
	
	private static Pane card_payment_pane, paypal_pane;
	
	private static RadioButton credit_card_radio_button, paypal_radio_button, mouse_radio_button, keyboard_radio_button, wireless_radio_button, wired_radio_button, uk_layout_radio_button, us_layout_radio_button;
	
	private static Currency amount;
	
	private static TabPane tab_pane;

	
	//-----------------------------------------------------------------------------------------------------//
	public static Scene getScene() {
		return store_scene;
	}
	//-----------------------------------------------------------------------------------------------------//
	private static void add_listeners() {
		// populate table view using current spinner value as filter key for which mice to display
		StoreController.spinner.getEditor().textProperty().addListener(
				new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						// TODO Auto-generated method stub
						try {
							StoreController.num_keys_query = Integer.decode(arg2);
							populate_store_tableview(num_keys_query);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			);
		// populate table view using current textfield value as filter key for which brands to display
		StoreController.brand_textfield.textProperty().addListener(
			new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				try {
					populate_store_tableview(StoreController.num_keys_query);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		// go back to login page after sign out
		StoreController.sign_out1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				try {
					SceneController.switch_scene(scenes.LOGIN);
					StoreController.clear_basket();
					//set store page as the active tab 
					StoreController.tab_pane.getSelectionModel().select(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// go back to login page after sign out
		StoreController.sign_out2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				try {
					SceneController.switch_scene(scenes.LOGIN);
					StoreController.clear_basket();
					StoreController.tab_pane.getSelectionModel().select(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// go back to login page after sign out
		StoreController.sign_out3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				try {
					SceneController.switch_scene(scenes.LOGIN);
					StoreController.clear_basket();
					StoreController.tab_pane.getSelectionModel().select(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		// go back to login page after sign out
		StoreController.sign_out4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				try {
					SceneController.switch_scene(scenes.LOGIN);
					StoreController.clear_basket();
					StoreController.tab_pane.getSelectionModel().select(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		// add item to basket on click
		// reduce the amount of that item that you can add to basket by 1
		StoreController.add_to_basket.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				if (StoreController.store_tableview.getSelectionModel().getSelectedItem() != null) {
				    Product selected_item = StoreController.store_tableview.getSelectionModel().getSelectedItem();

				    if(StoreController.item_quantities.get(selected_item) > 0) {
					    StoreController.basket_items.add(selected_item);
					    StoreController.basket.getItems().clear();
					    StoreController.basket.getItems().addAll(StoreController.basket_items);
					    StoreController.item_quantities.put(selected_item, (StoreController.item_quantities.get(selected_item))-1);
					    StoreController.quantity_error_message.setText("");
					    update_total_cost();
				    } else {
				    	StoreController.quantity_error_message.setText("Not enough of this item in stock");
				    }
				}
			}
			
		});
		// add item to basket on click
		// increase the amount of that item that you can add to basket by 1
		StoreController.remove_from_basket.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				if (StoreController.basket.getSelectionModel().getSelectedItem() != null) {
					StoreController.quantity_error_message.setText("");
					// remove from list of items, so total cost will update
					int selected_index = basket.getSelectionModel().getSelectedIndex();
				    StoreController.basket_items.remove(selected_index);
				    
				    // remove from TableView
				    Product selected_item = basket.getSelectionModel().getSelectedItem();
				    StoreController.basket.getItems().remove(selected_item);
				    StoreController.item_quantities.put(selected_item, (StoreController.item_quantities.get(selected_item))+1);
				    update_total_cost();
				}
			}
		});
		// show card payment scene when you click this
		StoreController.credit_card_radio_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		            StoreController.show_card_payment_pane();
		        } else {
		            // ...
		        }
		    }
		});
		// show paypal payment scene when you click this
		StoreController.paypal_radio_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	StoreController.show_paypal_payment_pane();
		        } else {
		            // ...
		        }
		    }
		});
		// show mice additional info entry when you click this
		StoreController.mouse_radio_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	StoreController.additional_information_label.setText("NO.keys");
		        	StoreController.uk_layout_radio_button.setVisible(false);
		        	StoreController.us_layout_radio_button.setVisible(false);
		        	StoreController.number_of_keys_spinner.setVisible(true);
		        } else {
		            // ...
		        }
		    }
		});
		// show keyboard additional info entry when you click this
		StoreController.keyboard_radio_button.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		        	StoreController.additional_information_label.setText("Layout");
		        	StoreController.number_of_keys_spinner.setVisible(false);
		        	StoreController.uk_layout_radio_button.setVisible(true);
		        	StoreController.us_layout_radio_button.setVisible(true);
		        	
		        } else {
		            // ...
		        }
		    }
		});
		// checkout using paypal when you click this
		StoreController.paypal_checkout_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				StoreController.update_total_cost();
				if (StoreController.validate_email()) {
					if (!StoreController.amount.toString().equals(new Currency(0.0f).toString())) {
						try {
							StoreController.checkout("paypal");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						StoreController.paypal_error_label.setText("");
					} else {
						StoreController.paypal_error_label.setText("Basket is empty");
					}
				} else {
					StoreController.paypal_error_label.setText("Invalid Email Address");
				}
			}
		});
		// checkout using card when you click this
		StoreController.card_checkout_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				StoreController.update_total_cost();
				if (StoreController.validate_card_details()) {
					if (!StoreController.amount.equals(new Currency(0.0f))) {
						StoreController.card_payment_error_label.setText("");
						try {
							StoreController.checkout("card");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						StoreController.card_payment_error_label.setText("Basket is empty");
					}
				} else {
					StoreController.card_payment_error_label.setText("Invalid Card Details");
				}
			}
		});	
		// clear basket after clicking this
		StoreController.clear_basket.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				StoreController.clear_basket();
			}
		});
		// add item to database
		// check inputs are valid
		// submit inputs as new database entry
		StoreController.add_item_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent button_clicked) {
				if (add_product_input_validated()) {
					String connectivity = "";
					String additional_information = "";
					String device_name = "";
					Integer barcode = Integer.parseInt(StoreController.barcode_textfield.getText());
					String brand = StoreController.add_brand_textfield.getText();
					String colour = StoreController.colour_textfield.getText();
					String type = StoreController.device_type_textfield.getText();
					if (StoreController.wired_radio_button.isSelected()) {
						connectivity = "wired";
					} else if (StoreController.wireless_radio_button.isSelected()) {
						connectivity = "wireless";
					}
					Integer quantity = StoreController.quantity_spinner.getValue();
					Float original_cost = Float.parseFloat(StoreController.original_cost_textfield.getText());
					Float retail_price = Float.parseFloat(StoreController.retail_price_textfield.getText());
					if(StoreController.keyboard_radio_button.isSelected()) {
						device_name = "keyboard";
						if (StoreController.uk_layout_radio_button.isSelected()) {
							additional_information = "UK";
						} else if (StoreController.us_layout_radio_button.isSelected()) {
							additional_information = "US";
						}
					} else if (StoreController.mouse_radio_button.isSelected()) {
						device_name = "mouse";
						 additional_information = String.valueOf(StoreController.number_of_keys_spinner.getValue());
					}
					
					try {
						FileReader.add_product_to_database(barcode, device_name, type, brand, colour, connectivity, quantity, original_cost, retail_price, additional_information);
						StoreController.populate_store_tableview(num_keys_query);
						StoreController.set_item_quantities();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					//
				}
			}


		});
	}
	//-----------------------------------------------------------------------------------------------------//
	// check add_product inputs are valid
	@SuppressWarnings("unused")
	private static Boolean add_product_input_validated() {
		Integer barcode = 0;
		Float original_cost = 0f;
		Float retail_price = 0f;
		try {
			barcode = Integer.parseInt(StoreController.barcode_textfield.getText());
		} catch (Exception e) {
			StoreController.add_item_confirmation_label.setText("Barcode must be an integer");
			return false;
		}
		if (String.valueOf(barcode).length() < 6) {
			StoreController.add_item_confirmation_label.setText("Barcode must be 6 digits");
			return false;
		}
		
		for (Product product: stock_info) {
			if (product.getBarcode().equals(barcode)) {
				StoreController.add_item_confirmation_label.setText("Product already exists with this barcode");
				return false;
			}
		}
		
		if ((StoreController.add_brand_textfield.getText() == null) ||  (StoreController.add_brand_textfield.getText().equals(""))) {
			StoreController.add_item_confirmation_label.setText("Brand is missing");
			return false;
		}
		
		if ((StoreController.colour_textfield.getText() == null) ||  (StoreController.colour_textfield.getText().equals(""))) {
			StoreController.add_item_confirmation_label.setText("Colour is missing");
			return false;
		}
		
		if ((StoreController.device_type_textfield.getText() == null) ||  (StoreController.device_type_textfield.getText().equals(""))) {
			StoreController.add_item_confirmation_label.setText("Device type is missing (e.g. gaming");
			return false;
		}
		
		if (!StoreController.wired_radio_button.isSelected() && !StoreController.wireless_radio_button.isSelected()) {
			StoreController.add_item_confirmation_label.setText("Connectivity is unknown");
			return false;
		}
		try {
			original_cost = Float.parseFloat(StoreController.original_cost_textfield.getText());
		} catch(Exception e) {
			StoreController.add_item_confirmation_label.setText("Original cost must be a number");
			return false;
		}
		try {
			retail_price = Float.parseFloat(StoreController.retail_price_textfield.getText());
		} catch(Exception e) {
			StoreController.add_item_confirmation_label.setText("Retail price must be a number");
			return false;
		}	
		if(StoreController.keyboard_radio_button.isSelected()) {
			if (!StoreController.uk_layout_radio_button.isSelected() && !StoreController.us_layout_radio_button.isSelected()) {
				StoreController.add_item_confirmation_label.setText("Please select a layout");
				return false;
			}
		}
		StoreController.add_item_confirmation_label.setText("Item successfully added");
		return true;
		
		
		
		
	}
	//-----------------------------------------------------------------------------------------------------//
	
	// clear basket
	private static void clear_basket() {
		StoreController.quantity_error_message.setText("");
		Integer num_items_in_basket = StoreController.basket_items.size();
		for (Integer i = 0; i < num_items_in_basket; i++ ) {
			Product selected_item = StoreController.basket_items.get(0);
		    StoreController.basket_items.remove(0);
	
		    StoreController.basket.getItems().remove(selected_item);
		    StoreController.item_quantities.put(selected_item, (StoreController.item_quantities.get(selected_item))+1);
		}
		
		update_total_cost();
		
	}
	
	//-----------------------------------------------------------------------------------------------------//
	// populate store with items from database
	private static void populate_store_tableview(Integer numkeys) throws FileNotFoundException {

		StoreController.store_tableview.getItems().clear();
		StoreController.stock_info.clear();
		String filter_method;
		if ((brand_textfield.getText() == null) || brand_textfield.getText().equals("")) {
			filter_method = "filterbykey";
		} else if(numkeys == 0) {
			filter_method = "filterbybrand";
		} else {
			filter_method = "filterbyboth";
		}
		
		String[] column_getters = {
			"barcode", "device_name", "device_type", "brand", "colour", "connectivity",
			"quantity", "in_stock", "original_cost", "retail_price", "additional_information"
		};

		for (Integer i = 0; i < column_getters.length; i++) {
			StoreController.store_tableview.getColumns().get(i).setCellValueFactory(new PropertyValueFactory<>(column_getters[i]));
		}
		StoreController.store_tableview.getColumns().get(10).setSortable(false);
		for (Product product : FileReader.return_stock_information()) {
			if (filter_method.equals("filterbykey")) {
				if (0 == numkeys || product.getAdditional_information().equals(numkeys+" keys")) {
					StoreController.stock_info.add(product);
				}
			} else if (filter_method.equals("filterbybrand")) {
				if (product.getBrand().toLowerCase().equals(StoreController.brand_textfield.getText().toLowerCase())) {
					StoreController.stock_info.add(product);
				}
			} else if (filter_method.equals("filterbyboth")) {
				if ((0 == numkeys || product.getAdditional_information().equals(numkeys+" keys")) && (product.getBrand().toLowerCase().equals(StoreController.brand_textfield.getText().toLowerCase()))) {
					StoreController.stock_info.add(product);
				}
			}
		}
		StoreController.store_tableview.getItems().addAll(StoreController.stock_info);
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	// set how many of that item you can add to basket, separate quantity value that changes as you add and remove from basket
	private static void set_item_quantities() {
		for(Product item : StoreController.stock_info) {
			StoreController.item_quantities.put(item, item.getQuantity());
		}
	}
	//-----------------------------------------------------------------------------------------------------//
	// setup basket tableview
	private static void setup_basket_tableview( ) {
		StoreController.basket.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("barcode"));
		StoreController.basket.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("retail_price"));
	}
	// hide original cost from customers
	public static void update_store_according_permissions() {
		if (SceneController.active_user.getPermission().equals(Permission.CUSTOMER)) {
			StoreController.store_tableview.getColumns().get(8).setVisible(false);
			StoreController.tab_pane.getTabs().get(2).setDisable(false);
			StoreController.tab_pane.getTabs().get(1).setDisable(false);;
			StoreController.tab_pane.getTabs().get(3).setDisable(true);
			StoreController.add_to_basket.setDisable(false);
		} else {
			StoreController.store_tableview.getColumns().get(8).setVisible(true);
			StoreController.tab_pane.getTabs().get(2).setDisable(true);
			StoreController.tab_pane.getTabs().get(1).setDisable(true);
			StoreController.tab_pane.getTabs().get(3).setDisable(false);
			StoreController.add_to_basket.setDisable(true);
		}
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	// keep track of users bill
	public static void update_total_cost() {
		Currency total_cost = new Currency(0.0f);
		Label display_total= (Label) StoreController.store_scene.lookup("#total_cost");
		for (Product product : StoreController.basket_items) {
			total_cost= total_cost.add(product.getRetail_price());
		}
		
		StoreController.amount = total_cost;
		display_total.setText("Total Cost: "+total_cost.toString());
	}
	//-----------------------------------------------------------------------------------------------------//
	
	// hide paypal and credit card payment pane
	public static void hide_checkout_panes() {
		StoreController.card_payment_pane.setVisible(false);
		StoreController.paypal_pane.setVisible(false);
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	public static boolean is_numeric(String string) { 
		  try {  
		    Double.parseDouble(string);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	//-----------------------------------------------------------------------------------------------------//	
	public static void show_card_payment_pane() {
		StoreController.paypal_pane.setVisible(false);
		StoreController.card_payment_pane.setVisible(true);
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	public static void show_paypal_payment_pane() {
		StoreController.card_payment_pane.setVisible(false);
		StoreController.paypal_pane.setVisible(true);
	}
	
	//-----------------------------------------------------------------------------------------------------//
	// check paypal payment input is valid
	public static boolean validate_email() {
		String email_address = StoreController.email_address_textfield.getText();
		// check email contains @ and .
		
		if (email_address == "") {
			return false;
		} else if ((email_address.indexOf("@") == -1) ||  (email_address.indexOf(".") == -1)) {
			return false;
			// at least 1 character between @ and ., @ must come first
		} else if(email_address.indexOf(".") - email_address.indexOf("@") < 2) {
			return false;
			//at least 1 char before @ => @ is not first char in email
		} else if(email_address.indexOf("@") == 0) {
			return false;
			// check that . isn't the last char => at least one char after .
		} else if(email_address.indexOf(".") == email_address.length()-1){
			return false;
		} else {
			return true;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------//
	public static boolean validate_card_details() {
		String card_number = StoreController.card_number_textfield.getText();
		String secuirity_code = StoreController.secuirity_code_textfield.getText();
		
		
		if((card_number == "") || (secuirity_code == "")) {
			return false;
		} else if (!is_numeric(card_number)) {
			return false;
		} else {
			Integer card_number_int = Integer.parseInt(card_number);
			Integer secuirity_code_int = Integer.parseInt(secuirity_code);
			if ((card_number_int >1000000) || (secuirity_code_int > 1000) || (card_number_int <100000) || (secuirity_code_int < 100)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------//
	// checkout
	public static void checkout(String payment_method) throws FileNotFoundException {
		StoreController.update_total_cost();
		Currency total_cost = StoreController.amount;
		String address = SceneController.active_user.getAddress();
		
		if (payment_method == "paypal") {
			try {
				FileReader.update_database_using_basket(StoreController.item_quantities);
				StoreController.populate_store_tableview(num_keys_query);
			    StoreController.basket.getItems().clear();
			    StoreController.basket_items.clear();
			    StoreController.set_item_quantities();
			    StoreController.amount = new Currency(0.0f);
			    Label display_total= (Label) StoreController.store_scene.lookup("#total_cost");
			    display_total.setText("Total Cost: "+amount.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String output = String.format("%s paid using PayPal, and the delivery address is %s", total_cost, address);
			StoreController.email_checkout_confirmation_label.setText(output);
		} else if (payment_method == "card") {
			try {
				FileReader.update_database_using_basket(StoreController.item_quantities);
				StoreController.populate_store_tableview(num_keys_query);
			    StoreController.basket.getItems().clear();
			    StoreController.basket_items.clear();
			    StoreController.set_item_quantities();
			    StoreController.amount = new Currency(0.0f);
			    Label display_total= (Label) StoreController.store_scene.lookup("#total_cost");
			    display_total.setText("Total Cost: "+amount.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String output = String.format("%s paid using Credit Card, and the delivery address is %s", total_cost, address);
			StoreController.card_payment_confirmation_label.setText(output);
		}
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	@SuppressWarnings("unchecked")
	public static void setup(Scene new_scene) throws FileNotFoundException {
		StoreController.store_scene = new_scene;
		
		StoreController.tab_pane=(TabPane) store_scene.lookup("#tab_pane");
		
		StoreController.basket=(TableView<Product>) store_scene.lookup("#basket");
		
		StoreController.store_tableview = (TableView<Product>) store_scene.lookup("#store_tableview");
		
		StoreController.spinner = ((Spinner<Integer>)store_scene.lookup("#sort_by_keys"));
		
		StoreController.brand_textfield = (TextField)  store_scene.lookup("#brand_textfield");
		
		StoreController.sign_out1 = (Button) store_scene.lookup("#sign_out1");
		
		StoreController.sign_out2 = (Button) store_scene.lookup("#sign_out2");
		
		StoreController.sign_out3 = (Button) store_scene.lookup("#sign_out3");
		
		StoreController.sign_out4 = (Button) store_scene.lookup("#sign_out4");
		
		StoreController.clear_basket = (Button) store_scene.lookup("#clear_basket");
		
		StoreController.add_to_basket = (Button) store_scene.lookup("#add_to_basket");

		StoreController.remove_from_basket = (Button) store_scene.lookup("#remove_item");
		
		StoreController.quantity_error_message = (Label) store_scene.lookup("#quantity_error_message");
		
		StoreController.card_payment_pane = (Pane) store_scene.lookup("#card_payment_pane");
		
		StoreController.paypal_pane = (Pane) store_scene.lookup("#paypal_pane");
		
		StoreController.credit_card_radio_button = (RadioButton) store_scene.lookup("#credit_card_radio_button");
		
		StoreController.paypal_radio_button = (RadioButton) store_scene.lookup("#paypal_radio_button");
		
		StoreController.paypal_radio_button = (RadioButton) store_scene.lookup("#paypal_radio_button");
		
		StoreController.mouse_radio_button = (RadioButton) store_scene.lookup("#mouse_radio_button");
		
		StoreController.keyboard_radio_button = (RadioButton) store_scene.lookup("#keyboard_radio_button");
		
		StoreController.wireless_radio_button = (RadioButton) store_scene.lookup("#wireless_radio_button"); 
		
		StoreController.wired_radio_button = (RadioButton) store_scene.lookup("#wired_radio_button");

		StoreController.card_checkout_button = (Button) store_scene.lookup("#card_checkout_button");
		
		StoreController.paypal_checkout_button = (Button) store_scene.lookup("#paypal_checkout_button");
		
		StoreController.card_number_textfield = (TextField) store_scene.lookup("#card_number_textfield");
		
		StoreController.secuirity_code_textfield = (TextField) store_scene.lookup("#secuirity_code_textfield");
		
		StoreController.email_address_textfield = (TextField) store_scene.lookup("#email_address_textfield");
		
		StoreController.card_payment_error_label = (Label) store_scene.lookup("#card_payment_error_label");

		StoreController.card_payment_confirmation_label = (Label) store_scene.lookup("#card_payment_confirmation_label");
		
		StoreController.paypal_error_label = (Label) store_scene.lookup("#paypal_error_label");
		
		StoreController.email_checkout_confirmation_label = (Label) store_scene.lookup("#email_checkout_confirmation_label");
		
		StoreController.mouse_radio_button = (RadioButton) store_scene.lookup("#mouse_radio_button"); 
		
		StoreController.keyboard_radio_button = (RadioButton) store_scene.lookup("#keyboard_radio_button"); 
		
		StoreController.wireless_radio_button = (RadioButton) store_scene.lookup("#wireless_radio_button"); 
		
		StoreController.wired_radio_button = (RadioButton) store_scene.lookup("#wired_radio_button"); 

		StoreController.us_layout_radio_button = (RadioButton) store_scene.lookup("#us_layout_radio_button");
		
		StoreController.uk_layout_radio_button = (RadioButton) store_scene.lookup("#uk_layout_radio_button");
		
		StoreController.barcode_textfield = (TextField) store_scene.lookup("#barcode_textfield");
		
		StoreController.add_brand_textfield = (TextField) store_scene.lookup("#add_brand_textfield");
		
		StoreController.colour_textfield = (TextField) store_scene.lookup("#colour_textfield");
		
		StoreController.original_cost_textfield = (TextField) store_scene.lookup("#original_cost_textfield");
		
		StoreController.retail_price_textfield = (TextField) store_scene.lookup("#retail_price_textfield");
		
		StoreController.device_type_textfield = (TextField) store_scene.lookup("#device_type_textfield");
		
		StoreController.additional_information_label = (Label) store_scene.lookup("#additional_information_label");
		
		StoreController.add_item_confirmation_label = (Label) store_scene.lookup("#add_item_confirmation_label");
		
		StoreController.quantity_spinner = ((Spinner<Integer>)store_scene.lookup("#quantity_spinner"));
		
		StoreController.number_of_keys_spinner = ((Spinner<Integer>)store_scene.lookup("#number_of_keys_spinner"));
		
		StoreController.add_item_button = (Button) store_scene.lookup("#add_item_button");
		
		StoreController.hide_checkout_panes();
		
		StoreController.populate_store_tableview(0);
		
		StoreController.setup_basket_tableview();
		
		StoreController.set_item_quantities();
		// 0 default value -> no sort
		StoreController.spinner.getValueFactory().setValue(0);
		
		StoreController.quantity_spinner.getValueFactory().setValue(0);
		
		StoreController.number_of_keys_spinner.getValueFactory().setValue(0);
		
		StoreController.add_listeners();
		
		StoreController.amount = new Currency(0.0f);
		
    	StoreController.uk_layout_radio_button.setVisible(false);
    	StoreController.us_layout_radio_button.setVisible(false);
    	StoreController.mouse_radio_button.selectedProperty().set(true);
    	StoreController.number_of_keys_spinner.setVisible(true);
		
	}
}
