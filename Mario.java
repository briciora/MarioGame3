import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

class Mario extends Sprite
{		
	int prev_x;
	int prev_y;
	int framesInAir; 
	double vert_vel; //gravity
	int picture; 
	Model m;
	int numberJumps;
	boolean inAir;
	int coinsCollected;
	
	
	static Image[] mario_images = new Image[5];

	Mario(Model m)
	{
		super(0, 500, 60, 95);
	
		prev_x = this.x;
		prev_y =this.y;
		
		this.m = m;
		
		framesInAir = 0; 
		
		coinsCollected = 0;
		
		try
		{
			for(int i = 0; i < mario_images.length; i++)
			{
				if(mario_images[i] == null)
				{
					mario_images[i] = ImageIO.read(getClass().getResourceAsStream("mario" + (i + 1) + ".png"));
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	Mario(Mario mar)
	{
		super(mar);
		this.prev_x = mar.prev_x;
		this.prev_y = mar.prev_y;
		this.framesInAir = mar.framesInAir; 
		this.vert_vel = mar.vert_vel; //gravity
		this.picture = mar.picture; 
		this.m = mar.m;
		this.numberJumps = mar.numberJumps;
		this.coinsCollected = mar.coinsCollected;
	}
	
	boolean doesCollide(Sprite b)
	{
		b.collidedWithMario = false;
		
		if(b.isMario())
		{
			return false;
		}
		
		//lengths of all sides of the brick
		int brickRight = b.x+b.w;
		int brickLeft = b.x;
		int brickTop = b.y;
		int brickBottom = b.y+b.h;
		
		//lengths of all sides of the mario image
		int marioRight = this.x+this.w;
		int marioLeft = this.x;
		int marioTop = this.y;
		int marioBottom = this.y+this.h;
		
		//place to store the previous position if there is a collision (this is how to correct it)
		int prevRight = prev_x + this.w;
		int prevLeft = prev_x;
		int prevTop = prev_y;
		int prevBottom = prev_y + this.h;
				
		if(marioRight <= brickLeft)
			return false;
		if(marioLeft >= brickRight)
			return false;
		if(marioBottom <= brickTop) // assumes bigger is downward
			return false;
		if(marioTop >= brickBottom) // assumes bigger is downward
			return false;
		
		//hit right side of brick
		if(prevLeft >= brickRight && marioLeft < brickRight)
		{
			this.x = brickRight;

		}
		
		//hit left side of brick
		if(prevRight <= brickLeft && marioRight > brickLeft)
		{
			this.x = brickLeft - this.w;
		}
		

		//hit top of brick or on ground
		if(prevBottom <= brickTop && marioBottom > brickTop)
		{
			vert_vel = 0;
			this.y = brickTop-this.h;
			
			framesInAir = 0;
		}
		
		//hit bottom of brick
		if(prevTop >= brickBottom && marioTop < brickBottom)
		{
			vert_vel = 4;
			this.y = brickBottom;
			
			b.collidedWithMario = true;
		}
		
		return true;
	}
	
	@Override 
	void Update()
	{
		if(framesInAir > 0 && vert_vel < 0)
		{
			if(!inAir)   
			{
				numberJumps++;
				//System.out.println(numberJumps);
				inAir = true;
			}
		}
		else
		{
			inAir = false;
		}
	
		framesInAir++;
		
		vert_vel += 1.2;
		y += vert_vel;
		
		for(int i = 0; i < m.sprites.size(); i++)
		{ 			
			Sprite b = m.sprites.get(i); //get brick i out of model 			
			doesCollide(b);
		}
		
		prev_x = this.x;
		prev_y = this.y;
		
	}

	@Override
	void Draw(Graphics g) 
	{
		//draw mario
		g.drawImage(Mario.mario_images[m.mario.picture], 0, m.mario.y, null);
	}

	@Override
	boolean isMario() 
	{
		return true;
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
		
        ob.add("prev_x", prev_x);
        ob.add("prev_y", prev_y);
        ob.add("framesInAir", framesInAir);
        ob.add("inAir", inAir);
        ob.add("picture", picture);
        ob.add("type", "mario");
		return ob;
	}
	
	Mario(Json ob, Model m)
	{
		super(ob);
		
		prev_x = (int)ob.getLong("prev_x");
		prev_y = (int)ob.getLong("prev_y");
		framesInAir = (int)ob.getLong("framesInAir");
		picture = (int)ob.getLong("picture");
		
		this.m = m;
	}

	@Override
	boolean isCoin() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	Sprite Copy() {
		// TODO Auto-generated method stub
		return new Mario(this);
	}
}