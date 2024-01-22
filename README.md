### About:
This is a collaborative project, completed as an assignment for a Year 2 Module at the University of Exeter, using multi-threading. It was completed using Microsoft Visual Studio Code.

### Project Description:
You will develop, in Java, a multi-threaded card playing simulation. The decks and players will form a ring topology. At the start of the game, each player will be distributed four cards in a round-robin fashion, from the top of the pack, starting by giving one card to player1, then one card to player2, etc. After the hands have been distributed, the decks will then be filled from the remaining cards in the pack, again in a round-robin fashion. To win the game, a player needs four cards of the same value in their hand. If the game is not won immediately, then the game progresses as follows: each player picks a card from the top of the deck to their left, and discards one to the bottom of the deck to their right. This process continues until the first player declares that they have four cards of the same value, at which point the game ends.

## Tests:
The test suite has its own Runner - AllTestsRunner.java
This file will run the AllTests class, which is the test suite that holds all the JUnit 4 test files
This includes cardTest, deckTest, and playerTest

Running this file should return the output 'true' if all the tests work properly

All JUnit 4 testing has been done using Visual Studio Code, with all testing working properly at the time of uploading
