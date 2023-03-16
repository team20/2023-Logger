package frc.robot.replay;

import java.io.File;
import java.io.IOException;

import hlib.frc.FRC2023Visualizer;
import hlib.frc.table.ClientOperator;
import hlib.frc.table.SubscriptionList;
import hlib.frc.table.SynchronizedReaderOperator;

public class LoggerFeeder {

    public static void main(String... args) throws IOException {
        if (args.length > 0)
        run(args);
    else
        run("logs" + File.separator + "2023-03-12_22_06.csv", "logs" + File.separator + "2023-03-12_22_06.csv");
    }
    
    protected static void run(String... fileNames) throws IOException {
        SubscriptionList list = new SubscriptionList(FRC2023Visualizer.deployPath + File.separator + "configuration.json");
        for (String fileName: fileNames) {
            ClientOperator client = new ClientOperator("localhost", 10000);
            new SynchronizedReaderOperator(fileName, list, client).run();
        }

    }
}
