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

	private final OccupancyMap map;
	private final MobileRobot robot;

    private double position[] = new double[3];
    private double measures[] = new double[360];

    private PipedInputStream pipeIn;
    private BufferedReader input;
    private PrintWriter output;

	private boolean running;

	public MobileRobotAI(MobileRobot robot, OccupancyMap map) {
		this.map = map;
		this.robot = robot;
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
		while (running) {
			try {

//      ases where a variable value is never used after its assignment, i.e.:
				System.out.println("intelligence running");

//                for (int i = 0; i < 360; i++) {
//                    robot.sendCommand(String.format("P1.ROTATERIGHT 1", i));
//                    result = input.readLine();
//                }


                // SCANNEN
                // WAARNEMEN
                for (int i = 0; i < 10000000; i++) {
                    updatePosition();

                    // Denken
                    robot.sendCommand("L1.SCAN");
                    result = input.readLine();
                    parseMeasures(result, measures);
                    map.drawLaserScan(position, measures);

                    double[] newPosition = zoekverstepunt(measures);
                    if (newPosition[0] != position[0] || newPosition[1] != position[1]) {
                        // nog meer denken
                        // hier logica om te berekenen hoe we moeten moven

                        // Do the Y
                        double yDiff = position[1] - newPosition[1];

                        if (yDiff > 0) {
                            // positive = WEST
                            robot.sendCommand("P1.ROTATELEFT 90");
                            result = input.readLine();
                            robot.sendCommand(String.format("P1.MOVEFW %s", yDiff));
                            result = input.readLine();
                        } else if (yDiff < 0) {
                            // negative = EAST
                            robot.sendCommand("P1.ROTATERIGHT 90");
                            result = input.readLine();
                            robot.sendCommand(String.format("P1.MOVEFW %s", Math.abs(yDiff)));
                            result = input.readLine();
                        }


                        // Do the X
                        double xDiff = position[0] - newPosition[0];
                        if (xDiff > 0) {
                            // positive == NORTH
                            robot.sendCommand(String.format("P1.MOVEFW %s", xDiff));
                            result = input.readLine();
                        } else if (xDiff < 0) {
                            // negative == SOUTH
                            robot.sendCommand(String.format("P1.MOVEBW %s", Math.abs(xDiff)));
                            result = input.readLine();
                        }

                        // doen
                    } else {
                        robot.sendCommand("P1.MOVEFW 10");
                        result = input.readLine();
                    }
                    // repeat

                }

                updatePosition();

				robot.sendCommand("L1.SCAN");
				result = input.readLine();
				parseMeasures(result, measures);
				map.drawLaserScan(position, measures);
				this.running = false;
			} catch (IOException ioe) {
				System.err.println("execution stopped");
				running = false;
			}
		}

	}

    private boolean zoekPunt(double[] measures) {
        updatePosition();

        return false;
    }

    /**
     * @param measures
     * @return
     */
    private double[] zoekverstepunt(double[] measures) {
        // Initieel gelijk aan die van de robot
        // indien er geen obstacles zijn gevonden
        double obstaclePosition[] = position.clone();
        double maxDistance = -1;
        int direction = -1;
        // Zoek het verste punt
        for (int i = 0; i < measures.length; i++) {
            if (measures[i] < 100 && measures[i] > maxDistance) {
                // positie gevonden
                maxDistance = measures[i];
                direction = i;
            }
        }
        if (maxDistance != -1 && direction != -1) {
            double aanliggend = Math.cos(direction) * maxDistance;
            double overstaand = Math.sin(direction) * maxDistance;
            System.out.println("Aanliggend: " + aanliggend);
            System.out.println("Overstaand: " + overstaand);

            // bereken de position van het opstacle
            obstaclePosition[0] = position[0] + overstaand;
            obstaclePosition[1] = position[1] + aanliggend;
            obstaclePosition[2] = position[2];

        }
        return obstaclePosition;
    }

    private double[] zoekdichtstepunt(double[] measures) {
        updatePosition();
        double obstaclePosition[] = position;
        double minDistance = 100;
        int direction = -1;
        // Zoek het verste punt
        for (int i = 0; i < measures.length; i++) {
            if (measures[i] < 100 && measures[i] < minDistance) {
                // positie gevonden
                // Check of het een obstacle is
                // update dan de list
                minDistance = measures[i];
                direction = i;
            }
        }
        if (minDistance != 100 && direction != -1) {
            // bereken de position van het opstacle

            obstaclePosition[0] = 0;
            obstaclePosition[1] = 0;
            obstaclePosition[2] = position[2];

        }
        return obstaclePosition;
    }

    private void updatePosition() {
        robot.sendCommand("R1.GETPOS");
        String value = null;
        try {
            value = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

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
				System.out.println("direction = " + direction + " distance = " + distance);
			}
		}
	}


}
