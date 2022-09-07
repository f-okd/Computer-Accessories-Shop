package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import backend.FileReader;
import backend.User;
import backend.User.Permission;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneController extends Application {
	public static Stage stage;
	public static enum scenes {LOGIN, STORE};
	public static User active_user;
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		LoginController.setup(FXMLLoader.load(SceneController.class.getResource("Login.fxml")));
		StoreController.setup(FXMLLoader.load(SceneController.class.getResource("Store.fxml")));
		SceneController.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		SceneController.stage = arg0;
		SceneController.switch_scene(scenes.LOGIN);
		arg0.show();
		
	}
	
	// switch scene
	public static void switch_scene(scenes scene) throws IOException {
		Scene new_scene = null;
		if (scene.equals(scenes.LOGIN)) {
			new_scene = LoginController.getScene();
		} else if (scene.equals(scenes.STORE)) {
			new_scene = StoreController.getScene();
		}
		SceneController.stage.setScene(new_scene);
	}

}
