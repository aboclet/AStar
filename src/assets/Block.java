package assets;

import java.awt.Color;
import java.awt.Graphics;

public class Block {
	
	int x;
	int y;
	int id;
	Color color;
	
	private Block parent;
	private int cost;
	private int heuristic;	
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setColor(int colour) {
		if(colour == 0) {
			this.color = Color.BLACK;
		}
		else if(colour == 1) {
			this.color = Color.BLUE;
		}
		else if(colour == 2) {
			this.color = Color.CYAN;
		}
		else if(colour == 3) {
			this.color = Color.GREEN;
		}
		else if(colour == 4) {
			this.color = Color.RED;
		}
		else if(colour == 5) {
			this.color = Color.PINK;
		}
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void calcMovementCost(Block start)
	{
		
		if(this == start)
		{
			cost = 0;
		}
		else
		{
			cost = parent.getCost() + 1;
		}
		
	}
	
	public void calcHeuristic(Block end)
	{
		int xdist = Math.abs(this.getX() - end.getX());
		int ydist = Math.abs(this.getY() - end.getY());
		
		
		
		//heuristic (guess from block to goal)
		heuristic = (xdist + ydist) / 40;
	}

	public int getCost() {
		return cost;
	}
	
	public int getHeuristic() {
		return heuristic;
	}
	
	public int getScore() {
		return cost + heuristic;
	}
	
	
	public void Draw(Graphics g) {
		
		g.setColor(color);
		g.fillRect(x, y, 40, 40);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, 40, 40);
		
	}
	
	public Block getParent() {
		return this.parent;
	}

	public void setParent(Block parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return this.color;
	}

	
	

}
