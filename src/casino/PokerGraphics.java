package casino;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PokerGraphics {

    private static Pane rootPane;

    //etc on table
    private static Pane pokerBtns = new VBox(2);
    private static Pane burnPile = new Pane();
    private static HBox commCards = new HBox();
    private static Pane potPane = new Pane();

    private static Random rand = new Random();
    private static int raiseAmount;

    private static Text raiseText;
    private static Text waitText;
    private static boolean clicked = false;

    private static final double deckX = 1100;
    private static final double deckY = Player.middleY;
    private static final double flopX = 700;
    private static final double flopY = Player.middleY;
    private static final double burnX = 1200;
    private static final double potX = Player.betweenLeft;
    private static final double potY = Player.middleY + 75;
    private static final double chipSize = 20;
    private ImagePattern ip = null;

    public PokerGraphics() {

    }

    public static void pokerSetUp() {
        setRootPane(new Pane());
        ImagePattern ip = new ImagePattern(ImageBuffer.pokerTable);
        ImageView pTable = new ImageView();
        pTable.setImage(ImageBuffer.pokerTable);
        pTable.setFitHeight(1080);
        pTable.setFitWidth(1920);
        pTable.setX(0);
        pTable.setY(-40);
        Scene pokerScene = new Scene(getRootPane(), 1920, 1080);
        getRootPane().getChildren().add(pTable);

        //poker scene back button
        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> Casino.primaryStage.setScene(Casino.menu));
        getRootPane().getChildren().add(backBtn);

        Casino.primaryStage.setScene(pokerScene);
        Poker poker = new Poker();
        Casino.setPoker(poker);
        ArrayList<Player> players = Poker.getPlayers();
        for (Player player : Poker.getAllPlayers()) {
            addPlayerInfo(player);
            getRootPane().getChildren().add(player.getPane());
        }
        //displayShuffle(deck);
        Casino.getPoker().playPoker();

        Font game = new Font("Times New Roman", 35);
        Font f = new Font("Times New Roman", 16);

        //positioning the panes
        Poker.getDeck().getdPane().setTranslateX(getDeckX());
        Poker.getDeck().getdPane().setTranslateY(getDeckY());
        getCommCards().setTranslateX(getFlopX());
        getCommCards().setTranslateY(getFlopY());
        getBurnPile().setTranslateX(getBurnX());
        getBurnPile().setTranslateY(getDeckY());
        getPotPane().setTranslateX(getPotX());
        getPotPane().setTranslateY(getPotY());
        getRootPane().getChildren().add(getPotPane());
        getRootPane().getChildren().add(getCommCards());
    }

    public void createButtons() {
        setPokerBtns(new VBox());
        Font f = new Font("Times New Roman", 16);
        getRootPane().getChildren().add(getPokerBtns());
        //button call
        Button btnCall = new Button();
        btnCall.setText("Call");
        btnCall.setFont(f);
        btnCall.setMinSize(70, 70);
        btnCall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    int requiredChips = Poker.getRequiredChips();
                    if (Poker.getCurrentPlayer().getChipsInCurrent() != requiredChips) {
                        System.out.println("Call");
                        Poker.call(Poker.getCurrentPlayer(), requiredChips);
                        int playerIndex = Poker.getPlayers().indexOf(Poker.getCurrentPlayer());
                        getRootPane().getChildren().remove(getPokerBtns());
                        Casino.getPoker().determiningNextAction(playerIndex);
                    } else {
                        System.out.println("You cannot call");
                        getPokerBtns().getChildren().remove(btnCall);
                    }
                }
            }
        });

        //button raise
        clicked = false;
        Pane raisePane = new HBox();
        HBox hb = new HBox();
        Button btnRaise = new Button();
        btnRaise.setText("Bet");
        btnRaise.setFont(f);
        btnRaise.setMinSize(70, 70);
        raisePane.getChildren().add(btnRaise);
        btnRaise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(Poker.getCurrentPlayer() instanceof AI) && !(clicked)) {
                    setRaiseAmount(0);
                    createChips(hb);
                    clicked = true;
                }
            }
        });
        raisePane.getChildren().add(hb);

        //button fold
        Button btnFold = new Button();
        btnFold.setText("Fold");
        btnFold.setFont(f);
        btnFold.setMinSize(70, 70);
        btnFold.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    ArrayList<Player> players = Poker.getPlayers();
                    System.out.println("Fold");
                    PokerGraphics.displayFold(Poker.getCurrentPlayer());
                    int playerIndex = players.indexOf(Poker.getCurrentPlayer());
                    Poker.getPlayers().remove(Poker.getCurrentPlayer());
                    getRootPane().getChildren().remove(getPokerBtns());
                    Casino.getPoker().determiningNextAction(playerIndex);
                }
            }
        });

        //button check
        Button btnCheck = new Button();
        btnCheck.setText("Check");
        btnCheck.setFont(f);
        btnCheck.setMinSize(70, 70);
        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Player> players = Poker.getPlayers();
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    int requiredChips = Poker.getRequiredChips();
                    if (requiredChips == Poker.getCurrentPlayer().getChipsInCurrent()) {
                        Poker.getCurrentPlayer().getPane().getChildren().clear();
                        PokerGraphics.updatePlayerAction(Poker.getCurrentPlayer(), "Check");
                        System.out.println("Check");
                        int playerIndex = players.indexOf(Poker.getCurrentPlayer());
                        getRootPane().getChildren().remove(getPokerBtns());
                        Casino.getPoker().determiningNextAction(playerIndex);
                    } else {
                        System.out.println("You cannot check");
                        getPokerBtns().getChildren().remove(btnCheck);
                    }
                }
            }
        });
        getPokerBtns().setTranslateY(700);
        getPokerBtns().getChildren().addAll(btnCheck, btnCall, btnFold, raisePane);
        //the raising buttons
        //Pane betPane = new HBox();
        //button raise

        //betPane.getChildren().add(btnRaise);
    }

    public void createChips(HBox raisePane) {
        raiseAmount = 0;
        System.out.println("raise");
        setRaiseText(new Text());
        //button raise
        Font f = new Font("Times New Roman", 16);
        //betPane.getChildren().add(btnRaise);
        //the chip buttons for raising
        double x = getChipSize(), y = getChipSize();
        setIp(null);

        Pane betChips = new Pane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = new Circle(x + (j * getChipSize() * 2), y + (i * getChipSize() * 2), getChipSize());
                c.setOnMouseEntered(ChipOnMouseEntered);
                c.setOnMouseExited(ChipOnMouseExited);
                if (i == 0) {
                    switch (j) {
                        case 0:
                            if (Poker.getPlayers().get(0).getChips() >= 1) {
                                setIp(new ImagePattern(ImageBuffer.chipAllIn));
                                c.setOnMouseClicked(chipAllInOnClickAction);
                            }
                            break;
                        case 1:
                            if (Poker.getPlayers().get(0).getChips() >= 500) {
                                setIp(new ImagePattern(ImageBuffer.chip500));
                                c.setOnMouseClicked(chip500OnClickAction);
                            }
                            break;
                        case 2:
                            if (Poker.getPlayers().get(0).getChips() >= 100) {
                                setIp(new ImagePattern(ImageBuffer.chip100));
                                c.setOnMouseClicked(chip100OnClickAction);
                            }
                            break;
                        case 3:
                            if (Poker.getPlayers().get(0).getChips() >= 50) {
                                setIp(new ImagePattern(ImageBuffer.chip50));
                                c.setOnMouseClicked(chip50OnClickAction);
                            }
                            break;
                        default:
                            break;
                    }
                } else if (i == 1) {
                    switch (j) {
                        case 0:
                            if (Poker.getPlayers().get(0).getChips() >= 25) {
                                setIp(new ImagePattern(ImageBuffer.chip25));
                                c.setOnMouseClicked(chip25OnClickAction);
                            }
                            break;
                        case 1:
                            if (Poker.getPlayers().get(0).getChips() >= 10) {

                                setIp(new ImagePattern(ImageBuffer.chip10));
                                c.setOnMouseClicked(chip10OnClickAction);
                            }
                            break;
                        case 2:
                            if (Poker.getPlayers().get(0).getChips() >= 5) {

                                setIp(new ImagePattern(ImageBuffer.chip5));
                                c.setOnMouseClicked(chip5OnClickAction);
                            }
                            break;
                        case 3:
                            if (Poker.getPlayers().get(0).getChips() >= 1) {

                                setIp(new ImagePattern(ImageBuffer.chip1));
                                c.setOnMouseClicked(chip1OnClickAction);
                            }
                            break;
                        default:
                            break;
                    }
                }
                c.setFill(getIp());
                betChips.getChildren().add(c);
                setIp(null);
            }
        }

        raisePane.getChildren().add(betChips);
        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setFont(f);
        confirm.setMinSize(70, 70);
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Player> players = Poker.getPlayers();
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    int requiredChips = Poker.getRequiredChips();
                    Poker.call(Poker.getCurrentPlayer(), requiredChips);
                    Poker.raise(Poker.getCurrentPlayer(), requiredChips, raiseAmount);
                    Poker.setRequiredChips(Poker.getRequiredChips() + getRaiseAmount());
                    int playerIndex = players.indexOf(Poker.getCurrentPlayer());
                    raisePane.getChildren().clear();
                    getRootPane().getChildren().remove(getPokerBtns());
                    Casino.getPoker().determiningNextAction(playerIndex);
                }
            }
        });
        raisePane.getChildren().add(confirm);

        Button reset = new Button();
        reset.setText("Reset");
        reset.setFont(f);
        reset.setMinSize(70, 70);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Player> players = Poker.getPlayers();
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    setRaiseAmount(0);
                    getRaiseText().setText("Raise: " + getRaiseAmount());
                    raisePane.getChildren().clear();
                    createChips(raisePane);
                }
            }
        });
        raisePane.getChildren().add(reset);

        //displaying raise amount
        setRaiseText(new Text());
        getRaiseText().setText("Raise: " + getRaiseAmount());
        getRaiseText().setFont(f);
        getRaiseText().setFill(Color.WHITE);
        raisePane.setMargin(getRaiseText(), new Insets(-18, 0, 0, -185));
        raisePane.getChildren().add(getRaiseText());
    }

    public static void addPlayerInfo(Player player) {
        String blind;

        Pane playerInfo = new Pane();

        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(5));
        vbox.setSpacing(0);

        //setting blind names
        if (player.getBlind().getTypeBlind().equalsIgnoreCase("big")) {
            blind = "Big Blind";
        } else if (player.getBlind().getTypeBlind().equalsIgnoreCase("small")) {
            blind = "Small Blind";
        } else {
            blind = "";
        }

        Rectangle infoBck = new Rectangle(140, 70);
        infoBck.setFill(Color.rgb(0, 0, 0, 0.3));
        infoBck.setArcHeight(25);
        infoBck.setArcWidth(25);
        infoBck.setX(-7);
        infoBck.setY(-4);

        if (player.getPlayerNum() == 1 || player.getPlayerNum() == 2 || player.getPlayerNum() == 8) { //places info on bottom
            playerInfo.setTranslateY(95 + 40);
        } else if (player.getPlayerNum() == 3) { //places info on leftside
            playerInfo.setTranslateX(-135);
            playerInfo.setTranslateY(0 + 40);
        } else if (player.getPlayerNum() == 7) { //places info on rightside
            playerInfo.setTranslateX(135 + 40);
            playerInfo.setTranslateY(0 + 40);
        } else if (player.getPlayerNum() == 4 || player.getPlayerNum() == 5 || player.getPlayerNum() == 6) { //places info on top
            playerInfo.setTranslateY(-70 - 20);
        }

        Text title = new Text(player.getName());
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Text options[] = new Text[]{
            new Text("Chips: " + player.getChips()),
            new Text(blind)};

        for (int i = 0; i < 2; i++) {
            options[i].setFill(Color.WHITESMOKE);
            VBox.setMargin(options[i], new Insets(0, 0, -2, 5));
            vbox.getChildren().add(options[i]);
        }
        playerInfo.getChildren().addAll(infoBck, vbox);
        player.getPane().getChildren().add(playerInfo);
    }

    public static void updatePlayerAction(Player player, String action) {
        String blind;
        Pane playerInfo = new Pane();

        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(5));
        vbox.setSpacing(0);

        //setting blind names
        if (player.getBlind().getTypeBlind().equalsIgnoreCase("big")) {
            blind = "Big Blind";
        } else if (player.getBlind().getTypeBlind().equalsIgnoreCase("small")) {
            blind = "Small Blind";
        } else {
            blind = "";
        }

        Rectangle infoBck = new Rectangle(140, 70);
        infoBck.setFill(Color.rgb(0, 0, 0, 0.3));
        infoBck.setArcHeight(25);
        infoBck.setArcWidth(25);
        infoBck.setX(-7);
        infoBck.setY(-4);

        if (player.getPlayerNum() == 1 || player.getPlayerNum() == 2 || player.getPlayerNum() == 8) { //places info on bottom
            playerInfo.setTranslateY(95 + 40);
        } else if (player.getPlayerNum() == 3) { //places info on leftside
            playerInfo.setTranslateX(-135);
            playerInfo.setTranslateY(0 + 40);
        } else if (player.getPlayerNum() == 7) { //places info on rightside
            playerInfo.setTranslateX(135 + 40);
            playerInfo.setTranslateY(0 + 40);
        } else if (player.getPlayerNum() == 4 || player.getPlayerNum() == 5 || player.getPlayerNum() == 6) { //places info on top
            playerInfo.setTranslateY(-70 - 20);
        }

        Text title = new Text(player.getName());
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Text options[] = new Text[]{
            new Text("Chips: " + player.getChips()),
            new Text(action),
            new Text(blind)};

        for (int i = 0; i < 3; i++) {
            options[i].setFill(Color.WHITESMOKE);
            VBox.setMargin(options[i], new Insets(0, 0, -2, 5));
            vbox.getChildren().add(options[i]);
        }
        playerInfo.getChildren().addAll(infoBck, vbox);
        player.getPane().getChildren().add(playerInfo);
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

    public static void dealCard(double x, double y, Card card, Player player) {
        Pane pane = new Pane();

        Path path = new Path();
        path.getElements().add(new MoveTo(card.getX(), card.getY()));
        path.getElements().add(new LineTo(x, y));

        PathTransition pt = new PathTransition();
        pt.setNode(card);
        pt.setPath(path);
        pt.setDuration(Duration.seconds(1));
        pt.play();

        player.getPane().getChildren().add(pane);
    }

    public static void displayShuffle(Deck deck) {
        ImagePattern ip = new ImagePattern(ImageBuffer.back1);
        deck.getdPane().getChildren().clear();
        for (int i = 0; i < deck.getDeck().size() - 1; i++) {
            Card card = deck.getDeck().get(i);
            card.setFill(ip);
            Path cpath = new Path();
            cpath.getElements().add(new MoveTo(0, 0));
            cpath.getElements().add(new CubicCurveTo(-rand.nextInt(500) + 100, -rand.nextInt(500) + 200, -rand.nextInt(500) + 200, getRand().nextInt(200) + 100, getRand().nextInt(500) + 100, getRand().nextInt(200) + 100));
            cpath.getElements().add(new CubicCurveTo(getRand().nextInt(500) + 100, getRand().nextInt(500) + 200, getRand().nextInt(500) + 200, -rand.nextInt(200) + 100, 0, 0));
            cpath.getElements().add(new MoveTo(0, 0));

            PathTransition cPT = new PathTransition();
            cPT.setNode(card);
            cPT.setPath(cpath);
            cPT.setDuration(Duration.seconds(2));
            cPT.play();
            deck.getdPane().getChildren().add(card);
            cPT.setDelay(Duration.seconds(2));
        }
        displayDeck(deck);
    }

    public static void displayDeck(Deck deck) {
        deck.getdPane().getChildren().clear();
        deck.getdPane().setTranslateX(getDeckX());
        deck.getdPane().setTranslateY(getDeckY());
        ImagePattern ip = new ImagePattern(ImageBuffer.back1);
        for (int i = 0; i < deck.getDeck().size() - 1; i++) {
            Card card = deck.getDeck().get(i);
            card.setX(0);
            card.setY(0 - (i * 0.25));
            deck.getdPane().getChildren().add(displayCard(card));
        }
        getRootPane().getChildren().add(deck.getdPane());
    }

    public static void displayBurn() {
        getBurnPile().setTranslateX(getBurnX());
        getBurnPile().setTranslateY(getDeckY());
        Card card = Poker.getDeck().getDeck().get(0);
        card.setFaceUp(false);
        getBurnPile().getChildren().add(displayCard(card));
    }

    public static void displayFlop(ArrayList<Card> communityCards) {
        getCommCards().setTranslateX(getFlopX());
        getCommCards().setTranslateY(getFlopY());
        for (int i = 0; i < 3; i++) {
            Card card = communityCards.get(i);
            card.setFaceUp(true);
            getCommCards().getChildren().add(displayCard(card));
        }
    }

    public static void displayTurn(ArrayList<Card> communityCards) {
        Card card = communityCards.get(3);
        card.setFaceUp(true);
        getCommCards().getChildren().add(displayCard(card));
    }

    public static void displayRiver(ArrayList<Card> communityCards) {
        Card card = communityCards.get(4);
        card.setFaceUp(true);
        getCommCards().getChildren().add(displayCard(card));
    }

    public static void removeCommunityCard() {
        for (int i = getCommCards().getChildren().size() - 1; i >= 0; i--) {
            getCommCards().getChildren().remove(i);
        }
    }

    public static void displayAllCards(ArrayList<Player> playersInRound) {

        for (Player player : playersInRound) {
            HBox pocketCards = new HBox();
            player.getPane().getChildren().clear();
            addPlayerInfo(player);
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);
                if (!card.isFaceUp()) {
                    System.out.println("flipped");
                    card.setFaceUp(true);
                }
                pocketCards.getChildren().add(displayCard(card));
            }
            player.getPane().getChildren().add(pocketCards);
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Casino.getPoker().distributeWin();
            }
        }));
        timeline.play();
    }

    public static void displayAllCardsAllIn(ArrayList<Player> playersInRound) {

        for (Player player : playersInRound) {
            HBox pocketCards = new HBox();
            player.getPane().getChildren().clear();
            addPlayerInfo(player);
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);
                if (!card.isFaceUp()) {
                    System.out.println("flipped");
                    card.setFaceUp(true);
                }
                pocketCards.getChildren().add(displayCard(card));
            }
            player.getPane().getChildren().add(pocketCards);
        }
    }

    public static void displayFold(Player player) {
        player.getPane().getChildren().clear();
        addPlayerInfo(player);
        for (int i = 0; i < 2; i++) {
            Card card = player.getPocketHand().getPocketHand().get(i);
            Path path = new Path();
            path.getElements().add(new MoveTo(card.getX(), card.getY()));
            path.getElements().add(new LineTo(getBurnX(), getDeckY()));
            PathTransition pt = new PathTransition();
            pt.setNode(card);
            pt.setPath(path);
            pt.setDuration(Duration.seconds(1));
            pt.play();
        }
    }

    public static void displayPot() {
        getPotPane().getChildren().clear();

        int totalPot = Poker.getPot(), k = 0, i;
        double x = 0, y = 0;
        ImagePattern chipImage;
        Pane p1000 = new Pane();
        Pane p500 = new Pane();
        Pane p100 = new Pane();
        Pane p50 = new Pane();
        Pane p25 = new Pane();
        Pane p10 = new Pane();
        Pane p5 = new Pane();
        Pane p1 = new Pane();

        if (totalPot > 55000) {
            y = 100;
        }
        p25.setTranslateX(x - getChipSize() + 3);
        p25.setTranslateY(y);
        p10.setTranslateX(x - getChipSize() + (getChipSize() * 2) + (3 * 2));
        p10.setTranslateY(y);
        p5.setTranslateX(x - getChipSize() + ((getChipSize() * 2) * 2) + (3 * 3));
        p5.setTranslateY(y);
        p1.setTranslateX(x - getChipSize() + ((getChipSize() * 2) * 3) + (3 * 4));
        p1.setTranslateY(y);
        p1000.setTranslateX(x + 3);                            //25
        p1000.setTranslateY(y - (getChipSize() * 2));
        p500.setTranslateX(x + (getChipSize() * 2) + (3 * 2));    //10
        p500.setTranslateY(y - (getChipSize() * 2));
        p100.setTranslateX(x + ((getChipSize() * 2) * 2) + (3 * 3));   //5
        p100.setTranslateY(y - (getChipSize() * 2));
        p50.setTranslateX(x + ((getChipSize() * 2) * 3) + (3 * 4));   //1
        p50.setTranslateY(y - (getChipSize() * 2));

        Text pot = new Text(Integer.toString(Poker.getPot()) + " Chips");
        Rectangle bck = new Rectangle(85, 12);
        bck.setFill(Color.rgb(0, 0, 0, 0.3));
        bck.setArcHeight(15);
        bck.setArcWidth(15);
        bck.setX(x + 2);
        bck.setY(y + 20);

        Font f = new Font("Times New Roman", 50);
        pot.setFill(Color.WHITE);
        pot.setX(x + 10);
        pot.setY(y + 30);

        DropShadow highlight = new DropShadow(1, Color.BLACK);

        for (i = totalPot; i >= 1000; i -= 1000) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip1000);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 10 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p1000.getChildren().add(chip);
            totalPot -= 1000;
        }
        k = 0;
        for (i = totalPot; i >= 500; i -= 500) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip500);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p500.getChildren().add(chip);
            totalPot -= 500;
        }
        k = 0;
        for (i = totalPot; i >= 100; i -= 100) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip100);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p100.getChildren().add(chip);
            totalPot -= 100;
        }
        k = 0;
        for (i = totalPot; i >= 50; i -= 50) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip50);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p50.getChildren().add(chip);
            totalPot -= 50;
        }
        k = 0;
        for (i = totalPot; i >= 25; i -= 25) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip25);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p25.getChildren().add(chip);
            totalPot -= 25;
        }
        k = 0;
        for (i = totalPot; i >= 10; i -= 10) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip10);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p10.getChildren().add(chip);
            totalPot -= 10;
        }
        k = 0;
        for (i = totalPot; i >= 5; i -= 5) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip5);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p5.getChildren().add(chip);
            totalPot -= 5;
        }
        k = 0;
        for (i = totalPot; i >= 1; i -= 1) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip1);
            Circle chip = new Circle(getChipSize(), chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (getChipSize() / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p1.getChildren().add(chip);
            totalPot -= 1;
        }
        getPotPane().getChildren().addAll(p1000, p500, p100, p50, p25, p10, p5, p1, bck, pot);
    }

    public static void displayDealPlayers(ArrayList<Player> players) {
        Image image = null;

        for (Player player : players) {
            HBox pocketCards = new HBox();
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);

                //is it computer or person to hide cards or not to hide cards
                if (player instanceof AI) {
                    card.setFaceUp(false);
                } else {
                    card.setFaceUp(true);
                }
                pocketCards.getChildren().add(displayCard(card));
            }
            player.getPane().getChildren().add(pocketCards);
        }
    }

    EventHandler chip1OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 1 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 1);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }

        }
    };
    EventHandler chip5OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 5 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 5);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }
        }
    };
    EventHandler chip10OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 10 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 10);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }
        }
    };
    EventHandler chip25OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 25 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 25);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }
        }
    };
    EventHandler chip50OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 50 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 50);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }
        }
    };
    EventHandler chip100OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 100 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 100);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }
        }
    };
    EventHandler chip500OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() - raiseAmount - 500 < 0) {
                source.setVisible(false);
            } else {
                setRaiseAmount(getRaiseAmount() + 500);
                getRaiseText().setText("Raise: " + getRaiseAmount());
            }
        }
    };
    EventHandler chipAllInOnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            setRaiseAmount(Poker.getCurrentPlayer().getChips());
            getRaiseText().setText("Raise: " + getRaiseAmount());
        }
    };
    EventHandler ChipOnMouseEntered = new EventHandler() {
        @Override
        public void handle(Event event) {
            DropShadow highlight = new DropShadow(10, Color.GOLDENROD);
            Circle source = (Circle) event.getSource();
            source.setEffect(highlight);
        }
    };
    EventHandler ChipOnMouseExited = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            source.setEffect(null);
        }
    };

    /**
     * @return the rootPane
     */
    public static Pane getRootPane() {
        return rootPane;
    }

    /**
     * @param aRootPane the rootPane to set
     */
    public static void setRootPane(Pane aRootPane) {
        rootPane = aRootPane;
    }

    /**
     * @return the pokerBtns
     */
    public static Pane getPokerBtns() {
        return pokerBtns;
    }

    /**
     * @param aPokerBtns the pokerBtns to set
     */
    public static void setPokerBtns(Pane aPokerBtns) {
        pokerBtns = aPokerBtns;
    }

    /**
     * @return the burnPile
     */
    public static Pane getBurnPile() {
        return burnPile;
    }

    /**
     * @param aBurnPile the burnPile to set
     */
    public static void setBurnPile(Pane aBurnPile) {
        burnPile = aBurnPile;
    }

    /**
     * @return the commCards
     */
    public static HBox getCommCards() {
        return commCards;
    }

    /**
     * @param aCommCards the commCards to set
     */
    public static void setCommCards(HBox aCommCards) {
        commCards = aCommCards;
    }

    /**
     * @return the potPane
     */
    public static Pane getPotPane() {
        return potPane;
    }

    /**
     * @param aPotPane the potPane to set
     */
    public static void setPotPane(Pane aPotPane) {
        potPane = aPotPane;
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

    /**
     * @return the raiseAmount
     */
    public static int getRaiseAmount() {
        return raiseAmount;
    }

    /**
     * @param aRaiseAmount the raiseAmount to set
     */
    public static void setRaiseAmount(int aRaiseAmount) {
        raiseAmount = aRaiseAmount;
    }

    /**
     * @return the raiseText
     */
    public static Text getRaiseText() {
        return raiseText;
    }

    /**
     * @param aRaiseText the raiseText to set
     */
    public static void setRaiseText(Text aRaiseText) {
        raiseText = aRaiseText;
    }

    /**
     * @return the waitText
     */
    public static Text getWaitText() {
        return waitText;
    }

    /**
     * @param aWaitText the waitText to set
     */
    public static void setWaitText(Text aWaitText) {
        waitText = aWaitText;
    }

    /**
     * @return the deckX
     */
    public static double getDeckX() {
        return deckX;
    }

    /**
     * @return the deckY
     */
    public static double getDeckY() {
        return deckY;
    }

    /**
     * @return the flopX
     */
    public static double getFlopX() {
        return flopX;
    }

    /**
     * @return the flopY
     */
    public static double getFlopY() {
        return flopY;
    }

    /**
     * @return the burnX
     */
    public static double getBurnX() {
        return burnX;
    }

    /**
     * @return the potX
     */
    public static double getPotX() {
        return potX;
    }

    /**
     * @return the potY
     */
    public static double getPotY() {
        return potY;
    }

    /**
     * @return the chipSize
     */
    public static double getChipSize() {
        return chipSize;
    }

    /**
     * @return the ip
     */
    public ImagePattern getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(ImagePattern ip) {
        this.ip = ip;
    }
}
