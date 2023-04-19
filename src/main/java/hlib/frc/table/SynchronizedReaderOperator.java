package hlib.frc.table;

import java.io.File;
import java.io.FileNotFoundException;

public class SynchronizedReaderOperator extends hlib.frc.table.ReaderOperator {

	Long startTimestamp = null;

	Long startTime = null;

	public SynchronizedReaderOperator(File file, Iterable<SubscriptionDefinition> schemas,
			Operator... nextOperators) throws FileNotFoundException {
		super(file, schemas, nextOperators);
	}

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
					while (diffTimestamp > 1 * (System.currentTimeMillis() - startTime)) {
						Thread.sleep(100);
						if (stopped)
							return;
					}
				}
				output(record);
			} catch (Exception e) {
			}
		}
	}

	public static void main(String... args) throws FileNotFoundException {
		SubscriptionList list = new SubscriptionList("." + File.separator + "src" + File.separator + "main"
				+ File.separator + "deploy" + File.separator + "configuration.json");
		new SynchronizedReaderOperator("logs" + File.separator + "2023-03-12_20_47.csv", list,
				new PrintStreamOperator(System.out)).run();
	}

}
