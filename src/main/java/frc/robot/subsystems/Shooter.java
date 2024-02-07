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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.ShooterConstants;
public class Shooter extends SubsystemBase {
  private CANSparkFlex leftMotor = new CANSparkFlex(ShooterConstants.LEFT_MOTOR_ID, MotorType.kBrushless);
  private CANSparkFlex rightMotor = new CANSparkFlex(ShooterConstants.RIGHT_MOTOR_ID, MotorType.kBrushless);
  private CANSparkMax angleMotor = new CANSparkMax(ShooterConstants.ANGLE_MOTOR_ID, MotorType.kBrushless);
  private CANSparkMax transportMotor = new CANSparkMax(ShooterConstants.TRANSPORT_MOTOR_ID, MotorType.kBrushless);
  private RelativeEncoder shootingEncoder;
  private RelativeEncoder angleEncoder;
  private SoftLimiter shootLimiter;

  public boolean isTracking = false;

  public Shooter() {
    shootingEncoder = leftMotor.getEncoder();
    angleEncoder = angleMotor.getEncoder();
    shootLimiter = new SoftLimiter(() -> angleEncoder.getPosition());
    shootLimiter.setRange(ShooterConstants.angleHighLimit, ShooterConstants.angleLowLimit);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("is tracking", isTracking);
    SmartDashboard.putNumber("measure anglr", angleEncoder.getPosition());
    SmartDashboard.putNumber("shooting speed", shootingEncoder.getVelocity());
  }

  public void setShootingSpeed(double speed) {
    leftMotor.set(speed);
    rightMotor.set(-speed);
  }

  public double getShootingSpeed() {
    return shootingEncoder.getVelocity();
  }

  public void setShooterAngle(double speed) {
    angleMotor.set(shootLimiter.getOutput(speed));
  }

  public void setTransportSpeed(double speed) {
    transportMotor.set(speed);
  }

  public void setToZero() {
    angleEncoder.setPosition(0);
  }

  public double getAnglePosition() {
    return angleEncoder.getPosition();
  }
}
