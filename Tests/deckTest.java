package cardgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class deckTest {

    @Test
    public void testAddCardToDeckandGetDeckLength() {
        //creates a deck
        Deck testDeck = new Deck(2);
        //checks that getDeckLength by making sure that the length is 0
        assertEquals(0, testDeck.getDeckLength());
    
        //creates a card, the value is irrelevant
        Card testCard1 = new Card(4);
        //adds a card to the deck
        testDeck.addCardToDeck(testCard1);
        //tests that addCardToDeck and getDeckLength works by making sure that the length outputted is 1
        assertEquals(1, testDeck.getDeckLength());
        

        //does the same as above 
        Card testCard2 = new Card(6);
        testDeck.addCardToDeck(testCard2);
        assertEquals(2, testDeck.getDeckLength());

    }

    @Test
    public void testDeckToString() {
        //creates a deck
        Deck testDeck= new Deck(2);

        //creates 4 cards and adds them to the deck
        Card testCard1 = new Card(6);
        testDeck.addCardToDeck(testCard1);
        Card testCard2 = new Card(4);
        testDeck.addCardToDeck(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCardToDeck(testCard3);
        Card testCard4 = new Card(7);
        testDeck.addCardToDeck(testCard4);

        //checks that the output of deckToString is as expected
        assertEquals("6 4 1 7 ", testDeck.deckToString());


    }

    @Test
    public void testGetDeckNo() {
        //creates a deck with a specified number
        Deck testDeck = new Deck(3);
        //makes sure that the value returned by getDeckNo
        assertEquals(3, testDeck.getDeckNo());
    }

    @Test
    public void testPopCardFromDeck() {
        //creates a deck and adds cards to it
        Deck testDeck= new Deck(2);
        Card testCard1 = new Card(6);
        testDeck.addCardToDeck(testCard1);
        Card testCard2 = new Card(4);
        testDeck.addCardToDeck(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCardToDeck(testCard3);
        Card testCard4 = new Card(7);
        testDeck.addCardToDeck(testCard4);

        //pops the card at the top of the deck using popCardFromDeck
        Card x = testDeck.popCardFromDeck();
        //gets the value of the card
        int y = x.getCardValue();

        //makes sure that something has been returned by the method
        assertNotNull(x);
        //tests that the value returned by the method is the correct value
        assertTrue(y==6);
        //tests that the card has been removed from the deck by checking its length
        assertEquals(3, testDeck.getDeckLength());
    }

    @Test
    public void testPopRandomCardFromHand() {
        //creates a variable that will be used to test if the cards with this number will be retained
        int testPlayerNo = 1;
        //creates a deck and adds cards to it
        Deck testDeck= new Deck(2);
        Card testCard1 = new Card(1);
        testDeck.addCardToDeck(testCard1);
        Card testCard2 = new Card(1);
        testDeck.addCardToDeck(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCardToDeck(testCard3);
        Card testCard4 = new Card(7);
        testDeck.addCardToDeck(testCard4);

        //removes a card using popRandomCardFromHand and pass testPlayerNoas a variable
        Card x = testDeck.popRandomCardFromHand(testPlayerNo);
        //gets the value of the card
        int y = x.getCardValue();

        //makes sure that something had been returned
        assertNotNull(x);
        //the returned value should be 7, as it is the only card that does not match the testPlayerNo
        assertTrue(y==7);
        //tests that the card has been removed from the deck
        assertEquals(3, testDeck.getDeckLength());

    }

    @Test
    public void testWriteDeckToFile() {
        Deck testDeck= new Deck(0);
        Card testCard1 = new Card(6);
        testDeck.addCardToDeck(testCard1);
        Card testCard2 = new Card(4);
        testDeck.addCardToDeck(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCardToDeck(testCard3);
        Card testCard4 = new Card(7);
        testDeck.addCardToDeck(testCard4);
        testDeck.writeDeckToFile();


        try {
            File myFile = new File(testDeck.deckFile.getName());
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals("deck0 contents: 6 4 1 7 ", data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    
}