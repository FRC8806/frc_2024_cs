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

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class TeleGetNote extends Command {
  private Intake intake;
  private Supplier<Boolean> inverseTrigger;
  
  public TeleGetNote(Intake intake, Supplier<Boolean> inverseTrigger) {
    this.intake = intake;
    this.inverseTrigger = inverseTrigger;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setIntakeDown();
    intake.setRolling(true);
    intake.setMicroPhone(true);
  }

  @Override
  public void execute() {
    if(inverseTrigger.get()) {
      intake.setRollingSpeed(-0.2);
    } else {
      intake.setRolling(true);
    }
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeUp();
    intake.setRolling(false);
    intake.setMicroPhone(false);
  }

  @Override
  public boolean isFinished() {
    return intake.isNoteSet();
  }
}
