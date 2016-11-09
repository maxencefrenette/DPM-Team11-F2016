package ca.mcgill.ecse211.team11;

import lejos.hardware.motor.NXTRegulatedMotor;

/**
 * This class controls the motors for the claw and its lifting mechanism.
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 *
 */
public class ClawMotorController {

  private NXTRegulatedMotor clawClosingMotor;
  private NXTRegulatedMotor clawRaisingMotor;
  
  public ClawMotorController(NXTRegulatedMotor clawClosingMotor, NXTRegulatedMotor clawRaisingMotor) {
    this.clawClosingMotor = clawClosingMotor;
    this.clawRaisingMotor = clawRaisingMotor;
  }
  
  public void closeClaw() {
    //TODO Add code
  }
  
  public void openClaw() {
    //TODO Add code
  }
  
  public void raiseClaw() {
    //TODO Add code
  }
  
  public void lowerClaw() {
    //TODO Add code
  }
}
