# FRC 2023 Logger/Visualizer


## Benefits/Capabilities of Logger

- remote subscription to the NetworkTable of the robot: can run on your own computer.

- nondisruptive logging: detects changes in the NetworkTable and saves them in log files within the "logs" directory in the project.

- configurable: reads "src/main/deploy/configuration.json" to find the IP address of the robot and the NetworkTable entries to monitor. Edit that file to configure the Logger differently.

- service to Visualizers: runs as a server that can broadcast NetworkTable changes to (remote) Visualizers.

## Benefits/Capabilities of Visualizer

- realtime monitoring of the robot: can connect to the Logger to obtain changes in the NetworkTable of the robot.

- graphical user interface: displays the current robot pose, values of NetworkTable entries, preregistered target poses (a set of positions and directions to which you would like to align the robot), and a mapping of joystick buttons to a chosen set of target poses (e.g., button[3] to target "3"). You can change that mapping by using the lists in the graphical user interface (this change is then written to the NetworkTable which the robot can read).

- configurable: reads "src/main/deploy/configuration.json" to obtain the preregistered target poses and mapping of joystick buttons to target poses. Edit that file to configure the Visualizer differently.

- replay: can read log files and display the past changes in the robot pose and NetworkTable (see "src/main/java/frc/robot/replay/VisualizerReplay.java").

- simulations: can perform simulations (e.g., pose estimation) based on the current/past changes in the robot pose and NetworkTable (see "src/main/java/frc/robot/replay/VisualizerReplayPoseEstimated.java").

## Starting Logger
- In the menu, choose "Run" and "Run Without Debugging".
- You will see in TERMINAL something like (which means the Logger is working):
 ```lang-js
RoboRIO IP address: 10.0.20.2
9 subscriptions read from "./src/main/deploy/configuration.json".

NT: starting network client
NetworkTable subscriptions:
logger; heartbeat; 0
SmartDashboard; target(button[0]); ""
SmartDashboard; target(button[1]); ""
SmartDashboard; target(button[2]); ""
limelight; botpose; [0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
SmartDashboard; Pose (Estimated); ""
SmartDashboard; Pose (LimeLight); ""
SmartDashboard; Wheel Encoder Positions; ""
SmartDashboard; Target ID; ""

connections: [10.0.20.2]
progress: 0/1800.0
progress: 1/1800.0
progress: 2/1800.0
progress: 3/1800.0
progress: 4/1800.0
 ```


## Starting Visualizer
- Open "src/main/java/frc/robot/Visualizer.java"
- In the file, press "Run" above:
 ```lang-js
public static void main(String[] args) { 
 ```
 - You will see in TERMINAL something like (which means the Logger is working):
 ```lang-js
3 target chooser labels read from "./src/main/deploy/configuration.json".
10 poses read from "./src/main/deploy/configuration.json".
8 tags read from "./src/main/deploy/frc2023.fmap".
Visualizer connected to localhost:10000
 ```

## Stopping Logger/Visualizer
- Press the "Stop" button (shown as a red rectangle) in VSCode or
- In TERMINAL, press the C key while holding down the Ctrly key.
