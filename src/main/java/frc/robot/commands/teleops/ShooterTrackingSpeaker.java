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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShooterTrackingSpeaker extends Command {
  private Shooter shooter;
  private Intake intake;
  private NetworkTable shooterLimelight;
  private Supplier<Double> trigger;
  // private PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI,
  //     ShooterConstants.shooterKD);
  private PIDController speedPID = new PIDController(ShooterConstants.shooterSpeedKP, ShooterConstants.shooterSpeedKI,
      ShooterConstants.shooterSpeedKD);
  public XboxController operatorController;

  private double targetPosition;
  // private double angleSpeed;

  public ShooterTrackingSpeaker(Shooter shooter, Intake intake, NetworkTable shooterLimelight,
      Supplier<Double> transportTrigger, XboxController operatorController) {
    this.shooter = shooter;
    this.shooterLimelight = shooterLimelight;
    this.intake = intake;
    this.trigger = transportTrigger;
    this.operatorController = operatorController;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.isTracking = true;
    targetPosition = -15;
  }

  @Override
  public void execute() {
    double ty = shooterLimelight.getEntry("ty").getDouble(0.0);

    SmartDashboard.putNumber("target position", targetPosition);
    // SmartDashboard.putNumber("measure angle", measureAngle.getRotations());
    if (ty != 0.0) {
      // ty = ty + 2;
      // targetPosition = 40.1 - 4.44 * ty - 0.0749 * Math.pow(ty, 2) + 0.0251 * Math.pow(ty, 3) - 0.0011 * Math.pow(ty, 4);
      // targetPosition = 34.2 - 4.55 * ty + 0.0539 * ty * ty;
      targetPosition = 19.1 - 5.57*ty - 0.195*ty*ty;//打google sheet  20.2-4.18-0.0261
    }
    // angleSpeed = shooterPID.calculate(shooter.getAnglePosition(), targetPosition);
    // shooter.setAngleSpeed(angleSpeed);
    //shooter.setAnglePosition(targetPosition);
    shooter.setAngleSpeed(operatorController.getLeftBumper() ? 0.2 : operatorController.getRightBumper() ? -0.2 : 0);//如果要測自動校正把這行註解掉 復原上面那行
    // shooter.setFlyWheelSpeed(0.9);
    shooter.setFlyWheelSpeed(0.9 + speedPID.calculate(shooter.getUpFlyWheelSpeed(),
        ShooterConstants.FLYWHEEL_SPEED));
    // if (shooter.isSpeedReached() && Math.abs(shooter.getAnglePosition() - targetPosition) < 1) {
    if (shooter.isSpeedReached()){
      shooter.setLED(ShooterConstants.LEDMODE_SHOOTER_READY);
    } else {
      shooter.setLED(ShooterConstants.LEDMODE_SPEED_UP);
    }

    if (trigger.get() > 0.2) {
      intake.setMicSpeed(IntakeConstants.microPhoneSpeed);
      shooter.setTransportSpeed(ShooterConstants.TRANSPORT_MOTOR_SPEED);
    } else {
      intake.setMicSpeed(0);
      shooter.setTransportSpeed(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setLED(ShooterConstants.LEDMODE_DEFAULT);
    shooter.isTracking = false;
    shooter.setAngleSpeed(0);
    shooter.setTransportSpeed(0);
    shooter.setFlyWheelSpeed(0);
    intake.setMicSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
