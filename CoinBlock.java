import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class CoinBlock extends Sprite
{
	static Image coinBlock_image;
	static Image coinBlockDead_image;
	
	int coinCount;

	CoinBlock(int x_pos, int y_pos, int width, int height) 
	{
		super(x_pos, y_pos, width, height);
		
		this.coinCount = 5;
		
		try
		{
			if(coinBlock_image == null)
			{
				coinBlock_image = ImageIO.read(getClass().getResourceAsStream("coinBlock_image.png"));
			}
			
			if(coinBlockDead_image == null)
			{
				coinBlockDead_image = ImageIO.read(getClass().getResourceAsStream("coinBlockDead_image.png"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	CoinBlock(CoinBlock c)
	{
		super(c);
		this.coinCount = c.coinCount;
	}
	@Override
	void Update() 
	{		
			if(this.collidedWithMario) 
			{
				//this.alreadyCollided = true;
				coinCount--;
				if(coinCount < 0)
				{
					this.needCoin = false;
				}
				if(coinCount >= 0)
				{
					this.needCoin = true;
				}
			}
	}

	@Override
	void Draw(Graphics g)
	{
		if(coinCount <= 0)
		{
			g.drawImage(CoinBlock.coinBlockDead_image, this.x - scrollPos, this.y, this.w, this.h, null);
	 		
		}
		else
		{
			g.drawImage(CoinBlock.coinBlock_image, this.x - scrollPos, this.y, this.w, this.h, null);
		}
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
		
        ob.add("coinCount", coinCount);
        //ob.add("alreadyCollided", alreadyCollided);
        ob.add("type", "coinBlock");
		return ob;
	}
	
	CoinBlock(Json ob)
	{
		super(ob);
		
		coinCount = (int)ob.getLong("coinCount");
		//alreadyCollided = ob.getBool("alreadyCollided");
	}

	@Override
	boolean isCoin() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	Sprite Copy() 
	{
		return new CoinBlock(this);
		
	}
}
