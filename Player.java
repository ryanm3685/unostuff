import java.util.ArrayList;

public class Player
{
    private ArrayList<Card> playerCards = new ArrayList<Card>();
    private String name;
    private ArrayList<Integer> theScores = new ArrayList<Integer>();
    private int total; //total score so far
    private boolean computer = false; //is this an AI player?
    
    public Player(String name)
    {
        this.name = name;
        total = 0;
    }

    public void addCard(Card theCard)
    {
        playerCards.add(theCard);
    }
    
    public Card removeCard(int cardIndex)
    {
        Card ret = playerCards.get(cardIndex);
        playerCards.remove(cardIndex);
        return ret;
    }
    
    public Card removeCard(Card c)
    {
        playerCards.remove(playerCards.indexOf(c));
        return c;
    }

    
    public void removeAllCards()
    {
        playerCards.clear();
    }
    
    public String getName() { return name; }
    
    public int getTotal() { return total; }

    public int getScore(int i) { return theScores.get(i); }
    
    public int getScoreSize() { return theScores.size(); }
    
    public void addScore(int score)
    {
        total += score;
        theScores.add(score);
    }
    
    public String printScores()
    {
        String s = "";
        for (Integer i : theScores) s += (i + "\n") ;
        return s;
    }
    
    public void getCards()
    {
        for (Card c : playerCards)
            System.out.println(playerCards.indexOf(c) + " " + c);
    }
    
    public int getNumberOfCards()
    {
        return playerCards.size();
    }
    
    public Card chooseCard(int cardIndex)
    {
        return playerCards.get(cardIndex);
    }
    
    public boolean isEmpty() { return playerCards.isEmpty(); }
    
    public void setIsComputer(boolean b) {computer = b; }
    
    public boolean getIsComputer() { return computer; }
    
}