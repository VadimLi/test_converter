import java.net.MalformedURLException;

public class Test {

    public static void main(String[] args)
            throws MalformedURLException, InterruptedException {
        final DownloadFromYoutube downloadFromYoutube =
                new DownloadFromYoutube("https://www.youtube.com/watch?v=PTYXsSsFrG8");
        new Thread(downloadFromYoutube).start();
        System.out.println(downloadFromYoutube.getTitle());
    }

}
