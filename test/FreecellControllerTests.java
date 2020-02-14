import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw03.FreecellController;

import static junit.framework.TestCase.assertEquals;

/**
 * Test for the FreecellController class.
 */
public class FreecellControllerTests {
  
  
  String unshuffedBoard = "\nF1:\n"
      + "F2:\n"
      + "F3:\n"
      + "F4:\n"
      + "O1:\n"
      + "O2:\n"
      + "C 1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
      + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
      + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
      + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n";
  
  String cascadeToOpenupdatedBoard = "\nF1:\n"
      + "F2:\n"
      + "F3:\n"
      + "F4:\n"
      + "O1: K♣\n"
      + "O2:\n"
      + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
      + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
      + "C3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
      + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n";
  
  /* test if IllegalArgumentException if thrown when null ap value */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsIllegalArgumentExceptionAP() {
    FreecellController controller = new FreecellController(new StringReader(""), null);
  }
  
  /* test if IllegalArgumentException is thrown when null rd value */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsIllegalArgumentExRD() {
    FreecellController controller = new FreecellController(null, new StringBuffer());
  }
  
  /* test if IllegalArgumentException is thrown when null rd value and null ap value */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorThrowsIllegalArgumentExRDAndAP() {
    FreecellController controller = new FreecellController(null, null);
  }
  
  /* tests if IllegalArgumentException when null model is passed into playGame*/
  @Test(expected = IllegalArgumentException.class)
  public void testPlaygameThrowsIllegalArgumentExNullMode() {
    FreecellController controller = new FreecellController(new StringReader(""),
        new StringWriter());
    
    FreecellModel model = new FreecellModel();
    List<Card> deck = model.getDeck();
    
    controller.playGame(deck, null, 4, 6, false);
  }
  
  /* tests if IllegalArgumentException when null deck is passed into playGame*/
  @Test(expected = IllegalArgumentException.class)
  public void testPlaygameThrowsIllegalArgumentExNullDeck() {
    FreecellController controller =
        new FreecellController(new StringReader(""), new StringWriter());
    
    FreecellModel model = new FreecellModel();
    
    controller.playGame(null, model, 4, 6, false);
  }
  
  @Test
  public void testQuitFirstCharacter() {
    StringReader rd = new StringReader("q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    assertEquals(unshuffedBoard + "\nGame quit prematurely.\n", ap.toString());
  }
  
  @Test
  public void testQuitFirstCharacterUpperCase() {
    StringReader rd = new StringReader("Q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    assertEquals(unshuffedBoard + "\nGame quit prematurely.\n", ap.toString());
  }
  
  @Test
  public void testQuitAfterSourcePileInput() {
    StringReader rd = new StringReader("C1 Q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    assertEquals(unshuffedBoard + "\nGame quit prematurely.\n", ap.toString());
  }
  
  @Test
  public void testQuitAfterSourceCardInput() {
    StringReader rd = new StringReader("C1 2 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    assertEquals(unshuffedBoard + "\nGame quit prematurely.\n", ap.toString());
  }
  
  @Test
  public void testQuitAfterOneMove() {
    StringReader rd = new StringReader("C1 13 O1 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    
    assertEquals(unshuffedBoard + cascadeToOpenupdatedBoard
        + "\nGame quit prematurely.\n", ap.toString());
  }
  
  @Test
  public void testInvalidPileSourceStringReader() {
    StringReader rd = new StringReader("A1 13 O1 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    String expectedap = unshuffedBoard + "\nInvalid move, try again.\n" + unshuffedBoard
          + "\nInvalid move, try again.\n" + unshuffedBoard + "\nGame quit prematurely.\n";
    
    
    assertEquals(expectedap, ap.toString());
  }
  
  @Test
  public void testInvalidCardIndexStringReader() {
    StringReader rd = new StringReader("A1 16 O1 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    String expectedap = unshuffedBoard + "\nInvalid move, try again.\n" + unshuffedBoard
        + "\nInvalid move, try again.\n" + unshuffedBoard + "\nGame quit prematurely.\n";
    
    
    assertEquals(expectedap, ap.toString());
  }
  
  @Test
  public void testInvalidDestinationPileStringReader() {
    StringReader rd = new StringReader("A1 16 O12 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    String expectedap = unshuffedBoard + "\nInvalid move, try again.\n" + unshuffedBoard
        + "\nInvalid move, try again.\n" + unshuffedBoard + "\nGame quit prematurely.\n";
    
    
    assertEquals(expectedap, ap.toString());
  }
  
  @Test
  public void testInvalidMoveToFoundationPile() {
    StringReader rd = new StringReader("C1 13 F2 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    String expectedap = unshuffedBoard + "\nInvalid move, try again.\n"
        + unshuffedBoard + "\nGame quit prematurely.\n";
    
    assertEquals(expectedap, ap.toString());
  }
  
  @Test
  public void testInvalidMoveToCascadePile() {
    StringReader rd = new StringReader("C1 13 C3 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    String expectedap = unshuffedBoard + "\nInvalid move, try again.\n"
        + unshuffedBoard + "\nGame quit prematurely.\n";
    
    assertEquals(expectedap, ap.toString());
  }
  
  @Test
  public void testInvalidMoveToOpenPile() {
    StringReader rd = new StringReader("C1 13 O1 C2 13 O1 q");
    StringWriter ap = new StringWriter();
    FreecellController controller = new FreecellController(rd, ap);
    FreecellModel model = new FreecellModel();
    
    controller.playGame(model.getDeck(), model, 4, 2, false);
    
    String expectedap = unshuffedBoard
        + cascadeToOpenupdatedBoard + "\nInvalid move, try again.\n" + cascadeToOpenupdatedBoard
        + "\nGame quit prematurely.\n";
    
    assertEquals(expectedap, ap.toString());
  }
}
