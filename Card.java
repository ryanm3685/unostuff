public class Card
{
    private int value; //0-9 face value,
    //10 = skip, 11 = reverse, 12 = draw 2, 13 = wild
    //14 = wild draw 4
    private String color; //0 = red, 1 = yellow
    //2 = blue, 3 = green, 4 = black (wild)
    private int points; //word cards are 20
    //wilds are 50
    
    public Card(int value, String color)
    {
        this.value = value;
        this.color = color;
        if (value < 10) points = value;
        else if ((value >= 10) && (value < 13)) points = 20;
        else if ((value == 13) || (value == 14)) points = 50;
    }
    
    public int getValue() {return value;}
    public String getColor() {return color;}
    public int getPoints() {return points;}
    public String toString() { return value + color; }
}