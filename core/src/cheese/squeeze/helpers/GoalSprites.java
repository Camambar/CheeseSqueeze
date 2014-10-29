package cheese.squeeze.helpers;


import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GoalSprites extends Sprite{
	
	HashMap<Integer,Sprite> goals;
	
	public GoalSprites(HashMap<Integer,Sprite> goals) {
		goals = goals;
	}
	
	public GoalSprites() {
		goals = new HashMap<Integer,Sprite>();
	}

	public Sprite getGoal(int nbCheese) {
		return goals.get(nbCheese);
	}
	
	public void addSprite(int nb, Sprite s) {
		goals.put(nb, s);
	}
	

}
