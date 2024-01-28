// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.IntakeDefaultCommand;
import frc.robot.commands.ShooterDefaultCommand;
import frc.robot.commands.SpeakerTracking;
import frc.robot.commands.SwerveControl;
import frc.robot.commands.ZeroEncoder;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


public class RobotContainer {
  private final NetworkTable limelightShooter = NetworkTableInstance.getDefault().getTable("limelight-shooter");
  private final DriveTrain driveTrain = new DriveTrain();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Elevator elevator = new Elevator();
  private final XboxController controller = new XboxController(0);
  private final XboxController operatorController = new XboxController(1);
  private final SendableChooser<Command> autoChooser;
  

  public RobotContainer() {
    driveTrain.setDefaultCommand(new SwerveControl(driveTrain, controller));
    intake.setDefaultCommand(new IntakeDefaultCommand(intake, controller));
    shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, controller));
    elevator.setDefaultCommand(new ElevatorCommand(elevator, operatorController));
    // Configure the trigger bindings
    configureBindings();

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings() {
    new JoystickButton(controller, Button.kStart.value).whileTrue(new SpeakerTracking(shooter, limelightShooter, driveTrain));
    new JoystickButton(operatorController, Button.kRightStick.value).toggleOnTrue(new ZeroEncoder(elevator, operatorController));
  }

  public Command getAutonomousCommand() {
    // PathPlannerPath path = PathPlannerPath.fromPathFile("Path1");
    // return AutoBuilder.followPath(path);
    return autoChooser.getSelected();
  }
}
