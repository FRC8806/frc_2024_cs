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
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  private ColorMatch colorMatch = new ColorMatch();
  // led
  private AddressableLED led = new AddressableLED(ShooterConstants.LED_PORT);
  private AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(ShooterConstants.LED_LENTH);
  // fly wheel motors
  private CANSparkFlex leftMotor = new CANSparkFlex(ShooterConstants.LEFT_MOTOR_ID, MotorType.kBrushless);
  private CANSparkFlex rightMotor = new CANSparkFlex(ShooterConstants.RIGHT_MOTOR_ID, MotorType.kBrushless);
  // angle motor
  private CANSparkMax angleMotor = new CANSparkMax(ShooterConstants.ANGLE_MOTOR_ID, MotorType.kBrushless);
  // transport motor
  private CANSparkFlex transportMotor = new CANSparkFlex(ShooterConstants.TRANSPORT_MOTOR_ID, MotorType.kBrushless);
  // encoders
  private RelativeEncoder flyWheelEncoder;
  private RelativeEncoder angleEncoder;
  // angle limiter
  private SoftLimiter angleLimiter;
  // angle PID controller
  private PIDController shooterPID = new PIDController(ShooterConstants.shooterKP, ShooterConstants.shooterKI,
      ShooterConstants.shooterKD);

  public boolean isTracking = false;
  public boolean isTargetAimed = false;

  private int firstPixelHue = 0;
  private int[] ledState = new int[ShooterConstants.LED_LENTH / 2];
  private boolean isSetToPosition = false;
  private double shooterPosition = 10;

  public Shooter() {
    colorMatch.addColorMatch(IntakeConstants.noteTarget);
    colorMatch.addColorMatch(IntakeConstants.noneTarget);
    led.setLength(ledBuffer.getLength());
    led.start();
    flyWheelEncoder = leftMotor.getEncoder();
    angleEncoder = angleMotor.getEncoder();
    angleLimiter = new SoftLimiter(() -> angleEncoder.getPosition());
    angleLimiter.setRange(ShooterConstants.angleHighLimit, ShooterConstants.angleLowLimit);
  }

  @Override
  public void periodic() {

    for (var i = 0; i < ShooterConstants.LED_LENTH / 2; i++) {
      ledBuffer.setHSV(i, ledState[i], 255, 128);
      ledBuffer.setHSV(i + ledBuffer.getLength() / 2, ledState[ledState.length - 1 - i], 255, 128);
    }
    led.setData(ledBuffer);
    setLED(ShooterConstants.LEDMODE_DEFAULT);
    if(isSetToPosition) { angleMotor.set(angleLimiter.getOutput(shooterPID.calculate(getAnglePosition(), shooterPosition)));}
    // SmartDashboard.putNumber("cr", colorSensor.getColor().red * 255);
    // SmartDashboard.putNumber("cg", colorSensor.getColor().green * 255);
    // SmartDashboard.putNumber("cb", colorSensor.getColor().blue * 255);
    SmartDashboard.putBoolean("is tracking", isTracking);
    SmartDashboard.putNumber("shooter position", angleEncoder.getPosition());
    SmartDashboard.putNumber("fly wheel speed", flyWheelEncoder.getVelocity());
    SmartDashboard.putBoolean("isNoteSet", isNoteSet());
  }

  public void setAnglePosition(double position) {
    isSetToPosition = true;
    shooterPosition = position;
  }

  public void setFlyWheelSpeed(double speed) {
    leftMotor.set(speed);
    rightMotor.set(-speed);
  }

  public double getFlyWheelSpeed() {
    return flyWheelEncoder.getVelocity();
  }

  public void setAngleSpeed(double speed) {
    isSetToPosition = false;
    angleMotor.set(angleLimiter.getOutput(speed));
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

  public boolean isSpeedReached() {
    return getFlyWheelSpeed() >= 4500;//4900
  }

  public void setLED(int ledMode) {
    switch (ledMode) {
      case ShooterConstants.LEDMODE_DEFAULT:
        // For every pixel
        for (var i = 0; i < ledState.length; i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (firstPixelHue + (i * 180 / (ledBuffer.getLength() / 2))) % 180;
          // Set the value
          ledState[i] = hue;
        }
        // Increase by to make the rainbow "move"
        firstPixelHue += 2;
        // Check bounds
        firstPixelHue %= 180;
        break;
      case ShooterConstants.LEDMODE_SHOOTER_READY:
        for (var i = 0; i < ledState.length; i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = 90;
          // Set the value
          ledState[i] = hue;
        }
        break;
      case ShooterConstants.LEDMODE_SPEED_UP:
        int lightLenth = (int) (getFlyWheelSpeed() / 5000 * 31) + 1;
        lightLenth = lightLenth > 31 ? 31 : lightLenth;
        for (var i = 0; i < lightLenth; i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = 50;
          // Set the value
          ledState[i] = hue;
        }
        for (var i = lightLenth; i < ledState.length; i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = 0;
          // Set the value
          ledState[i] = hue;
        }
        break;
      case ShooterConstants.LEDMODE_NOTE_ON:
        for (var i = 0; i < ledState.length; i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = 8;
          // Set the value
          ledState[i] = hue;
        }
        break;
      // case ShooterConstants.LEDMODE_INTAKE_DOWN:
      // for (var i = 0; i < ledState.length; i++) {
      // // Calculate the hue - hue is easier for rainbows because the color
      // // shape is a circle so only one value needs to precess
      // final var hue = 30;
      // // Set the value
      // ledState[i] = hue;
      // }
      // break;
      default:
        // For every pixel
        for (var i = 0; i < ledState.length; i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (firstPixelHue + (i * 180 / (ledBuffer.getLength() / 2))) % 180;
          // Set the value
          ledState[i] = hue;
        }
        // Increase by to make the rainbow "move"
        firstPixelHue += 2;
        // Check bounds
        firstPixelHue %= 180;
        break;
    }
  }

  public boolean isNoteSet() {
    Color detectedColor = colorSensor.getColor();
    ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);
    return match.color != null && match.color == IntakeConstants.noteTarget;
  }
}
