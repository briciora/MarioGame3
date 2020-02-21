import java.util.ArrayList;

//store mario in model

class Model
{
	Mario mario;
	ArrayList<Sprite> sprites; //array list member

	int d;
	int k;
	
	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>(); //initializing ArrayList
		sprites.add(mario);
		d = 40;
		k = 10;
	}

	Model(Model m)
	{
		this.sprites = new ArrayList<Sprite>();
		d = m.d;
		k = m.k;
		
		for(Sprite s: m.sprites)
		{
			Sprite copy = s.Copy();
			if(s.isMario())
			{
				this.mario = (Mario) copy;
			}
			this.sprites.add(copy);
		}
	}

	public void update()
	{
		for(int i = 0; i < this.sprites.size(); i++)
		{
			this.sprites.get(i).scrollPos = mario.x;
			this.sprites.get(i).Update();
			if(this.sprites.get(i).needCoin)
			{
				addCoin(this.sprites.get(i).x, this.sprites.get(i).y, this.sprites.get(i).w, this.sprites.get(i).h);
				this.sprites.get(i).needCoin = false;
				mario.coinsCollected ++;
			}
		}

		//removes coins from the sprites list 
		for(int i = 0; i < this.sprites.size(); i++)
		{
			if(this.sprites.get(i).y > 700 && this.sprites.get(i).isCoin())
			{
				this.sprites.remove(i);
				i--;
			}
		}
	}

	public void addBrick(int x, int y, int w, int h, boolean groundBrick)
	{
		Brick b = new Brick(x, y, w, h, groundBrick);
		sprites.add(b);
	}
	public void addCoinBlock(int x, int y, int w, int h)
	{
		CoinBlock c = new CoinBlock(x, y, w, h);
		sprites.add(c);
	}
	public void addCoin(int x, int y, int w, int h)
	{
		Coin c = new Coin(x, y, w, h);
		sprites.add(c);
	}

	void unmarshall(Json ob) //want to pass in a Json file (Json ---> Sprite)
	{
		sprites.clear(); //clears the list to make room for new sprites
		Json json_bricks = ob.get("sprites");

		for( int i = 0; i < json_bricks.size(); i++)
		{
			Json j = json_bricks.get(i);
			String type = j.getString("type");

			if(type.equals("brick"))
			{
				Sprite b = new Brick(j);
				sprites.add(b);	
			}

			else if(type.equals("coin"))
			{
				Sprite b = new Coin(j);
				sprites.add(b);	
			}

			else if(type.equals("coinBlock"))
			{
				Sprite b = new CoinBlock(j);
				sprites.add(b);	
			}

			else if(type.equals("mario"))
			{
				mario = new Mario(j, this);
				sprites.add(mario);
			}
		}
	}

	Json marshall()
	{
		Json ob = Json.newObject(); //new json object
		Json json_bricks = Json.newList(); //json list
		ob.add("sprites", json_bricks);
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite b = sprites.get(i);
			Json j = b.marshall();
			json_bricks.add(j);
		}
		return ob;
	}

	void save(String filename)
	{
		Json ob = marshall();
		ob.save(filename);
	}

	void load(String filename)
	{
		unmarshall(Json.load(filename));
	}

	void do_action(Action a)
	{
		switch(a)
		{
			case run: 
				mario.x +=5;
	
				mario.picture++;
	
				if(mario.picture >= 5)
				{
					mario.picture = 0;
				}
				break;
			
			case jump: 
				if(mario.framesInAir < 5)
					{
						mario.vert_vel -= 5.4;
					}
				break;
				
//			case wait: 
//				break;
				
			default: 
				break;

		}
	}
	
	double evaluateAction(Action action, int depth)
	{
		// Evaluate the state
		if(depth >= d)
		{
			return mario.x + 10000 * mario.coinsCollected - mario.numberJumps;
		}
		
		// Simulate the action
		Model copy = new Model(this); // uses the copy constructor
		copy.do_action(action); // like what Controller.update did before
		copy.update(); // advance simulated time

		// Recurse
		if(depth % k != 0)
			return copy.evaluateAction(action, depth + 1);
		else
		{
			double best = copy.evaluateAction(Action.run, depth + 1);
			best = Math.max(best,
					copy.evaluateAction(Action.jump, depth + 1));
//			best = Math.max(best,
//					copy.evaluateAction(Action.wait, depth + 1));
			return best;
		}
	}
}