import java.io.File;

public class Test {

    public static void main(String[] args)  {
        String link = "https://www.youtube.com/watch?v=rd6m-6l2xQQ";
        File out = new File("C:\\youtube\\we.FLV");
        new Thread(new Download(link, out)).start();
    }

}
