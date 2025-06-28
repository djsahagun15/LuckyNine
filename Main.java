import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (System.getProperty("os.name").contains("Windows")) {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "chcp 65001");

            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            pb.redirectError(ProcessBuilder.Redirect.DISCARD);

            pb.inheritIO().start().waitFor();
        
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        }
        
        System.out.println("\u001B[36m");
        System.out.println(" __         __  __     ______     __  __     __  __        __   __     __     __   __     ______    ");
        System.out.println("/\\ \\       /\\ \\/\\ \\   /\\  ___\\   /\\ \\/ /    /\\ \\_\\ \\      /\\ \"-.\\ \\   /\\ \\   /\\ \"-.\\ \\   /\\  ___\\   ");
        System.out.println("\\ \\ \\____  \\ \\ \\_\\ \\  \\ \\ \\____  \\ \\  _\"-.  \\ \\____ \\     \\ \\ \\-.  \\  \\ \\ \\  \\ \\ \\-.  \\  \\ \\  __\\   ");
        System.out.println(" \\ \\_____\\  \\ \\_____\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\/\\_____\\     \\ \\_\\\\\"\\_\\  \\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_____\\ ");
        System.out.println("  \\/_____/   \\/_____/   \\/_____/   \\/_/\\/_/   \\/_____/      \\/_/ \\/_/   \\/_/   \\/_/ \\/_/   \\/_____/ ");
        System.out.println("\u001B[0m");

        System.out.print("Game is starting...");
        Thread.sleep(2000);

        Game game = new Game();
        game.start();
    }
}
