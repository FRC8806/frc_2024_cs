package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private TalonFX leftMotor = new TalonFX(ClimberConstants.LEFT_CLIMBER_ID);
  private TalonFX rightMotor = new TalonFX(ClimberConstants.RIGHT_CLIMBER_ID);
  private SoftLimiter leftLimiter;
  private SoftLimiter rightLimiter;
  private PIDController climbPID = new PIDController(0.03, 0, 0);

  public Climber() {
    leftLimiter = new SoftLimiter(()-> getLeftPosition());
    rightLimiter = new SoftLimiter(()-> getRightPosition());
    leftLimiter.setRange(ClimberConstants.LEFT_ELEVATOR_HIGH_LIMIT, ClimberConstants.LEFT_ELEVATOR_LOW_LIMIT);
    rightLimiter.setRange(ClimberConstants.RIGHT_ELEVATOR_HIGH_LIMIT, ClimberConstants.RIGHT_ELEVATOR_LOW_LIMIT);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftElevator", getLeftPosition());
    SmartDashboard.putNumber("rightElevator", getRightPosition());
  }

  public void setToPosition(double position) {
    setLeftSpeed(climbPID.calculate(getLeftPosition(), -position));
    setRightSpeed(climbPID.calculate(getRightPosition(),position));
  }

  public void setRightSpeed(double speed) {
    rightMotor.set(rightLimiter.getOutput(speed));
  }

  public void setLeftSpeed(double speed) {
    leftMotor.set(leftLimiter.getOutput(speed));
  }

  public double getRightPosition() {
    return rightMotor.getRotorPosition().getValue();
  }

  public double getLeftPosition() {
    return leftMotor.getRotorPosition().getValue();
  }

  public void setToZero() {
    leftMotor.setPosition(0);
    rightMotor.setPosition(0);
  }
}
