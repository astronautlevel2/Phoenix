package org.usfirst.frc.team2537.robot.cuber;

import org.usfirst.frc.team2537.robot.Ports;
import org.usfirst.frc.team2537.robot.Robot;
import org.usfirst.frc.team2537.robot.input.HumanInput;
import org.usfirst.frc.team2537.robot.resources.CANTalon;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CuberSubsystem extends Subsystem {
	private CANTalon flywheelMotorLeft; 
	private CANTalon flywheelMotorRight;
	private CANTalon liftMotor;
	private Ultrasonic cuberUltron;
	private AnalogInput cuberIr;
	public static final double FLYWHEEL_SPEED = .5;
	public static final double FLYWHEEL_CURRENT_LIMIT = 30; // TODO: determine max amps
	public static final double CUTOFF_DISTANCE = 2; // TODO: determine cutoff distance
	public static final int ULTRASONIC_RANGE = 3;
	public static final double FLIPPER_TIMEOUT = 5000; //TODO: Figure this one out

	
	public CuberSubsystem() {
		flywheelMotorLeft = new CANTalon(Ports.FLYWHEEL_MOTOR_LEFT);
		flywheelMotorRight = new CANTalon(Ports.FLYWHEEL_MOTOR_RIGHT);
		liftMotor = new CANTalon(Ports.WINDOW_MOTOR);
		
		cuberUltron = new Ultrasonic(Ports.CUBER_ULTRASONIC_TRIGGER, Ports.CUBER_ULTRASONIC_ECHO);
		cuberIr = new AnalogInput(Ports.CUBER_IR);
		
		liftMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		liftMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		
	}
	
	public void initDefaultCommand() {
		
	}
	
	public void registerButtons() { 
		HumanInput.registerWhileHeldCommand(HumanInput.cuberPickUpButton, new PickUpCommand());
		HumanInput.registerWhileHeldCommand(HumanInput.cuberExpelButton, new ExpelCommand());
		HumanInput.registerWhenPressedCommand(HumanInput.cuberFlipDownButton, new LowerFlipperCommand());
		HumanInput.registerWhenPressedCommand(HumanInput.cuberFlipUpButton, new LiftFlipperCommand());
	}
	
	public void setFlywheelMotors(double speed) {
		flywheelMotorLeft.set(speed);
		flywheelMotorRight.set(speed);
	}

	public void setLiftMotor(double speedLift) {
			liftMotor.set(speedLift);  			
	}
	
	
	public double getLeftFlywheelCurrent() {
		return Robot.pdp.getCurrent(Ports.LEFT_FLYWHEEL_PDP_CHANNEL);
		 
	}
	
	public double getRightFlywheelCurrent() { //returns amps of right flywheel motor
		return Robot.pdp.getCurrent(Ports.RIGHT_FLYWHEEL_PDP_CHANNEL);
		
	}
	
	public double getUltrasonicInches() {
		return cuberUltron.getRangeInches();
	}
	
	

	
	public double getIrVoltage() {
		return cuberIr.getVoltage();
	}
	
}
