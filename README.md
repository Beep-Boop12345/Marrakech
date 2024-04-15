# Marrakech Project
This repository is based on work I did for the ANU course COMP1110 Assignment.


## Problem Description - Marrakech
In the game of Marrakech, two to four merchants in a crowded Marrakech souk compete to impress the market owner, Assam, in the hopes of being declared the best merchant in Marrakech. Players may direct how Assam wanders through the market to entice him into viewing their rugs, but be careful! If you put Assam on course to land on an opponent's rug, you will have to pay up! 

### The Board and Players

Marrakech is played on a 7x7 board, with a minimum of 2 and a maximum of 4 players. 

Each player starts the game with 15 rugs, which they will place on the board over the course of the game. Each rug is 2 squares long and one square wide (i.e they cover two squares on the board in a straight line).
Each player also has 30 dirhams (the currency in use in this game, and also in Morocco -- don't try and use the money from this game on a real-life trip to Morocco, though). The market owner, Assam, is also represented by a piece on the board, which moves as described below.

### Turns and Phases

Each player's turn consists of three phases:
First is the rotation phase, where a player may optionally rotate Assam.
Then, the player enters the movement phase, where a die is rolled and Assam moves, and the current player may be required to make payment to another player.
These two phases are described in the section [Assam and Payments Between Players](#assam-and-payments-between-players) below.
Last is the placement phase, where the current player must place a rug on the board.
The rules for placing the rug are describe in section [Rug Placement](#rug-placement) below.

The game is over when any player reaches the rotation phase of their turn, but does not have any rugs left to place. To be clear, when the first player places their last rug, the game will continue to allow any remaining players to place their final rugs. It is only over when a player enters the rotation phase and does not have any remaining rugs.

Once the game is over, it is scored. A player's final score is the number of dirhams they have plus the number of squares visible on the board that are of their colour. The player with the highest score wins. If two (or more) players have the same final score, then the player with the largest number of dirhams wins. If two (or more) of the tied players have the same number of dirhams, then the game is a draw.

### Assam and Payments Between Players

Assam is a key piece in the game of Marrakech, as he defines where players may place their rugs, as well as when players must pay other players. 

Assam starts the game in the centre of the board (i.e at position (3,3)), and may be facing in any direction (since the board is a square).

At the beginning of a player's turn, they choose to rotate Assam either 90 degrees to the right or left, or not at all (i.e., leave him facing the same direction).

Then, a special die is rolled to determine how far Assam moves on this turn.
The die is 6-sided, but the sides are numbered 1-4, with the numbers not equally common.
It has:
 - One face which shows a one
 - Two faces which show a two
 - Two faces which show a three
 - One face which shows a four

This means that the die is twice as likely to show a 2 or 3 as it is to show a 1 or 4.

After the die has been rolled, Assam is moved in the direction he is currently facing with the number of spaces he moves matching the number shown on the die. If he goes off the board at any time, he follows a pre-defined mosaic track on the edge of the board in order to be placed back on it and continue his movement. These tracks can be seen in the following image of the board:
![A top-down view of the Marrakech board](assets/Board%20Image.png)

Note that following one of these tracks does not count as one of the steps in the movement -- Assam must traverse a number of squares equal to the number shown on the die regardless of whether he left the board at any time during his movement. An example taken from the game rules can be seen here:
![An example of Assam's movement in a case involving moving off the board](assets/Assam%20Off%20Board%20Movement.png)

Note also that this board image is the canonical one with respect to the mosaic tracks -- that is to say that the three-quarter circles on some of the corners of the board should be in the top-right and bottom left; **not** the top-left and bottom-right. This is important for the unit-tested tasks, which assume this configuration.

If, after his movement ends, Assam lands on an empty square or a rug owned by the player whose turn it is, no payment is made, and the player's turn moves on to the placement phase (described below in the "Turns and Phases" section). If, however, Assam lands on a rug owned by another player then the player who moved him will have to pay the other player. This payment is equal to the size of the connected region of squares that are covered by rugs of the same colour, starting from the square that Assam landed on. Connected here means adjacent in one of the cardinal directions, i.e., sharing an edge; squares that are only diagonally adjacent (sharing only a corner) do not count as connected. The square which Assam landed on does count towards the size of the connected region and thus the payment. If a player is unable to pay the full amount, then they must pay as much as they are able, and then they are out of the game. A player who is out of the game cannot win, nor can they take any further turns. If Assam lands on one of their rugs later in the game, it is as though Assam landed on an empty square, and no payment is required.

Please see the following images for some example situations and their associated payments required. In each of these examples it is assumed that the player who moved Assam does not own the rug that he has landed on.

![A set of 3 board images showing some associated payments required](assets/Score%20Examples.png)

### Rug Placement

**NOTE**: the pictures in this section depict the board, rotated by 90 degrees clockwise. ***This is not the way the board is depicted in the game***. The board is instead depicted as it is in [this picture](assets/Board%20Image.png), in the section "[Assam and Payments Between Players](#assam-and-payments-between-players)".

After Assam has moved and any required payment is completed, the current player must place a rug on the board according to the following conditions:
- At least one of the squares of the rug must be adjacent to (share an edge with) the square that Assam occupies.
- The rug must not be placed under Assam.
- The rug may not cover both squares of another rug that is already on the board, if both squares of that rug are visible (not already covered).

To clarify the third condition, it is acceptable for a rug to cover up part of one or more rugs on the board, but not to be placed directly on top of another rug and cover both squares of it in one turn.
A rug can be placed exactly on top of a previously placed rug if at least one square of the previous rug is already covered by another rug already on the board.

For example, if the current state of the rugs is as in the top-left image, we can see some examples of valid and invalid placements (note that obviously all of these are technically illegal since Assam isn't on the board here, but this serves as an example of the rules surrounding overlapping).
![A set of 4 board images showing some legal and some illegal rug placements](assets/Placement_Rules_Examples.png)

To give a specific example of the third condition, if the board is currently set up like this:

![A purple rug is placed in the centre of the board on its own](assets/Third_Placement_Rule_FIRST.jpg)

And then the blue player plays like this:

![A blue rug is placed vertically over the top half of the purple rug](assets/Third_Placement_Rule_SECOND.jpg)

It is legal for the yellow player to play like this, even though it results in covering up an entire purple rug, because it does not cover both squares of that rug in the same turn (one of them has already been covered separately by the blue rug).

![A yellow rug is placed, covering up the remaining square of the purple rug and half of the blue rug](assets/Third_Placement_Rule_THIRD.jpg)



## String Representation

In order for us to provide unit tests for some of the tasks listed above, and thus to give you an indication of whether you have correctly solved them, we require a consistent representation of game state shared across all groups. This is done by providing a String representation of the state of the game at any given point. 
The String representation is **only for testing purposes**, and should not be the basis of your solution to the assignment. We expect you to create your own object-oriented representation of the game state, write constructors for your classes that create instances from the information in the game Strings (task 3), and to use your own objects in your implementation of the other tasks.
An implementation that solves the (unit-testable) tasks by manipulating only the String representation will not be eligible for full marks, even if the tasks are solved correctly.
For more guidance on this point, see the section of the FAQ below titled ["What does 'appropriate use of object-oriented features' mean?"](#what-does-appropriate-use-of-object-oriented-programming-features-mean).

Because you will be asked to convert from these Strings into your own state representation, and from your own objects into these Strings to return answers to unit tests, it's worth spending some time going over how they represent the game.
There are three key components to the game String:
 - The player strings
 - The Assam string
 - The board string

Let's go through each of these in turn, and then with that knowledge in hand construct the overall game String.

### Player Strings

A player String consists of 4 pieces of information. It always begins with a `P` character, to signify that it is a player String (and not some other kind of game String).
Then, we have the colour of the player in question. In the game of Marrakech, there are 4 options for the colour of a player, cyan (represented by a `c` character), yellow (represented by a `y` character), red (represented by an `r` character), and purple (represented by a `p` character).
After the colour character, we will have the amount of dirhams this player currently possesses. This should always be a 3-digit number, so if a player has 6 dirhams that's represented as `006`, and if they have 12 dirhams then that's represented as `012`.
Following that, we have the number of rugs remaining for this player to place. Players always start the game with 15 rugs, and lose one every time they place one. This is similarly always a 2-digit number.
Finally, we have a character which designates whether a given player is out of the game. A player who is in the game should have an `i` character in the final place, while a player who is out of the game should have an `o`.
So, for example, in the player string `Pr00803i`, we are representing the red player, who has 8 dirhams, 3 rugs remaining to place on the board, who is in the game.

### Assam String

An Assam String similarly encodes 3 pieces of information. It always begins with an `A` character.
Then, we have the x-coordinate of Assam's current position, followed by the y-coordinate of Assam's current position. These are always 1 digit numbers, since the board is only 7 squares wide. These coordinates are 0-indexed, so the top-left square of the board is (0,0), not (1,1).
Finally, we have Assam's orientation on the board. This can be one of 4 characters, `N`, `E`, `S`, or `W`. These characters refer to cardinal directions, and can be read as follows:
 - When Assam is facing `N`, he is facing towards the top of the board
 - When Assam is facing `E`, he is facing towards the right of the board
 - When Assam is facing `S`, he is facing towards the bottom of the board
 - When Assam is facing `W`, he is facing towards the left of the board

So, for example, in the Assam string `A04N`, Assam is at position (0,4), facing towards the top of the board.

### Rug Strings

A Rug String encodes information about a particular rug that either has been placed on the board, or is about to be placed. A rug string is exactly 7 characters long, and consists of four parts. 
Unlike the others, it does not have a consistent starting character. Instead, it starts with a character that matches the colour of the player who placed this rug on the board (or who is about to place it).
As with the player strings, this can be one of four options: `c`, `y`, `r`, or `p`.
Next, we have a 2-digit numerical ID. This ID can be any number (although having the first rug placed be 00, then the next one be 01, then 02, etc is one sensible system), yet every rug that a player owns must have a distinct ID.
That is to say that no two rugs on the board can share both a colour and an ID.
Finally, we have the x and y coordinates of both squares covered by the rug. As with the Assam strings, these are 0-indexed.
So for example, in the rug String `p014445`, this is a rug with ID 1, owned by the purple player, which covers squares (4,4) and (4,5).

This full rug String is used in tasks that involve evaluating a placement of a potential Rug, while the board String will use an abbreviated rug String, as described in the next section.

### Abbreviated Rug Strings
In addition, we also have abbreviated rug Strings which are used to make a more compact representation of the rugs for the board String. The abbreviated rug String is simply the first three characters of the full rug String. That is, the colour and the ID of the rug. So the purple player's rug with ID 2 would be `p02`.

We also use a special rug string, `n00`, to denote no rug. This is needed for the representation of the visible state of the board, described next.

### Board String

A Board String encodes the visible arrangement of rugs on the board at a given time. The board string begins with the character `B`, which is followed by a sequence of 49 abbreviated rug Strings, one for each square on the board, indicating which is the rug that is visible in that square. Thus, the board string is exactly 3*49=147 characters long.
If a particular square is not covered by any rug, the corresponding abbreviated rug string will be the special null string, `n00`.
The board string is read in column-major order, meaning that it first lists the visible rugs in column 0, then column 1, and so on, and within each column the squares in ascending row order. Thus, the first square in the board String is at position (0,0), the second at position (0,1), then (0,2), etc, up to (0,6), the position (1,0), (1,1), and so on.

### Game String

A Game string is the concatenation of one player string for each player, followed by one Assam string, followed by one board string.

For example, the game string `Pc00006oPy01107iPp05107iPr05907iA46WBn00c12n00r14y17y03n00r08r23p22p22y17y03n00r12r23r09r24r24p24c23n00p04c13p08p19n00n00p09p09c13n00r15n00r01n00n00r10r10p01n00r01n00n00r00p10p10p05p05` corresponds to the following game state:

![A game state corresponding to Pc00006oPy01107iPp05107iPr05907iA46WBn00c12n00r14y17y03n00r08r23p22p22y17y03n00r12r23r09r24r24p24c23n00p04c13p08p19n00n00p09p09c13n00r15n00r01n00n00r10r10p01n00r01n00n00r00p10p10p05p05](assets/example-game-state.png)

In this game state:
- There are four players;
- The red player has 59 dirhams and seven remaining rugs;
- The purple player has 51 dirhams and seven remaining rugs;
- The yellow player has 11 dirhams and seven remaining rugs;
- The cyan player has no dirhams, and therefore is out of the game; and
- Assam is standing on the red rug at (4, 6).



## Legal and Ethical Issues

First, as with any work you do, you must abide by the principles of
[honesty and integrity](http://academichonesty.anu.edu.au). You are
expected to demonstrate honesty and integrity in everything you do.

In addition to those ground rules, you are to follow the rules one
would normally be subject to in a commercial setting. In particular,
you may make use of the works of others under two fundamental
conditions: a) your use of their work must be clearly acknowledged,
and b) your use of their work must be legal (for example, consistent
with any copyright and licensing that applies to the given
material). *Please understand that violation of these rules is a very
serious offence.* However, as long as you abide by these rules, you
are explicitly invited to conduct research and make use of a variety
of sources. You are also given an explicit means with which to declare
your use of other sources (via originality statements you must
complete). It is important to realize that you will be assessed on the
basis of your original contributions to the project. While you won't
be penalized for correctly attributed use of others' ideas, the work
of others will not be considered as part of your
contribution. Therefore, these rules allow you to copy another
student's work entirely if: a) they gave you permission to do so, and
b) you acknowledged that you had done so. Notice, however, that if you
were to do this you would have no original contribution and so would
receive no marks for the assignment (but you would not have broken any
rules either).

