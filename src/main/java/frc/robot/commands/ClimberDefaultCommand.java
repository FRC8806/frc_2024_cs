package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class ClimberDefaultCommand extends Command {
  private Climber climber;
  private XboxController operatorController;
  /** Creates a new ElevatorControl. */
  public ClimberDefaultCommand(Climber climber, XboxController operatorController) {
    this.climber = climber;
    this.operatorController = operatorController;
    addRequirements(climber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.setLeftSpeed(operatorController.getLeftY());
    climber.setRightSpeed(-operatorController.getRightY());
    
    //elevator.setElevatorSpeed(operatorController.getRightTriggerAxis()-operatorController.getLeftTriggerAxis());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
