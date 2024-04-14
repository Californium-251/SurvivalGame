# Survival Thing

## Your second favorite vampire survivors ripoff

This application is inspired by titles such as *Vampire Survivors* and *Soul Knight*.
It will be a game about controlling a player that is faced against endless hordes of enemies.
Your goal: ~~upgrade your gear to~~ (To be implemented) survive as long as possible.

### Inspiration
The idea for this project came about when I was thinking about
some of the indie games I had played in the past. As a fan of indie
games in general, I knew I had to make something related to those games.

### Mechanics
This game will be quite rudimentary, consisting of just the player and
continuously spawning enemies in a fixed box. The player will fire projectiles
at the enemies to deplete their health and eventually kill them. However, if the
player touches an enemy (or potentially enemy projectile), they will take damage.


## User Stories:

- As a user, I want to see all active enemies on screen
- As a user, I want to feel challenged by a world border
  - The game would not be fun if you were able to just run forever
- As a user, I want to kill enemies to have them disappear
- As a user, I want to feel punished by touching threats
  - Lose Health/Die
- As a user, I want to be able to move around to evade threats
- As a user, I want to be able to place an arbitrary amount of traps into the world
- As a user, I want to be able to save my current game to the file
- As a user, I want to be able to reload a previously saved game from file and continue where I left off

## Game Instructions:

This is you: <br>
<pre>
||====================||
||                    ||
||                    ||
||                    ||
||                    ||
||                    ||
||          P         ||
||                    ||
||                    ||
||                    ||
||                    ||
||====================||
</pre>

Your objective: Survive endless hordes of enemies as long as possible.<br>
</br>
Enemies ('E') will spawn as soon as you perform one of the 5 commands:
- Attack: Space key
- Move Up: UP arrow
- Move Down: DOWN arrow
- Move Left: LEFT arrow
- Move Right: RIGHT arrow
- Place Trap: t
- Pause/Unpause: esc

Enemies will then make one move every time you perform an action. Namely, they will constantly
try to get closer to you. <br>
If an enemy touches you, your player icon will turn into an 'F' and you will lose
1 health point. When your health reaches 0, you die and the game ends.
<pre>
||====================||
||                    ||
||                    ||
||                    ||
||        E E         ||
||                    ||
||                    ||
||                    ||
||        E F         ||
||                    ||
||                    ||
||====================||
</pre>

However, you can prevent this by attacking the enemies. Every time you attack,
a + shape around your player is hit and any enemies in that area will be killed.

<pre>
||====================||            ||====================||
||                    ||            ||                    ||
||                    ||            ||                    ||
||                    ||            ||                    ||
||        E E         ||            ||                    ||
||                    ||            ||        E E         ||
||          _         ||     -->    ||                    ||
||        _| |_       ||            ||                    ||
||       |E F _|      ||            ||          P         ||
||         |_|        ||            ||                    ||
||                    ||            ||                    ||
||====================||            ||====================||
</pre>
*Note: the visual + is just for reference, not in the actual game*

Once you kill all enemies on screen, a new wave will appear. Try to survive
as long as possible.

# Instructions for Grader

- You can add a trap to the world by (having the game unpaused and) pressing t. The trap will be under the player
so it will not be immediately visible. Press the arrow keys to move around.
- You can get rid of traps by maneuvering in such a way that enemies walk over the trap.
- You can locate my visual component by pressing enter on the game over screen.
- You can save the state of my application by pausing (press esc) and then pressing s.
  - There is no visual feedback as of now but rest assured the game does save.
- You can reload the last saved state by selecting yes when prompted at the start of the game.

Launch from Main.java

# Phase 4
## Task 2
Sample console output from a game:

Wed Apr 03 13:15:38 PDT 2024| Added trap at: 40, 12 <br>
Wed Apr 03 13:15:38 PDT 2024| Added trap at: 40, 11 <br>
Wed Apr 03 13:15:40 PDT 2024| Added trap at: 41, 13 <br>
Wed Apr 03 13:15:41 PDT 2024| Added trap at: 39, 13 <br>
Wed Apr 03 13:15:43 PDT 2024| Consumed trap at: 40, 12 <br>
Wed Apr 03 13:15:43 PDT 2024| Consumed trap at: 40, 11 <br>

## Task 3
Looking at the UML Diagram, you immediately notice that LanternaInterface is doing a lot. It has very poor cohesion.
It is responsible for the updating of the game cycle, the display of the game, some game logic, and saving and loading.
This could be separated into 3, potentially 4 classes. One would handle game updates and logic (could be separated if it gets too large), another
the actual display, and the last, the saving and loading of the game.
World could also be separated into an EnemyHandler and a TrapHandler, or the program design could be improved to just use a single TickedEntityHandler.

There does currently exist a TickedEntity superclass of Trap and Enemy, but it does not get much use apart from defining a few abstract and concrete methods
for its subclasses. It might be a good idea to move some of the logic from LanternaInterface regarding the difference between
traps and enemies to this class and making it abstract, so Enemy and Trap each individually have to define their own logic which can then be called from a variable
with apparent type TickedEntity. This would work to improve cohesion.
