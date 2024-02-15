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
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ShooterDefaultCommand;
import frc.robot.commands.Auto.AutoGetNote;
import frc.robot.commands.Auto.AutoShooting;
import frc.robot.commands.Auto.AutoShootingTransport;
import frc.robot.commands.Auto.AutoSpeakerTracking;
import frc.robot.commands.Auto.TestAuto;
import frc.robot.commands.Teleop.ClimbUp;
import frc.robot.commands.Teleop.ReadyClimber;
import frc.robot.commands.Teleop.SpeakerTracking;
import frc.robot.commands.Teleop.SwerveControl;
import frc.robot.commands.Teleop.TeleAMP;
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
    nameCommands();
    setDefaultCommand();
    configureBindings();
    autoChooser = AutoBuilder.buildAutoChooser();
    autoChooser.addOption("aaaa", new PathPlannerAuto("Example Auto"));
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings() {
    SmartDashboard.putData("test auto", new PathPlannerAuto("Example Auto"));
    new JoystickButton(driveController, Button.kA.value).whileTrue(new SpeakerTracking(shooter, intake, limelightShooter, chassis, () -> driveController.getLeftY(), () -> driveController.getLeftX(), ()-> operatorController.getLeftTriggerAxis(), ()->operatorController.getRightTriggerAxis()));
    new JoystickButton(driveController, Button.kB.value).whileTrue(new TeleAMP(shooter, chassis, ()->operatorController.getLeftTriggerAxis(), ()->driveController.getRightX(), ()->driveController.getRightY(), limelightShooter));
    new JoystickButton(operatorController, Button.kA.value).toggleOnTrue(new TeleGetNote(intake));
    //new JoystickButton(operatorController, Button.kBack.value).onTrue(new ReadyClimber(climber, ()-> operatorController.getStartButton()).andThen(new ClimbUp(climber, () -> operatorController.getBackButton())));
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

  public void setDefaultCommand() {
    chassis.setDefaultCommand(new SwerveControl(chassis, ()-> driveController.getLeftY(), ()-> driveController.getLeftX(), ()-> driveController.getRightX()));
    shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, ()-> operatorController.getRightTriggerAxis(), ()-> operatorController.getLeftTriggerAxis(), ()-> operatorController.getRightBumper(), ()-> operatorController.getLeftBumper(), limelightShooter));
  }

  public void cancelDefaultCommand() {
    CommandScheduler.getInstance().removeDefaultCommand(chassis);
    CommandScheduler.getInstance().removeDefaultCommand(shooter);
  }

  public void nameCommands() {
    NamedCommands.registerCommand("test marker1", new TestAuto());
    NamedCommands.registerCommand("shooting", new AutoShooting(shooter));
    NamedCommands.registerCommand("transport",new AutoShootingTransport(shooter));
    NamedCommands.registerCommand("intake", new AutoGetNote(intake));
    NamedCommands.registerCommand("shooterAngle", new AutoSpeakerTracking(shooter, limelightShooter));
  }
}
