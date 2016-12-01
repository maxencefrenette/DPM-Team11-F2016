package ca.mcgill.ecse211.team11;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.NXTRegulatedMotor;

/**
 * Controls the motors for the claw and its lifting mechanism.
 * 
 * @author Justin Szeto
 * @version 4.1
 * @since 1.0
 *
 */
public class ClawMotorController {

  private EV3MediumRegulatedMotor clawClosingMotor;
  private NXTRegulatedMotor clawRaisingMotor;

  public ClawMotorController(EV3MediumRegulatedMotor clawClosingMotor,
      NXTRegulatedMotor clawRaisingMotor) {
    this.clawClosingMotor = clawClosingMotor;
    this.clawRaisingMotor = clawRaisingMotor;
  }

  /**
   * Closes the claw.
   */
  public void closeClaw() {
    clawClosingMotor.setSpeed(Constants.CLAW_CLOSING_SPEED);
    clawClosingMotor
        .rotateTo(clawClosingMotor.getTachoCount() + Constants.CLAW_CLOSING_DEGREE_ROTATION);
  }

  /**
   * Opens the claw.
   */
  public void openClaw() {
    clawClosingMotor.setSpeed(Constants.CLAW_OPENING_SPEED);
    clawClosingMotor
        .rotateTo(clawClosingMotor.getTachoCount() + Constants.CLAW_OPENING_DEGREE_ROTATION);
  }

  /**
   * Raises the claw.
   */
  public void raiseClaw() {
    clawRaisingMotor.setSpeed(Constants.CLAW_RAISING_SPEED);
    clawRaisingMotor
        .rotateTo(clawRaisingMotor.getTachoCount() + Constants.CLAW_RAISING_DEGREE_ROTATION);
  }

  /**
   * Lowers the claw.
   */
  public void lowerClaw() {
    clawRaisingMotor.setSpeed(Constants.CLAW_LOWERING_SPEED);
    clawRaisingMotor
        .rotateTo(clawRaisingMotor.getTachoCount() + Constants.CLAW_LOWERING_DEGREE_ROTATION);
  }
}
