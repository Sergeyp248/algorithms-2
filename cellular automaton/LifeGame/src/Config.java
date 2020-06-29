import java.awt.*;

public class Config {
    public static final int SIZE = 15;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int SLEEP_MS = 100;

    public static Color getColor(Status status) {
        switch (status) {
            default:
            case NONE:
                return Color.WHITE;
            case BORN:
                return Color.GRAY;
            case LIVE:
                return Color.GRAY;
            case DIED:
                return Color.WHITE;
        }
    }
}