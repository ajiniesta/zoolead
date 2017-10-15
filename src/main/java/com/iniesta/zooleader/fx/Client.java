package com.iniesta.zooleader.fx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Client extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parameters parameters = getParameters();
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Client.fxml"));
		final ClientController controller = new ClientController(parameters.getNamed().get("connect"),parameters.getNamed().get("leaderpath"));
		loader.setController(controller);
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Client");
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				controller.close();
			}
		});
		primaryStage.show();
	}

}
