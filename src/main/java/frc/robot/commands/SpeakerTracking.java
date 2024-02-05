package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Shooter;

public class SpeakerTracking extends Command {
  private Shooter shooter;
  private NetworkTable table;
  private Chassis driveTrain;
  /** Creates a new SpeakerTracking. */
  public SpeakerTracking(Shooter shooter, NetworkTable table, Chassis driveTrain) {
    this.shooter = shooter;
    this.table = table;
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double tx = table.getEntry("tx").getDouble(0);
    double ty = table.getEntry("ty").getDouble(0);
    shooter.setShooterAngle(-ty/50);
    driveTrain.drive(new ChassisSpeeds(0, 0, -tx/10));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setShooterAngle(0);
    shooter.setTransportSpeed(0);
    shooter.setShootingSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
