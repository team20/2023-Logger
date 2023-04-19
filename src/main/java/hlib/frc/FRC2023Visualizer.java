package hlib.frc;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import hlib.drive.Pose;
import hlib.frc.table.SubscriptionRecord;
import hlib.frc.table.SubscriptionSchema.DataType;
import hlib.visualization.SmartDashboardImpl;
import hlib.visualization.Visualizer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * A {@code Visualizer} can display visual components for the FRC 2023 competition.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public abstract class FRC2023Visualizer extends Visualizer {

	protected String deployPath() {
		return "." + File.separator + "src" + File.separator + "main" + File.separator + "deploy";
	}

	/**
	 * The {@code TargetChooserMap} used by this {@code Visualizer}.
	 */
	TargetChooserMap targetChooserMap = new TargetChooserMap(deployPath() + File.separator + "configuration.json");

	/**
	 * The {@code TargetMap} used by this {@code Visualizer}.
	 */
	protected TargetMapColorCoded targetMap = new TargetMapColorCoded(
			deployPath() + File.separator + "configuration.json");

	/**
	 * The {@code PathMap} used by this {@code Visualizer}.
	 */
	protected PathMap pathMap = new PathMap(deployPath() + File.separator + "configuration.json");

	/**
	 * The heading of this {@code Visualizer} on the field.
	 */
	double heading = -Math.PI / 2;

	/**
	 * The {@code AprilTagMap} used by this {@code Visualizer}.
	 */
	HashMap<Integer, Transform> aprilTags = new AprilTagMap(deployPath() + File.separator + "frc2023.fmap");

	/**
	 * The label for the entry representing the estimated {@code Pose} of the robot.
	 */
	public static String labelRobotPoseEstimated = "Pose (Estimated)";

	/**
	 * The label for the entry representing the {@code Pose} (detected by the LimeLight) of the robot.
	 */
	public static String labelRobotPoseLimeLight = "Pose (LimeLight)";

	/**
	 * The label for the entry representing the {@code Pose} (detected by the LimeLight) of the robot.
	 */
	public static String labelWheelEncoderPositions = "Wheel Encoder Positions";

	/**
	 * The label for the entry representing the ID of the current target.
	 */
	public static String labelTargetID = "Target ID";

	/**
	 * The label for the entry representing the ID of the current target.
	 */
	public static String labelTargetButton = "Target(Button)";

	/**
	 * The width of the field.
	 */
	protected static double fieldWidth = (54 * 12 + 3.25) * 0.0254;

	/**
	 * The length of the field.
	 */
	protected static double fieldLength = (26 * 12 + 3.5) * 0.0254;

	/**
	 * The width of the main {@code Robot}.
	 */
	protected static double robotWidth = 20.5 * 0.0254;

	/**
	 * The length of the main {@code Robot}.
	 */
	protected static double robotLength = 20.5 * 0.0254;

	/**
	 * The height of the main {@code Robot}.
	 */
	protected static double robotHeight = 50 * 0.0254;

	/**
	 * The camera height of the the main {@code Robot}.
	 */
	protected static double cameraHeight = 0.3;

	/**
	 * The width of the AprilTags.
	 */
	protected static double aprilTagWidth = 6 * 0.0254;

	/**
	 * The height of the AprilTags.
	 */
	protected static double aprilTagHeight = 6 * 0.0254;

	/**
	 * The thickness of the AprilTags.
	 */
	protected static double aprilTagThickness = 0.01;

	/**
	 * The size of the margin relative to the width/height of AprilTags.
	 */
	protected static double aprilTagMarginRatio = 1.5;

	/**
	 * The background color used for the {@code Scene}s.
	 */
	protected static Color backgroundColor = Color.BLACK;

	/**
	 * The width of the right panel showing information about the main {@code Robot}.
	 */
	protected static int rightPanelWidth = 400;

	/**
	 * The width of the first column of a table showing information about the main {@code Robot}.
	 */
	protected static int column1Width = 150;

	/**
	 * The height of the menu bar.
	 */
	protected static int menuBarHeight = 60;

	protected static Rectangle2D chargeStationRed = new Rectangle2D(3.41, 0.04 - 2.43, 1.91, 2.43);

	protected static Rectangle2D chargeStationBlue = new Rectangle2D(-3.41 - 1.91, 0.04 - 2.43, 1.91, 2.43);

	protected static Rectangle2D wallRed = new Rectangle2D(4.92, 1.60, 2.31, 0.1);

	protected static Rectangle2D wallBlue = new Rectangle2D(-4.92 - 2.31, 1.60, 2.31, 0.1);

	/**
	 * The bitmap definitions of the AprilTags.
	 */
	protected static long[] apriltags16h5 = new long[] { 0x231bL, 0x2ea5L, 0x346aL, 0x45b9L, 0x79a6L, 0x7f6bL, 0xb358L,
			0xe745L, 0xfe59L, 0x156dL, 0x380bL, 0xf0abL, 0x0d84L, 0x4736L, 0x8c72L, 0xaf10L, 0x093cL, 0x93b4L, 0xa503L,
			0x468fL, 0xe137L, 0x5795L, 0xdf42L, 0x1c1dL, 0xe9dcL, 0x73adL, 0xad5fL, 0xd530L, 0x07caL, 0xaf2eL };

	/**
	 * The {@code TableView} to display information about the main {@code Robot}.
	 */
	TableView<Object[]> tableView = new TableView<Object[]>();

	/**
	 * The {@code SmartDashboard} to display information about the main {@code Robot}.
	 */
	protected SmartDashboardImpl board = new SmartDashboardImpl(tableView);

	/**
	 * A map that associates the ID of each {@code Robot} with the node {@code Group}s that represent the {@code Robot}.
	 */
	protected Map<String, LinkedList<Group>> robotID2groupsRobot = new HashMap<String, LinkedList<Group>>();

	/**
	 * A map that associates the ID of each target with the {@code Box}s that represents the target.
	 */
	protected Map<String, Box> targetID2Box = new HashMap<String, Box>();

	/**
	 * The {@code Text}s shown by this {@code FRC2023Visualizer}.
	 */
	protected LinkedList<Text> texts = new LinkedList<Text>();

	/**
	 * The {@code TopScene} used by this {@code FRC2023Visualizer}.
	 */
	protected TopScene topScene;

	/**
	 * the {@code Button} managed by this {@code FRC2023Visualizer}.
	 */
	protected HashMap<String, HashMap<String, ToggleButton>> buttons = new HashMap<String, HashMap<String, ToggleButton>>();

	/**
	 * A {@code TopScene} shows the field from the top.
	 * 
	 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
	 */
	protected class TopScene extends SubScene {

		/**
		 * The {@code PerspectiveCamera} used by this {@code TopScene}.
		 */
		PerspectiveCamera camera = new PerspectiveCamera(true);

		/**
		 * Constructs a {@code TopScene}.
		 * 
		 * @param root
		 *            the root {@code Node}
		 * @param width
		 *            the width of the {@code TopScene}
		 * @param height
		 *            the height of the {@code TopScene}
		 */
		public TopScene(Group root, double width, double height) {
			super(root, width, height, true, SceneAntialiasing.BALANCED);
			setFill(backgroundColor);
			setCamera(camera);
			root.getTransforms().add(new Rotate(180, Rotate.X_AXIS));
			camera.setFieldOfView(12);
			camera.setFarClip(100 * scale);
			setDistance(50);
		}

		/**
		 * Sets the distance between the {@code PerspectiveCamera} and the center of the field.
		 * 
		 * @param distance
		 *            the desired distance between the {@code PerspectiveCamera} and the center of the field
		 */
		public void setDistance(double distance) {
			camera.translateZProperty().set(scale * -distance);
		}

		/**
		 * Rotates the field by the specified angle.
		 * 
		 * @param angle
		 *            the angle defining the rotation
		 */
		public void rotateBy(double angle) {
			heading = Pose.normalize(heading + angle * Math.PI / 180);
			Rotate r = new Rotate(angle, Rotate.Z_AXIS);
			for (Text text : texts)
				text.getTransforms().add(r);
			this.getRoot().getTransforms().add(r);
		}
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
		stage.setTitle("FRC 2023 Visualizer");
	}

	/**
	 * Is invoked when this {@code FRC2023Visualizer} receives a {@code SubscriptionRecord}.
	 * 
	 * @param record
	 *            the received {@code SubscriptionRecord}
	 */
	@Override
	protected void received(SubscriptionRecord record) {
		try {
			if (record != null) {
				if (record.dataType() == DataType.STRING)
					board.putString(record.topicName(), (String) record.value());
				else if (record.dataType() == DataType.STRING_ARRAY)
					board.putStringArray(record.topicName(), (String[]) record.value());
				else if (record.dataType() == DataType.LONG)
					board.putInt(record.topicName(), (Long) record.value());
				else if (record.dataType() == DataType.LONG_ARRAY)
					board.putIntArray(record.topicName(), (long[]) record.value());
				else if (record.dataType() == DataType.DOUBLE)
					board.putNumber(record.topicName(), (Double) record.value());
				else if (record.dataType() == DataType.DOUBLE_ARRAY)
					board.putNumberArray(record.topicName(), (double[]) record.value());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs the main {@code Scene} for this {@code FRC2023Visualizer}.
	 * 
	 * @param width
	 *            the width of the {@code Scene}
	 * @param height
	 *            the height of the {@code Scene}
	 * @return the constructed {@code Scene} for this {@code FRC2023Visualizer}
	 */
	@Override
	protected Scene createScene(double width, double height) {
		MenuBar menuBar = new MenuBar();
		setup(menuBar);
		SplitPane spMain = new SplitPane();
		VBox root = new VBox(menuBar, spMain);
		Group topView = new Group();
		setUpField(topView);
		setUpTargetPoses(topView);
		setUpRobot(labelRobotPoseLimeLight, 0.1, Color.GRAY, topView);
		setUpRobot(labelRobotPoseEstimated, 0.1, Color.LIGHTGREEN, topView);
		topScene = new TopScene(topView, width - rightPanelWidth, height - menuBarHeight);
		topScene.rotateBy(-90);
		topScene.setDistance(100);
		spMain.getItems().addAll(topScene, createRightPane(height));
		Scene scene = new Scene(root, width, height);
		board.subscribe((String topicName, Object value) -> {
			if (topicName.contains("botpose")) {
				double[] botpose = (double[]) value;
				changedRobotPose(labelRobotPoseLimeLight, createPose(botpose));
			} else if (topicName.contains(labelRobotPoseEstimated)) {
				Pose poseEstimated = parsePose((String) value);
				changedRobotPose(labelRobotPoseEstimated, poseEstimated);
			} else if (topicName.contains(labelTargetID))
				this.changedTargetID((String) value);
			else if (topicName.contains(labelTargetButton))
				this.changedTargetButton((String) value);
		});
		return scene;
	}

	public static Pose createPose(double[] botpose) {
		return new Pose(botpose[0], botpose[1], botpose[5] * Math.PI / 180 + Math.PI); // Aster - camera mounted in the
																						// back
		// return new Pose(botpose[0], botpose[1], -botpose[5] * Math.PI / 180);
	}

	/**
	 * Sets up the specified {@code MenuBar}.
	 * 
	 * @param menuBar
	 *            a {@code MenuBar}
	 */
	protected abstract void setup(MenuBar menuBar);

	/**
	 * Constructs a {@code Pane} that shows a table and configurable buttons.
	 * 
	 * @param height
	 *            the height of the {@code Pane}
	 * @return the constructed {@code Pane}
	 */
	protected Pane createRightPane(double height) {
		int rightTopHeight = 200;
		VBox rightBottom = new VBox();
		int rowHeight = 30;
		VBox rightPane = new VBox(createButtons(rightPanelWidth - 5, rightTopHeight), setUpTable(tableView,
				rightPanelWidth - 5, height - rightTopHeight - rowHeight * targetChooserMap.size(), column1Width),
				rightBottom);
		board.putString(labelRobotPoseEstimated, "");
		board.putString(labelRobotPoseLimeLight, "");
		board.putString(labelWheelEncoderPositions, "");
		for (Entry<String, String> e : targetChooserMap.entrySet()) {
			board.putString(e.getKey(), "");
			rightBottom.getChildren()
					.add(addTargetSelector(e.getKey(), e.getValue(), targetIDs(), column1Width, rowHeight));
		}
		return rightPane;
	}

	Node createButtons(double width, double height) {
		int rows = 3;
		int columns = 9;
		double buttonWidth = width / columns;
		double buttonHeight = height / (rows + 1);
		Image cone = new Image(new File(deployPath() + File.separator + "cone.png").toURI().toString());
		Image cube = new Image(new File(deployPath() + File.separator + "cube.png").toURI().toString());

		ToggleGroup group = new ToggleGroup();
		GridPane grid = new GridPane();
		grid.setPrefSize(width, height);
		createButton(grid, "4l", buttonWidth, buttonHeight, 0, 0, group).setVisible(false);
		createButton(grid, "4r", buttonWidth, buttonHeight, 1, 0, group).setVisible(false);
		createButton(grid, "5l", buttonWidth, buttonHeight, 7, 0, group);
		createButton(grid, "5r", buttonWidth, buttonHeight, 8, 0, group);
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < columns; c++)
				if (c % 3 == 1)
					createButton(grid, cube, buttonWidth, buttonHeight, c, r + 1, group);
				else
					createButton(grid, cone, buttonWidth, buttonHeight, c, r + 1, group);
		return grid;
	}

	ToggleButton createButton(GridPane grid, double buttonWidth, double buttonHeight, int columnIndex, int rowIndex,
			ToggleGroup group) {
		ToggleButton button = new ToggleButton();
		button.setStyle("");
		button.setPrefSize(buttonWidth, buttonHeight);
		grid.add(button, columnIndex, rowIndex);
		button.setFocusTraversable(false);
		button.setToggleGroup(group);
		return button;
	}

	ToggleButton createButton(GridPane grid, String label, double buttonWidth, double buttonHeight, int columnIndex,
			int rowIndex, ToggleGroup group) {
		ToggleButton button = createButton(grid, buttonWidth, buttonHeight, columnIndex, rowIndex, group);
		button.setText(label);
		addButton(button, label, "");
		button.setOnAction(event -> {
			if (!button.isSelected())
				board.putString("Target(Button)", "");
			else
				board.putString("Target(Button)", label);
		});
		return button;
	}

	void addButton(ToggleButton button, String targetID, String suffix) {
		HashMap<String, ToggleButton> m = buttons.get(targetID);
		if (m == null) {
			m = new HashMap<String, ToggleButton>();
			buttons.put(targetID, m);
		}
		m.put(suffix, button);
	}

	ToggleButton createButton(GridPane grid, Image image, double buttonWidth, double buttonHeight, int columnIndex,
			int rowIndex, ToggleGroup group) {
		ToggleButton button = createButton(grid, buttonWidth, buttonHeight, columnIndex, rowIndex, group);
		ImageView v = new ImageView(image);
		v.setFitWidth(buttonWidth * 0.6);
		v.setFitHeight(buttonHeight * 0.6);
		button.setGraphic(v);
		String lr = columnIndex % 3 == 1 ? "" : (columnIndex % 3 == 0 ? "l" : "r");
		addButton(button, "" + (columnIndex / 3 + 6) + lr, "" + rowIndex);
		addButton(button, "" + (columnIndex / 3 + 1) + lr, "" + rowIndex);
		button.setOnAction(event -> {
			if (!button.isSelected())
				board.putString("Target(Button)", "");
			else if (heading == 0) {
				board.putString("Target(Button)", "" + (columnIndex / 3 + 6) + lr + " " + rowIndex);
			} else {
				board.putString("Target(Button)", "" + (columnIndex / 3 + 1) + lr + " " + rowIndex);
			}
		});
		return button;
	}

	/**
	 * Returns a {@code Rectangle2D} that defines the visual bounds of this {@code FRC2023Visualizer}.
	 * 
	 * @return a {@code Rectangle2D} that defines the visual bounds of this {@code FRC2023Visualizer}
	 */
	@Override
	protected Rectangle2D getBounds() {
		Rectangle2D b = Screen.getPrimary().getVisualBounds();
		return new Rectangle2D(b.getMinX(), b.getMinY(), b.getHeight() * fieldLength / fieldWidth + rightPanelWidth,
				b.getHeight());
	}

	/**
	 * Performs tasks that need to be done periodically (every 20ms) for this {@code FRC2023Visualizer}.
	 */
	protected void runPeriodicTasks() {
	}

	/**
	 * Is invoked the value of a certain key is changed.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the new value of the key
	 */
	abstract protected void changed(String key, String value);

	/**
	 * Adds a {@code ComboBox} that can associate the specified button with one of the specified targets.
	 * 
	 * @param key
	 *            the key representing a button
	 * @param defaultValue
	 *            the default value
	 * @param targetIDs
	 *            the IDs of the target poses
	 * @param keyWidth
	 *            the width of the key
	 * @param comboBoxWidth
	 *            the width of the {@code ComboBox}
	 * @return a {@code Pane} containing the {@code ComboBox}
	 */
	protected Pane addTargetSelector(String key, String defaultValue, String[] targetIDs, double keyWidth,
			double comboBoxWidth) {
		Label label = new Label(key);
		label.setPrefWidth(keyWidth);
		ComboBox<String> comboBox = new ComboBox<String>(FXCollections.observableArrayList(targetIDs));
		comboBox.setPrefWidth(comboBoxWidth);
		comboBox.setFocusTraversable(false);
		HBox tilePane = new HBox(label, comboBox);
		comboBox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				changed(key, comboBox.getValue());
			}
		});
		comboBox.setValue(defaultValue);
		changed(key, comboBox.getValue());
		return tilePane;
	}

	/**
	 * Is invoked when the {@code Pose} of a {@code Robot} is changed.
	 * 
	 * @param robotID
	 *            the ID of a {@code Robot}
	 * @param pose
	 *            the new {@code Pose} of a {@code Robot}
	 */
	public void changedRobotPose(String robotID, Pose pose) {
		if (pose != null) {
			LinkedList<Group> groupsRobot = this.robotID2groupsRobot.get(robotID);
			if (groupsRobot != null)
				for (Group group : groupsRobot) {
					group.setVisible(false);
					group.translateXProperty().set(scale * pose.x());
					group.translateYProperty().set(scale * pose.y());
					group.rotateProperty().set(pose.directionalAngle() * 180 / Math.PI);
					group.setVisible(true);
				}
		}
	}

	/**
	 * Is invoked when the ID of the target is changed.
	 * 
	 * @param targetID
	 *            the ID of the new target
	 */
	public void changedTargetID(String targetID) {
		for (Entry<String, Box> e : targetID2Box.entrySet()) {
			e.getValue().setMaterial(
					e.getKey().equals(targetID) ? new PhongMaterial(Color.PURPLE.interpolate(Color.WHITE, 0.5))
							: new PhongMaterial(targetMap.color(e.getKey())));
		}
	}

	/**
	 * Is invoked when the target chooser button is changed.
	 * 
	 * @param buttonID
	 *            the ID of the target chooser button
	 */
	public void changedTargetButton(String buttonID) {
		try {
			String[] tokens = buttonID.split(" ");
			if (tokens.length == 2)
				changedTargetButton(tokens[0].trim(), tokens[1].trim());
			else
				changedTargetButton(tokens[0].trim(), "");
		} catch (Exception e) {
		}
	}

	/**
	 * Is invoked when the target chooser button is changed.
	 * 
	 * @param targetID
	 *            the ID of the new target
	 */
	public void changedTargetButton(String targetID, String suffix) {
		System.out.println(targetID + " " + suffix);
		try {
			ToggleButton button = buttons.get(targetID).get(suffix);
			if (button != null)
				button.setSelected(true);
		}
		catch (Exception e) {
		}
	}

	/**
	 * Adds {@code Box}es that represent the FRC 2023 field including the AprilTags.
	 * 
	 * @param groups
	 *            node {@code Group}s to which the {@code Box}es are added
	 */
	protected void setUpField(Group... groups) {
		for (Group group : groups) {
			addBox(-fieldWidth / 2, -fieldLength / 2, 0, fieldWidth / 2, fieldLength / 2, -0.01, Color.WHITE, group);
			addBox(chargeStationRed, 0, 0.1, TargetMapColorCoded.RED, group);
			addBox(chargeStationBlue, 0, 0.1, TargetMapColorCoded.BLUE, group);
			addBox(wallRed, 0, 0.4, TargetMapColorCoded.RED, group);
			addBox(wallBlue, 0, 0.4, TargetMapColorCoded.BLUE, group);
			for (int tagID : new int[] { 1, 2, 3, 5 })
				setUpAprilTag(tagID, TargetMapColorCoded.RED, group);
			for (int tagID : new int[] { 4, 6, 7, 8 })
				setUpAprilTag(tagID, TargetMapColorCoded.BLUE, group);
		}
	}

	/**
	 * Adds {@code Box}es that represent an AprilTag.
	 * 
	 * @param tagID
	 *            the ID of the AprilTag
	 * @param color
	 *            the Color of the AprilTag
	 * @param group
	 *            a node {@code Group} to which the {@code Box}es are added
	 */
	protected void setUpAprilTag(int tagID, Color color, Group group) {
		Group groupTag = new Group();
		double aprilTagTileWidth = aprilTagWidth / 6;
		double aprilTagTileHeight = aprilTagHeight / 6;
		double x = -aprilTagWidth / 2;
		double y = -aprilTagHeight / 2;
		addBox(-aprilTagThickness / 2, x * aprilTagMarginRatio, y * aprilTagMarginRatio, 0,
				aprilTagWidth / 2 * aprilTagMarginRatio, aprilTagHeight / 2 * aprilTagMarginRatio, color, groupTag);
		long tag16h5 = apriltags16h5[tagID];
		int mask = 0x8000;
		for (int row = 5; row >= 0; row--)
			for (int column = 0; column < 6; column++) {
				Color tileColor = Color.BLACK;
				if (row != 0 && row != 5 && column != 0 && column != 5) {
					if ((mask & tag16h5) != 0)
						tileColor = color;
					mask /= 2;
				}
				addBox(0, x + aprilTagTileWidth * column, y + aprilTagTileHeight * row, aprilTagThickness / 2,
						x + aprilTagTileWidth * (column + 1), y + aprilTagTileHeight * (row + 1), tileColor, groupTag);
			}
		Transform t = aprilTag(tagID);
		double roll = Math.atan2(-t.getMyx(), t.getMxx());
		groupTag.setTranslateX(scale * t.getTx());
		groupTag.setTranslateY(scale * t.getTy());
		groupTag.setTranslateZ(scale * t.getTz());
		groupTag.setRotate(roll * 180 / Math.PI);
		group.getChildren().add(groupTag);
	}

	/**
	 * Adds {@code Box}es that represent the targets predefined.
	 * 
	 * @param group
	 *            a node {@code Group} to which the {@code Box}es are added
	 */
	protected void setUpTargetPoses(Group group) {
		for (String targetID : targetIDs()) {
			Pose pose = targetPose(targetID);
			Group groupPose = new Group();
			Color color = targetMap.color(targetID);
			Color color2 = targetMap.color(targetID).interpolate(Color.WHITE, 0.5);
			Box box = addBox(robotLength / 2, -robotWidth / 2, 0, robotLength / 6, robotWidth / 2, 0.01, color,
					groupPose);
			addBox(-robotLength / 2, -robotWidth / 2, 0, robotLength / 6, robotWidth / 2, 0.01, color2, groupPose);
			groupPose.setRotate(pose.directionalAngle() * 180 / Math.PI);
			groupPose.setTranslateX(pose.x() * scale);
			groupPose.setTranslateY(pose.y() * scale);
			group.getChildren().add(groupPose);
			targetID2Box.put(targetID, box);
			Text t = new Text(targetID);
			t.setFill(Color.BLACK);
			t.getTransforms().add(new Rotate(180, Rotate.X_AXIS));
			t.setTranslateX((pose.x() + 0.1) * scale);
			t.setTranslateY((pose.y() - 0.1) * scale);
			group.getChildren().add(t);
			texts.add(t);
		}
	}

	/**
	 * Adds {@code Box}es that represent a {@code Robot}.
	 * 
	 * @param robotID
	 *            the ID of a {@code Robot}
	 * @param robotHeight
	 *            the height of a {@code Robot}
	 * @param color
	 *            the {@code Color} of a {@code Robot}
	 * @param group
	 *            a node {@code Group} to which the {@code Box}es are added
	 */
	protected void setUpRobot(String robotID, double robotHeight, Color color, Group... groups) {
		LinkedList<Group> groupsRobot = robotID2groupsRobot.get(robotID);
		if (groupsRobot == null) {
			groupsRobot = new LinkedList<Group>();
			robotID2groupsRobot.put(robotID, groupsRobot);
		}
		for (Group group : groups) {
			Group groupRobot = new Group();
			addBox(robotLength / 2, -robotWidth / 2, 0, robotLength / 6, robotWidth / 2, robotHeight, color.darker(),
					groupRobot);
			addBox(-robotLength / 2, -robotWidth / 2, 0, robotLength / 6, robotWidth / 2, robotHeight, color.brighter(),
					groupRobot);
			group.getChildren().add(groupRobot);
			groupRobot.setTranslateX(100 * scale);
			groupsRobot.add(groupRobot);
		}
	}

	/**
	 * Returns a {@code String} representation of a pair consisting of the two specified values.
	 * 
	 * @param v1
	 *            the 1st value
	 * @param v2
	 *            the 2nd value
	 * @return a {@code String} representation of a pair consisting of the two specified values
	 */
	public static String toString(double v1, double v2) {
		return String.format("(%.3f, %.3f)", v1, v2);
	}

	/**
	 * Returns the IDs of the targets.
	 * 
	 * @return the IDs of the targets
	 */
	protected String[] targetIDs() {
		return targetMap.keySet().toArray(new String[0]);
	}

	/**
	 * Returns the {@code Transform} that defines the pose of the specified AprilTag.
	 * 
	 * @param tagID
	 *            the ID of an AprilTag
	 * @return the {@code Transform} that defines the pose of the specified AprilTag
	 */
	protected Transform aprilTag(int i) {
		return this.aprilTags.get(i);
	}

	/**
	 * Is invoked when the specified key is pressed.
	 * 
	 * @param code
	 *            the {@code KeyCode} of a key
	 */
	@Override
	protected void keyPressed(KeyCode code) {
		if (code == KeyCode.ENTER) {
			topScene.rotateBy(180);
			if (heading == 0) {
				buttons.get("4l").get("").setVisible(true);
				buttons.get("4r").get("").setVisible(true);
				buttons.get("5l").get("").setVisible(false);
				buttons.get("5r").get("").setVisible(false);
			} else {
				buttons.get("4l").get("").setVisible(false);
				buttons.get("4r").get("").setVisible(false);
				buttons.get("5l").get("").setVisible(true);
				buttons.get("5r").get("").setVisible(true);
			}
		}
	}

	/**
	 * Is invoked when the specified key is released.
	 * 
	 * @param code
	 *            the {@code KeyCode} of a key
	 */
	@Override
	protected void keyReleased(KeyCode code) {
	}

	/**
	 * Returns the {@code Pose} of the specified target.
	 * 
	 * @param targetID
	 *            the ID of a target
	 * @return the {@code Pose} of the specified target
	 */
	protected Pose targetPose(String targetID) {
		return targetMap.get(targetID);
	}

	/**
	 * Parses the given {@code String} and constructs a new {@code Pose} accordingly.
	 * 
	 * @param s
	 *            a {@code String}
	 * @return the constructed {@code Pose}
	 */
	public static Pose parsePose(String s) {
		try {
			s = s.substring(1, s.length() - 1);
			String[] tokens = s.split(",");
			String angle = tokens[2].trim();
			angle = angle.split(" ")[0];
			return new Pose(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]),
					Double.parseDouble(angle) * Math.PI / 180);
		} catch (Exception e) {
			return null;
		}
	}

}
