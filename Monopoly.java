import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Monopoly {
    public static Player player1 = new Player("1");
    public static Player player2 = new Player("2");
    public static Banker banker = new Banker();
    public static ArrayList < Square > squares = new ArrayList < Square > ();
    private static PropertyJsonReader reader1 = new PropertyJsonReader();
    private static ListJsonReader reader2 = new ListJsonReader();
    private static Integer last;
    private static Integer lastDice;
    public static void Play(String arg){
        try (Scanner scan = new Scanner(new BufferedReader(new FileReader(arg)))){
            while(scan.hasNext()){
                if (scan.next().toLowerCase().equals("player")){
                    String[] inpt = scan.next().split(";");
                    Integer player = Integer.parseInt(inpt[0]);
                    Integer dice = Integer.parseInt(inpt[1]);
                    last = player;
                    lastDice = dice;
                    switch(player){
                        case 1:
                            if(player1.getWaitLaps() == 0){
                                player1.AddLocation(dice);
                                squares.get(player1.getLocation() - 1).Action(dice, 1);
                            }
                            else{
                                player1.SubWaitLaps();
                                Printer.jailTimer(player1, dice);
                            }
                            break;
                        case 2:
                            if(player2.getWaitLaps() == 0){
                                player2.AddLocation(dice);
                                squares.get(player2.getLocation() - 1).Action(dice, 2);
                            }
                            else{
                                player2.SubWaitLaps();
                                Printer.jailTimer(player2, dice);
                            }
                            break;                     
                    }
                    if(player1.getMoney() == 0 || player2.getMoney() == 0){
                        throw new NotEnoughMoneyException();
                    }
                }
                else{
                    Printer.show();
                }
            }
            Printer.show();
        } catch (NotEnoughMoneyException e) {
            if(last == 1 && player1.getMoney() == 0){
                Printer.show();
            }
            else if(last == 1 && player1.getMoney() == 0){
                Printer.show();
            }
            else{
                Printer.Bankrupt(last, lastDice);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }
    
}