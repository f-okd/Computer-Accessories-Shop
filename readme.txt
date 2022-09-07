 Two types of roles were created: "Admin" and "Customer".  Once the user logs into the system by choosing from the list of available usernames, they should be able to access the different functionalities according to their roles. This had full GUI functionality using JavaFX and Scenebuilder.
 
 ----------------------------------------------------- ----------------------------------------------------- -----------------------------------------------------

You need to have JavaFX sdk installed.
VM Argument template:
--module-path "\path\to\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml
Change path to be relevant to your javaFX sdk install location.

That needs to be changed for the run configurations for SceneController. Additionally needs to be added to your command prompt entry.

CMD prompt for me: java --module-path "C:\JavaFX\javafx-sdk-18.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar cas.jar

