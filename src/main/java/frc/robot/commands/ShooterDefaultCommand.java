package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterDefaultCommand extends Command {
  Shooter shooter;
  XboxController operatorController;
  /** Creates a new ShooterDefaultCommand. */
  public ShooterDefaultCommand(Shooter shooter, XboxController operatorController) {
    this.shooter = shooter;
    this.operatorController = operatorController;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setShootingSpeed(operatorController.getRightTriggerAxis());
    shooter.setShooterAngle(operatorController.getRightBumper() ? 0.15 : operatorController.getLeftBumper() ? -0.15 : 0);
    shooter.setTransportSpeed(operatorController.getLeftTriggerAxis());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
