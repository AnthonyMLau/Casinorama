package casino;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class BlackjackAI extends Player {

    private int runningCount = 0, bettingUnit = 0, realBet = 0;
    private double numDecks = 0.0, trueCount = 0.0;
    private boolean hit, split, stay, dDown, insurance;
    // private ArrayList<Card> counted = new ArrayList<>();
    private Random r = new Random();

    public BlackjackAI(String name, Deck deck,int playerNum) {
        super(name, deck, playerNum);
        setNumDecks(deck);
        setBettingUnit();
        setAi();
    }

    public void set(Deck deck, ArrayList<Player> players, Dealer dealer) {
        setRunningCount(players, dealer);
        setNumDecks(deck);
        setTrueCount();
    }

    public void setRunningCount(ArrayList<Player> players, Dealer dealer) {
        for (int i = 0; i < players.size(); i++) {
            for (int h = 0; h < players.get(i).getPocketHands().size(); h++) {
                for (int c = 0; c < players.get(i).getPocketHands().get(h).getPlayerHand().size(); c++) {
                    if (players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() == 10 || players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() == 1) {
                        this.runningCount -= 1;
                    } else if (players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() >= 2 && players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() <= 6) {
                        this.runningCount += 1;
                    }
                }
            }
        }
        for (int d = 0; d < dealer.getDealerHand().getPlayerHand().size(); d++) {
            if (dealer.getDealerHand().getPlayerHand().get(d).getWorth() == 10 || dealer.getDealerHand().getPlayerHand().get(d).getWorth() == 1) {
                this.runningCount -= 1;

            } else if (dealer.getDealerHand().getPlayerHand().get(d).getWorth() >= 2 && dealer.getDealerHand().getPlayerHand().get(d).getWorth() <= 6) {
                this.runningCount += 1;
            }
        }
    }

    @Override
    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance() {
        this.insurance = this.runningCount >= 3;
    }

    public void setAi() {
        super.setAi(true);
    }

    public void setTrueCount() {
        this.trueCount = 0;
        this.trueCount = this.runningCount / this.numDecks;
    }

    public void setNumDecks(Deck deck) {
        this.numDecks = 0;
        int cards = 0;
        double remainder;
        for (int i = 0; i < deck.getDeck().size(); i++) {
            cards += 1;
        }
        remainder = cards % 52;
        if (remainder >= 13 && remainder < 39) {
            numDecks += 0.5;
        } else if (remainder >= 39) {
            numDecks += 1;
        }
        numDecks += (int) (cards / 52);
    }

    public void setHit(Deck deck, int n) {
        if (super.getRealTotal(n) <= 17) {
            this.hit = true;
        } else if (super.getRealTotal(n) + deck.getDeck().get(0).getWorth() > 21) {
            this.hit = false;
            super.setStay(true);
        } else if (super.getRealTotal(n) + deck.getDeck().get(0).getWorth() <= 21) {
            this.hit = true;
        }
    }

    public boolean isLeave() {
        if (super.getChips() > 2500) {
            return true;
        } else if (super.getChips() < 50) {
            return true;
        } else {
            int n = r.nextInt(10);
            return n > 9;
        }
    }

    public void setSplit() {
        if (super.getChips() >= this.realBet) {
            this.split = super.getPocketHands().get(0).getPlayerHand().get(0).getWorth() == 1 && super.getPocketHands().get(0).getPlayerHand().get(1).getWorth() == 1;
        }
    }

    public void setdDown(int n) {
        if (super.getChips() >= this.realBet) {
            if (super.getRealTotal(n) >= 9 && super.getRealTotal(n) <= 11) {
                dDown = this.runningCount > 0;
            }
        }
    }

    public boolean isHit() {
        return hit;
    }

    @Override
    public boolean isSplit() {
        return split;
    }

    public boolean isdDown() {
        return dDown;
    }

    public void setBettingUnit() {
        this.bettingUnit = 0;
        double rand = ThreadLocalRandom.current().nextDouble(8,11);
        this.bettingUnit = (int) (super.getChips() / rand);
    }

    public void setRealBet() {
        this.realBet = 0;
        do {
            if (this.trueCount == 0) {
                this.realBet = this.bettingUnit;
            } else if (this.trueCount - 1 < 0) {
                double rand = ThreadLocalRandom.current().nextDouble(1, 1.5);
                this.realBet = (int) (this.bettingUnit / rand);
            } else if (this.trueCount > 1) {
                this.realBet = (int) (this.trueCount - 1) * (this.bettingUnit);
            }
        } while (this.realBet > super.getChips());
    }

    public int getRealBet() {
        return realBet;
    }

    public int getRunningCount() {
        return runningCount;
    }

    public double getTrueCount() {
        return trueCount;
    }

    public double getNumDecks() {
        return numDecks;
    }

    public int getBettingUnit() {
        return bettingUnit;
    }

}
