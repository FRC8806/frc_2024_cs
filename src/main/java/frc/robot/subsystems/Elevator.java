// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Limit;

public class Elevator extends SubsystemBase {
  private TalonFX leftElevatorMotor = new TalonFX(11);
  private TalonFX rightElevatorMotor = new TalonFX(12);
  /** Creates a new Elevator. */
  public Elevator() {}

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftElevator", getLeftPosition());
    SmartDashboard.putNumber("rightElevator", getRightPosition());
    // This method will be called once per scheduler run
  }

  public void setElevatorRightSpeed(double speed){
    speed = new Limit().setLimit(speed, getRightPosition(), Constants.RIGHT_ELEVATOR_HIGH_LIMIT, Constants.RIGHT_ELEVATOR_LOW_LIMIT, 0.3, 0.3);
    speed = speed > 0.2 ? speed : speed < -0.2 ? speed : 0;

    rightElevatorMotor.set(speed);
  }

  public void setElevatorLeftSpeed(double speed){
    speed = new Limit().setLimit(speed, getLeftPosition(), Constants.LEFT_ELEVATOR_HIGH_LIMIT, Constants.LEFT_ELEVATOR_LOW_LIMIT, 0.3, 0.22);
    speed = speed > 0.2 ? speed : speed < -0.2 ? speed : 0;

    leftElevatorMotor.set(speed);
  }

  public void setElevatorSpeed(double speed){
    setElevatorLeftSpeed(speed);
    setElevatorRightSpeed(-speed);
  }

  public double getRightPosition(){
    return rightElevatorMotor.getRotorPosition().getValue();
  }

  public double getLeftPosition(){
    return leftElevatorMotor.getRotorPosition().getValue();
  }

  public void zeroElevatorEncoder(){
    leftElevatorMotor.setPosition(0);
    rightElevatorMotor.setPosition(0);
  }

  public void setElevatorRightOutOfLimit(double speed){
    rightElevatorMotor.set(speed);
  }

  public void setElevatorLeftOutOfLimit(double speed){
    leftElevatorMotor.set(speed);
  }
}
