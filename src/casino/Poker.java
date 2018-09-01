package casino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Poker {

    private static int pot = 0;
    private static Random rand = new Random();
    private static int requiredChips;
    private static int bigBlind;
    private static int round;
    private static int smallBlindNum;
    private static ArrayList<Player> players;
    private static ArrayList<Player> allPlayers;
    private static ArrayList<Card> communityCards;
    private static Deck deck;
    private static Player currentPlayer;
    static boolean allPlayerCheck;

    public Poker() {
        createPlayers();
        players = new ArrayList<Player>();
        communityCards = new ArrayList<Card>();
    }

    public static ArrayList<Player> createPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        setAllPlayers(new ArrayList<Player>());
        Casino.getMainPlayer().setPlayerNum(1);
        getAllPlayers().add(Casino.getMainPlayer());
        for (int i = 2; i < 9; i++) {
            getAllPlayers().add(new AI("Player " + i, i));
        }
        return getAllPlayers();
    }

    public void newHand() {
        System.out.println("NEW HAND");
        if (Casino.getMainPlayer().getChips() == 0) {
            Casino.menu();
        }
        setPot(0);
        getPlayers().clear();
        for (Player player : getAllPlayers()) {
            if (player.getChips() != 0) {
                getPlayers().add(player);
            }
//            else{
//                allPlayers.remove(player);
//            }
        }
        for (int i = getCommunityCards().size() - 1; i >= 0; i--) {
            getCommunityCards().remove(getCommunityCards().get(i));
        }
        PokerGraphics.removeCommunityCard();
        for (Player player : getPlayers()) {
            player.setTotalChipsInPot(0);
            player.setChipsInCurrent(0);
            player.setPocketHand(new PocketHand());
        }
        //communityCardPane = new Pane();
        PokerGraphics.setBurnPile(new Pane());
        setRound(0);
    }

    public void playPoker() {
        newHand();
        setSmallBlindNum(setBlinds());
        findRequiredChips();
        setDeck(new Deck());
        PokerGraphics.displayDeck(getDeck());
        getDeck().shuffle();
        dealPlayers(getDeck());
        PokerGraphics.displayDealPlayers(getPlayers());
        Collections.sort(getPlayers());
        int startPlayer = Poker.findStartingPlayer();
        sortPlayers(startPlayer);
        setCurrentPlayer(getPlayers().get(getPlayers().size() - 1));
        roundOfBetting();
    }

    public static Hand determineHand(PocketHand pocketHand, ArrayList<Card> communityCards) {
        ArrayList<Card> possibleCard = new ArrayList<Card>();
        for (Card communityCard : communityCards) {
            possibleCard.add(communityCard);
        }
        for (Card card : pocketHand.getPocketHand()) {
            possibleCard.add(card);
        }
        Collections.sort(possibleCard);
        Hand highestHand = new Hand();
        int numCard = 0;
        int numHand = 0;
        for (int i = 0; i < possibleCard.size(); i++) {
            for (int j = i + 1; j < possibleCard.size(); j++) {
                Hand tempHand = new Hand();
                for (int chosenCard = 0; chosenCard < possibleCard.size(); chosenCard++) {
                    if (chosenCard != i && chosenCard != j) {
                        tempHand.getHand()[numCard] = possibleCard.get(chosenCard);
                        if (tempHand.compareTo(highestHand) < 0 && numCard == 4) {
                            highestHand = tempHand;
                        }
                        numCard += 1;
                    }
                }
                numHand += 1;
                numCard = 0;
            }

        }
        return highestHand;
    }

    public static void dealPlayers(Deck deck) {
        for (int i = 0; i < 2; i++) {
            for (Player player : getPlayers()) {
                player.getPocketHand().getPocketHand().add(deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
        }
    }

    public static int setBlinds() {
        int smallBlindNum = 0;
        boolean firstTurn = true;
        for (int i = 0; i < getPlayers().size(); i++) {
            if (getPlayers().get(i).getBlind().getTypeBlind().equals("small")) {
                if (i < getPlayers().size() - 2) {
                    getPlayers().get(i + 2).getBlind().setBlindAmount(getPlayers().get(i + 1).getBlind().getBlindAmount());
                    getPlayers().get(i + 2).getBlind().setTypeBlind(getPlayers().get(i + 1).getBlind().getTypeBlind());
                    getPlayers().get(i + 1).getBlind().setBlindAmount(getPlayers().get(i).getBlind().getBlindAmount());
                    getPlayers().get(i + 1).getBlind().setTypeBlind(getPlayers().get(i).getBlind().getTypeBlind());
                    getPlayers().get(i).getBlind().setBlindAmount(0);
                    getPlayers().get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = getPlayers().get(i + 1).getPlayerNum();
                    setPot(getPot() + getPlayers().get(i + 2).getBlind().getBlindAmount());
                    setPot(getPot() + getPlayers().get(i + 1).getBlind().getBlindAmount());
                } else if (i == getPlayers().size() - 2) {
                    getPlayers().get(0).getBlind().setBlindAmount(getPlayers().get(i + 1).getBlind().getBlindAmount());
                    getPlayers().get(0).getBlind().setTypeBlind(getPlayers().get(i + 1).getBlind().getTypeBlind());
                    getPlayers().get(i + 1).getBlind().setBlindAmount(getPlayers().get(i).getBlind().getBlindAmount());
                    getPlayers().get(i + 1).getBlind().setTypeBlind(getPlayers().get(i).getBlind().getTypeBlind());
                    getPlayers().get(i).getBlind().setBlindAmount(0);
                    getPlayers().get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = getPlayers().get(i + 1).getPlayerNum();
                    setPot(getPot() + getPlayers().get(0).getBlind().getBlindAmount());
                    setPot(getPot() + getPlayers().get(i + 1).getBlind().getBlindAmount());
                } else if (i == getPlayers().size() - 1) {
                    getPlayers().get(1).getBlind().setBlindAmount(getPlayers().get(0).getBlind().getBlindAmount());
                    getPlayers().get(1).getBlind().setTypeBlind(getPlayers().get(0).getBlind().getTypeBlind());
                    getPlayers().get(0).getBlind().setBlindAmount(getPlayers().get(i).getBlind().getBlindAmount());
                    getPlayers().get(0).getBlind().setTypeBlind(getPlayers().get(i).getBlind().getTypeBlind());
                    getPlayers().get(i).getBlind().setBlindAmount(0);
                    getPlayers().get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = getPlayers().get(0).getPlayerNum();
                    setPot(getPot() + getPlayers().get(0).getBlind().getBlindAmount());
                    setPot(getPot() + getPlayers().get(1).getBlind().getBlindAmount());

                }

                firstTurn = false;
                break;
            }
        }
        if (firstTurn) {
            getPlayers().get(0).getBlind().setTypeBlind("small");
            getPlayers().get(0).getBlind().setBlindAmount(10);
            getPlayers().get(1).getBlind().setTypeBlind("big");
            getPlayers().get(1).getBlind().setBlindAmount(20);
            smallBlindNum = getPlayers().get(0).getPlayerNum();
            setPot(getPot() + getPlayers().get(0).getBlind().getBlindAmount());
            setPot(getPot() + getPlayers().get(1).getBlind().getBlindAmount());
        }

        for (Player player : getAllPlayers()) {
            player.getPane().getChildren().clear();
            PokerGraphics.addPlayerInfo(player);
        }
        return smallBlindNum;
    }

    public static int findStartingPlayer() {
        int startPlayer = getSmallBlindNum();
        if (getRound() == 0) {
            for (int i = 0; i < getPlayers().size(); i++) {
                if (getPlayers().get(i).getBlind().getTypeBlind().equalsIgnoreCase("big")) {
                    getPlayers().get(i).setChips(getPlayers().get(i).getChips() - getPlayers().get(i).getBlind().getBlindAmount());
                    getPlayers().get(i).setChipsInCurrent(getPlayers().get(i).getBlind().getBlindAmount());
                    getPlayers().get(i).setTotalChipsInPot(getPlayers().get(i).getBlind().getBlindAmount());
                    if (i == getPlayers().size() - 1) {
                        getPlayers().get(i - 1).setChips(getPlayers().get(i - 1).getChips() - getPlayers().get(i - 1).getBlind().getBlindAmount());
                        getPlayers().get(i - 1).setChipsInCurrent(getPlayers().get(i - 1).getBlind().getBlindAmount());
                        getPlayers().get(i - 1).setTotalChipsInPot(getPlayers().get(i - 1).getBlind().getBlindAmount());
                        startPlayer = getPlayers().get(0).getPlayerNum();
                    } else if (i == 0) {
                        getPlayers().get(getPlayers().size() - 1).setChips(getPlayers().get(getPlayers().size() - 1).getChips() - getPlayers().get(getPlayers().size() - 1).getBlind().getBlindAmount());
                        getPlayers().get(getPlayers().size() - 1).setChipsInCurrent(getPlayers().get(getPlayers().size() - 1).getBlind().getBlindAmount());
                        getPlayers().get(getPlayers().size() - 1).setTotalChipsInPot(getPlayers().get(getPlayers().size() - 1).getBlind().getBlindAmount());
                        startPlayer = getPlayers().get(i + 1).getPlayerNum();
                    } else {
                        getPlayers().get(i - 1).setChips(getPlayers().get(i - 1).getChips() - getPlayers().get(i - 1).getBlind().getBlindAmount());
                        getPlayers().get(i - 1).setChipsInCurrent(getPlayers().get(i - 1).getBlind().getBlindAmount());
                        getPlayers().get(i - 1).setTotalChipsInPot(getPlayers().get(i - 1).getBlind().getBlindAmount());
                        startPlayer = getPlayers().get(i + 1).getPlayerNum();
                    }
                    break;
                }
            }
        } else {
            System.out.println(getSmallBlindNum());
            boolean found = false;
            for (int i = 0; i < getAllPlayers().size() - 1; i++) {
                for (Player player : getPlayers()) {
                    int checkNum = getSmallBlindNum() + i;
                    if (checkNum > 8) {
                        checkNum -= 8;
                    }
                    if (player.getPlayerNum() == checkNum) {
                        startPlayer = player.getPlayerNum();
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
        return startPlayer;
    }

    public static int getRequiredChips() {
        return requiredChips;
    }

    public static void setRequiredChips(int requiredChips) {
        Poker.requiredChips = requiredChips;
    }

    public static void findRequiredChips() {
        for (int i = 0; i < getPlayers().size(); i++) {
            if (getPlayers().get(i).getBlind().getTypeBlind().equalsIgnoreCase("big")) {
                setBigBlind(getPlayers().get(i).getBlind().getBlindAmount());
            }
        }
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Poker.players = players;
    }

    public void playTurn() {
        getCurrentPlayer().getPane().getChildren().clear();
        PokerGraphics.updatePlayerAction(getCurrentPlayer(), "Thinking");
        PokerGraphics.displayDealPlayers(getPlayers());
        getCurrentPlayer().setNumTurn(getCurrentPlayer().getNumTurn() + 1);
        if (getPlayers().size() == 1) {
            distributeWin();
            return;
        }
        int playerIndex = 0;
        System.out.println(getCurrentPlayer().getName() + " " + getPot());
        if (getCurrentPlayer() instanceof AI) {
            System.out.println("AI turn " + getRound());
            AI ai = (AI) (getCurrentPlayer());
            int response;
            if (getRound() != 0) {
                //response = ai.rateOfReturn(getCommunityCards(), getPlayers(), getPot(), getRequiredChips(), getBigBlind());
                Task task = new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        Integer response = ai.rateOfReturn(getCommunityCards(), getPlayers(), getPot(), getRequiredChips(), getBigBlind());
                        updateValue(response);
                        return response;
                    }
                };
                Thread th = new Thread(task);
                th.setDaemon(true);
                th.start();
                task.setOnSucceeded(taskFinished);
            } else {
                response = ai.preFlopBetting(getBigBlind(), getRequiredChips());
                if (response == 0 && ai.getChipsInCurrent() != getRequiredChips()) {
                    call(ai, getRequiredChips());
                } else if (response == -1) {
                    if (!(ai.getChipsInCurrent() == requiredChips)) {
                        playerIndex = getPlayers().indexOf(getCurrentPlayer());
                        System.out.println(ai.getName() + " folded");
                        getPlayers().remove(ai);
                        PokerGraphics.displayFold(ai);
                    } else {
                        System.out.println(ai.getName() + " checked");
                    }
                } else if (response == 0) {
                    getCurrentPlayer().getPane().getChildren().clear();
                    PokerGraphics.updatePlayerAction(getCurrentPlayer(), "Check");
                    System.out.println(ai.getName() + " checked");
                } else {
                    int raise = raise(ai, getRequiredChips(), response);
                    setRequiredChips(getRequiredChips() + raise);
                }
                if (response != -1) {
                    playerIndex = getPlayers().indexOf(getCurrentPlayer());
                }
                determiningNextAction(playerIndex);
            }
        } else {
            Casino.getPokerGraphics().createButtons();
        }
    }

    public void aiDecision(int response) {
        if (getCurrentPlayer() instanceof AI) {
            int playerIndex = 0;
            AI ai = (AI) (getCurrentPlayer());
            if (response > 4 && ai.getChips() > 0) {
                raise(ai, getRequiredChips(), response);
                setRequiredChips(getRequiredChips() + response);
            } else if (response == 3 && ai.getChipsInCurrent() < getRequiredChips()) {
                call(ai, getRequiredChips());
            } else if (response == 4 && ai.getChipsInCurrent() < getRequiredChips()) {
                playerIndex = getPlayers().indexOf(getCurrentPlayer());
                System.out.println(ai.getName() + " folded");
                PokerGraphics.displayFold(ai);
                getPlayers().remove(ai);
            } else {
                getCurrentPlayer().getPane().getChildren().clear();
                PokerGraphics.updatePlayerAction(getCurrentPlayer(), "Check");
                System.out.println(ai.getName() + " checked");
            }
            if (response != 4) {
                playerIndex = getPlayers().indexOf(getCurrentPlayer());
            }
            determiningNextAction(playerIndex);
        }
    }

    public void determiningNextAction(int playerIndex) {
        boolean everyoneAllIn = false;
        boolean waitForAi = false;
        allPlayerCheck = false;
        PokerGraphics.displayPot();
        if (playerIndex > 0) {
            setCurrentPlayer(getPlayers().get(playerIndex - 1));
        } else {
            setCurrentPlayer(getPlayers().get(getPlayers().size() - 1));
        }
        int numPlayerNotAllIn = 0;
        for (Player player : getPlayers()) {
            if (player.getChips() > 0) {
                numPlayerNotAllIn += 1;
            }
        }
        if (numPlayerNotAllIn <= 1 && (currentPlayer.getChipsInCurrent() == requiredChips || currentPlayer.getChips() == 0)) {
            everyoneAllIn = true;
        }
        if (!(everyoneAllIn)) {
            if (getCurrentPlayer().getChipsInCurrent() == getRequiredChips() && getRequiredChips() == 0 && getCurrentPlayer().getNumTurn() == 0) {
                waitForAi = true;
                playTurn();
            }
            if (getCurrentPlayer().getChipsInCurrent() != getRequiredChips() || (getRound() == 0 && getCurrentPlayer().getBlind().getTypeBlind().equalsIgnoreCase("big"))) {
                waitForAi = true;
                playTurn();

            } else {
                if (!(waitForAi)) {
                    System.out.println(communityCards.size());
                    if (getPlayers().size() == 1) {
                        distributeWin();
                    }
                    if (getCommunityCards().size() == 0) {
                        flop();
                        setRound(getRound() + 1);
                        Collections.sort(getPlayers());
                        int startPlayer = findStartingPlayer();
                        sortPlayers(startPlayer);
                        setCurrentPlayer(getPlayers().get(getPlayers().size() - 1));
                        allPlayerCheck = true;
                        roundOfBetting();
                    } else if (Poker.getCommunityCards().size() == 3 && !(allPlayerCheck)) {
                        turnAndRiver();
                        setRound(getRound() + 1);
                        PokerGraphics.displayTurn(getCommunityCards());
                        Collections.sort(getPlayers());
                        int startPlayer = findStartingPlayer();
                        sortPlayers(startPlayer);
                        setCurrentPlayer(getPlayers().get(getPlayers().size() - 1));
                        allPlayerCheck = true;
                        roundOfBetting();
                    } else if (Poker.getCommunityCards().size() == 4 && !(allPlayerCheck)) {
                        turnAndRiver();
                        setRound(getRound() + 1);
                        PokerGraphics.displayRiver(getCommunityCards());
                        Collections.sort(getPlayers());
                        int startPlayer = findStartingPlayer();
                        sortPlayers(startPlayer);
                        setCurrentPlayer(getPlayers().get(getPlayers().size() - 1));
                        allPlayerCheck = true;
                        roundOfBetting();
                    } else if (Poker.getCommunityCards().size() == 5 && !(allPlayerCheck)) {
                        setRound(getRound() + 1);
                        System.out.println("entered");
                        PokerGraphics.displayAllCards(getPlayers());
                    }
                }
            }
        } else {
            if (getPlayers().size() == 1) {
                distributeWin();
            } else {
                PokerGraphics.displayAllCardsAllIn(getPlayers());
                if (getCommunityCards().size() == 0) {
                    flop();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            turnAndRiver();
                            PokerGraphics.displayTurn(communityCards);
                        }
                    }));
                    timeline.play();
                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            turnAndRiver();
                            PokerGraphics.displayRiver(communityCards);
                        }
                    }));
                    timeline1.play();
                }
                else if (Poker.getCommunityCards().size() == 3) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            turnAndRiver();
                            PokerGraphics.displayTurn(communityCards);
                        }
                    }));
                    timeline.play();
                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            turnAndRiver();
                            PokerGraphics.displayRiver(communityCards);
                        }
                    }));
                    timeline1.play();
                } else if (Poker.getCommunityCards().size() == 4) {
                    Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            turnAndRiver();
                            PokerGraphics.displayRiver(communityCards);
                        }
                    }));
                    timeline1.play();
                }
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        distributeWin();
                    }
                }));
                timeline2.play();
            }
        }
    }

    public void roundOfBetting() {
        for (Player player : getPlayers()) {
            player.getPane().getChildren().clear();
            PokerGraphics.addPlayerInfo(player);
        }
        System.out.println("new round");
        PokerGraphics.displayPot();
        setRequiredChips(0);
        if (getRound() == 0) {
            setRequiredChips(getBigBlind());
        } else {
            for (Player player : getPlayers()) {
                player.setNumTurn(0);
                player.setChipsInCurrent(0);
            }
        }
        playTurn();
    }

    public static void flop() {
        PokerGraphics.displayBurn();
        getDeck().getDeck().remove(0);
        for (int i = 0; i < 3; i++) {
            getCommunityCards().add(getDeck().getDeck().get(0));
            getDeck().getDeck().remove(0);
        }
        PokerGraphics.displayFlop(getCommunityCards());
    }

    public static void turnAndRiver() {
        PokerGraphics.displayBurn();
        getDeck().getDeck().remove(0);
        getCommunityCards().add(getDeck().getDeck().get(0));
        getDeck().getDeck().remove(0);
    }

    public static int raise(Player player, int requiredChips, int raise) {
        if (player.getChipsInCurrent() < requiredChips) {
            call(player, requiredChips);
        }
        if (player.getChips() >= raise) {
            System.out.println(raise);
            System.out.println(player.getChips());
            pot += raise;
            player.setChips(player.getChips() - raise);
            System.out.println(player.getChips());
            player.setChipsInCurrent(player.getChipsInCurrent() + raise);
            player.setTotalChipsInPot(player.getTotalChipsInPot() + raise);
        } else {
            raise = player.getChips();
            pot += player.getChips();
            player.setChips(0);
            player.setChipsInCurrent(player.getChipsInCurrent() + player.getChips());
            player.setTotalChipsInPot(player.getTotalChipsInPot() + player.getChips());
        }
        getCurrentPlayer().getPane().getChildren().clear();
        PokerGraphics.updatePlayerAction(getCurrentPlayer(), "Raised: " + raise);
        System.out.println(player.getName() + " raised " + raise);
        return raise;
    }

    public static void call(Player player, int requiredChips) {
        int callAmount;
        callAmount = requiredChips - player.getChipsInCurrent();
        if (callAmount <= player.getChips()) {
            setPot(getPot() + callAmount);
            if (callAmount == player.getChips()) {
                System.out.println("All In");
            }
            player.setChips(player.getChips() - callAmount);
            player.setChipsInCurrent(player.getChipsInCurrent() + callAmount);
            player.setTotalChipsInPot(player.getTotalChipsInPot() + callAmount);
            getCurrentPlayer().getPane().getChildren().clear();
            PokerGraphics.updatePlayerAction(getCurrentPlayer(), "Called: " + callAmount);
            System.out.println(player.getName() + " called");

        } else {
            setPot(getPot() + player.getChips());
            player.setChipsInCurrent(player.getChipsInCurrent() + player.getChips());
            player.setTotalChipsInPot(player.getTotalChipsInPot() + player.getChips());
            player.setChips(0);
            getCurrentPlayer().getPane().getChildren().clear();
            PokerGraphics.updatePlayerAction(getCurrentPlayer(), "All In");
            System.out.println("All In");
        }
    }

    public static void sortPlayers(int startPlayer) {
        ArrayList<Player> sortedPlayers = new ArrayList<Player>();
        for (int i = 0; i < getPlayers().size(); i++) {
            if (getPlayers().get(i).getPlayerNum() == startPlayer) {
                if (i == 0) {
                    for (int j = 1; j <= getPlayers().size(); j++) {
                        sortedPlayers.add(getPlayers().get(getPlayers().size() - j));

                    }
                } else {
                    for (int j = 1; j <= getPlayers().size(); j++) {
                        sortedPlayers.add(getPlayers().get(i - 1));
                        i -= 1;
                        if (i <= 0) {
                            i = getPlayers().size();
                        }
                    }
                }

                break;
            }

        }
        setPlayers(sortedPlayers);
    }

    public void distributeWin() {
        System.out.println("distributed the wealth");
        boolean sidePot = false;
        Hand winningHand = new Hand();
        Player winningPlayer = Casino.getMainPlayer();
        if (getPlayers().size() == 1) {
            winningPlayer = getPlayers().get(0);
            getPlayers().get(0).setChips(getPlayers().get(0).getChips() + pot);
            setPot(0);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    playPoker();
                }
            }));
            timeline.play();
        } else {
            for (Player player : getPlayers()) {
                player.setHand(determineHand(player.getPocketHand(), getCommunityCards()));
                if (player.getHand().compareTo(winningHand) < 0) {
                    winningPlayer = player;
                    winningHand = player.getHand();
                }
            }
            int remainingPot = 0;
            for (Player player : getPlayers()) {
                if (player.getTotalChipsInPot() > winningPlayer.getTotalChipsInPot()) {
                    pot = pot - (player.getTotalChipsInPot() - winningPlayer.getTotalChipsInPot());
                    remainingPot += (player.getTotalChipsInPot() - winningPlayer.getTotalChipsInPot());
                    sidePot = true;
                }
            }
            winningPlayer.setChips(winningPlayer.getChips() + getPot());
            players.remove(winningPlayer);
            setPot(0 + remainingPot);
            if (sidePot) {
                distributeWin();
            }
            System.out.println(winningPlayer.getName() + " Chips: " + winningPlayer.getChips());
            playPoker();
        }

    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public static ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public static int getBigBlind() {
        return bigBlind;
    }

    public static int getRound() {
        return round;
    }

    public static void setAllPlayers(ArrayList<Player> allPlayers) {
        Poker.allPlayers = allPlayers;
    }

    public static void setCommunityCards(ArrayList<Card> communityCards) {
        Poker.communityCards = communityCards;
    }

    public static void setBigBlind(int bigBlind) {
        Poker.bigBlind = bigBlind;
    }

    public static void setRound(int round) {
        Poker.round = round;
    }

    public static Deck getDeck() {
        return deck;
    }

    public static void setDeck(Deck deck) {
        Poker.deck = deck;
    }

    public static int getSmallBlindNum() {
        return smallBlindNum;
    }

    public static void setSmallBlindNum(int smallBlindNum) {
        Poker.smallBlindNum = smallBlindNum;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Poker.currentPlayer = currentPlayer;
    }

    EventHandler taskFinished = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.out.println("Task Finished");
            Task task = (Task) (event.getSource());
            Integer response = (Integer) (task.getValue());
            aiDecision(response.intValue());
        }

    };

    /**
     * @return the pot
     */
    public static int getPot() {
        return pot;
    }

    /**
     * @param aPot the pot to set
     */
    public static void setPot(int aPot) {
        pot = aPot;
    }

    /**
     * @return the rand
     */
    public static Random getRand() {
        return rand;
    }

    /**
     * @param aRand the rand to set
     */
    public static void setRand(Random aRand) {
        rand = aRand;
    }
}
