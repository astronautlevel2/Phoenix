package org.usfirst.frc.team2537.robot.auto.routes;

import org.usfirst.frc.team2537.robot.auto.DriveStraightCommand;
import org.usfirst.frc.team2537.robot.auto.RotateCommand;
import org.usfirst.frc.team2537.robot.cuber.ExpelCommand;
import org.usfirst.frc.team2537.robot.cuber.LowerFlipperCommand;
import org.usfirst.frc.team2537.robot.vert.VertUpCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SameSideScaleRoute extends CommandGroup {
	public SameSideScaleRoute(){
		addSequential(new DriveStraightCommand(295));
		addSequential(new RotateCommand(90));
		addParallel(new LowerFlipperCommand(), 0.5);
		addParallel(new DriveStraightCommand(-30), 0.5);
		addSequential(new VertUpCommand(675000));
		addSequential(new ExpelCommand(0.8));
	}
}

/*

+------------------------------------------------E+
| XX                                             v|
|X                                               v|
S>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>o|
|                                                 |
|                                                 |
|               +------------+                    |
|               |          +-+           +-------v+
|               |          |             |        |
|               |          +-+           |       XX
|               |          +-+           |       X|
|             +-+          |             |       X|
|           +---|          +-+           |       X|
|         +-----|          |-|           |       X|
|         +-----|          |-|           |       X|
|           +---|          +-+           |       X|
|             +-+          |             |       X|
|               |          +-+           |       X|
|               |          +-+           |       XX
|               |          |             |        |
|               |          +-+           +--------+
|               +------------+                    |
|                                                 |
|                                                 |
|X                                                |
| XX                                              |
+-------------------------------------------------+

*/