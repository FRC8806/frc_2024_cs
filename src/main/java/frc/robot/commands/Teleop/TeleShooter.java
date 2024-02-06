//          2024202420242024      2024202420242024      2024202420242024      2024202420242024
//        20242024202420242024  20242024202420242024  20242024202420242024  20242024202420242024
//       2024            2024  2024            2024  2024            2024  2024
//       2024            2024  2024            2024  2024            2024  2024
//      2024            2024  2024            2024  2024            2024  2024
//      2024            2024  2024            2024  2024            2024  2024
//     20242024202420242024  20242024202420242024  2024            2024  20242024202420242024
//     20242024202420242024  20242024202420242024  2024            2024  20242024202420242024
//    2024            2024  2024            2024  2024            2024  2024            2024
//    2024            2024  2024            2024  2024            2024  2024            2024
//   2024            2024  2024            2024  2024            2024  2024            2024
//   2024            2024  2024            2024  2024            2024  2024            2024
//  20242024202420242024  20242024202420242024  20242024202420242024  20242024202420242024
//    2024202420242024      2024202420242024      2024202420242024      2024202420242024
package frc.robot.commands.Teleop;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class TeleShooter extends Command {
  private Shooter shooter;
  private Intake intake;
  private Supplier<Boolean> shootButton;
  private NetworkTable shooterLimelight;
  public TeleShooter(Shooter shooter, Intake intake, Supplier<Boolean> shootButton, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.intake = intake;
    this.shootButton = shootButton;
    this.shooterLimelight = shooterLimelight;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double tx = shooterLimelight.getEntry("tx").getDouble(0);
    double ty = shooterLimelight.getEntry("ty").getDouble(0);
    if(intake.isNoteSet()) {
      shooter.setShootingSpeed(0.8);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shootButton.get();
  }
}
