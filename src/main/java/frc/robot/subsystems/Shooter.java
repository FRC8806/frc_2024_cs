// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SoftLimiter;
import frc.robot.constants.ShooterConstants;
public class Shooter extends SubsystemBase {
  private CANSparkMax leftMotor = new CANSparkMax(ShooterConstants.LEFT_MOTOR_ID, MotorType.kBrushless);
  private CANSparkMax rightMotor = new CANSparkMax(ShooterConstants.RIGHT_MOTOR_ID, MotorType.kBrushless);
  private CANSparkMax angleMotor = new CANSparkMax(ShooterConstants.ANGLE_MOTOR_ID, MotorType.kBrushless);
  private CANSparkMax transportMotor = new CANSparkMax(ShooterConstants.TRANSPORT_MOTOR_ID, MotorType.kBrushless);
  private RelativeEncoder shootingEncoder;
  private RelativeEncoder angleEncoder;
  private SoftLimiter shootLimiter;

  public Shooter() {
    shootingEncoder = leftMotor.getEncoder();
    angleEncoder = angleMotor.getEncoder();
    shootLimiter = new SoftLimiter(() -> shootingEncoder.getPosition());
    shootLimiter.setRange(ShooterConstants.angleHighLimit, ShooterConstants.angleLowLimit);
    shootLimiter.enableLimiter();
  }

  @Override
  public void periodic() {
  }

  public void setShootingSpeed(double speed) {
    leftMotor.set(speed);
    rightMotor.set(-speed);
  }

  public void setShooterAngle(double speed) {
    angleMotor.set(shootLimiter.getOutput(speed));
  }

  public void setTransportSpeed(double speed) {
    transportMotor.set(speed);
  }

  public void setLimiter(boolean state) {
    if(state) {
      shootLimiter.enableLimiter();
    } else {
      shootLimiter.disableLimiter();
    }
  }

  public void setToZero() {
    angleEncoder.setPosition(0);
  }
}
