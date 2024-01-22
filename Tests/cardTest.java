package cardgame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class cardTest {
    @Test
    public void testGetCardValue() {
        //creates a card with a specifed value
        Card testCard = new Card(5);
        //checks that the card value matches the expected outcome
        assertEquals(5, testCard.getCardValue()); 
    }
}
