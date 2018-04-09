import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class DownloadFromYoutube implements Runnable {

    private static final String PATH = "/youtube/";

    private static final String USER_AGENT = "Mozilla/4.76";

    private URL downloadURL;

    private final String urlVideo;

    private final String fileName;

    private SyncQueue syncQueue;

    public DownloadFromYoutube(final String urlVideo,
                               final String fileName,
                               final SyncQueue syncQueue) {
        this.urlVideo = urlVideo;
        this.fileName = fileName;
        this.syncQueue = syncQueue;
    }

    private StringBuilder downloadHTTPRequest(String urlVideo) throws IOException, InterruptedException {
        String response;
        final StringBuilder res = new StringBuilder();
        final StringBuilder refinedres = new StringBuilder();

        final URL url = new URL("https://www.youtube.com/get_video_info?&video_id=" + getVideoID(urlVideo));
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (" +
                        "KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        conn.setRequestMethod("GET");
        conn.addRequestProperty("User-Agent", USER_AGENT);

        final int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            System.out.println(conn.getResponseMessage());
            System.out.println("message : " + conn.getResponseMessage());
            final BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((response = in .readLine()) != null)  {
                System.out.println(response);
                res.append(response).append("\n");
            }
            in.close();
            refinedres.append(URLDecoder.decode(
                    URLDecoder.decode(res.toString(), "UTF-8"), "UTF-8"));
            return refinedres;
        }
        return null;
    }

    private String getVideoID(String url) {
        int index = url.indexOf("v=");
        index += 2;
        StringBuilder id= new StringBuilder();
        for (int i = index; i < url.length(); i++)
            id.append(url.charAt(i));
        return id.toString();
    }

    private String getURLS(StringBuilder response) {
        StringBuilder temp1 = new StringBuilder();
        String[] temp2, temp3, temp4;
        try {
            int index = response.indexOf("url_encoded_fmt_stream_map");
            for (int i = index; i < response.length(); i++) {
                temp1.append(response.charAt(i));
            }
            temp2 = temp1.toString().split("&url=");
            if (temp2.length > 0) {
                temp3 = temp2[1].split(";");
                if (temp3.length > 0) {
                    temp4 = temp3[0].split(",");
                    if (temp4.length > 0) return temp4[0];
                    else return temp3[0];
                } else return temp2[1];
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void run() {
        StringBuilder builderRequest = null;
        try {
            builderRequest = downloadHTTPRequest(urlVideo);
            String urls = getURLS(builderRequest);
            downloadURL = new URL(urls);
            int count;
            try ( final BufferedInputStream bis =
                          new BufferedInputStream(downloadURL.openStream() );
                  final FileOutputStream fos =
                          new FileOutputStream(new File(PATH + fileName))) {
                final byte[] data = new byte[4096];
                int i = 0;
                while ((count = bis.read(data)) != -1) {
                    syncQueue.downloadVideo(i++);
                    Thread.sleep(100);
                    System.out.println(String.valueOf(count));
                    fos.write(data, 0, count);
                }
                System.out.println("finish stream");
                fos.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
