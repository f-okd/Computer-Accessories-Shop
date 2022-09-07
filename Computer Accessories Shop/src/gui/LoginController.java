package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import backend.FileReader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import backend.User;
import gui.SceneController.scenes;

public class LoginController {
	private static Scene login_scene;
	
	//return current scene
	public static Scene getScene() {
		return login_scene;
	}
	
	// setup function prepares the scene
	public static void setup(Scene scene) throws FileNotFoundException {
		LoginController.login_scene = scene;
		
		@SuppressWarnings("unchecked")
		ComboBox<String> user_select = (ComboBox<String>)login_scene.lookup("#user_select");
		Object[] users = FileReader.return_accounts().toArray();
		String[] usernames = new String[users.length];
		
		for (Integer i = 0; i < users.length; i++) {
			//TO-DO SHOW PERMISSIONS ON LOGIN
			usernames[i] = ((User)users[i]).getUsername();
		}
		user_select.setItems(FXCollections.observableArrayList(usernames));
	}
	
	// open store scene
    @SuppressWarnings("unchecked")
	@FXML
    void openStore(ActionEvent event) {
		try {
			ComboBox<String> user_select = (ComboBox<String>)login_scene.lookup("#user_select");
			String userinput = user_select.getValue();
			Integer user_index = user_select.getSelectionModel().getSelectedIndex();
			Label error_message = (Label) login_scene.lookup("#error_message1");
			// input validation
			if (userinput != null) {
				SceneController.active_user = FileReader.return_accounts().get(user_index);
				StoreController.update_store_according_permissions();
				SceneController.switch_scene(scenes.STORE);
				error_message.setVisible(false);
			} else {
				error_message.setVisible(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
