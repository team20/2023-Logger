package hlib.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class CustomizablePrintStream extends PrintStream {

	public static interface Processor {
		void received(String s);
	}
    
	public CustomizablePrintStream(Processor p) {
		super(new OutputStream() {

			String current = "";

			@Override
			public void write(int b) throws IOException {
				if ((char) b == '\n') {
					p.received(current);
					current = "";
				} else if ((char) b != '\r')
					current += (char) b;
			}

		});
	}

}

