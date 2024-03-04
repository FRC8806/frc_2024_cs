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

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class TeleGetNote extends Command {
  private Intake intake;
  // private Shooter shooter;
  private Supplier<Boolean> inverseTrigger;
  
  public TeleGetNote(Intake intake, Supplier<Boolean> inverseTrigger) {
    this.intake = intake;
    // this.shooter = shooter;
    this.inverseTrigger = inverseTrigger;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setIntakeDown();
    intake.setRollingSpeed(IntakeConstants.rollingSpeed);
    intake.setMicSpeed(IntakeConstants.microPhoneSpeed);
  }

  @Override
  public void execute() {
    if(inverseTrigger.get()) {
      intake.setRollingSpeed(-0.2);
      intake.setMicSpeed(-0.2);
    } else {
      intake.setRollingSpeed(IntakeConstants.rollingSpeed);
      intake.setMicSpeed(IntakeConstants.microPhoneSpeed);
    }
    // if(shooter.isNoteSet()) {
    //   shooter.setLED(ShooterConstants.LEDMODE_NOTE_ON);
    // }
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeUp();
    intake.setRollingSpeed(0);
    intake.setMicSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
