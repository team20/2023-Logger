package hlib.frc.table;

import java.io.IOException;

import hlib.networking.Server;
import hlib.networking.Communicator;

public class ServerOperator extends Operator {

	Server server;

	public ServerOperator(int port, Operator... nextOperators) throws IOException {
		server = new Server(10000) {

			@Override
			protected void handleRemoteCommunicator(Communicator communicator)
					throws ClassNotFoundException, IOException {
				while (true) {
					Object o = communicator.receiveObject();
					output((SubscriptionRecord) o);
				}
			}

		}; // construct server
	}

	@Override
	protected void process(SubscriptionRecord record) {
		server.broadcast(record);
	}

	public void shutdown() {
		server.shutdown();
	}

}
