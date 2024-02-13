// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Teleop;

import java.util.function.Supplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Shooter;

public class TeleAMP extends Command {
  Shooter shooter;
  Chassis chassis;
  Supplier<Double> lt, xAxis, yAxis;
  NetworkTable shooterLimelight;
  /** Creates a new TeleAMP. */
  public TeleAMP(Shooter shooter, Chassis chassis, Supplier<Double> lt, Supplier<Double> xAxis, Supplier<Double> yAxis, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.chassis = chassis;
    this.lt = lt;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.shooterLimelight = shooterLimelight;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setAMPAngle();
    shooterLimelight.getEntry("pipeline").setNumber(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double tx = shooterLimelight.getEntry("tx").getDouble(0);
    double ySpeed = -onDeadband(yAxis.get(), SwerveConstants.deadband);
    ySpeed *= SwerveConstants.kMaxThrottleSpeed;
    double zSpeed = (0-chassis.getPose().getRotation().getRotations())*0.05;

    shooter.setShootingSpeed(0.18);
    shooter.setTransportSpeed(lt.get());
    chassis.drive(new ChassisSpeeds(-tx/10,ySpeed,zSpeed));
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private double onDeadband(double value, double deadband) {
    if (value > 0) {
      value -= deadband;
      value = value < 0 ? 0 : value;
    }
    if (value < 0) {
      value += deadband;
      value = value > 0 ? 0 : value;
    }
    return value;
  }
}
