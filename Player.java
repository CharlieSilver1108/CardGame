package cardgame;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Player extends Thread {
    /* Attributes */
    Deck discardDeck;
    Deck pickUpDeck;
    Deck hand;
    int playerNo;
    static int winnerName;
    File playerFile;
    int noOfPlayers;


    /* Constructor */
    public Player(Deck hand, Deck discard, Deck pickup, int number, int noOfPlayers){
        this.hand = hand;                                       //sets the player's hand to the given deck
        this.discardDeck = discard;                             //sets the player's discard deck to the given deck
        this.pickUpDeck = pickup;                               //sets the player's pickup deck to the given deck
        this.playerNo = number;
        this.playerFile = new File("player" + Integer.toString(this.playerNo)+ "_output.txt");
        this.playerFile.delete();
        this.playerFile = new File("player" + Integer.toString(this.playerNo)+ "_output.txt");
        this.noOfPlayers = noOfPlayers;
    }

    /* Methods */
    public void writeMessageToFile(String message){
        //using exception handling in case something goes wrong creating/appending file
        //attempts to create a file object using the player number
        //if the file already exists, that file will be used

        //new objects are created in order to append to the file, not write over it 
        try (FileWriter fw = new FileWriter(playerFile.getName(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);){
            //prints a line escribing the action of the player    
            pw.println(message);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public synchronized void addToHand(Card card){
        this.hand.addCardToDeck(card);
    }

    public synchronized void discardCard(){                                                     //discards a card from the deck
        //choose card to discard randomly using popCard from deck.java
        //write card discarded to file
        //use addCard to add to the discard deck
        Card cardToDiscard = this.hand.popRandomCardFromHand(this.playerNo);
        int cardValue = cardToDiscard.getCardValue();
        //write card value to the file
        this.discardDeck.addCardToDeck(cardToDiscard);

        writeMessageToFile("player " + Integer.toString(playerNo) + " discards a " + Integer.toString(cardValue) + " to deck " + Integer.toString(this.discardDeck.getDeckNo()));
        

    }            

    public synchronized void pickupCard(){                                                      //pickups the card at the top of the deck
        //pickup card from pickup deck using popCard from deck.java
        //write card chosen to file
        //use addCard to add to the pickup deck
        
        Card cardToPickup = this.pickUpDeck.popCardFromDeck();
        int cardValue = cardToPickup.getCardValue();
        //write card value to the file
        this.hand.addCardToDeck(cardToPickup);

        writeMessageToFile("player " + Integer.toString(playerNo) + " draws a " + Integer.toString(cardValue) + " from deck " + Integer.toString(this.pickUpDeck.getDeckNo()));

    }

    public synchronized void currentHand() {                                    //writes the current hand of the player into their corresponding file
        writeMessageToFile("player " + Integer.toString(playerNo) + " current hand is " + this.hand.deckToString());
    }



    public synchronized boolean checkIfWon(){                                   //checks to see if the player has won the game
        int handLength = hand.getDeckLength();
        int cardValue = hand.cards.get(0).getCardValue();
        boolean haveWon = true;

        //iterates through all the cards in the deck checking if they all have the same value
        for (int i=0; i < handLength; i++) {
            int currentCardValue = hand.cards.get(i).getCardValue();
            if (currentCardValue != cardValue){
                haveWon = false;
                break;
            }
        }
        return haveWon;
    }


    static void interruptThreadByName(String threadName) {
        // Find the thread by name and interrupt it
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads);

        for (Thread thread : threads) {
            if (thread != null && thread.getName().equals(threadName)) {
                thread.interrupt();
                break;
            }
        }
    }

    public void run() {
        //writes the initial hand of the player into their file
        this.writeMessageToFile("player " + Integer.toString(this.playerNo) + " initial hand: " + this.hand.deckToString());
        while(!Thread.interrupted()){
            //if the player has won, the thread interrputs all the other threads to stop them running
            if (checkIfWon()){
                for (int i = 1; i <= noOfPlayers; i++) {
                    if(i != this.playerNo){
                        String threadName = "player" + i; 
                        interruptThreadByName(threadName);
                    }
                }
                winnerName = this.playerNo;
                break;
            }
            
            //ensures all players have taken their go before continuing
            while ((this.discardDeck.getDeckLength() > 5) && (this.pickUpDeck.getDeckLength() < 3)){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {

                }
            }

            //goes through all the methods required to have a 'turn'
            pickupCard();
            discardCard();
            currentHand();
        }

        //if the loop breaks then the file is written to either saying they have won or that another player has won
        if((this.playerNo) == (winnerName)){
            System.out.println("player " + winnerName + " wins");
            this.writeMessageToFile("player " + this.playerNo + " wins");
        }else{
            this.writeMessageToFile("player " + winnerName + " has informed player " + this.playerNo + " that player " + winnerName + " has won");
        }
        this.writeMessageToFile("player " + this.playerNo + " exits");
        this.writeMessageToFile("player " + this.playerNo + " final hand: " + this.hand.deckToString());
        this.pickUpDeck.writeDeckToFile();
    }

}