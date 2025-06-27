import java.util.ArrayList;
import java.util.List;


public class Player {
    public final String name;

    private final List<Card> hand;
    
    public Player(String name) { 
        this.name = name;

        this.hand = new ArrayList<>();
    }

    public void drawCard(Card card) {
        this.hand.add(card);
    }

    public void clearHand() {
        this.hand.clear();
    }

    @Override
    public String toString() {
        String handStr = "";
        for (Card card : this.hand) {
            handStr += card.toString() + " ";
        }

        return this.name + ": " + handStr;
    }
}