package casino;

public class Dealer extends Player {

    private PocketHand dealerHand;
    private int total, optionalTotal;
    private boolean bust = false;

    public Dealer(Deck deck) {
        super("Dealer", deck, 5);
        this.dealerHand = new PocketHand(deck);
    }

    public PocketHand getDealerHand() {
        return dealerHand;
    }

    public void removeHand() {
        for (int i = 0; i < dealerHand.getPlayerHand().size(); i++) {
            this.dealerHand.getPlayerHand().remove(i);
        }
    }

    public void addHand(Deck deck) {
        this.dealerHand = new PocketHand(deck);
    }

    public void setOptionalTotal(int optionalTotal) {
        this.optionalTotal = optionalTotal;
    }

    public int getOptionalTotal() {
        optionalTotal = 0;
        for (int i = 0; i < dealerHand.getPlayerHand().size(); i++) {
            if (this.dealerHand.getPlayerHand().get(i).getWorth() == 1) {
                optionalTotal += 11;
            } else {
                optionalTotal += this.dealerHand.getPlayerHand().get(i).getWorth();
            }
        }
        return optionalTotal;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public void setTotal() {
        this.total = 0;
        for (int i = 0; i < dealerHand.getPlayerHand().size(); i++) {
            this.total += dealerHand.getPlayerHand().get(i).getWorth();
        }
    }

    public int getTotal() {
        setTotal();
        if (total < getOptionalTotal() && getOptionalTotal() <= 21) {
            total = getOptionalTotal();
        }
        return total;
    }

    public void setDealerHand(PocketHand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public boolean checkInsured() {
        return this.dealerHand.getPlayerHand().get(0).getValue() == 1;
    }

    public boolean checkSeventeen() {
        return getTotal() >= 17;
    }
}
