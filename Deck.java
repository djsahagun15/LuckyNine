import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Random;


public class Deck {
    private final Random random = new Random();
    
    private static class Pair {
        public final Card card;
        public Boolean drawn;
        
        public Pair(Card card, Boolean drawn) {
            this.card = card;
            this.drawn = drawn;
        }
    }

    private final List<Pair> cards;

    public Deck() {
        String[] suits = { "hearts", "diamonds", "clubs", "spades" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
        
        this.cards = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                this.cards.add(new Pair(new Card(suit, rank), false));
            }
        }
    }

    public void shuffle() {
        for (Pair p : cards) {
            p.drawn = false;
        }

        Collections.shuffle(this.cards);
    }

    public Card draw() {
        int iter = 0;

        while (iter < 52) {
            int index = random.nextInt(this.cards.size());
            
            Pair pair = this.cards.get(index);
            if (!pair.drawn) {
                pair.drawn = true;
                return pair.card;
            }

            ++iter;
        }

        return null;
    }
}