import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {
    JLabel label;

    public MyThread(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
//        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
        while (true) {
            Date date = new Date();
//            Lay gio
//            String str = time.format(date);
//            label.setText(str);
            label.setText(date.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
