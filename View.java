import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class View extends JPanel
{

	Model model; //model member
	
	BufferedImage Background;
	
	View(Controller c, Model m) //must pass the model in 
	{
		model = m; //initializes model, changes it from null
		
		try
		{
			Background = ImageIO.read(getClass().getResourceAsStream("Background.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	public void paintComponent(Graphics g) //draws image
	{
		//clear the screen
		super.paintComponent(g);
		
		//draw the background
		g.drawImage(Background, (int)(0 - model.mario.x * .1), 0, null);
		
		//draw sprites
		for(int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).Draw(g);
		}
		
	}

}
