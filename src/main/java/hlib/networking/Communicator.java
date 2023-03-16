package hlib.networking;

import java.io.IOException;
import java.net.Socket;

/**
 * A {@code Communicator} can communicate with another {@code Communicator}.
 * 
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class Communicator {

	/**
	 * The {@code ObjectInputStream} used by this {@code Communicator}.
	 */
	protected java.io.ObjectInputStream in;

	/**
	 * The {@code ObjectOutputStream} used by this {@code Communicator}.
	 */
	protected java.io.ObjectOutputStream out;

	/**
	 * The {@code Socket} for this {@code Communicator}.
	 */
	protected Socket socket;

	/**
	 * Constructs a {@code Communicator}.
	 * 
	 * @param socket
	 *            the {@code Socket} to use
	 * @throws IOException
	 *             if the specified {@code Socket} cannot be used
	 */
	public Communicator(java.net.Socket socket) throws IOException {
		this.socket = socket;
		out = new java.io.ObjectOutputStream(socket.getOutputStream());
		in = new java.io.ObjectInputStream(socket.getInputStream());
	}

	/**
	 * Sends the specified object to the other {@code Communicator}.
	 * 
	 * @param object
	 *            an object to send
	 * @throws IOException
	 *             if an error occurs while sending the specified object
	 */
	public void send(Object object) throws IOException {
		out.writeObject(object); // write the object to the output stream
		out.flush(); // flush the output stream
		out.reset(); // disregard objects already written
	}

	/**
	 * Receives an object from the other {@code Communicator}.
	 * 
	 * @return an object received from the other {@code Communicator}
	 * @throws IOException
	 *             if an error occurs when the object is being received from the {@code Communicator}
	 * @throws ClassNotFoundException
	 *             if the class of the received object cannot be found
	 */
	public Object receiveObject() throws IOException, ClassNotFoundException {
		return in.readObject();
	}

	/**
	 * Shuts down this {@code Communicator}.
	 */
	public void shutdown() {
		try {
			socket.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Constructs a {@code Socket} connected to the specified port on the specified host.
	 * 
	 * @param address
	 *            the IP address of the host
	 * @param port
	 *            the port number.
	 * @return a {@code Socket} connected to the specified port on the specified host
	 */
	public static Socket createSocket(String address, int port) {
		for (;;) {
			try {
				return new java.net.Socket(address, port);
			} catch (Exception e) {
				System.err.println(e);
				try { // if failed, sleep for a short while.
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
		}
	}

}
