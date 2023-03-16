package hlib.visualization;

import java.util.Arrays;

import hlib.frc.table.Operator;
import hlib.frc.table.SubscriptionRecord;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A {@code Visualizer} can display visual components.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public abstract class Visualizer extends Application {

	/**
	 * The ratio of the coordinate values of the visual components to the real-world coordinate values.
	 */
	protected double scale = 40.0;

	/**
	 * Starts this {@code Visualizer} using the specified {@code Stage}.
	 * 
	 * @param stage
	 *            a {@code Stage}
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Rectangle2D bounds = getBounds();
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setResizable(false);
		stage.setScene(createScene(bounds.getWidth(), bounds.getHeight()));
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.02), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				runPeriodicTasks();
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		stage.show();
		stage.setTitle("2023 FRC Visualizer");
		stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			keyPressed(event.getCode());
		});
		stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			keyReleased(event.getCode());
		});

	}

	/**
	 * Returns a {@code Rectangle2D} that defines the visual bounds of this {@code Visualizer}.
	 * 
	 * @return a {@code Rectangle2D} that defines the visual bounds of this {@code Visualizer}
	 */
	protected abstract Rectangle2D getBounds();

	/**
	 * Constructs the main {@code Scene} for this {@code Visualizer}.
	 * 
	 * @param width
	 *            the width of the {@code Scene}
	 * @param height
	 *            the height of the {@code Scene}
	 * @return the constructed {@code Scene} for this {@code Visualizer}
	 */
	protected abstract Scene createScene(double width, double height);

	/**
	 * Performs tasks that need to be done periodically (every 20ms) for this {@code Visualizer}.
	 */
	protected abstract void runPeriodicTasks();

	/**
	 * Is invoked when the specified key is pressed.
	 * 
	 * @param code
	 *            the {@code KeyCode} of a key
	 */
	protected abstract void keyPressed(KeyCode code);

	/**
	 * Is invoked when the specified key is released.
	 * 
	 * @param code
	 *            the {@code KeyCode} of a key
	 */
	protected abstract void keyReleased(KeyCode code);

	/**
	 * Creates/organizes visual components to show a table.
	 * 
	 * @param tableView
	 *            a {@code TableView} representing a table
	 * @param preferredWidth
	 *            the preferred width of the table
	 * @param preferredHeight
	 *            the preferred height of the table
	 * @param preferredColumn1Width
	 *            the preferred width of the first column of the table
	 * @return a {@code Node} containing the table
	 */
	protected Node setUpTable(TableView<Object[]> tableView, double preferredWidth, double preferredHeight,
			double preferredColumn1Width) {
		TableColumn<Object[], String> columnName = new TableColumn<>("Name");
		columnName.setPrefWidth(preferredColumn1Width);
		columnName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[0]));
		TableColumn<Object[], String> columnValue = new TableColumn<>("Value");
		columnValue.setPrefWidth(preferredWidth - preferredColumn1Width + 2);
		columnValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(toString(cellData.getValue()[1])));
		tableView.getColumns().add(columnName);
		tableView.getColumns().add(columnValue);
		tableView.setFocusTraversable(false);
		tableView.setPrefSize(preferredWidth, preferredHeight);
		VBox vbox = new VBox(tableView);
		vbox.setFocusTraversable(false);
		ScrollPane sp = new ScrollPane(tableView);
		// sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		return vbox;
	}

	/**
	 * Adds a {@code Box} as a child of the specified {@code Group} node.
	 * 
	 * @param x1
	 *            the x coordinate value of 1st corner of the {@code Box}
	 * @param y1
	 *            the y coordinate value of 1st corner of the {@code Box}
	 * @param z1
	 *            the z coordinate value of 1st corner of the {@code Box}
	 * @param x2
	 *            the x coordinate value of 2nd corner (the opposite corner of the 1st corner) of the {@code Box}
	 * @param y2
	 *            the y coordinate value of 2nd corner (the opposite corner of the 1st corner) of the {@code Box}
	 * @param z2
	 *            the z coordinate value of 2nd corner (the opposite corner of the 1st corner) of the {@code Box}
	 * @param c
	 *            the {@code Color} of the {@code Box}
	 * @param g
	 *            a {@code Group} node
	 * @return the {@code Box} added
	 */
	protected Box addBox(double x1, double y1, double z1, double x2, double y2, double z2, Color c, Group g) {
		x1 *= scale;
		y1 *= scale;
		z1 *= scale;
		x2 *= scale;
		y2 *= scale;
		z2 *= scale;
		Box b = new Box(Math.abs(x2 - x1), Math.abs(y2 - y1), Math.abs(z2 - z1));
		b.setTranslateX(Math.min(x1, x2) + Math.abs(x2 - x1) / 2);
		b.setTranslateY(Math.min(y1, y2) + Math.abs(y2 - y1) / 2);
		b.setTranslateZ(Math.min(z1, z2) + Math.abs(z2 - z1) / 2);
		PhongMaterial m = new PhongMaterial(c);
		b.setMaterial(m);
		g.getChildren().add(b);
		return b;
	}

	/**
	 * Creates a {@code PrintStream} that provides input to this {@code Visualizer}.
	 * 
	 * @return a {@code PrintStream} that provides input to this {@code Visualizer}
	 */
	protected Operator createInputOperator() {
		return new Operator() {

			@Override
			protected void process(SubscriptionRecord record) {
				received(record);
			}

		};
	}

	/**
	 * Is invoked when this {@code Visualizer} receives a {@code SubscriptionRecord}.
	 * 
	 * @param record
	 *            a {@code SubscriptionRecord}
	 */
	protected abstract void received(SubscriptionRecord record);

	/**
	 * Returns a {@code String} representation of the specified {@code Object}.
	 * 
	 * @param o
	 *            an {@code Object}
	 * @return a {@code String} representation of the specified {@code Object}
	 */
	public static String toString(Object o) {
		if (o == null)
			return "" + o;
		if (o.getClass().isArray()) {
			try {
				return Arrays.toString((double[]) o);
			} catch (Exception e) {
			}
			return "?";
		}
		return "" + o;
	}

}
