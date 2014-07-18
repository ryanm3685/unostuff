import javax.swing.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import java.awt.*;

import java.util.ArrayList;
import java.io.*;
import java.lang.String;
import javax.imageio.*;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.Random;

public class UnoFrame extends JFrame implements ActionListener
{
    ArrayList<Player> playerList;
    Deck mainDeck, discardDeck;

    Container c; //main panel/container
    private int playerNumber; //number of players entered
    String Names[]; //array of all names typed in
    boolean [] computerPlayer;
    String currentName; //whose turn?
    JLabel currentNameLabel; //label for above
    JLabel compareCardLabel; //what card is in play, that the number or color needs to be matched
    //by the player
    JButton [] cardButtons; //buttons representing the cards
    //the current player has
    JLabel compLabel;
    Container vertContainer;
    JButton drawCard; //if the player needs to draw a card
    JButton skipSelf; //if player needs to skip themselves
    JButton continueButton; //for the end of round, to get to the next round
    JLabel currentColorLabel;
    String currentColor; //what color can be matched
    
    Player currentPlayer;
    Card currentCard;
    Card compareCard; //the card that needs to be matched
    
    int currentPlayerNumber = 0; //index of player arraylist
    int eliminationScore = 150; //when a player reaches this score, they are eliminated
    boolean lastWild = false; //was the last card wild?
    boolean reverse = false; //playing in the other direction after a reverse card thrown?
    
    Player theCurrentPlayer; //whose turn right now?
    Uno u2; //game
    
    UnoFrame u;
    TableFrame tf;
    Random r; //used for random number generation

    public UnoFrame()
    {
        u = this;
        r = new Random();
        //Object[] options = { "Yes", "No" }; //for computer player dialog
        //Object[] onlyoption = { "OK" };
        
        Object[] choices = {"2", "3", "4", "5"};
        String s = (String)JOptionPane.showInputDialog(this, "How many players?", "Player count",JOptionPane.PLAIN_MESSAGE, null, choices, "2");
        
        playerNumber = Integer.parseInt(s);
        computerPlayer = new boolean[playerNumber];
        Names = new String[playerNumber];
        
        for (int i = 0; i < playerNumber; i++)
        {
            Names[i] = (String)JOptionPane.showInputDialog(this, "Who is player " + (i + 1) + "?", null, JOptionPane.PLAIN_MESSAGE, null, null, null);
            
            int j = JOptionPane.showOptionDialog(null, "Is " + Names[i] + " a computer player?", " ",
                                         JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                         null, null, null);
            computerPlayer[i] = (j == JOptionPane.YES_OPTION);
        }
        
        setVisible(true);
        
        currentNameLabel = new JLabel();
        compareCardLabel = new JLabel();
        
        c = getContentPane();
        vertContainer = new Container();
        
        drawCard = new JButton("Draw");
        skipSelf = new JButton("Skip Yourself");
        currentColorLabel = new JLabel();
        
        drawCard.addActionListener(this);
        skipSelf.addActionListener(this);
        
        drawCard.setActionCommand("drawCard");
        skipSelf.setActionCommand("skipSelf");
        
        vertContainer.add(drawCard);
        vertContainer.add(skipSelf);
        vertContainer.add(currentColorLabel);
        
        
    }
    
    public void start() throws IOException
    {
        mainDeck = new Deck(true);
        discardDeck = new Deck(false);
        
        mainDeck.Shuffle();
        //get number of players
        
        playerNumber = getNumber();
        playerList = new ArrayList<Player>(playerNumber);
        
        //name players
        for (int i = 0; i < playerNumber; i++)
        {
            playerList.add(new Player(Names[i]));
            playerList.get(i).setIsComputer(computerPlayer[i]);
        }
        
        //get table frame stuff set up
        Vector playerNames = new Vector();//get names of all players to pass to TableFrame constructor
        for (Player p : playerList) playerNames.add(p.getName());
        tf = new TableFrame(playerNames, u);
        Deal(mainDeck, playerList);
    }
    
    public void restart()
    {
        while (!discardDeck.isEmpty()) mainDeck.addNewCard(discardDeck.getTopCard());
        
        for (Player p : playerList)
            p.removeAllCards();
        
        mainDeck.Shuffle();
        Deal(mainDeck, playerList);
        u.setVisible(true);
        play();
    }
    
    public void Deal(Deck mainDeck, ArrayList<Player> playerList)
    {
        //7 cards per player
        for (int i = 0; i < 7; i++)
        {
            for (Player p : playerList)
                p.addCard(mainDeck.getTopCard());
        }
        
        //flip first card into discard pile
        discardDeck.addNewCard(mainDeck.getTopCard());
    }

    public void play()
    {
        
            currentPlayer = playerList.get(currentPlayerNumber);
            currentCard = discardDeck.showTopCard();
            
            compareCard = discardDeck.showTopCard();
        
            int theNumber = currentCard.getValue();
            if (!lastWild) currentColor = currentCard.getColor();//otherwise theColor
            //will be whatever the person who threw the wild chooses
        
            //end round if empty
            //if (currentPlayer.isEmpty()) return;
            redraw();
    }

    
    public int getNumber() { return playerNumber; }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    /*protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ButtonDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }*/
    
    public void redraw()
    {
        //if there were already card buttons drawn, get rid of them
        //to make space for the new ones
        if (cardButtons != null)
        {
            for (JButton b : cardButtons) c.remove(b);
        }
        if (compLabel != null) c.remove(compLabel);
        c.setLayout(new FlowLayout());
        c.add(vertContainer);
        vertContainer.setLayout(new BoxLayout(vertContainer, BoxLayout.Y_AXIS));

        vertContainer.add(currentNameLabel);
        vertContainer.add(compareCardLabel);
        currentName = currentPlayer.getName();
        currentNameLabel.setText(currentName);
        compareCardLabel.setText("Current card is " + compareCard.toString());
        currentColorLabel.setText("Color is " + currentColor);
        
        
        int numberOfCards = currentPlayer.getNumberOfCards();
        
        
        
        if (!currentPlayer.getIsComputer()) cardButtons = new JButton[numberOfCards];
        
        if (currentPlayer.getIsComputer()) //computer player?
        {
            compLabel = new JLabel(currentPlayer.getName() + "'s turn");
            c.add(compLabel);
            Card bestCard = null; //what card has the highest value, the one that will be played?
            Card someCard = null;
            if (playable(currentPlayer.chooseCard(0))) someCard = currentPlayer.chooseCard(0);
            if (someCard != null) bestCard = someCard;
            for (int i = 1; i < numberOfCards; i++)
            {
                someCard = currentPlayer.chooseCard(i);
                //compare cards value to possibly replace it
                if ((someCard != null) && playable(someCard) && ((bestCard == null) || (someCard.getValue() > bestCard.getValue())))
                    bestCard = someCard;
            }
            
            if (someCard == null) //was there no playable card?
            {
                currentPlayer.addCard(mainDeck.getTopCard());
                int s = currentPlayer.getNumberOfCards() - 1; //most recently drawn card
                if (playable(currentPlayer.chooseCard(s))) bestCard = currentPlayer.chooseCard(s);

            }
            
            
            //play card if there is one to play
            if (bestCard != null)
            {
                discardDeck.addNewCard(currentPlayer.removeCard(bestCard));

                //handle word cards
                if (bestCard.getColor().equals("black"))
                {
                    lastWild = true;
                    currentColor = chooseWild();
                }

                switch (bestCard.getValue()) //10 = skip, 11 = reverse, 12 = draw 2
                {
                    case 10:
                    break;
                    case 11:
                    reverse();
                    break;
                    case 12:
                    drawCards(2);
                    break;
                }

            }
            
            if (bestCard != null)
            {
                System.out.println(currentPlayer.getName() + " played " + bestCard.getColor() + " " + bestCard.getValue());
                if (bestCard.getColor().equals("black"))
                    System.out.println(currentColor);
            }
            else System.out.println(currentPlayer.getName() + " has nothing");

            if (currentPlayer.isEmpty()) endRound();
            else
            {
                nextPlayer();
                play();
            }
        }
        else //human player?
        {
            
            //now print buttons for each card
            for (int i = 0; i < numberOfCards; i++)
            {
                Card someCard = currentPlayer.chooseCard(i);
                cardButtons[i] = new JButton(someCard.toString());
                try {
                    String s = "/cardpics/" + someCard.toString();
                    Image img = ImageIO.read(new File(s));
                    cardButtons[i].setIcon(new ImageIcon(img));
                } catch (IOException ex) { }
            
                //see if button representing the given card is playable,
                //if not, make it unclickable
                if (!playable(someCard)) cardButtons[i].setEnabled(false);
            
                cardButtons[i].addActionListener(this);
                cardButtons[i].setActionCommand(Integer.toString(i));
                //card
                c.add(cardButtons[i]);
            }
        }
           
        pack();
    }
    
    public boolean playable(Card someCard)
    {
        return ((someCard.getValue() == compareCard.getValue()) || ((someCard.getColor()).compareTo(compareCard.getColor()) == 0) || ((someCard.getColor()).compareTo("black") == 0) || (someCard.getColor().compareTo(currentColor) == 0));
    }
    
    public void endRound()
    {
        if (cardButtons != null)
        {
            for (JButton b : cardButtons) c.remove(b);
        }

        skipSelf.setEnabled(false);
        drawCard.setEnabled(false);

        currentNameLabel.setText(" ");
        compareCardLabel.setText(" ");
        currentColorLabel.setText(" ");

        continueButton = new JButton("Continue");
        continueButton.addActionListener(this);
        continueButton.setActionCommand("Continue");
        c.add(continueButton);

        ArrayList<Player> removeList = new ArrayList<Player>();
        
        System.out.println("in endRound");
        //add up players' scores for remaining cards
        for (Player p : playerList)
        {
            int roundScore = 0; //how many points this round?
            
            for (int i = 0; i < p.getNumberOfCards(); i++)//each card
                roundScore += p.chooseCard(i).getPoints();
            
            p.addScore(roundScore);
            if (p.getTotal() > eliminationScore) removeList.add(p);
        }
        for (Player p : removeList) eliminate(p);//get rid of players who went over the score
        
        if (playerList.size() == 1)//somebody won
        {
            JOptionPane.showMessageDialog(u,
                                          playerList.get(0).getName() + " has won it all!",
                                          "Congrats!",
                                          JOptionPane.PLAIN_MESSAGE);
            System.exit(0); //TODO: make it so that players can play again with new players

        }
        
        tf.addData(playerList);
        tf.pack();
        tf.setVisible(true);
    }
    
    public void eliminate(Player p)
    {
        playerList.remove(p);
        JOptionPane.showMessageDialog(u,
                                      p.getName() + " has gone bye bye!",
                                      "See ya",
                                      JOptionPane.PLAIN_MESSAGE);
    }

    public void reverse()
    {
        if (!reverse) reverse = true;
        else reverse = false;
        //reverse acts like a skip for two player games
        if (playerList.size() == 2) nextPlayer();
    }

     public void drawCards(int n)
    {
        nextPlayer();
        //draw the n cards
        for (int i = 0; i < n; i++)
            playerList.get(currentPlayerNumber).addCard(mainDeck.getTopCard());
    }


    public String chooseWild()
    {
        HashMap<String, Integer> colormap = new HashMap<String, Integer>();
        colormap.put("red", 0);
        colormap.put("yellow", 0);
        colormap.put("blue", 0);
        colormap.put("green", 0);
        //0 = red, 1 = yellow
    //2 = blue, 3 = green, 4 = black (wild)
        int red, yellow, blue, green;
        for (int i = 0; i < currentPlayer.getNumberOfCards(); i++)
        {
            String aColor = currentPlayer.chooseCard(i).getColor(); //what's the color of this card
            if (!(aColor.equals("black")))
            { 
                int count = colormap.get(aColor); //number of cards in hand which have this color
                colormap.put(aColor, count + 1); //increase the number of cards of that color
                //in the hashmap
            }
        }

        Set<String> thecolors = colormap.keySet();

        int max = 0;
        String retVal = ""; //return value - name of color
        for (String s : thecolors)
        {
            if (colormap.get(s) >= max)
            {
                max = colormap.get(s);
                retVal = s;
            }
        }

        return retVal;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if ("drawCard".equals(e.getActionCommand()))
        {
            currentPlayer.addCard(mainDeck.getTopCard());
            redraw();
            return;
        }
        else if ("skipSelf".equals(e.getActionCommand()))
        {
    
        }
        //done looking at scores
        else if ("Continue".equals(e.getActionCommand()))
        {   
            c.remove(continueButton);//continue button shouldn't be around during play
            tf.setVisible(false);//don't want to see the scores table frame
            skipSelf.setEnabled(true);//bring back necessary buttons for play
            drawCard.setEnabled(true);
            restart(); //start next round
        }
        else
        {
            int cardIndex = Integer.parseInt(e.getActionCommand());
            //play the card corresponding to the given index
            //add that card to discard pile as card to be played
            Card theCard = currentPlayer.chooseCard(cardIndex);
            //handle word cards 10 = skip, 11 = reverse, 12 = draw 2, 14 = wild draw 4

            if (theCard.getValue() == 10) nextPlayer(); //move to the next player
            if (theCard.getValue() == 11) reverse(); //reverse direction
            if (theCard.getValue() == 12) drawCards(2);    

            
            if (theCard.getColor().compareTo("black") == 0)
            {
                lastWild = true;
                Object[] colorchoices = {"red", "green", "blue", "yellow"};
                currentColor = (String)JOptionPane.showInputDialog(this, "Choose Color", "Choose Color",JOptionPane.PLAIN_MESSAGE, null, colorchoices, "red");
            }
            else
            {
                lastWild = false;
                currentColor = theCard.getColor();
            }
            discardDeck.addNewCard(currentPlayer.removeCard(cardIndex));
            if (currentPlayer.isEmpty()) endRound();//player got rid of all their cards?
        }
        
        //next player
        nextPlayer();
        
        play();
        
    }

    
    public void nextPlayer()
    {
        if (!reverse) //going forward
        {
            if (currentPlayerNumber == (playerList.size() - 1))
                currentPlayerNumber = 0;
            else currentPlayerNumber++;
        }
        else //going in reverse
        {
            if (currentPlayerNumber == 0)
                currentPlayerNumber = (playerList.size() - 1);
                else currentPlayerNumber--;
        }

        currentPlayer = playerList.get(currentPlayerNumber);

    }
    
    public void prevPlayer()
    {
        if (reverse) //going backward
        {
            if (currentPlayerNumber == (playerList.size() - 1))
                currentPlayerNumber = 0;
            else currentPlayerNumber++;
        }
        else //going forward
        {
            if (currentPlayerNumber == 0)
                currentPlayerNumber = (playerList.size() - 1);
            else currentPlayerNumber--;
        }

        currentPlayer = playerList.get(currentPlayerNumber);

    }
}