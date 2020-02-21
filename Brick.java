import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Brick extends Sprite 
{	
	boolean groundBrick;
	
	static Image brick_image;

	Brick(int x_pos, int y_pos, int width, int height, boolean groundBrick)
	{
		super(x_pos,y_pos, width, height);
		
		this.groundBrick = groundBrick;
		
		try
		{
			if(brick_image == null)
			{
				brick_image = ImageIO.read(getClass().getResourceAsStream("brick_image.jpg"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	Brick(Brick b)
	{
		super(b);
		this.groundBrick = b.groundBrick;
	}
	
	@Override
	void Update() 
	{
		
	}

	@Override
	void Draw(Graphics g) 
	{
			if(this.groundBrick == true)
			{
				g.setColor(Color.green);
				g.fillRect(this.x, this.y, this.w, this.h);
			}
			else
			{
				g.drawImage(Brick.brick_image, this.x - scrollPos, this.y, this.w, this.h, null);
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
	        ob.add("groundBrick", groundBrick);
	        ob.add("type", "brick");
	        return ob;
	}
	
	Brick(Json ob)
	{
		super(ob);
		
		groundBrick = ob.getBool("groundBrick");
		
	}


	@Override
	boolean isCoin() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	Sprite Copy() {
		// TODO Auto-generated method stub
		return new Brick(this);
	}
}