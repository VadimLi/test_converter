import java.io.IOException;

public class Test {

    private static final String URL_VIDEO = "https://www.youtube.com/watch?v=tyewJH0sen0";

    public static void main(String[] args)
            throws IOException, InterruptedException {
        final SyncQueue syncQueue = new SyncQueue();

        new Thread(new DownloadFromYoutube(URL_VIDEO, "1", syncQueue)).start();
        new Thread(new Converter("1", syncQueue)).start();

    }

}
