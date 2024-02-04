// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.SwerveConstants;

public class Intake extends SubsystemBase {
  private CANSparkMax rollingMotor = new CANSparkMax(IntakeConstants.ROLLING_MOTOR_ID, MotorType.kBrushless);
  private TalonFX angleMotor = new TalonFX(IntakeConstants.ANGLE_MOTOR_ID);
  private CANSparkMax microphone = new CANSparkMax(IntakeConstants.MICROPHONE_MOTOR_ID, MotorType.kBrushless);
  private SoftLimiter angleLimiter;
  public Intake() {
    angleLimiter = new SoftLimiter(()-> angleMotor.getPosition().getValue());
    angleLimiter.setRange(IntakeConstants.angleHighLimit, IntakeConstants.angleLowLimit);
    angleLimiter.enableLimiter();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Intake posiiton", angleMotor.getPosition().getValue());
  }

  public void setRollingSpeed(double rollingSpeed) {
    rollingMotor.set(rollingSpeed);
  }

  public void setIntakeAngle(double speed) {
    angleMotor.set(angleLimiter.getOutput(speed));
  }

  public void setLollipopSpeed(double speed){
    microphone.set(speed);
  }
}
