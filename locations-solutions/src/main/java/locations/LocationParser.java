package locations;

public class LocationParser {
    public Location Parse(String text){
    String[] attributes = text.split(",");
    return new Location(attributes[0],Double.parseDouble(attributes[1]),Double.parseDouble(attributes[2]));
    }

}

