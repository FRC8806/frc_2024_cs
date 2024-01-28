// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Limit {
    public double setLimit(double setSpeed, double states, double highLimit, double lowLimit, double upRange, double downRange) {
        double Speed;
        double range = highLimit-lowLimit;
        if(range < 0) setSpeed = -setSpeed;
        if ( highLimit-states < 100 && setSpeed > 0){
            Speed = setSpeed * (highLimit - states)/(upRange*100);
        }else if (states-lowLimit < 100 && setSpeed < 0){
            Speed = setSpeed * (states - lowLimit)/(downRange*100);
        }else {
            Speed = setSpeed;
        }
        if(range < 0) Speed = -Speed;
      return Speed;
    }

    // public double setLimit(double setSpeed, double states, double highLimit, double lowLimit, double upRange, double downRange) {
    //     double Speed;
    //     double range = highLimit-lowLimit;
    //     upRange =upRange*range;
    //     downRange=downRange*range;
    //     if(range < 0) setSpeed = -setSpeed;
    //     if ( highLimit-states < upRange && setSpeed > 0){
    //         Speed = setSpeed * (highLimit - states)/upRange;
    //     }else if (states-lowLimit < downRange && setSpeed < 0){
    //         Speed = setSpeed * (states - lowLimit)/downRange;
    //     }else {
    //         Speed = setSpeed;
    //     }
    //     if(range < 0) Speed = -Speed;
    //   return Speed;
    // }
}
