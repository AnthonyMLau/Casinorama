
package casino;

public class Blind {
    private int blindAmount;
    private String typeBlind;

    public Blind() {
        blindAmount = 0;
        typeBlind = "null";
    }

    public Blind(int blindAmount, String typeBlind) {
        this.blindAmount = blindAmount;
        this.typeBlind = typeBlind;
    }

    public int getBlindAmount() {
        return blindAmount;
    }

    public String getTypeBlind() {
        return typeBlind;
    }

    public void setBlindAmount(int blindAmount) {
        this.blindAmount = blindAmount;
    }

    public void setTypeBlind(String typeBlind) {
        this.typeBlind = typeBlind;
    }
    
    
    
}
