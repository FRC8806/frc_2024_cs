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
package frc.robot.commands.autos;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class AutoSpeakerTracking extends Command {
  private Shooter shooter;
  private NetworkTable shooterLimelight;
  private PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI, ShooterConstants.shooterKD);

  private double targetPosition, angleSpeed;

  public AutoSpeakerTracking(Shooter shooter, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.shooterLimelight = shooterLimelight;
  }

  @Override
  public void initialize() {
    targetPosition = 10;
  }

  @Override
  public void execute() {
    double ty = shooterLimelight.getEntry("ty").getDouble(0);

    if (ty != 0.0) {
      ty = ty + 2;
      targetPosition = 40.1 - 4.44 * ty - 0.0749 * Math.pow(ty, 2) + 0.0251 * Math.pow(ty, 3) - 0.0011 * Math.pow(ty, 4);
    }
    angleSpeed = shooterPID.calculate(shooter.getAnglePosition(), targetPosition);
    shooter.setAngleSpeed(angleSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setAngleSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
