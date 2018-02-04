package org.usfirst.frc.team2537.robot.vert;

import org.usfirst.frc.team2537.robot.Ports;
import org.usfirst.frc.team2537.robot.input.HumanInput;
import org.usfirst.frc.team2537.robot.resources.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VertSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private Encoder vertEnc;
	private CANTalon vertMotorOne;
	private CANTalon vertMotorTwo;
	private Ultrasonic ultrasonic;
	private PowerDistributionPanel PDP;
	private PowerDistributionPanel channelTwo;
	double current;

	public VertSubsystem() { 
		vertEnc = new Encoder(Ports.VERT_ENC_A, Ports.VERT_ENC_B, false, Encoder.EncodingType.k4X);
		vertMotorOne = new CANTalon(Ports.VERT_MOTOR_ONE);
		vertMotorTwo = new CANTalon(Ports.VERT_MOTOR_TWO);
		PDP = new PowerDistributionPanel(Ports.PDP);
		ultrasonic = new Ultrasonic(Ports.ULTRASONIC_INPUT, Ports.ULTRASONIC_OUTPUT); 
	}

	public void initDefaultCommand() {

	}
	
	//makes sure command works when button held
	public void registerButtons() {
		HumanInput.registerWhileHeldCommand(HumanInput.raiseButton, new VertUpCommand());
		HumanInput.registerWhileHeldCommand(HumanInput.lowerButton, new VertDownCommand());

	}

	//receives distance robot travels with encoders
	public int getDistance() {
		return vertEnc.get();
	}

	//sets speed of vertMotors
	public void setVertMotors(double speed) {
		vertMotorOne.set(speed);
		vertMotorTwo.set(-speed);
	}
	
	//returns amp for channel 5
	public double getAmperageOne() {
		return PDP.getCurrent(5);
	}

	//returns amp for channel 4
	public double getAmperageTwo() {
		return PDP.getCurrent(4);
		
	}

	//returns distance of object from robot
	public double getUltrasonic() {
		return ultrasonic.getRangeInches();
	}

}
