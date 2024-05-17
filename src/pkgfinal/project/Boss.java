package pkgfinal.project;

/**
 * Eugene Thompson
 * CS 173-B
 * May 10th, 2023
 * 
 * Class: Boss (All Bosses in the Game)
 * 
 * @author peichibunoreo
 */
public class Boss extends Entity{
    String secondaryElement;
    String tertiaryElement;
    String elementalImmunity;
        /**
         * Boss Class
         * 
         * @param name Full Name of Boss
         * @param hp Health Points of Boss
         * @param maxHP Maximum Health Points of Boss
         * @param atk Attack Value of the Boss
         * @param firstElement Primary Element the Boss Uses in Battle
         * @param secondElement Secondary Element the Boss Uses in Battle
         * @param thirdElement Tertiary Element the Boss Uses in Battle
         * @param elementalRes Boss' Elemental Immunity
         * @param region Boss' Origin in the Genshin Impact Universe in Teyvat
         */
        public Boss(String name, int hp, int maxHP, int atk, String firstElement, String secondElement, String thirdElement, String elementalRes, String region) {
            this.name = name;
            this.currentHealth = hp;
            this.maxHealth = maxHP;
            this.attack = atk;
            this.currentElement = firstElement;
            this.secondaryElement = secondElement;
            this.tertiaryElement = thirdElement;
            this.elementalImmunity = elementalRes;
            this.region = region;


        }
}
