// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private CANSparkMax rollingMotor = new CANSparkMax(1, MotorType.kBrushless);
  private TalonFX angleMotor = new TalonFX(10);
  private CANSparkMax lollipop = new CANSparkMax(3, MotorType.kBrushless);
  /** Creates a new Intake. */
  public Intake() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setRollingSpeed(double rollingSpeed) {
    rollingMotor.set(rollingSpeed);
  }

  public void setIntakeAngle(double speed) {
    angleMotor.set(speed);
  }

  public void setLollipopSpeed(double speed){
    lollipop.set(speed);
  }
}
