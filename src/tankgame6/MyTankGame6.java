/**
 * ̹�˴�ս��Ϸ5.0��
 * �����һ������������ޱ�ըЧ��
 * 1.��̹��
 * 2.̹�˿������������ƶ�
 * 3.���Է��ӵ����ӵ����������ͬʱ��5�� �ӵ��ǰ���J������ ��������Ƹ�����
 * 4.���е���̹�˵�����ʧ����ըЧ����
 * ���жϺ���д�����һ��ר���ж��ӵ�ʱ���е���̹�˵ĺ��� ֱ�ӷ�panel���
 * (�ж� ���ú��������� ��run������ÿ��100ms�ж�һ�Σ�)
 * ��ըЧ����1.����ͼƬ2.����bomb�� 3.���е���̹��ʱ��bomb����vector������ 4.���ƣ�
 * ���Ƶ���̹�˲��ص�
 * �޸�  ��������ʧ֮��  ����֮���б���Ч����bug����Ϊû��remove�ң� 
 * 5.���Է�ֹ����̹���ص�
 * 6.��Ϸ���Էֹ�
 *   6.1��һ����ʼ��panel���յ�
 *   6.2��������˸Ч��
 * 7.��Ϸ��ͣ����
 * 7.1��û��ɣ����û������ͣʱ���ӵ��ٶȺ�̹���ٶ���Ϊ�㣬����̹�˷���Ҫ�仯
 * 8.��¼��ҳɼ���
 *  8.1 ���ļ���
 *  8.2 ��дһ����¼�࣬��ɶ���ҵļ�¼
 *  8.3 ���湥�����˶�����̹�˵Ĺ���
 *  8.4.�����Ͼֵ���
 * 9.java���������ļ�
 *  9.1
 */
package tankgame6;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class MyTankGame6 extends JFrame implements ActionListener{
	MyStartPanel msp = null;
    MyPanel mp = null;
  
    //��������Ҫ�Ĳ˵�
    JMenuBar jmb = null;
    //��ʼ��Ϸ
    JMenu jm1 = null;
    JMenuItem jmi1 = null;
    JMenuItem jmi2 = null;
    JMenuItem jmi3 = null;
    JMenuItem jmi4 = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      MyTankGame6 mtk = new MyTankGame6();
	}
//���캯��
	public MyTankGame6() {
		
//	  mp = new MyPanel();
//		
//		//����mp�̣߳��Զ�ˢ����Ļ���ܿ�����ͣ��ǰ�ܵ��ӵ���
//		Thread t = new Thread(mp);
//		t.start();
//		this.add(mp);
//		//ע�����
//		this.addKeyListener(mp);
		
		//�����˵����˵�ѡ��
		jmb = new JMenuBar();
		jm1 = new JMenu("��Ϸ(G)");
		//��ݷ�ʽ
		jm1.setMnemonic('G');
		jmi1 = new JMenuItem("��ʼ����Ϸ(N)");
		jmi1.setMnemonic('N');
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newGame");
		
		jmi2 = new JMenuItem("�˳���Ϸ(E)");
		jmi2.setMnemonic('E');
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		
		jmi3 = new JMenuItem("�����˳�(C)");
		jmi3.setMnemonic('C');
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		
		jmi4 = new JMenuItem("�����Ͼ���Ϸ(S)");		
		jmi4.setMnemonic('S');
		jmi4.addActionListener(this);
		jmi4.setActionCommand("conGame");//continue
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		
		jmb.add(jm1);
		
		msp = new MyStartPanel();
	   Thread t = new Thread(msp);
	    t.start();
	    
	    this.setJMenuBar(jmb);
		this.add(msp);
		this.setSize(600,500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// ���û���ͬ���������ͬ����
		if(e.getActionCommand().equals("newGame")) {		
		  
			mp = new MyPanel("new");				
			//����mp�̣߳��Զ�ˢ����Ļ���ܿ�����ͣ��ǰ�ܵ��ӵ���
			Thread t = new Thread(mp);
			t.start();
			//��ɾ���ɵ����
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ ��ˢ��JFrame��
			this.setVisible(true);
		}
		else if(e.getActionCommand().equals("exit")) {
			//�˳�ϵͳ
			//������ٵ�������������recorder�ࣩ
			Recorder.keepRecording();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("saveExit")) {
			System.out.println("111");
			Recorder rd = new Recorder();
			//Ҫ��record��������ϵ���̹�˵�����
			rd.setEnemyList(mp.enemyList);
			//������ٵ�������������
			rd.keepRecordAndEnemy();
			System.exit(0);
		}else if(e.getActionCommand().equals("conGame")) {
			
			//����̹����mypanel�г�ʼ��
			
			  mp = new MyPanel("continue"); 			
		
				//����mp�̣߳��Զ�ˢ����Ļ���ܿ�����ͣ��ǰ�ܵ��ӵ���
				Thread t = new Thread(mp);
				t.start();
				//��ɾ���ɵ����
				this.remove(msp);
				this.add(mp);
				//ע�����
				this.addKeyListener(mp);
				//��ʾ ��ˢ��JFrame��
				this.setVisible(true);
		}
	}
}

//����һ����ʾ������
class MyStartPanel extends JPanel implements Runnable{
	int times = 0;
	
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 470, 350);
		//��ʾ��Ϣ
		if(times %2 == 0) {
			g.setColor(Color.yellow);
			Font myFont = new Font("����",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage: 1 ", 200, 160);
		}
	}
		

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Music music = new Music("src/111.wav");
		music.start(); 
		while(true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
			times++;
			if(times == 200) {
				times =0;
				break;
			}
			//�ػ�
			this.repaint();
		}
	}
}
//�ҵ���壨JPanel��
class MyPanel extends JPanel  implements KeyListener,Runnable{
	
	//����һ���ҵ�̹��//new�Ĵ������������ڹ��캯����
	Hero hero = null;

	
	//������˵�̹���� enemy��list
	Vector<Enemy>  enemyList = new Vector<Enemy>();
	Vector<Node> nodeList = new Vector<Node>();
	
	// ����ը������ ��Ϊ����ͬʱ���ж���̹�� ���˻��ҵ�̹��
	Vector<Bomb> bombList = new Vector<Bomb>();
	
	int enemySize = 3;//����ʾ3�� �������ڳ����µĵ���̹�ˣ�

	//��������ͼƬ
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	

	
	//���캯��
	public  MyPanel(String flag) {
		
		//�ָ���¼
		Recorder.getRecording();
		hero = new Hero(170,230);
		
		if(flag.equals("new")) {
			for(  int i = 0 ; i  < enemySize; i ++) {
				//�����鲻ͬ ���ϱ����ȴ���һ������̹��
				Enemy enemy = new Enemy((i + 1) * 100 - 30, 15);
				enemy.setColor(1);//��ɫ
				enemy.setDirection(1);//����
				//��myPanel�ĵ���̹�����������õ���̹��
				enemy.setEts(enemyList);
				//��������̹�� 
				Thread t = new Thread(enemy);
				t.start();
			    //���һ���ӵ� �մ�����ʱ����� ��������
				Shot s = new Shot(enemy.x + 10, enemy.y + 30, 1);
				//���������̹��
				enemy.shotList.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				//����
				enemyList.add(enemy);
			}
		}else {
			System.out.println("continue");
			nodeList = new Recorder().getNodeAndEnNum();
			//��ʼ�����˵�̹��
			for( int i = 0 ; i  <  nodeList.size(); i ++) {//Recorder.getEnNum(); i ++) {
				
				Node node = nodeList.get(i);
				//�����鲻ͬ ���ϱ����ȴ���һ������̹��
				Enemy enemy = new Enemy(node.x,node.y);
				enemy.setColor(1);//��ɫ
				enemy.setDirection(node.direction);//����
				//��myPanel�ĵ���̹�����������õ���̹��
				enemy.setEts(enemyList);
				//��������̹�� 
				Thread t = new Thread(enemy);
				t.start();
			    //���һ���ӵ� �մ�����ʱ����� ��������
				Shot s = new Shot(enemy.x + 10, enemy.y + 30, 1);
				//���������̹��
				enemy.shotList.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				//����
				enemyList.add(enemy);
			}
		}
		
		
		//��ʼ��ͼƬ//javax.imageio.*;
		try {
			image1 = ImageIO.read(new File("src/bomb_1.gif"));
			image2 = ImageIO.read(new File("src/bomb_2.gif"));
			image2 = ImageIO.read(new File("src/bomb_3.gif"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("bomb_1.gif"));
//		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("bomb_2.gif"));
//		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("bomb_3.gif"));

	}
	
	//������ʾ��Ϣ
	public void showInfo(Graphics g) {
		  //������ʾ��Ϣ̹��(��̹�˲�����ս��)
				this.drawTank(100, 350, g, 0, 1 );//���ϣ�����̹��
				g.setColor(Color.black);
				g.drawString(Recorder.getEnNum() +"", 140, 370);
				this.drawTank(200, 350, g, 0, 0);
				g.setColor(Color.black);
				g.drawString(Recorder.getMyLife()+"",240,370);
				
				//������ҵ��ܳɼ�
				g.setColor(Color.black);
				Font f = new Font("����",Font.BOLD,20);
				g.setFont(f);
				g.drawString("�����ܳɼ� ", 450, 30);
				this.drawTank(450, 50, g, 0,1);
				g.setColor(Color.black);
				g.drawString(Recorder.getDeadEnNum()+"", 500, 70);
	}
	
	//��дpaint����
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(5, 5, 420, 320);//̹�˻����
		g.setColor(Color.black);
	 
		//����̹����������ʾ��Ϣ
		this.showInfo(g);
		
		//�����Լ���̹��
		if(hero.isLive) {
			this.drawTank(hero.getX(), hero.getY(), g, hero.direction, hero.color );
		}	
			//��shotList��ȡ��ÿ���ӵ�������
			for(int i = 0; i < hero.shotList.size(); i++) {
				Shot myShot = hero.shotList.get(i);
				//�����ӵ�//Ҫ���ӵ��� ��Ҫ��ͣ�޸��ӵ�����
				if(myShot != null && myShot.isLive == true) {
			          g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
				}
				if(myShot.isLive == false) {
					//��������������ӵ�  ����дi ��Ϊ���ڻ���ʱ������������Ǹ�i
					hero.shotList.remove(myShot);
				}
			}
	
		
	
		//����ը����ըЧ��
		for(int i =0 ; i < bombList.size(); i++) {
		//	System.out.print("bombList.size()" + bombList.size());
			//ȡ��ը��
			Bomb b = bombList.get(i);
			
			if(b.life >6) {
				g.drawImage(image1, b.x	,b.y, 30, 30,this);//this:�����panel�ﻭ
			}
			else if(b.life >4) {
				g.drawImage(image2, b.x	,b.y, 30, 30,this);//this:�����panel�ﻭ
			}
			else {
				g.drawImage(image3, b.x	,b.y, 30, 30,this);//this:�����panel�ﻭ
			}
			//��b������ֵ��С
			b.lifeDown();
			//���ը������ֵΪ�� ��remove
			if(b.life == 0) {
				bombList.remove(b);
			}
		}
		
		
		//�о�̹���ü��� ������Ҫ��� Vectorͬ���̰߳�ȫ ����Ϸѡ�������
		//�������˵�̹�� Ҫ���жϵ���̹���Ƿ���Ų��ܻ�
		for(int i = 0 ; i < enemyList.size(); i ++) {//��Ϊ���ܴ��һ����ʣ������ ����Ҫ�����С
		    Enemy enemy = enemyList.get(i);
		    if(enemy.isLive) {
		    	//����ֱ��enemy.getX() û��������� �����vectorList enList��ȡ��һ��enemy��Ա ������enemy�ķ���
				this.drawTank(enemy.getX(), enemy.getY(), g, enemy.direction, enemy.getColor());
				
				//�������˵��ӵ�
				for(int j = 0 ; j< enemy.shotList.size();j ++) {
					//ȡ���ӵ�
					Shot enemyShot = enemy.shotList.get(j);
					if(enemyShot.isLive){
								g.draw3DRect(enemyShot.x,enemyShot.y, 1, 1, false);
					}else{
						  enemy.shotList.remove(enemyShot);
					}
				}
		    }		
		}		
  	}
	
	
	//�ж��ҵ��ӵ��Ƿ���е���
	public void hitEnemy(){
		//�ж��Ƿ���� ����ѭ�� ��֪���ĸ��ӵ���������̹��
		for(int i = 0 ; i < hero.shotList.size() ; i ++) {
			//ȡ���ӵ�
			Shot myShot = hero.shotList.get(i);
			//�ж��ӵ��Ƿ���Ч
			if(myShot.isLive) {
				//ȡ��ÿ������̹��
				for(int j = 0; j < enemyList.size();j++){
					Enemy enemy = enemyList.get(j);//!!��Ҫд�� i
					if(enemy.isLive) {
						if(this.hitTank(myShot, enemy)){
							//��¼������������
							//��¼��̹��������һ
							 Recorder.reduceEnNum();
							 Recorder.addEnRec();
						}
					}
				}
			}				
		}	
	}
	
	//�жϵ����Ƿ������
	public void hitHero(){
		//�ж��ĸ����˻�����
		for (int i = 0 ; i < this.enemyList.size();i++) {
			Enemy enemy = enemyList.get(i);
		//	if(enemy.isLive) {
				//ȡ��ÿ���ӵ�
				for(int j = 0; j <enemy.shotList.size(); j++) {
					Shot enemyShot = enemy.shotList.get(j);
				//	if(enemyShot.isLive) {
					if(hero.isLive) {
						
						if(this.hitTank(enemyShot, hero)) {
							   Recorder.reduceMyLife();
						}
					
					}				
						//}					
				}
			//}
		}
	}
	
	//дһ������ר���ж��ӵ��Ƿ���е���̹��
	public boolean  hitTank(Shot shot,Tank  tank) {
		boolean b = false;//Ĭ��û�л���
		//�жϸ�̹�˵ķ��� ̹���Ǿ���
		switch(tank.direction) {
		case 0:
			 if(shot.x > tank.x && shot.x < tank.x+20
				      &&shot.y> tank.y-5 && shot.y < tank.y + 30) {
					 //����  
				    //�ӵ�����
				 shot.isLive = false;
				   //��������
				 tank.isLive = false;
				 b = true;
				 //������ը ����������(�����hitTank�ͷ��ʲ���bomb��)//ps��ͼ������Ҳ��ͼ�����Ͻ� ��������Ҳ�����Ͻ�
				 Bomb bomb = new Bomb(tank.x, tank.y);
				 bombList.add(bomb);
				 
				 }
			 break;
		case 1:
			 if(shot.x > tank.x && shot.x < tank.x+20
			      &&shot.y> tank.y && shot.y < tank.y + 33) {
				 //����				
				 shot.isLive = false;			 
				 tank.isLive = false;
				 b = true;
				 Bomb bomb = new Bomb(tank.x, tank.y);
				 bombList.add(bomb);
			 }
			 break;
		case 2://��
			 if(shot.x > tank.x -5 && shot.x < tank.x+20
				      &&shot.y> tank.y && shot.y < tank.y + 20) {
					 //����
				 shot.isLive = false;			 
				 tank.isLive = false;
				 b = true;
				 Bomb bomb = new Bomb(tank.x, tank.y);
				 bombList.add(bomb);
				 }
			 break;
		case 3:
			 if(shot.x > tank.x && shot.x < tank.x+33
				      &&shot.y> tank.y && shot.y < tank.y + 20) {
					 //����
				 shot.isLive = false;			 
				 tank.isLive = false;
				 b = true;
				 Bomb bomb = new Bomb(tank.x, tank.y);
				 bombList.add(bomb);
				 }
			 break;	
		}
		  return b;	
	}
	
	//��̹�ˣ���չ��
	public void drawTank(int x,int y, Graphics g,int direction,int type) {
		//�ж����� ��ɫ
		switch(type) {
			case 0 : 
				g.setColor(Color.yellow);  //���ҵ�̹��
			    break;
			case 1 : 
				g.setColor(Color.cyan);//enemy
				break;
			case 2 : 
				g.setColor(Color.red);
				break;
			case 3 : 
				g.setColor(Color.blue);
				break;
		}
		
		//�жϷ���   0����	1����	2����  3����
		switch(direction) {			
			case 0 :{
			       //������ߵľ��Σ�������ӣ�
					g.fill3DRect(x, y, 5, 30,false);
					//���ұߵľ���
					g.fill3DRect(x +15, y, 5 , 30,false);
					//���м����
					g.fill3DRect(x +5,y + 7, 10, 17,false);
					//���м�Բ��
					g.fillOval(x +5, y + 10, 9, 9);
					//����
					g.drawLine(x + 10, y +10, x + 10, y -5);
					break;
			  }
			
			//1����
			case 1 :{
			       //������ߵľ��Σ�������ӣ�
					g.fill3DRect(x, y, 5, 30,false);
					//���ұߵľ���
					g.fill3DRect(x +15, y, 5 , 30,false);
					//���м����
					g.fill3DRect(x +5,y + 7, 10, 17,false);
					//���м�Բ��
					g.fillOval(x +5, y + 10, 9, 9);
					//����
					g.drawLine(x + 10, y +10, x + 10, y + 33);
					break;
			  }
			
	     	//����
			case 2 :{
			       //�����ϱߵľ��Σ�������ӣ�
					g.fill3DRect(x, y, 30, 5,false);
					//���±ߵľ���
					g.fill3DRect(x , y + 15, 30 , 5,false);
					//���м����
					g.fill3DRect(x +7,y + 5, 17, 10,false);
					//���м�Բ��
					g.fillOval(x +10, y + 5, 9, 9);
					//����
					g.drawLine(x + 10, y +10, x -5 , y +10);
					break;
			  }
			
			//����
			case 3 :{
			       //�����ϱߵľ��Σ�������ӣ�
					g.fill3DRect(x, y, 30, 5,false);
					//���±ߵľ���
					g.fill3DRect(x , y + 15, 30 , 5,false);
					//���м����
					g.fill3DRect(x +7,y + 5, 17, 10,false);
					//���м�Բ��
					g.fillOval(x +10, y + 5, 9, 9);
					//����
					g.drawLine(x + 12, y +10, x + 33 , y +10);
					break;
			  }
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}
	//���¼����� w����  s���� a���� d ����(�������ҵ�˳��)
	//��Ҫת����
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_W) {
			//�޸�hero�ķ���  д��tank����
			//�����ҵ�̹��hero�ķ���
			this.hero.setDirection(0);
			this.hero.moveUp();
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {//����getchar
			this.hero.setDirection(1);
			this.hero.moveDown();
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			this.hero.setDirection(2);
			this.hero.moveLeft();
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			this.hero.setDirection(3);
			this.hero.moveRight();
		}
		//�ӵ��ܿ���Ҫ���������Ҽ�ͬʱ��
		 if(e.getKeyCode() == KeyEvent.VK_J) {
			//����
			 System.out.println("this.hero.shotList.size()= " + this.hero.shotList.size());
			//�ͺ�һ�� �������Ҫ���ӵ������������ ��Ȼ��Զֻ�ܴ�5��
			 if(this.hero.shotList.size() < 5) {
				  this.hero.shotEnemy();
			}
	     }
			
		//�����ػ�panel
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//ÿ��100ms�ػ�
		while(true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    this.hitHero();
			this.hitEnemy();
			
			
			//�ػ�	
			this.repaint();
		}
	}
}

