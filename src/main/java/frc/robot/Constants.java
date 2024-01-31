// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double kThrottleGarRatio = 6.12;
    public static final double kWheelDiameter = 4*0.0254;
    public static final double kMaxThrottleSpeed = 6380/ 60/ kThrottleGarRatio* kWheelDiameter* Math.PI;
    public static final double kMaxRotationSpeed = kMaxThrottleSpeed/ Math.sqrt(2*Math.pow(0.5, 2)) * 2;

    public static final double kThrottleVelocityConversionFactor = kWheelDiameter* Math.PI/ kThrottleGarRatio* 10/ 2048;
    public static final double kWheelDiameterMeters = 4 * 0.0254;
    public static final double kThrottleGearRatio = 6.12;
    public static final double kThrottlePositionConversionFactor = (kWheelDiameterMeters*Math.PI)/(kThrottleGearRatio*2048);

    //CAN_ID
    public static final int A_THROTTLE_ID = 2;//2
    public static final int B_THROTTLE_ID = 4;//4
    public static final int C_THROTTLE_ID = 6;//6
    public static final int D_THROTTLE_ID = 8;//8

    public static final int A_ROTOR_ID = 1;//1
    public static final int B_ROTOR_ID = 3;//3
    public static final int C_ROTOR_ID = 5;//5
    public static final int D_ROTOR_ID = 7;//7

    public static final int A_ENCODER_ID = 2;//2
    public static final int B_ENCODER_ID = 1;//1
    public static final int C_ENCODER_ID = 3;//3
    public static final int D_ENCODER_ID = 4;//4

    public static final double A_OFFSET = -0.362060546875;//-0.362060546875
    public static final double B_OFFSET = -0.406005859375;//-0.406005859375
    public static final double C_OFFSET = -0.458984375;//-0.458984375
    public static final double D_OFFSET = -0.30029296875;//-0.30029296875

    public static final Translation2d A_TRANSLATION2D = new Translation2d(-0.25, -0.25);
    public static final Translation2d B_TRANSLATION2D = new Translation2d(-0.25, 0.25);
    public static final Translation2d C_TRANSLATION2D = new Translation2d(0.25, 0.25);
    public static final Translation2d D_TRANSLATION2D = new Translation2d(0.25, -0.25);

    public static final SwerveDriveKinematics SWERVE_KINEMATIS = new SwerveDriveKinematics(A_TRANSLATION2D, B_TRANSLATION2D, C_TRANSLATION2D, D_TRANSLATION2D);

    //limit
    public static final double RIGHT_ELEVATOR_HIGH_LIMIT = 0;
    public static final double RIGHT_ELEVATOR_LOW_LIMIT = -700;
    public static final double LEFT_ELEVATOR_HIGH_LIMIT = 640;
    public static final double LEFT_ELEVATOR_LOW_LIMIT = 0;
    public static final double SHOOTER_HIGH_LIMIT = 0;
    public static final double SHOOTER_LOW_LIMIT = 0;
    public static final double INTAKE_HIGH_LIMIT = 0;
    public static final double INTAKE_LOW_LIMIT = 0;
}
