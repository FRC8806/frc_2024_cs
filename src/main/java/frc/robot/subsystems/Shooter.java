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

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.ShooterConstants;
public class Shooter extends SubsystemBase {
  //fly wheel motors
  private CANSparkFlex leftMotor = new CANSparkFlex(ShooterConstants.LEFT_MOTOR_ID, MotorType.kBrushless);
  private CANSparkFlex rightMotor = new CANSparkFlex(ShooterConstants.RIGHT_MOTOR_ID, MotorType.kBrushless);
  //angle motor
  private CANSparkMax angleMotor = new CANSparkMax(ShooterConstants.ANGLE_MOTOR_ID, MotorType.kBrushless);
  //transport motor 
  private CANSparkFlex transportMotor = new CANSparkFlex(ShooterConstants.TRANSPORT_MOTOR_ID, MotorType.kBrushless);
  //encoders
  private RelativeEncoder flyWheelEncoder;
  private RelativeEncoder angleEncoder;
  //angle limiter
  private SoftLimiter shootLimiter;
  //angle PID controller
  private PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI, ShooterConstants.shooterKD);

  public boolean isTracking = false;
  public boolean isTargetAimed = false;

  public Shooter() {
    flyWheelEncoder = leftMotor.getEncoder();
    angleEncoder = angleMotor.getEncoder();
    shootLimiter = new SoftLimiter(() -> angleEncoder.getPosition());
    shootLimiter.setRange(ShooterConstants.angleHighLimit, ShooterConstants.angleLowLimit);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("is tracking", isTracking);
    SmartDashboard.putNumber("measure angle", angleEncoder.getPosition());
    SmartDashboard.putNumber("fly wheel speed", flyWheelEncoder.getVelocity());
    SmartDashboard.putBoolean("speed", isSpeedReached());
  }

  public void setFlyWheelSpeed(double speed) {
    leftMotor.set(speed);
    rightMotor.set(-speed);
  }

  public double getFlyWheelSpeed() {
    return flyWheelEncoder.getVelocity();
  }

  public void setAngleSpeed(double speed) {
    angleMotor.set(shootLimiter.getOutput(speed));
  }
  /**
   * 
   * @param speed The speed of the transport motor
   */
  public void setTransportSpeed(double speed) {
    transportMotor.set(speed);
  }
  /**
   * Reset the encoder of the angle motor to zero
   */
  public void resetAngleEncoder() {
    angleEncoder.setPosition(0);
  }
  /**
   * 
   * @return Position of the angle motor
   */
  public double getAnglePosition() {
    return angleEncoder.getPosition();
  }
  //放到command
  public void setAMPAngle(){
    double speed = shootLimiter.getOutput(shooterPID.calculate(getAnglePosition(), ShooterConstants.angleAMP));
    speed = speed > 0.15 ? 0.15 : speed < -0.15 ? -0.15 : speed;
    angleMotor.set(speed);
  } 

  public boolean isSpeedReached(){
    return getFlyWheelSpeed() >= 5300;
  }
}
