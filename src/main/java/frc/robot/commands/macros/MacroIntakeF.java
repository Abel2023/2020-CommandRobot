package frc.robot.commands.macros;

/* Imports */
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;
import frc.robot.subsystems.Intake;

/**
 * Command to run the intake forwards (into the robot).
 * 
 * Requires the intake subsystem.
 */
public class MacroIntakeF extends CommandBase
{
    /* TODO: Untested Code */

    /* Instance Variable Declaration */
    Intake _intake;
    
    /**
     * Constructs a new MacroIntakeF command with an Intake requirement.
     */
    public MacroIntakeF()
    {
        _intake = RobotContainer.s_Intake;
        addRequirements(_intake);
    }

    @Override
    public void execute()
    {
        _intake.set(Constants.INDEX_VELOCITY_F);
    }
}