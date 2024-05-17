package pkgfinal.project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

import static pkgfinal.project.FinalProject.DPS;
import static pkgfinal.project.FinalProject.SUP;
import static pkgfinal.project.FinalProject.ANEMO;
import static pkgfinal.project.FinalProject.PYRO;
import static pkgfinal.project.FinalProject.CRYO;
import static pkgfinal.project.FinalProject.HYDRO;
import static pkgfinal.project.FinalProject.DENDRO;
import static pkgfinal.project.FinalProject.ELECTRO;

import static pkgfinal.project.FinalProject.GAME_NAME;

import static pkgfinal.project.FinalProject.CHANGE_CHARACTER;
import static pkgfinal.project.FinalProject.REFRESH_CHARACTER;
import static pkgfinal.project.FinalProject.REFRESH_BOSS;

import static pkgfinal.project.FinalProject.BOSS_ATTACKING;
import static pkgfinal.project.FinalProject.TEAM_ATTACKING;

import static pkgfinal.project.FinalProject.SKILL_DMG_VAL;
import static pkgfinal.project.FinalProject.ULTIMATE_DMG_VAL;

import static pkgfinal.project.FinalProject.NORMAL_ATK_USED;
import static pkgfinal.project.FinalProject.SKILL_USED;
import static pkgfinal.project.FinalProject.ULTIMATE_USED;

import static pkgfinal.project.FinalProject.TEAM_DEFEATED;
import static pkgfinal.project.FinalProject.BOSS_DEFEATED;

import static pkgfinal.project.FinalProject.NORMAL_TURN;
import static pkgfinal.project.FinalProject.TEAM_FROZEN;

/**
 * Eugene Thompson
 * CS173-B
 * May 10th, 2023
 * 
 * This Class Contains the Combat Graphical User Interface (GUI) used for the game Trial of the Archons
 * 
 * @author peichibunoreo
 */
public class combatGUI implements ActionListener {

    private int turn = 0;
    private int phaseCounter = 0;
    private int totalBossTurns = 0;
    
    final int maxAllyHP;
    final int maxBossHP;
    
    private Character currentCharacter;
    private String currentCharName;
    
    final ArrayList<Character> team;
    final Boss boss;

    JFrame frame;
    
    private JPanel statusPanel;
    private JPanel actionPanel;
    private JPanel selectionPanel;
    private JPanel allyPanel;
    private JPanel bossPanel;

    private JButton normalATK;
    private JButton skill;
    private JButton ultimate;
    
    private JLabel turnLabel;
    private JLabel bossActionLabel;
    private JLabel bossElementLabel;
    private JMenuBar menuBar;
    private JMenu gameMenu, helpMenu;
    private JMenuItem userQuitItem, bossHelpItem;

    private JComboBox characterSelect;

    @Override
    public void actionPerformed(ActionEvent e) {
    //Updates the Current Character including Pictures and Attributes
        if (e.getSource() == characterSelect) {
            JComboBox charSelect = (JComboBox) e.getSource();
            String selectedCharacter = (String) charSelect.getSelectedItem();
            currentCharacter = switchCharacter(selectedCharacter);
            currentCharName = selectedCharacter;
            updateEntity(CHANGE_CHARACTER);
        } else if (e.getSource() == userQuitItem) {
            System.exit(0);
        } else if (e.getSource() == normalATK) {
            useCharacterAbility(NORMAL_ATK_USED);
        } else if (e.getSource() == skill) {
            useCharacterAbility(SKILL_USED);
        } else if (e.getSource() == ultimate) {
            useCharacterAbility(ULTIMATE_USED);
        } 
    }
    /**
     * This Class Contains the Combat GUI for the Game, Including Characters, Bosses, and Abilities
     * This is the Turn-Based Part of the Game, Containing Various Panels 
     * These Panels Contain the Team and Boss' Images, Character Abilities, and Battle Status Indicators
     * And, it includes a Panel to Change the Current Character the Player is playing with
     * 
     * @param characters an array list of the player's team
     * @param boss a boss object of the current boss the player is fighting
     */
    public combatGUI(ArrayList<Character> characters, Boss boss) {
    //Initializing Variables relating to Team, Boss, and their respective stats
        Character startingCharacter = characters.get(0);
        this.maxAllyHP = characters.get(0).maxHealth;
        this.maxBossHP = boss.maxHealth;
        this.team = characters;
        this.currentCharacter = startingCharacter;
        this.currentCharName = currentCharacter.name;
        this.boss = boss;

    //Initialization of BorderLayout
        BorderLayout layout = new BorderLayout();

    //Initialization of Frame
        frame = new JFrame(GAME_NAME);
        frame.setLayout(layout);

    //Creating a Panel for the Frame
        menuBar = createMenuBar();
        statusPanel = createStatusPanel();
        selectionPanel = createCharSelectionPanel();
        allyPanel = createEntityPanel(currentCharName, allyPanel, currentCharacter.currentHealth, maxAllyHP);
        bossPanel = createEntityPanel(boss.name, bossPanel, boss.currentHealth, maxBossHP);
        actionPanel = createActionPanel();

    //Setting Parameters for the frame
        frame.setJMenuBar(menuBar);  
        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(allyPanel, BorderLayout.WEST);
        frame.add(selectionPanel, BorderLayout.CENTER);
        frame.add(bossPanel, BorderLayout.EAST);
        frame.add(actionPanel, BorderLayout.SOUTH);
        frame.pack();
    }

    /**
     * Creates a Menu Bar With an Option for the User to Quit, 
     * 
     * @return a JMenuBar with a Game and Help Menu with Menu Items to Quit Game
     */
    private JMenuBar createMenuBar() {
    //Initialization
        //For macOS only: Shows the Menu Bar on the System Menu Bar
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        
        //Initializing MenuBar, Menus, and Menu Items
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        userQuitItem = new JMenuItem("Quit Game");

    //Processing
        //Adding Action Listener to Menu Items
        userQuitItem.addActionListener(this);

    //Output
        //Putting Menu Items on Menus, and Menus on the Menu Bar
        gameMenu.add(userQuitItem);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
        
        return menuBar;
    }
    
    /**
     * Creates a Status Panel that Contains the Total Number of Turns Taken, and Boss Status
     * Boss Status includes Boss' Current Element (for specific Mechanics where the boss changes elements)
     * And, Boss' next Ability after Next User Turn, which provides the Player with Critical Information
     * 
     * @return a JPanel with the Turn Count, Boss' Next Ability, and Boss' Current Element
     */
    private JPanel createStatusPanel(){
    //Initialization
        //Initializing Border and Layouts
        String borderTitle = "Turn Count and Boss Status:";
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder(etchedBorder, borderTitle);
        FlowLayout leftAligned = new FlowLayout(FlowLayout.LEFT);
        FlowLayout centerAligned = new FlowLayout(FlowLayout.CENTER);
        FlowLayout rightAligned = new FlowLayout(FlowLayout.RIGHT);
        
        //Initializing Panels and Borders
        statusPanel = new JPanel();
        statusPanel.setBorder(titledBorder);
        JPanel turnPanel = new JPanel();
        JPanel bossActionPanel = new JPanel();
        JPanel bossElementPanel = new JPanel();

        //Initializing JLabels with Turn Count, Boss' Next Action, Boss' Current Element
        turnLabel = new JLabel("Turns Taken: " + turn + "   |");
        bossActionLabel = showBossNextSkill();
        bossElementLabel = new JLabel(boss.name +"'s Element is " + boss.currentElement);
    
        //Aligning Turn, Boss Action, and Boss Element Panels to be Left, Center, and Right respectively
        turnPanel.add(turnLabel, leftAligned);
        bossActionPanel.add(bossActionLabel, centerAligned);
        bossElementPanel.add(bossElementLabel, rightAligned);
        
        //Adding Turn, Boss Action, and Boss Element Panels to the Main Status Panel
        statusPanel.add(turnPanel, BorderLayout.WEST);
        statusPanel.add(bossActionPanel, BorderLayout.CENTER);
        statusPanel.add(bossElementPanel, BorderLayout.EAST);

    //Output
        return statusPanel;
    }
    
    /**
     * Creating a Character Selection Panel for the Player to Choose a Different Character in the Team
     * Has a Title Guiding the User to Switch Characters and a Drop-Down Menu with the Player's Characters
     * Initially, the First Character Selected is the First Character in the Team (First DPS Character Selected)
     * 
     * @return a JPanel with a Drop-Down Menu with all the Characters in the Team with User Guidance Title
     */
    private JPanel createCharSelectionPanel() {
    //Initialization
        //Initializing Borders and Titles for the Selection Panel
        String borderTitle = "Switch Between Characters Below:";
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder(etchedBorder, borderTitle);
        JLabel charSelectionTitle = new JLabel("Currently Playing as:");
        selectionPanel = new JPanel();
        selectionPanel.setBorder(titledBorder);
        
        //Initializing the JComboBox's Character Options
        String[] teamCharNames = new String[team.size()];
        
    //Processing
        //Adding the Team to the String Array for JComboBox
        for (int i = 0; i < team.size(); i++) {
            teamCharNames[i] = team.get(i).name;
        }
        //Adding Character Names to the Combo Box
        characterSelect = new JComboBox(teamCharNames);
        
        //Adding Action Listener for User Input
        characterSelect.addActionListener(this);
        
        //Adding Combo Box to Selection Panel alongside with the default Current Character
        characterSelect.setSelectedItem(currentCharacter);
        selectionPanel.add(charSelectionTitle);
        selectionPanel.add(characterSelect);
        
    //Output
        return selectionPanel;
    }
    
    /**
     * Creates a Character Action Panel containing the Abilities for Each Character
     * Contains a Description for each Ability, Player-Activatable Buttons for Fighting Bosses 
     * Buttons have Images Corresponding to the Actual Character's Ability Icons in Genshin Impact
     * If the image are not found, replaces Images with Text corresponding to the abilities
     * 
     * @return a JPanel containing the Current Character's Abilities with Buttons and Descriptions
     */
    private JPanel createActionPanel() {
    //Initialization
        //Border Titles for Abilities and Action Panels:
        String borderTitle = currentCharName + "'s Abilities:";
        String attackTitle = "Normal Attack:";
        String skillTitle = "Skill:";
        String ultimateTitle = "Ultimate:";
        
        //Border Types for Each Ability and Action Panels
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder abilitiesBorder = new TitledBorder(etchedBorder, borderTitle);
        TitledBorder attackBorder = new TitledBorder(etchedBorder, attackTitle);
        TitledBorder skillBorder = new TitledBorder(etchedBorder, skillTitle);
        TitledBorder ultimateBorder = new TitledBorder(etchedBorder, ultimateTitle);
        
        //Initializes all the Ability and Action Panels
        JPanel attackPanel = new JPanel();
        JPanel skillPanel = new JPanel();
        JPanel ultimatePanel = new JPanel();
        actionPanel = new JPanel();
        
        //Sets the Borders for the Ability and Action Panels
        attackPanel.setBorder(attackBorder);
        skillPanel.setBorder(skillBorder);
        ultimatePanel.setBorder(ultimateBorder);
        actionPanel.setBorder(abilitiesBorder);
        
        //Creating the Components for the Ability and Action Panels
        createActionButtons();
        JLabel normalAttackLabel = new JLabel("Deals 1 Damage to Boss:");
        JLabel skillLabel = getAbilityDescription(SKILL_USED);
        JLabel ultimateLabel = getAbilityDescription(ULTIMATE_USED);
        
    //Processing
        //Adding an Action Listener to Buttons for User Input
        normalATK.addActionListener(this);
        skill.addActionListener(this);
        ultimate.addActionListener(this);
        
        //Adding the Labels and Buttons to the Appropriate Panels
        attackPanel.add(normalAttackLabel);
        attackPanel.add(normalATK);
        skillPanel.add(skillLabel);
        skillPanel.add(skill);
        ultimatePanel.add(ultimateLabel);
        ultimatePanel.add(ultimate);

        //Adding the Ability Panels to the Main Action Panel
        actionPanel.add(attackPanel);
        actionPanel.add(skillPanel);
        actionPanel.add(ultimatePanel);
        
    //Output
        return actionPanel;
    }
    
    /**
     * Creates an Entity (Character or Boss) Panel with an Entity's Image and HP Bar
     * If the Image is not Found, replaces the Image Label with an Error Message
     * 
     * @param entityName a string with the entity's name
     * @param imagePanel a JPanel containing the corresponding entity's Image Panel
     * @param currentHP an integer value of the entity's current health points
     * @param maxHP an integer value of the entity's maximum health points
     * @return a JPanel with the Entity's Image and HP Bar
     */
    private JPanel createEntityPanel(String entityName, JPanel imagePanel, int currentHP, int maxHP) {
    //Initialization
        //Given Entity's Image Path in Project Folder
        String entityPicPath = "entityImages" +File.separator+ entityName +File.separator+ entityName + ".png";
        
        //Initializing the Border Layouts, Dimensions, and Panel for the Entity's Panel
        BorderLayout borderLayout = new BorderLayout();
        JPanel rValPanel = new JPanel(borderLayout);
        Dimension entityPanelSize = new Dimension(320, 400);
        rValPanel.setPreferredSize(entityPanelSize);
        
        //Initializing the Image Panel and Style of Layout
        imagePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS);
        imagePanel.setLayout(boxLayout);
        
        //HP Bar for Entities
        JProgressBar hpBar = new JProgressBar(0, maxHP);
        hpBar.setValue(currentHP);
        hpBar.setStringPainted(true);
        hpBar.setFont(new Font("Helvetica", Font.PLAIN, 15));
        hpBar.setForeground(Color.green);
        hpBar.setBackground(Color.red);
        
        //Image Icons for Entities
        ImageIcon entityImage = new ImageIcon(entityPicPath);
        JLabel entityImageLabel = new JLabel(entityImage);
        
        //Boolean Determining if the Entity's Image is Found and Loaded
        boolean hasImageLoaded = entityImage.getImageLoadStatus() == MediaTracker.COMPLETE;
        
    //Processing
        //If the Image is Not Found it Displays an Error Message with info about what Entity is being played
        if (!hasImageLoaded) {
            entityImageLabel = new JLabel("Cannot Find " + entityName + " Image File!");
            JLabel characterLabel = new JLabel("Currently Playing With: " + entityName);
            JPanel missingImagePanel = new JPanel();
            missingImagePanel.add(entityImageLabel);
            missingImagePanel.add(characterLabel);
            imagePanel.add(missingImagePanel);
        //Otherwise, loads the image into the Image Panel
        } else {
            entityImageLabel = new JLabel(entityImage);
            imagePanel.add(entityImageLabel);
        }
        
    //Adding Components to the Image and Entity Panels
        imagePanel.add(hpBar);
        rValPanel.add(imagePanel);

    //Output
        return rValPanel;
    }
    
    /**
     * Checks if the Character's Skill is Off Cooldown and Enables or Disables Ability Buttons Accordingly
     * If Abilities are on Cooldown, the corresponding ability is disabled, otherwise it remains enabled
     * 
     * @param abilityType a string with the Ability Type the Character is Currently Using
     * @return a boolean value if the skill is available off cooldown or not
     */
    private boolean isSkillAvailable(String abilityType){ //Need to Work on Ultimate Cooldown Working Properly
    //Initialization
        //Initializing Booleans, Character Down Status, Type of Ability Used, and Availability Status
        boolean rVal = false;
        boolean isCurrentCharacterDown = currentCharacter.currentHealth <= 0;
        boolean isNormalATKUsed = abilityType.equals(NORMAL_ATK_USED);
        boolean isSkillUsed = abilityType.equals(SKILL_USED);
        boolean isUltimateUsed = abilityType.equals(ULTIMATE_USED);
        boolean isSkillAvailable =  currentCharacter.skillCD % 2 == 0;
        boolean isUltimateAvailable = currentCharacter.ultCD % 3 == 0;
        //Initializing Resetting the Skill or Ultimate Cooldown
        final int resetSkillCD = 0;
        final int resetUltCD = 0;
        
    //Processing
        //Enabling Buttons If the Skill or Ultimate is Available
            skill.setEnabled(isSkillAvailable);
            ultimate.setEnabled(isUltimateAvailable);
        //If the Character is Down, then they are Not Able to Use Skills
        if (isCurrentCharacterDown){
            normalATK.setEnabled(false);
            skill.setEnabled(false);
            ultimate.setEnabled(false);
        //If the Character uses a Normal Attack and the Skill and Ultimate is On Cooldown, it increases the count
        } else if (isNormalATKUsed && (!isSkillAvailable && !isUltimateAvailable)){
            currentCharacter.skillCD++;
            currentCharacter.ultCD++;
            rVal = true; 
        //If the Character uses a Skill, it Resets the Cooldown of the Skill and Disables the Button
        } else if (isSkillUsed && isSkillAvailable){
            currentCharacter.skillCD = resetSkillCD;
            skill.setEnabled(false);
            rVal = true;
        //If the Character uses their Ultimate, it Resets the Cooldown of the Ultimate and Disables the Button
        } else if (isUltimateUsed && isUltimateAvailable){
            currentCharacter.skillCD = resetUltCD;
            ultimate.setEnabled(false);
            rVal = true;
        //If the Character uses a Normal Attack With all 
        } else if (isNormalATKUsed){
            rVal = true;
        }
        
//Output
        return rVal;
    }
    
    /**
     * Creates an Ability Description Label based on the Ability
     * 
     * @param abilityType a string specifying which ability is being described
     * @return a label with the corresponding character type's ability description
     */
    private JLabel getAbilityDescription(String abilityType){
    //Initialization
        //Booleans with Ability Type and Character's Role 
        boolean isSkill = abilityType.equals(SKILL_USED);
        boolean isUltimate = abilityType.equals(ULTIMATE_USED);
        boolean isCharacterDPS = currentCharacter.role.equals(DPS);
        JLabel rVal = new JLabel();
        
    //Processing
        //If and Else Statements with Descriptions of what Abilities do when buttons are pressed
        if (isSkill && isCharacterDPS){
            rVal = new JLabel("Deals 2 Damage to Boss:");
        } else if (isUltimate && isCharacterDPS){
            rVal = new JLabel("Deals 4 Damage to Boss:");
        } else if (isSkill && !isCharacterDPS){
            rVal = new JLabel("Heals " + currentCharName + " by 2 Health:");
        } else if (isUltimate && !isCharacterDPS){
            rVal = new JLabel("Heals Team by 4 Health:");
        }
        
    //Output
        return rVal;
    }
    
    /**
     * Shows what ability the Boss will use Next Player's Turn and Informs the User through a JLabel
     * 
     * @return a JLabel with a description of the Boss' next move or ability being used
     */
    private JLabel showBossNextSkill(){
    //Initialization
        //Booleans referring to how many turns the boss has taken, and what ability is available next turn
        boolean hasBattleStarted = totalBossTurns == 0;
        boolean isUltimateNextTurn = totalBossTurns % 5 == 4;
        boolean isSkillNextTurn = totalBossTurns % 3 == 2;
        
    //Processing
        //If and Else Statements Letting the Player Know what Abilities are Coming Up for the Player to Strategize
        if (hasBattleStarted){
            bossActionLabel = new JLabel("Battle Has Just Started, Please Take a Turn!" + "   |");
        } else if (isUltimateNextTurn){
            bossActionLabel = new JLabel(boss.name + " is using Their Ultimate After Your Next Turn, Watch Out!" 
                    +"   |");
        } else if (isSkillNextTurn){
            bossActionLabel = new JLabel(boss.name + " is using Their Skill After Your Next Turn!" +"   |");
        } else {
            bossActionLabel = new JLabel(boss.name + " is using their Normal Attack After Your Next Turn" +"   |");
        }
        
    //Output
        return bossActionLabel;
    }
    
    /**
     * Creating Action Buttons for the Current Character
     * Action Buttons referring to the Normal Attack, Skill, and Ultimate every Character has
     */
    private void createActionButtons (){
    //Initialization
        //Initialization of the File Path for each image with the Attack, 
        String atkIconPath = "entityImages" + File.separator + currentCharName
                + File.separator + currentCharName + "ATK.png";
        String skillIconPath = "entityImages" + File.separator + currentCharName
                + File.separator + currentCharName + "Skill.png";
        String ultIconPath = "entityImages" + File.separator + currentCharName
                + File.separator + currentCharName + "Ultimate.png";
        //Initialization of Image Icons
        ImageIcon attackIcon = new ImageIcon(atkIconPath);
        ImageIcon skillIcon = new ImageIcon(skillIconPath);
        ImageIcon ultIcon = new ImageIcon(ultIconPath);
        
        //Booleans Checking if All Images are Found and Loaded or Not
        boolean hasAllIconsLoaded = attackIcon.getImageLoadStatus() == MediaTracker.COMPLETE && 
                skillIcon.getImageLoadStatus() == MediaTracker.COMPLETE && 
                ultIcon.getImageLoadStatus() == MediaTracker.COMPLETE;
    //Processing
        //If Images are not Found, Sets the Button to a Text Button
        if (!hasAllIconsLoaded) {
            normalATK = new JButton("Use Attack");
            skill = new JButton("Use Skill");
            ultimate = new JButton("Use Ultimate");
        //Otherwise, loads the ImageIcons as Buttons
        } else {
            normalATK = new JButton(attackIcon);
            skill = new JButton(skillIcon);
            ultimate = new JButton(ultIcon);
            
        }
    }

    /**
     * Updates the Entity Based on the Condition Given in the Parameters
     * Either Changes Character to new User-Selected Character
     * Reloads Character Panel to the New Character HP Values
     * Reloads Boss Panel to the New Boss HP Values
     * 
     * @param reloadingReason a string containing the reason for updating a specific entity
     */
    private void updateEntity(String reloadingReason) {
    //Initialization
        //Determining if the Reason For Updating is to Change Character or Reload Character or Boss Stats
        boolean isChangeCharacter = reloadingReason.equals(CHANGE_CHARACTER);
        boolean isReloadCharacter = reloadingReason.equals(REFRESH_CHARACTER);
        boolean isReloadBoss = reloadingReason.equals(REFRESH_BOSS);


    //Processing
        //If Switching Characters, Reloads New Character's Action and Stats Panel
        if (isChangeCharacter){
            allyPanel.removeAll();
            allyPanel = createEntityPanel(currentCharName, allyPanel, currentCharacter.currentHealth, maxAllyHP);
            frame.add(allyPanel, BorderLayout.WEST);
            actionPanel.removeAll();
            actionPanel = createActionPanel();
            frame.add(actionPanel, BorderLayout.SOUTH);
        //If Reloading Characters, Refreshes Current Character's HP and Refreshes Status Indicators 
        } else if (isReloadCharacter) {
            allyPanel.removeAll();
            allyPanel = createEntityPanel(currentCharName, allyPanel, currentCharacter.currentHealth, maxAllyHP);
            frame.add(allyPanel, BorderLayout.WEST);
            
            statusPanel = createStatusPanel();
            frame.add(statusPanel, BorderLayout.NORTH);
        //If Reloading Boss, Refreshes Boss' HP and the Status Panel Indicators with Boss Element and Next Turn 
        } else if (isReloadBoss) {
            bossPanel.removeAll();
            bossPanel = createEntityPanel(boss.name, bossPanel, boss.currentHealth, maxBossHP);
            frame.add(bossPanel, BorderLayout.EAST);
            
            statusPanel = createStatusPanel();
            frame.add(statusPanel, BorderLayout.NORTH);
        } 
    //Refreshes the Main Frame to Update with the New Changes
        frame.revalidate();
    }
    
    /**
     * Determines Which Ability the Character is Using and Deals Damage or Heals depending on Character Type
     */
    private void useCharacterAbility(String abilityUsed){
        boolean canContinueTurn = isSkillAvailable(abilityUsed);
            if (canContinueTurn){
                startNewTurn(!TEAM_FROZEN, NORMAL_TURN);
                calculateEntityDamage(TEAM_ATTACKING, abilityUsed);
                calculateAllyHealing(abilityUsed, currentCharacter.role);
            } 
            updateEntity(REFRESH_BOSS);
    }
    
    /**
     * Starts a New Turn for Entities (Characters and Bosses)
     * Depending on Frozen Status of Either Boss or Character, the Turn is Considered Normal or Not
     * Leaving the Option of Skipping Turns of Frozen, and continuing as Normal if not
     * 
     * @param isTeamFrozen a boolean value determining if the team is currently frozen from a boss attack
     * @param isNormalTurn a boolean value determining if the turn cycle is normal or team takes a turn again
     */
    private void startNewTurn(boolean isTeamFrozen, boolean isNormalTurn) {
    //Initialization
        turnLabel.setText("Turns Taken: " + turn + "   |");
        frame.setTitle(GAME_NAME + ": Currently Fighting " + boss.name);
        //Iterating the Turn Counter for a New Turn
        if (isNormalTurn){
            turn++;
        }
        
    //Check for Low HP or Fallen Characters/Team or Bosses
        boolean isAllyFallen = currentCharacter.currentHealth <= 0;
        boolean isBossFallen = boss.currentHealth <= 0;
        checkEntityDown(isAllyFallen, isBossFallen);
        
    //Determining if the Boss is Acting this Turn or Not
        boolean isBossTurn = turn % 2 == 1;
        if (isBossTurn && isNormalTurn){
            startBossTurn();
        } else if (isTeamFrozen){
            turnLabel.setText("Your Team Cannot Move this Turn, currently Frozen!" +"   |");
            startBossTurn();
        }
        
    }
    
    /**
     * Starts the Boss' Turn and the Boss Uses the Appropriate Ability Based off of Total Number of Turns
     * And, changes the Boss' Element according to their Unique Boss Mechanics
     * 
     * @param isTurn boolean value if it is the boss' turn or not
     */
    private void startBossTurn(){ 
    //Initialization of Azhdaha Phases' HP Values
        double phaseOneHP = 0.5 *boss.maxHealth;
        double phaseTwoHP = 0.25* boss.maxHealth;
    //Initialization of Boss Mechanics 
        //Bosses with Unique Mechanics
        boolean isAzhdaha = boss.name.equals("Azhdaha");
        boolean isSignora = boss.name.equals("La Signora");
        //HP Thresholds for the Mechanics
        boolean isPhaseOne = boss.currentHealth <= phaseOneHP && phaseCounter == 0;
        boolean isPhaseTwo = boss.currentHealth <= phaseTwoHP && phaseCounter == 1;
    //Processing
        //Initializing the Cooldowns for the Boss' Abilities
        totalBossTurns++;
        boolean isSkillAvailable = totalBossTurns % 3 == 0;
        boolean isUltimateAvailable = totalBossTurns % 5 == 0;
        //Using the Appropriate Ability Based on the Boss' turns thus far
        if (isUltimateAvailable) {
            calculateEntityDamage(BOSS_ATTACKING, ULTIMATE_USED);
        } else if (isSkillAvailable) {
            calculateEntityDamage(BOSS_ATTACKING, SKILL_USED);
        } else {
            calculateEntityDamage(BOSS_ATTACKING, NORMAL_ATK_USED);
        }
    //Checking the Boss HP Threshold to Change the swap the Primary Attacking Element
        if ((isSignora || isAzhdaha) && isPhaseOne) {
            boss.currentElement = boss.secondaryElement;
            bossElementLabel = new JLabel(boss.name +"'s Element is " + boss.currentElement);
            phaseCounter++;
        }  else if (isAzhdaha && isPhaseTwo) {
            boss.currentElement = boss.tertiaryElement;
            bossElementLabel = new JLabel(boss.name +"'s Element is " + boss.currentElement);
        }
    //Updates the Characters HP
        updateEntity(REFRESH_CHARACTER);
        
    //Checks if the Boss or Team is Fallen at or below 0 Health Points
        boolean isAllyFallen = currentCharacter.currentHealth <= 0;
        boolean isBossFallen = boss.currentHealth <= 0;
        checkEntityDown(isAllyFallen, isBossFallen);
    }
    
    /**
     * Finds the Character that the Player switched to from the current Character
     * 
     * @param selectedCharacterName the name of the character the player chose
     * @return the player's selected character
     */
    private Character switchCharacter(String selectedCharacterName) {
    //Initialization
        int i = 0;
        boolean isMatchingCharacter = false;
        Character rVal = currentCharacter;
    //Processing
        while (!isMatchingCharacter) {
            isMatchingCharacter = team.get(i).name.equals(selectedCharacterName);
            //After Finding the Matching Character to the Player's Selection, it returns the Character
            if (isMatchingCharacter) {
                 rVal = team.get(i);
            }
            i++;
        }
    //Output
        return rVal;
    }
    
    /**
     * Calculates Damage Dealt by the Attacker to the Defending Party for both Entities (Character and Boss)
     * Depending on the Attacker/Defender's elements, either amplifying damage or inducing special effects
     * Then, the method outputs the defending party's HP after the damage calculations
     * 
     * @param isTeamAttacking boolean value of rather or not the player's team is attacking
     * @param entityHP defending entity's current health points
     * @param damage the attack value of the attacking entity 
     * @param attackerElement the attacking party's elemental power
     * @param enemyElement the defending party's elemental power
     * 
     * @return the defending party's HP Value after elemental damage calculations
     */
    private int calculateElementalDamage(boolean isTeamAttacking, int entityHP, int damage,
            String attackerElement, String enemyElement) {
    //Initialization
        //Initializing Damage Multipliers and Area of Effect (AoE) Damage Multipliers 
        int damageMultiplier = 1;
        int aoeDamage = 1;
        int baseDamage = (damage - 1);
        
        //Switches Elements if Boss Attacking Team to Calculate the appropriate elemental reaction
        //Inflicted Element Refers to the Current Character's Elemental Status when hit by the boss
        if (!isTeamAttacking) {
            enemyElement = attackerElement;
            currentCharacter.inflictedElement = attackerElement;
        }
        //Booleans Containing Relevant Types of Elemental Reactions that Genshin Impact Uses
        boolean isSameElement = attackerElement.equals(enemyElement);
        boolean isFreeze = ((attackerElement.equals(HYDRO) || enemyElement.equals(HYDRO))
                && (attackerElement.equals(CRYO) || enemyElement.equals(CRYO)));
        boolean isElectroCharged =  ((attackerElement.equals(HYDRO) || enemyElement.equals(HYDRO))
                && (attackerElement.equals(ELECTRO) || enemyElement.equals(ELECTRO)));
        boolean isSuperConduct =  ((attackerElement.equals(CRYO) || enemyElement.equals(CRYO))
                && (attackerElement.equals(ELECTRO) || enemyElement.equals(ELECTRO)));
        boolean isSwirl = (attackerElement.equals(ANEMO) || enemyElement.equals(ANEMO) && 
                !(attackerElement.equals(DENDRO) || enemyElement.equals(DENDRO)));
        boolean isAggravate = ((attackerElement.equals(DENDRO) || enemyElement.equals(DENDRO))
                && (attackerElement.equals(ELECTRO) || enemyElement.equals(ELECTRO)));
        boolean isVaporize = ((attackerElement.equals(PYRO) || enemyElement.equals(PYRO))
                && (attackerElement.equals(HYDRO) || enemyElement.equals(HYDRO)));
        boolean isMelt = ((attackerElement.equals(CRYO) || enemyElement.equals(CRYO))
                && (attackerElement.equals(PYRO) || enemyElement.equals(PYRO)));
        
        //Including Conditions for Scaramouche's Unique Boss Mechanic of being Hydro Immune
        boolean isHydroBasedReaction =  attackerElement.equals(HYDRO) || enemyElement.equals(HYDRO);
        boolean isHydroImmune = boss.elementalImmunity.equals(HYDRO);

    //Processing
        if (isSameElement) {
            entityHP -= damage;
        } else if (isAggravate || isMelt || (isVaporize && !isHydroImmune)) {
            damageMultiplier = 2;
            entityHP -= (damage * damageMultiplier);
        //If the Boss Attacks and Inflicts the Swirl or Superconduct Reaction, it damages the entire team
        } else if (!isTeamAttacking && (isSwirl || isSuperConduct || isElectroCharged)) {
            for (int i = 0; i < team.size(); i++) {
                Character attackedCharacter = team.get(i);
                attackedCharacter.currentHealth -= aoeDamage;
            }
            entityHP -= baseDamage;
        //If the Boss Freezes the Team, the Boss takes an additional turn
        } else if (isFreeze && !isTeamAttacking) {
            entityHP -= damage;
            startNewTurn(TEAM_FROZEN, NORMAL_TURN);
        //If the Team Attacks, the Boss is Frozen for One Turn so the team gains an additional turn
        } else if (isFreeze && isTeamAttacking && !isHydroImmune) {
            entityHP -= damage;
            startNewTurn(!TEAM_FROZEN, !NORMAL_TURN);
        } else if (isHydroBasedReaction && isHydroImmune){
        } else {
            entityHP -= damage;  
        }
    //Output
        return entityHP ;
    }
    
    /**
     * Calculates Attacking Entity (Character or Boss) Damage and Changes the Opposing Party's HP
     * 
     * @param attackingEntity the attacking party either the current character or the boss's turn 
     * @param skillType the skill type they are using: normal attack, skill, or ultimate
     */
    private void calculateEntityDamage(String attackingEntity, String skillType) {
    //Initialization
        //Booleans that Self-Describe what Ability, Attacking Party, and the current character's damage type 
        //Thus, determining what type of damage to inflict on the defending party
        boolean isTeam = true;
        boolean isNormalAttack = skillType.equals(NORMAL_ATK_USED);
        boolean isSkill = skillType.equals(SKILL_USED);
        boolean isUltimate = skillType.equals(ULTIMATE_USED);
        boolean isTeamAttacking = attackingEntity.equals(TEAM_ATTACKING);
        boolean isBossAttacking = attackingEntity.equals(BOSS_ATTACKING);
        boolean isCurrentCharacterDPS = currentCharacter.role.equals(DPS);

    //Processing
        //If and Else Statements Changing the Boss' Current Health based on the Character's Ability Used
        if (isTeamAttacking && isUltimate && isCurrentCharacterDPS ){
            boss.currentHealth = calculateElementalDamage(isTeam, boss.currentHealth, 
                    ULTIMATE_DMG_VAL, currentCharacter.currentElement, boss.currentElement);
        } else if (isTeamAttacking && isSkill & isCurrentCharacterDPS) {
            boss.currentHealth = calculateElementalDamage(isTeam, boss.currentHealth,
                    SKILL_DMG_VAL, currentCharacter.currentElement, boss.currentElement);
        } else if (isTeamAttacking && isNormalAttack) {
            boss.currentHealth = calculateElementalDamage(isTeam, boss.currentHealth,
                    currentCharacter.attack, currentCharacter.currentElement, boss.currentElement);
            
        //If and Else Statements changing Current Character's Current Health based on the Boss' Ability Used
        } else if (isUltimate && isBossAttacking){
            currentCharacter.currentHealth = calculateElementalDamage(!isTeam, currentCharacter.currentHealth, 
                    ULTIMATE_DMG_VAL, boss.currentElement, currentCharacter.inflictedElement);
        } else if (isSkill && isBossAttacking){
            currentCharacter.currentHealth = calculateElementalDamage(!isTeam, currentCharacter.currentHealth, 
                    SKILL_DMG_VAL, boss.currentElement, currentCharacter.inflictedElement);
        } else if (isNormalAttack && isBossAttacking){
            currentCharacter.currentHealth = calculateElementalDamage(!isTeam, currentCharacter.currentHealth, 
                    boss.attack, boss.currentElement, currentCharacter.inflictedElement);
        } else {
            
        }
    }
    
    /**
     * Calculates Healing by Support Type Characters and Heals Themselves or All Allies
     * 
     * @param abilityType The Ability Type that the character used either skill or ultimate ability
     * @param charRole The Role of the Character using the ability, support types are the only type that can heal
     */
    private void calculateAllyHealing(String abilityType, String charRole) {
    //Initialization
        //Booleans Determining if the Character is a Support and what ability they used
        boolean isSupportAndUsedSkill = abilityType.equals(SKILL_USED) && charRole.equals(SUP);
        boolean isSupportAndUsedUltimate = abilityType.equals(ULTIMATE_USED) && charRole.equals(SUP);
        boolean isOverheal = currentCharacter.currentHealth > maxAllyHP;
        boolean isCharacterSlain = false;
        
    //Processing
        //If Support Used Skill, Heal Current Character by 2 Health Points
        if (isSupportAndUsedSkill) {
            currentCharacter.currentHealth += 2;
        //Otherwise, if Support Used Ultimate, Heal Entire Team by 4 Health Points
        } else if (isSupportAndUsedUltimate) {

            for (int i = 0; i< team.size(); i++){
                Character healedCharacter = team.get(i);
                isCharacterSlain = healedCharacter.currentHealth <= 0;
                if (!isCharacterSlain){
                    healedCharacter.currentHealth +=4;
                }
                isOverheal = healedCharacter.currentHealth > maxAllyHP;
                if (isOverheal){
                    healedCharacter.currentHealth = maxAllyHP;
                }
                team.set(i, healedCharacter);
            }
        //If the Current Character is Overhealed past their maximum HP, set their HP to the max possible HP
        } if (isOverheal) {
            currentCharacter.currentHealth = maxAllyHP;
        }

    }
    
    /**
     * Checks if the Entity (Character or Boss) is currently down (HP is at zero) 
     * 
     * @param isCharacterFallen is true if HP is at or below zero, false otherwise
     * @param isBossFallen is true if HP is at or below zero, false otherwise
     */
    private void checkEntityDown(boolean isCharacterFallen, boolean isBossFallen){
    //Initialization
        //Initializes the Player's Team's Total Health Points
        int alliesTotalHP = 0;

    //Processing
        //Calculating the Total Health Points of the entire Team
        for (int i = 0; i< team.size(); i++){
                alliesTotalHP += team.get(i).currentHealth;
        }
        //If the Previous Number is at or Below Zero, then the team is defeated and the battle ends
        boolean allAlliesDefeated = alliesTotalHP <= 0;
        endBattle(allAlliesDefeated, TEAM_DEFEATED);
        //If there is at least one character that is defeated, then it guides the player to switch to another character
        if (isCharacterFallen){
            String defeatedCharacterName = currentCharacter.name;
            JOptionPane.showMessageDialog(frame, defeatedCharacterName + " has been slain, "
                    + "Please switch to Another Character!", "Defeated Character Notice", 
                    JOptionPane.INFORMATION_MESSAGE);
        //Otherwise, if the boss is defeated, it ends the battle
        } else if (isBossFallen){
            endBattle(isBossFallen, BOSS_DEFEATED);
        }
        
    }
    
    /**
     * Ends the Current Boss Fight and Sends an Ending Reason back to the GUI Switcher Class
     * 
     * @param isBattleEnded a boolean value either the battle ended from team loss or boss slain or not
     * @param endReason the Reason Statement either the Entire Team or Boss was Defeated
     */
    private void endBattle(boolean isBattleEnded, String endReason) {
    //Initialization
        //Determines if the Boss is Defeated
        boolean isBossDefeated = endReason.equals(BOSS_DEFEATED);
        //If the boss is defeated then Displays an End of Combat Message, and removes the window
        if (isBattleEnded && isBossDefeated) {
            frame.setVisible(false);
            GUISwitcher switchGUI = new GUISwitcher(BOSS_DEFEATED, boss.region, team);
            frame.dispose();
        //If the team is defeated (the other option), and it displays an End of Combat Message 
        //And, it removes the window and opens the ending page GUI
        } else if (isBattleEnded){
            frame.setVisible(false);
            GUISwitcher switchGUI = new GUISwitcher(TEAM_DEFEATED, boss.region, team);
            frame.dispose();

        }
    }
    
}

