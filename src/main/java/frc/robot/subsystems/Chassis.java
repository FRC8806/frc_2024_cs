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
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SwerveConstants;

import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class Chassis extends SubsystemBase {
  public boolean updatePose = false;
  private AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private SwerveModule moduleA = new SwerveModule(SwerveConstants.A_THROTTLE_ID, SwerveConstants.A_ROTOR_ID,
      SwerveConstants.A_ENCODER_ID,
      SwerveConstants.A_OFFSET);
  private SwerveModule moduleB = new SwerveModule(SwerveConstants.B_THROTTLE_ID, SwerveConstants.B_ROTOR_ID,
      SwerveConstants.B_ENCODER_ID,
      SwerveConstants.B_OFFSET);
  private SwerveModule moduleC = new SwerveModule(SwerveConstants.C_THROTTLE_ID, SwerveConstants.C_ROTOR_ID,
      SwerveConstants.C_ENCODER_ID,
      SwerveConstants.C_OFFSET);
  private SwerveModule moduleD = new SwerveModule(SwerveConstants.D_THROTTLE_ID, SwerveConstants.D_ROTOR_ID,
      SwerveConstants.D_ENCODER_ID,
      SwerveConstants.D_OFFSET);

  private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(SwerveConstants.A_TRANSLATION2D,
      SwerveConstants.B_TRANSLATION2D, SwerveConstants.C_TRANSLATION2D, SwerveConstants.D_TRANSLATION2D);
  private SwerveDriveOdometry odometry = new SwerveDriveOdometry(kinematics, getRotation2d(),
      getModulePositions());
  private Field2d field = new Field2d();
  private Supplier<Boolean> isRedAlliance;

  public Chassis(Supplier<Boolean> isRedAlliance) {
    this.isRedAlliance = isRedAlliance;
    AutoBuilder.configureHolonomic(
        this::getPose, // Robot pose supplier
        this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
        this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
        this::autoDrive, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
        new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
            new PIDConstants(SwerveConstants.translationKP, SwerveConstants.translationKI,
                SwerveConstants.translationKD), // Translation PID constants
            new PIDConstants(SwerveConstants.rotationKP, SwerveConstants.rotationKI, SwerveConstants.rotationKD), // Rotation
                                                                                                                  // PID
                                                                                                                  // constants
            SwerveConstants.maxModuleSpeed, // Max module speed, in m/s
            SwerveConstants.driveBaseRadius, // Drive base radius in meters. Distance from robot center to furthest
                                             // module.
            new ReplanningConfig() // Default path replanning config. See the API for the options here
        ),
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red
          // alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE
          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this // Reference to this subsystem to set requirements
    );
    PathPlannerLogging.setLogActivePathCallback((poses) -> field.getObject("path").setPoses(poses));
    SmartDashboard.putData("field", field);
  }

  @Override
  public void periodic() {
    // if(updatePose){odometry.update(getRotation2d(), getModulePositions());}
    odometry.update(getRotation2d(), getModulePositions());
    field.setRobotPose(getPose());
    SmartDashboard.putNumber("ahrs", -ahrs.getAngle());
    SmartDashboard.putNumber("pose-z", getPose().getRotation().getDegrees());
    SmartDashboard.putNumber("pose-x", getPose().getX());
    SmartDashboard.putNumber("pose-y", getPose().getY());
    SmartDashboard.putNumber("module a angle", moduleA.getState().angle.getRotations());
    SmartDashboard.putNumber("module b angle", moduleB.getState().angle.getRotations());
    SmartDashboard.putNumber("module c angle", moduleC.getState().angle.getRotations());
    SmartDashboard.putNumber("module d angle", moduleD.getState().angle.getRotations());
  }

  // public void drive(ChassisSpeeds chassisSpeeds) {
  //   double xSpeed = chassisSpeeds.vxMetersPerSecond;
  //   double ySpeed = chassisSpeeds.vyMetersPerSecond;
  //   double rSpeed = chassisSpeeds.omegaRadiansPerSecond;

  //   SwerveModuleState[] states = kinematics.toSwerveModuleStates(
  //       ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rSpeed,
  //           isRedAlliance.get() ? Rotation2d.fromDegrees(odometry.getPoseMeters().getRotation().getDegrees() + 180)
  //               : odometry.getPoseMeters().getRotation()));
  //   setModuleStates(states);
  // }
  public void drive(ChassisSpeeds chassisSpeeds) {
    autoDrive(ChassisSpeeds.fromFieldRelativeSpeeds(chassisSpeeds, getPose().getRotation()));
  }

  public void autoDrive(ChassisSpeeds chassisSpeeds) {
    ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(chassisSpeeds, 0.02);
    SwerveModuleState[] targetStates = kinematics.toSwerveModuleStates(targetSpeeds);
    setModuleStates(targetStates);
  }

  public void setModuleStates(SwerveModuleState[] states) {
    SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveConstants.kMaxThrottleSpeed);
    moduleA.setState(states[0]);
    moduleB.setState(states[1]);
    moduleC.setState(states[2]);
    moduleD.setState(states[3]);
  }

  public SwerveModuleState[] getModuleStates() {
    return new SwerveModuleState[] {
        moduleA.getState(),
        moduleB.getState(),
        moduleC.getState(),
        moduleD.getState()
    };
  }

  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
        moduleA.getPosition(),
        moduleB.getPosition(),
        moduleC.getPosition(),
        moduleD.getPosition()
    };
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public void resetPose(Pose2d pose) {
    odometry.resetPosition(getRotation2d(), getModulePositions(), pose);
  }

  private Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(-ahrs.getAngle());
  }

  public ChassisSpeeds getRobotRelativeSpeeds() {
    return kinematics.toChassisSpeeds(getModuleStates());
  }
}
