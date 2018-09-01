package casino;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Card extends Rectangle implements Comparable {


    private int value, worth;
    private String suit;
    private double x;
    private double y;
    private boolean faceUp;
    private ImagePattern ip;

    public Card(int value, String suit) {
        super(63*1.25, 88*1.25);
        this.value = value;
        this.suit = suit;
        setWorth();
    }

    public Card(int value, String suit, ImagePattern ip) {
        super(63, 88, ip);
        this.value = value;
        this.suit = suit;
        this.faceUp = false;
        this.ip = ip;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth() {
        switch (value) {
            case 11:
                worth = 10;
                break;
            case 12:
                worth = 10;
                break;
            case 13:
                worth = 10;
                break;
            default:
                worth = value;
                break;
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        String display = "";
        if (value >= 2 && value <= 10) {
            display += value;
        } else if (value == 1) {
            display += "Ace";
        } else if (value == 11) {
            display += "Jack";
        } else if (value == 12) {
            display += "Queen";
        } else if (value == 13) {
            display += "King";
        }
        display += " of " + suit + "s";
        return display;
    }

    @Override
    public boolean equals(Object o) {
        Card card1;
        if(o instanceof Card){
            card1 = (Card) o;
        }
        else{
            return false;
        }
        if (value == card1.getValue() && suit.equalsIgnoreCase(card1.getSuit())) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object t) {
        Card card = (Card) t;

        if (value == card.getValue()) {
            return 0;
        } else if (value == 1) {
            return -1;
        } else if (value > card.getValue() && card.getValue() != 1) {
            return -1;
        } else {
            return 1;
        }

    }
}
