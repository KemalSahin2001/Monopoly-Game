import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;



public class PropertyJsonReader {
	 
     public PropertyJsonReader(){
         JSONParser processor = new JSONParser();
         try (Reader file = new FileReader("property.json")){
             JSONObject jsonfile = (JSONObject) processor.parse(file);
             JSONArray Land = (JSONArray) jsonfile.get("1");
             for(Object i:Land){
				 
				 //You can reach items by using statements below:
                 Monopoly.squares.add(new Land(Integer.parseInt((String)((JSONObject)i).get("id")),
                 (String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost"))));                 
				 //And you can add these items to any data structure (e.g. array, linkedlist etc.
				 
				 
                 
             }
             JSONArray RailRoad = (JSONArray) jsonfile.get("2");
             for(Object i:RailRoad){
				 //You can reach items by using statements below:
                 Monopoly.squares.add(new Railroad(Integer.parseInt((String)((JSONObject)i).get("id")),
                 (String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")))); 
				//And you can add these items to any data structure (e.g. array, linkedlist etc.
             }
			 
             JSONArray Company = (JSONArray) jsonfile.get("3");
             for(Object i:Company){
				 //You can reach items by using statements below:
                 Monopoly.squares.add(new Company(Integer.parseInt((String)((JSONObject)i).get("id")),
                 (String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost"))));
             }
             
         } catch (IOException e){
             e.printStackTrace();
         } catch (ParseException e){
             e.printStackTrace();
         }
         Monopoly.squares.add(new SpecialPlace(1,"Go"));
         Monopoly.squares.add(new SpecialPlace(5,"Tax"));
         Monopoly.squares.add(new SpecialPlace(11,"Jail"));
         Monopoly.squares.add(new SpecialPlace(21,"Free Parking"));
         Monopoly.squares.add(new SpecialPlace(31,"went to Jail"));
         Monopoly.squares.add(new SpecialPlace(39,"Tax"));

         Monopoly.squares.add(new CommunityChest(3,"CChest3"));
         Monopoly.squares.add(new CommunityChest(18,"CChest18"));
         Monopoly.squares.add(new CommunityChest(34,"CChest34"));
         Monopoly.squares.add(new Chance(8,"chance3"));
         Monopoly.squares.add(new Chance(23,"chance23"));
         Monopoly.squares.add(new Chance(37,"chance37"));

         Collections.sort(Monopoly.squares, Comparator.comparing(Square::getId));
     }
     //You can add function(s) if you want
}