import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


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
                enterOnly();

                continue;
            }

            System.out.print("\nThe game is ready! Press ENTER to start playing Lucky Nine!");
            enterOnly();

            break;
        }

        this.gameLoop();
    }

    private void deal() {
        this.player.clearHand();
        for (Player p : this.opponents) p.clearHand();

        this.player.drawCard(this.deck.draw());
        this.player.drawCard(this.deck.draw());

        for (Player p : this.opponents) {
            p.drawCard(this.deck.draw());
            p.drawCard(this.deck.draw());
        }
    }

    private Player tieBreaker(List<Player> possibleWinners) {
        if (possibleWinners.size() == 1) return possibleWinners.get(0);

        clearScreen();
        System.out.println("Lucky Nine\n");
        
        List<Player> next = new ArrayList<>();

        for (Player p : possibleWinners) {
            p.clearHand();
            p.drawCard(this.deck.draw());
        }

        int highest = 0;
        for (Player p : possibleWinners) {
            int handValue = p.getHandValue();
            if (handValue == highest) {
                next.add(p);
            } else if (handValue > highest) {
                next.clear();
                next.add(p);

                highest = handValue;
            }
        }

        if (possibleWinners.contains(this.player)) {
            for (Player p : possibleWinners) {
                if (p == this.player) continue;
                System.out.printf("%-25s = %-3d%s\n", p.toString(), p.getHandValue(), next.contains(p) ? "--- TIE ---" : "");
            }
        
            if (next.contains(this.player)) {
                System.out.printf("\n%-25s = %-3d%s\n", this.player.toString(), this.player.getHandValue(), "--- TIE ---");
                System.out.print("\nDRAW! Press ENTER to draw another card...");
            } else {
                System.out.printf("\n%-25s = %-3d\n", this.player.toString(), this.player.getHandValue());
                System.out.print("\nYOU LOSE! Press ENTER to skip the round and see the winner...");
            }
            enterOnly();
        }

        return tieBreaker(next);
    }

    private Player getRoundWinner(Set<Player> nat9s) {
        if (nat9s.size() > 1) {
            for (Player p : this.opponents) {
                System.out.printf("%-25s = %-3d%s\n", p.toString(), p.getHandValue(), nat9s.contains(p) ? "--- TIE ---" : "");
            }
    
            System.out.printf("\n%-25s = %-3d%s\n", this.player.toString(), this.player.getHandValue(), nat9s.contains(this.player) ? "--- TIE ---" : "");

            if (nat9s.contains(this.player)) System.out.print("\nDRAW! Press ENTER to draw another card...");
            else System.out.print("\nYOU LOSE! Press ENTER to skip the round and see the winner...");

            enterOnly();

            return tieBreaker(new ArrayList<>(nat9s));
        }

        for (Player p : this.opponents) {
            System.out.printf("%-25s = %-3d%s\n", p.toString(), p.getHandValue(), nat9s.contains(p) ? "<--- WINNER ---" : "");
        }

        System.out.printf("\n%-25s = %-3d%s\n", this.player.toString(), this.player.getHandValue(), nat9s.contains(this.player) ? "--- WINNER ---" : "");

        if (nat9s.contains(this.player)) System.out.print("\nDRAW! Press ENTER to draw another card...");
        else System.out.print("\nYOU LOSE! Press ENTER to start the next round...");
        

        return nat9s.iterator().next();
    }

    private Player getRoundWinner() {
        List<Player> possibleWinners = new ArrayList<>();
        possibleWinners.add(this.player);
        
        int highest = this.player.getHandValue();

        for (Player p : this.opponents) {
            int handValue = p.getHandValue();
            if (handValue == highest) {
                possibleWinners.add(p);
            } else if (handValue > highest) {
                possibleWinners.clear();
                possibleWinners.add(p);

                highest = handValue;
            }
        }

        Player winner = possibleWinners.iterator().next();
        if (possibleWinners.size() > 1) {
            for (Player p : this.opponents) {
                System.out.printf("%-25s = %-3d%s\n", p.toString(), p.getHandValue(), possibleWinners.contains(p) ? "--- TIE ---" : "");
            }
            
            if (possibleWinners.contains(this.player)) {
                System.out.printf("\n%-25s = %-3d%s\n", this.player.toString(), this.player.getHandValue(), "--- TIE ---");
                System.out.println("\nDRAW! Press ENTER to draw another card...");
            } else {
                System.out.printf("\n%-25s = %-3d\n", this.player.toString(), this.player.getHandValue());
                System.out.println("\nYOU LOSE! Press ENTER to skip the round and see the winner...");
            }
            enterOnly();
            
            winner = tieBreaker(possibleWinners);
        } else {
            for (Player p : this.opponents) {
                System.out.printf("%-25s = %-3d%s\n", p.toString(), p.getHandValue(), p == winner ? "<--- WINNER ---" : "");
            }

            System.out.printf("\n%-25s = %-3d\n", this.player.toString(), this.player.getHandValue());

            if (this.player != winner) {
                System.out.println("\nYOU LOSE! Press ENTER to continue...");
                enterOnly();
            }
        }
        
        return winner;
    }

    public void gameLoop() {
        boolean emptyHands = true;
        boolean isPlayerReady = false;

        Set<Player> nat9s = new HashSet<>();

        boolean shouldContinue = true;
        while (shouldContinue) {
            clearScreen();
            System.out.println("Lucky Nine\n");

            if (emptyHands) {
                deal();

                for (Player p : this.opponents) {
                    int handValue = p.getHandValue();
                    
                    if (handValue == 9) {
                        nat9s.add(p);
                    } else if (handValue < 7) {
                        p.drawCard(this.deck.draw());
                        System.out.printf("%s drew a card.\n", p.name);
                    } else {
                        System.out.printf("%s chose to play their card.\n", p.name);
                    }
                }

                if (this.player.getHandValue() == 9) {
                    nat9s.add(this.player);
                }

                emptyHands = false;
            }

            if (isPlayerReady) {
                Player winner;
                if (!nat9s.isEmpty()) winner = this.getRoundWinner(nat9s);
                else winner = this.getRoundWinner();


                if (this.player == winner) {
                    System.out.println("\nYOU WIN! Press ENTER to play again");
                } else {
                    System.out.printf("\n%s won this round!\n", winner.name);
                }

                emptyHands = true;
                isPlayerReady = false;

                nat9s.clear();

                boolean validInput = false;
                while (!validInput) {
                    System.out.print("Do you want to play again? [Y/n]: ");
                    String input = scan.nextLine().replaceAll("\\s+", "").toLowerCase();

                    switch (input) {
                        case "", "y" -> validInput = true;
                        case "n" -> { validInput = true; shouldContinue = false; }
                        default -> System.out.println("Invalid input.\n");
                    }
                }
                
                
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
                            nat9s.remove(this.player);
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

    private static void enterOnly() {
        try {
            while (true) {
                int input = System.in.read();
                if (input == '\n') break;
            }
        } catch (IOException e ) {}
    }
}
