package ai_methods;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import main.Main;
import assets.Block;

public class AStar {
	
		private ArrayList<Block> openList;
		private ArrayList<Block> closedList;
		private ArrayList<Block> successors;
		private Block startblock;
		private Block endblock;
		
		private boolean pathFound;
	
		public AStar() {
		
			openList = new ArrayList<Block>();
			closedList = new ArrayList<Block>();
			successors = new ArrayList<Block>();
			
		
		}
	
	public ArrayList<Block> pathfinding(ArrayList<Block> blocks, Block start, Block end)
	{
		//initialises lists
		openList.clear();
		closedList.clear();
		successors.clear();
		
		pathFound = false;
		
		this.startblock = start;
		this.endblock = end;
		//sets start to current block
		Block current = startblock;
		current.setParent(null);
		openList.add(startblock);
			
		do
		{
			
			int lowestScore = openList.get(0).getScore();
			for(int i = 1; i < openList.size(); i++)
			{
				if(openList.get(i).getScore() < lowestScore || openList.get(i).getScore() == lowestScore)
				{
					lowestScore = openList.get(i).getScore();
					current = openList.get(i);
				}
			}
			openList.remove(current);
			closedList.add(current);
			
			successors.clear();
			
			if(current == end)
			{
				return CalculatePath(end);
			}
			else
			{
				FindAdjacentBlocks(blocks, current);
				
				for(int i = 0; i < successors.size(); i++)
				{
					if(!closedList.contains(successors.get(i)))
					{
						if(!openList.contains(successors.get(i)))
						{
							successors.get(i).setParent(current);
							successors.get(i).calcMovementCost(current);
							successors.get(i).calcHeuristic(end);
							openList.add(successors.get(i));
						}
						else
						{
							if(successors.get(i).getCost() < current.getCost())
							{
								successors.get(i).calcMovementCost(current);
								current = successors.get(i);
							}
						}
					}
					
				}
			}
			
			
		}
		while(!openList.isEmpty());
		
		return null;
	}
	
	private void FindAdjacentBlocks(ArrayList<Block> blocks, Block current)
	{
		
		
		Block S1 = blocks.get(current.getId() - 20);
		Block S2 = blocks.get(current.getId() -  1);
		Block S3 = blocks.get(current.getId() +  1);
		Block S4 = blocks.get(current.getId() + 20);	
		
		//add successors to arraylist
		if(S1.getColor() != Color.BLUE)
			successors.add(S1);
		if(S2.getColor() != Color.BLUE)
			successors.add(S2);
		if(S3.getColor() != Color.BLUE)
			successors.add(S3);
		if(S4.getColor() != Color.BLUE)
			successors.add(S4);
			
		
	}
	
	private ArrayList<Block> CalculatePath(Block end)
	{
		ArrayList<Block> path = new ArrayList<Block>();
		Block current = end;
		while(current.getParent() != null) {
			path.add(current);
			current = current.getParent();
		}
		
		Collections.reverse(path);
		return path;
	}


}
