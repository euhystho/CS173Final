package pkgfinal.project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import static pkgfinal.project.FinalProject.GAME_NAME;
import static pkgfinal.project.FinalProject.GAME_START;
import static pkgfinal.project.FinalProject.START_TEAM_SELECTION;

/**
 * Eugene Thompson
 * CS173-B
 * May 10th, 2023
 * 
 * This Class contains the Game Welcome GUI that Starts the Game with a Brief Description about the Game
 * 
 * @author peichibunoreo
 */
public class gameStartGUI extends JFrame implements ActionListener {

    private ArrayList<Character> team;
    JFrame frame;

    final JPanel paimonPanel;
    final JPanel welcomePanel;
    final JPanel selectionPanel;
    

    private JButton quitButton;
    private JButton continueButton;
    
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem userQuitItem;


    @Override
    public void actionPerformed(ActionEvent e) {
    //If the User uses User Quit or Wishes to Leave, it quits the game
        if (e.getSource() == userQuitItem) {
            System.exit(0);
        } else if (e.getSource() == quitButton){
            System.exit(0);
        //Otherwise it Continues the Game
        } else if (e.getSource() == continueButton){
            frame.setVisible(false);
            GUISwitcher switchGUI = new GUISwitcher(START_TEAM_SELECTION, GAME_START, team);
            frame.dispose();
        }
    }

    /**
     * Contains the Game Start GUI for the Game, with Paimon (The Game's Guide)
     * It has a Description Briefly Describing the Plot and Objective of the Game along with a Useful Hint
     * It Gives Players the Choice to Leave or Continue with the Game
     * 
     * @param characters an array list of all Characters
     */
    public gameStartGUI(ArrayList<Character> characters) {
        this.team = characters;
        
    //Initialization of BorderLayout
        BorderLayout layout = new BorderLayout();

    //Initialization of Frame
        frame = new JFrame(GAME_NAME + ": We Will Be Reunited");
        frame.setLayout(layout);
    
    //Creating Components for the Main Frame
        menuBar = createMenuBar();
        welcomePanel = createWelcomePanel();
        paimonPanel = createPaimonPanel();
        selectionPanel = createSelectionPanel();
        
    //Adding Components to the Main Frame
        frame.setJMenuBar(menuBar);  
        frame.add(selectionPanel, BorderLayout.SOUTH);
        frame.add(welcomePanel, BorderLayout.CENTER);
        frame.add(paimonPanel, BorderLayout.WEST);
        frame.pack();

    }
    
    private final String PAIMON = "Paimon";
    
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
        this.setJMenuBar(menuBar);
        
        return menuBar;
    }
    
    /**
     * Creates a Panel with Welcome Message and Brief Description of Game 
     * 
     * @return a JPanel with Welcome Message and Brief Description of Game 
     */
    private JPanel createWelcomePanel() {
    //Initialization
        Font titleFont =  new Font("Helvetica", Font.PLAIN, 24);
        //Initializing the Panels
        JPanel rValPanel = new JPanel();
        JPanel descriptionPanel = new JPanel();
        //Initializing Box Layout and Setting Layouts For Descriptions
        BoxLayout layout = new BoxLayout(descriptionPanel, BoxLayout.PAGE_AXIS);
        descriptionPanel.setLayout(layout);
        //Initializing Labels
        JLabel titleLabel = new JLabel();
        JLabel introLabel = new JLabel();       
        JLabel gameDescriptionLabel = new JLabel();
        JLabel scaramoucheImmunityLabel = new JLabel();
        JLabel endingMessageLabel = new JLabel();

    //Assigning Text to Title Label
        titleLabel.setText("Welcome to " + GAME_NAME +"!");
        titleLabel.setFont(titleFont);
        
        //Assigning Descriptive Text to the Other Text-Based Labels (Using HTML to Format Text Line by Line)
        
        introLabel.setText("<html><br> Welcome Traveler! <br><br> In this Game, you can play with "
                + "Four Characters to Wield Elemental Damage to Various Bosses across the World of Teyvat! "
                + "<br> Through these series of Challenges across the world of Teyvat, you will reunite "
                + "with your former sibling who has succumbed to the evil powers of the Abyss. <br> </html>");
        gameDescriptionLabel.setText("<html><br> After this Screen, you will select Two DPS (Damage Type) "
                + "Characters who always do damage to the various bosses in Teyvat! <br> Additionally, "
                + "you select Two Support Characters that not only Heal Themselves, but your entire team! <br> "
                + "By choosing the Advantageous Element for Defeating These Bosses, you'll easily be able "
                + "to reunite with your sibling!</html>");
        scaramoucheImmunityLabel.setText("<html><br> Please Note! The Final Boss, "
                + "Scaramouche has a unique Elemental Immunity to the Hydro Element. <br> When fighting him, "
                + "refrain from using Hydro-Based Characters! </html>");
        endingMessageLabel.setText("<html><br>Now after knowing this information, I hope you have fun Traveler!"
                + " ~ Paimon <br><br>Would you like to continue your journey through the World of Teyvat?</html>");

    //Adding Labels to the Panel
        descriptionPanel.add(titleLabel);
        descriptionPanel.add(introLabel);
        descriptionPanel.add(gameDescriptionLabel);
        descriptionPanel.add(scaramoucheImmunityLabel);
        descriptionPanel.add(endingMessageLabel);
        rValPanel.add(descriptionPanel);
        
    //Output
        return rValPanel;
    }
    
    /**
     * Creates an Action Panel for the Player to Continue with the Game or Not After Reading Description
     * 
     * @return  a JPanel with User-Activatable Buttons with a Quit Game or Continue Game Option
     */
    private JPanel createSelectionPanel(){
    //Initialization
        JPanel rValPanel = new JPanel();
        continueButton = new JButton("Yes, I would like to Proceed!");
        quitButton = new JButton("No, I would like to Leave...");
        
    //Adding the Action Listener to the Buttons
        continueButton.addActionListener(this);
        quitButton.addActionListener(this);
        
    //Add Buttons to Panel
        rValPanel.add(continueButton);
        rValPanel.add(quitButton);
        
        return rValPanel;
    }
    
     /**
     * Creates a Panel with Paimon (Game's Guide Entity) 
     * If the Image is not Found, replaces the Image Label with an Error Message
     * 
     * @param imagePanel a JPanel containing the corresponding entity's Image Panel
     * @return a JPanel with Paimon 
     */
    private JPanel createPaimonPanel() {
    //Initialization
        //Paimon's Image Path in Project Folder
        String paimonPicPath = "dietyImages" +File.separator + PAIMON + ".png";
        
        //Initializing the Border Layouts, Dimensions, and Panel for the Paimon's Panel
        BorderLayout borderLayout = new BorderLayout();
        JPanel rValPanel = new JPanel(borderLayout);
        
        //Initializing the Image Panel and Style of Layout
        JPanel imagePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS);
        imagePanel.setLayout(boxLayout);
        
        //Image Icon for Paimon
        ImageIcon paimonImage = new ImageIcon(paimonPicPath);
        JLabel paimonImageLabel = new JLabel(paimonImage);
        
        //Boolean Determining if the Paimon's Image is Found and Loaded
        boolean hasImageLoaded = paimonImage.getImageLoadStatus() == MediaTracker.COMPLETE;
        
    //Processing
        //If the Image is Not Found it Displays an Error Message and Paimon Saying Hello :)
        if (!hasImageLoaded) {
            paimonImageLabel = new JLabel("Cannot Find " + PAIMON + " Image File!");
            JLabel characterLabel = new JLabel("Paimon Says Hello!");
            JPanel missingImagePanel = new JPanel();
            missingImagePanel.add(paimonImageLabel);
            missingImagePanel.add(characterLabel);
            imagePanel.add(missingImagePanel);
            
        //Otherwise, loads the image into the Image Panel
        } else {
            paimonImageLabel = new JLabel(paimonImage);
            imagePanel.add(paimonImageLabel);
        }
        
    //Adding Image to Paimon Panel
        rValPanel.add(imagePanel);

    //Output
        return rValPanel;
    }
    
}
