package org.usfirst.frc.team2537.robot.cuber;

import org.usfirst.frc.team2537.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LowerFlipperCommand extends Command {


	public LowerFlipperCommand() {
		requires(Robot.cuberSys); // requires cuberSys variables and methods
	}

	@Override
	protected void initialize() {
		System.out.println("trying to lower");
		Robot.cuberSys.setLiftMotor(-1); // initializes speed of lift motor to lower


	}

	@Override
	protected void execute() {

	}

	protected boolean isFinished() { // returns true if motor turns over or equal to 90 degrees or when flywheel
		return false;
	}

	protected void end() {

		Robot.cuberSys.setLiftMotor(0); // sets lift motor speed to zero
		System.out.println("hi");

	}

	protected void interrupted() { // sets lift motor speed to zero

		Robot.cuberSys.setLiftMotor(0);

	}
}
