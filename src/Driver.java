import java.util.Scanner;

/**
 * The Driver class.
 * This class initializes and starts the game.
 */
public class Driver {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game();

        boolean isQuit = false;

        do {
            game.initialize();
            game.start(sc);

            System.out.println("  [MESSAGE] Do you want to start another game? (yes or no)");
            System.out.print("  > ");

            if(sc.nextLine().equals("no")) {
                isQuit = true;
            }
        } while(!isQuit);

        System.out.print("  [MESSAGE] Program Terminated");
        sc.close();
    }
}