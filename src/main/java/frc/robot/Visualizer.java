package frc.robot;

import java.util.List;

import hlib.frc.FRC2023Visualizer;
import hlib.frc.table.ClientOperator;
import hlib.frc.table.Operator;
import hlib.frc.table.RelayOperator;
import hlib.frc.table.SubscriptionRecord;
import hlib.frc.table.SubscriptionSchema.DataType;
import javafx.scene.control.MenuBar;

/**
 * @author Jeong-Hyon Hwang (jhhbrown@gmail.com)
 */
public class Visualizer extends FRC2023Visualizer {

	Operator outputOperator = new RelayOperator();

	public static void main(String[] args) {
		if (args.length > 0)
			launch(args);
		else
			launch("localhost", "10000");
	}

	@Override
	public void init() throws Exception {
		super.init();
		List<String> args = getParameters().getRaw();
		String address = args.get(0);
		int port = Integer.parseInt(args.get(1));
		new Thread(() -> {
			run(address, port);
		}).start();
		return;
	}

	protected void run(String address, int port) {
		try {
			ClientOperator client = new ClientOperator(address, port, createInputOperator());
			System.out.println("Visualizer connected to " + address + ":" + port);
			outputOperator.addNextOperator(client);
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void changed(String key, String value) {
		SubscriptionRecord record = new SubscriptionRecord(System.currentTimeMillis(), "SmartDashboard", key,
				DataType.STRING, value);
		outputOperator.output(record);
	}

	@Override
	protected void setup(MenuBar menuBar) {
	}

}
