import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Convert {

    public void transformVideoToAudio(String titleVideo) {
        try {
            RandomString randomString = new RandomString();
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



