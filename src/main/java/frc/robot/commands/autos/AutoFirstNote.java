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
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class AutoFirstNote extends Command {
  private Shooter shooter;
  private Double position;
  private PIDController speedPID = new PIDController(ShooterConstants.shooterSpeedKP, ShooterConstants.shooterSpeedKI,
      ShooterConstants.shooterSpeedKD);

  public AutoFirstNote(Shooter shooter, double position) {
    this.shooter = shooter;
    this.position = position;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setTransportSpeed(0);
  }

  @Override
  public void execute() {
    shooter.setAnglePosition(position);
    shooter.setFlyWheelSpeed(0.9 + speedPID.calculate(shooter.getFlyWheelSpeed(),
        ShooterConstants.FLYWHEEL_SPEED));
    if (shooter.isSpeedReached() && Math.abs(shooter.getAnglePosition() - position) < 0.5) {
      shooter.setTransportSpeed(ShooterConstants.TRANSPORT_MOTOR_SPEED);
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setTransportSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
