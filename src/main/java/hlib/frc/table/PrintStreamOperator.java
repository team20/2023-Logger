package hlib.frc.table;

import java.io.PrintStream;

public class PrintStreamOperator extends Operator {

	public static final String header = "Timestamp; Table Name; Topic Name; Value";

	PrintStream[] streams;
	private boolean first;

	public PrintStreamOperator(PrintStream... streams) {
		this.streams = streams;
		first = true;
	}

	@Override
	protected void process(SubscriptionRecord record) {
		if (first) {
			printHeader();
			first = false;
		}
		for (PrintStream stream : streams)
			stream.println(record);
	}

	void printHeader() {
		for (PrintStream stream : streams)
			stream.println(header);
	}

}
