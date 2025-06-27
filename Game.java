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
        System.out.println("What's your name?: ");
        String name = scan.nextLine();

        this.player = new Player(name);

        while (true) {
            clearScreen();
            
            System.out.printf("Hello, %s! How many AI opponents would you like to face? [1-9]: ", this.player.name);
            String input = scan.nextLine();

            try {
                int count = Integer.parseInt(input);

                this.opponents = new ArrayList<>();
                
                for (int n = 0; n < count; ++n) {
                    name = "Computer #" + (n + 1);
                    this.opponents.add(new Player(name));
                    
                    System.out.println(name + " created");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid: the input must be a non-negative integer between 1 and 9");
                System.out.print("Press ENTER to try again...");
                
                try {
                    enterOnly();
                } catch (IOException ex) {}

                continue;
            }

            System.out.print("The game is ready! Press ENTER to start playing Lucky Nine!");
            
            try {
                enterOnly();
            } catch (IOException ex) {}

            break;
        }

        this.gameLoop();
    }

    public void gameLoop() {
        // TODO: Implement the main game loop and the core game logic
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
