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
package frc.robot.commands.teleops;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Shooter;

public class TeleAMP extends Command {
  Shooter shooter;
  Chassis chassis;
  Supplier<Double> lt;
  NetworkTable shooterLimelight;
  public TeleAMP(Shooter shooter, Chassis chassis, Supplier<Double> lt, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.chassis = chassis;
    this.lt = lt;
    this.shooterLimelight = shooterLimelight;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    //移入
    shooter.setFlyWheelSpeed(0.15);
    shooter.setTransportSpeed(lt.get()/5);
    //移出
    //shooter.setAMPAngle();
  }

  @Override
  public void end(boolean interrupted) {
    // shooter.setFlyWheelSpeed(0);
    shooter.setAngleSpeed(0);
    shooter.setTransportSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
