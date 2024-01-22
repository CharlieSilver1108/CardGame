package cardgame;

public class Card {
    /* Attributes */
    int cardValue;

     /* Constructor */
    public Card(int value){                             //creates a card with given value
        this.cardValue = value;
    }
    
    /* Methods */
    public int getCardValue(){                          //returns the card's value
        return this.cardValue;
    }
}