import java.util.ArrayList;
import java.io.*;
import java.lang.String;

public class Uno
{
    

    ArrayList<Player> playerList;
    Deck mainDeck, discardDeck;
    int playerNumber;
    InputStreamReader in = new InputStreamReader(System.in);
    BufferedReader keys = new BufferedReader(in);


    
    public static void main(String args[]) throws IOException
    {
        Uno u2 = new Uno();
        UnoFrame u = new UnoFrame();
        //u.setVisible();
        u.start();
        u.play();
        //u.endRound();
        
    }

    
    
    
    
   }
