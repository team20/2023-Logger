package hlib.frc.table;

import java.io.IOException;

import hlib.networking.Communicator;

public class ClientOperator extends Operator {

	private Communicator communicator;

	public ClientOperator(String address, int port, Operator... nextOperators) throws IOException {
		super(nextOperators);
		communicator = new Communicator(Communicator.createSocket(address, port));
	}

	public void run() throws ClassNotFoundException, IOException {
		while (true) {
			output((SubscriptionRecord) communicator.receiveObject());
		}
	}

	@Override
	protected void process(SubscriptionRecord record) {
		try {
			communicator.send(record);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
