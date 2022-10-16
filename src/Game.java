import java.util.Scanner;

public class Game {
    private Player player;

    public Game() {}

    public void start() {
        int flag = 1;
        Scanner sc = new Scanner(System.in);
        Farm farm = new Farm();
        
        System.out.print("[ENTER NAME] ");
        this.player = new Player(sc.nextLine(), farm);

        do {
            player.getFarm().displayFarm();
            player.displayInfo();

            System.out.println("\n ENTER LETTER IN BRACKETS");
            System.out.println(" [P]LOW  [B]UY AND PLANT TURNIP"); 
            System.out.println(" [W]ATER [F]ERTILIZE [H]ARVEST [A]DVANCE DAY\n ");

            switch(sc.nextLine()) {
                case "P":
                case "p":
                    
                    break;
                case "B":
                case "b":

                    break;
                case "A":
                case "a":
                    
                    break;
                case "end":
                    flag = 0;
                    break;
            }
        } while(flag != 0);

        sc.close();
    }
}
