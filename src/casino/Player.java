package casino;

import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Player implements Comparable {

    private String name;
    private int chips;
    private PocketHand pocketHand;
    private Hand hand;
    private int playerNum, total, bet, insuranceAmount;
    private Blind blind;
    private int chipsInCurrent, totalChipsInPot;
    private ArrayList<PocketHand> pocketHands = new ArrayList<>();
    private ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
    private boolean insurance = false, stay = false, naturalBlackJack, ai = false, split;
    private double x = 0, y = 0;
    private Pane playerPane;
    private HBox hBox;
    final public static double top = 130, bottom = 700, middleY = (top + bottom) / 2, farLeft = 200, farRight = 1550, middleX = (farLeft + farRight) / 2, betweenLeft = (farLeft + middleX) / 2, betweenRight = (farRight + middleX) / 2,
            p1x = middleX, p1y = bottom,
            p2x = betweenLeft - 50, p2y = bottom,
            p3x = farLeft, p3y = middleY,
            p4x = betweenLeft - 50, p4y = top,
            p5x = middleX, p5y = top,
            p6x = betweenRight + 50, p6y = top,
            p7x = farRight, p7y = middleY,
            p8x = betweenRight + 50, p8y = bottom;

    private int numTurn;

    public Player(String name, int playerNum) {
        this.name = name;
        this.playerNum = playerNum;
        this.chips = 500;
        blind = new Blind();
        pocketHand = new PocketHand();
        this.chipsInCurrent = 0;
        this.totalChipsInPot = 0;
        playerPane = new Pane();
        setPlayerPosition();
        this.numTurn = 0;
    }

    public void setPlayerPosition() {
        switch (playerNum) {
            case 1:
                playerPane.setTranslateX(p1x);
                playerPane.setTranslateY(p1y);
                break;
            case 2:
                playerPane.setTranslateX(p2x);
                playerPane.setTranslateY(p2y);
                break;
            case 3:
                playerPane.setTranslateX(p3x);
                playerPane.setTranslateY(p3y);
                break;
            case 4:
                playerPane.setTranslateX(p4x);
                playerPane.setTranslateY(p4y);
                break;
            case 5:
                playerPane.setTranslateX(p5x);
                playerPane.setTranslateY(p5y);
                break;
            case 6:
                playerPane.setTranslateX(p6x);
                playerPane.setTranslateY(p6y);
                break;
            case 7:
                playerPane.setTranslateX(p7x);
                playerPane.setTranslateY(p7y);
                break;
            case 8:
                playerPane.setTranslateX(p8x);
                playerPane.setTranslateY(p8y);
                break;
            default:
                System.out.println("error");
                break;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Pane getPane() {
        return playerPane;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    public boolean isAi() {
        return ai;
    }

    public void setInsuranceAmount(int insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public boolean isNaturalBlackJack() {
        return naturalBlackJack;
    }

    public void setNaturalBlackJack(boolean naturalBlackJack) {
        this.naturalBlackJack = naturalBlackJack;
    }

    public int getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount() {
        this.insuranceAmount = bet / 2;
        chips = chips - insuranceAmount;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public boolean isStay() {
        return stay;
    }

    public Player(String name, Deck deck, int playerNum) {
        this.name = name;
        this.playerNum = playerNum;
        this.chips = 500;
        this.pocketHands.add(new PocketHand(deck));
        playerPane = new Pane();
        setPlayerPosition();
        hBox = new HBox();
        //playerPane.getChildren().add(hBox);
    }

    public HBox gethBox() {
        return hBox;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setBet(int bet) {
        this.bet = bet;
        this.chips = chips - bet;
    }

    public int setTotal(int n) {
        this.total = 0;
        int optionalTotal = 0;
        for (int i = 0; i < pocketHands.get(n).getPlayerHand().size(); i++) {
            if (pocketHands.get(n).getPlayerHand().get(i).getWorth() == 1) {
                optionalTotal += 11;
                total += 1;
            } else {
                optionalTotal += pocketHands.get(n).getPlayerHand().get(i).getWorth();
                total += pocketHands.get(n).getPlayerHand().get(i).getWorth();
            }
        }
        if (optionalTotal <= 21) {
            return optionalTotal;
        } else {
            return 0;
        }
    }

    public void setObTotal(int n) {
        this.total = n;
    }

    public int getTotal(int n) {
        total = 0;
        for (int i = 0; i < pocketHands.get(n).getPlayerHand().size(); i++) {
            total += pocketHands.get(n).getPlayerHand().get(i).getWorth();
        }
        return total;
    }

    public int getRealTotal(int n) {
        if (setTotal(n) > getTotal(n)) {
            this.total = setTotal(n);
        }
        return this.total;
    }

    public int getTotal() {
        return total;
    }

    public int getBet() {
        return bet;
    }

    public void ifSplit(Deck deck) {
        this.pocketHands.add(new PocketHand(deck, pocketHands.get(0).getPlayerHand().get(1).getValue(), pocketHands.get(0).getPlayerHand().get(1).getSuit()));
        this.pocketHands.get(0).getPlayerHand().remove(1);
        this.pocketHands.get(0).hitCard(deck);
    }

    public void setPocketHands(Deck deck) {
        this.pocketHands.clear();
        this.pocketHands.add(new PocketHand(deck));
    }

    public void setPocketHand(PocketHand pocketHand) {
        this.pocketHand = pocketHand;
    }

    public ArrayList<PocketHand> getPocketHands() {
        return pocketHands;
    }

    public PocketHand getPocketHand() {
        return pocketHand;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public Blind getBlind() {
        return blind;
    }

    public int getChipsInCurrent() {
        return chipsInCurrent;
    }

    public int getTotalChipsInPot() {
        return totalChipsInPot;
    }

    public void setTotalChipsInPot(int totalChipsInPot) {
        this.totalChipsInPot = totalChipsInPot;
    }

    public void setChipsInCurrent(int chipsInCurrent) {
        this.chipsInCurrent = chipsInCurrent;
    }

    public void setBlind(Blind blind) {
        this.blind = blind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public void payout(int amount) {
        this.setChips(chips + amount);
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public ArrayList<Integer> getNumsBetOn() {
        return numsBetOn;
    }

    public void setNumsBetOn(ArrayList<Integer> numsBetOn) {
        this.numsBetOn = numsBetOn;
    }

    public int getNumTurn() {
        return numTurn;
    }

    public void setNumTurn(int numTurn) {
        this.numTurn = numTurn;
    }

    @Override
    public int compareTo(Object t) {
        Player player = (Player) t;
        if (playerNum > player.getPlayerNum()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        Player player = (Player) o;
        if (playerNum == player.getPlayerNum()) {
            return true;
        }
        return false;
    }

}
