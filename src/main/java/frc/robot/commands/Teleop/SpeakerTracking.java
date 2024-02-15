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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class SpeakerTracking extends Command {
  private Shooter shooter;
  private Intake intake;
  private NetworkTable shooterLimelight;
  private Chassis driveTrain;
  private Supplier<Double> xAxis, yAxis;
  private Supplier<Double> transportTrigger,shootingTrigger ;
  private PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI, ShooterConstants.shooterKD);
  private PIDController speedPID = new PIDController(ShooterConstants.shooterSpeedKP, ShooterConstants.shooterSpeedKI, ShooterConstants.shooterSpeedKD);
  public SpeakerTracking(Shooter shooter, Intake intake, NetworkTable shooterLimelight, Chassis driveTrain, Supplier<Double> xAxis, Supplier<Double> yAxis, Supplier<Double> transportTrigger, Supplier<Double> shootingTrigger) {
    this.shooter = shooter;
    this.shooterLimelight = shooterLimelight;
    this.intake = intake;
    this.driveTrain = driveTrain;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.transportTrigger = transportTrigger;
    this.shootingTrigger = shootingTrigger;
    addRequirements(driveTrain);
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    var alliance = DriverStation.getAlliance();
      if (alliance.isPresent()) {
        shooterLimelight.getEntry("pipeline").setNumber(0);
      }else{
        shooterLimelight.getEntry("pipeline").setNumber(1);
      }
    shooter.isTracking = true;
  }

  @Override
  public void execute() {
    double xSpeed = -onDeadband(xAxis.get(), SwerveConstants.deadband);
    double ySpeed = -onDeadband(yAxis.get(), SwerveConstants.deadband);
    xSpeed *= SwerveConstants.kMaxThrottleSpeed;
    ySpeed *= SwerveConstants.kMaxThrottleSpeed;
    double tx = shooterLimelight.getEntry("tx").getDouble(0);
    double ty = shooterLimelight.getEntry("ty").getDouble(0);
    double targetPosition = 49.9 - 3.73 * ty - 0.0362 * ty * ty;
    SmartDashboard.putNumber("target position", targetPosition);
    // SmartDashboard.putNumber("measure angle", measureAngle.getRotations());
    shooter.setShooterAngle(shooterPID.calculate(shooter.getAnglePosition(), targetPosition));
    driveTrain.drive(new ChassisSpeeds(xSpeed, ySpeed, -tx * 0.15));
    shooter.setShootingSpeed(shootingTrigger.get());
    //shooter.setShootingSpeed(0.8 + speedPID.calculate(shooter.getShootingSpeed(), 5000));
    if(transportTrigger.get() > 0.2) {
      intake.setMicroPhone(true);
      shooter.setTransportSpeed(0.8/2);
    } else {
      intake.setMicroPhone(false);
      shooter.setTransportSpeed(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.isTracking = false;
    shooter.setShooterAngle(0);
    shooter.setTransportSpeed(0);
    shooter.setShootingSpeed(0);
    intake.setMicroPhone(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private double onDeadband(double value, double deadband) {
    if (value > 0) {
      value -= deadband;
      value = value < 0 ? 0 : value;
    }
    if (value < 0) {
      value += deadband;
      value = value > 0 ? 0 : value;
    }
    return value;
  }
}
