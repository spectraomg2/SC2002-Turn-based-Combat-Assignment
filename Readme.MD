SC2002 Turn-Based Combat Arena
A command-line turn-based combat game built in Java, demonstrating Object-Oriented Design Principles (OODP) and SOLID principles.

Table of Contents

Game Overview
Characters
Actions
Items
Status Effects
Levels & Difficulty
How to Run
Project Structure
Architecture & Design
SOLID Principles
UML Diagrams
Team Members


Game Overview
The game is a round-based combat arena where a player fights waves of enemies. Each round, combatants act in a turn order determined by their Speed stat (higher speed goes first). The player wins by defeating all enemies; the player loses if their HP reaches zero.
Game Flow

Select a player class (Warrior or Wizard)
Select two items (duplicates allowed)
Select a difficulty level (Easy, Medium, Hard)
Battle proceeds in rounds:

Backup enemies spawn (if applicable) when the current wave is fully defeated
Status effects are applied at the start of each turn
HP is updated; characters with 0 HP are eliminated
Player chooses an action via the CLI menu
Enemies always perform BasicAttack
End-of-round summary is displayed


Game ends with a Victory or Defeat screen


Characters
Players
AttributeWarriorWizardHP260200Attack4050Defense2010Speed3020Special SkillShield BashArcane Blast
Shield Bash (Warrior): Deals BasicAttack damage to a selected enemy. That enemy is stunned and cannot act for the current turn and the next turn. Cooldown: 3 turns.
Arcane Blast (Wizard): Deals BasicAttack damage to all enemies currently in combat. Each enemy killed by Arcane Blast permanently adds +10 to the Wizard's Attack (until end of level). Cooldown: 3 turns.
Enemies
AttributeGoblinWolfHP5540Attack3545Defense155Speed2535
Enemies always perform BasicAttack on their turn.

Actions
Players may choose one action per turn:
ActionDescriptionBasicAttackAttack a selected enemy. Damage = max(0, Attacker ATK − Target DEF)DefendIncrease own Defense by 10 for the current round and the next roundItemUse one of the equipped itemsSpecialSkillExecute the class-specific special ability (3-turn cooldown)

Items
Two single-use items are selected at game start (duplicates allowed):
ItemEffectPotionHeal 100 HP. New HP = min(Current HP + 100, Max HP)Power StoneTriggers the special skill effect once without starting or changing the cooldown timer (free extra use)Smoke BombEnemy attacks deal 0 damage for the current turn and the next turn

Status Effects
EffectDescriptionStunAffected entity skips their turn for the current turn and the next turnDefend Buff+10 Defense for the current and next roundSmoke Bomb ShieldIncoming enemy damage reduced to 0 for the current and next turnArcane Blast Buff+10 Attack per enemy killed by Arcane Blast, lasting until end of level

Levels & Difficulty
LevelDifficultyInitial SpawnBackup Spawn1Easy3 GoblinsNone2Medium1 Goblin + 1 Wolf2 Wolves3Hard2 Goblins1 Goblin + 2 Wolves
Backup enemies spawn simultaneously after the entire initial wave is defeated.

How to Run
Prerequisites

Java JDK 11 or higher
A terminal / command prompt

Steps
bash# Clone the repository
git clone <your-github-repo-url>
cd <repo-folder>

# Compile
javac -d out src/**/*.java

# Run
java -cp out Main

Project Structure
src/
├── main/
│   └── Main.java                  # Entry point
├── ui/
│   └── CLI.java                   # Command-line interface (Boundary)
├── engine/
│   └── BattleEngine.java          # Core battle loop (Control)
├── turnorder/
│   └── TurnOrderStrategy.java     # Interface for turn ordering
│   └── SpeedBasedTurnOrder.java   # Concrete strategy (sorted by Speed)
├── combatants/
│   └── Combatant.java             # Abstract base class
│   └── Player.java                # Abstract player class
│   └── Warrior.java
│   └── Wizard.java
│   └── Enemy.java                 # Abstract enemy class
│   └── Goblin.java
│   └── Wolf.java
├── actions/
│   └── Action.java                # Interface
│   └── BasicAttack.java
│   └── Defend.java
│   └── ItemAction.java
│   └── SpecialSkill.java
├── items/
│   └── Item.java                  # Interface
│   └── Potion.java
│   └── PowerStone.java
│   └── SmokeBomb.java
├── effects/
│   └── StatusEffect.java          # Interface
│   └── StunEffect.java
│   └── DefendEffect.java
│   └── SmokeBombEffect.java
│   └── ArcaneBlastBuff.java
└── levels/
    └── Level.java
    └── EasyLevel.java
    └── MediumLevel.java
    └── HardLevel.java

Architecture & Design
The system is structured around three layers:

Boundary (UI): CLI handles all input/output and is fully decoupled from game logic.
Control (Engine): BattleEngine manages round flow, turn ordering, action processing, and win/loss conditions.
Entity (Domain): Combatant, Action, Item, StatusEffect, and Level abstractions model the game world.

Key design patterns used:

Strategy Pattern — TurnOrderStrategy allows swappable turn-order logic
Command Pattern — Action interface encapsulates player and enemy actions
Template Method — Common combatant behaviour defined in abstract base classes
Factory / Level configuration — Level classes encapsulate enemy spawn configuration


SOLID Principles
PrincipleApplicationSRPEach class has a single responsibility (e.g., CLI only handles I/O, BattleEngine only manages battle flow)OCPNew Action or StatusEffect types can be added without modifying BattleEngineLSPWarrior, Wizard, Goblin, and Wolf are all interchangeable as CombatantISPInterfaces are kept focused; no class is forced to implement methods it does not useDIPBattleEngine depends on Combatant, Action, and TurnOrderStrategy abstractions, not concrete classes

UML Diagrams

Class Diagram: See diagrams/ClassDiagram.pdf
Sequence Diagram: See diagrams/SequenceDiagram.pdf

The sequence diagram models the following scenario:

Goblin executes BasicAttack on Warrior
Warrior uses Defend
Goblin attacks again with reduced damage
Warrior uses Shield Bash, stunning Goblin
Goblin is stunned, skips turn
Warrior uses Potion to heal
Goblin is still stunned, skips turn
Warrior executes BasicAttack, eliminating Goblin


Submission Checklist

 Report (UML diagrams, design considerations, reflection)
 UML Class Diagram (high-resolution PDF or image)
 UML Sequence Diagram (high-resolution PDF or image)
 GitHub repository with full commit history
 Declaration of Original Work (Appendix B) — signed by all members
 Declaration on Use of GAI Assistance (Appendix C) — signed individually

Deadline: April 19, 11:59 PM — Submit via NTULearn lab group assignment link.
Zip file naming convention: LABGroup_AssignmentGrp.zip (e.g., FCS1_Grp1.zip)
