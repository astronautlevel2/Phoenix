public class DriveSubsystem extends Subsystem {
	private TalonSRX leftWheel;
	private TalonSRX rightWheel;

	public DriveSubsystem() {
		leftWheel = new TalonSRX(Ports.FRONT_LEFT_DRIVE_MOTOR);
		rightWheel = new TalonSRX(Ports.FRONT_RIGHT_DRIVE_MOTOR);
	}
	
	@Override
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveCommand());
	}
	public void setMotors(double speed) {
		leftWheel.set(speed);
		rightWheel.set(speed);
	}	
}
