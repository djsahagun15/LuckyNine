public class Card {
    private char suit;
    private String rank;

    private int value;

    public Card(String suit, String rank) {
        switch (suit) {
            case "hearts" -> { this.suit = '♥'; }
            case "diamonds" -> { this.suit = '♦'; }
            case "clubs" -> { this.suit = '♣'; }
            case "spades" -> { this.suit = '♠'; }
            default -> { this.suit = '?'; }
        }

        switch (rank) {
            case "Ace" -> {
                this.rank = "A";
                this.value = 1;
            }
            case "Jack", "Queen", "King" -> {
                this.rank = "" + rank.charAt(0);
                this.value = 0;
            }
            case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> {
                this.rank = rank;
                this.value = Integer.parseInt(rank);
            }
            default -> {
                this.rank = "?";
                this.value = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "" + this.rank + this.suit;
    }

    public int getValue() {
        return this.value;
    }
}