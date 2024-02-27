// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoSetup extends Command {
  private Intake intake;
  private Shooter shooter;
  public AutoSetup(Intake intake, Shooter shooter) {
    this.intake = intake;
    this.shooter = shooter;
    addRequirements(intake);
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.setIntakeDown();
    shooter.setAnglePosition(ShooterConstants.AUTO_FIRST_NOTE_POSITION);
    shooter.setFlyWheelSpeed(0.9);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
