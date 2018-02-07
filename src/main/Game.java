package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import ai_methods.AStar;
import assets.Block;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
	
	private Thread gameThread;
	private Random rand;
	private volatile boolean run;
	private Mouse mouse;
	
	private ArrayList<Block>blocks;
	private ArrayList<Block>path;
	private ArrayList<Block>chaserpath;
	private Block blocky;
	private Block chaser;
	private int blockyid = 21;
	private int chaserid = 378;
	private AStar astar;
	
	private boolean bNewPath;
	private boolean bPlayerMove;
	private boolean bSelectDest = true;
	private boolean bPlaceOb = false;
	
	private int[] colours = 
		{
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1		
		};  
	
	
	
//================================================================//
//							I N I T							      //
//================================================================//	
	
	public Game(int gameWidth, int gameHeight) {
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.WHITE);
		setFocusable(true);
		requestFocus();
		mouse = new Mouse();
		addMouseListener(mouse);
		
		blocks = new ArrayList<Block>();
		path = new ArrayList<Block>();
		chaserpath = new ArrayList<Block>();
		astar = new AStar();
		
		
		for(int x = 0; x < 800; x += 40) {
			for(int y = 0; y < 800; y += 40) {			
					Block block = new Block(x,y);
					blocks.add(block);
			}
		}
		
		for(int i = 0; i < blocks.size(); i++) {
			blocks.get(i).setId(i);
		}
		for(int i = 0; i < blocks.size(); i++) {
			blocks.get(i).setColor(colours[i]);
		}
		
		
		blocky = new Block(blocks.get(blockyid).getX(), blocks.get(blockyid).getY());
		blocky.setColor(3);
		chaser = new Block(blocks.get(chaserid).getX(), blocks.get(chaserid).getY());
		chaser.setColor(4);
		
		start();
		

			
	}
				
	public void start() {
		run = true;
		gameThread = new Thread(this, "Loop");
		gameThread.start();
	}

//================================================================//
//							U P D A T E							  //
//================================================================//		
	
	public void update()
	{
		if(bNewPath == true) {
			for(int i = 0; i < path.size(); i++) {
				blockyid = path.get(i).getId();
				blocky.setX(blocks.get(blockyid).getX());
				blocky.setY(blocks.get(blockyid).getY());
				
				//update();
				repaint();
				//pauses 100 milliseconds to show animation
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i = 0; i < path.size(); i++) {
				path.get(i).setColor(0);
			}
			bNewPath = false;

		}
	}
	
	@Override
	public void run() {
		while(run)
		{
			update();
			repaint();
			try {
				Thread.sleep(14); //14
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
//================================================================//
// 							D R A W								  //
//================================================================//	
	public void paint(Graphics g)
	{
		
		g.clearRect(0,0, 800, 800);
		
		for(int i = 0; i < blocks.size(); i++) {
			blocks.get(i).Draw(g);
		}

		g.setColor(Color.BLACK);
		g.fillRect(800, 0, 200, 800);
		g.setColor(Color.WHITE);
		g.drawString("PLACE OBSTACLES", 810, 40);
		g.drawString("SELECT DESTINATION", 810, 80);
		if(bSelectDest == true) {
			g.setColor(Color.RED);
			g.drawRect(800, 50, 200, 40);
		}
		else if(bPlaceOb == true) {
			g.setColor(Color.RED);
			g.drawRect(800, 0, 200, 40);
		}

		
		blocky.Draw(g);
		chaser.Draw(g);
	}
	

	
//=============================================================//
//	INPUT								   //
//=============================================================//
private class Mouse implements MouseListener {	
	
		@Override
		public void mousePressed(MouseEvent e) {
		// Do Nothing
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		
			
			
			
			if(bNewPath == false && bSelectDest == true) {
				for(int i = 0; i < blocks.size(); i++)
				{
					if(e.getX() > blocks.get(i).getX() &&
							e.getX() < blocks.get(i).getX() + 40)
					{
						if(e.getY() > blocks.get(i).getY() &&
								e.getY() < blocks.get(i).getY() + 40)
						{
							if(blocks.get(i).getColor() != Color.BLUE) {
								path = astar.pathfinding(blocks, blocks.get(blockyid),blocks.get(i));
								for(int j = 0; j < path.size(); j++) {
									path.get(j).setColor(2);
								}
								bNewPath = true;
							}
							
						}
					}
				}
			}
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		}
	
	}
	
}
