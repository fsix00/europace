import java.util.Date;

public class Prices {
    private final Date date;
    private final float open;
    private final float high;
    private final float low;
    private final float close;
    private final float volume;
    private final float adjclose;

    public Prices(Date date, float open, float high, float low, float close, float volume, float adjclose) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjclose = adjclose;
    }

    public Date getDate() {
        return date;
    }

    public float getOpen() {
        return open;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getClose() {
        return close;
    }

    public float getVolume() {
        return volume;
    }

    public float getAdjclose() {
        return adjclose;
    }

    // good for debugging
    @Override
    public String toString() {
        return "Prices{" +
                "date=" + date +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", adjclose=" + adjclose +
                '}';
    }
}
