package pkgfinal.project;

/**
 * Eugene Thompson
 * CS 173-B
 * May 10th, 2023
 * 
 * Class: Character (Playable Characters in the Game)
 * 
 * @author peichibunoreo
 */
public class Character extends Entity{
    int skillCD;
    int ultCD;
    
        /**
         * Character Class
         *
         * @param name Full Name of the Character
         * @param hp Health Points of the Character
         * @param maxHP Maximum Health Points of the Character
         * @param skillCooldown the Total Skill Cooldown of the Character
         * @param ultimateCooldown the Total Ultimate Cooldown of the Character
         * @param atk Attack Value of the Character
         * @param element Elemental Type of the Character
         * @param combatStyle Combat Style or Role in the Team as a Character
         * @param region Region the Character Originates from
         */
        public Character(String name, int hp, int maxHP, int atk, int skillCooldown, int ultimateCooldown, 
                String element, String combatStyle, String region) {
            this.name = name;
            this.currentHealth = hp;
            this.maxHealth = maxHP;
            this.attack = atk;
            this.skillCD = skillCooldown;
            this.ultCD = ultimateCooldown;
            this.currentElement = element;
            this.role = combatStyle;
            this.region = region;
        }
}
