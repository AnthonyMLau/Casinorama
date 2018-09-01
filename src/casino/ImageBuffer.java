package casino;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

public class ImageBuffer {

    //<editor-fold defaultstate="collapsed" desc="declaring card image">
    //spades
    public static Image spadeA = null;
    public static Image spade2 = null;
    public static Image spade3 = null;
    public static Image spade4 = null;
    public static Image spade5 = null;
    public static Image spade6 = null;
    public static Image spade7 = null;
    public static Image spade8 = null;
    public static Image spade9 = null;
    public static Image spade10 = null;
    public static Image spadeJ = null;
    public static Image spadeQ = null;
    public static Image spadeK = null;
    //clubs
    public static Image clubsA = null;
    public static Image clubs2 = null;
    public static Image clubs3 = null;
    public static Image clubs4 = null;
    public static Image clubs5 = null;
    public static Image clubs6 = null;
    public static Image clubs7 = null;
    public static Image clubs8 = null;
    public static Image clubs9 = null;
    public static Image clubs10 = null;
    public static Image clubsJ = null;
    public static Image clubsQ = null;
    public static Image clubsK = null;
    //hearts
    public static Image heartA = null;
    public static Image heart2 = null;
    public static Image heart3 = null;
    public static Image heart4 = null;
    public static Image heart5 = null;
    public static Image heart6 = null;
    public static Image heart7 = null;
    public static Image heart8 = null;
    public static Image heart9 = null;
    public static Image heart10 = null;
    public static Image heartJ = null;
    public static Image heartQ = null;
    public static Image heartK = null;
    //diamonds
    public static Image diamondA = null;
    public static Image diamond2 = null;
    public static Image diamond3 = null;
    public static Image diamond4 = null;
    public static Image diamond5 = null;
    public static Image diamond6 = null;
    public static Image diamond7 = null;
    public static Image diamond8 = null;
    public static Image diamond9 = null;
    public static Image diamond10 = null;
    public static Image diamondJ = null;
    public static Image diamondQ = null;
    public static Image diamondK = null;
    //chips
    public static Image chip1 = null;
    public static Image chip5 = null;
    public static Image chip10 = null;
    public static Image chip25 = null;
    public static Image chip50 = null;
    public static Image chip100 = null;
    public static Image chip500 = null;
    public static Image chip1000 = null;
    public static Image chipAllIn = null;

    //etc
    public static Image back1 = null;
    public static Image back2 = null;
    public static Image back3 = null;
    public static Image blank = null;
    public static Image pokerTable = null;
    public static Image menu = null;
//</editor-fold>

    static {
        //<editor-fold defaultstate="collapsed" desc="images">
        //spades
        try {
            spadeA = new Image(new FileInputStream("src/Resource/as.png"), 1203, 1803, true, true);
            spade2 = new Image(new FileInputStream("src/Resource/2s.png"), 1203, 1803, true, true);
            spade3 = new Image(new FileInputStream("src/Resource/3s.png"), 1203, 1803, true, true);
            spade4 = new Image(new FileInputStream("src/Resource/4s.png"), 1203, 1803, true, true);
            spade5 = new Image(new FileInputStream("src/Resource/5s.png"), 1203, 1803, true, true);
            spade6 = new Image(new FileInputStream("src/Resource/6s.png"), 1203, 1803, true, true);
            spade7 = new Image(new FileInputStream("src/Resource/7s.png"), 1203, 1803, true, true);
            spade8 = new Image(new FileInputStream("src/Resource/8s.png"), 1203, 1803, true, true);
            spade9 = new Image(new FileInputStream("src/Resource/9s.png"), 1203, 1803, true, true);
            spade10 = new Image(new FileInputStream("src/Resource/10s.png"), 1203, 1803, true, true);
            spadeJ = new Image(new FileInputStream("src/Resource/js.png"), 1203, 1803, true, true);
            spadeQ = new Image(new FileInputStream("src/Resource/qs.png"), 1203, 1803, true, true);
            spadeK = new Image(new FileInputStream("src/Resource/ks.png"), 1203, 1803, true, true);
            //clubs
            clubsA = new Image(new FileInputStream("src/Resource/ac.png"), 1203, 1803, true, true);
            clubs2 = new Image(new FileInputStream("src/Resource/2c.png"), 1203, 1803, true, true);
            clubs3 = new Image(new FileInputStream("src/Resource/3c.png"), 1203, 1803, true, true);
            clubs4 = new Image(new FileInputStream("src/Resource/4c.png"), 1203, 1803, true, true);
            clubs5 = new Image(new FileInputStream("src/Resource/5c.png"), 1203, 1803, true, true);
            clubs6 = new Image(new FileInputStream("src/Resource/6c.png"), 1203, 1803, true, true);
            clubs7 = new Image(new FileInputStream("src/Resource/7c.png"), 1203, 1803, true, true);
            clubs8 = new Image(new FileInputStream("src/Resource/8c.png"), 1203, 1803, true, true);
            clubs9 = new Image(new FileInputStream("src/Resource/9c.png"), 1203, 1803, true, true);
            clubs10 = new Image(new FileInputStream("src/Resource/10c.png"), 1203, 1803, true, true);
            clubsJ = new Image(new FileInputStream("src/Resource/jc.png"), 1203, 1803, true, true);
            clubsQ = new Image(new FileInputStream("src/Resource/qc.png"), 1203, 1803, true, true);
            clubsK = new Image(new FileInputStream("src/Resource/kc.png"), 1203, 1803, true, true);
            //hearts
            heartA = new Image(new FileInputStream("src/Resource/ah.png"), 1203, 1803, true, true);
            heart2 = new Image(new FileInputStream("src/Resource/2h.png"), 1203, 1803, true, true);
            heart3 = new Image(new FileInputStream("src/Resource/3h.png"), 1203, 1803, true, true);
            heart4 = new Image(new FileInputStream("src/Resource/4h.png"), 1203, 1803, true, true);
            heart5 = new Image(new FileInputStream("src/Resource/5h.png"), 1203, 1803, true, true);
            heart6 = new Image(new FileInputStream("src/Resource/6h.png"), 1203, 1803, true, true);
            heart7 = new Image(new FileInputStream("src/Resource/7h.png"), 1203, 1803, true, true);
            heart8 = new Image(new FileInputStream("src/Resource/8h.png"), 1203, 1803, true, true);
            heart9 = new Image(new FileInputStream("src/Resource/9h.png"), 1203, 1803, true, true);
            heart10 = new Image(new FileInputStream("src/Resource/10h.png"), 1203, 1803, true, true);
            heartJ = new Image(new FileInputStream("src/Resource/jh.png"), 1203, 1803, true, true);
            heartQ = new Image(new FileInputStream("src/Resource/qh.png"), 1203, 1803, true, true);
            heartK = new Image(new FileInputStream("src/Resource/kh.png"), 1203, 1803, true, true);
            //diamonds
            diamondA = new Image(new FileInputStream("src/Resource/ad.png"), 1203, 1803, true, true);
            diamond2 = new Image(new FileInputStream("src/Resource/2d.png"), 1203, 1803, true, true);
            diamond3 = new Image(new FileInputStream("src/Resource/3d.png"), 1203, 1803, true, true);
            diamond4 = new Image(new FileInputStream("src/Resource/4d.png"), 1203, 1803, true, true);
            diamond5 = new Image(new FileInputStream("src/Resource/5d.png"), 1203, 1803, true, true);
            diamond6 = new Image(new FileInputStream("src/Resource/6d.png"), 1203, 1803, true, true);
            diamond7 = new Image(new FileInputStream("src/Resource/7d.png"), 1203, 1803, true, true);
            diamond8 = new Image(new FileInputStream("src/Resource/8d.png"), 1203, 1803, true, true);
            diamond9 = new Image(new FileInputStream("src/Resource/9d.png"), 1203, 1803, true, true);
            diamond10 = new Image(new FileInputStream("src/Resource/10d.png"), 1203, 1803, true, true);
            diamondJ = new Image(new FileInputStream("src/Resource/jd.png"), 1203, 1803, true, true);
            diamondQ = new Image(new FileInputStream("src/Resource/qd.png"), 1203, 1803, true, true);
            diamondK = new Image(new FileInputStream("src/Resource/kd.png"), 1203, 1803, true, true);
            //chips
            chip1 = new Image(new FileInputStream("src/Resource/chip1.png"), 180, 180, true, true);
            chip5 = new Image(new FileInputStream("src/Resource/chip5.png"), 180, 180, true, true);
            chip10 = new Image(new FileInputStream("src/Resource/chip10.png"), 180, 180, true, true);
            chip25 = new Image(new FileInputStream("src/Resource/chip25.png"), 180, 180, true, true);
            chip50 = new Image(new FileInputStream("src/Resource/chip50.png"), 180, 180, true, true);
            chip100 = new Image(new FileInputStream("src/Resource/chip100.png"), 180, 180, true, true);
            chip500 = new Image(new FileInputStream("src/Resource/chip500.png"), 180, 180, true, true);
            chip1000 = new Image(new FileInputStream("src/Resource/chip1000.png"), 180, 180, true, true);
            chipAllIn = new Image(new FileInputStream("src/Resource/chipallin.png"), 180, 180, true, true);

            //etc
            back1 = new Image(new FileInputStream("src/Resource/back.png"), 1203, 1803, true, true);
            back2 = new Image(new FileInputStream("src/Resource/back2.png"), 1203, 1803, true, true);
            back3 = new Image(new FileInputStream("src/Resource/back3.png"), 1203, 1803, true, true);
            blank = new Image(new FileInputStream("src/Resource/blank.png"), 1203, 1803, true, true);
            pokerTable = new Image(new FileInputStream("src/Resource/table.png"), 1920, 1080, true, true);
            menu = new Image(new FileInputStream("src/Resource/menubackground.png"), 1920, 1080, true, true);
        } catch (Exception e) {
            System.out.println("Error!");
        }
//</editor-fold>
    }
}
