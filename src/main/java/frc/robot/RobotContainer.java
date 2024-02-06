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
package frc.robot;


import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ShooterDefaultCommand;
import frc.robot.commands.Teleop.ClimbUp;
import frc.robot.commands.Teleop.IntakeDefaultCommand;
import frc.robot.commands.Teleop.ReadyClimber;
import frc.robot.commands.Teleop.SpeakerTracking;
import frc.robot.commands.Teleop.SwerveControl;
import frc.robot.commands.Teleop.TeleGetNote;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


public class RobotContainer {
  private final NetworkTable limelightShooter = NetworkTableInstance.getDefault().getTable("limelight-shooter");
  private final Chassis chassis = new Chassis();
  public final Intake intake = new Intake();
  public final Shooter shooter = new Shooter();
  public final Climber climber = new Climber();
  private final XboxController driveController = new XboxController(OIConstants.driveControllerID);
  public final XboxController operatorController = new XboxController(OIConstants.operatorControllerID);
  private final SendableChooser<Command> autoChooser;
  

  public RobotContainer() {
    setDefaultCommand();
    configureBindings();
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings() {
    new JoystickButton(driveController, Button.kStart.value).toggleOnTrue(new SpeakerTracking(shooter, intake, limelightShooter, chassis, () -> driveController.getLeftY(), () -> driveController.getLeftX(), ()-> operatorController.getLeftTriggerAxis()));
    new JoystickButton(operatorController, Button.kA.value).toggleOnTrue(new TeleGetNote(intake));
    new JoystickButton(operatorController, Button.kBack.value).onTrue(new ReadyClimber(climber, ()-> operatorController.getStartButton()).andThen(new ClimbUp(climber)));
  }

  public Command getAutonomousCommand() {
    PathPlannerPath path = PathPlannerPath.fromPathFile("Path5");
    return AutoBuilder.followPath(path);
  }

  public void setDefaultCommand() {
    chassis.setDefaultCommand(new SwerveControl(chassis, ()-> driveController.getLeftY(), ()-> driveController.getLeftX(), ()-> driveController.getRightX()));
    shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, operatorController, intake, limelightShooter));
    intake.setDefaultCommand(new IntakeDefaultCommand(intake, operatorController));
  }

  public void cancelDefaultCommand() {
    CommandScheduler.getInstance().removeDefaultCommand(chassis);
    CommandScheduler.getInstance().removeDefaultCommand(shooter);
  }
}
