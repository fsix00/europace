import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Europace {

    private Boolean checkIfObjNull(JsonObject objToBeChecked){
        boolean isNull = false;

        Set<String> keys = objToBeChecked.keySet();
        for (String key : keys){
            if (objToBeChecked.get(key).isJsonNull()){
                isNull = true;
            }
        }

        return isNull;
    }

    // parses json file and extracts all data .. returns list with all prices
    public List<Prices> parseJsonFile(String pathToFile){
        File input = new File(pathToFile);

        // using google's gson library to work with json file
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray jsonArrayOfPrices = fileObject.get("prices").getAsJsonArray();

            List<Prices> listOfPrices = new ArrayList<>(); // list to store all prices

            // process all prices
            for (JsonElement price : jsonArrayOfPrices){
                //get the jsonobject
                JsonObject priceJsonObject = price.getAsJsonObject();

                //check for null value
                if (checkIfObjNull(priceJsonObject)) continue;

                ///* extract all data *///

                // convert epoch to date
                Date date = new Date(priceJsonObject.get("date").getAsLong() * 1000 );

                float open = priceJsonObject.get("open").getAsInt();
                float high = priceJsonObject.get("high").getAsFloat();
                float low = priceJsonObject.get("low").getAsFloat();
                float close = priceJsonObject.get("close").getAsFloat();
                float volume = priceJsonObject.get("volume").getAsFloat();
                float adjclose = priceJsonObject.get("adjclose").getAsFloat();

                // add price to list
                Prices p = new Prices(date,open,high,low,close,volume,adjclose);
                listOfPrices.add(p);
            }

            return listOfPrices;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // if something went wrong return null
        return null;
    }

    public Date getLowestPriceDay(List<Prices> allPrices){

        // start by saying the first day has the lowest price
        float lowestPrice = allPrices.get(0).getLow();
        Date lowestPriceDay = allPrices.get(0).getDate();

        //go through all prices ..
        for (Prices price : allPrices){
            // if price is lower than current lowest price -> set as new lowest price
            if (price.getLow() < lowestPrice){
                lowestPrice = price.getLow();
                lowestPriceDay = price.getDate();
            }
        }

        return lowestPriceDay;

    }

    public Date getHighestPriceDay(List<Prices> allPrices){

        // start by saying the first day has the highest price
        float highestPrice = allPrices.get(0).getHigh();
        Date highestPriceDay = allPrices.get(0).getDate();

        // go through all prices
        for (Prices price : allPrices){
            // if price is higher than current highest price -> set as new highest price
            if (price.getHigh() > highestPrice){
                highestPrice = price.getHigh();
                highestPriceDay = price.getDate();
            }
        }

        return highestPriceDay;
    }

    public Date getHighestDifference(List<Prices> allPrices){

        // start by saying the first day hast the highest Difference
        float highestDifference = Math.abs(allPrices.get(0).getOpen() - allPrices.get(0).getClose());
        Date highestPriceDay = allPrices.get(0).getDate();

        for (Prices price : allPrices){
            // if difference is higher than current highest difference -> set as new highest difference
            float diff = Math.abs(price.getOpen() - price.getClose());
            if (diff > highestDifference){
                highestDifference = diff;
                highestPriceDay = price.getDate();
            }
        }

        return highestPriceDay;
    }

    public float getAverageClosingPrice(List<Prices> allPrices){
        float sumOfClosingPrices = 0;

        for (Prices price : allPrices){
            sumOfClosingPrices += price.getClose();
        }

        return sumOfClosingPrices / allPrices.size();
    }




    public static void main(String[]args){
        Europace europace = new Europace();
        String userDirectory = new File("hypoport.json").getAbsolutePath();

        List<Prices> allPrices = europace.parseJsonFile(userDirectory);

        assert allPrices != null;

        System.out.println("Tag mit dem niedrigsten Kurs: " + europace.getLowestPriceDay(allPrices) +"\n");


        System.out.println("Tag mit dem hoechsten Kurs: " + europace.getHighestPriceDay(allPrices) + "\n");


        System.out.println("Tag mit dem hoechsten Unterschied zwischen Eroeffnungs- und Schlusskurs: " + europace.getHighestDifference(allPrices) + "\n");


        System.out.println("Durchschnittlicher Schlusskurs der Aktie: " + europace.getAverageClosingPrice(allPrices) + "\n");




    }
}
