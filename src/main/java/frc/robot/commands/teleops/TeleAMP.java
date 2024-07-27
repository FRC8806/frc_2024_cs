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
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class TeleAMP extends Command {
  private Shooter shooter;
  private Intake intake;
  private Supplier<Boolean> trigger;
  private PIDController speedPID = new PIDController(ShooterConstants.shooterAMPSpeedKP, ShooterConstants.shooterAMPSpeedKI,
      ShooterConstants.shooterAMPSpeedKD);
  public TeleAMP(Shooter shooter,Intake intake, Supplier<Boolean> trigger) {
    this.shooter = shooter;
    this.intake = intake;
    this.trigger = trigger;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setAnglePosition(ShooterConstants.angleAMP); //-3
    shooter.setUpFlyWheelSpeed(ShooterConstants.UpSpeedAMP);//0.174 0.153
    shooter.setDownFlyWheelSpeed(ShooterConstants.DownSpeedAMP);
    intake.setMicSpeed(IntakeConstants.microPhoneSpeed);
  }

  @Override
  public void execute() {
    shooter.setLED(ShooterConstants.LEDMODE_AMP);
    if (trigger.get()) {
      shooter.setTransportSpeed(ShooterConstants.TRANSPORT_MOTOR_SPEED);
      intake.setMicSpeed(IntakeConstants.microPhoneSpeed);

    } else {
      shooter.setTransportSpeed(0);
      intake.setMicSpeed(0);
    }
    //shooter.setFlyWheelSpeed(ShooterConstants.speedAMP + speedPID.calculate(shooter.getFlyWheelSpeed(), 500)); //780
    shooter.setUpFlyWheelSpeed(ShooterConstants.UpSpeedAMP);
    shooter.setDownFlyWheelSpeed(ShooterConstants.DownSpeedAMP);
    
    //0.083 530 -1
    //0.089 550 -4
  }

  @Override
  public void end(boolean interrupted) {
    //shooter.setFlyWheelSpeed(0);
    shooter.setUpFlyWheelSpeed(0);
    shooter.setDownFlyWheelSpeed(0);
    shooter.setTransportSpeed(0);
    shooter.setAngleSpeed(0);
    intake.setMicSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
