package cz.filipekt.jdcv.gui_logic;

import cz.filipekt.jdcv.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Listener for the {@link Event} that user clicks the "show controls bar" option in the menu.
 * Makes sure that the controls bar is shown/hidden appropriately.
 * 
 * @author Tomas Filipek <tom.filipek@seznam.cz>
 */
public class ControlsBarItemHandler implements EventHandler<ActionEvent> {
	
	/**
	 * Marks whether the controls bar is shown
	 */
	private boolean barIsShown = true;
	
	/**
	 * The item in the "view" menu allowing for showing/hiding the controls bar
	 */
	private final MenuItem controlsBarItem;
	
	/**
	 * The "checked" image shown next to the {@link ControlsBarItemHandler#controlsBarItem} when selected
	 */
	private final ImageView checkBoxImage;
	
	/**
	 * Context in which this handler is called
	 */
	private final Visualizer visualizer;

	/**
	 * @param controlsBarItem The item in the "view" menu allowing for showing/hiding the controls bar
	 * @param checkBoxImage The "checked" image shown next to the {@link ControlsBarItemHandler#controlsBarItem} when selected
	 * @param visualizer Context in which this handler is called
	 */
	public ControlsBarItemHandler(MenuItem controlsBarItem, ImageView checkBoxImage, Visualizer visualizer) {
		this.controlsBarItem = controlsBarItem;
		this.checkBoxImage = checkBoxImage;
		this.visualizer = visualizer;
	}

	/**
	 * Called whenever the user clicks the "show controls bar" option in the menu. 
	 * Makes sure that the controls bar is shown/hidden appropriately.
	 */
	@Override
	public void handle(ActionEvent arg0) {
		HBox controlsBar = visualizer.getControlsBar();
		VBox vbox = visualizer.getRootPane();
		if (barIsShown){
			if (vbox.getChildren().contains(controlsBar)){
				vbox.getChildren().remove(controlsBar);
				barIsShown = false;
				controlsBarItem.setGraphic(null);
			}
		} else {
			if (!vbox.getChildren().contains(controlsBar)){
				vbox.getChildren().add(controlsBar);
				barIsShown = true;
				controlsBarItem.setGraphic(checkBoxImage);
			}
		}
	}
}