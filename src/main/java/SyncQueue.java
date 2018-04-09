public class SyncQueue {

    int value;

    private boolean filled = false;

    public synchronized void downloadVideo(int value) {
        while (filled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.value = value;
        filled = true;
        System.out.println("Отправлено: " + value);
        notify();
    }

    public synchronized int convertVideo() {
        while (!filled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        filled = false;
        System.out.println("Пoлyчeнo: " + value);
        notify();
        return value;
    }

}
