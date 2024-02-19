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
package frc.robot.commands.Teleop;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterDefaultCommand extends Command {
  private Shooter shooter;
  private Supplier<Double> rt;
  private Supplier<Double> lt;
  private Supplier<Boolean> rb;
  private Supplier<Boolean> lb;
  private NetworkTable shooterLimelight;
  public ShooterDefaultCommand(Shooter shooter, Supplier<Double> rt, Supplier<Double> lt, Supplier<Boolean> rb, Supplier<Boolean> lb, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.rt = rt;
    this.lt = lt;
    this.rb = rb;
    this.lb = lb;
    this.shooterLimelight = shooterLimelight;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    shooter.setFlyWheelSpeed(rt.get());
    shooter.setAngleSpeed(rb.get() ? 0.15 : lb.get() ? -0.15 : 0);
    shooter.setTransportSpeed(lt.get()/2);
    SmartDashboard.putNumber("ty", shooterLimelight.getValue("ty").getDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
