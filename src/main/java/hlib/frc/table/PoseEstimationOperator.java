package hlib.frc.table;

import hlib.drive.Pose;
import hlib.drive.RobotPoseEstimator;
import hlib.drive.RobotPoseEstimatorWeighted;
import hlib.frc.table.SubscriptionSchema.DataType;

public class PoseEstimationOperator extends Operator {

	protected RobotPoseEstimatorWeighted poseEstimator;

	/**
	 * Construts the {@code PoseEstimationOperator}.
	 * 
	 * @param robotWidth
	 *            the width of the {@code Robot}
	 * @param distanceThreshold
	 *            the distance threshold for outlier detection (i.e., the difference in x- or y-coordinates of the
	 *            {@code Pose} from LimeLight compared to the {@code Pose} that has been estimated in order for the
	 *            {@code Pose} from LimeLight to be considered an outlier)
	 * @param rejectionLimit
	 *            the number of rejections before resetting the {@code PoseEstimationSubsystem}
	 * @param weight
	 *            the weight of each new {@code Pose} from the LimeLight.
	 */
	public PoseEstimationOperator(double robotWidth, double distanceThreshold, int rejectionLimit, double weight,
			Operator... nextOperators) {
		super(nextOperators);
		poseEstimator = new RobotPoseEstimatorWeighted(robotWidth, distanceThreshold, rejectionLimit, weight);
	}

	@Override
	protected void process(SubscriptionRecord record) {
		try {
			if (!record.topicName().equals("Pose (Estimated)"))
				output(record);
			if (record.topicName().equals("botpose")) {
				double[] botpose = (double[]) record.value();
				Pose poseDetected = new Pose(botpose[0], botpose[1], botpose[5] * Math.PI / 180 + Math.PI);
				if (poseDetected != null) {
					if (isValid(poseDetected, poseEstimator))
						poseEstimator.update(poseDetected); //
					output(new SubscriptionRecord(record.timestamp(), "SmartDashboard", "Pose (LimeLight)",
							DataType.STRING, "" + poseDetected));
					output(new SubscriptionRecord(record.timestamp(), "SmartDashboard", "Pose (Estimated)",
							DataType.STRING, "" + poseEstimator.poseEstimated()));
					output(new SubscriptionRecord(record.timestamp(), "SmartDashboard", "Max Pose Inconsisency",
							DataType.STRING, "" + poseEstimator.largestPoseInconsistency()));
				}
			} else if (record.topicName().equals("Wheel Encoder Positions"))
				handleWheelEncoderPositions(record);
			else if (record.topicName().equals("Heading"))
				handleHeading(record);
		} catch (Exception e) {
			// output(toString(r.timestamp(), "SmartDashboard", "Pose (LimeLight)", null));
		}
	}

	boolean isValid(Pose poseDetected, RobotPoseEstimator poseEstimator) {
		Pose poseEstimated = poseEstimator.poseEstimated();
		return poseDetected != null && Math.abs(poseDetected.x()) > 4.7 && Math.abs(poseDetected.x()) < 7
				&& (poseEstimated == null || (Math.abs(poseEstimated.x()) > 4.7 && Math.abs(poseEstimated.x()) < 7));
	}

	protected void handleHeading(SubscriptionRecord record) {
		recentRecordHeading = record;
		if (recentRecordWheelEncoderPositions != null
				&& record.timestamp() - recentRecordWheelEncoderPositions.timestamp() < 2) {
			handleRecentRedords();
		}
	}

	protected void handleWheelEncoderPositions(SubscriptionRecord record) {
		recentRecordWheelEncoderPositions = record;
		if (recentRecordHeading != null && record.timestamp() - recentRecordHeading.timestamp() < 2) {
			handleRecentRedords();
		}
	}

	protected void handleRecentRedords() {
		double[] positions = ReaderOperator.doubleArray((String) recentRecordWheelEncoderPositions.value());
		Double yaw = (Double) recentRecordHeading.value();
		poseEstimator.update(positions[0], positions[1], yaw * Math.PI / 180);
		output(new SubscriptionRecord(
				Math.max(recentRecordHeading.timestamp(), recentRecordWheelEncoderPositions.timestamp()),
				"SmartDashboard", "Pose (Estimated)", DataType.STRING, "" + poseEstimator.poseEstimated()));
	}

	SubscriptionRecord recentRecordHeading = null;

	SubscriptionRecord recentRecordWheelEncoderPositions = null;

}
