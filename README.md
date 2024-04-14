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
continuously spawning enemies in a fixed box. The player will attack ~~fire projectiles
at~~ the enemies to ~~deplete their health and eventually~~ kill them. However, if the
player touches an enemy ~~(or potentially enemy projectile)~~, they will take damage.

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
