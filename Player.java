/**
 * Reference to the location of the player. Has three boolean instance variables that check if the player found the treasure, found clue, and found the key.
 *
 *
 * @author Sean Chen
 */
public class Player
{
    private int xPlayer,yPlayer;//keeps the coordinates of the player
    public boolean foundTreasure;//checks to see if the treasure was found
    public boolean foundClue;//checks to see if the clue was found
    public boolean key;//checks to see if the key was found
    public Player(int xcoordinate, int ycoordinate)
    {
        xPlayer=xcoordinate;
        yPlayer=ycoordinate;
    }
    //returns xcoordinates
    public int getPlayerX()
    {
        return xPlayer;
    }
    //returns the ycoordintes
    public int getPlayerY()
    {
        return yPlayer;
    }
    //sets the xcoordinates
    public void setX(int num, String plusminus)
    {
        if(plusminus=="+")
            xPlayer+=num;
        if(plusminus=="-")
            xPlayer-=num;
    }
    //sets the ycoordinates
    public void setY(int num, String plusminus)
    {
        if(plusminus=="+")
            yPlayer+=num;
        if(plusminus=="-")
            yPlayer-=num;
    }

    public void override( int x, int y)
    {
        xPlayer=x;
        yPlayer=y;
    }


}
