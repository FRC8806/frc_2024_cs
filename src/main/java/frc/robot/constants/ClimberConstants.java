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

public class ClimberConstants {
  public static final int LEFT_CLIMBER_ID = 11;
  public static final int RIGHT_CLIMBER_ID = 12;   
  
  public static final double RIGHT_ELEVATOR_HIGH_LIMIT = 380;
  public static final double RIGHT_ELEVATOR_LOW_LIMIT = 10;
  public static final double LEFT_ELEVATOR_HIGH_LIMIT = -10;
  public static final double LEFT_ELEVATOR_LOW_LIMIT = -380;

  public static final double SETUP_POSE = 360;
  public static final double DOWN_POSE = 20;

  public static final double climberGearRitio = 1/60;

  public static final double climberKP = 0.03;
  public static final double climberKI = 0;
  public static final double climberKD = 0;
}
