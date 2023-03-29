import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.HashMap;

public class JavaFXTemplate extends Application {

	TextField s1,s2,s3,s4, c1;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	//Client clientConnection;

	ListView<String> listItems, listItems2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		primaryStage.setTitle("Server");


		Label l1 = new Label("Select Port");
		l1.setStyle("-fx-background-color: Black; -fx-border-width: 4px; -fx-font-size: 3em; -fx-text-fill: White");
		s1 = new TextField("Input Port");
		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 300px");
		this.serverChoice.setStyle("-fx-pref-height: 50px");

			this.serverChoice.setOnAction(e->{ primaryStage.setScene(sceneMap.get("server"));
				primaryStage.setTitle("This is the Server");
				int port = Integer.parseInt(s1.getText());
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						listItems.getItems().add(data.toString());
					});

				}, port);

			});


		startPane = new BorderPane();
		startPane.minWidth(700);
		startPane.minHeight(700);
		startPane.setPadding(new Insets(70));
		VBox vbox = new VBox(l1,s1,serverChoice);
		startPane.setCenter(vbox);
		startPane.setStyle("-fx-background-color: blue");
		vbox.setAlignment(Pos.CENTER);

		startScene = new Scene(startPane, 800,800);

		listItems = new ListView<String>();
		listItems2 = new ListView<String>();

		sceneMap = new HashMap<String, Scene>();

		sceneMap.put("server",  createServerGui());

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.setScene(startScene);
		primaryStage.show();
	}

	public Scene createServerGui() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: aqua");
		Label l3 = new Label("Game Information");
		l3.setStyle("-fx-background-color: Blue; -fx-border-width: 4px; -fx-font-size: 3em; -fx-text-fill: White");
		VBox vbox = new VBox(l3,listItems);
		pane.setCenter(vbox);
		vbox.setAlignment(Pos.CENTER);
		return new Scene(pane, 500, 400);


	}

}
