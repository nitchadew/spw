package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heal extends Sprite{
	public static final int Y_TO_FADE = 500;
	public static final int Y_TO_DIE = 600;
	
	private int step = 10;
	public boolean alive = true;
	BufferedImage heart;
	
	public Heal(int x, int y, int a, int b) {  // change int to varible for random
		super(x, y, a, b);
		try{
			heart = ImageIO.read(new File("f2/spw/image/heart.png"));
		}
		catch(IOException d){

		}
		
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		//g.setColor(Color.WHITE);
		//g.fillRect(x, y, width, height);
		g.drawImage(heart,x,y,width,height,null);
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}