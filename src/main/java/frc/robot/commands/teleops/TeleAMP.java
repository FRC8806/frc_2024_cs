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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class TeleAMP extends Command {
  private Shooter shooter;
  private Supplier<Boolean> trigger;

  public TeleAMP(Shooter shooter, Supplier<Boolean> trigger) {
    this.shooter = shooter;
    this.trigger = trigger;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setAnglePosition(0);
    shooter.setFlyWheelSpeed(0.174);//0.165
  }

  @Override
  public void execute() {
    shooter.setLED(ShooterConstants.LEDMODE_AMP);
    if (trigger.get()) {
      shooter.setTransportSpeed(0.4);
    } else {
      shooter.setTransportSpeed(0);
    }
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
