import java.util.ArrayList;

public abstract class User {
    private String name;
    private Integer money;
    private ArrayList < String > ownedLands = new ArrayList < String > ();
    private Integer waitLaps = 0;   
    public Integer getWaitLaps() {
        return waitLaps;
    }
    public void AddWaitLaps(Integer waitLaps) {
        this.waitLaps += waitLaps;
    }
    public void SubWaitLaps() {
        this.waitLaps--;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getMoney() {
        return money;
    }
    public void SubMoney(Integer money) throws NotEnoughMoneyException {
        if(this.money - money < 0){
            throw new NotEnoughMoneyException();
        }
        else {
            this.money -= money;
        }
    }
    public void AddMoney(Integer money) {
        this.money += money;
    }
    public void SetMoney(Integer money){
        this.money = money;
    }
    public ArrayList < String > getOwnedLands() {
        return ownedLands;
    }
    public void AddOwnedLands(String ownedLands) {
        this.ownedLands.add(ownedLands);
    }
    public String toString(){
        return "Player "+ this.name; //Names are numbers like 1 or 2
    }
}
class Player extends User {
    private Integer location;
    
    public Player(String name) {
        this.setName(name);
        this.SetMoney(15000);
        this.location = 1;
    }
    public Integer getLocation() {
        return location;
    }
    public void SubLocation(Integer location) {
        if(this.location - location <= 0){
            this.location -= location;
            this.location += 40;
        }
        else{
            this.location -= location;
        }       
    }
    public void AddLocation(Integer location) throws NotEnoughMoneyException{
        if(this.location + location > 40){
            this.location += location;
            this.location -= 40;
            this.AddMoney(200);
            Monopoly.banker.SubMoney(200);
        }
        else{
            this.location += location;
        }
    }
    public void setLocation(Integer location) {
        this.location = location;
    }
}
class Banker extends User{
    public Banker() {
        this.setName("Banker");
        this.SetMoney(100000);      
    }    
}
