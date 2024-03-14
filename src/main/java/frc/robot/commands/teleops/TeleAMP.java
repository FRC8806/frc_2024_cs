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
package frc.robot.commands.teleops;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class TeleAMP extends Command {
  private Shooter shooter;
  private Supplier<Boolean> trigger;
  private PIDController speedPID = new PIDController(ShooterConstants.shooterAMPSpeedKP, ShooterConstants.shooterAMPSpeedKI,
      ShooterConstants.shooterAMPSpeedKD);
  public TeleAMP(Shooter shooter, Supplier<Boolean> trigger) {
    this.shooter = shooter;
    this.trigger = trigger;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setAnglePosition(ShooterConstants.angleAMP); //-3
    shooter.setFlyWheelSpeed(ShooterConstants.speedAMP);//0.174 0.153
  }

  @Override
  public void execute() {
    shooter.setLED(ShooterConstants.LEDMODE_AMP);
    if (trigger.get()) {
      shooter.setTransportSpeed(ShooterConstants.TRANSPORT_MOTOR_SPEED);
    } else {
      shooter.setTransportSpeed(0);
    }
    shooter.setFlyWheelSpeed(ShooterConstants.speedAMP + speedPID.calculate(shooter.getFlyWheelSpeed(), 500)); //780
    //0.083 530 -1
    //0.089 550 -4
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setFlyWheelSpeed(0);
    shooter.setTransportSpeed(0);
    shooter.setAngleSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
