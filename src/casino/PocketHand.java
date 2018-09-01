package casino;

import java.util.ArrayList;
import java.util.Collections;
    
public class PocketHand{
    private ArrayList<Card>pocketHand;
    private ArrayList<Card> playerHand = new ArrayList<>();
    private int splitBet;
    public PocketHand() {
        pocketHand= new ArrayList<Card>();
    }
    
    public PocketHand(Deck deck) {
        for (int x = 0; x < 2; x++) {
            playerHand.add(deck.getDeck().get(x));
            deck.getDeck().remove(x);
        }
    }

    public ArrayList<Card> getPocketHand() {
        return pocketHand;
    }

    public void setPocketHand(ArrayList<Card> pocketHand) {
        this.pocketHand = pocketHand;
    }
    

    public void setSplitBet(int splitBet) {
        this.splitBet = splitBet;
    }

    public int getSplitBet() {
        return splitBet;
    }

    public PocketHand(Deck deck, int value, String suit) {
        playerHand.add(new Card(value, suit));
        playerHand.add(deck.getDeck().get(0));
        deck.getDeck().remove(0);
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public void hitCard(Deck deck) {
        playerHand.add(deck.getDeck().get(0));
        deck.getDeck().remove(0);
    }

    public boolean checkBust() {
        int check = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            check += playerHand.get(i).getWorth();
        }
        return check>21;
    }
    public boolean checkSplit() {
        return this.playerHand.get(0).getWorth() == this.playerHand.get(1).getWorth();
    }

    public boolean checkBlackJack() {
        int check = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            check += playerHand.get(i).getWorth();
        }
        return check == 21;
    }

    public boolean pocketPair() {
        if (pocketHand.get(0).getValue() == pocketHand.get(1).getValue()) {
            return true;
        }
        return false;
    }
    
    public int cardSeperation(){
        Collections.sort(pocketHand);
        if(pocketHand.get(0).getValue()==1){
            int cardValue=14;
            return cardValue-pocketHand.get(1).getValue();
        }
        else{
            return pocketHand.get(0).getValue()-pocketHand.get(1).getValue();
        }
    }
    
     public boolean checkSuited(){
        if(pocketHand.get(0).getSuit().equals(pocketHand.get(1).getSuit())){
            return true;
        }
        return false;
    }

}
