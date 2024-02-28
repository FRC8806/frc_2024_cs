// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autos;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoAllStop extends Command {
  private Shooter shooter;
  private Intake intake;
  private Chassis chassis;
  /** Creates a new AutoAllStop. */
  public AutoAllStop(Shooter shooter, Intake intake, Chassis chassis) {
    this.shooter = shooter;
    this.intake = intake;
    this.chassis = chassis;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setAngleSpeed(0);
    shooter.setFlyWheelSpeed(0);
    shooter.setTransportSpeed(0);
    intake.setAngleSpeed(0);
    intake.setMicSpeed(0);
    intake.setRollingSpeed(0);
    chassis.drive(new ChassisSpeeds(0,0,0));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
