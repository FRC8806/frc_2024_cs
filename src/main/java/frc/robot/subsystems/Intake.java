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
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  private ColorMatch colorMatch = new ColorMatch();

  private CANSparkMax rollingMotor = new CANSparkMax(IntakeConstants.ROLLING_MOTOR_ID, MotorType.kBrushless);
  private TalonFX angleMotor = new TalonFX(IntakeConstants.ANGLE_MOTOR_ID);
  private CANSparkMax microphone = new CANSparkMax(IntakeConstants.MICROPHONE_MOTOR_ID, MotorType.kBrushless);
  private SoftLimiter angleLimiter;
  private PIDController intakePID = new PIDController(0.05, 0, 0);

  private boolean isSetToPosition = false;
  private double intakePosition = 10;
  public boolean isIntakeDown = false;

  public Intake() {
    angleLimiter = new SoftLimiter(()-> angleMotor.getPosition().getValue());
    angleLimiter.setRange(IntakeConstants.angleHighLimit, IntakeConstants.angleLowLimit);
    colorMatch.addColorMatch(IntakeConstants.noteColor);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Intake position", angleMotor.getPosition().getValue());
    SmartDashboard.putBoolean("color matched", isNoteSet());

    if(isSetToPosition) { angleMotor.set(angleLimiter.getOutput(intakePID.calculate(angleMotor.getPosition().getValue(), intakePosition)));}
  }

  public void setIntakePosition(double position) {
    isSetToPosition = true;
    intakePosition = position;
  }

  public void setIntakeUp() {
    isIntakeDown = false;
    setIntakePosition(IntakeConstants.upPosition);
  }

  public void setIntakeDown() {
    isIntakeDown = true;
    setIntakePosition(IntakeConstants.downPosition);
  }

  public void setRollingSpeed(double rollingSpeed) {
    rollingMotor.set(rollingSpeed);
  }

  public void setRolling(boolean state) {
    setRollingSpeed(state? 0.4: 0);
  }

  public void setIntakeAngle(double speed) {
    isSetToPosition = false;
    angleMotor.set(angleLimiter.getOutput(speed));
  }

  public void setMicroPhoneSpeed(double speed) {
    microphone.set(speed);
  }

  public void setMicroPhone(boolean state) {
    setMicroPhoneSpeed(state ? 0.2 : 0);
  }

  public void setToZero() {
    angleMotor.setPosition(0);
  }

  public Color getColor() {
    return colorSensor.getColor();
  }

  public boolean isNoteSet() {
    // if (colorMatch.matchColor(getColor()) != null) {
    //   return colorMatch.matchColor(getColor()).color == IntakeConstants.noteColor;
    // }
    return false;
  }
}
