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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SwerveConstants;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class Chassis extends SubsystemBase {
  private AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private SwerveModule moduleA = new SwerveModule(SwerveConstants.A_THROTTLE_ID, SwerveConstants.A_ROTOR_ID, SwerveConstants.A_ENCODER_ID,
      SwerveConstants.A_OFFSET);
  private SwerveModule moduleB = new SwerveModule(SwerveConstants.B_THROTTLE_ID, SwerveConstants.B_ROTOR_ID, SwerveConstants.B_ENCODER_ID,
      SwerveConstants.B_OFFSET);
  private SwerveModule moduleC = new SwerveModule(SwerveConstants.C_THROTTLE_ID, SwerveConstants.C_ROTOR_ID, SwerveConstants.C_ENCODER_ID,
      SwerveConstants.C_OFFSET);
  private SwerveModule moduleD = new SwerveModule(SwerveConstants.D_THROTTLE_ID, SwerveConstants.D_ROTOR_ID, SwerveConstants.D_ENCODER_ID,
      SwerveConstants.D_OFFSET);
  private SwerveDriveOdometry odometry = new SwerveDriveOdometry(SwerveConstants.SWERVE_KINEMATIS, getRotation2d(),
      getModulePositions());

  public Chassis() {
    AutoBuilder.configureHolonomic(
        this::getPose, // Robot pose supplier
        this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
        this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
        this::drive, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
        new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
            new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
            new PIDConstants(5.0, 0.0, 0.0), // Rotation PID constants
            4.5, // Max module speed, in m/s
            0.4, // Drive base radius in meters. Distance from robot center to furthest module.
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
  }

  @Override
  public void periodic() {
  }

  public void drive(ChassisSpeeds chassisSpeeds) {
    double xSpeed = chassisSpeeds.vxMetersPerSecond;
    double ySpeed = chassisSpeeds.vyMetersPerSecond;
    double rSpeed = chassisSpeeds.omegaRadiansPerSecond;
    SwerveModuleState[] states = SwerveConstants.SWERVE_KINEMATIS.toSwerveModuleStates(
        ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rSpeed, getRotation2d()));
    setModuleStates(states);
  }

  public void setModuleStates(SwerveModuleState[] states) {
    moduleA.setState(states[0]);
    moduleB.setState(states[1]);
    moduleC.setState(states[2]);
    moduleD.setState(states[3]);
  }

  public SwerveModuleState[] getModuleStates(){
    return new SwerveModuleState[]{
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
    return SwerveConstants.SWERVE_KINEMATIS.toChassisSpeeds(getModuleStates());
  }
}
