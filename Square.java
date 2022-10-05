import java.util.ArrayList;
public abstract class Square {
    private Integer id;
    private String name;
    private Integer cost;
    private boolean Buyable;
    public Square(Integer id, String name, Integer cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
    public boolean isBuyable() {
        return Buyable;
    }
    public void setBuyable(boolean buyable) {
        Buyable = buyable;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getCost() {
        return cost;
    }
    public abstract void Action(Integer dice, Integer playerNum) throws NotEnoughMoneyException;
    public String toString() {
        return this.getName();
    }
}
class Land extends Square {
    private Integer rent;
    public Land(Integer id, String name, Integer cost) {
        super(id, name, cost);
        this.setBuyable(true);
    }
    public Integer getRent() {
        return rent;
    }
    public void setRent(Integer rent){
        this.rent = rent;
    }
    private void calculateRent(Integer dice) {
        if (this.getCost() < 2001) {
            this.rent = (int) Math.round(this.getCost() * 40 / 100);
        } else if (this.getCost() < 3001) {
            this.rent = (int) Math.round(this.getCost() * 30 / 100);
        } else {
            this.rent = (int) Math.round(this.getCost() * 35 / 100);
        }
    }
    @Override
    public void Action(Integer dice, Integer playerNum) throws NotEnoughMoneyException {
        this.calculateRent(dice);
        switch (playerNum) {
            case 1:
                if (this.isBuyable()) {
                    if (Monopoly.player1.getMoney() >= this.getCost()) {
                        Monopoly.player1.SubMoney(this.getCost());
                        Monopoly.banker.AddMoney(this.getCost());
                        Monopoly.player1.AddOwnedLands(this.getName());
                        this.setBuyable(false);
                        Printer.printsmtng("Player 1 bought " + this.getName(),1,dice);
                    } else {
                        throw new NotEnoughMoneyException();
                    }
                } else if (Monopoly.player1.getOwnedLands().contains(this.getName())) {
                    Printer.printsmtng("Player 1 has " + this.getName(),1,dice);
                } else if (Monopoly.player2.getOwnedLands().contains(this.getName())) {
                    Monopoly.player1.SubMoney(this.rent);
                    Monopoly.player2.AddMoney(this.rent);
                    Printer.printsmtng("Player 1 paid rent for " + this.getName(),1,dice);
                } else {
                    throw new NotEnoughMoneyException();
                }
                break;
            case 2:
                if (this.isBuyable()) {
                    if (Monopoly.player2.getMoney() >= this.getCost()) {
                        Monopoly.player2.SubMoney(this.getCost());
                        Monopoly.banker.AddMoney(this.getCost());
                        Monopoly.player2.AddOwnedLands(this.getName());
                        this.setBuyable(false);
                        Printer.printsmtng("Player 2 bought " + this.getName(),2,dice);
                    } else {
                        throw new NotEnoughMoneyException();
                    }
                } else if (Monopoly.player2.getOwnedLands().contains(this.getName())) {
                    Printer.printsmtng("Player 2 has " + this.getName(),2,dice);
                } else if (Monopoly.player1.getOwnedLands().contains(this.getName())) {
                    Monopoly.player2.SubMoney(this.rent);
                    Monopoly.player1.AddMoney(this.rent);
                    Printer.printsmtng("Player 2 paid rent for " + this.getName(),2,dice);
                } else {
                    throw new NotEnoughMoneyException();
                }
                break;
            default:
                break;
        }
    }
}
class Chance extends Square {
    private static Integer count = 0;
    public static ArrayList < String > Chancelist = new ArrayList < String > ();

    public Chance(Integer id, String name) {
        super(id, name, 0);
        this.setBuyable(false);
    }

    @Override
    public void Action(Integer dice, Integer playerNum) throws NotEnoughMoneyException {
        if (playerNum == 1) {
            switch (count) {
                case 0:
                    Monopoly.player1.setLocation(1);
                    Monopoly.player1.AddMoney(200);
                    count++;
                    Monopoly.banker.SubMoney(200);
                    Printer.printsmtng("Player 1 draw " + Chancelist.get(0),1,dice);
                    break;
                case 1:
                    count++;
                    if (Monopoly.player1.getLocation() > 27) {
                        Monopoly.player1.AddMoney(200);
                    }
                    Monopoly.player1.setLocation(27);
                    Square lnd = Monopoly.squares.get(26);
                    if (lnd.isBuyable()) {
                        Monopoly.player1.SubMoney(lnd.getCost());
                        Monopoly.banker.AddMoney(lnd.getCost());
                        lnd.setBuyable(false);
                        Printer.printsmtng("Player 1 draw " + Chancelist.get(1) + " Player 1 bought " + lnd.getName(),1,dice);
                    } else {
                        if (Monopoly.player1.getOwnedLands().contains(lnd.getName())) {
                            Printer.printsmtng("Player 1 draw " + Chancelist.get(1) + " Player 1 has " + lnd.getName(),1,dice);
                        } else {
                            Land tmp = (Land) lnd;
                            Monopoly.player1.SubMoney(tmp.getRent());
                            Monopoly.player2.AddMoney(tmp.getRent());
                            Printer.printsmtng("Player 1 draw " + Chancelist.get(1) + " Player 1 paid rent " + lnd.getName(),1,dice);
                        }
                    }
                    break;
                case 2:
                    count++;
                    Monopoly.player1.SubLocation(3);
                    Square lnd2 = Monopoly.squares.get(Monopoly.player1.getLocation() - 1);
                    if (lnd2.isBuyable()) {
                        Monopoly.player1.SubMoney(lnd2.getCost());
                        Monopoly.banker.AddMoney(lnd2.getCost());
                        lnd2.setBuyable(false);
                        Printer.printsmtng("Player 1 draw " + Chancelist.get(2) + " Player 1 bought " + lnd2.getName(),1,dice);
                    } else {
                        if (Monopoly.player1.getOwnedLands().contains(lnd2.getName())) {
                            Printer.printsmtng("Player 1 draw " + Chancelist.get(2) + " Player 1 has " + lnd2.getName(),1,dice);
                        } else {
                            Land tmp2 = (Land) lnd2;
                            Monopoly.player1.SubMoney(tmp2.getRent());
                            Monopoly.player2.AddMoney(tmp2.getRent());
                            Printer.printsmtng("Player 1 draw " + Chancelist.get(2) + " Player 1 paid rent " + lnd2.getName(),1,dice);
                        }
                    }
                    break;
                case 3:
                    Monopoly.player1.SubMoney(15);
                    Monopoly.banker.AddMoney(15);
                    count++;
                    Printer.printsmtng("Player 1 draw " + Chancelist.get(3),1,dice);
                    break;
                case 4:
                    Monopoly.player1.AddMoney(150);
                    Monopoly.banker.SubMoney(150);
                    count++;
                    Printer.printsmtng("Player 1 draw " + Chancelist.get(4),1,dice);
                case 5:
                    Monopoly.player1.AddMoney(100);
                    Monopoly.banker.SubMoney(100);
                    count++;
                    Printer.printsmtng("Player 1 draw " + Chancelist.get(5),1,dice);
                default:
                    count = 0;
                    this.Action(dice, playerNum);
            }
        } else {
            switch (count) {
                case 0:
                    Monopoly.player2.setLocation(1);
                    Monopoly.player2.AddMoney(200);
                    count++;
                    Monopoly.banker.SubMoney(200);
                    Printer.printsmtng("Player 2 draw " + Chancelist.get(0),2,dice);
                    break;
                case 1:
                    count++;
                    if (Monopoly.player2.getLocation() > 27) {
                        Monopoly.player2.AddMoney(200);
                    }
                    Monopoly.player2.setLocation(27);
                    Square lnd = Monopoly.squares.get(26);
                    if (lnd.isBuyable()) {
                        Monopoly.player2.SubMoney(lnd.getCost());
                        Monopoly.banker.AddMoney(lnd.getCost());
                        lnd.setBuyable(false);
                        Printer.printsmtng("Player 2 draw " + Chancelist.get(1) + " Player 2 bought " + lnd.getName(),2,dice);
                    } else {
                        if (Monopoly.player2.getOwnedLands().contains(lnd.getName())) {
                            Printer.printsmtng("Player 2 draw " + Chancelist.get(1) + " Player 2 has " + lnd.getName(),2,dice);
                        } else {
                            Land tmp = (Land) lnd;
                            Monopoly.player2.SubMoney(tmp.getRent());
                            Monopoly.player1.AddMoney(tmp.getRent());
                            Printer.printsmtng("Player 2 draw " + Chancelist.get(1) + " Player 2 paid rent " + lnd.getName(),2,dice);
                        }
                    }
                    break;
                case 2:
                    count++;
                    Monopoly.player2.SubLocation(3);
                    Square lnd2 = Monopoly.squares.get(Monopoly.player2.getLocation() - 1);
                    if (lnd2.isBuyable()) {
                        Monopoly.player2.SubMoney(lnd2.getCost());
                        Monopoly.banker.AddMoney(lnd2.getCost());
                        lnd2.setBuyable(false);
                        Printer.printsmtng("Player 2 draw " + Chancelist.get(2) + " Player 2 bought " + lnd2.getName(),2,dice);
                    } else {
                        if (Monopoly.player2.getOwnedLands().contains(lnd2.getName())) {
                            Printer.printsmtng("Player 2 draw " + Chancelist.get(2) + " Player 2 has " + lnd2.getName(),2,dice);
                        } else {
                            Land tmp2 = (Land) lnd2;
                            Monopoly.player2.SubMoney(tmp2.getRent());
                            Monopoly.player1.AddMoney(tmp2.getRent());
                            Printer.printsmtng("Player 2 draw " + Chancelist.get(2) + " Player 2 paid rent " + lnd2.getName(),2,dice);
                        }
                    }
                    break;
                case 3:
                    Monopoly.player2.SubMoney(15);
                    Monopoly.banker.AddMoney(15);
                    count++;
                    Printer.printsmtng("Player 2 draw " + Chancelist.get(3),2,dice);
                    break;
                case 4:
                    Monopoly.player2.AddMoney(150);
                    Monopoly.banker.SubMoney(150);
                    count++;
                    Printer.printsmtng("Player 2 draw " + Chancelist.get(4),2,dice);
                    break;
                case 5:
                    Monopoly.player2.AddMoney(100);
                    Monopoly.banker.SubMoney(100);
                    count++;
                    Printer.printsmtng("Player 2 draw " + Chancelist.get(5),2,dice);
                    break;
                default:
                    count = 0;
                    this.Action(dice, playerNum);
                    break;
            }
        }

    }

}
class CommunityChest extends Square {
    public CommunityChest(Integer id, String name) {
        super(id, name, 0);
        //TODO Auto-generated constructor stub
    }
    public static ArrayList < String > CommunityChests = new ArrayList < String > ();
    private static Integer count = 0;
    @Override
    public void Action(Integer dice, Integer playerNum) throws NotEnoughMoneyException {
        if (playerNum == 1) {
            switch (count) {
                case 0:
                    Monopoly.player1.setLocation(1);
                    Monopoly.player1.AddMoney(200);
                    Monopoly.banker.SubMoney(200);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(0),1,dice);
                    break;
                case 1:
                    Monopoly.player1.AddMoney(75);
                    Monopoly.banker.SubMoney(75);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(1),1,dice);
                    break;
                case 2:
                    Monopoly.player1.SubMoney(50);
                    Monopoly.banker.AddMoney(50);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(2),1,dice);
                    break;
                case 3:
                    Monopoly.player2.SubMoney(10);
                    Monopoly.player1.AddMoney(10);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(3),1,dice);
                    break;
                case 4:
                    Monopoly.player2.SubMoney(50);
                    Monopoly.player1.AddMoney(50);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(4),1,dice);
                    break;
                case 5:
                    Monopoly.player1.AddMoney(20);
                    Monopoly.banker.SubMoney(20);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(5),1,dice);
                    break;
                case 6:
                    Monopoly.player1.AddMoney(100);
                    Monopoly.banker.SubMoney(100);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(6),1,dice);
                    break;
                case 7:
                    Monopoly.player1.SubMoney(100);
                    Monopoly.banker.AddMoney(100);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(7),1,dice);
                    break;
                case 8:
                    Monopoly.player1.SubMoney(50);
                    Monopoly.banker.AddMoney(50);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(8),1,dice);
                    break;
                case 9:
                    Monopoly.player1.AddMoney(100);
                    Monopoly.banker.SubMoney(100);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(9),1,dice);
                    break;
                case 10:
                    Monopoly.player1.AddMoney(50);
                    Monopoly.banker.SubMoney(50);
                    count++;
                    Printer.printsmtng("Player 1 draw " + CommunityChests.get(10),1,dice);
                    break;
                default:
                    count = 0;
                    this.Action(dice, playerNum);
                    break;
            }
        } else {
            switch (count) {
                case 0:
                    Monopoly.player2.setLocation(1);
                    Monopoly.player2.AddMoney(200);
                    Monopoly.banker.SubMoney(200);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(0),2,dice);
                    break;
                case 1:
                    Monopoly.player2.AddMoney(75);
                    Monopoly.banker.SubMoney(75);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(1),2,dice);
                    break;
                case 2:
                    Monopoly.player2.SubMoney(50);
                    Monopoly.banker.AddMoney(50);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(2),2,dice);
                    break;
                case 3:
                    Monopoly.player1.SubMoney(10);
                    Monopoly.player2.AddMoney(10);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(3),2,dice);
                    break;
                case 4:
                    Monopoly.player1.SubMoney(50);
                    Monopoly.player2.AddMoney(50);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(4),2,dice);
                    break;
                case 5:
                    Monopoly.player2.AddMoney(20);
                    Monopoly.banker.SubMoney(20);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(5),2,dice);
                    break;
                case 6:
                    Monopoly.player2.AddMoney(100);
                    Monopoly.banker.SubMoney(100);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(6),2,dice);
                    break;
                case 7:
                    Monopoly.player2.SubMoney(100);
                    Monopoly.banker.AddMoney(100);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(7),2,dice);
                    break;
                case 8:
                    Monopoly.player2.SubMoney(50);
                    Monopoly.banker.AddMoney(50);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(8),2,dice);
                    break;
                case 9:
                    Monopoly.player2.AddMoney(100);
                    Monopoly.banker.SubMoney(100);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(9),2,dice);
                    break;
                case 10:
                    Monopoly.player2.AddMoney(50);
                    Monopoly.banker.SubMoney(50);
                    count++;
                    Printer.printsmtng("Player 2 draw " + CommunityChests.get(10),2,dice);
                    break;
                default:
                    count = 0;
                    this.Action(dice, playerNum);
                    break;
            }

        }

    }
}
class SpecialPlace extends Square {
    public SpecialPlace(Integer id, String name) {
        super(id, name, 0);
        this.setBuyable(false);
    }
    public void Action(Integer dice, Integer playerNum) throws NotEnoughMoneyException {
        if (playerNum == 1) {
            switch (this.getId()) {
                case 1:
                    Printer.printsmtng("Player 1 is at Go",1,dice);
                    break;
                case 5:
                    Monopoly.banker.AddMoney(100);
                    Monopoly.player1.SubMoney(100);
                    Printer.printsmtng("Player 1 paid Tax",1,dice);
                    break;
                case 11:
                    Monopoly.player1.AddWaitLaps(3);
                    Printer.printsmtng("Player 1 went to Jail",1,dice);
                    break;
                case 21:
                    Monopoly.player1.AddWaitLaps(1);
                    Printer.printsmtng("Player 1 is in Free Parking",1,dice);
                    break;
                case 31:
                    Monopoly.player1.setLocation(11);
                    Monopoly.player1.AddWaitLaps(3);
                    Printer.printsmtng("Player 1 went to jail",1,dice);
                    break;
                case 39:
                    Monopoly.banker.AddMoney(100);
                    Monopoly.player1.SubMoney(100);
                    Printer.printsmtng("Player 1 paid Tax",1,dice);
                    break;
            }
        } else {
            switch (this.getId()) {
                case 1:
                    Printer.printsmtng("Player 2 is at Go",2,dice);
                    break;
                case 5:
                    Monopoly.banker.AddMoney(100);
                    Monopoly.player2.SubMoney(100);
                    Printer.printsmtng("Player 2 paid Tax",2,dice);
                    break;
                case 11:
                    Monopoly.player2.setLocation(11);
                    Monopoly.player2.AddWaitLaps(3);
                    Printer.printsmtng("Player 2 went to Jail",2,dice);
                    break;
                case 21:
                    Monopoly.player2.AddWaitLaps(1);
                    Printer.printsmtng("Player 2 is in Free Parking",2,dice);
                    break;
                case 31:
                    Monopoly.player2.setLocation(11);
                    Monopoly.player2.AddWaitLaps(3);
                    Printer.printsmtng("Player 2 went to jail",2,dice);
                    break;
                case 39:
                    Monopoly.banker.AddMoney(100);
                    Monopoly.player2.SubMoney(100);
                    Printer.printsmtng("Player 2 paid Tax",2,dice);
                    break;
            }
        }
    }
}
class Company extends Land {
    public Company(Integer id, String name, Integer cost) {
        super(id, name, cost);
        this.setBuyable(true);
    }
    public void calculateRent(Integer dice) {      
        this.setRent(this.getCost() * 4 * dice);        
    }
}
class Railroad extends Land {

    public Railroad(Integer id, String name, Integer cost) {
        super(id, name, cost);
        this.setBuyable(true);
    }

    public void calculateRent(Integer dice) {
        this.setRent(25*dice);
    }

}