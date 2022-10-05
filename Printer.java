import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    public static void printsmtng(String action,Integer playerNum,Integer dice){
        try (FileWriter writer = new FileWriter("output.txt", true)){            
            if(playerNum == 1){
                writer.write("Player1\t" + dice + "\t" + Monopoly.player1.getLocation() + "\t" + Monopoly.player1.getMoney() +
                "\t" + Monopoly.player2.getMoney() + "\t" + action + "\n");
            }
            else{
                writer.write("Player2\t" + dice + "\t" + Monopoly.player2.getLocation() + "\t" + Monopoly.player1.getMoney() +
                "\t" + Monopoly.player2.getMoney() + "\t" + action + "\n");
            }
        } catch (IOException e) {
            //TODO: handle exception
        }     
    }
    public static void show(){
        try (FileWriter writer = new FileWriter("output.txt", true)){
            writer.write("-----------------------------------------------------------------------------------------------------------\n");
            writer.write("Player1\t" + Monopoly.player1.getMoney() + " have: ");
            for (String square: Monopoly.player1.getOwnedLands()) {
                writer.write(square + " ");
            }
            writer.write("\nPlayer 2" + "\t" + Monopoly.player2.getMoney() + " have: ");
            for (String square: Monopoly.player2.getOwnedLands()) {
                writer.write(square + " ");
            }
            writer.write("\nBanker\t" + Monopoly.banker.getMoney() + "\n");
            if (Monopoly.player1.getMoney() > Monopoly.player2.getMoney()) {
                writer.write("Winner Player 1\n");
            } else if (Monopoly.player1.getMoney() < Monopoly.player2.getMoney()) {
                writer.write("Winner\tPlayer 2\n");
            } else {
                writer.write("Draw\n");
            }
            writer.write("-----------------------------------------------------------------------------------------------------------\n");
        } catch (IOException e) {
            //TODO: handle exception
        }     
    }
    public static void Bankrupt(Integer last,Integer dice){
        Printer.printsmtng("Player " + last + " has gone bankrupt", last, dice);
        Printer.show();
    }
    public static void jailTimer(Player player,Integer dice){
        try (FileWriter writer = new FileWriter("output.txt", true)){
            writer.write(player +"\t" + dice + "\t" + player.getLocation() + "\t" + Monopoly.player1.getMoney() +
            "\t" + Monopoly.player2.getMoney() + "\t" + player +" in jail (count=" + (3 - player.getWaitLaps()) + ")\n");
        }
        catch (IOException e){

        }
    }
}
