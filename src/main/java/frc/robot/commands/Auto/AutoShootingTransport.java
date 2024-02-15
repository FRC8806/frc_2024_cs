// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoShootingTransport extends Command {
  Shooter shooter;
  NetworkTable shooterLimelight;
  PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI, ShooterConstants.shooterKD);
  /** Creates a new AutoShootingTransport. */
  public AutoShootingTransport(Shooter shooter, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.shooterLimelight = shooterLimelight;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setTransportSpeed(1);
    shooter.setShootingSpeed(1);

    double ty = shooterLimelight.getEntry("ty").getDouble(0);
    double targetPosition = 49.9 - 3.73 * ty - 0.0362 * ty * ty;
    shooter.setShooterAngle(shooterPID.calculate(shooter.getAnglePosition(), targetPosition));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
