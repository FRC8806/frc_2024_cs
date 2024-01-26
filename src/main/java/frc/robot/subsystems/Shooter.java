// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  CANSparkFlex shootingMotor1 = new CANSparkFlex(50, MotorType.kBrushless);
  CANSparkFlex shootingMotor2 = new CANSparkFlex(51, MotorType.kBrushless);
  //TalonFX shooterAngleMotor = new TalonFX(12);
  CANSparkMax shooterAngleMotor = new CANSparkMax(4, MotorType.kBrushless);
  CANSparkMax transportMotor = new CANSparkMax(2, MotorType.kBrushless);
  /** Creates a new Shooter. */
  public Shooter() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setShootingSpeed(double speed) {
    shootingMotor1.set(speed);
    shootingMotor2.set(-speed);
  }
  public void setShooterAngle(double speed) {
    shooterAngleMotor.set(speed);
  }
  public void setTransportSpeed(double speed){
    transportMotor.set(speed);
  }
}
