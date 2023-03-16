package hlib.networking;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;

/**
 * A {@code Server} can communicate with remote {@code Communicator}s.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public abstract class Server implements Runnable {

	/**
	 * The {@code ServerSocket} for this {@code Server}.
	 */
	java.net.ServerSocket serverSocket;

	/**
	 * The {@code Communicator}s for communicating with remote {@code Communicator}s.
	 */
	HashSet<Communicator> communicators = new HashSet<Communicator>();

	/**
	 * Constructs a {@code Server}.
	 * 
	 * @param port
	 *            the port number
	 * @throws IOException
	 *             if the specified port is not available
	 */
	public Server(int port) throws IOException {
		serverSocket = new java.net.ServerSocket(port);
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket s = serverSocket.accept(); // a new socket s created for each new connection
				final Communicator communicator = new Communicator(s); // create a communicator using socket s
				synchronized (communicators) {
					communicators.add(communicator);
				}
				Runnable r = new Runnable() {
					@Override
					public void run() {
						try {
							handleRemoteCommunicator(communicator);
						} catch (Exception e) {
						}
						synchronized (communicators) {
							communicators.remove(communicator);
						}
					}
				};
				new Thread(r).start();
			} catch (IOException e) {
				return;
			}
		}
	}

	/**
	 * Communicates with a remote {@code Communicator} using the specified {@code Communicator}.
	 * 
	 * @param communicator
	 *            a {@code Communicator} connected to a remote {@code Communicator}
	 * @throws Exception
	 *             if an error occurs
	 */
	abstract protected void handleRemoteCommunicator(Communicator communicator) throws Exception;

	/**
	 * Shuts down this {@code Server}.
	 */
	public void shutdown() {
		try {
			serverSocket.close();
		} catch (Exception e) {
		}
		synchronized (communicators) {
			for (Communicator communicator : communicators)
				communicator.shutdown();
		}
	}

	/**
	 * Sends the specified {@code Object}s to all of the remote {@code Communicator}s connected to this {@code Server}.
	 * 
	 * @param object
	 *            an {@code Object} to send
	 */
	public void broadcast(Object object) {
		synchronized (communicators) {
			for (Communicator communicator : communicators)
				try {
					communicator.send(object);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
