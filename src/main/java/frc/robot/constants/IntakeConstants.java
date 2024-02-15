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

import edu.wpi.first.wpilibj.util.Color;

public class IntakeConstants {
  //Talon FX
  public static final int ANGLE_MOTOR_ID = 10;
  //Spark Max
  public static final int MICROPHONE_MOTOR_ID = 3;
  public static final int ROLLING_MOTOR_ID = 5;
  //position
  public static final double angleHighLimit = 41;//41
  public static final double angleLowLimit = 0;//8
  public static final double downPosition = 41;//41
  public static final double upPosition = 23;//13
  //color
  public static final Color noteColor = new Color(135 , 93, 26);
  public static final Color noneColor = new Color(67, 118, 70);

  //PID
  public static final double intakrKP = 0.05;
  public static final double intakrKI = 0;
  public static final double intakrKD = 0;

  //speed
  public static final double rollingSpeed = 0.6;
  public static final double microPhoneSpeed = 0.2;
  public static final double reverseSpeed = -0.6;
}
