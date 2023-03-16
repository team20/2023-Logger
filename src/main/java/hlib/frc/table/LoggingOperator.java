package hlib.frc.table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingOperator extends Operator {

	PrintStream stream;

	long startTime;

	public LoggingOperator() throws FileNotFoundException {
		stream = newPrintStream();
	}

	protected synchronized PrintStream newPrintStream() throws FileNotFoundException {
		if (stream != null)
			stream.close();
		startTime = System.currentTimeMillis() / 1000 / 60 * 1000 * 60;
		Date date = new Date(startTime);
		PrintStream p = new PrintStream(
				"logs" + File.separator + new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(date) + ".log");
		p.println(PrintStreamOperator.header);
		return p;
	}

	@Override
	protected void process(SubscriptionRecord record) {
		if (System.currentTimeMillis() / 1000 / 60 * 1000 * 60 != startTime)
			try {
				stream = newPrintStream();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		stream.println(record);
	}

}
