public class DriveCommand extends Command {
	public DriveCommand() {
		requires(Robot.driveSys);
	}
	// We don't need to initialize anything for this command
	protected void initialize() {}
	
	protected void execute() {
		Robot.driveSys.setMotors(.5); // sets both motors to 50% speed
	}

	protected boolean isFinished() { return false; } // always runs
	
	protected void end() {
		Robot.driveSys.setMotors(0);
	}
	
	protected void interrupted() {
		end(); // stop motors when finished		
	}
}
