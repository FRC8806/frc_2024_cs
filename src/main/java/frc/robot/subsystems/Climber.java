package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private TalonFX leftMotor = new TalonFX(ClimberConstants.LEFT_CLIMBER_ID);
  private TalonFX rightMotor = new TalonFX(ClimberConstants.RIGHT_CLIMBER_ID);
  private SoftLimiter leftLimiter;
  private SoftLimiter rightLimiter;

  public Climber() {
    leftLimiter = new SoftLimiter(()-> getLeftPosition());
    rightLimiter = new SoftLimiter(()-> getRightPosition());
    leftLimiter.setRange(ClimberConstants.LEFT_ELEVATOR_HIGH_LIMIT, ClimberConstants.LEFT_ELEVATOR_LOW_LIMIT);
    rightLimiter.setRange(ClimberConstants.RIGHT_ELEVATOR_HIGH_LIMIT, ClimberConstants.RIGHT_ELEVATOR_LOW_LIMIT);
    leftLimiter.enableLimiter();
    rightLimiter.enableLimiter();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftElevator", getLeftPosition());
    SmartDashboard.putNumber("rightElevator", getRightPosition());
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

  public void setElevatorRightOutOfLimit(double speed) {
    rightMotor.set(speed);
  }

  public void setElevatorLeftOutOfLimit(double speed) {
    leftMotor.set(speed);
  }
}