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
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {
  //rolling motor
  private CANSparkFlex rollingMotor = new CANSparkFlex(IntakeConstants.ROLLING_MOTOR_ID, MotorType.kBrushless);
  //angle motor
  private TalonFX angleMotor = new TalonFX(IntakeConstants.ANGLE_MOTOR_ID);
  //microphone motor 
  private CANSparkMax microphone = new CANSparkMax(IntakeConstants.MICROPHONE_MOTOR_ID, MotorType.kBrushless);
  //limiter of the angle motor
  private SoftLimiter angleLimiter;
  //PID controller of th angle motor
  private PIDController intakePID = new PIDController(IntakeConstants.intakrKP, IntakeConstants.intakrKI, IntakeConstants.intakrKD);

  private boolean isSetToPosition = false;
  private double intakePosition = 10;
  public boolean isIntakeDown = false;

  public Intake() {
    angleLimiter = new SoftLimiter(()-> getAnglePosition());
    angleLimiter.setRange(IntakeConstants.angleHighLimit, IntakeConstants.angleLowLimit);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Intake position", angleMotor.getPosition().getValue());
    if(isSetToPosition) { angleMotor.set(angleLimiter.getOutput(intakePID.calculate(angleMotor.getPosition().getValue(), intakePosition)));}
  }

  public double getAnglePosition(){
    return angleMotor.getPosition().getValue();
  }

  public void setAnglePosition(double position) {
    isSetToPosition = true;
    intakePosition = position;
  }

  public void setIntakeUp() {
    isIntakeDown = false;
    setAnglePosition(IntakeConstants.upPosition);
  }

  public void setIntakeDown() {
    isIntakeDown = true;
    setAnglePosition(IntakeConstants.downPosition);
  }

  public void setRollingSpeed(double rollingSpeed) {
    rollingMotor.set(rollingSpeed);
  }

  public void setAngleSpeed(double speed) {
    isSetToPosition = false;
    angleMotor.set(angleLimiter.getOutput(speed));
  }

  public void setMicSpeed(double speed) {
    microphone.set(speed);
  }

  public void resetAngleEncoder() {
    angleMotor.setPosition(0);
  }

  public boolean isNoteSet() {
    return false;
  }
}
