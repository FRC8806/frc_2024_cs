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
import frc.robot.subsystems.Intake;

public class TeleAMP extends Command {
  private Intake intake;
  private Timer timer;
  private Supplier<Boolean> intakeUpTrigger;

  public TeleAMP(Intake intake, Supplier<Boolean> intakeUpTrigger) {
    this.intake = intake;
    this.intakeUpTrigger = intakeUpTrigger;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    intake.setIntakeDown();
  }

  @Override
  public void execute() {
    if (timer.get() > 0.2) {
      intake.setRollingSpeed(-0.2);
      intake.setMicSpeed(-0.2);
    }
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeUp();
    intake.setRollingSpeed(0);
    intake.setMicSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return intakeUpTrigger.get();
  }
}
