package casino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BlackJackGraphics {

    public static Player currentPlayer;
    static Pane blkJckBtns = new VBox(2);
    private static Button stay = new Button("Stay");
    private static Button dDown = new Button("Double Down");
    private static Button split = new Button("Split");
    private static Button hit = new Button("Hit");
    private static Button btnBet;
    private static Pane root = new Pane();
    private static int round = 1, bet, x = 0;
    private static Pane pBet = new HBox();
    static Pane buttons = new HBox();
    static ImagePattern ip;
    static double chipSize = 17;
    static Text betText;
    static int betAmount;
    static Pane betPane;
    static HBox dealerCard = new HBox();
    static ArrayList<HBox> pCard = new ArrayList<>();
    static ArrayList<Pane> playerInfo = new ArrayList<>();
    static Pane dealerPane = new Pane();

    public BlackJackGraphics() {
    }

    public static Card displayCard(Card card) {
        Image image = null;
        ImagePattern ip = null;
        //Pane cPane = new Pane();

        String cardSuit = card.getSuit();
        int cardValue = card.getValue();

        if (card.isFaceUp() == false) {
            image = ImageBuffer.back1;
        } else {
            card.setFaceUp(true);
            //<editor-fold defaultstate="collapsed" desc="Setting the image for each suit/value">
            if (cardSuit.equalsIgnoreCase("Spade")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.spadeA;
                        break;
                    case 2:
                        image = ImageBuffer.spade2;
                        break;
                    case 3:
                        image = ImageBuffer.spade3;
                        break;
                    case 4:
                        image = ImageBuffer.spade4;
                        break;
                    case 5:
                        image = ImageBuffer.spade5;
                        break;
                    case 6:
                        image = ImageBuffer.spade6;
                        break;
                    case 7:
                        image = ImageBuffer.spade7;
                        break;
                    case 8:
                        image = ImageBuffer.spade8;
                        break;
                    case 9:
                        image = ImageBuffer.spade9;
                        break;
                    case 10:
                        image = ImageBuffer.spade10;
                        break;
                    case 11:
                        image = ImageBuffer.spadeJ;
                        break;
                    case 12:
                        image = ImageBuffer.spadeQ;
                        break;
                    case 13:
                        image = ImageBuffer.spadeK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else if (cardSuit.equalsIgnoreCase("Club")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.clubsA;
                        break;
                    case 2:
                        image = ImageBuffer.clubs2;
                        break;
                    case 3:
                        image = ImageBuffer.clubs3;
                        break;
                    case 4:
                        image = ImageBuffer.clubs4;
                        break;
                    case 5:
                        image = ImageBuffer.clubs5;
                        break;
                    case 6:
                        image = ImageBuffer.clubs6;
                        break;
                    case 7:
                        image = ImageBuffer.clubs7;
                        break;
                    case 8:
                        image = ImageBuffer.clubs8;
                        break;
                    case 9:
                        image = ImageBuffer.clubs9;
                        break;
                    case 10:
                        image = ImageBuffer.clubs10;
                        break;
                    case 11:
                        image = ImageBuffer.clubsJ;
                        break;
                    case 12:
                        image = ImageBuffer.clubsQ;
                        break;
                    case 13:
                        image = ImageBuffer.clubsK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else if (cardSuit.equalsIgnoreCase("Diamond")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.diamondA;
                        break;
                    case 2:
                        image = ImageBuffer.diamond2;
                        break;
                    case 3:
                        image = ImageBuffer.diamond3;
                        break;
                    case 4:
                        image = ImageBuffer.diamond4;
                        break;
                    case 5:
                        image = ImageBuffer.diamond5;
                        break;
                    case 6:
                        image = ImageBuffer.diamond6;
                        break;
                    case 7:
                        image = ImageBuffer.diamond7;
                        break;
                    case 8:
                        image = ImageBuffer.diamond8;
                        break;
                    case 9:
                        image = ImageBuffer.diamond9;
                        break;
                    case 10:
                        image = ImageBuffer.diamond10;
                        break;
                    case 11:
                        image = ImageBuffer.diamondJ;
                        break;
                    case 12:
                        image = ImageBuffer.diamondQ;
                        break;
                    case 13:
                        image = ImageBuffer.diamondK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else if (cardSuit.equalsIgnoreCase("Heart")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.heartA;
                        break;
                    case 2:
                        image = ImageBuffer.heart2;
                        break;
                    case 3:
                        image = ImageBuffer.heart3;
                        break;
                    case 4:
                        image = ImageBuffer.heart4;
                        break;
                    case 5:
                        image = ImageBuffer.heart5;
                        break;
                    case 6:
                        image = ImageBuffer.heart6;
                        break;
                    case 7:
                        image = ImageBuffer.heart7;
                        break;
                    case 8:
                        image = ImageBuffer.heart8;
                        break;
                    case 9:
                        image = ImageBuffer.heart9;
                        break;
                    case 10:
                        image = ImageBuffer.heart10;
                        break;
                    case 11:
                        image = ImageBuffer.heartJ;
                        break;
                    case 12:
                        image = ImageBuffer.heartQ;
                        break;
                    case 13:
                        image = ImageBuffer.heartK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else {
                image = ImageBuffer.blank;
            }
//</editor-fold>
        }
        ip = new ImagePattern(image);
        //card.setIp(ip);
        card.setFill(ip);

        return card;
    }

    public static void printCard(int handNum) throws InterruptedException, IOException {
        pCard.get(x).getChildren().clear();
        for (int i = 0; i < currentPlayer.getPocketHands().get(handNum).getPlayerHand().size(); i++) {
            Card card = currentPlayer.getPocketHands().get(handNum).getPlayerHand().get(i);
            if (!card.isFaceUp()) {
                System.out.println("flipped");
                card.setFaceUp(true);
            }
            pCard.get(x).getChildren().add(displayCard(card));
            pCard.get(x).setSpacing(-50);
            pCard.get(x).setTranslateX(currentPlayer.getPane().getTranslateX());
            pCard.get(x).setTranslateY(currentPlayer.getPane().getTranslateY());
        }
        if (root.getChildren().contains(pCard.get(x))) {
            root.getChildren().remove(pCard.get(x));
        }
        root.getChildren().add(pCard.get(x));
        setButtons(handNum);
    }

    public static void setButtonSize() {
        hit.setMinSize(90, 90);
        stay.setMinSize(90, 90);
        split.setMinSize(90, 90);
        dDown.setMinSize(90, 90);
    }

    public static void checkInsuranceWin() {
        if (BlackjackJAVA.dealer.getDealerHand().getPlayerHand().get(0).getWorth() == 1 && BlackjackJAVA.dealer.getDealerHand().getPlayerHand().get(1).getWorth() == 10) {
            for (int i = 0; i < BlackjackJAVA.numOfPlayers.size(); i++) {
                if (BlackjackJAVA.numOfPlayers.get(i).isInsurance()) {
                    BlackjackJAVA.numOfPlayers.get(i).setChips(BlackjackJAVA.numOfPlayers.get(i).getChips() + BlackjackJAVA.numOfPlayers.get(i).getInsuranceAmount() * 3);
                    //System.out.println("Player " + numOfPlayers.get(i).getName() + " insurance paid off! Chips: " + numOfPlayers.get(i).getChips());
                }
            }
        }
    }

    public static void checkWin() throws IOException {
        checkInsuranceWin();
        if (BlackjackJAVA.dealer.isBust()) {
            for (int i = 0; i < BlackjackJAVA.numOfPlayers.size(); i++) {
                for (int s = 0; s < BlackjackJAVA.numOfPlayers.get(i).getPocketHands().size(); s++) {
                    if (BlackjackJAVA.numOfPlayers.get(i).getTotal(s) <= 21 && !BlackjackJAVA.numOfPlayers.get(i).isNaturalBlackJack()) {
                        BlackjackJAVA.numOfPlayers.get(i).setChips(BlackjackJAVA.numOfPlayers.get(i).getChips() + BlackjackJAVA.numOfPlayers.get(i).getBet() * 2);
                        setWin(i, 0);
                    } else if (BlackjackJAVA.numOfPlayers.get(i).getTotal(s) > 21) {
                        setWin(i, 1);
                    }
                }
            }
        } else {
            for (int i = 0; i < BlackjackJAVA.numOfPlayers.size(); i++) {
                for (int s = 0; s < BlackjackJAVA.numOfPlayers.get(i).getPocketHands().size(); s++) {
                    if (BlackjackJAVA.numOfPlayers.get(i).setTotal(s) > BlackjackJAVA.numOfPlayers.get(i).getTotal(s) && BlackjackJAVA.numOfPlayers.get(i).setTotal(s) <= 21) {
                        BlackjackJAVA.numOfPlayers.get(i).setObTotal(BlackjackJAVA.numOfPlayers.get(i).setTotal(s));
                    } else {
                        BlackjackJAVA.numOfPlayers.get(i).setObTotal(BlackjackJAVA.numOfPlayers.get(i).getTotal(s));
                    }
                    if (BlackjackJAVA.numOfPlayers.get(i).getTotal(s) > 21) {
                        setWin(i, 1);
                    }
                    if (!BlackjackJAVA.numOfPlayers.get(i).isNaturalBlackJack() && BlackjackJAVA.numOfPlayers.get(i).getTotal() <= 21) {
                        if (BlackjackJAVA.dealer.getTotal() > BlackjackJAVA.numOfPlayers.get(i).getTotal()) {
                            setWin(i, 1);
                            BlackjackJAVA.numOfPlayers.get(i).setBet(0);
                        } else if (BlackjackJAVA.dealer.getTotal() == BlackjackJAVA.numOfPlayers.get(i).getTotal()) {
                            setWin(i, 2);
                            BlackjackJAVA.numOfPlayers.get(i).setChips(BlackjackJAVA.numOfPlayers.get(i).getChips() + BlackjackJAVA.numOfPlayers.get(i).getBet());
                        } else if (BlackjackJAVA.dealer.getTotal() < BlackjackJAVA.numOfPlayers.get(i).getTotal()) {
                            setWin(i, 0);
                            BlackjackJAVA.numOfPlayers.get(i).setChips(BlackjackJAVA.numOfPlayers.get(i).getChips() + BlackjackJAVA.numOfPlayers.get(i).getBet() * 2);
                        }
                    }
                }
            }
        }
    }

    public static void setWin(int i, int win) {
        Text name = new Text(BlackjackJAVA.numOfPlayers.get(i).getName());
        Text chips = new Text("Chips: $" + BlackjackJAVA.numOfPlayers.get(i).getChips());
        Text bet = new Text("Bet: $" + BlackjackJAVA.numOfPlayers.get(i).getBet());
        Text condition = new Text();
        if (win == 0) {
            condition.setText("Win!");
        } else if (win == 1) {
            condition.setText("Lose!");
        } else if (win == 2) {
            condition.setText("Stands!");
        }
        name.setFill(Color.WHITE);
        chips.setFill(Color.WHITE);
        bet.setFill(Color.WHITE);
        condition.setFill(Color.WHITE);
        playerInfo.get(i).setTranslateX(BlackjackJAVA.numOfPlayers.get(i).getPane().getTranslateX());
        playerInfo.get(i).setTranslateY(BlackjackJAVA.numOfPlayers.get(i).getPane().getTranslateY() + 200);
        chips.setY(20);
        bet.setY(40);
        condition.setX(60);
        playerInfo.get(i).getChildren().clear();
        playerInfo.get(i).getChildren().addAll(name, chips, bet, condition);
    }

    public static void setThinking() {
        Text name = new Text(BlackjackJAVA.numOfPlayers.get(x).getName());
        Text chips = new Text("Chips: $" + BlackjackJAVA.numOfPlayers.get(x).getChips());
        Text bet = new Text("Bet: $" + BlackjackJAVA.numOfPlayers.get(x).getBet());
        Text thinking = new Text("Thinking...");
        name.setFill(Color.WHITE);
        chips.setFill(Color.WHITE);
        bet.setFill(Color.WHITE);
        thinking.setFill(Color.WHITE);
        playerInfo.get(x).setTranslateX(BlackjackJAVA.numOfPlayers.get(x).getPane().getTranslateX());
        playerInfo.get(x).setTranslateY(BlackjackJAVA.numOfPlayers.get(x).getPane().getTranslateY() + 200);
        chips.setY(20);
        bet.setY(40);
        thinking.setX(40);
        playerInfo.get(x).getChildren().clear();
        playerInfo.get(x).getChildren().addAll(name, chips, bet, thinking);
    }

    public static void setButtons(int handNum) throws InterruptedException, IOException {
        setButtonSize();
        clearBtn();
        if (!currentPlayer.isStay()) {
            System.out.println(currentPlayer.getName());
            if (currentPlayer.isAi()) {
                setThinking();
                BlackjackAI ai = (BlackjackAI) currentPlayer;
                ai.setSplit();
                ai.setdDown(handNum);
                ai.setHit(BlackjackJAVA.deck, handNum);
                if (round == 1) {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setNaturalBlackJack(true);
                        setWin(x, 0);
                        currentPlayer.setStay(true);
                        currentPlayer.setChips((int) (currentPlayer.getBet() * 1.5 + currentPlayer.getChips() + currentPlayer.getBet()));
                        printCard(handNum);
                    }
                    if (ai.isSplit()) {
                        System.out.println("Split");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    BlackjackJAVA.playerSplit(currentPlayer, 0);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }));
                        timeline.play();
                    } else if (ai.isdDown()) {
                        System.out.println("dd");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    BlackjackJAVA.playerDD(currentPlayer, 0);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }));
                        timeline.play();
                    } else if (ai.isHit()) {
                        System.out.println("hit");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    BlackjackJAVA.playerHit(currentPlayer, 0);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }));
                        timeline.play();
                    } else if (!ai.isHit()) {
                        System.out.println("stay");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    currentPlayer.setStay(true);
                                    setButtons(0);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }));
                        timeline.play();
                    }
                } else {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setStay(true);
                        printCard(handNum);
                    } else if (ai.isHit()) {
                        System.out.println("hit");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    BlackjackJAVA.playerHit(currentPlayer, 0);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }));
                        timeline.play();
                    } else if (!ai.isHit()) {
                        System.out.println("stay");
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    currentPlayer.setStay(true);
                                    setButtons(0);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }));
                        timeline.play();
                    }
                }
            } else {
                if (round == 1) {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setNaturalBlackJack(true);
                        setWin(x, 0);
                        currentPlayer.setStay(true);
                        currentPlayer.setChips((int) (currentPlayer.getBet() * 1.5 + currentPlayer.getChips() + currentPlayer.getBet()));
                        printCard(handNum);
                    } else if (currentPlayer.getTotal(handNum) >= 9 && currentPlayer.getTotal(handNum) <= 11) {
                        if (currentPlayer.getPocketHands().get(0).checkSplit()) {
                            buttons.getChildren().addAll(hit, stay, split, dDown);
                        } else {
                            buttons.getChildren().addAll(hit, stay, dDown);
                        }
                    } else if (currentPlayer.getPocketHands().get(0).checkSplit()) {
                        buttons.getChildren().addAll(hit, stay, split);
                    } else {
                        buttons.getChildren().addAll(hit, stay);
                    }
                } else {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setStay(true);
                        printCard(handNum);
                    } else {
                        buttons.getChildren().addAll(hit, stay);
                    }
                }
                buttons.setTranslateX(100);
                buttons.setTranslateY(900);
                root.getChildren().add(buttons);
            }
        } else if (currentPlayer.isSplit()) {
            if (handNum == 0) {
                currentPlayer.setStay(false);
                currentPlayer.setSplit(false);
                printCard(1);
            } else {
                clearBtn();
                nextPlayer();
            }
        } else {
            clearBtn();
            nextPlayer();
        }

        round++;
    }

    public static void clearBtn() {
        buttons.getChildren().clear();
        if (root.getChildren().contains(buttons)) {
            root.getChildren().remove(buttons);
        }
    }

    public static void setDealerThinking() {
        dealerPane.getChildren().clear();
        Text dealer = new Text("Dealer");
        Text thinking = new Text("Thinking");
        dealer.setFill(Color.WHITE);
        thinking.setFill(Color.WHITE);
        dealer.setY(-40);
        thinking.setY(-20);
        dealerPane.getChildren().addAll(dealer, thinking);
        dealerPane.setTranslateX(BlackjackJAVA.dealer.getPane().getTranslateX());
        dealerPane.setTranslateY(BlackjackJAVA.dealer.getPane().getTranslateY());
        if (root.getChildren().contains(dealerPane)) {
            root.getChildren().remove(dealerPane);
        }
        root.getChildren().add(dealerPane);
    }

    public static void printDealer() throws InterruptedException, IOException {
        dealerCard.getChildren().clear();
        setDealerThinking();
        for (int i = 0; i < BlackjackJAVA.dealer.getDealerHand().getPlayerHand().size(); i++) {
            Card card = BlackjackJAVA.dealer.getDealerHand().getPlayerHand().get(i);
            card.setFaceUp(true);
            dealerCard.getChildren().add(displayCard(card));
            dealerCard.setSpacing(-50);
            dealerCard.setTranslateX(BlackjackJAVA.dealer.getPane().getTranslateX());
            dealerCard.setTranslateY(BlackjackJAVA.dealer.getPane().getTranslateY());
        }
        if (root.getChildren().contains(dealerCard)) {
            root.getChildren().remove(dealerCard);
        }
        root.getChildren().add(dealerCard);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (BlackjackJAVA.dealer.checkSeventeen()) {
                        setDealerBox();
                        checkWin();
                    } else {
                        BlackjackJAVA.playDealer();
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        timeline.play();

    }

    public static void nextPlayer() throws InterruptedException, IOException {
        round = 1;
        x += 1;
        resetInfo(x - 1);
        if (x == BlackjackJAVA.numOfPlayers.size()) {
            x = 0;
            currentPlayer = BlackjackJAVA.numOfPlayers.get(x);
            BlackjackJAVA.playDealer();
        } else {
            currentPlayer = BlackjackJAVA.numOfPlayers.get(x);
            pCard.add(new HBox());
            printCard(0);
        }
    }

    public static void begin(String name) throws IOException, InterruptedException {
        root = new Pane();
        hit.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerHit(currentPlayer, 0);

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        stay.setOnAction((ActionEvent event) -> {
            try {
                currentPlayer.setStay(true);
                setButtons(0);

            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        split.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerSplit(currentPlayer, 0);
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        dDown.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerDD(currentPlayer, 0);
                currentPlayer.setStay(true);
                setButtons(0);

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        ImagePattern ip = new ImagePattern(ImageBuffer.pokerTable);
        ImageView pTable = new ImageView();
        pTable.setImage(ImageBuffer.pokerTable);
        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> Casino.primaryStage.setScene(Casino.menu));
        pTable.setFitHeight(1080);
        pTable.setFitWidth(1920);
        pTable.setX(0);
        pTable.setY(-40);
        Scene scene = new Scene(root, 1920, 1080);
        root.getChildren().addAll(pTable, backBtn);
        Casino.primaryStage.setTitle("BLACKJACK!");
        Casino.primaryStage.setScene(scene);
        Casino.primaryStage.show();
        BlackjackJAVA.main(name);
    }

    public static void setDealerBox() {
        dealerPane.getChildren().clear();
        Text dealer = new Text("Dealer");
        dealer.setFill(Color.WHITE);
        dealer.setY(-40);
        dealerPane.getChildren().add(dealer);
        dealerPane.setTranslateX(BlackjackJAVA.dealer.getPane().getTranslateX());
        dealerPane.setTranslateY(BlackjackJAVA.dealer.getPane().getTranslateY());
        if (root.getChildren().contains(dealerPane)) {
            root.getChildren().remove(dealerPane);
        }
        root.getChildren().add(dealerPane);
    }

    public static void printBeginDealer() {
        for (int i = 0; i < BlackjackJAVA.dealer.getDealerHand().getPlayerHand().size(); i++) {
            Card card = BlackjackJAVA.dealer.getDealerHand().getPlayerHand().get(i);
            if (i == 0) {
                card.setFaceUp(true);
            }
            dealerCard.getChildren().add(displayCard(card));
            dealerCard.setSpacing(-50);
            dealerCard.setTranslateX(BlackjackJAVA.dealer.getPane().getTranslateX());
            dealerCard.setTranslateY(BlackjackJAVA.dealer.getPane().getTranslateY());
        }
        if (root.getChildren().contains(dealerCard)) {
            root.getChildren().remove(dealerCard);
        }
        setDealerBox();
        root.getChildren().addAll(dealerCard);
        if (BlackjackJAVA.dealer.checkInsured()) {
            showInsurance(0);
        }
    }

    public static void getInsurance(int n) {
        BlackjackJAVA.numOfPlayers.get(n).setInsurance(true);

        BlackjackJAVA.numOfPlayers.get(n).setInsuranceAmount();

    }

    public static void showInsurance(int n) {
        if (BlackjackJAVA.numOfPlayers.get(n).getChips() >= BlackjackJAVA.numOfPlayers.get(n).getBet() / 2) {
            if (BlackjackJAVA.numOfPlayers.get(n).isAi()) {
                BlackjackAI ai = ((BlackjackAI) (BlackjackJAVA.numOfPlayers.get(n)));
                if (ai.isInsurance()) {
                    getInsurance(n);
                }
            } else { //add insurnace buttons pane to root
            }
        } else { //not enough chips
        }
        if (n == BlackjackJAVA.numOfPlayers.size() - 1) {
            showInsurance(n - 1);
        }
    }

    public static void printBoard() throws InterruptedException, IOException {    // When I make the board. need to print the players cards
        ArrayList<HBox> boardCards = new ArrayList<>();
        if (root.getChildren().contains(boardCards)) {
            root.getChildren().remove(boardCards);
        }
        for (int c = 0; c < BlackjackJAVA.numOfPlayers.size(); c++) {

            for (int i = 0; i < BlackjackJAVA.numOfPlayers.get(c).getPocketHands().get(0).getPlayerHand().size(); i++) {
                boardCards.add(new HBox());
                Card card = BlackjackJAVA.numOfPlayers.get(c).getPocketHands().get(0).getPlayerHand().get(i);
                if (!card.isFaceUp()) {
                    System.out.println("flipped");
                    card.setFaceUp(true);
                }
                boardCards.get(c).getChildren().add(displayCard(card));
                boardCards.get(c).setSpacing(-50);
                boardCards.get(c).setTranslateX(BlackjackJAVA.numOfPlayers.get(c).getPane().getTranslateX());
                boardCards.get(c).setTranslateY(BlackjackJAVA.numOfPlayers.get(c).getPane().getTranslateY());

            }
        }
        root.getChildren().addAll(boardCards);
        pCard.add(new HBox());
        printCard(0);
    }

    public static void printBlankBoard() throws InterruptedException, IOException {    // When I make the board. need to print the players cards
        printBeginDealer();
        ArrayList<HBox> boardCards = new ArrayList<>();
        if (root.getChildren().contains(boardCards)) {
            root.getChildren().remove(boardCards);
        }
        for (int c = 0; c < BlackjackJAVA.numOfPlayers.size(); c++) {

            for (int i = 0; i < BlackjackJAVA.numOfPlayers.get(c).getPocketHands().get(0).getPlayerHand().size(); i++) {
                boardCards.add(new HBox());
                Card card = BlackjackJAVA.numOfPlayers.get(c).getPocketHands().get(0).getPlayerHand().get(i);
                boardCards.get(c).getChildren().add(displayCard(card));
                boardCards.get(c).setSpacing(-50);
                boardCards.get(c).setTranslateX(BlackjackJAVA.numOfPlayers.get(c).getPane().getTranslateX());
                boardCards.get(c).setTranslateY(BlackjackJAVA.numOfPlayers.get(c).getPane().getTranslateY());

            }
        }
        root.getChildren().addAll(boardCards);
        pCard.add(new HBox());
        printPlayerInfo();
        setBet(0);
    }

    public static void printPlayerInfo() {
        if (root.getChildren().contains(playerInfo)) {
            root.getChildren().remove(playerInfo);
        }
        for (int i = 0; i < BlackjackJAVA.numOfPlayers.size(); i++) {
            playerInfo.add(new Pane());
            Text name = new Text(BlackjackJAVA.numOfPlayers.get(i).getName());
            Text chips = new Text("Chips: $" + BlackjackJAVA.numOfPlayers.get(i).getChips());
            Text bet = new Text("Bet: $" + BlackjackJAVA.numOfPlayers.get(i).getBet());
            name.setFill(Color.WHITE);
            chips.setFill(Color.WHITE);
            bet.setFill(Color.WHITE);
            playerInfo.get(i).setTranslateX(BlackjackJAVA.numOfPlayers.get(i).getPane().getTranslateX());
            playerInfo.get(i).setTranslateY(BlackjackJAVA.numOfPlayers.get(i).getPane().getTranslateY() + 200);
            chips.setY(20);
            bet.setY(40);
            playerInfo.get(i).getChildren().addAll(name, chips, bet);
        }
        root.getChildren().addAll(playerInfo);

    }

    public static void resetInfo(int n) {
        Text name = new Text(BlackjackJAVA.numOfPlayers.get(n).getName());
        Text chips = new Text("Chips: $" + BlackjackJAVA.numOfPlayers.get(n).getChips());
        Text bet = new Text("Bet: $" + BlackjackJAVA.numOfPlayers.get(n).getBet());
        name.setFill(Color.WHITE);
        chips.setFill(Color.WHITE);
        bet.setFill(Color.WHITE);
        playerInfo.get(n).setTranslateX(BlackjackJAVA.numOfPlayers.get(n).getPane().getTranslateX());
        playerInfo.get(n).setTranslateY(BlackjackJAVA.numOfPlayers.get(n).getPane().getTranslateY() + 200);
        chips.setY(20);
        bet.setY(40);
        playerInfo.get(n).getChildren().clear();
        playerInfo.get(n).getChildren().addAll(name, chips, bet);
    }

    public static void setBet(int n) throws InterruptedException, IOException {
        if (root.getChildren().contains(betPane)) {
            root.getChildren().remove(betPane);
        }
        betPane = new Pane();
        currentPlayer = BlackjackJAVA.numOfPlayers.get(n);
        if (currentPlayer.isAi()) {
            BlackjackAI ai = (BlackjackAI) currentPlayer;
            ai.setRealBet();
            ai.setBet(ai.getRealBet());
            System.out.println(currentPlayer.getName() + " bet " + currentPlayer.getBet());
            resetInfo(n);
            if (n == BlackjackJAVA.numOfPlayers.size() - 1) {
                currentPlayer = BlackjackJAVA.numOfPlayers.get(0);
                try {
                    printBoard();

                } catch (InterruptedException ex) {
                    Logger.getLogger(BlackJackGraphics.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                setBet(n + 1);
            }
        } else {
            betPane.getChildren().add(createChips(n));
            betPane.setTranslateY(900);
            betPane.setTranslateX(1600);
            root.getChildren().add(betPane);
        }
    }

    public static HBox createChips(int n) {
        System.out.println("Bet");
        Font f = new Font("Times New Roman", 16);
        double x = chipSize, y = chipSize;
        ip = null;
        betAmount = 0;
        HBox bPane = new HBox();

        Pane betChips = new Pane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = new Circle(x + (j * chipSize * 2), y + (i * chipSize * 2), chipSize);
                c.setOnMouseEntered(ChipOnMouseEntered);
                c.setOnMouseExited(ChipOnMouseExited);
                if (i == 0) {
                    switch (j) {
                        case 0:
                            if (currentPlayer.getChips() >= 1000) {
                                ip = new ImagePattern(ImageBuffer.chip1000);
                                c.setOnMouseClicked(chip1000OnClickAction);
                            }
                            break;
                        case 1:
                            if (currentPlayer.getChips() >= 500) {
                                ip = new ImagePattern(ImageBuffer.chip500);
                                c.setOnMouseClicked(chip500OnClickAction);
                            }
                            break;
                        case 2:
                            if (currentPlayer.getChips() >= 100) {
                                ip = new ImagePattern(ImageBuffer.chip100);
                                c.setOnMouseClicked(chip100OnClickAction);
                            }
                            break;
                        case 3:
                            if (currentPlayer.getChips() >= 50) {
                                ip = new ImagePattern(ImageBuffer.chip50);
                                c.setOnMouseClicked(chip50OnClickAction);
                            }
                            break;
                        default:
                            break;
                    }
                } else if (i == 1) {
                    switch (j) {
                        case 0:
                            if (currentPlayer.getChips() >= 25) {
                                ip = new ImagePattern(ImageBuffer.chip25);
                                c.setOnMouseClicked(chip25OnClickAction);
                            }
                            break;
                        case 1:
                            if (currentPlayer.getChips() >= 10) {

                                ip = new ImagePattern(ImageBuffer.chip10);
                                c.setOnMouseClicked(chip10OnClickAction);
                            }
                            break;
                        case 2:
                            if (currentPlayer.getChips() >= 5) {

                                ip = new ImagePattern(ImageBuffer.chip5);
                                c.setOnMouseClicked(chip5OnClickAction);
                            }
                            break;
                        case 3:
                            if (currentPlayer.getChips() >= 1) {

                                ip = new ImagePattern(ImageBuffer.chip1);
                                c.setOnMouseClicked(chip1OnClickAction);
                            }
                            break;
                        default:
                            break;
                    }
                }
                c.setFill(ip);
                betChips.getChildren().add(c);
            }
        }
        bPane.getChildren().add(betChips);

        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setFont(f);
        confirm.setMinSize(70, 70);
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (betAmount > currentPlayer.getChips() || betAmount == 0) {
                    betAmount = 0;
                    betText.setText("Bet: " + betAmount);
                } else {
                    bet = betAmount;
                    currentPlayer.setBet(bet);
                    resetInfo(n);
                    System.out.println(currentPlayer.getName() + " bet " + currentPlayer.getBet());
                    bPane.getChildren().clear();
                    root.getChildren().remove(bPane);
                    if (n == BlackjackJAVA.numOfPlayers.size() - 1) {
                        currentPlayer = BlackjackJAVA.numOfPlayers.get(0);
                        try {
                            printBoard();

                        } catch (InterruptedException | IOException ex) {
                            Logger.getLogger(BlackJackGraphics.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            setBet(n + 1);
                        } catch (IOException | InterruptedException ex) {
                            Logger.getLogger(BlackJackGraphics.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        bPane.getChildren().add(confirm);

        Button reset = new Button();
        reset.setText("Reset");
        reset.setFont(f);
        reset.setMinSize(70, 70);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(currentPlayer.isAi())) {
                    betAmount = 0;
                    betText.setText("Bet: " + betAmount);
                }
            }
        });
        bPane.getChildren().add(reset);

        betText = new Text();
        betText.setText("Bet: " + betAmount);
        betText.setFill(Color.WHITE);
        bPane.setMargin(betText, new Insets(-18, 0, 0, -185));
        bPane.getChildren().add(betText);

        return bPane;
    }

    public static void addPlayerInfo(Player player) {
        String blind;

        Pane playerInfo = new Pane();
        playerInfo.setTranslateY(95 + 40);

        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(5));
        vbox.setSpacing(0);

        Rectangle infoBck = new Rectangle(140, 70);
        infoBck.setFill(Color.rgb(0, 0, 0, 0.3));
        infoBck.setArcHeight(25);
        infoBck.setArcWidth(25);
        infoBck.setX(-7);
        infoBck.setY(-4);

        Text title = new Text(player.getName());
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Text options[] = new Text[]{
            new Text("Chips: " + player.getChips()),
            new Text("Bet: " + player.getBet())};

        for (int i = 0; i < 2; i++) {
            options[i].setFill(Color.WHITESMOKE);
            VBox.setMargin(options[i], new Insets(0, 0, -2, 5));
            vbox.getChildren().add(options[i]);
        }
        playerInfo.getChildren().addAll(infoBck, vbox);
        player.getPane().getChildren().add(playerInfo);
    }

    static EventHandler chip1OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 1) {
                source.setVisible(false);
            } else {
                betAmount += 1;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip5OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 5) {
                source.setVisible(false);
            } else {
                betAmount += 5;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip10OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 10) {
                source.setVisible(false);
            } else {
                betAmount += 10;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip25OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 25) {
                source.setVisible(false);
            } else {
                betAmount += 25;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip50OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 50) {
                source.setVisible(false);
            } else {
                betAmount += 50;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip100OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 100) {
                source.setVisible(false);
            } else {
                betAmount += 100;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip500OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 500) {
                source.setVisible(false);
            } else {
                betAmount += 500;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler chip1000OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (currentPlayer.getChips() < 1000) {
                source.setVisible(false);
            } else {
                betAmount += 1000;
                betText.setText("Bet: " + betAmount);
            }
        }
    };
    static EventHandler ChipOnMouseEntered = new EventHandler() {
        @Override
        public void handle(Event event) {
            DropShadow highlight = new DropShadow(10, Color.GOLDENROD);
            Circle source = (Circle) event.getSource();
            source.setEffect(highlight);
        }
    };
    static EventHandler ChipOnMouseExited = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            source.setEffect(null);
        }
    };
}
