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

public final class ShooterConstants {
  // PWM
  public static final int LED_PORT = 9;
  // spark flex
  public static final int LEFT_MOTOR_ID = 52;
  public static final int RIGHT_MOTOR_ID = 51;
  // spark max
  public static final int TRANSPORT_MOTOR_ID = 53;
  public static final int ANGLE_MOTOR_ID = 10;// 4
  // position
  public static final double angleHighLimit = 60;
  public static final double angleLowLimit = 2;
  public static final double angleAMP = 7.928;
  // PID
  public static final double shooterSpeedKP = 0.001;
  public static final double shooterSpeedKI = 0;
  public static final double shooterSpeedKD = 0;

  public static final double shooterKP = 0.06;
  public static final double shooterKI = 0;
  public static final double shooterKD = 0;
  // speed
  public static final double TRANSPORT_MOTOR_SPEED = 0.4;
  public static final double AMPSpeed = 0.18;
  // led
  public static final int LED_LENTH = 60;
  public static final int LEDMODE_DEFAULT = 0;
  public static final int LEDMODE_SPEED_UP = 1;
  public static final int LEDMODE_SHOOTER_READY = 2;
  public static final int LEDMODE_AMP = 3;
  public static final int LEDMODE_NOTE_ON = 4;
  public static final double FLYWHEEL_SPEED = 4500;

  //Auto
  public static final double AUTO_FIRST_NOTE_POSITION = 28;//30

  public static final double AUTO_SECOND_NOTE_POSITION = 54;
  public static final double AUTO_A2_NOTE_POSITION = 27;
}
