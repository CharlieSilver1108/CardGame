package cardgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    cardTest.class,
    deckTest.class,
    playerTest.class
})

public class AllTests {
}
