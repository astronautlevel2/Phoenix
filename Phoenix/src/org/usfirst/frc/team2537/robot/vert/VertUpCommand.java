package org.usfirst.frc.team2537.robot.vert;

import org.usfirst.frc.team2537.robot.Robot;
import java.util.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VertUpCommand extends Command {

	private static final int AMP_LIMIT = 5;  //5 amps HECKKA TBD
	
	public VertUpCommand() {
		requires(Robot.vertSys);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		Robot.vertSys.initDefaultCommand();
		// stops bot when it exceeds amp limit for channel 5
		if (Robot.vertSys.getCurrentOne() >= AMP_LIMIT) {
			Robot.vertSys.setVertMotors(0);
		} else {
			Robot.vertSys.setVertMotors(Robot.vertSys.targetVelocity); //PID calculated speed
		}
		// stops bot when it exceeds amp limit for channel 4
		if (Robot.vertSys.getCurrentTwo() >= AMP_LIMIT) {
			Robot.vertSys.setVertMotors(0);
		} else {
			Robot.vertSys.setVertMotors(Robot.vertSys.targetVelocity);
		}
		
		//if(org.usfirst.frc.team2537.robot.Ports.VERT_LOWER_BUTTON.)
		
		//checks how long the motor has gone without any joystick inputs; currently, it only checks if the cuber is falling and 
		
	
		//unused detection method that only sets motors to a constant upward speed
		//double testSpeed = Robot.vertSys.getSpeedVertMotorOne();
		//if(testSpeed < 0) {
			//Robot.vertSys.setVertMotors(.1);
		//}
	}

	protected boolean isFinished() {

		return (Robot.vertSys.getLimitSwitchUp()); //this prevents actuator from moving too far up and breaking the robot.

	}

	protected void end() {
		Robot.vertSys.setVertMotors(0); //turns off vertActuator whenever something listed in isFinished goes wrong 
	}

	protected void interrupted() {
		Robot.vertSys.setVertMotors(0); //turns off vertActuator whenever switching to different command
	}
}
