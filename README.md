Java II Group Work Assignment 2015 (Group E-2)

# COMMAND-LINE MINESWEEPER
Version:4.2 (2016/01/11)

## What's new in V4.2
***
+ refactored code
+ debugged windows display

## Description
***
This is a simple Minesweeper game written fully in Java, playable from the computer's command line.
The game does not use any GUI, thus requiring less processing power, and giving it an old-school taste.
Block numbers and mine counts are fully customisable, although default is set to 9*9 and 13% mines.

The Mac version uses terminal escape sequences to add color to command line.
Windows command prompt does not support ANSI escape sequences, thus only playable in monochrome.

## How to play
***
1. Open terminal or command prompt and go to this directory.

1. To play in default mode, type:
```java
java Minesweeper
```
1. To set custom dimension and mine count, type:
```java
 java Minesweeper [side-length] [how-many-mines]
```
*-- note: arguments must be integer!!*

1. Action commands:
```java
> open x y // open cell at coordinate
> flag x y // flag/unflag cell at coordinate
```
4. To quit game, hit **ctrl + c** on your keyboard.


## Contributors
***
+ Shinnosuke Kishida [leader]
+ Tomoki Kano [sub-leader]
+ Ryouta Hatayama
+ Satoka Hiraoka
+ Hiroaki Miyake
+ Takumi Okubo
