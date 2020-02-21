import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Random;


public class Coin extends Sprite
{
	
	static Image coin_image;
	int verticalVelocity;
	int horizontalVelocity;
	
	static Random randomNumber = new Random();
	
	Coin(int x_pos, int y_pos, int width, int height) 
	{
		super(x_pos, y_pos, width, height);
		
		verticalVelocity = -5;
		horizontalVelocity = (int)(50 * Math.random() - 25);
		
		try
		{
			if(coin_image == null)
			{
				coin_image = ImageIO.read(getClass().getResourceAsStream("coin_image.png"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	Coin(Coin c)
	{
		super(c);
		this.verticalVelocity = c.verticalVelocity;
		this.horizontalVelocity = c.horizontalVelocity;
	}
	
	@Override
	void Update() 
	{
		verticalVelocity += 2;
		y += verticalVelocity;
		x += horizontalVelocity;
	}

	@Override
	void Draw(Graphics g) 
	{
			g.drawImage(Coin.coin_image, this.x - scrollPos, this.y, this.w, this.h, null);
		
	}

	@Override
	boolean isMario() 
	{
		return false;
	}

	@Override
	Json marshall() 
	{
		Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("collidedWithMario", collidedWithMario);
		ob.add("scrollPos", scrollPos);
		ob.add("needCoin", needCoin);
        ob.add("verticallVelocity", verticalVelocity);
        ob.add("horizontalVelocity", horizontalVelocity);
        ob.add("type", "coin");
		return ob;
	}
	
	Coin(Json ob)
	{
		super(ob);
		
		verticalVelocity = (int)ob.getLong("verticalVelocity");
		horizontalVelocity = (int)ob.getLong("horizontalVelocity");
	}

	@Override
	boolean isCoin() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	Sprite Copy() 
	{
		return new Coin(this);
	}
}
