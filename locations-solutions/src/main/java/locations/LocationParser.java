package locations;

public class LocationParser {
    public Location Parse(String text){
    String[] attributes = text.split(",");
    return new Location(attributes[0].trim(),
            Double.parseDouble(attributes[1].trim()),
            Double.parseDouble(attributes[2].trim()));
    }
}

