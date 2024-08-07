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

import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
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
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.*;

public class RobotContainer {
  // limelight tables
  private final NetworkTable limelightShooter = NetworkTableInstance.getDefault().getTable("limelight-shooter");
  // subsystems
  public Chassis chassis;
  public final Intake intake = new Intake();
  public final Shooter shooter = new Shooter();
  public final Climber climber = new Climber();
  // controllers
  private final XboxController driveController = new XboxController(ControllerConstants.driveControllerID);
  public final XboxController operatorController = new XboxController(ControllerConstants.operatorControllerID);
  public final XboxController testController = new XboxController(ControllerConstants.testControllerID);

  private final SendableChooser<Command> autoChooser;
  public Supplier<Boolean> isRedAliance;


  public RobotContainer(Supplier<Boolean> isRedAliance) {
    chassis = new Chassis(isRedAliance);
    nameCommands();
    setDefaultCommand();
    configureBindings();
    this.isRedAliance = isRedAliance;
    if (isRedAliance.get()) {
      limelightShooter.getEntry("pipeline").setNumber(LimelightConatants.PIPELINE_RED_SPEAKER);
    } else {
      limelightShooter.getEntry("pipeline").setNumber(LimelightConatants.PIPELINE_BLUE_SPEAKER);
    }
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings() {
    new JoystickButton(driveController,Button.kRightBumper.value).whileTrue(new ChassisTrackingSpeaker(limelightShooter, chassis,
        () -> driveController.getLeftY(), () -> driveController.getLeftX()));
    new JoystickButton(operatorController, Button.kY.value)
        .toggleOnTrue(new ShooterTrackingSpeaker(shooter, intake, limelightShooter,
            () -> operatorController.getLeftTriggerAxis(), operatorController));
    // new JoystickButton(operatorController, Button.kB.value)
    //     .whileTrue(new TeleAMP(shooter, chassis, () -> operatorController.getLeftTriggerAxis(), limelightShooter));
    new JoystickButton(operatorController, Button.kA.value)
        .toggleOnTrue(new TeleGetNote(intake, () -> operatorController.getXButton()));
    new JoystickButton(operatorController, Button.kBack.value)
        .onTrue(new ClimberSetup(climber, intake, shooter, () -> operatorController.getStartButton())
            .andThen(new ClimbUp(climber, () -> operatorController.getBackButton())));

    //new JoystickButton(operatorController, Button.kRightBumper.value).toggleOnTrue(new TeleAMP(shooter, intake, () -> operatorController.getLeftBumper()));
    //new JoystickButton(operatorController, Button.kLeftBumper.value).whileTrue(new TeleAMP2(intake));
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
  
  public void setDefaultCommand() {
    chassis.setDefaultCommand(new SwerveControl(chassis, () -> driveController.getLeftY(),
        () -> driveController.getLeftX(), () -> driveController.getRightX()));
    // shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, intake, () -> operatorController.getRightTriggerAxis(),
    //     () -> operatorController.getLeftTriggerAxis(), () -> operatorController.getRightBumper(),
    //     () -> operatorController.getLeftBumper(), limelightShooter));
  }
  
  public void cancelDefaultCommand() {
    CommandScheduler.getInstance().removeDefaultCommand(chassis);
    // CommandScheduler.getInstance().removeDefaultCommand(shooter);
  }

  public void nameCommands() {
    NamedCommands.registerCommand("setup", new AutoSetup(intake, shooter));
    NamedCommands.registerCommand("first note", new AutoFirstNote(shooter, ShooterConstants.AUTO_FIRST_NOTE_POSITION));
    NamedCommands.registerCommand("second note", new AutoSencondNote(shooter, ShooterConstants.AUTO_SECOND_NOTE_POSITION));
    NamedCommands.registerCommand("third note", new AutoSencondNote(shooter, ShooterConstants.AUTO_THIRD_NOTE_POSITION));
    NamedCommands.registerCommand("a1", new AutoFirstNote(shooter, ShooterConstants.AUTO_A1_NOTE_POSITION));
    NamedCommands.registerCommand("start note", new AutoFirstNote(shooter, ShooterConstants.AUTO_START_NOTE_POSITION));
    NamedCommands.registerCommand("m2 note", new AutoSencondNote(shooter, ShooterConstants.AUTO_M2_NOTE_POSITION));
    NamedCommands.registerCommand("middle back", new AutoSencondNote(shooter, ShooterConstants.AUTO_MIDDLE_BACK_NOTE_POSITION));
    // NamedCommands.registerCommand("a2 note", new AutoFirstNote(shooter, ShooterConstants.AUTO_A2_NOTE_POSITION));
    // NamedCommands.registerCommand("test note", new AutoFirstNote(shooter, 32));
    NamedCommands.registerCommand("intake rolling", new AutoGetNote(intake));
    NamedCommands.registerCommand("green rolling", new AutoTransport(shooter, true));
    NamedCommands.registerCommand("green stop", new AutoTransport(shooter, false));
    NamedCommands.registerCommand("stop", new AutoAllStop(shooter, intake, chassis));

    // NamedCommands.registerCommand("shooterAngle", new AutoSpeakerTracking(shooter, limelightShooter));
    // NamedCommands.registerCommand("intakeDown", new AutoIntakeDown(intake));

  }
}
