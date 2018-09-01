package casino;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.layout.Pane;

public class Deck implements Comparable {

    Pane dPane;
    
    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {
        dPane = new Pane();
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Heart");
            deck.add(card);
        }
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Diamond");
            deck.add(card);
        }
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Club");
            deck.add(card);
        }
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Spade");
            deck.add(card);
        }
        dPane.setTranslateX(797);
        dPane.setTranslateY(295);
    }

    public Deck(int n) {
        for (int x = 0; x < n; x++){
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Heart");
                deck.add(card);
            }
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Diamond");
                deck.add(card);
            }
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Club");
                deck.add(card);
            }
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Spade");
                deck.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Pane getdPane() {
        return dPane;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    @Override
    public int compareTo(Object t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
