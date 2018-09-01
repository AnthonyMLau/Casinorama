package casino;

import java.io.*;
import java.util.ArrayList;
import javafx.scene.layout.HBox;

public class BlackjackJAVA {

    public static InputStreamReader inStream = new InputStreamReader(System.in);
    public static BufferedReader stdin = new BufferedReader(inStream);
    public static ArrayList<Player> numOfPlayers = new ArrayList<>();
    public static Deck deck = new Deck(5);
    public static Dealer dealer;
    public static int round = 1;

    public static void main(String name) throws IOException, InterruptedException {
        makeDeck();
        addPlayer(name);
        dealer = new Dealer(deck);
        addAI();
        if (numOfPlayers.isEmpty()) {
        }
        BlackJackGraphics.printBlankBoard();
//        System.out.println("");
//        printBoard();
//        resetCharacteristics();
//        for (int i = 0; i < numOfPlayers.size(); i++) {
//            System.out.print("\n" + numOfPlayers.get(i).getName().toUpperCase() + " would you like to:\n1) Play again\n2) Cash out\nEnter choice: ");
//            if (numOfPlayers.get(i).isAi()) {
//                BlackjackAI ai = (BlackjackAI) (numOfPlayers.get(i));
//                Thread.sleep(1000);
//                if (ai.isLeave()) {
//                    System.out.print("2");
//                    answer = 2;
//                } else {
//                    System.out.print("1");
//                    answer = 1;
//                }
//                System.out.println("");
//            } else {
//                answer = Integer.parseInt(stdin.readLine());
//            }
//            if (answer == 2) {
//                numOfPlayers.remove(i);
//                i = i - 1;
//            }
//        }
//        if (deck.getDeck().isEmpty()) {
//            deck = new Deck();
//        }

    }

    public static void addPlayer(String name) {
        if (name == null) {
            numOfPlayers.add(new Player("Player 1", deck, 1));
        } else {
            numOfPlayers.add(new Player(name, deck, 1));
        }
        numOfPlayers.get(0).setChips(Casino.getMainPlayer().getChips());
    }

    public static void makeDeck() {
        deck = new Deck(5);
        deck.shuffle();
    }

    public static void addAI() throws IOException {
        numOfPlayers.add(new BlackjackAI("John", deck, 2));
        numOfPlayers.add(new BlackjackAI("Bob", deck, 8));
    }

    public static void resetCharacteristics() {
        for (int i = 0; i < numOfPlayers.size(); i++) {
            numOfPlayers.get(i).getPane().getChildren().clear();
            numOfPlayers.get(i).setBet(0);
            numOfPlayers.get(i).setStay(false);
            numOfPlayers.get(i).setNaturalBlackJack(false);
            numOfPlayers.get(i).setInsurance(false);
            numOfPlayers.get(i).setInsuranceAmount(0);
            if (numOfPlayers.get(i).isAi()) {
                BlackjackAI ai = (BlackjackAI) (numOfPlayers.get(i));
                ai.set(deck, numOfPlayers, dealer);
            }
            for (int s = 0; s < numOfPlayers.get(i).getPocketHands().size(); s++) {
                numOfPlayers.get(i).getPocketHands().remove(s);
            }
            numOfPlayers.get(i).getPocketHands().add(new PocketHand(deck));

        }
        dealer = new Dealer(deck);
    }

    public static void printBoard() throws IOException, InterruptedException {
        BlackjackAI ai;
        System.out.println("\nDEALER ~ HAND");
        System.out.println(dealer.getDealerHand().getPlayerHand().get(0) + "\t*********");
        if (dealer.checkInsured()) {
            System.out.println("Would you like insurance?");
        }
        for (int i = 0; i < numOfPlayers.size(); i++) {
            if (dealer.getDealerHand().getPlayerHand().get(0).getValue() == 1) {
                System.out.print(numOfPlayers.get(i).getName().toUpperCase() + " ~ Would you like insurance?");
                if (numOfPlayers.get(i).isAi()) {
                    ai = (BlackjackAI) (numOfPlayers.get(i));
                    if (ai.isInsurance()) {
                        System.out.print(" yes");
                        getInsurance(i);
                    } else {
                        System.out.print(" no");
                    }
                    System.out.println("");
                } else if (stdin.readLine().equalsIgnoreCase("yes")) {
                    getInsurance(i);
                }
            }
        }
        if (1 != dealer.getDealerHand().getPlayerHand().get(0).getValue()) {
            Thread.sleep(2000);
        }
        System.out.println("");
        for (int i = 0; i < numOfPlayers.size(); i++) {
            System.out.println("\t\t" + numOfPlayers.get(i).getName().toUpperCase() + "\t\tBet: $" + numOfPlayers.get(i).getBet());
            System.out.println("~HAND~");
            if (numOfPlayers.get(i).setTotal(0) != 0) {
                if (numOfPlayers.get(i).setTotal(0) != numOfPlayers.get(i).getTotal(0)) {
                    if (numOfPlayers.get(i).setTotal(0) == 21 || numOfPlayers.get(i).getTotal(0) == 21) {
                        System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: 21");
                    } else {
                        System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal(0) + " or " + numOfPlayers.get(i).setTotal(0));
                    }
                } else {
                    System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal(0));
                }
            } else {
                System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal(0));
            }
            playRound(i, 0);
        }
        playDealer();
        System.out.println("");
        checkWin();
    }

    public static void checkWin() throws IOException {
        checkInsuranceWin();
        if (dealer.isBust()) {
            for (int i = 0; i < numOfPlayers.size(); i++) {
                for (int s = 0; s < numOfPlayers.get(i).getPocketHands().size(); s++) {
                    if (numOfPlayers.get(i).getTotal(s) <= 21 && !numOfPlayers.get(i).isNaturalBlackJack()) {
                        numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet() * 2);
                        System.out.println("Player " + numOfPlayers.get(i).getName() + " won due to dealer BUST!");
                    }
                }
            }
        } else {
            for (int i = 0; i < numOfPlayers.size(); i++) {
                for (int s = 0; s < numOfPlayers.get(i).getPocketHands().size(); s++) {
                    if (numOfPlayers.get(i).setTotal(s) > numOfPlayers.get(i).getTotal(s) && numOfPlayers.get(i).setTotal(s) <= 21) {
                        numOfPlayers.get(i).setObTotal(numOfPlayers.get(i).setTotal(s));
                    } else {
                        numOfPlayers.get(i).setObTotal(numOfPlayers.get(i).getTotal(s));
                    }
                    if (!numOfPlayers.get(i).isNaturalBlackJack() && numOfPlayers.get(i).getTotal() <= 21) {
                        if (dealer.getTotal() > numOfPlayers.get(i).getTotal()) {
                            System.out.println("Player " + numOfPlayers.get(i).getName() + " lost!");
                            numOfPlayers.get(i).setBet(0);
                        } else if (dealer.getTotal() == numOfPlayers.get(i).getTotal()) {
                            System.out.println("Player " + numOfPlayers.get(i).getName() + " stands!");
                            numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet());
                        } else if (dealer.getTotal() < numOfPlayers.get(i).getTotal()) {
                            System.out.println("Player " + numOfPlayers.get(i).getName() + " won!");
                            numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet() * 2);
                        }
                    }
                }
            }
        }
    }

    public static void checkInsuranceWin() {
        if (dealer.getDealerHand().getPlayerHand().get(0).getWorth() == 1 && dealer.getDealerHand().getPlayerHand().get(1).getWorth() == 10) {
            for (int i = 0; i < numOfPlayers.size(); i++) {
                if (numOfPlayers.get(i).isInsurance()) {
                    numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getInsuranceAmount() * 3);
                    System.out.println("Player " + numOfPlayers.get(i).getName() + " insurance paid off! Chips: " + numOfPlayers.get(i).getChips());
                }
            }
        }
    }

    public static void getInsurance(int i) throws IOException {
        numOfPlayers.get(i).setInsurance(true);
        if (numOfPlayers.get(i).getChips() >= numOfPlayers.get(i).getBet() / 2) {
            numOfPlayers.get(i).setInsuranceAmount();
        } else {
            System.out.println("You do not have enough chips");
        }
    }

    public static void playDealer() throws InterruptedException, IOException {
        BlackJackGraphics.printDealer();
        if (!dealer.checkSeventeen()) {
            dealer.getDealerHand().hitCard(deck);
            BlackJackGraphics.printDealer();
        }
        if (dealer.getTotal() > 21) {
            System.out.println("\nDealer BUST!");
            dealer.setBust(true);
        }
        if (dealer.checkSeventeen()) {
            BlackJackGraphics.setDealerBox();
            BlackJackGraphics.checkWin();
        }
    }

    public static void printDealer() {
        for (int i = 0; i < dealer.getDealerHand().getPlayerHand().size(); i++) {
            System.out.print(dealer.getDealerHand().getPlayerHand().get(i) + "\t\t");
        }
        //   System.out.print("Total: " + dealer.getTotal());
    }

    public static void printCards(Player player, int handNum) throws IOException, InterruptedException {
        if (player.setTotal(handNum) != 0) {
            if (player.setTotal(handNum) != player.getTotal(handNum)) {
                if (player.setTotal(handNum) == 21 || player.getTotal(handNum) == 21) {
                    //   System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: 21");
                } else {
                    //   System.out.print("Total: " + numOfPlayers.get(i).setTotal(handNum) + " or " + numOfPlayers.get(i).getTotal(handNum));
                }
            } else {
                //   System.out.print("Total: " + numOfPlayers.get(i).getTotal(handNum));
            }
        } else {
            // System.out.print("Total: " + numOfPlayers.get(i).getTotal(handNum));
        }
        if (player.getTotal(handNum) > 21) {
            //   System.out.println("\tBUST!\n");
            player.setStay(true);
            player.setBet(0);
        }
        //      System.out.println("");
        BlackJackGraphics.printCard(handNum);
    }

    public static void playerHit(Player player, int handNum) throws IOException, InterruptedException {
        //    int i = numOfPlayers.indexOf(player);
        //     numOfPlayers.get(i).getPocketHands().get(handNum).hitCard(deck);
        player.getPocketHands().get(handNum).hitCard(deck);
        printCards(player, handNum);
    }

    public static void playerSplit(Player player, int handNum) throws IOException, InterruptedException {
        if (player.getChips() >= player.getBet()) {
            player.ifSplit(deck);
            player.getPocketHands().get(1).setSplitBet(player.getBet());
            player.setChips(player.getChips() - player.getPocketHands().get(1).getSplitBet());
            player.setSplit(true);
            printCards(player, 0);
        } else {
            System.out.println("You do not have enough chips to split!");
        }
    }

    public static void playerDD(Player player, int handNum) throws IOException, InterruptedException {
        if (player.getChips() >= player.getBet()) {
            player.setChips(player.getChips() + player.getBet());
            player.setBet(player.getBet() * 2);
            player.getPocketHands().get(handNum).hitCard(deck);
            player.setStay(true);
            printCards(player, handNum);
        } else {
            System.out.println("You do not have enough chips to double down!");
        }
    }

    public static void playRound(int i, int handNum) throws IOException, InterruptedException {
//        round = 1;
//        //     BlackJackGraphics.setButtons(i, handNum);
//        if (handNum > 0) {
//            numOfPlayers.get(i).setStay(false);
//        }
//        while (!numOfPlayers.get(i).isStay()) {
//            if (numOfPlayers.get(i).isAi()) {
//                BlackjackAI ai = (BlackjackAI) (numOfPlayers.get(i));
//                ai.setdDown(handNum);
//                ai.setSplit();
//                ai.setHit(deck, handNum);
//                if (round == 1) {
//                    if (numOfPlayers.get(i).getPocketHands().get(handNum).checkBlackJack() || numOfPlayers.get(i).setTotal(handNum) == 21) {
//                        System.out.println("NATURAL BLACKJACK!");
//                        numOfPlayers.get(i).setNaturalBlackJack(true);
//                        numOfPlayers.get(i).setStay(true);
//                        numOfPlayers.get(i).setChips((int) (numOfPlayers.get(i).getBet() * 1.5 + numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet()));
//                        break;
//                    } else if (numOfPlayers.get(i).getPocketHands().get(handNum).checkSplit() && ai.isdDown()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Double down\n4) Split");
//                        if (ai.isSplit()) {
//                            Thread.sleep(2000);
//                            System.out.println("4");
//                            //   playerSplit(i, handNum);
//                        } else if (ai.isdDown()) {
//                            Thread.sleep(2000);
//                            System.out.println("3");
//                            //              playerDD(i, handNum);
//                        } else if (ai.isHit()) {
//                            Thread.sleep(2000);
//                            System.out.println("1");
////                            playerHit(i, handNum);
//                        } else if (!ai.isHit()) {
//                            Thread.sleep(2000);
//                            System.out.println("2");
//                        }
//                    } else if (numOfPlayers.get(i).getPocketHands().get(handNum).checkSplit()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split");
//                        if (ai.isSplit()) {
//                            Thread.sleep(2000);
//                            System.out.println("3");
//                            //playerSplit(i, handNum);
//                        } else if (ai.isHit()) {
//                            Thread.sleep(2000);
//                            System.out.println("1");
////                            playerHit(i, handNum);
//                        } else if (!ai.isHit()) {
//                            Thread.sleep(2000);
//                            System.out.println("2");
//                        }
//                    } else if (ai.isdDown()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Double down");
//                        Thread.sleep(2000);
//                        System.out.println("3");
//                        //                 playerDD(i, handNum);
//                    } else if (ai.isHit()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay");
//                        Thread.sleep(2000);
//                        System.out.println("1");
////                        playerHit(i, handNum);
//                    } else if (!ai.isHit()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay");
//                        Thread.sleep(2000);
//                        System.out.println("2");
//                    }
//                } else {
//                    if (numOfPlayers.get(i).getPocketHands().get(handNum).checkBlackJack() || numOfPlayers.get(i).setTotal(handNum) == 21) {
//                        System.out.println("BLACKJACK!");
//                        numOfPlayers.get(i).setStay(true);
//                        break;
//                    } else if (ai.isHit()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay");
//                        Thread.sleep(2000);
//                        System.out.println("1");
////                        playerHit(i, handNum);
//                    } else if (!ai.isHit()) {
//                        System.out.println("Would you like to\n1) Hit\n2) Stay");
//                        Thread.sleep(2000);
//                        System.out.println("2");
//                    }
//                }
//            } else {
//            }
//        }
    }

}
