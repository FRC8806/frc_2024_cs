package frc.robot.commands;

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

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = -onDeadband(xAxis.get(), 0.07);
    double ySpeed = -onDeadband(yAxis.get(), 0.07);
    double rSpeed = -onDeadband(zAxis.get(), 0.07);
    xSpeed *= SwerveConstants.kMaxThrottleSpeed;
    ySpeed *= SwerveConstants.kMaxThrottleSpeed;
    rSpeed *= SwerveConstants.kMaxRotationSpeed;
    chassis.drive(new ChassisSpeeds(xSpeed,ySpeed,rSpeed));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chassis.drive(new ChassisSpeeds());
  }

  // Returns true when the command should end.
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
