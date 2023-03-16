package hlib.frc.table;

import java.io.File;
import java.io.FileNotFoundException;

import hlib.frc.FRC2023Visualizer;

public class SynchronizedReaderOperator extends hlib.frc.table.ReaderOperator {

	Long startTimestamp = null;

	Long startTime = null;

	public SynchronizedReaderOperator(String fileName, Iterable<SubscriptionDefinition> schemas,
			Operator... nextOperators) throws FileNotFoundException {
		super(fileName, schemas, nextOperators);
	}

	@Override
	protected void process(SubscriptionRecord record) {
		if (record != null) {
			try {
				if (startTimestamp == null) {
					startTimestamp = record.timestamp();
					startTime = System.currentTimeMillis();
				} else {
					long diffTimestamp = record.timestamp() - startTimestamp;
					long elapsedTime = System.currentTimeMillis() - startTime;
					if (diffTimestamp > elapsedTime)
						Thread.sleep(diffTimestamp - elapsedTime);
				}
				output(record);
			} catch (Exception e) {
			}
		}
	}

	public static void main(String... args) throws FileNotFoundException {
		SubscriptionList list = new SubscriptionList(FRC2023Visualizer.deployPath + File.separator + "configuration.json");
		new SynchronizedReaderOperator("logs" + File.separator + "2023-03-12_20_47.csv", list,
				new PrintStreamOperator(System.out)).run();
	}

}
