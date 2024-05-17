package pkgfinal.project;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import static pkgfinal.project.FinalProject.GAME_NAME;
import static pkgfinal.project.FinalProject.DPS;
import static pkgfinal.project.FinalProject.SUP;
import static pkgfinal.project.FinalProject.CHANGE_CHARACTER;
import static pkgfinal.project.FinalProject.START_BATTLE;
import static pkgfinal.project.FinalProject.KHAENRIAH;
import static pkgfinal.project.FinalProject.getCharacterDescription;


/**
 * Eugene Thompson
 * CS173-B
 * May 10th, 2023
 * 
 * This Class contains the Team Selection GUI Allowing the Player to Select their Team 
 * 
 * @author peichibunoreo
 */
public class teamSelectionGUI implements ActionListener{
    
    private Character currentlySelectedCharacter;
    
    int switchingToSupportSelectionCounter = 0;
    final int maxTeamSize = 4;
    final String charSelectionTitle = "Select Your Team Here:";
    
    ArrayList<Character> team;
    private final  ArrayList<Character> charList;
    
    JFrame frame;

    private JPanel currentCharacterPanel;
    private JPanel charDescriptionPanel;
    private JPanel dpsSelectionPanel;
    private JPanel supSelectionPanel;
    
    
    
    private JButton dpsSelectionButton;
    private JButton supSelectionButton;
    

    
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem userQuitItem;
    
    private JComboBox dpsCharacterSelect;
    private JComboBox supCharacterSelect;

    @Override
    public void actionPerformed(ActionEvent e) {
        //If the User uses User Quit or Wishes to Leave, it quits the game
        if (e.getSource() == dpsCharacterSelect) {
            JComboBox charSelect = (JComboBox) e.getSource();
            String selectedCharacter = (String) charSelect.getSelectedItem();
            currentlySelectedCharacter = getCharacter(selectedCharacter);
            updatePanels(CHANGE_CHARACTER);
        } else if(e.getSource() == supCharacterSelect){
            JComboBox charSelect = (JComboBox) e.getSource();
            String selectedCharacter = (String) charSelect.getSelectedItem();
            currentlySelectedCharacter = getCharacter(selectedCharacter);
            updatePanels(CHANGE_CHARACTER);
        } else if (e.getSource() == userQuitItem) {
            System.exit(0);
        } else if (e.getSource() == dpsSelectionButton){
            makeSelection();
        } else if (e.getSource() == supSelectionButton){
            makeSelection();
        } 
    }
    /**
     * Contains the Team Selection GUI, providing the User With Options to Select Characters
     * 
     * @param characters an ArrayList with all the Characters
     */
    public teamSelectionGUI(ArrayList<Character> characters){
    //Initializing Variables relating to Team, and Characters
        Character initialCharacter = characters.get(0);
        this.currentlySelectedCharacter = initialCharacter;
        this.team = new ArrayList<>();
        this.charList = characters;
        
    //Initialization of BorderLayout
        BorderLayout layout = new BorderLayout();
        
    //Initialization of Frame
        frame = new JFrame(GAME_NAME + ": We Will Be Reunited");
        frame.setLayout(layout);
        
    //Creating Components for the Main Frame
        menuBar = createMenuBar();
        currentCharacterPanel = createCharImagePanel(initialCharacter);
        createDPSCharSelectionPanel();
        charDescriptionPanel = createCharDescriptionPanel(initialCharacter);
        
        //Welcome Label
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setText("<html>Welcome to the Team Selection Process! <br> Below you will Find the "
                + "Character of Your Choosing and a Drop-Down Menu to Look at other Characters<br> "
                + "To select a Character, simply press the Select Button and the Character Will be Added to the Team"
                + "<br></html>");

        frame.setJMenuBar(menuBar);  
        frame.add(currentCharacterPanel, BorderLayout.WEST);
        frame.add(welcomeLabel, BorderLayout.NORTH);
        frame.add(charDescriptionPanel, BorderLayout.CENTER);
        frame.add(dpsSelectionPanel, BorderLayout.SOUTH);
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
     * Creating a Character Selection Panel for the Player to Choose between Various Characters in DPS Role
     * Has a Title Guiding the User to Switch Characters and a Drop-Down Menu with the Player's Characters
     * It also has a Select Button for Characters to Select the Current Character
     * 
     * @return a JPanel with a Drop-Down Menu  with all the Characters in the Team with User Guidance Title
     *  And, it contains a button for the user to select Characters, 
     */
    private void createDPSCharSelectionPanel() {
    //Initialization
        //Initializing Borders and Titles for the Selection Panel
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder(etchedBorder, charSelectionTitle);
        dpsSelectionPanel = new JPanel();
        dpsSelectionPanel.setBorder(titledBorder);

        //Initializing the DPS Character List
        String[] dpsCharList = getCharSelectionList(DPS);
        
        //Adding Character Names to the Combo Box
        dpsCharacterSelect = new JComboBox(dpsCharList);
        
        //Initializing Selection Button
        dpsSelectionButton = new JButton("Select This Character");
        
        //Adding Action Listener for User Input
        dpsSelectionButton.addActionListener(this);
        dpsCharacterSelect.addActionListener(this);
        
        //Adding Combo Box to Selection Panel alongside with the default Current Character
        dpsCharacterSelect.setSelectedItem(currentlySelectedCharacter);
        dpsSelectionPanel.add(dpsCharacterSelect);
        dpsSelectionPanel.add(dpsSelectionButton);
        
    }
    
    /**
     * Creating a Character Selection Panel for the Player to Choose between Various Characters in SUP Role
     * Has a Title Guiding the User to Switch Characters and a Drop-Down Menu with the Player's Characters
     * It also has a Select Button for Characters to Select the Current Character
     * 
     * @return a JPanel with a Drop-Down Menu  with all the Characters in the Team with User Guidance Title
     *  And, it contains a button for the user to select Characters, 
     */
    private void createSUPCharSelectionPanel() {
    //Initialization
        //Initializing Borders and Titles for the Selection Panel
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder(etchedBorder, charSelectionTitle);
        supSelectionPanel = new JPanel();
        supSelectionPanel.setBorder(titledBorder);

        //Initializing the DPS Character List
        String[] supCharList = getCharSelectionList(SUP);
        //Adding Character Names to the Combo Box
        supCharacterSelect = new JComboBox(supCharList);
        
        //Initializing Selection Button
        supSelectionButton = new JButton("Select This Character");
        
        //Adding Action Listener for User Input
        supSelectionButton.addActionListener(this);
        supCharacterSelect.addActionListener(this);
        
        //Adding Combo Box to Selection Panel alongside with the default Current Character
        dpsCharacterSelect.setSelectedItem(currentlySelectedCharacter);
        supSelectionPanel.add(supCharacterSelect);
        supSelectionPanel.add(supSelectionButton);
    }
    
    /**
     * Creates an Panel with the Selected Character's Image and Description of Character's Type and Element
     * If the Image is not Found, replaces the Image Label with an Error Message
     * 
     * @param character the current Character Selected
     * @return a JPanel with the Character's Image and HP Bar
     */
    private JPanel createCharImagePanel(Character character) {
    //Initialization
        //Given Character's Image Path in Project Folder
        String charName = character.name;
        String charPicPath = "entityImages" +File.separator+ charName +File.separator+ charName + ".png";
        
        //Initializing the Border Layouts, Dimensions, and Panel for the Character's Panel
        BorderLayout borderLayout = new BorderLayout();
        JPanel rValPanel = new JPanel(borderLayout);
        Dimension charPanelSize = new Dimension(320, 400);
        rValPanel.setPreferredSize(charPanelSize);
        
        //Initializing the Image Panel and Style of Layout
        JPanel imagePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS);
        imagePanel.setLayout(boxLayout);
        
        //Image Icons for Character
        ImageIcon charImage = new ImageIcon(charPicPath);
        JLabel charImageLabel = new JLabel(charImage);
       
        
        //Boolean Determining if the Character's Image is Found and Loaded
        boolean hasImageLoaded = charImage.getImageLoadStatus() == MediaTracker.COMPLETE;
        
    //Processing
        //If the Image is Not Found it Displays an Error Message with info about what Character is being played
        if (!hasImageLoaded) {
            charImageLabel = new JLabel("Cannot Find " + charName + " Image File!");
            JLabel characterLabel = new JLabel("Currently Selecting: " + charName);
            JPanel missingImagePanel = new JPanel();
            missingImagePanel.add(charImageLabel);
            missingImagePanel.add(characterLabel);
            imagePanel.add(missingImagePanel);
        //Otherwise, loads the image into the Image Panel
        } else {
            charImageLabel = new JLabel(charImage);
            imagePanel.add(charImageLabel);
        }
        
    //Adding Components to the Image and Character Panels
        rValPanel.add(imagePanel);

    //Output
        return rValPanel;
    }
    
    /**
     * Creating a Brief Character Description Label for the Selected Character
     * 
     * @param character the character the player selected
     * @return a JLabel with a Description of the Selected Character and a Brief Biography
     */
    private JPanel createCharDescriptionPanel (Character character){
//Initialization
    //Initialize JPanel
        JPanel rValPanel = new JPanel();
        
    //Output
        //Brief Description of Character
        JLabel charDescriptionLabel = new JLabel();
        
        //Obtains Method from Main Class to Get Character's description
        String characterDescription = getCharacterDescription(character);
        charDescriptionLabel.setText(characterDescription);
        charDescriptionLabel.setVerticalAlignment(JLabel.NORTH);
        charDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        //Adding Label to Panel
        rValPanel.add(charDescriptionLabel);
        
        return rValPanel;
    }
    
    /**
     * Updates Panels if User Changes Character Option (Triggered by Action Listener)
     */
    private void updatePanels(String updateReason) {
    //Initialization

        boolean isChangingCharacter = updateReason.equals(CHANGE_CHARACTER);
        boolean isChoosingSUP = team.size() == 2 && switchingToSupportSelectionCounter == 0; 
        String[] supList = getCharSelectionList(SUP);
        
    //Initialization and Processing
        //Updating Character Selection Panel
        if (isChoosingSUP && isChangingCharacter){
                dpsSelectionPanel.removeAll();
                frame.remove(dpsSelectionPanel);
                dpsSelectionPanel.setVisible(false);
                createSUPCharSelectionPanel();
                frame.add(supSelectionPanel, BorderLayout.SOUTH);
                switchingToSupportSelectionCounter++;
            }
        currentCharacterPanel.removeAll();
        charDescriptionPanel.removeAll();
        
        currentCharacterPanel = createCharImagePanel(currentlySelectedCharacter);
        charDescriptionPanel = createCharDescriptionPanel(currentlySelectedCharacter);

        frame.add(currentCharacterPanel, BorderLayout.WEST);
        frame.add(charDescriptionPanel, BorderLayout.CENTER);

        
    //Output
        //Refreshes the Main Frame to Update with the New Changes
        frame.revalidate();
    }
    
    /**
     * Gets the Character Selection List based on the Character's Type for the JComboBox
     * 
     * @param charType a string value containing the characters role
     * @return a 
     */
    private String[] getCharSelectionList(String charType){
    //Initialization
        final int selectionSize = charList.size()/2;
        final int supSelectIndex = selectionSize;
        String[] rVal = new String[selectionSize];
        boolean isDPSList = charType.equals(DPS);
        boolean isSUPList = charType.equals(SUP);
    //Processing
        if (isDPSList){
            for (int i = 0; i< selectionSize; i++){
                rVal[i] = charList.get(i).name;
            }
        } else if (isSUPList){
            for (int m = 0; m< selectionSize; m++){
                int listIndex = m + selectionSize;
                rVal[m] = charList.get(listIndex).name;
            }
        }
    
    //Output
        return rVal;
    }
    
    /**
     * When the User Makes a Selection, the method Displays a Message if the Character has been selected
     * If DPS Character Selection Process is Over, it displays all the Support Characters for the Selection Process
     * 
     */
    private void makeSelection(){
    //Initialization
            boolean continueSelection = false;
            boolean isTeamEmpty = team.isEmpty();
            
            
    //Processing
            //If the Team is Not Empty then it proceeds as normal
            if (!isTeamEmpty){
                continueSelection = isUniqueCharacter(currentlySelectedCharacter);
                isTeamComplete();
            } else {
                continueSelection = true;
            }
            //Selecting Character Successful
            if (continueSelection){
                team.add(currentlySelectedCharacter);   
                JOptionPane.showMessageDialog(frame, currentlySelectedCharacter.name + " has been selected! "
                    , "Selected Character Notice",  JOptionPane.INFORMATION_MESSAGE);
                isDPSSelectionFinished();
                isTeamComplete();
            //Selecting Character Unsuccessful, Duplicate Character!
            } else if (!continueSelection){
                JOptionPane.showMessageDialog(frame, currentlySelectedCharacter.name + " has already been selected, "
                    + "Please Choose a Different Character from the Drop-Down Menu! ", "Duplicate Character Notice",  
                    JOptionPane.INFORMATION_MESSAGE);
            }
    }
    
    /**
     * Determines if the DPS Selection Process is Finished or Not, 
     */
    private void isDPSSelectionFinished(){
    //Initialization
        boolean isFirstPhaseOfSelectionComplete = team.size() == 2;
    //Processing (Creates a Dialog Box Reminding User that it's going to second round of selections)
        if (isFirstPhaseOfSelectionComplete) {
            JOptionPane.showMessageDialog(frame, "Onto Support Character Selections, "
                    + "Please Choose Two Supports of your Choice!", "Changing Selections", 
                    JOptionPane.INFORMATION_MESSAGE);
            currentlySelectedCharacter = charList.get(6);
            updatePanels(CHANGE_CHARACTER);
        }

    }
    
    /**
     * Finds the Character that the Player Selected
     * 
     * @param currentlySelectedCharacterName the name of the character the player chose
     * @return the player's selected character
     */
    private Character getCharacter(String currentlySelectedCharacterName) {
    //Initialization
        int i = 0;
        boolean isMatchingCharacter = false;
        Character rVal = currentlySelectedCharacter;
        
    //Processing
        while (!isMatchingCharacter) {
            isMatchingCharacter = charList.get(i).name.equals(currentlySelectedCharacterName);
            //After Finding the Matching Character to the Player's Selection, it returns the Character
            if (isMatchingCharacter) {
                 rVal = charList.get(i);
            }
            i++;
        }
    //Output
        return rVal;
    }
    
    /**
     * Checks if the Selected Character is Selected Already
     * 
     * @return a boolean value of rather or not the Selection for DPS Characters is Completed
     */
    private boolean isUniqueCharacter(Character character){
    //Initialization
        int index = 0;
        boolean isTeamEmpty = team.isEmpty();
        boolean isMatchingCharacter = false;
        boolean rVal = true;
        String currentlySelectedCharacterName = character.name;
        
    //Processing
        while(index < team.size() & !isTeamEmpty && !isMatchingCharacter){
            isMatchingCharacter = team.get(index).name.equals(currentlySelectedCharacterName);
            //If the Selected Character is matching with one already on a list, it returns a false boolean value
            if (isMatchingCharacter){
                rVal = false;
            }
            index++;
        }

    //Output
        return rVal;
    }
    
    /**
     * Checks if the Team Selection Process is Completed and the Player Selected Four Characters
     */
    private void isTeamComplete (){
    //Initialization
        boolean isTeamSelectionComplete = team.size() == maxTeamSize;
        
    //If the Team Selection Process is Finished the GUI switches to the First Fight!
        if (isTeamSelectionComplete){
            frame.setVisible(false);
            JOptionPane.showMessageDialog(frame, "Onto Battle, Your First Enemy is Dvalin!",
                    "Battle Commences!",  JOptionPane.INFORMATION_MESSAGE);
            GUISwitcher switchGUI = new GUISwitcher(START_BATTLE, KHAENRIAH, team);
            frame.dispose();
        }
    }
}
