package com.iniesta.zooleader.fx;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXAppContext;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class ClientController {

	private String connectionString;

	private String zNodLeader;

	private String name;
	
	private CuratorFramework client;
	private FxLeaderSelector sel;
	
	public ClientController(String connectionString, String znodLeader) {
		this.connectionString = connectionString;
		this.zNodLeader = znodLeader;
		this.name = ""+System.currentTimeMillis();
	}
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label label;

	@FXML
    private Label msg;
	
	@FXML
	private Circle circle;

	@FXML
	void initialize() {
		assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'Client.fxml'.";
		assert circle != null : "fx:id=\"circle\" was not injected: check your FXML file 'Client.fxml'.";

		
		label.setText("Client # "+name);
		
		msg.setText(String.format("Connecting to %s%nZNode Lead Path:%s",connectionString, zNodLeader));
		
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						try {
						client = CuratorFrameworkFactory.newClient(connectionString,
								new ExponentialBackoffRetry(1000, 3));
						
						sel = new FxLeaderSelector(client, zNodLeader, name, circle);
						
						client.start();
						sel.start();
						}catch(Exception e) {
							e.printStackTrace();
							update("black");
						}
						return null;
					}
				};
			}
		};
		service.start();
	}

	public void update(final String color) {
		Platform.runLater(new Runnable() {
			public void run() {
				circle.setStyle(String.format("-fx-fill:%s;", color));
			}
		});
	}
	
	public void close() {
		CloseableUtils.closeQuietly(sel);
		CloseableUtils.closeQuietly(client);
	}

}
