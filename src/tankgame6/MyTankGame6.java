/**
 * 坦克大战游戏5.0版
 * 解决第一次设计设计射击无爆炸效果
 * 1.画坦克
 * 2.坦克可以上下左右移动
 * 3.可以发子弹，子弹连发（最多同时发5颗 子弹是按下J创建的 从那里控制个数）
 * 4.打中敌人坦克敌人消失（爆炸效果）
 * （判断函数写在哪里（一个专门判断子弹时候集中敌人坦克的函数 直接放panel里））
 * (判断 调用函数在哪里 在run函数（每隔100ms判断一次）)
 * 爆炸效果（1.三张图片2.定义bomb类 3.击中敌人坦克时把bomb放在vector向量内 4.绘制）
 * 控制敌人坦克不重叠
 * 修改  击中我消失之后  那里之后还有爆照效果的bug（因为没有remove我） 
 * 5.可以防止敌人坦克重叠
 * 6.游戏可以分关
 *   6.1做一个开始的panel，空的
 *   6.2字体做闪烁效果
 * 7.游戏暂停继续
 * 7.1（没完成）当用户点击暂停时，子弹速度和坦克速度设为零，并让坦克方向不要变化
 * 8.记录玩家成绩、
 *  8.1 用文件流
 *  8.2 单写一个记录类，完成对玩家的记录
 *  8.3 保存攻击毁了多少辆坦克的功能
 *  8.4.继续上局的玩
 * 9.java操作声音文件
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
  
    //做出我需要的菜单
    JMenuBar jmb = null;
    //开始游戏
    JMenu jm1 = null;
    JMenuItem jmi1 = null;
    JMenuItem jmi2 = null;
    JMenuItem jmi3 = null;
    JMenuItem jmi4 = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      MyTankGame6 mtk = new MyTankGame6();
	}
//构造函数
	public MyTankGame6() {
		
//	  mp = new MyPanel();
//		
//		//启动mp线程（自动刷新屏幕才能看到不停往前跑的子弹）
//		Thread t = new Thread(mp);
//		t.start();
//		this.add(mp);
//		//注册监听
//		this.addKeyListener(mp);
		
		//创建菜单及菜单选项
		jmb = new JMenuBar();
		jm1 = new JMenu("游戏(G)");
		//快捷方式
		jm1.setMnemonic('G');
		jmi1 = new JMenuItem("开始新游戏(N)");
		jmi1.setMnemonic('N');
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newGame");
		
		jmi2 = new JMenuItem("退出游戏(E)");
		jmi2.setMnemonic('E');
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		
		jmi3 = new JMenuItem("存盘退出(C)");
		jmi3.setMnemonic('C');
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		
		jmi4 = new JMenuItem("继续上局游戏(S)");		
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
		// 对用户不同点击做出不同处理
		if(e.getActionCommand().equals("newGame")) {		
		  
			mp = new MyPanel("new");				
			//启动mp线程（自动刷新屏幕才能看到不停往前跑的子弹）
			Thread t = new Thread(mp);
			t.start();
			//先删除旧的面板
			this.remove(msp);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示 （刷新JFrame）
			this.setVisible(true);
		}
		else if(e.getActionCommand().equals("exit")) {
			//退出系统
			//保存击毁敌人数量（放入recorder类）
			Recorder.keepRecording();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("saveExit")) {
			System.out.println("111");
			Recorder rd = new Recorder();
			//要给record传入面板上敌人坦克的向量
			rd.setEnemyList(mp.enemyList);
			//保存击毁敌人数量和坐标
			rd.keepRecordAndEnemy();
			System.exit(0);
		}else if(e.getActionCommand().equals("conGame")) {
			
			//敌人坦克在mypanel中初始化
			
			  mp = new MyPanel("continue"); 			
		
				//启动mp线程（自动刷新屏幕才能看到不停往前跑的子弹）
				Thread t = new Thread(mp);
				t.start();
				//先删除旧的面板
				this.remove(msp);
				this.add(mp);
				//注册监听
				this.addKeyListener(mp);
				//显示 （刷新JFrame）
				this.setVisible(true);
		}
	}
}

//就是一个提示的作用
class MyStartPanel extends JPanel implements Runnable{
	int times = 0;
	
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 470, 350);
		//提示信息
		if(times %2 == 0) {
			g.setColor(Color.yellow);
			Font myFont = new Font("黑体",Font.BOLD,30);
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
			//重画
			this.repaint();
		}
	}
}
//我的面板（JPanel）
class MyPanel extends JPanel  implements KeyListener,Runnable{
	
	//定义一个我的坦克//new的创建工作都放在构造函数里
	Hero hero = null;

	
	//定义敌人的坦克组 enemy的list
	Vector<Enemy>  enemyList = new Vector<Enemy>();
	Vector<Node> nodeList = new Vector<Node>();
	
	// 定义炸弹向量 因为可以同时集中多辆坦克 敌人或我的坦克
	Vector<Bomb> bombList = new Vector<Bomb>();
	
	int enemySize = 3;//（显示3个 打死后在出现新的敌人坦克）

	//定义三张图片
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	

	
	//构造函数
	public  MyPanel(String flag) {
		
		//恢复记录
		Recorder.getRecording();
		hero = new Hero(170,230);
		
		if(flag.equals("new")) {
			for(  int i = 0 ; i  < enemySize; i ++) {
				//和数组不同 集合必须先创建一辆敌人坦克
				Enemy enemy = new Enemy((i + 1) * 100 - 30, 15);
				enemy.setColor(1);//青色
				enemy.setDirection(1);//向下
				//将myPanel的敌人坦克向量交给该敌人坦克
				enemy.setEts(enemyList);
				//启动敌人坦克 
				Thread t = new Thread(enemy);
				t.start();
			    //添加一颗子弹 刚创建的时候敌人 方向向下
				Shot s = new Shot(enemy.x + 10, enemy.y + 30, 1);
				//加入给敌人坦克
				enemy.shotList.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				//加入
				enemyList.add(enemy);
			}
		}else {
			System.out.println("continue");
			nodeList = new Recorder().getNodeAndEnNum();
			//初始化敌人的坦克
			for( int i = 0 ; i  <  nodeList.size(); i ++) {//Recorder.getEnNum(); i ++) {
				
				Node node = nodeList.get(i);
				//和数组不同 集合必须先创建一辆敌人坦克
				Enemy enemy = new Enemy(node.x,node.y);
				enemy.setColor(1);//青色
				enemy.setDirection(node.direction);//向下
				//将myPanel的敌人坦克向量交给该敌人坦克
				enemy.setEts(enemyList);
				//启动敌人坦克 
				Thread t = new Thread(enemy);
				t.start();
			    //添加一颗子弹 刚创建的时候敌人 方向向下
				Shot s = new Shot(enemy.x + 10, enemy.y + 30, 1);
				//加入给敌人坦克
				enemy.shotList.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				//加入
				enemyList.add(enemy);
			}
		}
		
		
		//初始化图片//javax.imageio.*;
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
	
	//画出提示信息
	public void showInfo(Graphics g) {
		  //画出提示信息坦克(该坦克不参与战斗)
				this.drawTank(100, 350, g, 0, 1 );//朝上，敌人坦克
				g.setColor(Color.black);
				g.drawString(Recorder.getEnNum() +"", 140, 370);
				this.drawTank(200, 350, g, 0, 0);
				g.setColor(Color.black);
				g.drawString(Recorder.getMyLife()+"",240,370);
				
				//画出玩家的总成绩
				g.setColor(Color.black);
				Font f = new Font("宋体",Font.BOLD,20);
				g.setFont(f);
				g.drawString("您的总成绩 ", 450, 30);
				this.drawTank(450, 50, g, 0,1);
				g.setColor(Color.black);
				g.drawString(Recorder.getDeadEnNum()+"", 500, 70);
	}
	
	//重写paint函数
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(5, 5, 420, 320);//坦克活动区域
		g.setColor(Color.black);
	 
		//画出坦克数量的提示信息
		this.showInfo(g);
		
		//画出自己的坦克
		if(hero.isLive) {
			this.drawTank(hero.getX(), hero.getY(), g, hero.direction, hero.color );
		}	
			//从shotList中取出每颗子弹并画出
			for(int i = 0; i < hero.shotList.size(); i++) {
				Shot myShot = hero.shotList.get(i);
				//画出子弹//要让子弹跑 就要不停修改子弹坐标
				if(myShot != null && myShot.isLive == true) {
			          g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
				}
				if(myShot.isLive == false) {
					//从向量中清除该子弹  不能写i 因为正在画的时候肯能正在用那个i
					hero.shotList.remove(myShot);
				}
			}
	
		
	
		//画出炸弹爆炸效果
		for(int i =0 ; i < bombList.size(); i++) {
		//	System.out.print("bombList.size()" + bombList.size());
			//取出炸弹
			Bomb b = bombList.get(i);
			
			if(b.life >6) {
				g.drawImage(image1, b.x	,b.y, 30, 30,this);//this:在这个panel里画
			}
			else if(b.life >4) {
				g.drawImage(image2, b.x	,b.y, 30, 30,this);//this:在这个panel里画
			}
			else {
				g.drawImage(image3, b.x	,b.y, 30, 30,this);//this:在这个panel里画
			}
			//让b的生命值减小
			b.lifeDown();
			//如果炸弹生命值为零 就remove
			if(b.life == 0) {
				bombList.remove(b);
			}
		}
		
		
		//敌军坦克用集合 死后需要添加 Vector同步线程安全 本游戏选这个集合
		//画出敌人的坦克 要先判断敌人坦克是否活着才能画
		for(int i = 0 ; i < enemyList.size(); i ++) {//因为可能打掉一辆就剩两辆了 所以要算出大小
		    Enemy enemy = enemyList.get(i);
		    if(enemy.isLive) {
		    	//不能直接enemy.getX() 没有这个方法 必须从vectorList enList中取出一个enemy成员 才能用enemy的方法
				this.drawTank(enemy.getX(), enemy.getY(), g, enemy.direction, enemy.getColor());
				
				//画出敌人的子弹
				for(int j = 0 ; j< enemy.shotList.size();j ++) {
					//取出子弹
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
	
	
	//判断我的子弹是否击中敌人
	public void hitEnemy(){
		//判断是否击中 两重循环 不知道哪个子弹打中哪辆坦克
		for(int i = 0 ; i < hero.shotList.size() ; i ++) {
			//取出子弹
			Shot myShot = hero.shotList.get(i);
			//判断子弹是否有效
			if(myShot.isLive) {
				//取出每个敌人坦克
				for(int j = 0; j < enemyList.size();j++){
					Enemy enemy = enemyList.get(j);//!!不要写成 i
					if(enemy.isLive) {
						if(this.hitTank(myShot, enemy)){
							//记录敌人数量减少
							//记录的坦克数量减一
							 Recorder.reduceEnNum();
							 Recorder.addEnRec();
						}
					}
				}
			}				
		}	
	}
	
	//判断敌人是否击中我
	public void hitHero(){
		//判断哪个敌人击中我
		for (int i = 0 ; i < this.enemyList.size();i++) {
			Enemy enemy = enemyList.get(i);
		//	if(enemy.isLive) {
				//取出每颗子弹
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
	
	//写一个函数专门判断子弹是否击中敌人坦克
	public boolean  hitTank(Shot shot,Tank  tank) {
		boolean b = false;//默认没有击中
		//判断该坦克的方向 坦克是矩形
		switch(tank.direction) {
		case 0:
			 if(shot.x > tank.x && shot.x < tank.x+20
				      &&shot.y> tank.y-5 && shot.y < tank.y + 30) {
					 //击中  
				    //子弹死亡
				 shot.isLive = false;
				   //敌人死亡
				 tank.isLive = false;
				 b = true;
				 //创建爆炸 放入向量中(否则出hitTank就访问不到bomb了)//ps绘图的坐标也是图的左上角 敌人坐标也是左上角
				 Bomb bomb = new Bomb(tank.x, tank.y);
				 bombList.add(bomb);
				 
				 }
			 break;
		case 1:
			 if(shot.x > tank.x && shot.x < tank.x+20
			      &&shot.y> tank.y && shot.y < tank.y + 33) {
				 //击中				
				 shot.isLive = false;			 
				 tank.isLive = false;
				 b = true;
				 Bomb bomb = new Bomb(tank.x, tank.y);
				 bombList.add(bomb);
			 }
			 break;
		case 2://左
			 if(shot.x > tank.x -5 && shot.x < tank.x+20
				      &&shot.y> tank.y && shot.y < tank.y + 20) {
					 //击中
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
					 //击中
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
	
	//画坦克（扩展）
	public void drawTank(int x,int y, Graphics g,int direction,int type) {
		//判断类型 颜色
		switch(type) {
			case 0 : 
				g.setColor(Color.yellow);  //画我的坦克
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
		
		//判断方向   0向上	1向下	2向左  3向右
		switch(direction) {			
			case 0 :{
			       //画出左边的矩形（左边轮子）
					g.fill3DRect(x, y, 5, 30,false);
					//画右边的矩形
					g.fill3DRect(x +15, y, 5 , 30,false);
					//画中间矩形
					g.fill3DRect(x +5,y + 7, 10, 17,false);
					//画中间圆形
					g.fillOval(x +5, y + 10, 9, 9);
					//画线
					g.drawLine(x + 10, y +10, x + 10, y -5);
					break;
			  }
			
			//1向下
			case 1 :{
			       //画出左边的矩形（左边轮子）
					g.fill3DRect(x, y, 5, 30,false);
					//画右边的矩形
					g.fill3DRect(x +15, y, 5 , 30,false);
					//画中间矩形
					g.fill3DRect(x +5,y + 7, 10, 17,false);
					//画中间圆形
					g.fillOval(x +5, y + 10, 9, 9);
					//画线
					g.drawLine(x + 10, y +10, x + 10, y + 33);
					break;
			  }
			
	     	//向左
			case 2 :{
			       //画出上边的矩形（左边轮子）
					g.fill3DRect(x, y, 30, 5,false);
					//画下边的矩形
					g.fill3DRect(x , y + 15, 30 , 5,false);
					//画中间矩形
					g.fill3DRect(x +7,y + 5, 17, 10,false);
					//画中间圆形
					g.fillOval(x +10, y + 5, 9, 9);
					//画线
					g.drawLine(x + 10, y +10, x -5 , y +10);
					break;
			  }
			
			//向右
			case 3 :{
			       //画出上边的矩形（左边轮子）
					g.fill3DRect(x, y, 30, 5,false);
					//画下边的矩形
					g.fill3DRect(x , y + 15, 30 , 5,false);
					//画中间矩形
					g.fill3DRect(x +7,y + 5, 17, 10,false);
					//画中间圆形
					g.fillOval(x +10, y + 5, 9, 9);
					//画线
					g.drawLine(x + 12, y +10, x + 33 , y +10);
					break;
			  }
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}
	//按下键处理 w向上  s向下 a向左 d 向右(上下左右的顺序)
	//还要转方向
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_W) {
			//修改hero的方向  写在tank类里
			//设置我的坦克hero的方向
			this.hero.setDirection(0);
			this.hero.moveUp();
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {//不能getchar
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
		//子弹很可能要和上下左右键同时按
		 if(e.getKeyCode() == KeyEvent.VK_J) {
			//开火
			 System.out.println("this.hero.shotList.size()= " + this.hero.shotList.size());
			//滞后一步 发完五颗要把子弹从向量中清除 不然永远只能打5颗
			 if(this.hero.shotList.size() < 5) {
				  this.hero.shotEnemy();
			}
	     }
			
		//必须重绘panel
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//每隔100ms重绘
		while(true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    this.hitHero();
			this.hitEnemy();
			
			
			//重绘	
			this.repaint();
		}
	}
}

