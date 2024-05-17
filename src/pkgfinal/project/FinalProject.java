package pkgfinal.project;

import java.util.*;

/**
 * Eugene Thompson
 * CS173-B
 * May 10th, 2023
 * 
 * Final Project: Trial of Archons, a Genshin Impact Inspired Turn-Based Role Playing Game (RPG)
 * 
 * @author peichibunoreo
 */
public class FinalProject {

//Initializing Elements for Characters and Bosses
    public static final String CRYO =  "Cryo";
    public static final String PYRO = "Pyro";
    public static final String HYDRO = "Hydro";
    public static final String ELECTRO = "Electro";
    public static final String ANEMO = "Anemo";
    public static final String DENDRO = "Dendro"; //Maybe Add Dendro Color?
    public static final String BLANK = "";
    
//Initialize Game Name and Paimon Icon FilePath
    public static final String GAME_NAME = "Trials of the Archons";
    
//Initializing Game Starting Reason
    public static final String GAME_START = "Game is Starting!";
    public static final String START_TEAM_SELECTION = "Starting Team Selection";
    public static final String START_BATTLE = "Start the Battle!";
    
//Initialization of Character and Boss Place of Origin or Region
    public static final String MONDSTADT = "Mondstadt";
    public static final String LIYUE = "Liyue";
    public static final String INAZUMA = "Inazuma";
    public static final String SUMERU = "Sumeru";
    //Khaenriah serves as the starting region to Start the Game Off
    public static final String KHAENRIAH = "Khaenri'ah";
    
//Initialization of Character roles (DPS for Damaging Enemies Exclusively, SUP for Supports Healing Allies)
    public static final String DPS = "Damage";
    public static final String SUP = "Support";

//Initialization for Outlining which party is attacking who, and to update characters and bosses
    public static final String REFRESH_CHARACTER =  "Refreshing Character";
    public static final String REFRESH_BOSS = "Refreshing Boss";
    public static final String CHANGE_CHARACTER = "Update To New Character";
    public static final String TEAM_ATTACKING = "Team Attacking";
    public static final String BOSS_ATTACKING = "Boss Attacking";

//Initializatoin for Outlining what Skill the Character or Boss is Using
    public static final String NORMAL_ATK_USED = "Normal Attack";
    public static final String SKILL_USED = "Skill";
    public static final String ULTIMATE_USED = "Ultimate";
    
//Initialization for Quit Battle Conditions:
    public static final String TEAM_DEFEATED = "Team Defeated";
    public static final String BOSS_DEFEATED = "Boss Defeated";
    
//Initialization of Boss and Character Health Points Values and Base Attack Values
    public static final int CHAR_HP_VAL = 10;
    public static final int DVALIN_HP_VAL = 20;
    public static final int AZHDAHA_HP_VAL = 40; //Always has to be a Multiple of Four! 
    public static final int SIGNORA_HP_VAL = 80;
    public static final int SCARAMOUCHE_HP_VAL = 100;
    public static final int CHAR_ATK_VAL = 1;
    public static final int SKILL_DMG_VAL = 2;
    public static final int ULTIMATE_DMG_VAL = 4;
    public static final int DEFAULT_ATK_VAL = 1;
    
//Initializing the Number of Turns the Character can Use Abilities in Battle
    public static final int SKILL_COOLDOWN_VAL = 2;
    public static final int ULTIMATE_COOLDOWN_VAL = 3;
    
//Initializing the Types of Turns that can occur in battle
    public static final boolean NORMAL_TURN = true;
    public static final boolean TEAM_FROZEN = true;


    /**
     * Contains all the Characters in the Game, Provides an array based of All
     * Characters
     *
     * @return an all damage, support, or all character array based on the
     * parameters
     */
    public static ArrayList<Character> createCharacterList() {
    //Initializing Characters (Initialization)
        //Damage Types
        Character ayaka = new Character("Kamisato Ayaka", CHAR_HP_VAL, CHAR_HP_VAL,  CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, CRYO, DPS, INAZUMA);
        Character cyno = new Character("Cyno", CHAR_HP_VAL, CHAR_HP_VAL, CHAR_ATK_VAL,
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, ELECTRO, DPS, SUMERU);
        Character ayato = new Character("Kamisato Ayato", CHAR_HP_VAL, CHAR_HP_VAL,  CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, HYDRO, DPS, INAZUMA);
        Character klee = new Character("Klee", CHAR_HP_VAL, CHAR_HP_VAL, CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, PYRO, DPS, MONDSTADT);
        Character alhaitham = new Character("Alhaitham", CHAR_HP_VAL, CHAR_HP_VAL, CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, DENDRO, DPS, SUMERU);
        Character heizou = new Character("Heizou", CHAR_HP_VAL, CHAR_HP_VAL, CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, ANEMO, DPS, INAZUMA);
        //Support Types
        Character qiqi = new Character("Qiqi", CHAR_HP_VAL,CHAR_HP_VAL, CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, CRYO, SUP, LIYUE);
        Character dori = new Character("Dori", CHAR_HP_VAL, CHAR_HP_VAL,  CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, ELECTRO, SUP, SUMERU);
        Character barbara = new Character("Barbara", CHAR_HP_VAL, CHAR_HP_VAL,  CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, HYDRO, SUP, MONDSTADT);
        Character bennett = new Character("Bennett", CHAR_HP_VAL,CHAR_HP_VAL,  CHAR_ATK_VAL,
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, PYRO, SUP, MONDSTADT);
        Character yaoyao = new Character("Yaoyao", CHAR_HP_VAL,CHAR_HP_VAL,  CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, DENDRO, SUP, LIYUE);
        Character jean = new Character("Jean", CHAR_HP_VAL,CHAR_HP_VAL,  CHAR_ATK_VAL, 
                SKILL_COOLDOWN_VAL, ULTIMATE_COOLDOWN_VAL, ANEMO, SUP, MONDSTADT);

        //Initialize Character List 
        ArrayList<Character> characterList = new ArrayList<>();
        
        //Adding Each Character to the ArrayList
        characterList.add(ayaka);
        characterList.add(cyno);
        characterList.add(ayato);
        characterList.add(klee);
        characterList.add(alhaitham);
        characterList.add(heizou);
        characterList.add(qiqi);
        characterList.add(dori);
        characterList.add(barbara);
        characterList.add(bennett);
        characterList.add(yaoyao);
        characterList.add(jean);


        return characterList;
    }
    
    /**
     * Gets the Character Description for Each Character in the Game
     * Contains Each Character with a Title, Weapon Type, Brief Biography, and Ability Descriptions
     * 
     * @param character the character object of the specific character from the character list
     * @return a String with the character's description
     */
    public static String getCharacterDescription(Character character){
    //Initialization
        //Initializing Strings for Descriptions
        String rVal = "";
        String descriptionTitle = "<html> <b>Character Description: </b> <br><br>";
        //Booleans Determining Which Character the selected character is Referring to
        boolean isAyaka = character.name.equals("Kamisato Ayaka");
        boolean isAyato = character.name.equals("Kamisato Ayato");
        boolean isCyno = character.name.equals("Cyno");
        boolean isKlee = character.name.equals("Klee");
        boolean isHeizou = character.name.equals("Heizou");
        boolean isAlhaitham = character.name.equals("Alhaitham");
        boolean isQiqi = character.name.equals("Qiqi");
        boolean isDori = character.name.equals("Dori");
        boolean isBarbara = character.name.equals("Barbara");
        boolean isBennett = character.name.equals("Bennett");
        boolean isYaoyao = character.name.equals("Yaoyao");
        boolean isJean = character.name.equals("Jean");
        
        if (isYaoyao){
           rVal = descriptionTitle +character.name + " summons Yuegui (Bunny-Like Creature)"
                + " for her abilities. <br> Yaoyao is the youngest, most mature disciple of Liyue's"
                   + " Adepti. <br> Liyue's Adepti is a group of Immortal Gods of the Elements from "
                   + "the Region of Contracts. <br><br> In combat, Yaoyao uses a polearm to deal " 
                   +character.currentElement +" damage with normal attacks. <br> "
                   + "Her other abilities let her heals herself, and her Team with Radishes produced by Yuegui! </html>";
        } else if (isAyaka){
            rVal = descriptionTitle + character.name + " wields a sword using her inherited Kamisato Art Technique."
                + "<br><br> She is the sister of Kamisato Ayato, and she is Inazuma's (Region of Eternity)"
                    + " highest praised noblewoman. <br><br> In combat, she deals " + character.currentElement + 
                    " damage to enemies, by summoning ice with her abilities.</html>";
        } else if (isAyato){
            rVal = descriptionTitle + character.name + " wields a sword using his inherited Kamisato Art Technique."
                + "<br><br> He is the head of the Kamisato Clan, and has a lower public profile than his sister, Ayaka."
                + "<br><br> In combat, he deals " + character.currentElement + " damage to enemies, "
                    + "by summoning dewdrops with his abilities.</html>";
        } else if (isBennett){
            rVal = descriptionTitle + character.name + " wields a sword, and is an orphan child."
                + "<br><br> He has a reputation for being unlucky, and remains the only member "
                    + "of his Adventure Team. <br><br> In combat, he deals " + character.currentElement + 
                    " damage with his normal attacks. <br> His other abilities heal himself or allies "
                    + "with an Inspiring Field.</html>";
        } else if (isCyno){
            rVal = descriptionTitle + character.name + " wields a polearm, and is akin to the Egyptian God Anubis."
                + "<br><br> He is known for his terribly unfunny jokes, and his obsession with card games."
                    + "<br><br> In combat, he deals " + character.currentElement + " damage with his normal attacks. "
                    + "<br> His Skill pierces enemies with his polearm dashing behind them.<br> "
                    + "His Ultimate deals massive damage as he dons his Jackal Mask.</html>";
        } else if (isKlee){
            rVal = descriptionTitle + character.name + " is a catalyst user, and throws bombs at enemies."
                + "<br><br> She regularly gets in trouble with Mondstadt's (Region of Freedom) authorities. <br> "
                    + "She has also radically changed landscapes in Teyvat with her bombs."
                    + "<br><br> In combat, she deals " + character.currentElement + " damage with her normal attacks. "
                    + "<br> Her Skill throws one of her bombs named Jumpty-Dumpty at nearby enemies.<br> "
                    + "His Ultimate deals massive damage as she ignites her bombs near enemies.</html>";
        } else if (isJean){
            rVal = descriptionTitle + character.name + " is a sword user, and harnessing the power of the wind."
                + "<br><br> She is the head of Mondstadt's governing body as Acting Grand Master. <br> "
                    + "She regularly has to discipline Klee whenever she gets in trouble, and is a workaholic."
                    + "<br><br> In combat, she deals " + character.currentElement + " damage with her normal attacks. "
                    + "<br> Her Skill heals herself by creating a vortex of wind<br> "
                    + "His Ultimate creates a Dandelion Field that heals all allies in the area.</html>";
        } else if (isAlhaitham){
            rVal = descriptionTitle + character.name + " is a sword user, and harnessing mirrors in his attacks."
                + "<br><br> He is the head of Sumeru's (Region of Wisdom) academic governing body (Akademiya) as"
                    + " Grand Sage. <br> He is known for his highly egotistical and logical demeanor, "
                    + "making him difficult to approach. <br><br> In combat, he deals " + character.currentElement + 
                    " damage with his normal attacks. <br> His Skill summons a mirror to deal increased damage"
                    + " to an opponent.<br> His Ultimate summons a multitude of mirrors "
                    + "to shoot light beams at enemies.</html>";
        } else if (isHeizou){
            rVal = descriptionTitle + character.name + " is a catalyst user, and punching enemies with " + 
                    character.currentElement + "<br><br> "
                    + "He is the head detective in Inazuma (Region of Eternity), one of the best in Inazuma. "
                    + "<br> He is known for his rebellious nature against authority, but he has an amazing personality "
                    + ". <br><br> In combat, he deals " + character.currentElement + " damage with his normal attacks. "
                    + "<br> Her Skill charges up to deal a massive amount of Anemo Damage at an enemy.<br> "
                    + "His Ultimate summons a wind vortex dealing a massive damage to enemies in the area.</html>";
        } else if (isDori){ 
            rVal = descriptionTitle + character.name + " is a claymore user, using her Jinni lamp to attack enemies" + 
                    character.currentElement + "<br><br> "
                    + "She is one of the richest merchants in Sumeru (Region of Wisdom). <br>"
                    + "She has a reputation for charging exorbitant prices for her goods. <br> "
                    + "She owns one Sumeru's few palaces and has an irrecovable love for Mora (Teyvat's Currency)."
                    + " <br><br> In combat, she deals " + character.currentElement + " damage with his normal attacks. "
                    + "<br> Her Skill fires her Jinni lamp to heal herself.<br> "
                    + "His Ultimate summons her Jinni lamp and tethers it to herself to heal all allies.</html>";
        } else if (isBarbara){ 
            rVal = descriptionTitle + character.name + " is a catalyst user, utilizing music and water to"
                    + " attack enemies" + character.currentElement + "<br><br> "
                    + "She is a deaconess at Mondstadt's (Region of Freedom) Church"
                    + " and is adored by her fans as a pop star idol. <br> "
                    + "She is the younger sister of Jean, the Acting Grand Master of the Knights of Favonius. <br> "
                    + "The Knights of Favonius is Mondstadt's primary Governing Body. "
                    + "<br><br> In combat, she deals " + character.currentElement + 
                    " damage with his normal attacks. <br> Her Skill summons water resembling musical "
                    + "notes to heal herself.<br> Her Ultimate heals all allies after singing a song.</html>";
        } else if (isQiqi) {
            rVal = descriptionTitle + character.name + " is a sword user, and is a zombie resurrected by the Adepti "
                    + "(Immortal Gods of the Elements)" + character.currentElement + "<br><br> "
                    + "She works at Bubu Pharmacy in Liyue's capital city, Liyue Harbor. <br> "
                    + "She is known for her quiet, soft demeanor and talking about an infamous 'cocogoat'."
                    + ". <br><br> In combat, she deals " + character.currentElement + 
                    " damage with his normal attacks. <br> Her Skill summons a spirit to heal herself."
                    + "<br> Her Ultimate releases her talisman healing all allies.</html>";
        }
        
        return rVal;
    }

    
    public static void main(String[] args){
    //Initialization
        //Array List with All Character in Game
        ArrayList<Character> charList = createCharacterList();

    //Input/Processing/Output
        //Starts the Game by Calling the GUISwitcher class to start
        GUISwitcher startGame = new GUISwitcher(GAME_START, KHAENRIAH, charList);
       

    }
}

