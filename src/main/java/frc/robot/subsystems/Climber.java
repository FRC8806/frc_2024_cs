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
package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.ClimberConstants;

public class Climber extends SubsystemBase {
  //climb motors
  private TalonFX leftMotor = new TalonFX(ClimberConstants.LEFT_CLIMBER_ID);
  private TalonFX rightMotor = new TalonFX(ClimberConstants.RIGHT_CLIMBER_ID);
  //The limiter of motors
  private SoftLimiter limiter;
  //The PID controller of motor
  private PIDController climbPID = new PIDController(ClimberConstants.climberKP, ClimberConstants.climberKI, ClimberConstants.climberKD);

  public Climber() {
    limiter = new SoftLimiter(()-> getLeftPosition());
    limiter.setRange(ClimberConstants.ELEVATOR_HIGH_LIMIT, ClimberConstants.ELEVATOR_LOW_LIMIT);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftElevator", getLeftPosition());
    SmartDashboard.putNumber("rightElevator", getRightPosition());
  }

  public void setToPosition(double position) {
    setLeftSpeed(climbPID.calculate(getLeftPosition(), -position));
    setSpeed(climbPID.calculate(getRightPosition(),position));
  }

  public void setSpeed(double speed) {
    rightMotor.set(limiter.getOutput(speed));
  }

  public void setLeftSpeed(double speed) {
    leftMotor.set(limiter.getOutput(speed));
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
