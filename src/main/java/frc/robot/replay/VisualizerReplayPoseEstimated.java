package frc.robot.replay;

import java.io.File;

import hlib.frc.FRC2023Visualizer;
import hlib.frc.table.PoseEstimationOperator;
import hlib.frc.table.SubscriptionList;
import hlib.frc.table.SynchronizedReaderOperator;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class VisualizerReplayPoseEstimated extends VisualizerReplay {

	public static double distanceThreshold = 1.0;

	public static int rejectionLimit = 5;

	public static double weight = 0.1;

	public static void main(String[] args) {
		if (args.length > 0)
			launch(args);
		else
			// launch("logs" + File.separator + "2023-03-12_20_47.csv");
			launch("logs" + File.separator + "2023-03-12_22_06.csv", "logs" + File.separator + "2023-03-12_22_06.csv");
	}

	@Override
	protected void replay(String fileName) {
		try {
			PoseEstimationOperator o = new PoseEstimationOperator(robotWidth, distanceThreshold, rejectionLimit, weight,
					createInputOperator());
			SubscriptionList list = new SubscriptionList(FRC2023Visualizer.deployPath + File.separator + "configuration.json");
			new SynchronizedReaderOperator(fileName, list, o).run();
		} catch (Exception e) {
		}

	}

}
