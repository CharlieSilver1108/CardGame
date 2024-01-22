package cardgame;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class CardGame {

    public static void main(String [] args) {
        //creates an arraylist of all cards which makes it easier to manage
        ArrayList<Card> allCards = new ArrayList<Card>();
        //creates an arraylist of all decks which makes it easier to manage
        ArrayList<Deck> decks = new ArrayList<Deck>();
        //creates an arraylist of all players to make it easier to manage
        ArrayList<Player> players = new ArrayList<Player>();


        //allows the user to input the number of players
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the number of players:");
        int noOfPlayers = scanner.nextInt();


        //allows the user to input the desired pack to be loaded
        System.out.println("Please enter location of pack to load:");
        String fileName = scanner.next();
        
        
        //tries to read the file if it exists
        boolean validFile = false;
        //program loops until the user puts in a valid pack
        while(!validFile) {
            try {
                File file = new File(fileName);
                Scanner fileReader = new Scanner(file);
                //puts each line in the file into the arraylist with each card being assigned its value
                int cardValue;
                while (fileReader.hasNextLine()) {
                    cardValue = fileReader.nextInt();
                    allCards.add(new Card(cardValue));
                }
                fileReader.close();
                scanner.close();
                validFile = true;
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println("Please enter location of pack to load:");
                fileName = scanner.next();
            }
        }

        for (int i=1; i<=(2*noOfPlayers); i++) {
            //creates all decks required
            decks.add(new Deck(i));
        }
        

        for(int i=1; i<=noOfPlayers; i++){
            //creates a player based on the number of the player
            //if it is the last player, the discard deck loops back around
            if (i==noOfPlayers){
                players.add(new Player(decks.get(noOfPlayers+i-1), decks.get(0), decks.get(i-1), i, noOfPlayers));
            } else {
                players.add(new Player(decks.get(noOfPlayers+i-1), decks.get(i), decks.get(i-1), i, noOfPlayers));
            }
        }

        //adds cards to each hand in a round robin style
        for (int i=0; i<(4*noOfPlayers); i++){
            Random random = new Random();
            //generates an integer based on the length of the arraylist
            int j = random.nextInt(allCards.size());
            //modular division is used to get the player number if i is greater than the number of players in the game
            players.get(i % noOfPlayers).addToHand(allCards.get(j));
            allCards.remove(j);
        }
 

        //adds cards to each deck in a round robin style
        for (int i=0; i<(4*noOfPlayers); i++){
            Random random = new Random();
            int j = random.nextInt(allCards.size());
            decks.get(i % noOfPlayers).addCardToDeck(allCards.get(j));
            allCards.remove(j);
        }

    

        //all players will be threaded
        int threadNoToBeAssigned = 1;
        for (Player player : players){
            //each thread is given a name so that the threads can interact with each other
            player.setName("player" + threadNoToBeAssigned);
            //starts the thread
            player.start();
            //increments the variable
            threadNoToBeAssigned += 1;
        }

    }

}
