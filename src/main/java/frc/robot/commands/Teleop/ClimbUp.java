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
import frc.robot.constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class ClimbUp extends Command {
  private Climber climber;
  private Supplier<Boolean> resetButton;
  public ClimbUp(Climber climber, Supplier<Boolean> resetButton) {
    this.climber = climber;
    this.resetButton = resetButton;
    addRequirements(climber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    climber.setToPosition(ClimberConstants.DOWN_POSE);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return resetButton.get();
  }
}
