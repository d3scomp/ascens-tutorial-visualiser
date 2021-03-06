package cz.filipekt.jdcv.gui_logic;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import cz.filipekt.jdcv.Visualizer;

/**
 * Listener for the {@link Event} that user clicks the "close this scene" option in the menu.
 * Makes sure that the current visualization is stopped and the map scene is closed.
 * 
 * @author Tomas Filipek <tom.filipek@seznam.cz>
 */
public class CloseSceneHandler implements EventHandler<ActionEvent>{
	
	/**
	 * The item in the "file" menu allowing for importing a new scene.
	 */
	private final MenuItem importSceneItem;
	
	/**
	 * The item in the "file" menu allowing for closing the current scene.
	 * This is the item to which this handler is assigned.
	 */
	private final MenuItem closeThisSceneItem;
	
	/**
	 * Context in which this handler is called
	 */
	private final Visualizer visualizer;

	/**
	 * @param importSceneItem The item in the "file" menu allowing for importing a new scene.
	 * @param closeThisSceneItem The item in the "file" menu allowing for closing the current scene.
	 * @param visualizer Context in which this handler is called
	 */
	public CloseSceneHandler(MenuItem importSceneItem, MenuItem closeThisSceneItem, Visualizer visualizer) {
		this.importSceneItem = importSceneItem;
		this.closeThisSceneItem = closeThisSceneItem;
		this.visualizer = visualizer;
	}

	/**
	 * Called whenever the user clicks the "close this scene" option in the menu.
	 * Makes sure that the current visualization is stopped and the map scene is closed.
	 */
	@Override
	public void handle(ActionEvent arg0) {
		visualizer.setScene(null, 0, 0);
		visualizer.setGraphicsColumnDefaults();
		closeThisSceneItem.setDisable(true);
		importSceneItem.setDisable(false);	
	}
	
}