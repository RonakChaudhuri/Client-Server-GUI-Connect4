import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import java.awt.event.MouseEvent;
import java.util.HashMap;

public class JavaFXTemplate extends Application {

	TextField c1;
	Button clientChoice,b1,exitButton,playAgainButton;
	private EventHandler<ActionEvent> myHandler;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Client clientConnection;
	int sceneCount = 1;

	ListView<String> listItems, listItems2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Client");

		myHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//System.out.println("button pressed: " + ((Button)e.getSource()).getText());
				GameButton b1 = (GameButton)e.getSource();
				if(clientConnection.getC4Info().getTurn()){
					if(clientConnection.getC4Info().checkBoard(b1.getRow(), b1.getCol())){
						clientConnection.output("Space occupied. Try Again!");
					}
					else {
						if(clientConnection.getC4Info().getP1Turn()) {
							int i = 0;
							int j = 0;
							if(clientConnection.getC4Info().getP2Plays().length() > 0) {
								i = Integer.parseInt(clientConnection.getC4Info().getP2Plays().substring(0,1));
								j = Integer.parseInt(clientConnection.getC4Info().getP2Plays().substring(1,2));
								if(grid.contains(i,j)) {
									grid.getChildren().get((i*7)+j).setStyle("-fx-background-color: Blue;");
									grid.getChildren().get((i*7)+j).setDisable(true);
								}
							}

							b1.setStyle("-fx-background-color: Red;");
						}
						if(clientConnection.getC4Info().getP2Turn()){
							int i = Integer.parseInt(clientConnection.getC4Info().getP1Plays().substring(0,1));
							int j = Integer.parseInt(clientConnection.getC4Info().getP1Plays().substring(1,2));
							if(grid.contains(i,j)) {
								grid.getChildren().get((i*7)+j).setStyle("-fx-background-color: Red;");
								grid.getChildren().get((i*7)+j).setDisable(true);
							}
							b1.setStyle("-fx-background-color: Blue;");

						}
						b1.setDisable(true);
						clientConnection.send(b1.getRow().toString() + b1.getCol().toString());
					}
				}
				else{
					clientConnection.output("NOT YOUR TURN!");
				}
			}
		};

		Label l1 = new Label("Select Port");
		l1.setStyle("-fx-background-color: Black; -fx-border-width: 4px; -fx-font-size: 3em; -fx-text-fill: White");
		c1 = new TextField("Input Port");

		Label l2 = new Label("Select IP");
		l2.setStyle("-fx-background-color: Black; -fx-border-width: 4px; -fx-font-size: 3em; -fx-text-fill: White");
		ObservableList<String> options2 =
				FXCollections.observableArrayList(
						"Local Host"
				);
		final ComboBox comboBox2 = new ComboBox(options2);
		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 300px");
		this.clientChoice.setStyle("-fx-pref-height: 50px");

		this.clientChoice.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
			primaryStage.setTitle("This is a client");
			clientConnection = new Client(data->{
				Platform.runLater(()->{listItems2.getItems().add(data.toString());
				});
			}, Integer.parseInt(c1.getText()));
			clientConnection.start();
		});

		startPane = new BorderPane();
		startPane.minWidth(700);
		startPane.minHeight(700);
		startPane.setPadding(new Insets(70));
		VBox vbox = new VBox(l1,c1,l2,comboBox2,clientChoice);
		startPane.setCenter(vbox);
		startPane.setStyle("-fx-background-color: red");
		vbox.setAlignment(Pos.CENTER);

		startScene = new Scene(startPane, 800,800);

		listItems2 = new ListView<String>();

		listItems2.setOnMouseMoved(e->{if(clientConnection.getC4Info().getWinner() != 0) {
			primaryStage.setScene(sceneMap.get("endScene"));
		}});



		sceneMap = new HashMap<String, Scene>();

		sceneMap.put("client",  createClientGui());
		sceneMap.put("endScene",createEndScene());

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		playAgainButton.setOnAction(e-> {sceneMap.put("client"+sceneCount,createClientGui());
			primaryStage.setScene(sceneMap.get("client"+sceneCount));
			primaryStage.setTitle("This is a client");
			clientConnection = new Client(data->{
				Platform.runLater(()->{listItems2.getItems().add(data.toString());
				});
			},Integer.parseInt(c1.getText()));
			clientConnection.start();
			sceneCount++;
		});

		grid.setOnMouseMoved(ev->{if(clientConnection.getC4Info().getWinner() != 0) {
			primaryStage.setScene(sceneMap.get("endScene"));
		}});

		primaryStage.setScene(startScene);
		primaryStage.show();

	}

	public Scene createClientGui() {
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		addGrid(grid); //populate the GridPane with buttons
		grid.setPrefSize(500,400);
		clientBox = new VBox(grid,listItems2);
		clientBox.setStyle("-fx-background-color: green");
		return new Scene(clientBox, 600, 500);
	}

	public void addGrid(GridPane grid) {
		for(int i = 0; i<6; i++) {
			for(int j = 0; j<7; j++) {
				GameButton b1 = new GameButton(i,j);
				b1.setOnAction(myHandler);
				grid.add(b1,j,i);
			}
		}
	}

	public Scene createEndScene() {
		exitButton = new Button("EXIT");
		exitButton.setOnAction(e->{Platform.exit();});
		exitButton.setStyle("-fx-background-color: black; -fx-border-color: #243000; -fx-border-width: 4px; -fx-font-size: 2em;");
		playAgainButton = new Button("Play Again");
		playAgainButton.setStyle("-fx-background-color: black; -fx-border-color: #243000; -fx-border-width: 4px; -fx-font-size: 2em;");
		HBox endBox = new HBox(exitButton,playAgainButton);
		endBox.setStyle("-fx-background-color: blue");
		endBox.setAlignment(Pos.CENTER);
		return new Scene(endBox, 600, 500);
	}

}
