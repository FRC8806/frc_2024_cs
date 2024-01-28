// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class ZeroEncoder extends Command {
  Elevator elevator;
  XboxController operatorController;
  /** Creates a new ZeroEncoder. */
  public ZeroEncoder(Elevator elevator, XboxController operatorController) {
    this.elevator = elevator;
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
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elevator.zeroElevatorEncoder();
    SmartDashboard.putBoolean("reset", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
