package cardgame;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class playerTest {

    @Test
    public void testWriteMessageToFile() {
        //creates a set of decks and a player
        Deck pickUpDeck = new Deck(0);
        Deck discardDeck = new Deck(1);
        Player testPlayer = new Player(new Deck(0), discardDeck, pickUpDeck, 0, 1);

        //writes a message to the file using writeMessageToFile
        String x = "This is a test message";
        testPlayer.writeMessageToFile(x);

        try {
            //reads the file and makes sure the line in the file matches the string inputted into the file
            File myFile = new File(testPlayer.playerFile.getName());
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals(x, data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Test
    public void testAddToHand() {
        //creates a set of decks, a card and a player
        Deck pickUpDeck = new Deck(0);
        Deck discardDeck = new Deck(1);
        Card testCard = new Card(7);

        Player testPlayer = new Player(new Deck(0), discardDeck, pickUpDeck, 0, 1);

        //adds the card to the players hand
        testPlayer.addToHand(testCard);
        //checks that the value of the card matches the card put into the hand
        assertEquals("7 ", testPlayer.hand.deckToString());

    }

    @Test
    public void testDiscardCard() {
        //creates a set of decks, some cards and a player
        Deck pickUpTest = new Deck(0);
        Deck discardTest = new Deck(1);
        Card testCard1 = new Card(7);
        Card testCard2 = new Card(6);

        Player testPlayer = new Player(new Deck(0), discardTest, pickUpTest, 0, 1);

        //adds the cards to the hand of the player
        testPlayer.addToHand(testCard1);
        testPlayer.addToHand(testCard2);

        //makes sure that the deck has the correct cards in it and is of the correct length
        assertEquals("7 6 ", testPlayer.hand.deckToString());
        assertEquals(2, testPlayer.hand.getDeckLength());
        
        //makes sure that the discarddeck has nothing in it
        assertEquals(0, testPlayer.discardDeck.getDeckLength());
        
        //discards a card to the deck using discardCard
        testPlayer.discardCard();
        //checks that the method has worked by checking the length of the deck
        assertEquals(1, testPlayer.discardDeck.getDeckLength());
        //discards a card to the deck using discardCard
        testPlayer.discardCard();
        //checks that the method has worked by checking the length of the deck
        assertEquals(2, testPlayer.discardDeck.getDeckLength());
        //checks that the discard deck is the same as the one passed through in the constructor of the player
        assertEquals(discardTest, testPlayer.discardDeck); 
        //makes sure that the hand does not have any cards in it
        assertEquals("", testPlayer.hand.deckToString());
        assertEquals(0, testPlayer.hand.getDeckLength());
    }

    @Test
    public void testPickupCard() {
        //creates a set of decks, some cards and a player
        Deck pickUpTest = new Deck(0);
        Deck discardTest = new Deck(1);
        Card testCard1 = new Card(7);
        Card testCard2 = new Card(6);
        Player testPlayer = new Player(new Deck(0), discardTest, pickUpTest, 0, 1);

        //adds cards to the pick up deck
        testPlayer.pickUpDeck.addCardToDeck(testCard1);
        testPlayer.pickUpDeck.addCardToDeck(testCard2);
        //checks that the deck is correct before proceeding
        assertEquals("7 6 ", testPlayer.pickUpDeck.deckToString());
        assertEquals(2, testPlayer.pickUpDeck.getDeckLength());
        //checks that the pick up deck is the same as the one passed through in the constructor of the player
        assertEquals(pickUpTest, testPlayer.pickUpDeck);

        //uses the pickupCard method
        testPlayer.pickupCard();
        //checks that the card has been removed from the pickup deck and moved into the hand
        assertEquals(1, testPlayer.hand.getDeckLength());
        assertEquals(1, testPlayer.pickUpDeck.getDeckLength());
        //checks that the card taken is from the 'top' of the deck
        assertEquals("6 ", testPlayer.pickUpDeck.deckToString());

        //does the same as above
        testPlayer.pickupCard();
        assertEquals(2, testPlayer.hand.getDeckLength());
        assertEquals(0, testPlayer.pickUpDeck.getDeckLength());
    }

    @Test
    public void testCurrentHand() {
        //creates a set of decks, some cards and a player
        Deck pickUpTest = new Deck(0);
        Deck discardTest = new Deck(1);
        Card testCard1 = new Card(7);
        Card testCard2 = new Card(6);
        Card testCard3 = new Card(9);
        Card testCard4 = new Card(1);

        Player testPlayer = new Player(new Deck(0), discardTest, pickUpTest, 0, 1);

        //adds the cards to the player's hand
        testPlayer.addToHand(testCard1);
        testPlayer.addToHand(testCard2);
        testPlayer.addToHand(testCard3);
        testPlayer.addToHand(testCard4);

        //uses the currentHand method
        testPlayer.currentHand();

        //checks that the line written into the file has the correct cards in it
        try {
            File myObj = new File(testPlayer.playerFile.getName());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals("player 0 current hand is 7 6 9 1 ", data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    @Test
    public void testCheckIfWon() {
        //creates a set of decks, some cards and some players
        Deck pickUpTest = new Deck(0);
        Deck discardTest = new Deck(1);
        Card testCard1 = new Card(6);
        Card testCard2 = new Card(6);
        Card testCard3 = new Card(6);
        Card testCard4 = new Card(6);
        Card testCard5 = new Card(5);

        Player testPlayer1 = new Player(new Deck(0), discardTest, pickUpTest, 0, 2);

        //adds four of the cards to the first players hand
        testPlayer1.addToHand(testCard1);
        testPlayer1.addToHand(testCard2);
        testPlayer1.addToHand(testCard3);
        testPlayer1.addToHand(testCard4);
        //since the hand is a winning one, checkIfWon should return true
        assertTrue(testPlayer1.checkIfWon());

        Player testPlayer2 = new Player(new Deck(0), discardTest, pickUpTest, 0, 2);
        //adds four of the cards to the second players hand
        testPlayer2.addToHand(testCard1);
        testPlayer2.addToHand(testCard2);
        testPlayer2.addToHand(testCard3);
        testPlayer2.addToHand(testCard5);
        //since the hand is not a winning one, checkIfWon should return false
        assertFalse(testPlayer2.checkIfWon());
    }

    @Test
    public void testInterruptThreadByName() {
        //creates a thread using a lambda function
        Thread testThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        });

        //gives the thread a name and starts it
        testThread.setName("testThread");
        testThread.start();
        //checks that the thread is running
        assert(testThread.isAlive());
        //uses interruptTheadByName to interrupt the thread
        Player.interruptThreadByName("testThread");
        //tests that the method has worked by checking that the thread has been interrupted
        assert(testThread.isInterrupted());
    }

    @Test
    public void testRun() {
        //creates a set of decks, some cards and some players
        Deck pickUpTest = new Deck(0);
        Deck discardTest = new Deck(1);
        Card testCard1 = new Card(1);
        Card testCard2 = new Card(1);
        Card testCard3 = new Card(1);
        Card testCard4 = new Card(4);
        Card testCard5 = new Card(1);
        Card testCard6 = new Card(1);
        Card testCard7 = new Card(7);
        Card testCard8 = new Card(8);


        Player testPlayer = new Player(new Deck(0), discardTest, pickUpTest, 1, 1);

        //adds the cards to the hand and decks
        testPlayer.addToHand(testCard1);
        testPlayer.addToHand(testCard2);
        testPlayer.addToHand(testCard3);
        testPlayer.addToHand(testCard4);

        pickUpTest.addCardToDeck(testCard5);
        pickUpTest.addCardToDeck(testCard6);

        discardTest.addCardToDeck(testCard7);
        discardTest.addCardToDeck(testCard8);

        //runs the thread
        testPlayer.run();

        //checks that the lines written into the file 
        try {
            String data;
            File myFile = new File(testPlayer.playerFile.getName());
            Scanner myReader = new Scanner(myFile);
            myReader.hasNextLine();
            data = myReader.nextLine();
            assertEquals("player 1 initial hand: 1 1 1 4 ", data);
            data = myReader.nextLine();
            assertEquals("player 1 draws a 1 from deck 0", data);
            data = myReader.nextLine();
            assertEquals("player 1 discards a 4 to deck 1", data);
            data = myReader.nextLine();
            assertEquals("player 1 current hand is 1 1 1 1 ", data);
            data = myReader.nextLine();
            assertEquals("player 1 wins", data);
            data = myReader.nextLine();
            assertEquals("player 1 exits", data);
            data = myReader.nextLine();
            assertEquals("player 1 final hand: 1 1 1 1 ", data);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}
