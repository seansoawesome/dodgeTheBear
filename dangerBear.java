
/**
 * This is a bear that roams around the map. Any contact means death.
 *
 * @author Sean Chen
 */
import javax.swing.*;
public class dangerBear
{
    int xBear,yBear;//coordinates of the bear
    boolean contact;//checks if the bear mauled you
    int xLimit,yLimit;//keeps the bear within the map
    int easy,medium,hard,diabolical,choice;//difficulty setting, the speed of the bear
    public dangerBear(int x,int y)
    {
        xLimit=x;
        yLimit=y;
        xBear=x;
        yBear=y;
        easy=7;
        medium=14;
        hard=28;
        diabolical=50;
    }
    //difficulty settings option pops up in the beginning of the game
    public void setDifficulty()
    {
        String[] options = new String[] {"Easy", "Medium", "Hard", "Diabolical"};
        int response = JOptionPane.showOptionDialog(null, "Choose the difficulty setting for this game", "Difficulty Setting",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        // Where response == 0 for Yes, 1 for No, 2 for Maybe and -1 or 3 for Escape/Cancel.
        if(response==0)
        {
            choice=easy;
        }else if(response==1)
        {
            choice=medium;
        }else if(response==2)
        {
            choice=hard;
        }else
        {
            choice=diabolical;
        }
    }

    public int getXBear()
    {
        return xBear;
    }

    public int getYBear()
    {
        return yBear;
    }

    public void moveforward()
    {
        xBear-=choice;
    }
    public void override( int x, int y)
    {
        xBear=x;
        yBear=y;
    }
}
