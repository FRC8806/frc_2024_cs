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

public class AutoShoot extends Command {
  private Shooter shooter;
  private PIDController speedPID = new PIDController(ShooterConstants.shooterSpeedKP, ShooterConstants.shooterSpeedKI,
      ShooterConstants.shooterSpeedKD);
  
  public AutoShoot(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooter.setAnglePosition(ShooterConstants.AUTO_FIRST_NOTE_POSITION);
    shooter.setFlyWheelSpeed(0.9 + speedPID.calculate(shooter.getFlyWheelSpeed(),
    ShooterConstants.FLYWHEEL_SOEED));
    if(shooter.isSpeedReached()) {
      shooter.setTransportSpeed(ShooterConstants.TRANSPORT_MOTOR_SPEED);
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
