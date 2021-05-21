import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EuropaceTest {
    Europace europace = new Europace();
    String userDirectory = new File("hypoport.json").getAbsolutePath();
    List<Prices> allPrices = europace.parseJsonFile(userDirectory);

    private void assertPriceIsLowest(float price){
        // checks if lower price exists
        boolean isLowest = true;
        for (Prices p : allPrices){
            if (p.getLow() < price) {
                isLowest = false;
                break;
            }
        }
        assertTrue(isLowest);
    }

    private void assertPriceIsHighest(float price){
        // checks if higher price exists
        boolean isHighest = true;
        for (Prices p : allPrices){
            if (p.getHigh() > price) {
                isHighest = false;
                break;
            }
        }
        assertTrue(isHighest);
    }

    private void assertDifferenceIsHighest(float difference) {
        // checks if higher price difference exists
        boolean isHighest = true;
        for (Prices p: allPrices){
            if (Math.abs(p.getOpen() - p.getClose()) > difference) {
                isHighest = false;
                break;
            }
        }
        assertTrue(isHighest);
    }

    @org.junit.jupiter.api.Test
    void getLowestPriceDay() {
        assert allPrices != null;
        Date lowestPriceDay = europace.getLowestPriceDay(allPrices);
        float lowestPrice = 0;
        for (Prices pr : allPrices){
            if (pr.getDate().equals(lowestPriceDay)){
                lowestPrice = pr.getLow();
            }
        }
        assertPriceIsLowest(lowestPrice);

    }

    @org.junit.jupiter.api.Test
    void getHighestPriceDay() {
        assert allPrices != null;
        Date highestPriceDay = europace.getHighestPriceDay(allPrices);
        float highestPrice = 0;
        for (Prices pr : allPrices){
            if (pr.getDate().equals(highestPriceDay)){
                highestPrice = pr.getHigh();
            }
        }
        assertPriceIsHighest(highestPrice);

    }

    @org.junit.jupiter.api.Test
    void getHighestDifference() {
        assert allPrices != null;
        Date highestDifferenceDay = europace.getHighestDifference(allPrices);
        float highestDifference = 0;
        for (Prices pr : allPrices){
            if (pr.getDate().equals(highestDifferenceDay)){
                highestDifference = Math.abs(pr.getOpen()-pr.getClose());
            }
        }
        assertDifferenceIsHighest(highestDifference);
    }

    @org.junit.jupiter.api.Test
    void getAverageClosingPrice() {
        assert allPrices != null;
        float sum = 0;
        for (Prices p: allPrices){
            sum += p.getClose();
        }
        assertEquals(sum/allPrices.size(),europace.getAverageClosingPrice(allPrices));
    }
}