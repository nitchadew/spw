package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private SpaceShip v;	
	
	private Timer timer;
	private long damagetime = 50;
	private long score = 0;
	private double difficulty = 0.1;			// bullet fequency
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(150, new ActionListener() {   //speed
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}


	


	/*private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30, (int)(Math.random()*100),10); //random size w
		gp.sprites.add(e);
		enemies.add(e);
	}


	private void generateEnemytwo(){
		Enemy e = new Enemy((int)(Math.random()*390), 30, 10,(int)(Math.random()*200)); //random size h
		gp.sprites.add(e);
		enemies.add(e);

	}*/

	
	private void generateEnemy(){
		Enemy e = new Enemy(80, 10, 10, 10); 
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateEnemytwo(){
		Enemy e = new Enemy(280, 10, 10, 10); 
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateEnemythree(){
		Enemy e = new Enemy(180, 10, 10, 10); 
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateEnemybig(){
		Enemy e = new Enemy(180, 10, 10, 250); 
		gp.sprites.add(e);
		enemies.add(e);
	}
	


	private void lift(){
		Enemy e = new Enemy(-120, 10, 160 ,30); //lift way
		gp.sprites.add(e);
		enemies.add(e);

	}

	private void rigth(){
		Enemy e = new Enemy(345, 10, 480,30); //rigth way
		gp.sprites.add(e);
		enemies.add(e);

	}




	
	private void process(){
		rigth();						//call
		lift();
		if(Math.random() < 0.04){
			if(Math.random() < 0.3)
				generateEnemybig();	
			generateEnemy();				//call random
		}
	
		if(Math.random() < 0.04)
			generateEnemythree();
		else if(Math.random() < 0.04)
			generateEnemytwo();
		
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 1; 		// add socre 
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				damagetime = damagetime-1;
				if(damagetime == 0){	//number of damage
					die();
					return;
				}
			}
		}
	}
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1,0);
			break;
		case KeyEvent.VK_RIGHT:     //input from keybord U D R L
			v.move(1,0);
			break;
		case KeyEvent.VK_DOWN:
			v.move(0,1);
			break;
		case KeyEvent.VK_UP:
			v.move(0,-1);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		}
	}

	public long getScore(){
		return score;
	}


	public long getdamagetime(){
		return damagetime;
	}



	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
	
}
