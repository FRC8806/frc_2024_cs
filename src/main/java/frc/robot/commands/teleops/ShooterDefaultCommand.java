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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShooterDefaultCommand extends Command {
  private Shooter shooter;
  private Intake intake;
  private Supplier<Double> rt;
  private Supplier<Double> lt;
  private Supplier<Boolean> rb;
  private Supplier<Boolean> lb;
  private NetworkTable shooterLimelight;
    private PIDController speedPID = new PIDController(ShooterConstants.shooterSpeedKP, ShooterConstants.shooterSpeedKI,
      ShooterConstants.shooterSpeedKD);
  public ShooterDefaultCommand(Shooter shooter, Intake intake, Supplier<Double> rt, Supplier<Double> lt, Supplier<Boolean> rb, Supplier<Boolean> lb, NetworkTable shooterLimelight) {
    this.shooter = shooter;
    this.intake = intake;
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
    if (shooter.getFlyWheelSpeed() > 4900) {
      shooter.setLED(ShooterConstants.LEDMODE_SHOOTER_READY);
    } else {
      shooter.setLED(ShooterConstants.LEDMODE_SPEED_UP);
    }
    intake.setMicSpeed(lt.get() > 0.2 ? 0.2 : 0);
    shooter.setAngleSpeed(rb.get() ? 0.08 : lb.get() ? -0.08 : 0);
    shooter.setTransportSpeed(lt.get()>0.2?0.4:0);
    if(rt.get() > 0.2) {
      shooter.setFlyWheelSpeed(0.9 + speedPID.calculate(shooter.getFlyWheelSpeed(),
      5000));
    } else {
      shooter.setFlyWheelSpeed(0);
    }
    SmartDashboard.putNumber("ty", shooterLimelight.getValue("ty").getDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
