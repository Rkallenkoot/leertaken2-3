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
                boolean turning = false;

                // Denken
                robot.sendCommand("L1.SCAN");
                result = input.readLine();
                parseMeasures(result, measures);
                map.drawLaserScan(position, measures);

                double forward = measures[0];
                double right = measures[90];
                double southEast = measures[145];

                System.out.println("Right:" + right);
                System.out.println("southEast: " + southEast);
                System.out.println("Forward: " + forward);

                if(right < 100){

                    if(forward < 30){
                        // 45 graden naar links
                        robot.sendCommand("P1.ROTATELEFT 90");
                        result = input.readLine();

                    } else if (!turnedRight && right > 30 && southEast > 50) {
//                        // TODO: Deze kan subtielier
//                        // rotate 90 naar rechts
//                        robot.sendCommand("P1.ROTATERIGHT 90");
//                        result = input.readLine();
//
//                        // movefw 20
//                        robot.sendCommand("P1.MOVEFW 10");
//                        result = input.readLine();
//
//                        // rotate 90 links
//                        robot.sendCommand("P1.ROTATELEFT 90");
//                        result = input.readLine();
                        double overstaande = right - 25;
                        double schuin = Math.sin(Math.toRadians(45)) * overstaande;

                        robot.sendCommand("P1.ROTATERIGHT 45");
                        result = input.readLine();

                        // movefw 20
                        robot.sendCommand(String.format("P1.MOVEFW %s", schuin));
                        result = input.readLine();

                        robot.sendCommand("P1.ROTATELEFT 45");
                        result = input.readLine();
                    }
                    if(right < 20 ){
                        double distance = 20 - right;
                        double schuin = Math.sin(Math.toRadians(45)) * distance;
                        // rotate 45 naar links

                        robot.sendCommand("P1.ROTATELEFT 45");
                        result = input.readLine();
                        // movefw 20

                        robot.sendCommand(String.format("P1.MOVEFW %s", 5));
                        result = input.readLine();

                        // rotate 45 right
                        robot.sendCommand("P1.ROTATERIGHT 45");
                        result = input.readLine();
                    }

                    double distance = 10;

                    robot.sendCommand(String.format("P1.MOVEFW %s", distance));
                    result = input.readLine();

                    turnedRight = false;
                } else {
                    if (southEast > 50) {
                        // draai 90 rechts
                        robot.sendCommand("P1.ROTATERIGHT 45");
                        result = input.readLine();

                        robot.sendCommand("P1.MOVEFW 5");
                        result = input.readLine();

                        robot.sendCommand("P1.ROTATELEFT 45");
                        result = input.readLine();

                        robot.sendCommand("P1.MOVEFW 10");
                        result = input.readLine();

                        robot.sendCommand("P1.ROTATERIGHT 45");
                        result = input.readLine();

                        robot.sendCommand("P1.MOVEFW 5");
                        result = input.readLine();

                        robot.sendCommand("P1.ROTATERIGHT 45");
                        result = input.readLine();

                        robot.sendCommand("P1.MOVEFW 10");
                        result = input.readLine();


                        turnedRight = true; // Na een rechterturn
                    }
                    // moet hij niet meer proberen dichterbij de kant te gaan
                    // stukje naar voren
                    if (forward - 20 >= 0) {
                        robot.sendCommand("P1.MOVEFW 20");
                        result = input.readLine();
                    } else {
                        robot.sendCommand("P1.MOVEFW 10");
                        result = input.readLine();
                    }

                }
                updatePosition();
                stepCount++;

                if(stepCount > MAX_STEP_COUNT && Math.abs(position[0]-startPosition[0]) < 30 && Math.abs(position[1]-startPosition[1]) < 30){
                    System.out.println("Done exploring");
                    robot.sendCommand("P1.MOVEFW 5");
                    result = input.readLine();

                    robot.sendCommand("P1.ROTATERIGHT 360");
                    result = input.readLine();

                    running = false;
                }
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
