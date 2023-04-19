package frc.robot.replay;

import java.io.File;
import java.util.List;

import hlib.frc.FRC2023Visualizer;
import hlib.frc.table.SubscriptionList;
import hlib.frc.table.SynchronizedReaderOperator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class VisualizerReplay extends FRC2023Visualizer {

	SynchronizedReaderOperator activeOperator = null;

	boolean stopped = false;

	public static void main(String[] args) {
		// launch(args);
		// launch("logs" + File.separator + "sample.csv");
		// launch("logs" + File.separator + "__2023-03-25_19-57.csv");
		launch("logs" + File.separator + "_2023-04-14_17-40.csv");
		// launch("logs" + File.separator + "2023-04-11_19-50.log");
		// launch("logs" + File.separator + "2023-03-12_22_06.csv", "logs" +
		// File.separator + "2023-03-12_22_06.csv");
	}

	/**
	 * Starts this {@code FRC2023Visualizer} using the specified {@code Stage}.
	 * 
	 * @param stage
	 *            a {@code Stage}
	 */
	@Override
	public void start(Stage stage) throws Exception {
		super.start(stage);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.out.println("stopping...");
				stopped = true;
				if (activeOperator != null)
					activeOperator.stop();
			}
		});
		Parameters parameters = getParameters();
		new Thread(() -> {
			for (String fileName : parameters.getRaw())
				if (!stopped)
					replay(fileName);
		}).start();
	}

	@Override
	protected void setup(MenuBar menuBar) {
		Menu m = new Menu("File");
		MenuItem menuItemOpen = new MenuItem("Open...");
		menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				List<File> list = fileChooser.showOpenMultipleDialog(null);
				if (list != null) {
					for (File file : list)
						if (!stopped)
							replay(file);
				}
			}
		});
		m.getItems().add(menuItemOpen);
		menuBar.getMenus().add(m);
	}

	protected void replay(String fileName) {
		try {
			SubscriptionList list = new SubscriptionList(deployPath() + File.separator + "configuration.json");
			SynchronizedReaderOperator o = new SynchronizedReaderOperator(fileName, list, createInputOperator());
			if (activeOperator != null)
				activeOperator.stop();
			activeOperator = o;
			o.run();
		} catch (Exception e) {
		}

	}

	protected void replay(File file) {
		try {
			SubscriptionList list = new SubscriptionList(deployPath() + File.separator + "configuration.json");
			SynchronizedReaderOperator o = new SynchronizedReaderOperator(file, list, createInputOperator());
			if (activeOperator != null)
				activeOperator.stop();
			activeOperator = o;
			o.run();
		} catch (Exception e) {
		}

	}

	@Override
	protected void changed(String key, String value) {
		board.putString(key, value);
	}

}
