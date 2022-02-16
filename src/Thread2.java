import java.text.SimpleDateFormat;
import java.util.Date;

public class Thread2 extends Thread {
    @Override
    public void run() {
        while (true) {
            Date date = new Date();
            System.out.println("" + date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

//
//    @Override
//    public void run() {
//        for (int i = 100; i >0; i--) {
//
//            try {
//                Thread.sleep(1000);
//                System.out.println(i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    public static void main(String[] args) {
        Thread2 thread2 = new Thread2();
        thread2.start();

    }
}
