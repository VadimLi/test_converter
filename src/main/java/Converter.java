import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Converter implements Runnable {

    private String titleVideo;

    private SyncQueue syncQueue;

    public Converter(final String titleVideo,
                     final SyncQueue syncQueue) {
        this.titleVideo = titleVideo;
        this.syncQueue = syncQueue;
    }

    public void run() {
        try {
            final RandomString randomString = new RandomString();
            String line;
            final String mp4File = "/youtube/" + titleVideo;
            final String mp3File = "/youtube/" + randomString.nextString() + ".mp3";

            System.out.println(mp4File);

            String cmd = "/youtube/ffmpeg-3.4.2-win64-static/bin/ffmpeg -i " + mp4File + " " + mp3File;
            final Process p = Runtime.getRuntime().exec(cmd);
            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            System.out.println(titleVideo);
            while ((line = in.readLine()) != null) {
                Thread.sleep(2000);
                syncQueue.convertVideo();
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Video converted successfully!");
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}



