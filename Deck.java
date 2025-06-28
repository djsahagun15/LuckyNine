import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Deck {
    private final List<Card> cards;

    private int top;

    public Deck() {
        String[] suits = { "hearts", "diamonds", "clubs", "spades" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
        
        this.cards = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                this.cards.add(new Card(suit, rank));
            }
        }

        top = 0;

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public Card draw() {
        if (this.top == 51) {
            this.top = 0;
            this.shuffle();
        }
        
        Card card = this.cards.get(top++);

        return card;
    }
}