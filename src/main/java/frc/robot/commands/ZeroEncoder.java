// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ZeroEncoder extends Command {
  Elevator elevator;
  Intake intake;
  Shooter shooter;
  XboxController operatorController;
  /** Creates a new ZeroEncoder. */
  public ZeroEncoder(Elevator elevator, Intake intake, Shooter shooter, XboxController operatorController) {
    this.elevator = elevator;
    this.intake = intake;
    this.shooter = shooter;
    this.operatorController = operatorController;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("reset", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elevator.setElevatorLeftOutOfLimit(operatorController.getLeftY()*0.5);
    elevator.setElevatorRightOutOfLimit(-operatorController.getRightY()*0.5);
    intake.setIntakeOutOfLimit(0);
    shooter.setShooterOutOfLimit(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevator.zeroElevatorEncoder();
    intake.zeroIntakeEncoder();
    shooter.zeroShooterEncoder();
    SmartDashboard.putBoolean("reset", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
