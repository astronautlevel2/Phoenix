public class DriveCommand extends Command {
	
	public DriveCommand() {
		requires(Robot.driveSys);
	}
	@Override
	protected void initialize() {
		// We don't need to initialize anything for this command
	}
	
	@Override 
	protected void execute() {
		Robot.driveSys.setMotors(.5); // sets both motors to 50% speed
	}
	
	@Override
	protected boolean isFinished() {
		return false; // always runs
	}
	
	protected void end() {
		Robot.driveSys.setMotors(0);
	}
	
	protected void interrupted() {
		end(); // stop motors when finished		
	}
}
