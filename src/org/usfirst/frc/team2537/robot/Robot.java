package org.usfirst.frc.team2537.robot;

import org.usfirst.frc.team2537.robot.auto.AutoChooser;
import org.usfirst.frc.team2537.robot.auto.Navx;
import org.usfirst.frc.team2537.robot.auto.vision.CoordinateSystems;
import org.usfirst.frc.team2537.robot.auto.vision.VisionInput;
import org.usfirst.frc.team2537.robot.climb.ClimbSubsystem;
import org.usfirst.frc.team2537.robot.cuber.CuberSubsystem;
import org.usfirst.frc.team2537.robot.drive.DriveSubsystem;
import org.usfirst.frc.team2537.robot.input.Cameras;
import org.usfirst.frc.team2537.robot.ramp.RampSubsystem;
import org.usfirst.frc.team2537.robot.vert.VertSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static DriveSubsystem driveSys;
	public static VertSubsystem vertSys;
	public static ClimbSubsystem climbSys;
	public static RampSubsystem rampSys;
	public static CuberSubsystem cuberSys;

//	public static PowerDistributionPanel pdp;

	public static SmartDashboard smartDashboard;
	public static VisionInput visionSerial;
	public static Cameras cameras;

	public static long startTime;
	public static String fmsData="OOO";

	private static AutoChooser autoChooser;

	public void robotInit() {
		driveSys = new DriveSubsystem();
		driveSys.initDefaultCommand();
	}

	@Override
	public void teleopPeriodic() {
		// All subsystems automatically register themselves with the scheduler
		// So all you have to do is call Scheduler.getInstance().run()
		// Every loop in teleopPeriodic
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {
	
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putBoolean("Hall Effect", cuberSys.getHallEffectOne());
		SmartDashboard.putBoolean("Bottom reed switch", vertSys.getBottomSwitch());
		SmartDashboard.putBoolean("Top reid switch", vertSys.getTopSwitch());
		if (visionSerial.getVisionPacket().length!=0) {
			SmartDashboard.putNumber("RPi Value", visionSerial.getVisionPacket()[0].getBoundingBoxCenter().getY(CoordinateSystems.CARTESIAN));
		}
	}
	

}
