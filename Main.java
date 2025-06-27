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
        
        Game game = new Game();
        game.start();
    }
}
