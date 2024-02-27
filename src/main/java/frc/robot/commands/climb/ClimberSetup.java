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
package frc.robot.commands.climb;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ClimberConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;

public class ClimberSetup extends Command {
  private Climber climber;
  private Intake intake;
  private Supplier<Boolean> climbButton;
  public ClimberSetup(Climber climber, Intake intake, Supplier<Boolean> climbButton) {
    this.climber = climber;
    this.intake = intake;
    this.climbButton = climbButton;
    addRequirements(climber);
  }

  @Override
  public void initialize() {
    intake.setIntakeDown();
  }

  @Override
  public void execute() {
    climber.setToPosition(ClimberConstants.SETUP_POSE);
  }

  @Override
  public void end(boolean interrupted) {
    climber.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return climbButton.get();
  }
}
