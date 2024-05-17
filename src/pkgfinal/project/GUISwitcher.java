package pkgfinal.project;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

import static pkgfinal.project.FinalProject.ANEMO;
import static pkgfinal.project.FinalProject.ELECTRO;
import static pkgfinal.project.FinalProject.HYDRO;
import static pkgfinal.project.FinalProject.BLANK;
import static pkgfinal.project.FinalProject.PYRO;
import static pkgfinal.project.FinalProject.CRYO;

import static pkgfinal.project.FinalProject.AZHDAHA_HP_VAL;
import static pkgfinal.project.FinalProject.CHAR_HP_VAL;
import static pkgfinal.project.FinalProject.TEAM_DEFEATED;
import static pkgfinal.project.FinalProject.BOSS_DEFEATED;
import static pkgfinal.project.FinalProject.GAME_START;
import static pkgfinal.project.FinalProject.START_TEAM_SELECTION;
import static pkgfinal.project.FinalProject.START_BATTLE;


import static pkgfinal.project.FinalProject.DEFAULT_ATK_VAL;
import static pkgfinal.project.FinalProject.DVALIN_HP_VAL;

import static pkgfinal.project.FinalProject.INAZUMA;
import static pkgfinal.project.FinalProject.LIYUE;
import static pkgfinal.project.FinalProject.MONDSTADT;
import static pkgfinal.project.FinalProject.KHAENRIAH;

import static pkgfinal.project.FinalProject.SCARAMOUCHE_HP_VAL;
import static pkgfinal.project.FinalProject.SIGNORA_HP_VAL;
import static pkgfinal.project.FinalProject.SUMERU;

/**
 * Eugene Thompson
 * CS173-B
 * May 10th, 2023
 * 
 * This Class Determines which GUI to Open Next for the Game
 * 
 * @author peichibunoreo
 */
public class GUISwitcher {

    public static final String ALL_BOSS_DEFEATED = "All Bosses Defeated!";

    /**
     * Switching Between GUIs in the Game Based on the Reasoning for Exiting
     * 
     * @param switchingReason a string containing the Reason for Exiting the GUI
     * @param region the region the previous GUI originated from
     * OR the bossRegion param when starting the game, it serves as starting the game with GAME_START instead
     * @param team an ArrayList with the current Team
     */
    public GUISwitcher(String switchingReason, String region, ArrayList<Character> team) {
    //Initialization
        //Initializing Booleans
        boolean isTeamDefeated = switchingReason.equals(TEAM_DEFEATED);
        boolean isBossDefeated = switchingReason.equals(BOSS_DEFEATED);
        boolean isStartOfGame = switchingReason.equals(GAME_START);
        boolean isStartingTeamSelectionProcess = switchingReason.equals(START_TEAM_SELECTION);
        boolean isStartingBattle = switchingReason.equals(START_BATTLE);
        
        //Determine Next Boss To Fight!
        Boss nextBoss = selectNextBoss(region);
        
        //Initializing Boss Regions and Status
        boolean areNoBossesLeft = nextBoss == null && region.equals(SUMERU);
        
        //If Start of Game Starts with the Game Start GUI
        if (isStartOfGame){
            gameStartGUI startGame = new gameStartGUI(team);
            startGame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            startGame.frame.setVisible(true);
            
        //If Start of Game is Continued, then onto Team Selection Process
        } else if (isStartingTeamSelectionProcess){
            teamSelectionGUI startTeamSelectionProcess = new teamSelectionGUI(team);
            startTeamSelectionProcess.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            startTeamSelectionProcess.frame.setVisible(true);
            
        //If Team Selection Process Finished, onto First Fight
        } else if (isStartingBattle){
            Boss firstBoss = selectNextBoss(region);
            combatGUI firstCombat = new combatGUI(team, firstBoss);
            firstCombat.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            firstCombat.frame.setVisible(true);
            
        //If Team is Defeated for any Reason goes to Ending Screen
        } else if (isTeamDefeated){
            endingScreenGUI endingScreen = new endingScreenGUI(TEAM_DEFEATED, region);
            endingScreen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            endingScreen.frame.setVisible(true);
            
        //If Team Defeats all Bosses then Completed Game Ending Screen
        } else if (areNoBossesLeft){
            endingScreenGUI endingScreen = new endingScreenGUI(ALL_BOSS_DEFEATED, region);
            endingScreen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            endingScreen.frame.setVisible(true);
            
        //If the Team Defeats a Boss their Characters are Fully Revived (Health set to Max) and they Move On!
        } else if (isBossDefeated){
            ArrayList<Character> revivedTeam = reviveTeam(team);
            combatGUI combat = new combatGUI(revivedTeam, nextBoss);
            combat.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            combat.frame.setVisible(true);
        } 
        
    }
    /**
     * Between Fighting Bosses, this Method Revives any Fallen Characters
     * @param team
     * @return an ArrayList of Characters that are fully revived
     */
    private ArrayList<Character> reviveTeam(ArrayList<Character> team){
    //Initialization
        ArrayList<Character> rVal = new ArrayList<>();
        
    //Processing
        for (int i = 0; i< team.size(); i++){
            rVal.add(team.get(i));
            rVal.get(i).currentHealth = CHAR_HP_VAL;
        }
    //Output
        return rVal;
    }
    
    /**
     * Randomly Chooses Three Unique Elements for the Boss "Azhdaha" as Part of His Boss Mechanic
     * 
     * @return a String array with Azdaha's Elements
     */
    public static String[] chooseAzhdahaElement() {
    //Initialization
        final int azhdahasElements = 3;
        boolean areUniqueElements = false;
        String[] list = {CRYO, PYRO, ELECTRO, HYDRO};
        Random ran = new Random();
        String[] elements = new String[azhdahasElements];
        
    //Processing
        //While Loop Determing if the Random Elements are Unique
        while (!areUniqueElements) {
            elements[0] = list[ran.nextInt(list.length)];
            elements[1] = list[ran.nextInt(list.length)];
            elements[2] = list[ran.nextInt(list.length)];

            areUniqueElements = !(elements[0].equals(elements[1]) || elements[1].equals(elements[2])
                    || elements[0].equals(elements[2]));
        }
        
    //Output
        return elements;
    }
    
     /**
     * Contains all the Bosses in the Game and Obtains the Next Boss
     *
     * @param region region that the previous boss originates from
     * @return the boss class for the region's specific boss
     */
    public static Boss selectNextBoss(String region) {
    //Initialization
        Boss selection = new Boss(BLANK, 0, 0, 0, BLANK, BLANK, 
                BLANK, BLANK, BLANK);

        //Azhdaha's Unique Mechanic: Initializing Each Random Element Attributed to Azhdaha
        String[] azhdahaElements = chooseAzhdahaElement();
        String azhdahaFirstElement = azhdahaElements[0];
        String azhdahaSecondElement = azhdahaElements[1];
        String azhdahaThirdElement = azhdahaElements[2];

         //Initialization of Bosses
        Boss dvalin = new Boss("Dvalin (Stormterror)", DVALIN_HP_VAL, DVALIN_HP_VAL, DEFAULT_ATK_VAL, 
                ANEMO, BLANK, BLANK, BLANK, MONDSTADT);
        
        Boss azhdaha = new Boss("Azhdaha", AZHDAHA_HP_VAL, AZHDAHA_HP_VAL, DEFAULT_ATK_VAL, 
                azhdahaFirstElement, azhdahaSecondElement, azhdahaThirdElement, BLANK, LIYUE);
        
        Boss signora = new Boss("La Signora", SIGNORA_HP_VAL, SIGNORA_HP_VAL, DEFAULT_ATK_VAL, 
                CRYO, PYRO, BLANK, BLANK, INAZUMA);
        
        Boss scaramouche = new Boss("Shouki no Kami (Scaramouche)", SCARAMOUCHE_HP_VAL, 
                SCARAMOUCHE_HP_VAL, DEFAULT_ATK_VAL, ELECTRO, BLANK, BLANK, HYDRO, SUMERU);

    //Processing
        //Booleans Determining the Next Boss in the Cycle
        boolean isNextBossDvalin = region.equals(KHAENRIAH);
        boolean isNextBossAzdaha = region.equals(dvalin.region);
        boolean isNextBossSignora = region.equals(azhdaha.region);
        boolean isNextBossScaramouche = region.equals(signora.region);
        boolean isNoMoreBosses = region.equals(scaramouche.region);
        
        //Obtains the Next Boss for the Corresponding Last Boss' Region
        if (isNextBossDvalin){
            selection = dvalin;
        } else if (isNextBossAzdaha) {
            selection = azhdaha;
        } else if (isNextBossSignora) {
            selection = signora;
        } else if (isNextBossScaramouche) {
            selection = scaramouche;
        } else if (isNoMoreBosses) {
            selection = null;
        }
        return selection;
    }

}
