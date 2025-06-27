import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private static final Scanner scan = new Scanner(System.in);
    
    private final Deck deck;

    private List<Player> opponents;
    private Player player;
    
    public Game() {
        this.deck = new Deck();
    }

    public void start() {
        clearScreen();
        
        System.out.println("Welcome to Lucky Nine!");
        System.out.print("What's your name?: ");
        String name = scan.nextLine();

        this.player = new Player(name);

        while (true) {
            clearScreen();
            
            System.out.printf("Hello, %s! How many AI opponents would you like to face? [1-9]: ", this.player.name);
            String input = scan.nextLine();

            try {
                int count = Integer.parseInt(input);

                if (count <= 0 || count > 9) throw new Exception();

                this.opponents = new ArrayList<>();
                
                for (int n = 0; n < count; ++n) {
                    name = "Computer #" + (n + 1);
                    this.opponents.add(new Player(name));
                    
                    System.out.println(name + " created");
                }
            } catch (Exception e) {
                System.out.println("Invalid: the input must be a non-negative integer between 1 and 9");
                System.out.print("Press ENTER to try again...");
                
                try {
                    enterOnly();
                } catch (IOException ex) {}

                continue;
            }

            System.out.print("\nThe game is ready! Press ENTER to start playing Lucky Nine!");
            
            try {
                enterOnly();
            } catch (IOException ex) {}

            break;
        }

        this.gameLoop();
    }

    private void deal() {
        this.player.clearHand();
        for (Player p : this.opponents) p.clearHand();

        this.deck.shuffle();

        this.player.drawCard(this.deck.draw());
        this.player.drawCard(this.deck.draw());

        for (Player p : this.opponents) {
            p.drawCard(this.deck.draw());
            p.drawCard(this.deck.draw());
        }
    }

    public void gameLoop() {
        boolean emptyHands = true;
        boolean isPlayerReady = false;

        while (true) {
            clearScreen();
            System.out.println("Lucky Nine\n");

            if (emptyHands) {
                deal();

                for (Player p : this.opponents) {
                    int handValue = p.getHandValue();
                    if (handValue < 7) {
                        p.drawCard(this.deck.draw());
                        System.out.printf("%s drew a card.\n", p.name);
                    } else {
                        System.out.printf("%s chose to play their card.\n", p.name);
                    }
                }

                emptyHands = false;
            }

            if (isPlayerReady) {
                Player winner = this.player;
                int highest = this.player.getHandValue();

                for (Player p : this.opponents) {
                    int handValue = p.getHandValue();
                    if (handValue > highest) {
                        winner = p;
                        highest = handValue;
                    }
                }

                for (Player p : this.opponents) {
                    System.out.println(p.toString() + (p == winner ? "\t<--- WINNER ---" : ""));
                }
                
                if (this.player == winner) {
                    System.out.println('\n' + this.player.toString() + "\t<--- WINNER ---");
                    System.out.println("Value: " + this.player.getHandValue());
                    System.out.println("\nYOU WIN! Press ENTER to play again");
                } else {
                    System.out.println('\n' + this.player.toString());
                    System.out.println("Value: " + this.player.getHandValue());
                    System.out.println("\nYOU LOSE! Press ENTER to play again");
                }

                try {
                    enterOnly();
                } catch (IOException e) {}

                emptyHands = true;
                isPlayerReady = false;
            } else {
                System.out.println('\n' + this.player.toString());
                System.out.printf("Value: %d\n\n", this.player.getHandValue());
                
                try {
                    System.out.print("[1] Draw 1 more card\n[2] Play\nInput: ");
                    String input = scan.nextLine();
    
                    int selection = Integer.parseInt(input);
                    switch (selection) {
                        case 1 -> {
                            this.player.drawCard(this.deck.draw());
                            isPlayerReady = true;
                        }
                        case 2 -> isPlayerReady = true;
                        default -> throw new Exception();
                    }
                } catch (Exception ime) {}
            }
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void enterOnly() throws IOException {
        while (true) {
            int input = System.in.read();
            if (input == '\n') break;
        }
    }
}
