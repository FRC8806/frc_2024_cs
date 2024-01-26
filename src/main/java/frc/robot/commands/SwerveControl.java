// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class SwerveControl extends Command {
  DriveTrain driveTrain;
  XboxController controller;
  /** Creates a new SwerveControl. */
  public SwerveControl(DriveTrain driveTrain, XboxController controller) {
    this.driveTrain = driveTrain;
    this.controller = controller;
    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = -controller.getLeftY() * Constants.kMaxThrottleSpeed;
    double ySpeed = -controller.getLeftX() * Constants.kMaxThrottleSpeed;
    double rSpeed = -controller.getRightX() * 0.6 * Constants.kMaxRotationSpeed;
    if(Math.abs(xSpeed) <= 0.2 && Math.abs(ySpeed) <= 0.2 && Math.abs(rSpeed) <= 0.4) {
      xSpeed = 0;
      ySpeed = 0;
      rSpeed = 0;
    }
    driveTrain.drive(new ChassisSpeeds(xSpeed,ySpeed,rSpeed));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(new ChassisSpeeds());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
