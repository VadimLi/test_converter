import com.github.axet.vget.VGet;
import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.info.VideoInfo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadFromYoutube implements Runnable {

    private static final String path = "/youtube/";

    private VGet vGet;

    private final RandomString randomString;

    private String titleVideo;

    public DownloadFromYoutube(final String urlVideo)
            throws MalformedURLException {
        randomString = new RandomString();
        final AtomicBoolean stop = new AtomicBoolean(false);
        final URL web = new URL(urlVideo);
        final VGetParser user = VGet.parser(web);
        final VideoInfo videoinfo = user.info(web);
        vGet = new VGet(videoinfo, new File(path));
        titleVideo = randomString.nextString();
    }

    public void run() {
        try {
            vGet.getVideo().setTitle("sd");
            vGet.download();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTitle() {
        return titleVideo;
    }

//    public void renameVideoFile() throws InterruptedException {
//        final RandomString randomString = new RandomString();
//        final File oldFile = new File(getFullPath(path,
//                vGet.getVideo().getTitle(), ".mp4"));
//        final File newFile = new File(getFullPath(path,
//                randomString.nextString(), ".mp4"));
//        if ( oldFile.renameTo(newFile) ) {
//            System.out.println("Rename successfully");
//        } else {
//            System.out.println("Rename failed");
//        }
//    }
//
//    private String getFullPath(final String dir,
//                                 final String title,
//                                 final String format) {
//        StringBuilder fullPath = new StringBuilder();
//        fullPath.append(dir)
//                .append(title)
//                .append(format);
//        return String.valueOf(fullPath);
//    }

}
