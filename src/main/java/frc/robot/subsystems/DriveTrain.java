package frc.robot.subsystems;

/* Imports */
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/**
 * Subsystem used to managed the drive train of the robot.
 */
public class DriveTrain extends SubsystemBase
{
    /* TODO: MagEncoders Untested */

    /* Class Variable Declaration */
    TalonSRX talLM, talLF, talRM, talRF;

    /**
     * Constructs the DriveTrain subsystem.
     */
    public DriveTrain()
    {
        /* Talon Initialization */
        talLM = new TalonSRX(Constants.p_TAL_LM);
        talLF = new TalonSRX(Constants.p_TAL_LF);
        talRM = new TalonSRX(Constants.p_TAL_RM);
        talRF = new TalonSRX(Constants.p_TAL_RF);

        // Configures each of the mag enconders on the master Drive Train talons
        // The "relative" configuration indicates that they will work identically
        // to quadrature encoders (as opposed to PWM Sensors). 
        talLM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        talRM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        // Invert the right side || TODO: This may need to change once we test on the competition drive train
        talRM.setInverted(true);
        talRF.setInverted(true);
    }

    /**
     * Set talons to given input speeds.
     * 
     * @param velocityL
     * @param velocityR
     */
    public void set(double velocityL, double velocityR)
    {
        talLM.set(ControlMode.PercentOutput, velocityL);
        talLF.set(ControlMode.Follower, Constants.p_TAL_LM);

        talRM.set(ControlMode.PercentOutput, velocityR);
        talRF.set(ControlMode.Follower, Constants.p_TAL_RM);
    }

    /**
     * @return The distance traveled by the left side of the drive train
     * (currently in revolutions).
     */
    public double getLeftDist()
    {
        return (talLM.getSelectedSensorPosition() / Constants.ENCODER_PPR) * Constants.DIST_PER_ROTATION;
    }

    /**
     * @return The distance traveled by the right side of the drive train
     * (currently in revolutions).
     */
    public double getRightDist()
    {
        return (talRM.getSelectedSensorPosition() / Constants.ENCODER_PPR) * Constants.DIST_PER_ROTATION;
    }

    /**
     * Reset the distance traveled by both sides of the drive train.
     */
    public void resetEncoders()
    {
        talLM.setSelectedSensorPosition(0);
        talRM.setSelectedSensorPosition(0);
    }
}