import java.util.Stack;
import java.util.Random;

public class Deck
{
    private Stack<Card> theDeck = new Stack<Card>();

    

    public Deck(boolean isMain)
    {
        if (isMain)
        {
       
        //do this twice
        for (int h = 0; h <= 1; h++)
        {
            for (int i = 0; i < 4; i++)
            {
                String color = "black";
                
                switch (i)
                {
                    case 0:
                        color = "red";
                        break;
                    case 1:
                        color = "yellow";
                        break;
                    case 2:
                        color = "blue";
                        break;
                    case 3:
                        color = "green";
                        break;
                }
                for (int j = 0; j <13; j++) theDeck.push(new Card(j, color));
                theDeck.push(new Card(13, "black")); //4 wilds
                theDeck.push(new Card(14, "black")); //4 draw 4s
            }
            
            
        }
            
        }
    }
    
    public boolean isEmpty() { return theDeck.empty(); }
    
    public void Shuffle()
    {
        Random r = new Random();
        Stack<Card> first = new Stack<Card>();
        Stack<Card> second = new Stack<Card>();
        int cut;
        for (int i = 0; i < 7; i++)//do this seven times
        {
            cut = r.nextInt(40) + 1; //which card to cut at?
            //System.out.println("cut = " + cut);
            for (int j = 0; j < cut; j++)//make first part of cut
            {
                first.push(theDeck.pop());
            }
            
            
            //now do second part of cut (remainder of deck)
            while (!theDeck.empty())
                second.push(theDeck.pop());
            
            //alternate cards between decks as long as they are not empty
            while (!first.empty() && !second.empty())
            {
                theDeck.push(first.pop());
                theDeck.push(second.pop());
            }
            
            //once a deck is empty, just fill theDeck from remaining deck
            if (first.empty())
                while (!second.empty())
                    theDeck.push(second.pop());
            
            else if (second.empty())
                while (!first.empty())
                    theDeck.push(first.pop());
            
        }
    }
    
    public Card getTopCard()
    {
        if (isEmpty()) Shuffle();
        return theDeck.pop();
    }
    public Card showTopCard() { return theDeck.peek(); }
    public void addNewCard(Card c)
    {
        theDeck.push(c);
    }
    
    /*public static void main(String args[])
    {
        Deck d = new Deck();
        d.Shuffle();
        
        while (!d.isEmpty()) System.out.println(d.getTopCard());
    }*/
}