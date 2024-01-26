// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeDefaultCommand extends Command {
  Intake intake;
  XboxController controller;
  /** Creates a new IntakeDefaultCommand. */
  public IntakeDefaultCommand(Intake intake, XboxController controller) {
    this.intake = intake;
    this.controller = controller;
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.setIntakeAngle(controller.getXButton() ? 0.12 : controller.getYButton() ? -0.12 : 0);
    intake.setRollingSpeed(controller.getBButton() ? 0.6 : 0);
    intake.setLollipopSpeed(controller.getAButton() ? 0.2 : 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeAngle(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
