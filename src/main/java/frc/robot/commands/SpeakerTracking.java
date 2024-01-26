// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

public class SpeakerTracking extends Command {
  Shooter shooter;
  NetworkTable table;
  DriveTrain driveTrain;
  /** Creates a new SpeakerTracking. */
  public SpeakerTracking(Shooter shooter, NetworkTable table, DriveTrain driveTrain) {
    this.shooter = shooter;
    this.table = table;
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double tx = table.getEntry("tx").getDouble(0);
    double ty = table.getEntry("ty").getDouble(0);
    shooter.setShooterAngle(-ty/50);
    driveTrain.drive(new ChassisSpeeds(0, 0, -tx/10));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setShooterAngle(0);
    shooter.setTransportSpeed(0);
    shooter.setShootingSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
