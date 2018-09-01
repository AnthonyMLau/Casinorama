/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author laua8572
 */
public class RouletteGraphics {

    private ArrayList<Rectangle> rects = new ArrayList<>();
    private ArrayList<Rectangle> outsideBets = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Object> playerSquares = new ArrayList<>();//all the elements that displays player name, chips, etc
    private ArrayList<Rectangle> playerSquareCovers = new ArrayList<>();//to dim all the players except the current one
    private ArrayList<Bet> bets = new ArrayList<Bet>();
    private ArrayList<Button> buttons = new ArrayList();//list of inside bet buttons and the outside bet button
    private ArrayList<Button> moneyOptions = new ArrayList();//list of bet options eg. $100, $200, etc.
    private ArrayList<Integer> blackNums = new ArrayList<>();
    private ArrayList<Circle> chipsOnBoard = new ArrayList<>();

    private int playerNum = 0;
    private int playerXLoc = -325;
    private int outsideBetIndex;
    private int amountBet = 50;
    private int winNum;
    private Text winNumText = new Text();

    private Button Single = new Button();
    private Button Split = new Button();
    private Button Corner = new Button();
    private Button Street = new Button();
    private Button rectButton = new Button();
    private Button outsideButton = new Button();
    private String buttonName = "";
    private Button instBack = new Button("Back");
    private boolean AI;

    private Rectangle coverBoard;
    private Rectangle coverBets;
    private Rectangle inst1;
    private Rectangle inst2;

    Random rand = new Random();
    private int numClicked = -1;
    Circle wheel = new Circle(400, 500, 100);

    public void addPlayerList(ArrayList<Player> players) {
        this.players = players;

    }

    public RouletteGraphics() {
    }

    public void rouletteSetUp() throws FileNotFoundException, InterruptedException {
        players.add(Casino.getMainPlayer());
        players.get(0).setPlayerNum(0);
        players.add(new Player("dsfhek", 1));
        players.add(new Player("djwkalsdj", 2));
        players.add(new Player("JKKJS", 3));
        players.add(new Player("dsfhek", 4));
        players.add(new Player("djwkalsdj", 5));
        players.add(new Player("JKKJS", 6));

        players.get(2).setChips(0);

        Group root = new Group();
        Scene scene = new Scene(root, 1700, 1000, Color.GREEN);

        coverBets(root);
        QuestionMark(root);
        backbtn(root);
        drawBoard(root);
        drawInsideBets(root);
        drawOutsideeBets(root);
        drawMoneyOptions(root);
        drawWheel(root);
        drawPlayers(root);
        coverBets(root);
        drawAiButton(root);

        //flyingChip(root, scene);
        playPlayer(root);

        Casino.primaryStage.setScene(scene);
        Casino.primaryStage.show();

    }

    public void drawAiButton(Group root) throws FileNotFoundException {
        Rectangle r = new Rectangle(650, 30, 200, 50);
        r.setFill(Color.DARKGREEN);

        r.setArcWidth(25);
        r.setArcHeight(25);
        root.getChildren().add(r);

        Image image = new Image(new FileInputStream("src\\Resource\\AI.png"), 5000, 5000, true, true);
        r.setFill(new ImagePattern(image));

        Button b = new Button("Ai AutoBet       ");
        b.setFont(new Font(25));
        b.setTranslateX(650);
        b.setTranslateY(30);
        b.setOpacity(0);
        root.getChildren().add(b);

        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AI(root);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RouletteGraphics.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public void AI(Group root) throws FileNotFoundException {

        if (playerNum < players.size() - 1) {
            //coverAllBets(root);
            int betPick = rand.nextInt(5);
            if (betPick == 0) {
                amountBet = 25;
            } else if (betPick == 1) {
                amountBet = 75;
            } else if (betPick == 2) {
                amountBet = 150;
            } else if (betPick == 3) {
                amountBet = players.get(playerNum).getChips();
            } else if (betPick == 4) {
                amountBet = 0;
            }
            if (players.get(playerNum).getChips() < amountBet) {
                amountBet = players.get(playerNum).getChips();
            }

            players.get(playerNum).setBet(amountBet);
            AI = true;
            updatePlayerMoneyText(root);

            ArrayList<Integer> a = new ArrayList<>();
            a.add(rand.nextInt(36) + 1);
            bets.add(new Bet(amountBet, 1, playerNum, a));

            placeChip(a, root);

            nextPlayer(root);
        }
    }

    public void backbtn(Group root) throws FileNotFoundException {
        Rectangle r = new Rectangle(40, 20, 240, 100);
        r.setFill(Color.DARKGREEN);
        r.setOpacity(0.5);
        r.setArcWidth(25);
        r.setArcHeight(25);
        root.getChildren().add(r);

        Button backBtn = new Button();
        backBtn.setTranslateX(50);
        backBtn.setTranslateY(25);
        //backBtn.setText("  ");
        backBtn.setShape(new Circle());

        Image image = new Image(new FileInputStream("src\\Resource\\backButton.png"), 5000, 5000, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        backBtn.setGraphic(imageView);

        // backBtn.setOnAction(e -> Casino.primaryStage.setScene(Casino.menu));
        root.getChildren().add(backBtn);

        Font f = new Font(45);
        Text t = new Text(150, 83, "Back");
        t.setFont(f);
        t.setFill(Color.WHITE);
        root.getChildren().add(t);

        Rectangle r2 = new Rectangle(40, 20, 240, 100);
        r2.setFill(Color.DARKGREEN);
        r2.setOpacity(0);
        r2.setArcWidth(25);
        r2.setArcHeight(25);
        root.getChildren().add(r2);
        r2.setOnMouseClicked(backAction);

    }

    public void playPlayer(Group root) {

        for (int i = 0; i < moneyOptions.size(); i++) {//get which button is pressed for amount bet buttons
            moneyOptions.get(i).setOnAction(e -> {
                uncoverBoard(root);
                uncoverBets(root);
                System.out.println("bet amount pressed");
                String amount = ((Button) e.getSource()).getText();
                System.out.println(amount);
                if (amount.equals("AllIn")) {
                    amountBet = players.get(playerNum).getChips();

                } else {
                    amountBet = Integer.parseInt(amount);
                    System.out.println(amountBet + "= Amount bet");
                }

                setClickOutside();
                buttonName = "";
                System.out.println("-------------------------------------");

                for (int j = 0; j < buttons.size(); j++) {//get which button is pressed for inside bet buttons
                    buttons.get(j).setOnAction(e2 -> {
                        players.get(playerNum).setBet(amountBet);
                        buttonName = ((Button) e2.getSource()).getText();
                        System.out.println(buttonName + "button name");
                        removeClickOutside();
                        updatePlayerMoneyText(root);
                        setClickNums();
                        try {
                            addBet(root);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(RouletteGraphics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }

            });
        }

    }

    public void addBet(Group root) throws FileNotFoundException {
        if (buttonName.equalsIgnoreCase("outsideButton")) {
            addOutsideBet(root);
            removeClickNums();
        }
        rectButton.setOnAction((e -> {//when a number/rect on board is clicked and an inside bet button is clicked, then add the bet
            if (buttonName.equalsIgnoreCase("Single")) {
                System.out.println("singleRect");
                setClickNums();

                int num1 = numClicked;

                ArrayList<Integer> nums = new ArrayList();
                nums.add(num1);

                bets.add(new Bet(amountBet, 1, playerNum, nums));
                try {
                    placeChip(nums, root);
                } catch (FileNotFoundException ex) {
                }
                amountBet = 50;
                System.out.println("BetPlaced");
                removeClickNums();
                System.out.println(num1 + "num1");
                try {
                    nextPlayer(root);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RouletteGraphics.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (buttonName.equals("Corner")) {
                System.out.println("CornerRect");
                setClickNums();
                int num1 = 0;

                num1 = numClicked;
                ArrayList<Integer> nums = new ArrayList();
                for (int i = num1; i < num1 + 5; i++) {
                    nums.add(i);
                }
                nums.remove(2);
                removeClickNums();

                bets.add(new Bet(amountBet, 4, playerNum, nums));
                try {
                    placeChip(nums, root);
                } catch (FileNotFoundException ex) {
                }
                amountBet = 50;
                System.out.println(num1 + "num1");
                try {
                    nextPlayer(root);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RouletteGraphics.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (buttonName.equals("Split")) {
                System.out.println("SplitRect");
                setClickNums();
                int num1 = 0;

                num1 = numClicked;

                removeClickNums();

                if (num1 % 3 == 0) {
                    rects.get(num1 - 1).setOnMousePressed(rectOnClickAction);//index of num1 is the same as the actual number
                } else if ((num1 - 1) % 3 == 0) {
                    rects.get(num1 + 1).setOnMousePressed(rectOnClickAction);
                } else if (num1 == 2) {
                    rects.get(num1 + 1).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 - 1).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 + 3).setOnMousePressed(rectOnClickAction);
                } else if (num1 == 35) {
                    rects.get(num1 + 1).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 - 1).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 - 3).setOnMousePressed(rectOnClickAction);
                } else {
                    rects.get(num1 + 1).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 - 1).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 + 3).setOnMousePressed(rectOnClickAction);
                    rects.get(num1 - 3).setOnMousePressed(rectOnClickAction);
                }

                final int num1F = num1;//use num1F since variable has to be final in anonomous class
                rectButton.setOnAction((e2 -> {//when a number is clicked and an inside bet button is clicked, then add the bet
                    final int num2 = numClicked;
                    ArrayList<Integer> nums = new ArrayList();

                    for (int j = num1F; j < num2 + 1; j++) {
                        nums.add(j);
                    }
                    System.out.println(num1F + "num1");
                    System.out.println(num2 + "num2");
                    bets.add(new Bet(500, 2, playerNum, nums));
                    try {
                        placeChip(nums, root);
                    } catch (FileNotFoundException ex) {
                    }
                    amountBet = 50;
                    removeClickNums();
                    try {
                        nextPlayer(root);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(RouletteGraphics.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }));

            } else if (buttonName.equals("Street")) {
                System.out.println("StreetRect");
                int num1 = numClicked;
                int lastNum = 0;

                if (num1 % 3 == 0) {//num1 is highest, so make it lowest, make lastnum hightest
                    lastNum = num1;
                    num1 -= 2;
                } else if ((num1 - 1) % 3 == 0) {//num1 is already lowest, make lastnum hightest
                    lastNum = num1 + 2;
                } else if ((num1 + 1) % 3 == 0) {//num1 is middle number
                    lastNum = num1 + 1;
                    num1 -= 1;
                }

                ArrayList<Integer> nums = new ArrayList();
                for (int i = num1; i <= lastNum; i++) {
                    nums.add(i);
                }
                bets.add(new Bet(500, 3, playerNum, nums));
                try {
                    placeChip(nums, root);
                } catch (FileNotFoundException ex) {
                }
                amountBet = 50;
                System.out.println("Num1:" + num1);
                System.out.println("NLastum1:" + lastNum);
                try {
                    nextPlayer(root);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RouletteGraphics.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }));

    }

    public void addOutsideBet(Group root) throws FileNotFoundException {
        if (outsideBetIndex >= 0 && outsideBetIndex <= 2) {//column bets
            int numToAdd = outsideBetIndex + 1;
            ArrayList<Integer> numsBet = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                numsBet.add(numToAdd);
                numToAdd += 3;

            }
            bets.add(new Bet(amountBet, 15, playerNum, numsBet));
            try {
                placeChip(numsBet, root);
            } catch (FileNotFoundException ex) {
            }
            amountBet = 50;
            nextPlayer(root);

        } else if (outsideBetIndex <= 5) {
            int startingNum = (outsideBetIndex - 3) * 12 + 1;
            System.out.println(startingNum + "Starting num");//if index@3, start @ 1, index@ 4, start @13, index @5, start at 25 (for 1st ,3nd 3rd 12 nums)
            ArrayList<Integer> numsBet = new ArrayList<>();
            for (int i = startingNum; i < startingNum + 12; i++) {
                numsBet.add(i);
            }
            bets.add(new Bet(amountBet, 11, playerNum, numsBet));
            try {
                placeChip(numsBet, root);
            } catch (FileNotFoundException ex) {
            }
            amountBet = 50;
            nextPlayer(root);

        } else if (outsideBetIndex == 6 || outsideBetIndex == 11) {//1-18, 19-36
            int startNum = 0;
            if (outsideBetIndex == 6) {
                startNum = 1;
            } else if (outsideBetIndex == 11) {
                startNum = 19;
            }

            ArrayList<Integer> numsBet = new ArrayList<>();
            for (int i = startNum; i < startNum + 18; i++) {
                numsBet.add(i);
            }
            bets.add(new Bet(amountBet, 6, playerNum, numsBet));
            try {
                placeChip(numsBet, root);
            } catch (FileNotFoundException ex) {
            }
            amountBet = 50;
            nextPlayer(root);

        } else if (outsideBetIndex == 7 || outsideBetIndex == 10) {//even odd
            System.out.println("evenodd");
            int startNum = 0;
            if (outsideBetIndex == 7) {
                startNum = 2;
            } else if (outsideBetIndex == 10) {
                startNum = 1;
            }
            ArrayList<Integer> numsBet = new ArrayList<>();
            for (int i = startNum; i <= 36; i += 2) {
                numsBet.add(i);
            }
            bets.add(new Bet(amountBet, 6, playerNum, numsBet));
            try {
                placeChip(numsBet, root);
            } catch (FileNotFoundException ex) {
            }
            amountBet = 50;
            nextPlayer(root);

        } else if (outsideBetIndex == 8 || outsideBetIndex == 9) {//red black
            if (outsideBetIndex == 10) {
                bets.add(new Bet(amountBet, 6, playerNum, blackNums));
                try {
                    placeChip(blackNums, root);
                } catch (FileNotFoundException ex) {
                }
                amountBet = 50;
                nextPlayer(root);

            } else {
                ArrayList<Integer> numsBet = new ArrayList<>();
                for (int i = 1; i < 36; i++) {
                    if (!blackNums.contains(i)) {
                        numsBet.add(i);
                    }
                }
                bets.add(new Bet(amountBet, 6, playerNum, numsBet));
                try {
                    placeChip(numsBet, root);
                } catch (FileNotFoundException ex) {
                }
                amountBet = 50;
                nextPlayer(root);
            }

        }
    }

    public void nextPlayer(Group root) throws FileNotFoundException {

        removeClickNums();
        removeClickOutside();
        coverBoard(root);
        coverBets(root);
        if (playerNum < players.size()) {
            if (playerNum != players.size() - 1) {
                if (players.get(playerNum + 1).getChips() == 0) {
                    playerNum++;
                    playerSquareCovers.get(playerNum).setOpacity(0.7);
                    movePlayers(root, playerXLoc);
                    playerXLoc -= 325;
                }
            }

            playerSquareCovers.get(playerNum).setOpacity(0.5);
            if (playerNum < players.size() - 1) {
                playerSquareCovers.get(playerNum + 1).setOpacity(0);
            }

            if (!(playerNum >= players.size() - 3)) {//if there are 4 or less players left do not move the squares
                movePlayers(root, playerXLoc);
                playerXLoc -= 325;
            }
            if (AI) {
                playerNum++;
                System.out.println(playerNum + "playernum-----------------");
                AI(root);
            }
        }

        System.out.println("playernum===" + playerNum);
        playerNum++;
        System.out.println("Player num====" + playerNum);
        if (playerNum == players.size()) {
            AI = false;
            roll(root);
            playerNum = 0;
            playerXLoc = -325;
        }
    }

    public void setClickNums() {
        for (int i = 1; i < rects.size(); i++) {//start at 1 since you cannot bet on 0
            rects.get(i).setOnMousePressed(rectOnClickAction);
        }
    }

    public void removeClickNums() {
        for (int i = 0; i < rects.size(); i++) {
            rects.get(i).removeEventHandler(EventType.ROOT, rectOnClickAction);
        }
    }

    public void setClickOutside() {
        for (int i = 0; i < outsideBets.size(); i++) {
            outsideBets.get(i).setOnMousePressed(outsideOnClickAction);

        }
    }

    public void removeClickOutside() {
        for (int i = 0; i < outsideBets.size(); i++) {
            outsideBets.get(i).removeEventHandler(EventType.ROOT, outsideOnClickAction);
        }
    }

    EventHandler<Event> rectOnClickAction = new EventHandler<Event>() {

        @Override
        public void handle(Event event) {

            Rectangle temp = (Rectangle) event.getSource();
            Paint p = temp.getFill();
            temp.setOpacity(0.3);
            temp.setFill(Color.BLUEVIOLET);
            numClicked = rects.indexOf(temp);
            System.out.println(numClicked);

            removeClickNums();

            IntStream.range(0, 1).forEach(//automatically presses rectbutton when a rect is clicked
                    i -> rectButton.fire()
            );

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    temp.setFill(p);
                    temp.setOpacity(0);
                }
            },
                    200
            );
        }

    };

    EventHandler<Event> outsideOnClickAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Rectangle temp = (Rectangle) event.getSource();
            Paint p = temp.getFill();
            temp.setFill(Color.BLUEVIOLET);
            outsideBetIndex = outsideBets.indexOf(temp);

            IntStream.range(0, 1).forEach(//automatically presses outsidebutton when an outside bet is clicked
                    i -> outsideButton.fire()
            );

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    temp.setFill(p);
                }
            },
                    300
            );
        }

    };

    EventHandler<Event> backAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Casino.primaryStage.setScene(Casino.menu);

        }

    };

    public void drawBoard(Group root) {

        int counter = 1;
        int colxPos = 180;

        Rectangle outline = new Rectangle(175, 145, 1000, 280); //for white outline
        outline.setFill(Color.WHITE);
        root.getChildren().add(outline);
        Rectangle outline2 = new Rectangle(100, 200, 75, 60);
        outline2.setFill(Color.WHITE);
        root.getChildren().add(outline2);

        Font f = new Font(STYLESHEET_CASPIAN, 35);

        Rectangle zero = new Rectangle(105, 205, 70, 50);
        zero.setFill(Color.GREEN);
        root.getChildren().add(zero);
        rects.add(zero);

        Text t0 = new Text(125, 240, "0");
        t0.setFill(Color.WHITE);
        t0.setFont(f);
        root.getChildren().add(t0);

        for (int i = 0; i < 12; i++) {

            Rectangle a = new Rectangle(colxPos, 150, 70, 50);
            a.setFill(Color.RED);

            Text t1 = new Text(colxPos + 15, 185, Integer.toString(counter));
            t1.setFill(Color.WHITE);
            t1.setFont(f);

            Rectangle b = new Rectangle(colxPos, 205, 70, 50);
            b.setFill(Color.RED);

            Text t2 = new Text(colxPos + 15, 240, Integer.toString(counter + 1));
            t2.setFill(Color.WHITE);
            t2.setFont(f);

            Rectangle c = new Rectangle(colxPos, 260, 70, 50);
            c.setFill(Color.RED);

            Text t3 = new Text(colxPos + 15, 295, Integer.toString(counter + 2));
            t3.setFill(Color.WHITE);
            t3.setFont(f);

            root.getChildren().add(a);
            root.getChildren().add(b);
            root.getChildren().add(c);

            root.getChildren().add(t1);
            root.getChildren().add(t2);
            root.getChildren().add(t3);

            Rectangle click1 = new Rectangle(colxPos, 150, 70, 50);
            Rectangle click2 = new Rectangle(colxPos, 205, 70, 50);
            Rectangle click3 = new Rectangle(colxPos, 260, 70, 50);
            click1.setOpacity(0);
            click2.setOpacity(0);
            click3.setOpacity(0);
            rects.add(click1);
            rects.add(click2);
            rects.add(click3);
            root.getChildren().add(click1);
            root.getChildren().add(click2);
            root.getChildren().add(click3);

            colxPos += 75;
            counter += 3;

        }
        fillBlackNums(root);
    }

    public void drawOutsideeBets(Group root) {
        outsideButton = new Button();
        outsideButton.setText("outsideButton");
        outsideButton.setVisible(false);
        buttons.add(outsideButton);

        Font f1 = new Font(STYLESHEET_CASPIAN, 25);

        Rectangle coverWhiteArea = new Rectangle(1080, 315, 95, 165);
        coverWhiteArea.setFill(Color.GREEN);
        root.getChildren().add(coverWhiteArea);

        int yPos = 150;
        for (int i = 0; i < 3; i++) {
            Rectangle r = new Rectangle(1080, yPos, 90, 50);    //column bets
            r.setFill(Color.GREEN);
            root.getChildren().add(r);

            Text t = new Text(1095, yPos + 32, "Col " + Integer.toString(i + 1));
            t.setFill(Color.WHITE);
            t.setFont(f1);
            root.getChildren().add(t);

            Rectangle r2 = new Rectangle(1080, yPos, 90, 50);    
            r2.setOpacity(0);
            root.getChildren().add(r2);
            outsideBets.add(r2);

            yPos += 55;
        }
        int xPos = 180;
        for (int i = 0; i < 3; i++) {
            Rectangle r = new Rectangle(xPos, 315, 295, 50);    //dozen bets
            r.setFill(Color.GREEN);
            root.getChildren().add(r);

            Text t = new Text(xPos + 110, yPos + 32, "Dozen " + Integer.toString(i + 1));
            t.setFill(Color.WHITE);
            t.setFont(f1);
            root.getChildren().add(t);

            Rectangle r2 = new Rectangle(xPos, 315, 295, 50);    
            r2.setOpacity(0);
            root.getChildren().add(r2);
            outsideBets.add(r2);

            xPos += 300;
        }
        xPos = 180;
        for (int i = 0; i < 12; i++) {
            Rectangle r = new Rectangle(xPos, 370, 145, 50);
            r.setFill(Color.GREEN);
            root.getChildren().add(r);

            Rectangle r2 = new Rectangle(xPos, 370, 145, 50);    
            r2.setOpacity(0);
            root.getChildren().add(r2);
            outsideBets.add(r2);

            xPos += 150;
        }
        xPos = 230;
        yPos = 405;

        Text t1 = new Text(xPos, yPos, "1-18");
        t1.setFill(Color.WHITE);
        t1.setFont(f1);
        root.getChildren().add(t1);

        Text t2 = new Text(xPos + 150, yPos, "Even");
        t2.setFill(Color.WHITE);
        t2.setFont(f1);
        root.getChildren().add(t2);

        Text t3 = new Text(xPos + 150 * 2, yPos, "Red");
        t3.setFill(Color.RED);
        t3.setFont(f1);
        root.getChildren().add(t3);

        Text t4 = new Text(xPos + 150 * 3, yPos, "Black");
        t4.setFill(Color.BLACK);
        t4.setFont(f1);
        root.getChildren().add(t4);

        Text t5 = new Text(xPos + 150 * 4, yPos, "Odd");
        t5.setFill(Color.WHITE);
        t5.setFont(f1);
        root.getChildren().add(t5);

        Text t6 = new Text(xPos + 150 * 5 - 10, yPos, "19-36");
        t6.setFill(Color.WHITE);
        t6.setFont(f1);
        root.getChildren().add(t6);

    }

    public void drawInsideBets(Group root) throws FileNotFoundException {
        buttons.add(Single);
        buttons.add(Corner);
        buttons.add(Street);
        buttons.add(Split);

        Rectangle white = new Rectangle(175, 500, 610, 200);
        white.setFill(Color.WHITE);
        white.setArcWidth(30);
        white.setArcHeight(30);
        root.getChildren().add(white);

        Rectangle green = new Rectangle(180, 505, 600, 190);
        green.setFill(Color.GREEN);
        green.setArcWidth(20);
        green.setArcHeight(20);
        root.getChildren().add(green);

        Font f = new Font(STYLESHEET_MODENA, 50);
        Text t = new Text(355, 570, "Inside Bets");
        t.setFill(Color.WHITE);
        t.setFont(f);
        root.getChildren().add(t);

        singleButton(root);

        streetButton(root);

        cornerButton(root);

        splitButton(root);
    }

    public void singleButton(Group root) throws FileNotFoundException {
        root.getChildren().add(Single);

        Single.setTranslateX(200);
        Single.setTranslateY(625);
        Single.setPadding(Insets.EMPTY);
        Single.setText("Single");
        Single.setFont(new Font(0));

        Single.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rectangle r = new Rectangle(200, 625, 100, 40);
                r.setOpacity(0.5);
                r.setArcHeight(10);
                r.setArcWidth(10);
                root.getChildren().add(r);
                buttonClickColor(root, r);
                //clickedInsideBetButton = true;

            }
        });

        Image image = new Image(new FileInputStream("src\\Resource\\Single.png"), 2000, 2000, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(40);
        Single.setGraphic(imageView);

    }

    public void cornerButton(Group root) throws FileNotFoundException {
        //   if (!clickedInsideBetButton) {
        Corner.setTranslateX(350);
        Corner.setTranslateY(625);
        Corner.setPadding(Insets.EMPTY);
        Corner.setText("Corner");
        Corner.setFont(new Font(0));

        Corner.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rectangle r = new Rectangle(350, 625, 100, 40);
                r.setOpacity(0.5);
                r.setArcHeight(10);
                r.setArcWidth(10);
                root.getChildren().add(r);
                buttonClickColor(root, r);
                // clickedInsideBetButton = true;
            }
        });

        Image image2 = new Image(new FileInputStream("src\\Resource\\Corner.png"), 2000, 2000, true, true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(40);
        Corner.setGraphic(imageView2);

        root.getChildren().add(Corner);
        //  }
    }

    public void streetButton(Group root) throws FileNotFoundException {
        //   if (!clickedInsideBetButton) {
        Street.setTranslateX(500);
        Street.setTranslateY(625);
        Street.setPadding(Insets.EMPTY);
        Street.setText("Street");
        Street.setFont(new Font(0));

        Street.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rectangle r = new Rectangle(500, 625, 100, 40);
                r.setOpacity(0.5);
                r.setArcHeight(10);
                r.setArcWidth(10);
                root.getChildren().add(r);
                buttonClickColor(root, r);
                //clickedInsideBetButton = true;
            }
        });

        Image image2 = new Image(new FileInputStream("src\\Resource\\Street.png"), 2000, 2000, true, true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(40);
        Street.setGraphic(imageView2);

        root.getChildren().add(Street);
        // }
    }

    public void splitButton(Group root) throws FileNotFoundException {
        // if (!clickedInsideBetButton) {
        Split.setTranslateX(650);
        Split.setTranslateY(625);
        Split.setPadding(Insets.EMPTY);
        Split.setText("Split");
        Split.setFont(new Font(0));

        Split.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rectangle r = new Rectangle(650, 625, 100, 40);
                r.setOpacity(0.5);
                r.setArcHeight(10);
                r.setArcWidth(10);
                root.getChildren().add(r);
                buttonClickColor(root, r);
                // clickedInsideBetButton = true;
            }
        });

        Image image2 = new Image(new FileInputStream("src\\Resource\\Split.png"), 2000, 2000, true, true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(40);
        Split.setGraphic(imageView2);

        root.getChildren().add(Split);
        // }
    }

    public void buttonClickColor(Group root, Rectangle r) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                r.setOpacity(0);
            }
        },
                300
        );
    }

    public void drawMoneyOptions(Group root) throws FileNotFoundException {
        Rectangle white = new Rectangle(875, 500, 610, 200);
        white.setFill(Color.WHITE);
        white.setArcWidth(30);
        white.setArcHeight(30);
        root.getChildren().add(white);

        Rectangle green = new Rectangle(880, 505, 600, 190);
        green.setFill(Color.GREEN);
        green.setArcWidth(20);
        green.setArcHeight(20);
        root.getChildren().add(green);

        Font f = new Font(STYLESHEET_MODENA, 50);
        Text t = new Text(1055, 570, "Bet Options");
        t.setFill(Color.WHITE);
        t.setFont(f);
        root.getChildren().add(t);

        Button b = new Button("25");
        b.setTranslateX(920);
        b.setTranslateY(625);
        b.setPadding(Insets.EMPTY);
        b.setText("25");
        b.setFont(new Font(0));
        Image image = new Image(new FileInputStream("src\\Resource\\$25.png"), 2000, 2000, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(40);
        b.setGraphic(imageView);
        root.getChildren().add(b);
        moneyOptions.add(b);

        Button b2 = new Button("75");
        b2.setTranslateX(1050);
        b2.setTranslateY(625);
        b2.setPadding(Insets.EMPTY);
        b2.setText("75");
        b2.setFont(new Font(0));
        Image image2 = new Image(new FileInputStream("src\\Resource\\75.png"), 2000, 2000, true, true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(110);
        imageView2.setFitHeight(40);
        b2.setGraphic(imageView2);
        root.getChildren().add(b2);
        moneyOptions.add(b2);

        Button b3 = new Button("150");
        b3.setTranslateX(1190);
        b3.setTranslateY(625);
        b3.setPadding(Insets.EMPTY);
        b3.setText("150");
        b3.setFont(new Font(0));
        Image image3 = new Image(new FileInputStream("src\\Resource\\$150.png"), 2000, 2000, true, true);
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(110);
        imageView3.setFitHeight(40);
        b3.setGraphic(imageView3);
        root.getChildren().add(b3);
        moneyOptions.add(b3);

        Button b4 = new Button("AllIn");
        b4.setTranslateX(1330);
        b4.setTranslateY(625);
        b4.setPadding(Insets.EMPTY);
        b4.setText("AllIn");
        b4.setFont(new Font(0));
        Image image4 = new Image(new FileInputStream("src\\Resource\\All_In.png"), 2000, 2000, true, true);
        ImageView imageView4 = new ImageView(image4);
        imageView4.setFitWidth(100);
        imageView4.setFitHeight(40);
        b4.setGraphic(imageView4);
        root.getChildren().add(b4);
        moneyOptions.add(b4);
    }

    public void fillBlackNums(Group root) {
        int[] blackNums = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
        for (int i = 0; i < blackNums.length; i++) {//put blacknums into the arraylist
            this.blackNums.add(blackNums[i]);
        }

        for (int i = 0; i < rects.size(); i++) {
            for (int j = 0; j < blackNums.length; j++) {

                if (i == blackNums[j] - 1) {
                    int index = root.getChildren().indexOf(rects.get(i)) - 6;
                    ((Rectangle) (root.getChildren().get(index))).setFill(Color.BLACK);
                }

            }

        }

    }

    public void drawWheel(Group root) throws FileNotFoundException, InterruptedException {
        Image image = new Image(new FileInputStream("src\\Resource\\Wheel.png"), 2000, 2000, true, true);
        ImagePattern ip = new ImagePattern(image);
        wheel = new Circle(1425, 275, 200);
        wheel.setFill(Color.GREY);
        wheel.setFill(ip);
        root.getChildren().add(wheel);

        Circle c = new Circle(1425, 275, 160);
        c.setFill(Color.GREY);
        c.setOpacity(1);
        root.getChildren().add(c);

        Font f = new Font(120);
        winNumText.setX(1365);
        winNumText.setY(200);
        winNumText.setTextOrigin(VPos.TOP);
        winNumText.setFont(f);
        root.getChildren().add(winNumText);

    }

    public void rotateWheel(Group root, int angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(5000), wheel);
        rt.setByAngle(angle);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.play();

        int resetAng = 360 - angle % 360;
        RotateTransition reset = new RotateTransition(Duration.millis(1000), wheel);
        reset.setByAngle(resetAng);
        reset.setCycleCount(1);
        reset.setAutoReverse(false);

        rt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                checkWin(root, winNum);
                winNumText.setText(String.valueOf(winNum));

                resetPlayers(root);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("That sucks");
                }
                removeChipsOnBoard(root);
                checkPlayerPoor(root);
                reset.play();
            }
        });

    }

    public void drawPlayers(Group root) throws FileNotFoundException {
        int xPos = 150;
        for (int i = 0; i < players.size(); i++) {
            Rectangle r = new Rectangle(xPos, 750, 300, 220);
            r.setFill(Color.WHITE);
            r.setOpacity(0.5);
            r.setArcWidth(20);
            r.setArcHeight(20);
            root.getChildren().add(r);
            playerSquares.add(r);

            Font f = new Font(30);
            Text t = new Text(xPos + 15, 800, players.get(i).getName());
            t.setFill(Color.WHITE);
            t.setFont(f);
            root.getChildren().add(t);
            playerSquares.add(t);

            Image image = new Image(new FileInputStream("src\\Resource\\blackChip.png"), 2000, 2000, true, true);
            ImagePattern ip = new ImagePattern(image);
            Circle c = new Circle(xPos + 75, 890, 60);
            c.setFill(ip);
            root.getChildren().add(c);
            playerSquares.add(c);

            Font f2 = new Font(45);
            Text chips = new Text(xPos + 145, 900, Integer.toString(players.get(i).getChips()));
            chips.setFill(Color.WHITE);
            chips.setFont(f2);
            root.getChildren().add(chips);
            playerSquares.add(chips);

            Rectangle cover = new Rectangle(xPos, 750, 300, 220);
            cover.setFill(Color.GREY);
            cover.setOpacity(0.5);
            cover.setArcWidth(20);
            cover.setArcHeight(20);
            root.getChildren().add(cover);
            playerSquares.add(cover);
            playerSquareCovers.add(cover);

            xPos += 350;
        }
        playerSquareCovers.get(0).setOpacity(0);
    }

    public void movePlayers(Group root, int xLoc) {
        for (int i = 0; i < playerSquares.size(); i++) {
            TranslateTransition t = new TranslateTransition(new Duration(3000), (Node) playerSquares.get(i));
            t.setToX(xLoc);
            t.play();
        }
    }

    public void roll(Group root) {
        winNum = rand.nextInt(37);
        int angle = 0;
        int wheelNums[] = new int[]{0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26};

        for (int i = 0; i < wheelNums.length; i++) {
            if (winNum == wheelNums[i]) {
                angle = 360 * 3 + 360 - 15 - (360 / 37 * i);
            }
        }

        rotateWheel(root, angle);

    }

    public void resetPlayers(Group root) {
        playerNum = 0;
        playerXLoc = -325;
        for (int i = 0; i < playerSquareCovers.size(); i++) {
            playerSquareCovers.get(i).setOpacity(0.5);
            playerSquareCovers.get(0).setOpacity(0);
        }
        for (int i = 0; i < playerSquares.size(); i++) {
            TranslateTransition t = new TranslateTransition(new Duration(3000), (Node) playerSquares.get(i));
            t.setToX(0);
            t.play();
        }
    }

    public void checkWin(Group root, int winNum) {
        for (int i = 0; i < bets.size(); i++) {
            if (bets.get(i).getNumsBetOn().contains(winNum)) {
                int payout = (int) (bets.get(i).getAmountBet() * bets.get(i).getPayout());
                players.get(bets.get(i).getPlayerNum()).payout(payout);

            }
        }
        updatePlayerMoneyText(root);
    }

    public void updatePlayerMoneyText(Group root) {
        for (int i = 3; i < playerSquares.size(); i += 5) {
            if (playerSquares.get(i).getClass() == Text.class) {
                String text = Integer.toString(players.get((i - 3) / 5).getChips());

                int index = root.getChildren().indexOf((Text) playerSquares.get(i));
                ((Text) (root.getChildren().get(index))).setText(text);
            }
        }
    }

    public void flyingChip(Group root, Scene scene) throws FileNotFoundException {
        Cylinder cl = new Cylinder(100, 15);
        cl.setTranslateX(500);
        cl.setTranslateY(500);
        cl.setTranslateZ(5000);

        PerspectiveCamera camera = new PerspectiveCamera(false);
        scene.setCamera(camera);

        cl.setRotationAxis(Rotate.X_AXIS);
        cl.setRotate(90);

        root.getChildren().add(cl);

        PhongMaterial m = new PhongMaterial();
        Image image = null;
        image = new Image(new FileInputStream("src\\Resource\\blackChip.png"), 5000, 5000, true, true);

        m.setDiffuseMap(image);
        cl.setMaterial(m);

        TranslateTransition t = new TranslateTransition(new Duration(5000), cl);
        t.setToZ(-5000);
        t.play();
        TranslateTransition t2 = new TranslateTransition(new Duration(5000), cl);
        t2.setToX(1000);
        t2.play();

        RotateTransition r = new RotateTransition(new Duration(4500), cl);
        r.setByAngle(3000);
        r.setAxis(new Point3D(20, 20, 20));
        r.play();
    }

    public void coverBoard(Group root) {
        coverBoard = new Rectangle(180, 150, 900, 160);
        coverBoard.setOpacity(0);
        root.getChildren().add(coverBoard);
        coverBoard.toFront();
    }

    public void uncoverBoard(Group root) {
        root.getChildren().remove(coverBoard);
    }

    public void coverBets(Group root) {
        coverBets = new Rectangle(180, 150, 1200, 380);
        coverBets.setOpacity(0);
        root.getChildren().add(coverBets);
    }

    public void uncoverBets(Group root) {
        root.getChildren().remove(coverBets);
    }

    public void QuestionMark(Group root) throws FileNotFoundException {
        Circle c = new Circle(500, 60, 40);
        c.setFill(Color.DARKGREEN);
        root.getChildren().add(c);

        Image image = new Image(new FileInputStream("src\\Resource\\QuestionMark.png"), 5000, 5000, true, true);
        c.setFill(new ImagePattern(image));

        inst1 = new Rectangle(0, 0, 2500, 2000);
        inst1.setFill(Color.ALICEBLUE);
        root.getChildren().add(inst1);
        inst1.setOpacity(0);
        inst2 = new Rectangle(200, 0, 1300, 1000);
        Image image2 = new Image(new FileInputStream("src\\Resource\\howToPlay.png"), 7000, 7000, true, true);
        inst2.setFill(new ImagePattern(image2));
        inst2.setOpacity(0);
        root.getChildren().add(inst2);

        Circle c2 = new Circle(500, 60, 40);
        c2.setOnMouseClicked(instructions);
        c2.setOpacity(0);
        root.getChildren().add(c2);
        System.out.println("asdf");

        instBack.setFont(new Font(20));
        instBack.setTranslateX(100);
        instBack.setTranslateY(700);
        root.getChildren().add(instBack);
        instBack.toBack();
        instBack.setVisible(false);

        instBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                inst1.toBack();
                inst2.toBack();
                inst1.setOpacity(0);
                inst2.setOpacity(0);
                instBack.toBack();
                instBack.setVisible(false);
            }
        });

    }

    EventHandler<Event> instructions = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            System.out.println("inst");
            inst1.toFront();
            inst2.toFront();
            inst1.setOpacity(1);
            inst2.setOpacity(1);
            instBack.toFront();
            instBack.setVisible(true);

        }

    };

    public void placeChip(ArrayList<Integer> nums, Group root) throws FileNotFoundException {
        for (int i = 0; i < nums.size(); i++) {
            int numsBefore = -10;
            for (int j = 0; j < bets.size(); j++) {//goes through all bets
                if (bets.get(j).getNumsBetOn().contains(nums.get(i))) {
                    numsBefore += 10;
                }
            }
            System.out.println(numsBefore + "numsBefore");

            int colNum = ((nums.get(i) - 1) / 3);

            int rowNum = -1;
            if ((nums.get(i) - 1) % 3 == 0) {
                rowNum = 0;
            } else if ((nums.get(i) - 2) % 3 == 0) {
                rowNum = 1;
            } else if ((nums.get(i)) % 3 == 0) {
                rowNum = 2;
            }
            int x = 210 + 75 * colNum;
            int y = 175 + (55 * rowNum) - numsBefore;

            Circle c = new Circle(x, y, 20);
            root.getChildren().add(c);

            Color color = Color.BLUEVIOLET;;
            if (playerNum == 1) {
                color = Color.ALICEBLUE;
            } else if (playerNum == 2) {
                color = Color.DARKORANGE;
            } else if (playerNum == 3) {
                color = Color.CADETBLUE.brighter();
            } else if (playerNum == 4) {
                color = Color.HOTPINK;
            } else if (playerNum == 5) {
                color = Color.BROWN;
            } else if (playerNum == 6) {
                color = Color.LIGHTSEAGREEN.saturate();
            } else {
                c.setFill(Color.PLUM);
            }
            c.setFill(color);
            c.setOpacity(0.8);
            chipsOnBoard.add(c);

            Circle c2 = new Circle(x, y, 15, Color.WHITE);
            c2.setOpacity(0.3);
            root.getChildren().add(c2);
            chipsOnBoard.add(c2);

            rectsToFront(root);//makes rects clickable, not covered by chips

        }

    }

    public void rectsToFront(Group root) {
        for (int i = 0; i < rects.size(); i++) {
            rects.get(i).toFront();
        }
    }

    public void removeChipsOnBoard(Group root) {
        for (int i = 0; i < chipsOnBoard.size(); i++) {
            int index = root.getChildren().indexOf(chipsOnBoard.get(i));
            root.getChildren().remove(index);
        }
    }

    public void checkPlayerPoor(Group root) {
        if (players.get(0).getChips() <= 0) {
            Rectangle r = new Rectangle(0, 0, 3000, 3000);
            r.setOpacity(0.9);
            r.setFill(Color.WHITE);
            root.getChildren().add(r);

            Text t = new Text("You have no money left.");
            t.setFont(new Font(50));
            t.setTranslateX(625);
            t.setTranslateY(400);
            root.getChildren().add(t);

            Text t2 = new Text("Roulette will exit");
            t2.setFont(new Font(50));
            t2.setTranslateX(700);
            t2.setTranslateY(550);
            root.getChildren().add(t2);

            Button b = new Button("OK");
            b.setFont(new Font(35));
            b.setTranslateX(800);
            b.setTranslateY(700);

            root.getChildren().add(b);

            b.setOnAction(e -> Casino.primaryStage.setScene(Casino.menu));

            Button b2 = new Button("Buy $500");
            b2.setFont(new Font(35));
            b2.setTranslateX(750);
            b2.setTranslateY(850);

            root.getChildren().add(b2);

            b2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    players.get(0).setChips(500);
                    root.getChildren().remove(r);
                    root.getChildren().remove(t);
                    root.getChildren().remove(t2);
                    root.getChildren().remove(b);
                    root.getChildren().remove(b2);
                    updatePlayerMoneyText(root);

                }
            });

        }
    }
}
