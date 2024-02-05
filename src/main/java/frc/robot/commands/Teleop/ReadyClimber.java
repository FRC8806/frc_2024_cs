// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Teleop;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class ReadyClimber extends Command {
  private Climber climber;
  private Supplier<Boolean> climbButton;
  /** Creates a new TeleClimb. */
  public ReadyClimber(Climber climber, Supplier<Boolean> climbButton) {
    this.climber = climber;
    this.climbButton = climbButton;
    addRequirements(climber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.setToPosition(ClimberConstants.SETUP_POSE);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return climbButton.get();
  }
}
