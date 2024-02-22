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
package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class SwerveConstants {
  public static final double deadband = 0.12;

  public static final double kThrottleGarRatio = 6.12;
  public static final double kWheelDiameter = 4 * 0.0254;
  public static final double kMaxThrottleSpeed = 6380 / 60 / kThrottleGarRatio * kWheelDiameter * Math.PI;
  public static final double kMaxRotationSpeed = kMaxThrottleSpeed / Math.sqrt(2 * Math.pow(0.5, 2)) * 2;

  public static final double kThrottleVelocityConversionFactor = kWheelDiameter * Math.PI / kThrottleGarRatio * 10
      / 2048;
  public static final double kWheelDiameterMeters = 4 * 0.0254;
  public static final double kThrottleGearRatio = 6.12;
  public static final double kThrottlePositionConversionFactor = (kWheelDiameterMeters * Math.PI)
      / (kThrottleGearRatio * 2048);

  // CAN_ID
  public static final int A_THROTTLE_ID = 1;//2
  public static final int B_THROTTLE_ID = 7;//4
  public static final int C_THROTTLE_ID = 2;//6
  public static final int D_THROTTLE_ID = 5;//8

  public static final int A_ROTOR_ID = 4;//1
  public static final int B_ROTOR_ID = 3;//3
  public static final int C_ROTOR_ID = 8;//5
  public static final int D_ROTOR_ID = 6;//7

  public static final int A_ENCODER_ID = 3;//2
  public static final int B_ENCODER_ID = 2;//1
  public static final int C_ENCODER_ID = 1;//3
  public static final int D_ENCODER_ID = 4;//4

  public static final double A_OFFSET = -0.471923828125;// 0.143798828125
  public static final double B_OFFSET = -0.2099609375;// 0.09619140625
  public static final double C_OFFSET = 0.34912109375;// 0.043701171875
  public static final double D_OFFSET = -0.15869140625;// 0.19970704

  public static final Translation2d A_TRANSLATION2D = new Translation2d(0.29, 0.29);
  public static final Translation2d B_TRANSLATION2D = new Translation2d(0.29, -0.29);
  public static final Translation2d C_TRANSLATION2D = new Translation2d(-0.29, -0.29);
  public static final Translation2d D_TRANSLATION2D = new Translation2d(-0.29, 0.29);

  public static final SwerveDriveKinematics SWERVE_KINEMATIS = new SwerveDriveKinematics(A_TRANSLATION2D,
      B_TRANSLATION2D, C_TRANSLATION2D, D_TRANSLATION2D);

  // PID
  public static final double rotorKP = 0.013;
  public static final double rotorKI = 0;
  public static final double rotorKD = 0;

  public static final double translationKP = 0.08;
  public static final double translationKI = 0.015;
  public static final double translationKD = 0.02;

  public static final double rotationKP = 0.26;
  public static final double rotationKI = 0;
  public static final double rotationKD = 0;

  public static final double maxModuleSpeed = 4.5;
  public static final double driveBaseRadius = 0.41012;
}
