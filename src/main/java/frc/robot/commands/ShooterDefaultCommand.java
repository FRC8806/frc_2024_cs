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
package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShooterDefaultCommand extends Command {
  private Shooter shooter;
  private Intake intake;
  private XboxController operatorController;
  private NetworkTable shooterLimelight;
  public ShooterDefaultCommand(Shooter shooter, XboxController operatorController, Intake intake, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.intake = intake;
    this.operatorController = operatorController;
    this.shooterLimelight = shooterLimelight;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    shooter.setShootingSpeed(operatorController.getRightTriggerAxis());
    // intake.setMicroPhoneSpeed(operatorController.getLeftTriggerAxis() > 0.2 ? 0.2 : 0);
    shooter.setShooterAngle(operatorController.getRightBumper() ? 0.15 : operatorController.getLeftBumper() ? -0.15 : 0);
    shooter.setTransportSpeed(operatorController.getLeftTriggerAxis());
    SmartDashboard.putNumber("ty", shooterLimelight.getValue("ty").getDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
