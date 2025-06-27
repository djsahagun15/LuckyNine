public class Card {
    private String suit;
    private String rank;

    private int value;

    public Card(String suit, String rank) {
        switch (suit) {
            case "hearts" -> { this.suit = "\u2665"; }
            case "diamonds" -> { this.suit = "\u2666"; }
            case "clubs" -> { this.suit = "\u2663"; }
            case "spades" -> { this.suit = "\u2660"; }
            default -> { this.suit = "?"; }
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