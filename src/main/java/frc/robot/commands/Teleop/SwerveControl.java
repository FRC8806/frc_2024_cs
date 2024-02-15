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

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Chassis;

public class SwerveControl extends Command {
  private Chassis chassis;
  private Supplier<Double> xAxis;
  private Supplier<Double> yAxis;
  private Supplier<Double> zAxis;

  public SwerveControl(Chassis chassis, Supplier<Double> xAxis, Supplier<Double> yAxis, Supplier<Double> zAxis) {
    this.chassis = chassis;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.zAxis = zAxis;
    addRequirements(chassis);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double xSpeed = -onDeadband(xAxis.get(), SwerveConstants.deadband);
    double ySpeed = -onDeadband(yAxis.get(), SwerveConstants.deadband);
    double rSpeed = -onDeadband(zAxis.get(), SwerveConstants.deadband);
    xSpeed *= SwerveConstants.kMaxThrottleSpeed;
    ySpeed *= SwerveConstants.kMaxThrottleSpeed;
    rSpeed *= SwerveConstants.kMaxRotationSpeed;
    chassis.drive(new ChassisSpeeds(xSpeed,ySpeed,rSpeed));
  }

  @Override
  public void end(boolean interrupted) {
    chassis.drive(new ChassisSpeeds());
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
