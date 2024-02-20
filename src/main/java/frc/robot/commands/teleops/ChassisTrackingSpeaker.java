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

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Chassis;

public class ChassisTrackingSpeaker extends Command {
  private NetworkTable shooterLimelight;
  private Chassis chassis;
  private Supplier<Double> xAxis, yAxis;

  public ChassisTrackingSpeaker(NetworkTable shooterLimelight, Chassis driveTrain,
      Supplier<Double> xAxis, Supplier<Double> yAxis) {
    this.shooterLimelight = shooterLimelight;
    this.chassis = driveTrain;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double xSpeed = onDeadband(xAxis.get(), SwerveConstants.deadband);
    double ySpeed = onDeadband(yAxis.get(), SwerveConstants.deadband);
    xSpeed *= SwerveConstants.kMaxThrottleSpeed;
    ySpeed *= SwerveConstants.kMaxThrottleSpeed;
    double tx = shooterLimelight.getEntry("tx").getDouble(0);
    chassis.drive(new ChassisSpeeds(xSpeed, ySpeed, -tx * 0.15));
  }

  @Override
  public void end(boolean interrupted) {
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
