/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
/* Imports */
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.*;
import frc.robot.commands.auto.AutoCenterGroup;
import frc.robot.commands.auto.AutoLeftGroup;
import frc.robot.commands.auto.AutoRightGroup;
import frc.robot.commands.macros.*;
import frc.robot.commands.startgame.*;
import frc.robot.gyro.Gyroscope;
import frc.robot.commands.teleop.TeleJoyDrive;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
  /* Class Variable Declaration */
  public Gyroscope Rgyro;
  /**
   * Top level commands
   */
  public TeleopGroup cg_TeleopGroup;

  private OI oi;
  public SendableChooser<Command> choice;

  /**
   * Subsystems
   */
  public Climber s_Climber = new Climber();
  public DriveTrain s_DriveTrain = new DriveTrain();
  public Indexer s_Indexer = new Indexer();
  public Intake s_Intake = new Intake();
  public Shooter s_Shooter = new Shooter();
  public Vision s_Vision = new Vision();
  public Turret s_Turret = new Turret();

  

  /**
   * Constructs the RobotContainer.
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {

    /* Class Variable Instantiation */
    
    Rgyro = new Gyroscope();
    cg_TeleopGroup = new TeleopGroup();
    choice = new SendableChooser<Command>();
    choice.addOption("Left", new AutoLeftGroup());
    choice.addOption("Right", new AutoRightGroup());
    choice.addOption("Center", new AutoCenterGroup());
    // Configure the button bindings
    configureButtonBindings();
    

    // //set defaults
    s_DriveTrain.setDefaultCommand(new TeleJoyDrive());
    s_Turret.setDefaultCommand(new MacroAim(s_Turret,false));
  }

  /**
   * Configure the command-button bindings according to the controls configuration stated in Constants.
   */
  public void configureButtonBindings()
  {
    // Configure button bindings as dictated by the controls configuration from Constants
    switch(Constants.controls)
    { 
      // Xbox controller, assigning numbers on drive station to buttons on the controller
      case XBOX:

        oi.addButton("btnxA", "Xbox", 1);
        oi.addButton("btnxB", "Xbox", 2);
        oi.addButton("btnxX", "Xbox", 3);
        oi.addButton("btnxY", "Xbox", 4);
        oi.addButton("btnxLB","Xbox", 5);
        oi.addButton("btnxRB","Xbox", 6);
        oi.addButton("BTBACK","Xbox", 7);

        oi.getButton("btnxA").whileHeld(/* Run the shooter */ new MacroFire(s_Indexer));
        oi.getButton("btnxX").toggleWhenPressed(/* Intake In */new MacroIntakeF(s_Intake));
        oi.getButton("btnxY").whenPressed(/* Indexer F */new MacroIndexerF(s_Indexer));
        oi.getButton("btnxB").whenPressed(/* Indexer R */new MacroIndexerR(s_Indexer));
        oi.getButton("btnxRB").whileHeld(/* Climbing up*/new MacroClimbDown(s_Climber));
        oi.getButton("btnxLB").whileHeld(/* Climbing down */new MacroClimbUp(s_Climber));
        oi.getButton("BTBACK").toggleWhenPressed(new MacroRunShooter(s_Shooter,false));
      break;

      // Left and right joystick controllers, assigning numbers on drive station to buttons on the controller
      case JOYSTICKS:
        // Right joystick
        oi.addButton("btn1","JoyR", 1);
        oi.addButton("btn3","JoyR", 3);
        oi.addButton("btn2","JoyR", 2);
        oi.addButton("btn4","JoyR", 4);
        oi.addButton("btn5","JoyR", 5);
        // Left joystick
        oi.addButton("btn3","JoyL", 3);
        oi.addButton("btn2","JoyL", 2);
        oi.addButton("btn4", "joyL", 4);

        // Referencing the added buttons when pressed
        oi.getButton("btn1").whileHeld(/* Run the shooter */ new MacroFire(s_Indexer));
        oi.getButton("btn3").toggleWhenPressed(/* Intake In */new MacroIntakeF(s_Intake));
        oi.getButton("btn2").toggleWhenPressed(/* Intake Out */ new MacroIntakeR(s_Intake));
        oi.getButton("btn3").whenPressed(/* Indexer F */new MacroIndexerF(s_Indexer));
        oi.getButton("btn2").whenPressed(/* Indexer R */new MacroIndexerR(s_Indexer));
        oi.getButton("btn4").whileHeld(/* Climbing down !!! need an up/down climb!!*/new MacroClimbDown(s_Climber));
        oi.getButton("btn5").whileHeld(/* Climbing up -!!! need an up/down climb!! */new MacroClimbUp(s_Climber));
        oi.getButton("btn4").toggleWhenPressed(new MacroRunShooter(s_Shooter,false));
        
      break;

      case DUALDRIVERS:
      System.out.println("Chosen: Dual Drivers");
        oi.addButton("BT3R", "JoyR", 3);
        oi.addButton("BT4R", "JoyR", 4);
        // oi.addButton("BT2L", "JoyL", 2);
        // oi.addButton("BT3L", "JoyL", 3);

        oi.addButton("LB", "Xbox", 5);
        oi.addButton("RB", "Xbox", 6);
        oi.addButton("BTX","Xbox", 3);
        oi.addButton("BTB","Xbox", 2);
        oi.addButton("BTY", "Xbox", 4);
        oi.addButton("BTA", "Xbox", 1);
        oi.addButton("BTBACK", "Xbox", 7);

        // Referencing the added buttons when pressed

        oi.getButton("BT3R").toggleWhenPressed(/* Intake In */ new MacroIntakeF(s_Intake));
        oi.getButton("BT4R").whileHeld(/* Intake Out */ new MacroIntakeR(s_Intake));
        // //oi.getButton("BT3L").whileHeld(new MacroClimbUp());
        // //oi.getButton("BT2L").whileHeld(new MacroClimbDown());

        oi.getButton("LB").whileHeld(new MacroRotateTurret(s_Turret,-1));
        oi.getButton("RB").whileHeld(new MacroRotateTurret(s_Turret, 1));
        oi.getButton("BTX").whileHeld(new MacroIndexerF(s_Indexer));
        oi.getButton("BTB").whileHeld(new MacroIndexerR(s_Indexer));
        oi.getButton("BTY").toggleWhenPressed(new MacroAim(s_Turret,false));
        oi.getButton("BTA").whenPressed(new MacroFire(s_Indexer));
        oi.getButton("BTBACK").toggleWhenPressed(new MacroRunShooter(s_Shooter,false));
        break;


      default:
        System.out.println("Controls configuration not selected");
      break;
    }
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  /**
   * Pass back teleop group
   * 
   * @return the command to run in teleop
   */
  public Command getTeleopCommand()
  {
    return cg_TeleopGroup;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    return choice.getSelected();
  }
}
