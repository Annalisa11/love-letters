# Love Letters

Welcome to my implementation of the card game Love Letter by Seiji Kanai.  
A project developed during the course SEP (Softwareentwicklungspraktikum) at the LMU.

## Table of contents

- [Game](#-game)
  - [Rules](#rules)
- [Features and Specifications](#-features-and-specifications)
  - [Mandatory Features](#mandatory-features)
  - [Additional Features](#additional-features)
- [How to run it](#-how-to-run-it)
  - [Run the game](#run-the-game)
  - [See emojis](#see-emojis)
- [UML diagram](#-uml-diagram)
- [Author](#-author)

## 💌 Game
### Rules

#### Objective of the Game

In the wake of the arrest of Queen Marianna for high treason, none was more heartbroken than her daughter, Princess Annette. Suitors throughout the City-State of Tempest sought to ease Annette's sorrow by courting her to bring some joy into her life.

You are one of these suitors, trying to get your love letter to the princess. Unfortunately, she has locked herself in the palace, so you must rely on intermediaries to carry your message.

During the game, you hold one secret card in your hand. This is who currently carries your message of love for the princess.

Make sure that the person closest to the princess holds your love letter at the end of the day, so it reaches her first!

#### Game Play

Love Letter is played in a series of rounds. Each round represents one day. At the end of each round, one player's letter reaches Princess Annette, and she reads it.

When she reads enough letters from one suitor, she becomes enamored and grants that suitor permission to court her. That player wins the princess's heart and the game.

#### Taking a Turn

On your turn, draw the top card from the deck and add it to your hand. Then choose one of the two cards in your hand and discard it face up in front of you. Apply any effect on the card you discarded. You must apply its effect, even if it is bad for you.

All discarded cards remain in front of the player who discarded them. Overlap the cards so that it's clear in which order they were discarded. This helps players to figure out which cards other players might be holding.

Once you finish applying the card's effect, the turn passes to the player on your left.

#### Out of the Round

If a player is knocked out of the round, that player discards the card in his or her hand face up (do not apply the card's effect) and takes no more turns until next round.


#### End of a Round

A round ends if the deck is empty at the end of a turn. The royal residence closes for the evening, the person closest to the princess delivers the love letter, and Princess Annette retires to her chambers to read it.

All players still in the round reveal their hands. The player with the highest ranked person wins the round. In case of a tie, the player who discarded the highest total value of cards wins.

A round also ends if all players but one are out of the round, in which case the remaining player wins.

The winner receives a token of affection. Shuffle all 16 cards together, and play a new round following all of the setup rules.

The winner of the previous round goes first, because the princess speaks kindly of him or her at breakfast.

#### End of the game

A player wins the game after winning a number of tokens based on the number of players:

- 2 players - 7 tokens
- 3 players - 5 tokens
- 4 players - 4 tokens


## 📋 Features and specifications 

### Mandatory features
- Various **commands** have to be implemented.   
    (help, start, showHand, showScore, playCard, showPlayers)
- Users must be able to choose the **number of players** and their **names**
- The game should be **playable** in the version for **2-4 players**.
- invalid inputs and errors should be handled
### Additional features
- Other Commands  
  (rules, exit)
- ~~Colorful Console Output~~
- more beautiful UI in terminal with special cards display and emojis

## 🚀 How to run it 

>:warning: **Prerequisites:**  
>- Java 19 or higher installed
>- UTF-8 encoding enabled

### Run the game
1. Go to the [artifacts folder](out/artifacts) _(out/artifacts)_ and download the `.jar` file.   
2. Go to the location where you saved the file on your computer and open the terminal.  
3. Paste this command into the terminal and press enter.
```batch
java -jar LoveLetters.jar
```
The game should start now... enjoy :)

### See Emojis
if you see `?` instead of emojis you possibly have to enable UTF-8 encoding on your computer.   
For Windows you can try following steps:  
1. Go to Windows Settings > Time & Language > Language > Administrative language settings > change system locale
2. check the box 'Beta: Use Unicode UTF-8 for worldwide language support'
3. restart your computer

## 📉 UML diagram 

Here you can see the most recent UML diagram for my code. 

![UML diagram](UML-diagrams/UML-diagram_04.png "UML diagram")
You can also check out [previous versions](UML-diagrams) of the diagram.  
_(UML-diagrams)_

## 👩‍💻 Author 
Annalisa Comin
- [GitHub](https://github.com/Annalisa11)
- [Instagram](https://www.instagram.com/annalisa_comin/)

