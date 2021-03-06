package org.usfirst.frc.team2537.robot.auto.vision;

import org.usfirst.frc.team2537.robot.Robot;
import org.usfirst.frc.team2537.robot.drive.Motor;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionRotateCommand extends Command {

	/* when the pi cannot see the target, we spin faster to try to find the target */
	private static final double DEFAULT_PERCENT_OUTPUT = 0.60;
	private static final double CENTER_kP = 8;
	private static final double TURNING_TOLERANCE = 0.01;
	private static final double PI_MOUNT_OFFSET = 0;
	
	private double centerX;
	private Side lastSide;
	private boolean stopAtTarget;
	
	public VisionRotateCommand(Side defaultTurn, boolean stopAtTarget){
		requires(Robot.driveSys);
		this.centerX = Double.POSITIVE_INFINITY;
		this.lastSide = defaultTurn;
		this.stopAtTarget = stopAtTarget;
	}
	
	public VisionRotateCommand(Side defaultTurn) {
		this(defaultTurn, true);
	}
	
	public VisionRotateCommand() {
		this(Side.LEFT);
	}

	@Override
	protected void initialize() {
		System.out.println("starting visionRotate");
	}

	@Override
	protected void execute() {
		Target[] targets = Robot.visionSerial.getVisionPacket();
		for(Target target : targets) {
			System.out.println(target.getBoundingBoxCenter().y +": "+(target.getBoundingBoxCenter().y/240.0 - 1));
		}
		System.out.println();
		/* if we cannot see the target, we spin faster */
		double power = DEFAULT_PERCENT_OUTPUT;
		if(lastSide == Side.LEFT){
			power *= -1;
		}

		if(targets.length > 0){
			Target largestTarget = new Target();
			for(Target target : targets){	// focus on the largest target only
				if(target.getBoundingBoxArea() > largestTarget.getBoundingBoxArea()){
					largestTarget = target;
				}
			}
			centerX = largestTarget.getBoundingBoxCenter().y / 240.0 - 1;// - PI_MOUNT_OFFSET;
			lastSide = centerX < 0 ? Side.LEFT : Side.RIGHT;

			SmartDashboard.putNumber("center", centerX);
			
			power = Math.min(Math.abs(power), Math.abs(centerX*power*CENTER_kP)) * Math.signum(centerX);
		}
		
		Robot.driveSys.setMotors(-power,  Motor.LEFT);
		Robot.driveSys.setMotors(power, Motor.RIGHT);
	}

	@Override
	protected boolean isFinished() {
		System.out.println("centerX: " + centerX);
		if (Math.abs(centerX) >= 0.99) {
			return true;
		}
		return stopAtTarget && Math.abs(centerX) < TURNING_TOLERANCE;
	}

	@Override
	protected void end() {
		Robot.driveSys.setMotors(0, Motor.ALL);
		System.out.println("ending visionROtate");
	}

	@Override
	protected void interrupted() {
		Robot.driveSys.setMotors(0, Motor.ALL);
	}
	
	enum Side {
		LEFT, RIGHT;
	}

}
