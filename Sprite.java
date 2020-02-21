import java.awt.Graphics;

public abstract class Sprite 
{
	int x;
	int y;
	int w;
	int h;
	boolean collidedWithMario;
	int scrollPos;
	boolean needCoin;
	
	Sprite(int x_pos, int y_pos, int width, int height)
	{
		x = x_pos;
		y = y_pos;
		w = width;
		h = height;
		collidedWithMario = false;
		scrollPos = 0;
		needCoin = false;
	}
	

	Sprite(Sprite s)
	{
		this.x = s.x;
		this.y = s.y;
		this.w = s.w;
		this.h = s.h;
		this.collidedWithMario = s.collidedWithMario;
		this.scrollPos = s.scrollPos;
		this.needCoin = s.needCoin;
	}
	
	abstract Sprite Copy();
	
	abstract void Update();	
		
	abstract void Draw(Graphics g);
	
	abstract boolean isMario();
	
	abstract Json marshall();
	
	abstract boolean isCoin();
	
	Sprite(Json ob)
	{
		x = (int)ob.getLong("x"); 
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		collidedWithMario = ob.getBool("collidedWithMario");
		scrollPos = (int)ob.getLong("scrollPos");
		needCoin = ob.getBool("needCoin");
	
	}
}

