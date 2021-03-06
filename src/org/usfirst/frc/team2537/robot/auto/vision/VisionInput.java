package org.usfirst.frc.team2537.robot.auto.vision;

import org.usfirst.frc.team2537.robot.Ports;
import org.usfirst.frc.team2537.robot.Robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionInput extends Subsystem {
	public final boolean DEBUG = true;
	public final int BAUDRATE = 38400;

	private SerialPort serial;
	private String buffer;
	private String lastCompletedString;
	
	public VisionInput() {
		serial = new SerialPort(BAUDRATE, Ports.RASPI);
		buffer = "";
		lastCompletedString = "";
	}
	
	@Override
	public void initDefaultCommand() {
		// TODO Auto-generated method stub
		this.setDefaultCommand(new VisionCommand());
	}

	public Target[] getVisionPacket() {
		return decodeVisionPacket(lastCompletedString);
	}
	
	public void clearCache() {
		buffer="";
		lastCompletedString="";
	}

	public static Target[] decodeVisionPacket(String packetToDecode) {
		try {
			if (packetToDecode.equals("") || packetToDecode == null) {
				return new Target[0];
			}
			
			String[] stringTargets = packetToDecode.split("#");
			Target[] targets = new Target[stringTargets.length];
			for (int i = 0; i < stringTargets.length; i++) {
				String[] pointArr = stringTargets[i].split("\\|");
				Point[] points = new Point[pointArr.length];
				
				int leftX = 640;
				int rightX = 0;
				for (int j = 0; j < pointArr.length; j++) {
					String[] coordinates = pointArr[j].split(",");
					points[j] = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
					if(points[j].x < leftX){
						leftX = points[j].x;
					}
					if(points[j].x > rightX){
						rightX = points[j].x;
					}
				}
				
				targets[i] = new Target(points);
			}
			return targets;
		}
		catch (Exception e) {
			System.out.println("fuck");
			return new Target[0];
		}
	}

	public void addToBuffer() { // should run periodically
		try { // wrapped in a try/catch because if the pi inits before the rio it'll crash otherwise
			if (serial.getBytesReceived() > 0) {
				String stringToAppend = serial.readString();
				serial.flush();
//				System.out.println(stringToAppend);
				int packetEnd = stringToAppend.lastIndexOf('<');
				if(packetEnd == -1){
					buffer += stringToAppend;
				} else {
					
					int packetStart = stringToAppend.lastIndexOf('<', packetEnd - 1);
					if(packetStart == -1){
						lastCompletedString = buffer + stringToAppend.substring(0, packetEnd);
					} else {
						lastCompletedString = stringToAppend.substring(packetStart + 1, packetEnd);
					}
					
					buffer = stringToAppend.substring(packetEnd + 1);
				}
			}
		} catch (Exception e) {
//			System.out.println("serial exception " + e.getMessage());
		}
	}



	/*
	 * public void sendVisionPacket(Point[] packetsToSend) {
	 * serial.writeString(encodeVisionPacket(packetsToSend)); }
	 */
}

class VisionCommand extends Command {

	public VisionCommand() {
		requires(Robot.visionSerial);
	}
	
	@Override
	protected void execute() {
		Robot.visionSerial.addToBuffer();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
