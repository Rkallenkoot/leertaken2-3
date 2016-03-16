package model.robot;

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

        double startPosition[] = new double[]{-1, -1, 0};

        System.out.println("intelligence running");
        boolean turnedRight = false;
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
                parseMeasures(result, measures);
                map.drawLaserScan(position, measures);

                //Check positions
                double forward = measures[0];
                double right = measures[90];

                //System.out.println("Forward: " + forward);
                //System.out.println("Right:" + right);

                //If wall is near
                if(right < 50) {
                    //If wall is to far
                    if(forward > 15.0) {
                        robot.sendCommand("P1.MOVEFW " + Math.min((forward - 15.0), 20.0));
                        result = input.readLine();
                    }
                    //If near wall
                    else {
                        robot.sendCommand("P1.ROTATELEFT 90");
                        result = input.readLine();
                    }
                }
                //If wall is to far or gone
                else {
                    robot.sendCommand("P1.MOVEFW " + (41.0));
                    result = input.readLine();

                    robot.sendCommand("P1.ROTATERIGHT 90");
                    result = input.readLine();

                    robot.sendCommand("P1.MOVEFW " + (41.0));
                    result = input.readLine();
                }

                if(stepCount > MAX_STEP_COUNT &&
                        Math.abs(position[0] - startPosition[0]) < 30 &&
                        Math.abs(position[1] - startPosition[1]) < 30) {
                    System.out.println("Done exploring");

                    robot.sendCommand("P1.ROTATELEFT 270");
                    result = input.readLine();

                    running = false;
                }
                stepCount++;
                // repeat
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


    private void parseMeasures(String value, double measures[]) {
		for (int i = 0; i < 360; i++) {
			measures[i] = 100.0;
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
