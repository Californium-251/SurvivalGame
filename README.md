# Survival Thing

## Your second favorite vampire survivors ripoff

This application is inspired by titles such as *Vampire Survivors* and *Soul Knight*.
It will be a game about controlling a player that is faced against endless hordes of enemies.
Your goal: upgrade your gear to survive as long as possible.

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
Wed Apr 03 13:15:38 PDT 2024| Added trap at: 40, 12
Wed Apr 03 13:15:38 PDT 2024| Added trap at: 40, 11
Wed Apr 03 13:15:40 PDT 2024| Added trap at: 41, 13
Wed Apr 03 13:15:41 PDT 2024| Added trap at: 39, 13
Wed Apr 03 13:15:43 PDT 2024| Consumed trap at: 40, 12
Wed Apr 03 13:15:43 PDT 2024| Consumed trap at: 40, 11

## Task 3