/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.util.ArrayList;

/**
 *
 * @author laua8572
 */
public class Bet {

    private double amountBet;
    private int betType, playerNum;
    private ArrayList<Integer> numsBetOn = new ArrayList<Integer>();

    public Bet(double amountBet, int betType, int playerNum, ArrayList<Integer> numsBetOn) {
        this.amountBet = amountBet;
        this.betType = betType;
        this.playerNum = playerNum;
        this.numsBetOn = numsBetOn;
    }

    public double getPayout() {
        if (betType == 1) {//single
            return 36;
        } else if (betType == 2) {//split
            return 18;
        } else if (betType == 3) {//street
            return 12;
        } else if (betType == 4) {//corner
            return 9;
        } else if (betType >= 5 && betType <= 10) {
            return 2;
        } else if (betType >= 11 && betType <= 13) {
            return 3;
        } else if (betType >= 14 && betType <= 16) {
            return 3;
        }
        return -1000;
    }

    public double getAmountBet() {
        return amountBet;
    }

    public void setAmountBet(double amountBet) {
        this.amountBet = amountBet;
    }

    public int getBetType() {
        return betType;
    }

    public void setBetType(int betType) {
        this.betType = betType;
    }

    public int getPlayerNum() {
        return playerNum;
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
}

    //    public int single(){1
//        return 1;
//    }
//    public int splitH(){2
//        return 2;       //check if it is horizontal in roulette class
//    }
//    public int splitV(){3
//        return 4;       //check if it is vertical in roulette class
//    }
//    public int street(){4
//        return 3;
//    }
//    public int doubleStreet(){5
//        return 6;
//    }
//    public int corner(){6    //check if it is a corner num in roulette class
//        return 4;
//    }
//    public int lowHighEvenOddRedBlack(){
//        return 18;
//    }
//    public int dozenOrSnakeOrColumn(){
//        return 12;
//    }


