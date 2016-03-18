package model.robot;

import model.device.Device;
import model.device.Laser;
import model.device.Sonar;
import model.virtualmap.OccupancyMap;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 * @author Dustin Meijer        (2012)
 * @author Alexander Jeurissen  (2012)
 * @author Davide Brugali       (2002)
 * @version 2.0
 */

public class MobileRobotAI implements Runnable {

    private static final int MAX_STEP_COUNT = 10;

    private final OccupancyMap map;
	private final MobileRobot robot;

    private double position[] = new double[3];
    private double measures[] = new double[360];
    private double sonarMeasures[] = new double[360];

    private int stepCount;


    private PipedInputStream pipeIn;
    private BufferedReader input;
    private PrintWriter output;

	private boolean running;

	public MobileRobotAI(MobileRobot robot, OccupancyMap map) {
		this.map = map;
		this.robot = robot;
        stepCount = 0;

        try {
            pipeIn = new PipedInputStream();
            input = new BufferedReader(new InputStreamReader(pipeIn));
            output = new PrintWriter(new PipedOutputStream(pipeIn), true);
            robot.setOutput(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * In this method the gui.controller sends commands to the robot and its devices.
	 * At the moment all the commands are hardcoded.
	 * The exercise is to let the gui.controller make intelligent decisions based on
	 * what has been discovered so far. This information is contained in the OccupancyMap.
	 */
	public void run() {
		String result;
		this.running = true;
        boolean turning = false;
        double startPosition[] = new double[]{-1, -1, 0};
        System.out.println("intelligence running");

		while (running) {
			try {
                updatePosition();
                if(startPosition[0] == -1 && startPosition[1] == -1){
                    // set initial starting position
                    startPosition = position.clone();
                }

                // Denken
                robot.sendCommand("L1.SCAN");
                result = input.readLine();
                parseMeasures(result, robot.getSensors().get(0), measures);
                map.drawLaserScan(position, measures);

                // Eerst Laser, daarna Sonar.
                // Als de Sonar iets ziet, wat dichterbij is dan dat de Laser heeft kunnen detecteren.
                // Dan zetten we die meting in Measures i.p.v. de meting van de laser op die specifieke Direction.
                robot.sendCommand("S1.SCAN");
                result = input.readLine();
                parseMeasures(result, robot.getSensors().get(1), sonarMeasures);
                parseSonarMeasures(result, measures);
                map.drawSonarScan(position, sonarMeasures);

                //Check positions
                double forward = measures[1];
                double northEast = measures[45];
                double right = measures[90];

                // Wall fix
                if(right < 50) {
                    //If wall is to far
                    if(forward > 20.0) {
                        if(right < 25 || (!turning && northEast < 29.5)){
                            int eastOffset = Math.max(30-(int)Math.floor(right),30-(int)Math.floor(northEast));
                            int distance = (int)Math.floor(eastOffset / Math.sin(Math.toRadians(45)));

                            robot.sendCommand("P1.ROTATELEFT 45");
                            result = input.readLine();

                            robot.sendCommand("P1.MOVEFW " + distance);
                            result = input.readLine();

                            robot.sendCommand("P1.ROTATERIGHT 45");
                            result = input.readLine();
                        } else {
                            robot.sendCommand("P1.MOVEFW " + Math.min((forward - 15.0), 20.0));
                            result = input.readLine();
                        }
                        turning = false;
                    }
                    // Wall ahead, linkerbocht
                    else {
                        robot.sendCommand("P1.ROTATELEFT 90");
                        result = input.readLine();
                        turning = true;
                    }
                }
                // If wall is to far or gone
                else {
                    robot.sendCommand("P1.MOVEFW " + (38.0));
                    result = input.readLine();

                    robot.sendCommand("P1.ROTATERIGHT 90");
                    result = input.readLine();

                    robot.sendCommand("P1.MOVEFW " + (41.0));
                    result = input.readLine();
                }


                // end
                if(stepCount > MAX_STEP_COUNT &&
                        Math.abs(position[0] - startPosition[0]) < 30 &&
                        Math.abs(position[1] - startPosition[1]) < 30) {
                    System.out.println("Done exploring");

                    robot.sendCommand("P1.ROTATELEFT 45");
                    result = input.readLine();

                    robot.sendCommand("P1.MOVEFW " + (5.0));
                    result = input.readLine();

                    robot.sendCommand("P1.ROTATELEFT 225");
                    result = input.readLine();

                    running = false;
                }
                stepCount++;
                // repeat

			} catch (IOException ioe) {
				System.err.println("execution stopped");
				running = false;
			}
		}

	}



    private void updatePosition() throws IOException {
        robot.sendCommand("R1.GETPOS");
        String value = input.readLine();

        int indexInit;
        int indexEnd;
        String parameter;

        indexInit = value.indexOf("X=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[0] = Double.parseDouble(parameter.substring(0, indexEnd));

        indexInit = value.indexOf("Y=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[1] = Double.parseDouble(parameter.substring(0, indexEnd));

        indexInit = value.indexOf("DIR=");
        parameter = value.substring(indexInit + 4);
        position[2] = Double.parseDouble(parameter);
    }


    private void parseSonarMeasures(String value, double[] measures) {
        if (value.length() >= 5) {
            value = value.substring(5);  // removes the "SCAN " keyword

            StringTokenizer tokenizer = new StringTokenizer(value, " ");

            double distance;
            int direction;
            while (tokenizer.hasMoreTokens()) {
                distance = Double.parseDouble(tokenizer.nextToken().substring(2));
                direction = (int) Math.round(Math.toDegrees(Double.parseDouble(tokenizer.nextToken().substring(2))));
                if (direction == 360) {
                    direction = 0;
                }
                if (distance < measures[direction]) {
                    measures[direction] = distance;
                }
            }
        }

    }

    private void parseMeasures(String value, Device device, double measures[]) {
        int range;
        if (device instanceof Laser) {
            range = Laser.RANGE;
        } else if (device instanceof Sonar) {
            range = Sonar.RANGE;
        } else {
            // Default range naar 100
            range = 100;
        }

		for (int i = 0; i < 360; i++) {
            measures[i] = range;
        }
		if (value.length() >= 5) {
			value = value.substring(5);  // removes the "SCAN " keyword

			StringTokenizer tokenizer = new StringTokenizer(value, " ");

			double distance;
			int direction;
			while (tokenizer.hasMoreTokens()) {
				distance = Double.parseDouble(tokenizer.nextToken().substring(2));
				direction = (int) Math.round(Math.toDegrees(Double.parseDouble(tokenizer.nextToken().substring(2))));
				if (direction == 360) {
					direction = 0;
				}
				measures[direction] = distance;
				// Printing out all the degrees and what it encountered.
//				System.out.println("direction = " + direction + " distance = " + distance);
			}
		}
	}


}
