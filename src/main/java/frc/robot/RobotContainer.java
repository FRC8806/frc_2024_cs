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

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.autos.*;
import frc.robot.commands.teleops.*;
import frc.robot.commands.climb.*;
import frc.robot.constants.ControllerConstants;
import frc.robot.constants.LimelightConatants;
import frc.robot.subsystems.*;

public class RobotContainer {
  // limelight tables
  private final NetworkTable limelightShooter = NetworkTableInstance.getDefault().getTable("limelight-shooter");
  // subsystems
  public final Chassis chassis = new Chassis();
  public final Intake intake = new Intake();
  public final Shooter shooter = new Shooter();
  public final Climber climber = new Climber();
  // controllers
  private final XboxController driveController = new XboxController(ControllerConstants.driveControllerID);
  public final XboxController operatorController = new XboxController(ControllerConstants.operatorControllerID);
  public final XboxController testController = new XboxController(ControllerConstants.testControllerID);

  private final SendableChooser<Command> autoChooser;
  private static Optional<Alliance> alliance;

  public RobotContainer() {
    nameCommands();
    setDefaultCommand();
    configureBindings();
    alliance = DriverStation.getAlliance();

    if (isRedAlliance()) {
      limelightShooter.getEntry("pipeline").setNumber(LimelightConatants.PIPELINE_RED_SPEAKER);
    } else {
      limelightShooter.getEntry("pipeline").setNumber(LimelightConatants.PIPELINE_BLUE_SPEAKER);
    }
    autoChooser = AutoBuilder.buildAutoChooser();
    // autoChooser.addOption("aaaa", new PathPlannerAuto("Example Auto"));
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings() {
    // SmartDashboard.putData("test auto", new PathPlannerAuto("Example Auto"));
    new JoystickButton(driveController, Button.kA.value).whileTrue(new ChassisTrackingSpeaker(limelightShooter, chassis,
        () -> driveController.getLeftY(), () -> driveController.getLeftX()));
    new JoystickButton(operatorController, Button.kY.value)
        .toggleOnTrue(new ShooterTrackingSpeaker(shooter, intake, limelightShooter,
            () -> operatorController.getLeftTriggerAxis()));
    new JoystickButton(operatorController, Button.kB.value)
        .whileTrue(new TeleAMP(shooter, chassis, () -> operatorController.getLeftTriggerAxis(), limelightShooter));
    new JoystickButton(operatorController, Button.kA.value)
        .toggleOnTrue(new TeleGetNote(intake, shooter, () -> operatorController.getXButton()));
    new JoystickButton(operatorController, Button.kBack.value)
        .onTrue(new ClimberSetup(climber, () -> operatorController.getStartButton())
            .andThen(new ClimbUp(climber, () -> operatorController.getBackButton())));
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

  public void setDefaultCommand() {
    chassis.setDefaultCommand(new SwerveControl(chassis, () -> driveController.getLeftY(),
        () -> driveController.getLeftX(), () -> driveController.getRightX()));
    shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, intake, () -> operatorController.getRightTriggerAxis(),
        () -> operatorController.getLeftTriggerAxis(), () -> operatorController.getRightBumper(),
        () -> operatorController.getLeftBumper(), limelightShooter));
  }

  public void cancelDefaultCommand() {
    CommandScheduler.getInstance().removeDefaultCommand(chassis);
    CommandScheduler.getInstance().removeDefaultCommand(shooter);
  }

  public void nameCommands() {
    // NamedCommands.registerCommand("amp", new TeleAMP(shooter, chassis,
    // operatorController::getLeftTriggerAxis, limelightShooter));
    // NamedCommands.registerCommand("greenRoll", );
    NamedCommands.registerCommand("shooting", new AutoShooting(shooter));
    NamedCommands.registerCommand("intakeRolling", new AutoGetNote(intake));
    NamedCommands.registerCommand("shooterAngle", new AutoSpeakerTracking(shooter, limelightShooter));
    NamedCommands.registerCommand("intakeDown", new AutoIntakeDown(intake));
    NamedCommands.registerCommand("greenRolling", new AutoTransport(shooter));
  }

  public static boolean isRedAlliance() {
    if (alliance.isPresent()) {
      return alliance.get() == DriverStation.Alliance.Red;
    }
    return false;
  }
}
