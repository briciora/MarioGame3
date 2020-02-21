import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, KeyListener
{
	Model model; //model object
	boolean keyLeft;
	boolean keyRight;
	boolean spaceBar;

	Controller(Model m) //new constructor 
	{
		model = m;
		
		//add ground
		model.addBrick(0, 596, 200000, 596, true);
		
		//add bricks
		for(int i = 0; i < 100; i++)
		{
			if(i%2 == 0)
			{
				model.addBrick(100+200*i, 540, 100, 56, false);
			}
//			else
//			{
//				model.addBrick(100+600*i, 300, 100, 30, false);
//			}
		}
		
		//add coin blocks
		for(int i = 0; i < 50; i++)
		{
			model.addCoinBlock(300+800*i, 430, 50, 50);
		}
	
	}

	//This used to remove the button after it was pressed 
	public void actionPerformed(ActionEvent e)
	{
	}

	void setView(View v) //lets the caller set the object that "view" references
	{
	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_SPACE: spaceBar = true; break;
			case KeyEvent.VK_S: model.save("map.json");
			System.out.println ("SAVED!"); 
			break;
			case KeyEvent.VK_L: model.load("map.json");
			System.out.println("LOADED!");
			break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_SPACE: spaceBar = false; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

//	void update()
//	{
//		if(keyRight)
//		{
//			model.mario.x +=5;
//			
//			model.mario.picture++;
//			
//			if(model.mario.picture >= 5)
//			{
//				model.mario.picture = 0;
//			}
//		}
//		
//		if(keyLeft)
//		{	
//			model.mario.x -=5; 
//			
//			model.mario.picture--;
//			
//			
//			if(model.mario.picture < 0)
//			{
//				model.mario.picture = 4;
//			}
//			
//			if(model.mario.x < 0) 
//			{
//				model.mario.x = 0;
//			}
//		}
//		
//		if(spaceBar)
//		{
//			
//			if(model.mario.framesInAir < 5)
//			{
//				model.mario.vert_vel -= 5.4;
//			}
//		}
//	}

	void update() {
		// Evaluate each possible action
		double score_run = model.evaluateAction(Action.run, 0);
		double score_jump = model.evaluateAction(Action.jump, 0);
//		double score_jump_and_run =
//			model.evaluateAction(Action.wait, 0);

		// Do the best one
//		if(score_jump_and_run > score_jump &&
//				score_jump_and_run > score_run)
//		{
//			model.do_action(Action.wait);
//			//System.out.println("wait");
//		}
		if(score_run > score_jump)
		{
			model.do_action(Action.run);
			//System.out.println("run");
		}	
		else
		{
			model.do_action(Action.jump);
			//System.out.println("jump");
		}
	}
	
}
