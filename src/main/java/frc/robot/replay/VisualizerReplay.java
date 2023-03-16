package frc.robot.replay;

import java.io.File;

import hlib.frc.FRC2023Visualizer;
import hlib.frc.table.SubscriptionList;
import hlib.frc.table.SynchronizedReaderOperator;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class VisualizerReplay extends FRC2023Visualizer {

	public static void main(String[] args) {
		if (args.length > 0)
			launch(args);
		else
			launch("logs" + File.separator + "2023-03-12_22_06.csv", "logs" + File.separator + "2023-03-12_22_06.csv");
	}

	@Override
	public void init() throws Exception {
		super.init();
		Parameters parameters = getParameters();
		new Thread(() -> {
			for (String fileName : parameters.getRaw())
				replay(fileName);
		}).start();
	}

	protected void replay(String fileName) {
		try {
			SubscriptionList list = new SubscriptionList(FRC2023Visualizer.deployPath + File.separator + "configuration.json");
			new SynchronizedReaderOperator(fileName, list, createInputOperator()).run();
		} catch (Exception e) {
		}

	}

	@Override
	protected void changed(String key, String value) {
		board.putString(key, value);
	}

}
