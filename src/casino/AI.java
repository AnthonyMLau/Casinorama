package casino;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.concurrent.Task;
public class AI extends Player {

    public AI(String name, int playerNum) {
        super(name, playerNum);
    }


    public int preFlopBetting(int bigBlind, int requiredChips) {
        PocketHand pocketHand = super.getPocketHand();
        Collections.sort(pocketHand.getPocketHand());
        if (requiredChips == bigBlind) {
            if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(0).getValue() == 1 && pocketHand.cardSeperation() <= 3) {
                return bigBlind * 3;
            } else if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(0).getValue() == 1) {
                return bigBlind * 2;
            } else if (pocketHand.pocketPair() && pocketHand.getPocketHand().get(0).getValue() > 10) {
                return (int) (bigBlind * 2.5);
            } else if (pocketHand.pocketPair()) {
                return bigBlind * 2;
            } else if (!(pocketHand.checkSuited()) && pocketHand.getPocketHand().get(0).getValue() == 1 && pocketHand.cardSeperation() <= 4) {
                return bigBlind * 2;
            } else if (pocketHand.checkSuited() && pocketHand.cardSeperation() == 1 && pocketHand.getPocketHand().get(0).getValue() >= 11) {
                return (int) (bigBlind * 2.5);
            } else if (pocketHand.checkSuited() && pocketHand.cardSeperation() == 1) {
                return (int) (bigBlind * 1.5);
            } else if (!(pocketHand.checkSuited()) && pocketHand.getPocketHand().get(0).getValue() >= 11 && pocketHand.getPocketHand().get(1).getValue() >= 11) {
                return (int) (bigBlind * 2.5);
            } else if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(0).getValue() >= 10 && pocketHand.getPocketHand().get(1).getValue() >= 10) {
                return bigBlind * 3;
            } else if (pocketHand.checkSuited() && (pocketHand.getPocketHand().get(0).getValue() == 11 || pocketHand.getPocketHand().get(0).getValue() == 10) && pocketHand.cardSeperation() == 2) {
                return (int) (bigBlind * 1.5);
            } else if (!(pocketHand.checkSuited()) && pocketHand.getPocketHand().get(1).getValue() >= 7) {
                return 0;
            } else if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(1).getValue() >= 6) {
                return 0;
            }
            return -1;
        } else {
            int raiseAmount = requiredChips - super.getChipsInCurrent();
            if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(0).getValue() == 1 && pocketHand.cardSeperation() <= 3) {
                return 0;
            } else if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(0).getValue() == 1) {
                return 0;
            } else if (pocketHand.pocketPair() && pocketHand.getPocketHand().get(0).getValue() > 11) {
                return 0;
            } else if (pocketHand.pocketPair()) {
                return 0;
            } else if (!(pocketHand.checkSuited()) && pocketHand.getPocketHand().get(0).getValue() == 1 && pocketHand.cardSeperation() <= 4) {
                return 0;
            } else if (pocketHand.checkSuited() && pocketHand.cardSeperation() == 1 && pocketHand.getPocketHand().get(0).getValue() >= 11 && raiseAmount <= (int) (bigBlind * 4)) {
                return 0;
            } else if (pocketHand.checkSuited() && pocketHand.cardSeperation() == 1 && raiseAmount <= (int) (bigBlind * 1.5)) {
                return 0;
            } else if (!(pocketHand.checkSuited()) && pocketHand.getPocketHand().get(0).getValue() >= 11 && pocketHand.getPocketHand().get(1).getValue() >= 11 && raiseAmount <= bigBlind * 2) {
                return 0;
            } else if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(0).getValue() >= 10 && pocketHand.getPocketHand().get(1).getValue() >= 10 && raiseAmount <= bigBlind * 3) {
                return 0;
            } else if (pocketHand.checkSuited() && (pocketHand.getPocketHand().get(0).getValue() == 11 || pocketHand.getPocketHand().get(0).getValue() == 10) && pocketHand.cardSeperation() == 2 && raiseAmount <= bigBlind * 2) {
                return 0;
            } else if (!(pocketHand.checkSuited()) && pocketHand.getPocketHand().get(1).getValue() >= 7 && raiseAmount <= bigBlind) {
                return 0;
            } else if (pocketHand.checkSuited() && pocketHand.getPocketHand().get(1).getValue() >= 6 && raiseAmount <= bigBlind) {
                return 0;
            }
            return -1;
        }
    }

    private double handStrength(PocketHand pocketHand, ArrayList<Card> communityCards, int totalCombinations, ArrayList<Card> possibleCards) {
        int numRun = 0;
        int numWin = 0;
        int size = communityCards.size();
        //Collections.sort(possibleCards);
        for (int i = possibleCards.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                PocketHand possiblePocketHand = new PocketHand();
                possiblePocketHand.getPocketHand().add(possibleCards.get(i));
                possiblePocketHand.getPocketHand().add(possibleCards.get(j));
//                possibleCards.remove(i);
//                possibleCards.remove(j);
                if (size == 3) {
                    for (int k = possibleCards.size() - 1; k > 0; k--) {
                        for (int p = k - 1; p >= 0; p--) {
                            if (k != i && k != j && p != i && p != j) {
                                communityCards.add(possibleCards.get(k));
                                communityCards.add(possibleCards.get(p));
                                Hand myHand = Poker.determineHand(pocketHand, communityCards);
                                Hand possibleHand = Poker.determineHand(possiblePocketHand, communityCards);
                                if (myHand.compareTo(possibleHand) <= 0) {
                                    numWin += 1;
                                }
                                while (communityCards.size() > size) {
                                    communityCards.remove(communityCards.size() - 1);
                                }
                                numRun += 1;
                                if (numRun == 75000) {
                                    return ((double) (numWin) / numRun);
                                }
                            }
                        }
                    }
                } else if (size == 4) {
                    for (int k = 0; k < possibleCards.size() - 1; k++) {
                        if (k != i && k != j) {
                            communityCards.add(possibleCards.get(k));
                            Hand myHand = Poker.determineHand(pocketHand, communityCards);
                            Hand possibleHand = Poker.determineHand(possiblePocketHand, communityCards);
                            if (myHand.compareTo(possibleHand) <= 0) {
                                numWin += 1;
                            }
                            communityCards.remove(communityCards.size() - 1);
                            numRun += 1;
                        }
                    }
                } else {
                    Hand myHand = Poker.determineHand(pocketHand, communityCards);
                    Hand possibleHand = Poker.determineHand(possiblePocketHand, communityCards);
                    if (myHand.compareTo(possibleHand) <= 0) {
                        numWin += 1;
                    }
                }
//                possibleCards.add(possiblePocketHand.getPocketHand().get(0));
//                possibleCards.add(possiblePocketHand.getPocketHand().get(1));
                possiblePocketHand.getPocketHand().removeAll(possibleCards);
//                Collections.sort(possibleCards);
            }
        }
        double handStrength = ((double) (numWin) / totalCombinations);
        return handStrength;
    }

    private double potOdds(int pot, int callAmount) {
        double potOdds = (double) (callAmount) / (pot + callAmount);
        return potOdds;
    }

    public Integer rateOfReturn(ArrayList<Card> communityCards, ArrayList<Player> players, int pot, int requiredChips, int bigBlind) {

        BigInteger totalCombinations = factorial((50 - communityCards.size())).divide(factorial(2).multiply(factorial(50 - communityCards.size() - 2))).multiply(factorial((48 - communityCards.size())).divide(factorial(5 - communityCards.size()).multiply(factorial(48 - communityCards.size() - (5 - communityCards.size())))));
        int totalCombination = totalCombinations.intValue();
        Deck deck = new Deck();
        for (Card communityCard : communityCards) {
            for (int i = deck.getDeck().size() - 1; i >= 0; i--) {
                if (deck.getDeck().get(i).equals(communityCard) || deck.getDeck().get(i).equals(super.getPocketHand().getPocketHand().get(0)) || deck.getDeck().get(i).equals(super.getPocketHand().getPocketHand().get(1))) {
                    deck.getDeck().remove(i);
                }
            }
        }
        deck.shuffle();
        double handStrength = handStrength(super.getPocketHand(), communityCards, totalCombination, deck.getDeck());
        System.out.println(this.getName()+" "+handStrength);
        handStrength = handStrength / (players.size() - 1);

        int callAmount = requiredChips - super.getChipsInCurrent();

        double potOdds = potOdds(pot, callAmount);
        try {
            double rateOfReturn = handStrength / potOdds;
            Random ranNum = new Random();
            if (rateOfReturn < 0.8) {
                int random = ranNum.nextInt(100);
                if (random >= 94) {
                    random = ranNum.nextInt(15);
                    double percentRaise = (double) (random) / 100 + 0.5;
                    int raise = (int) (pot * percentRaise);
                    if (lowOnChips(raise, bigBlind, handStrength, players)) {
                        return 4;
                    }
                    return raise;
                } else {
                    return 4;
                }
            } else if (rateOfReturn < 1) {
                int random = ranNum.nextInt(100);
                if (random >= 94) {
                    if (lowOnChips(callAmount, bigBlind, handStrength, players)) {
                        return 4;
                    }
                    return 3;
                } else if (random >= 79) {
                    random = ranNum.nextInt(10);
                    double percentRaise = (double) (random) / 100 + 0.33;
                    int raise = (int) (pot * percentRaise);
                    if (lowOnChips(raise, bigBlind, handStrength, players)) {
                        return 4;
                    }
                    return raise;
                } else {
                    return 4;
                }
            } else if (rateOfReturn < 1.3) {
                int random = ranNum.nextInt(100);
                if (random >= 59) {
                    random = ranNum.nextInt(10);
                    double percentRaise = (double) (random) / 100 + 0.3;
                    int raise = (int) (pot * percentRaise);
                    if (lowOnChips(raise, bigBlind, handStrength, players)) {
                        if (lowOnChips(callAmount, bigBlind, handStrength, players)) {
                            return 4;
                        } else {
                            return 3;
                        }
                    }
                    return raise;
                } else {
                    if (lowOnChips(callAmount, bigBlind, handStrength, players)) {
                        return 4;
                    }
                    return 3;
                }
            } else {
                int random = ranNum.nextInt(100);
                if (random >= 69) {
                    return 3;
                } else {
                    random = ranNum.nextInt(15);
                    double percentRaise = (double) (random) / 100 + 0.5;
                    return (int) (pot * percentRaise);
                }
            }
        } catch (Exception e) {
            Random ranNum = new Random();
            if (handStrength < 0.4) {
                return 2;
            } else {
                int random = ranNum.nextInt(25);
                double percentRaise = (double) (random) / 100 + 0.2;
                int raise = (int) (pot * percentRaise);
                if (lowOnChips(raise, bigBlind, handStrength, players)) {
                    return 2;
                }
                return (int) (pot * percentRaise);
            }
        }
    }

    private boolean lowOnChips(int raise, int bigBlind, double handStrength, ArrayList<Player> players) {
        if (super.getChips() - raise < 4 * bigBlind && handStrength < 1.0 / players.indexOf(this)) {
            return true;
        }
        return false;
    }

    public static BigInteger factorial(int num) {
        // Initialize result
        BigInteger f = new BigInteger("1"); // Or BigInteger.ONE

        // Multiply f with 2, 3, ...N
        for (int i = 2; i <= num; i++) {
            f = f.multiply(BigInteger.valueOf(i));
        }

        return f;
    }

}
