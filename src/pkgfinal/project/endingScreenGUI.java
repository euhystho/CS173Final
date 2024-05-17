package pkgfinal.project;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

import static pkgfinal.project.FinalProject.GAME_NAME;

import static pkgfinal.project.FinalProject.MONDSTADT;
import static pkgfinal.project.FinalProject.LIYUE;
import static pkgfinal.project.FinalProject.INAZUMA; 
import static pkgfinal.project.FinalProject.SUMERU; 

import static pkgfinal.project.FinalProject.GAME_START;
import static pkgfinal.project.FinalProject.TEAM_DEFEATED;

import static pkgfinal.project.FinalProject.createCharacterList;

/**
 * Eugene Thompson 
 * CS173-B 
 * May 10th, 2023
 *
 * This Class Displays a Ending Screen GUI if the Player's Team or the Boss is
 * Defeated
 *
 * @author peichibunoreo
 */
public class endingScreenGUI implements ActionListener{

    private final String ALL_BOSS_DEFEATED = "All Bosses Defeated!";
    
    final String MONDSTADT_ARCHON = "Venti";
    final String LIYUE_ARCHON = "Zhongli";
    final String INAZUMA_ARCHON = "Ei";
    final String PAIMON = "Paimon";
    
    final String lastDefeatedBossRegion;
        
    JFrame frame;

    private JPanel paimonPanel;
    private JPanel dietyPanel;
    private JPanel messagePanel;
    private JPanel buttonPanel;

    private JButton quitGameButton;
    private JButton playAgainButton;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem userQuitItem;

    @Override
    public void actionPerformed(ActionEvent e) {
        //If the User uses User Quit or Wishes to Leave, it quits the game
        if (e.getSource() == userQuitItem) {
            System.exit(0);
        } else if (e.getSource() == quitGameButton) {
            System.exit(0);
        } else if (e.getSource() == playAgainButton){
            ArrayList<Character> charList = createCharacterList();
            GUISwitcher startGame = new GUISwitcher(GAME_START, lastDefeatedBossRegion, charList);
            frame.setVisible(false);
            frame.dispose();
        }
    }

    /**
     * Contains the Game Ending GUI for the Game, with One of the Five Endings
     * 
     * @param endingReason a string containing the reason for ending the game
     * @param bossRegion a string containing the last defeated boss' region
     */
    public endingScreenGUI(String endingReason, String bossRegion) {
    //Initializing Boolean for End of Game
        boolean isEndOfGame = endingReason.equals(ALL_BOSS_DEFEATED);
     //Initialization/Processing of Boss' Region Value based on End of Game Status
        if (!isEndOfGame){
            lastDefeatedBossRegion = bossRegion; 
        } else {
            lastDefeatedBossRegion = MONDSTADT;
        }
        
        //Initialization of BorderLayout
        BorderLayout layout = new BorderLayout();

        //Initialization of Frame
        frame = new JFrame(GAME_NAME + ": We Will Be Reunited");
        frame.setLayout(layout);

        //Initializing Boolean for Player Defeated by First Boss
        boolean isTeamDefeatedByDvalin = endingReason.equals(TEAM_DEFEATED) && 
                bossRegion.equals(MONDSTADT);

    //Processing
        //Determining the Diety's Name for the Message and Image
        String dietyName = determineDiety(bossRegion, isEndOfGame);
        
        //Creating Components for the Main Frame
        dietyPanel = createDietyPanel(dietyName);
        messagePanel = createEndingMessagePanel(dietyName,isEndOfGame );
        menuBar = createMenuBar();
        buttonPanel = createButtonsPanel();
        

        //Adding Components to Main Frame
        frame.setJMenuBar(menuBar);
        frame.add(messagePanel, BorderLayout.EAST);
        frame.add(dietyPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
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
     * Creates an Image Panel of the Archon in the form of Statue of the Seven that bestows 
     * a Blessing to the Player
     * 
     * @param archonRegion the region that the Blessing Archon comes from
     * @return a JPanel with the Images of the Archon
     */
    private JPanel createDietyPanel(String dietyName){
    //Initialization
        //Diety's Image Path in Project Folder
        String dietyPicPath = "dietyImages" +File.separator + dietyName + ".png";
        
        //Initializing the Border Layouts, Dimensions, and Panel for the Archon's Panel
        BorderLayout borderLayout = new BorderLayout();
        JPanel rValPanel = new JPanel(borderLayout);
        
        //Initializing the Image Panel and Style of Layout
        JPanel imagePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS);
        imagePanel.setLayout(boxLayout);
        
        //Image Icon for the Diety
        ImageIcon dietyImage = new ImageIcon(dietyPicPath);
        JLabel dietyImageLabel = new JLabel(dietyImage);
        
        //Boolean Determining if the Diety's Image is Found and Loaded
        boolean hasImageLoaded = dietyImage.getImageLoadStatus() == MediaTracker.COMPLETE;
        
    //Processing
        //If the Image is Not Found it Displays an Error Message
        if (!hasImageLoaded) {
            dietyImageLabel = new JLabel("Cannot Find " + dietyName + " Image File!");
            JPanel missingImagePanel = new JPanel();
            missingImagePanel.add(dietyImageLabel);
            imagePanel.add(missingImagePanel);
            
        //Otherwise, loads the image into the Image Panel
        } else {
            dietyImageLabel = new JLabel(dietyImage);
            imagePanel.add(dietyImageLabel);
        }
        
    //Adding Image to Diety Panel
        rValPanel.add(imagePanel);

    //Output
        return rValPanel;
    }
    
    /**
     * Creates an Ending Message Panel for the Respective Archon of the Last Defeated Region
     * 
     * @param messageType a String with the messageType usually boss region or boss or character status
     * @return a JPanel with the Ending Message
     */
    private JPanel createEndingMessagePanel(String messageType, boolean isEndOfGame){
    //Initialization
        //Initialize JPanel
        JPanel rValPanel = new JPanel();
        //Brief Description of Character
        JLabel messageLabel = new JLabel();
        
    //Output
        //Calls Method to get the Appropriate Ending Message
        String characterDescription = getEndingMessageDescription(messageType, isEndOfGame);
        messageLabel.setText(characterDescription);
        messageLabel.setVerticalAlignment(JLabel.NORTH);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        //Adding Label to Panel
        rValPanel.add(messageLabel);
        
        return rValPanel;
    }
    
    /**
     * Creates a Button Panel that Contains an Option to Play Again or Quit the Game
     * 
     * @return a JPanel with Buttons for the Player to Interact with
     */
    private JPanel createButtonsPanel(){
    //Initialization
        JPanel rValPanel = new JPanel();
        playAgainButton = new JButton();
        quitGameButton = new JButton();
        
    //Processing
        playAgainButton.setText("Yes, I would like to try again!");
        quitGameButton.setText("No, I would like to leave...");

    //Adding the Action Listener to the Buttons
        playAgainButton.addActionListener(this);
        quitGameButton.addActionListener(this);
        
    //Add Buttons to Panel
        rValPanel.add(playAgainButton);
        rValPanel.add(quitGameButton);

        
        return rValPanel;
    }

    /**
     * Determines which Diety Gives the Players Tips and Options
     * 
     * @param bossRegion the region the boss that defeated the team is from
     * @return a string containing the Diety that Gives the Players Tips and Options
     */
    private String determineDiety(String bossRegion, boolean isEndOfGame){
    //Initialization
        //Booleans Detereming what the Boss the Team was Defeated By
        boolean isPaimon = (bossRegion.equals(SUMERU) && isEndOfGame) || bossRegion.equals(MONDSTADT);
        boolean isTeamDefeatedByAzhdaha = bossRegion.equals(LIYUE);
        boolean isTeamDefeatedBySignora = bossRegion.equals(INAZUMA);
        boolean isTeamDefeatedByScaramouche = bossRegion.equals(SUMERU);
        
        //String Containing the Diety that Gives Favor and a Boss Tip to the Player
        String rVal = "";

    //Processing
        if (isPaimon){
            rVal = PAIMON;
        } else if (isTeamDefeatedByAzhdaha){
            rVal = MONDSTADT_ARCHON;
        } else if (isTeamDefeatedBySignora){
            rVal = LIYUE_ARCHON;
        } else if (isTeamDefeatedByScaramouche){
            rVal = INAZUMA_ARCHON;
        } 
        
    //Output
        return rVal;
    }
    
    /**
     * Gets an Ending Message Description Depending on the Type of Message
     * 
     * @param messageType a String Containing the boss region or entity defeated status
     * @return a String containing the appropriate message based on the type of message
     */
    private String getEndingMessageDescription(String dietyName, boolean isEndOfGame){
    //Initialization
        //Initializing Strings
        String rVal = "";
        
        //Initializing Type of Message
        boolean isTeamDefeatedByDvalin = dietyName.equals(PAIMON);
        boolean isTeamDefeatedByAzhdaha = dietyName.equals(MONDSTADT_ARCHON);
        boolean isTeamDefeatedBySignora = dietyName.equals(LIYUE_ARCHON);
        boolean isTeamDefeatedByScaramouche = dietyName.equals(INAZUMA_ARCHON);
        boolean isAllBossesSlain = isEndOfGame;
        
    //Processing
        //Messages Based on Who was Defeated and the Boss Slain Statuses
        if (isTeamDefeatedByDvalin && !isAllBossesSlain){
            rVal = "<html> Paimon feels bad that you got defeated by the first boss (Dvalin), "
                    + "but Paimon always believes in you! <br><br> Paimon recommends switching"
                    + " between characters to maintain overall team HP. <br> "
                    + "That way, you will minimize being defeated by Dvalin and other Bosses <br><br> "
                    + "Paimon understands you might be distraught, but would you like to try again?</html>";
        } else if (isTeamDefeatedByAzhdaha){
            rVal = "<html>Greetings Traveler, it's Venti, the Wind Archon of Mondstadt!<br><br> "
                    + "It looks like you were defeated by the great and powerful Azhdaha...<br> Fortunately, "
                    + "I may be a lowly bard, but I am here to give you some tips!"
                    + "<br><br> Azdaha does more damage by changing Elements mid-fight...<br> "
                    + "Keep an eye out for that Elements Indicator on the top of your screen!<br> "
                    + "As always, let the wind lead you!<br>"
                    + "<br> Would you like to restart your adventure Traveler?</html>";
        } else if (isTeamDefeatedBySignora){
            rVal = "<html>Hello Traveler, it's Zhongli, the Geo (Earth) Archon of Liyue.<br><br> "
                    + "It seems like you were defeated by the Harbinger La Signora...<br> I may be a lowly Consultant "
                    + "at the Wangsheng Funeral Parlor, but I have decades of experience..."
                    + "<br><br> La Signora primarily does more damage through changing Elements mid-fight...<br> "
                    + "Keep an eye out for that Elements Indicator on the top of your screen.<br> "
                    + "You are welcome to stay in Liyue Harbor anytime...!<br><br> "
                    + "Would you like to restart your adventure Traveler?</html>";
        } else if (isTeamDefeatedByScaramouche){
            rVal = "<html>Oh, it's you Traveler...<br><br>I am Raiden Ei, the Electro Archon of Inazuma! <br> "
                    + "The embodiment of Everlasting Eternity!<br><br> "
                    + "It seems like you were defeated by my puppet Scaramouche...<br> Fortunately, "
                    + "I am able to come to your aid..."
                    + "<br><br> Scaramouche has a high amount of Health Points for a Boss, "
                    + "so using abilities wisely is a necessity<br>"
                    + "Also, he is immune to the Hydro Element, so Refrain from Using Characters that Wield Hydro!.<br> "
                    + "Go, and fight for eternity!<br><br> "
                    + "Would you like to restart your pursuit of eternity in Teyvat Traveler?</html>";
        } else if (isAllBossesSlain){
            rVal = "<html> <b> Congratulations Traveler! You have Finished the Game!</b>"
                    + "<br>Paimon is excited that you get to reunite with your sibling! <br> "
                    + "Think of all the amazing food that you can eat together!<br><br> "
                    + "Paimon is getting hungry for Sticky Honey Roast!<br><br><br><br><br><br><br><br>"
                    + "Paimon knows you're having a reunion, but would you like to play again?</html>";
        }

    //Output
        return rVal;
    }
}
