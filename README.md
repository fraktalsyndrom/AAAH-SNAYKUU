# SNAYKUU THE MANUAL: THIS TIME IT'S PERSONAL

An engine/API for programming Snake bots and pitting them against each other

Copyright (C) <2011>  <Arian Jafari, Sixten Hilborn, Erik Thuning>

Version 0.2


## TABLE OF CONTENTS 

* [GETTING STARTED](#GETTING-STARTED)
  * [Compiling the source](#Compiling-the-source)
  * [Compiling your bots](#Compiling-the-source)
  * [Programming your bot](#Programming-your-bot)
* [USING THE APPLICATION](#USING-THE-APPLICATION)
  * [Starting SNAYKUU](#Starting-SNAYKUU)
  * [The main menu](#The-main-menu)
  * [The 'Game settings' tab](#The-Game-settings-tab)
  * [Replays](#Replays)
  * [Developer mode](#Developer-mode)
* [IN- AND POST-GAME](#IN--AND-POST-GAME)
  * [The game board](#The-game-board)
  * [The score board](#The-score-board)
  * [The post-game window](#The-post-game-window)
* [CREDITS](#CREDITS)


## GETTING STARTED

### Compiling the source

_NOTE - This step is only for those who are interested in modifying 
the source code. The program can be run by simply double-clicking
the 'snaykuu.jar' file_

Compilation of the program is done with ant.

Using your terminal, navigate to the `snaykuu` directory and run the command 
`ant jar`. This compiles the code and creates an executable jar file, which 
can then be executed either by double-clicking it or running `ant run`.


### Compiling the bots

Bots are compiled separately.

With ant, bots are compiled using the command `ant bot`.

If you don't have ant installed, navigate to the directory where your `snaykuu.jar`
is located, and then run this command:
```
# javac bot/*.java -cp snaykuu.jar
```

### Programming your bot

The essence of writing a SNAYKUU bot is simple. Your bot is a java class 
implementing the interface `Brain`, requiring a single method called 
`getNextMove()`. This method will be called once each tick of the game, and is 
where you calculate in which direction you want your snake to move next. Each 
tick you are given a `GameState` object, which is a detailed representation of 
the current state of the game, also containing several useful methods to assist 
you in the coding process. 

Much more detailed information can be found in the API in the `doc/` subdirectory.


## USING THE APPLICATION

### Starting SNAYKUU

Start the program by either double-clicking `snaykuu.jar`, navigating to the 
snaykuu directory and running `java -jar snaykuu.jar` in a terminal, or by 
running the `ant run` command if you have ant installed.


### The main menu

When you first start SNAYKUU, you will see two lists in the middle of the 
screen; _Snakes in game_ and _Available snakes_. _Available snakes_ contains 
a list of all the compiled classes implementing the `Brain` interface. By 
double clicking the name of a bot in the _Available snakes_ list, you can 
add/remove it to the game. When you want to start the game, press the 
_Start_ button.


### The 'Game settings' tab

In the _Game settings_ tab you can manually select which settings you want to 
play with. These are your options:

* **Board width**  
  The width of the game board, in squares.
* **Board height**  
  The height of the game board, in squares.
* **Pixels per square**  
  The size of each square, in pixels.
* **Fruit to win**  
  The number of fruits each snake has to eat in order to win the game.
* **Ticks between fruits**  
  The frequency with which new fruits appear on the game board.
  '5' means that a new fruit spawns every five ticks.
* **Ticks per unit of snayk growth**  
  The frequency with which snayks grow. '5' means that snayks grow every 
  five ticks.
* **Thinking time** (ms/frame)  
  The amount of time (in ms) each brain has to decide on its next move.
* **Game speed** (ms/frame)  
  The amount of time (in ms) that will elapse between each _tick_. Decrease 
  to make the game appear to move faster, and vice versa. NOTE: It is recommended 
  that this is set to at least thrice the value of _Thinking time_.


### Replays

After a game has been played, you are given the option to save a replay of the 
game at hand to be able to view later. If you want to view a saved replay, simply 
click Replay tab, click the "Load an old replay and play it!" button, and use the 
built in file browser to find your .srp replay file.

Note that replays saved in older versions may not be compatible wth your current version.

### Developer mode

In the developer mode, you can run any number of 'simulated' games much faster 
than running them individually with graphics. To use dev mode, begin by selecting 
the participating snakes in the snake selection screen. Then, navigate to the 
_developer_ tab, select the number of games you want to be simulated, and then click 
_Run test games_. The program will freeze for a while while the simulations are running. 

When it is done, a menu will pop up containing statistics and data from the simulations.


## IN- AND POST-GAME

### The game board

This screen is fairly straight-forward. The snakes show up on the screen, ready to 
rumble! Fruit objects will occasionally show up on the board, and it is up to the snakes 
to reach them and devour them.


### The score board

On the right hand side of the game board is a live-updated score board of the current 
round. Snakes are shown, identified by name and color, together with the number of fruits 
they have eaten and the number of turns they have survived.


### The post-game window

After a game has finished, a new window displaying the results of the game will pop up, 
together with some options. The score board will be nearly identical to the one that is 
shown during the game, showing the scores of all the snakes as well as their placement in 
the round. Together with this, four buttons will appear: _New game_, _Rematch_, 
_Save replay_ and _Exit_. This is what they do:

* **New game**  
  Brings you to the original snake selection menu.
* **Rematch**  
  Immediately starts a new round using the same snakes and game settings.
* **Save replay**  
  Allows you to save a replay of this game. See ['Replays'](#Replays).
* **Exit**  
  Exits the application.

## CREDITS

- Sixten Hilborn; _Project co-manager, Lead coder, Whiteboard wizard._  
- Arian Jafari; _Project co-manager, Lead coder, Documentation dork, Executive credits producer._  
- Erik Thuning; _Coder, Tester, Graphics guru, Affine transformer, Readme janitor._  
- Fredrik Norberg; _Tester, Master fruit devourer._  
- Esbjörn Olsson; _Tester, Internet aficionado, Security ninja._  
