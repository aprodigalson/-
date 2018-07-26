package guyueyu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements KeyListener,ActionListener{
	
	ImageIcon head = new ImageIcon("res/head.png");
	ImageIcon food = new ImageIcon("res/food.png");
	ImageIcon body = new ImageIcon("res/body.png");
	ImageIcon background = new ImageIcon("res/sky.jpg");
	ImageIcon brick = new ImageIcon("res/brick.png");
	int[] snakex = new int[100];
	int[] snakey = new int[100];
	int len = 3;
	String direction = "R";
	
	Random random = new Random();
	int foodx = random.nextInt(34)*25 + 25;
	int foody = random.nextInt(24)*25 + 75;
	
	boolean isStart = false;
	
	boolean isFail = false;
	
	int speed = 200;
	Timer timer = new Timer(speed, this);
	
	int score = 0;
	
	public SnakePanel(){
		this.setFocusable(true);
		initSnake();
		this.addKeyListener(this);
		timer.start();
	}
	
	public void initSnake(){
		isFail = false;
		isStart = false;
		len = 3;
		direction = "R";
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
	}
	
	public void paint(Graphics graphics){
		
		this.setBackground(Color.BLACK);
		
		background.paintIcon(this, graphics, 0, 0);
		//»­×©¿é
		for(int i=0; i<34;i++){
			brick.paintIcon(this, graphics, i*25+25, 50);
			brick.paintIcon(this, graphics, i*25+25, 675);
		}
		for(int i=0 ; i<24;i++){
			brick.paintIcon(this, graphics, 0, i*25+75);
			brick.paintIcon(this, graphics, 875, i*25+75);
		}
		//»­ÉßÍ·
		head.paintIcon(this, graphics, snakex[0], snakey[0]);
		//»­ÉßÉí
		for(int i=1; i<len; i++){
			body.paintIcon(this, graphics, snakex[i], snakey[i]);
		}
		//»­¿ªÊ¼ÌáÊ¾Óï
		if(!isStart){
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("arial", Font.BOLD, 30));
			graphics.drawString("Press Space To Start / Stop", 300, 300);
		}
		//»­ÓÎÏ·½áÊøÓï
		if(isFail){
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("arial", Font.BOLD, 30));
			graphics.drawString("Game Over Press Space To Start / Stop", 200, 300);
		}
		food.paintIcon(this, graphics, foodx, foody);
		
		graphics.setColor(Color.white);
		graphics.setFont(new Font("arial",Font.BOLD, 15));
		graphics.drawString("Score  :\t"+score, 750, 30);
		graphics.drawString("Length :\t"+len, 750, 50);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_SPACE){
			if(isFail){
				initSnake();
			}else{
				isStart = !isStart;
			}
		}else if (keyCode == KeyEvent.VK_UP && !direction.equals("D")) {
			direction = "U";
		}else if(keyCode == KeyEvent.VK_DOWN&& !direction.equals("U")){
			direction = "D";
		}else if(keyCode == KeyEvent.VK_RIGHT&& !direction.equals("L")){
			direction = "R";
		}else if(keyCode == KeyEvent.VK_LEFT&& !direction.equals("R")){
			direction = "L"; 
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		
		if(isStart && !isFail){
			for(int i=len;i>0;i--){
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if(direction.equals("R")){
				snakex[0] += 25;
				if(snakex[0]>850){
					isFail = true;
				}
			}else if (direction.equals("L")) {
				snakex[0] -= 25;
				if(snakex[0]<25){
					isFail = true;
				}
			}else if (direction.equals("U")) {
				snakey[0] -= 25;
				if(snakey[0]<75){
					isFail = true;
				}
			}else if (direction.equals("D")) {
				snakey[0] += 25;
				if(snakey[0] > 650){
					isFail = true;
				}
			}	
			if(snakex[0] == foodx && snakey[0] == foody){
				len++;
				if(speed>100){
					speed-= 10;
				}else if(speed>50){
					speed-= 5;
				}else if(speed>10){
					speed-= 1;
				}
				timer.setDelay(speed);
				score++;
				foodx = random.nextInt(34)*25 + 25;
				foody = random.nextInt(24)*25 + 75;
			}
			for(int i=1; i<len; i++){
				if(snakex[i]==snakex[0] && snakey[i]==snakey[0]){
					isFail = true;
				}
			}
		}
		
		repaint();
	}
	
}
