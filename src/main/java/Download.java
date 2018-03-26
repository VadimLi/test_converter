import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download implements Runnable {

    private String link;

    private File out;

    public Download(String link, File out) {
        this.link = link;
        this.out = out;
    }

    public void run() {
        try {
            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            double fileSize = http.getContentLength();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 4096);
            byte[] buffer = new byte[4096];
            double downloaded = 0.00;
            int read = 0;
            double percentDownloaded = 0.00;
            while ( (read = in.read(buffer, 0, 4096)) != -1) {
                bout.write(buffer, 0, read);
                downloaded += read;
                percentDownloaded = (downloaded * 100) / fileSize;
                String percent = String.format("%.4f", percentDownloaded);
                System.out.println("Downloaded " + percent + "% of a file.");
            }
            bout.close();
            in.close();
            System.out.println("Downloaded completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
