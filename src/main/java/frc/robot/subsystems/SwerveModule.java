// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.constants.SwerveConstants;

public class SwerveModule {
	private TalonFX throttle;
	private TalonFX rotor;
	private CANcoder encoder;
	private PIDController rotorPID = new PIDController(0.015, 0, 0);
	public CANcoderConfiguration config = new CANcoderConfiguration();

	public SwerveModule(int throttleID, int rotorID, int cancoderID, double encoderOffset) {
		throttle = new TalonFX(throttleID);
		rotor = new TalonFX(rotorID);
		encoder = new CANcoder(cancoderID);
		config.MagnetSensor.AbsoluteSensorRange.compareTo(AbsoluteSensorRangeValue.Signed_PlusMinusHalf);
		config.MagnetSensor.MagnetOffset = encoderOffset;
		setup();
	}

	public SwerveModuleState getState() {
		return new SwerveModuleState(
				throttle.getVelocity().getValue() * SwerveConstants.kThrottleVelocityConversionFactor,
				Rotation2d.fromRotations(encoder.getAbsolutePosition().getValue()));
	}

	public void setState(SwerveModuleState states) {
		SwerveModuleState optimizedState = SwerveModuleState.optimize(states, getState().angle);
		double throttleOutput = optimizedState.speedMetersPerSecond / SwerveConstants.kMaxThrottleSpeed;
		double rotorOutput = rotorPID.calculate(getState().angle.getDegrees(), optimizedState.angle.getDegrees());
		throttle.set(throttleOutput);
		rotor.set(-rotorOutput);
	}

	public SwerveModulePosition getPosition() {
		double throttlePosition = throttle.getPosition().getValue() * SwerveConstants.kThrottlePositionConversionFactor;
		return new SwerveModulePosition(throttlePosition,
				Rotation2d.fromRotations(encoder.getAbsolutePosition().getValue()));
	}

	private void setup() {
		throttle.getConfigurator().apply(new TalonFXConfiguration());
		throttle.setNeutralMode(NeutralModeValue.Brake);

		rotor.getConfigurator().apply(new TalonFXConfiguration());
		rotor.setNeutralMode(NeutralModeValue.Brake);

		encoder.getConfigurator().apply(config);

		rotorPID.enableContinuousInput(-180, 180);
	}
}
