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
	private ArrayList<Bonus> bonusscore = new ArrayList<Bonus>();
	private ArrayList<Heal> healit = new ArrayList<Heal>();

		
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



	
	private void generateEnemy(int Y){
		Enemy e = new Enemy(Y, 10, 10, 10); 
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

	private void Bonusdrop(int Y){
		Bonus b = new Bonus(Y, 10, 20, 20); 
		gp.sprites.add(b);
		bonusscore.add(b);
	}

	private void Healdrop(int Y){
		Heal h = new Heal(Y, 10, 20, 20); 
		gp.sprites.add(h);
		healit.add(h);
	}
	

	
	
	private void process(){
		rigth();						//call
		lift();
		if(Math.random() < 0.07){
			if(Math.random() < 0.6)
				generateEnemy(180);	
			else if(Math.random() < 0.3)
				Bonusdrop(175);
			else if(Math.random() < 0.12)
				Healdrop(175);	
			if(Math.random() < 0.05)
				generateEnemybig();		//call random
		}
	
		if(Math.random() < 0.07){
			if(Math.random() < 0.6)
				generateEnemy(80);
			else if(Math.random() < 0.3)
				Bonusdrop(75);
			else if(Math.random() < 0.12)
				Healdrop(75);	
		}
		else{
			if(Math.random() < 0.06)
				generateEnemy(280);
			else if(Math.random() < 0.005)
				Bonusdrop(275);
			else if(Math.random() < 0.0012)
				Healdrop(275);	
			
		}
		
		
		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Bonus> b_iter = bonusscore.iterator();
		Iterator<Heal> h_iter = healit.iterator();
		
		
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			

			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);

			}
		
		}

		while(b_iter.hasNext()){
			Bonus b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
		
			}
		}

		while(h_iter.hasNext()){
			Heal h = h_iter.next();
			h.proceed();
			
			if(!h.isAlive()){
				h_iter.remove();
				gp.sprites.remove(h);
			}

		}
			

		gp.updateGameUI(this);
		Rectangle2D.Double vr = v.getRectangle();  //v is space ship
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		Rectangle2D.Double hr;
	
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				damagetime = damagetime - 1;
				if(damagetime == 0){
					die();
					return;
				}
			}
		}

		for(Bonus b : bonusscore){
			br = b.getRectangle();
			if(br.intersects(vr)){
				score += 1000;
				b.alive = false;
			}
		}

		for(Heal h : healit){
			hr = h.getRectangle();
			if(hr.intersects(vr)){
				damagetime += 1;
				h.alive = false;
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
