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
package frc.robot.commands.teleop;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShooterTrackingSpeaker extends Command {
  private Shooter shooter;
  private Intake intake;
  private NetworkTable shooterLimelight;
  private Supplier<Double> trigger;
  private PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI,
      ShooterConstants.shooterKD);
  private PIDController speedPID = new PIDController(ShooterConstants.shooterSpeedKP, ShooterConstants.shooterSpeedKI,
      ShooterConstants.shooterSpeedKD);

  private double targetPosition, angleSpeed;

  public ShooterTrackingSpeaker(Shooter shooter, Intake intake, NetworkTable shooterLimelight, 
        Supplier<Double> transportTrigger) {
    this.shooter = shooter;
    this.shooterLimelight = shooterLimelight;
    this.intake = intake;
    this.trigger = transportTrigger;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    //移到外面
    if (RobotContainer.isRedAlliance()) {
      shooterLimelight.getEntry("pipeline").setNumber(0);
    } else {
      shooterLimelight.getEntry("pipeline").setNumber(1);
    }
    shooter.isTracking = true;
    //待測
    targetPosition = 30;
  }

  @Override
  public void execute() {
    double ty = shooterLimelight.getEntry("ty").getDouble(0.0);
    
    SmartDashboard.putNumber("target position", targetPosition);
    // SmartDashboard.putNumber("measure angle", measureAngle.getRotations());
    if (ty != 0.0) {
      targetPosition = 49.9 - 3.73 * ty - 0.0362 * ty * ty;
      angleSpeed = shooterPID.calculate(shooter.getAnglePosition(), targetPosition);
    }
    shooter.setAngleSpeed(angleSpeed);
    shooter.setFlyWheelSpeed(0.9 + speedPID.calculate(shooter.getFlyWheelSpeed(),
    5400));
    if (trigger.get() > 0.2) {
      intake.setMicSpeed(IntakeConstants.microPhoneSpeed);
      //移入
      shooter.setTransportSpeed(0.8 / 2);
    } else {
      intake.setMicSpeed(0);
      shooter.setTransportSpeed(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
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
