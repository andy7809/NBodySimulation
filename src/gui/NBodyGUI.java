package gui;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import lib.StdDraw;
import lib.WildCardFilter;
import nBodySim.Body;
import nBodySim.Universe;

/**
 * The associated GUI of the NBody project
 * @author andre
 *
 */
public class NBodyGUI extends Application {
	/**
	 * A dark grey background for setting the background of nodes
	 */
	protected final Background DARKGREYBG = new Background(new BackgroundFill(Color.DARKGRAY, 
			new CornerRadii(1), new Insets(0, 0, 0, 0)));
	/**
	 * The default spacing for styling spaced nodes
	 */
	protected final double SPACINGSIZE = 5;
	
	/**
	 * The common inset for styling, used for all padding of root/parent nodes
	 */
	protected final Insets ELEMENTINSETS = new Insets(5, 5, 5, 5);
	/**
	 * The relative path of all Simulations in the project
	 */
	protected static final String simPath = "src\\data\\";
	
	/**
	 * Combo box cell factory - display cells in the combobox as the filename of the associated files
	 */
	protected static final Callback<ListView<File>, ListCell<File>> comboBoxCellFactory = new Callback<ListView<File>, ListCell<File>>() {
		public ListCell<File> call(ListView<File> p) {
	         return new ListCell<File>() {	             
	             @Override 
	             protected void updateItem(File item, boolean empty) {
	                 super.updateItem(item, empty);
	                 
	                 if (item == null || empty) {
	                     setText(null);
	                 } else {
	                     setText(item.getName());
	                 }
	            }
	       };
	   }
	};
	
	/**
	 * The universe to simulate
	 */
	protected Universe toSim = null;
	
	/**
	 * The gui's primary stage
	 */
	protected Stage primaryStage;
	
	/**
	 * height of window
	 */
	protected int height = 350;
	/**
	 * width of window
	 */
	protected int width = 450;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		String title = "nBody Simulator";
		primaryStage.setTitle(title);
		primaryStage.setScene(getMainScene());
		primaryStage.show();

	}
	
	/**
	 * Get the main window of the GUI
	 * @return the main scene of the gui
	 */
	public Scene getMainScene() {
		GridPane sceneBox = new GridPane();
		//Style the window here
		sceneBox.setPadding(ELEMENTINSETS);
		sceneBox.setAlignment(Pos.CENTER);
		sceneBox.setHgap(SPACINGSIZE);
		sceneBox.setVgap(SPACINGSIZE);
		sceneBox.setBackground(DARKGREYBG);
		
		Label deltaTime = new Label("Delta time:");
		TextField inptDeltaTime = new TextField();
		sceneBox.add(deltaTime, 0, 0);
		sceneBox.add(inptDeltaTime, 1, 0);
		
		Label endTime = new Label("End time:");
		TextField inptEndTime = new TextField();
		sceneBox.add(endTime, 0, 1);
		sceneBox.add(inptEndTime, 1, 1);
		
		Label simName = new Label("Simulation Type:");
		ComboBox<File> inptSimName = new ComboBox<File>();
		//Add all of the simulations (as files) to the combobox
		inptSimName.getItems().addAll(getAllSims(simPath));
		inptSimName.setCellFactory(comboBoxCellFactory);
		sceneBox.add(simName, 0, 2);
		sceneBox.add(inptSimName, 1, 2);
		
		Button btnStart = new Button();
		btnStart.setText("Start Simulation!");
		//On button press
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Enable the double buffer
				StdDraw.enableDoubleBuffering();
				try {
					//Get the selected univers
					toSim = getUniverseFromFile(simPath + inptSimName.getValue().getName());
				} catch(Exception e) {
					//Incorporating this into the gui, safely, is on the to-do list
					System.out.println("Error!");
				}
				//Interpret the inputted values
				toSim.setMaxTime(Double.parseDouble(inptEndTime.getText()));
				//Run the sim
				while(toSim.continueSimulation()) {
					toSim.update(Double.parseDouble(inptDeltaTime.getText()));
				}
				//Reset the scene
				primaryStage.setScene(getMainScene());
			}
		});
		sceneBox.add(btnStart, 1, 3);
		
		//Add the universe text update, but nothing if not yet initialized
		Text update;
		if(toSim == null) {
			update = new Text();
		} else {
			update = new Text(toSim.toString());
		}
		
		sceneBox.add(update, 0, 4);
		GridPane.setColumnSpan(update, GridPane.REMAINING);
		
		return new Scene(sceneBox, width, height);
	}
	
	/**
	 * Get all text documents from a folder as an array
	 * This solution is from https://stackoverflow.com/questions/794381/how-to-find-files-that-match-a-wildcard-string-in-java/4456735
	 * @param filePath the file path of the folder to get all files from
	 */
	private static File[] getAllSims(String filePath) {
		File dir = new File(filePath);
		//This filter will only accept files that contain ".txt"
		FileFilter txtFilter = new WildCardFilter(".txt");
		File[] fileList = dir.listFiles(txtFilter);
		return fileList;
	}
	
	/**
	 * Get a universe from a text file path
	 * @param filePath The file to create from
	 * @return the universe represented by the file
	 * @throws FileNotFoundException if the file can not be found
	 */
	public static Universe getUniverseFromFile(String filePath) throws FileNotFoundException {
		File universeFile = new File(filePath);
		Scanner in = new Scanner(universeFile);
		int numberOfBodies = Integer.parseInt(in.nextLine().trim());
		double radius = Double.parseDouble(in.nextLine().trim());
		Universe retUniverse = new Universe(radius);
		int counter = 0;
		while(in.hasNextLine() && counter < numberOfBodies) {
			String nextLine = in.nextLine();
			if(!nextLine.trim().equals("")) {
				Body nextBody = Body.getBodyFromString(nextLine);
				retUniverse.addBody(nextBody);
				counter++;
			}
		}
		in.close();
		return retUniverse;
	}
}
