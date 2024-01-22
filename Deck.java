package cardgame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Deck {
    /* Attributes */
    public ArrayList<Card> cards = new ArrayList<Card>();
    int deckNo;
    File deckFile;

    /* Constructor */
    public Deck(int number){
        this.deckNo = number;
        this.deckFile = new File("deck" + Integer.toString(this.deckNo)+ "_output.txt");
        this.deckFile.delete();
        this.deckFile = new File("deck" + Integer.toString(this.deckNo)+ "_output.txt");
    }

    /* Methods */
    public int getDeckNo(){
        return this.deckNo;
    }

    public int getDeckLength() {                             //gets the length of the deck and returns it
        int deckLength = cards.size();
        return deckLength;
    }

    public void addCardToDeck(Card card){                        //adds a card to the deck's ArrayList  
        this.cards.add(card);
    }

    public Card popCardFromDeck(){                                    //returns a random card from the deck and removes it from the deck
        //if it gets to a point in which there are no cards in the deck, the game will finished as it is assumed there is no winning combination
        if (cards.isEmpty()) {
            System.out.println("No winner can be found, game over");
            System.exit(0);
        }
        
        Card cardToRemove = cards.get(0);
        cards.remove(0);
        return cardToRemove;
    }

    
    public Card popRandomCardFromHand(int playerNo){                                    //returns a random card from the deck and removes it from the deck
        int deckLength = cards.size();
        //generates a number based upon the length of the deck
        int randomNumber = (int)(Math.random() * (deckLength));
        Card cardToRemove = cards.get(randomNumber);
        //stops the player from removing a card thats value is equivalent to the player number
        while(cardToRemove.getCardValue() == playerNo){
            randomNumber = (int)(Math.random() * (deckLength));
            cardToRemove = cards.get(randomNumber);
        }
        cards.remove(randomNumber);
        return cardToRemove;
    }

    public String deckToString(){                                           //returns the deck's contents as a string
        String deckString = "";                                             
        int cardValue = -1;

        int deckLength = this.getDeckLength();
        
        //loops through every card in the deck
        for (int i=0; i < deckLength; i++) {
            cardValue = cards.get(i).getCardValue();
            //converts the value of the card into a string and concatenates it to the string to be returned
            deckString += String.valueOf(cardValue) + " ";
        }

        return deckString;
    }

    public void writeDeckToFile(){
        //using exception handling in case something goes wrong creating/appending file
        //attempts to create a file object using the deck number
        //if the file already exists, that file will be used

        //new objects are created in order to append to the file, not write over it 
        String message = "deck" + this.getDeckNo() + " contents: " + this.deckToString();
        try (FileWriter fw = new FileWriter(deckFile.getName(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);){
            //prints a line describing the deck contents     
            pw.println(message);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}