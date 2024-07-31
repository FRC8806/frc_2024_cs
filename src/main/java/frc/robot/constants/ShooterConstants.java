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
  public static final int DOWN_MOTOR_ID = 51;//52
  public static final int UP_MOTOR_ID = 52;//51
  public static final int TRANSPORT_MOTOR_ID = 53;
  // spark max
  public static final int ANGLE_MOTOR_ID = 10;//4
  // position
  public static final double angleHighLimit = 55;
  public static final double angleLowLimit = -15;
  public static final double angleAMP = 0;//7.928 15
  // PID
  public static final double shooterSpeedKP = 0.001;
  public static final double shooterSpeedKI = 0;
  public static final double shooterSpeedKD = 0;

  public static final double shooterAMPSpeedKP = 0.0003;
  public static final double shooterAMPSpeedKI = 0.0001;
  public static final double shooterAMPSpeedKD = 0;

  public static final double shooterKP = 0.06;
  public static final double shooterKI = 0;
  public static final double shooterKD = 0;
  // speed
  public static final double TRANSPORT_MOTOR_SPEED = 0.55;
  public static final double UpSpeedAMP = -0.05;//0.18 0.092 0.04 -0.05 要調
  public static final double DownSpeedAMP = 1;//0.36  0.35要調成負的(反轉)

  // led
  public static final int LED_LENTH = 60;
  public static final int LEDMODE_DEFAULT = 0;
  public static final int LEDMODE_SPEED_UP = 1;
  public static final int LEDMODE_SHOOTER_READY = 2;
  public static final int LEDMODE_AMP = 3;
  public static final int LEDMODE_NOTE_ON = 4;
  public static final double FLYWHEEL_SPEED = 5200;//4500

  //Auto
  public static final double AUTO_START_NOTE_POSITION = -7;
  public static final double AUTO_FIRST_NOTE_POSITION = 20;//30 28 
  public static final double AUTO_SECOND_NOTE_POSITION = 42.7;//54 57
  public static final double AUTO_THIRD_NOTE_POSITION = 42.9;
  public static final double AUTO_A1_NOTE_POSITION = 32;//30 
  public static final double AUTO_M2_NOTE_POSITION = 60;
  public static final double AUTO_MIDDLE_BACK_NOTE_POSITION = 20;
  // public static final double AUTO_A2_NOTE_POSITION = 27;
}
