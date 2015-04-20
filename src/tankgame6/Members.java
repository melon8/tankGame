package tankgame6;
import java.util.*;
import java.io.*;

import javax.sound.sampled.*;




//播放声音的类
class Music extends Thread {
	private String filename;
	public Music(String wavefile) {
		filename = wavefile;
	}
	public void run() {
		File soundFile = new File(filename);
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auLine = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		try {
			auLine = (SourceDataLine) AudioSystem.getLine(info);
			auLine.open(format);
		} catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
			return;
		}
		
		auLine.start();
		int nBytesRead = 0;
		//韩冲
		byte[] abData = new byte[512];
		
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData,0,abData.length);
				if(nBytesRead >=0) 
					  auLine.write(abData, 0, nBytesRead);
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		
	}
}


//继续上局游戏时 坐标加方向为一个对象 
class Node {
	int x;
	int y;
    int direction;
    public Node(int x,int y, int direction) {
    	this.x=x;
    	this.y = y;
    	this.direction = direction;
    }
}


//记录类
class Recorder {
	//记录每关有多少敌人  做成静态的让大家访问的都是同一个 直接从类名就可以访问
	private static int enNum = 20;
	//我的生命
	private static int myLife = 3;
	//我杀掉的敌人
	private static int deadEnNum = 0;
	//从文件中恢复记录点
	static Vector<Node> nodeList = new Vector<Node>();
	
	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	private static  FileReader fr = null;
	private static  BufferedReader br = null;
	
	private   Vector<Enemy> enemyList = new Vector<Enemy>();
	
	//完成读取任务
	public  Vector<Node> getNodeAndEnNum() {	
		try {
			fr = new FileReader("d:/myTankRecording.txt");
			br = new BufferedReader(fr);
			//先读取第一行：剩余坦克数，后面：坦克坐标方向
			String n = "  ";//对于初始化的string  里面有多少空格都视为null
		    n =  br.readLine();
		    deadEnNum = Integer.parseInt(n);
			while((n=br.readLine()) != null) {
				String[] m = n.split(" ");  //必须严格的按照空格的个数给出
				//肯定每行分成三份
		        Node node = new Node(Integer.parseInt(m[0]),
		        		Integer.parseInt(m[1]),Integer.parseInt(m[2]));
				nodeList.add(node);				
				}	
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				//后打开先关闭
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
		return nodeList;
	}
	
	//保存击毁敌人的数量和敌人坦克坐标，方向
	public  void keepRecordAndEnemy() {
		try {
			//创建文件流
			fw = new  FileWriter("d:/myTankRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(deadEnNum + "\r\n");
			//保存当前还活着的敌人坦克坐标和方向(要先传进全部敌人向量)
			for(int i = 0; i < enemyList.size(); i++ ) {
				//取出一个坦克
				Enemy en = enemyList.get(i);
				if(en.isLive) {
					//活的就保存   注意只有一个空格  若有两个空格 则分隔符必须为两个空格
					String message = en.x + " " +en.y + " " + en.direction;
					//写入
					bw.write(message +"\r\n");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//后开先关
				bw.close()
				;fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//从文件中读取,记录
	public static void getRecording() {
		try {
			fr = new FileReader("d:/myTankRecording.txt");
			br = new BufferedReader(fr);
			String n = br.readLine();
			deadEnNum = Integer.parseInt(n);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				//后打开先关闭
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	//把玩家击毁敌人的数量保存到文件中
	public  static void keepRecording() {
		try {
			//创建文件流
			fw = new  FileWriter("d:/myTankRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(deadEnNum + "\r\n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//后开先关
				bw.close()
				;fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
   }

  public static int getDeadEnNum() {
		return deadEnNum;
	}
	public static void setDeadEnNum(int deadEnNum) {
		Recorder.deadEnNum = deadEnNum;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	public   Vector<Enemy> getEnemyList() {
		return enemyList; 
	}
	public   void setEnemyList(Vector<Enemy> enemyList1) {
	this.enemyList = enemyList1;//静态方法不能用this.
		System.out.print("ok");
	}
	//减掉一个敌人 静态方法可以直接通过类名访问
	public static void reduceEnNum() {
		enNum--;		
	}	
	//消灭敌人数
	public static void  addEnRec() {
		deadEnNum++;
	}
	public static void reduceMyLife(){
		myLife--;
	}
}

//炸弹类 不是线程 没有坐标改变
class Bomb {
	//炸弹坐标
	int x,y;
	//炸弹生命
	int life = 9;
	boolean isLive;
	public Bomb(int x,int y) {
		this.x = x;
		this.y = y;
	}
	//减少生命值
	public void lifeDown() {
		if(life > 0) {
			life--;
		}
		else{
			this.isLive = false;
		}
	}
}
//子弹类 属于一个坦克的
class Shot implements Runnable {
	int x;
	int y;
	int direction;
	int speed = 1;
	//是否活着
	boolean isLive = true;
	public Shot(int x,int y,int direction) {
		this.x = x;
		this.y= y;
		this.direction = direction;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 
			switch(direction) {
			case 0 :
				y -= speed;
				break;
			case 1 :
				y +=speed;
				break;
			case 2 :
				x -= speed;
				break;
			case 3 :
				x += speed;
				break;
			}
		//	System.out.println("子弹坐标 x =" + x  + ",y = " + y);
			//子弹还没确定什么时候死亡if .getBackgrond = Cyan(敌人)，则死
			//else if 背景是灰色 出框了 就死
			//判断该子弹是否碰到边缘.
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
}
//坦克类
class Tank {
	//location
	int x = 0;//坦克坐标
	int y = 0;
	boolean isLive = true;
	//坦克方向
	//0向上	1向下	2向左  3向右
	int direction = 0;
	
	//设置坦克的速度  坦克的属性
	int speed = 3;//原始速度
	//设置颜色
	int color = 0;//hero （我的坦克）的颜色
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public Tank (int x, int y) {
		this.x = x;
		this.y = y;
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
	
	
}

//我的坦克
class Hero extends Tank {
   
	 //子弹 改成向量 不然只有 一颗子弹 可能涉及到并发 选Vector
	Shot shot = null;
	Vector<Shot> shotList = new Vector<Shot>();
	
	public Hero(int x ,int y) {
		super(x,y);//还要有父类的构造函数 ，用父类的构造函数初始化子类
	}
	
	//开火 坦克的动作//子弹与坦克方向有关//子弹在哪里创建 线程就从哪里开始
	public void shotEnemy() {
				
		switch(this.direction) {
			case 0 :
				//创建一颗子弹
				shot = new Shot(x + 10, y,0); 
				//把子弹加入到向量（还没画出 画出去myPane函数）
				shotList.add(shot);
				break;
			case 1 :
				shot = new Shot(x + 10, y + 30,1);
				shotList.add(shot);
				break;
			case 2 :
				shot = new Shot(x - 5 , y + 10,2);
				shotList.add(shot);
				break;
			case 3 :
				shot = new Shot(x + 30, y + 10,3);
				shotList.add(shot);
				break;
		}
		//启动子弹线程
		Thread t = new Thread (shot);
		t.start();
		
	}
	//坦克向上移动
	public void moveUp() {
		 y -= speed;//若将来想改变速度 可以直接通过setSpeed来改
	}
	public void moveDown() {
		y +=speed;
	}
	public void moveLeft() {
		x -= speed;
	}
	public void moveRight() {
		x += speed;
	}
}	
	
//敌人坦克
class Enemy extends Tank implements Runnable{
	//boolean isLive = true; 放到tank类中
	int times;
	
	//定义一个向量 可以访问mypanel中所有敌人坦克
	Vector<Enemy> enemyList = new Vector <Enemy>();
	//定义一个向量，存放敌人的子弹
	 Vector<Shot> shotList = new Vector<Shot>();
	 //敌人添加子弹应该在刚刚创建敌人坦克 和 敌人坦克子弹死亡后
	 
	public Enemy(int x, int y) {
		super(x, y);//必须继承父类构造函数
	}
	
	
	//得到MyPanel的敌人坦克向量  每隔敌人坦克被调用 就要调用此函数
		public void setEts(Vector<Enemy> v)
		{
			this.enemyList=v;
		}
	
	//判断是否重叠
	public boolean isTouchOtherEnemy() {
		boolean isTouch = false;
	
		switch(this.direction) {
			case 0:
						//本坦克向上
						//取出所有敌人坦克
						for(int i = 0; i < enemyList.size(); i++) {
							//取出一个敌人坦克(可能包括本坦克)
							Enemy en =  enemyList.get(i);
							//如果不是自己
							if(en !=this) {
								//如果敌人坦克向上或向下
								if(en.direction == 0 || en.direction ==1) {
										//我的坦克矩形的上横边左点
										if(this.x >= en.x -3 && this.x <= en.x +3+20 && this.y >= en.y -3&& this.y <= en.y +3+30) {
											return true;
										}
										//右点
										if(this.x +20 >= en.x -3&& this.x + 20 <= en.x+3 +20 && this.y >= en.y -3&& this.y <= en.y +3+30) {
											return true;
										}
								}
								//如果敌人向左或向右
								if(en.direction == 2 || en.direction ==3) {
									//左点
									if(this.x >= en.x -3&& this.x <= en.x +3+30 && this.y >= en.y -3&& this.y <= en.y+3 +20) {
										return true;
									}
									//右点
									if(this.x +20 >= en.x -3&& this.x + 20 <= en.x +3+30 && this.y >= en.y-3 && this.y <= en.y +3+20) {
										return true;
									}
								}						
							}
						}
						break;
				
			case 1:		
						//本坦克向下
						//取出所有敌人坦克
						for(int i = 0; i < enemyList.size(); i++) {
							//取出一个敌人坦克
							Enemy en =  enemyList.get(i);
							//如果不是自己
							if(en !=this) {
								//如果敌人坦克向上或向下
								if(en.direction == 0 || en.direction ==1) {
										//我的坦克进行矩形的下横边左点
										if(this.x >= en.x -3&& this.x <= en.x +3+20 && this.y +30>= en.y -3&& this.y+30 <= en.y+3 +30) {
											return true;
										}
										//右点
										if(this.x +20 >= en.x -3&& this.x + 20 <= en.x +3+20 && this.y +30>= en.y -3&& this.y+30 <= en.y+3 +30) {
											return true;
										}
								}
								//如果敌人向左或向右
								if(en.direction == 2 || en.direction ==3) {
									//左点
									if(this.x >= en.x -3&& this.x <= en.x +3+3+30 && this.y+30 >= en.y-3 && this.y+30 <= en.y +3+20) {
										return true;
									}
									//右点
									if(this.x +20 >= en.x -3&& this.x + 30 <= en.x+3 +20 && this.y+30 >= en.y -3&& this.y +30<= en.y +3+20) {
										return true;
									}
								}						
							}
						}
					  break;
			case 2:
						//本坦克向左
						//取出所有敌人坦克
						for(int i = 0; i < enemyList.size(); i++) {
							//取出一个敌人坦克
							Enemy en =  enemyList.get(i);
							//如果不是自己
							if(en !=this) {
								//如果敌人坦克向上或向下
								if(en.direction == 0 || en.direction ==1) {
										//我的坦克矩形的左竖边上点
										if(this.x >= en.x -3&& this.x <= en.x +3+20 && this.y >= en.y-3 && this.y <= en.y +3+30) {
											return true;
										}
										// 下点
										if(this.x  >= en.x -3&& this.x  <= en.x +3+20 && this.y +20 >= en.y -3&& this.y +20 <= en.y +3+30) {
											return true;
										}
								}
								//如果敌人向左或向右
								if(en.direction == 2 || en.direction ==3) {
									//上点
									if(this.x >= en.x -3&& this.x <= en.x +3+30 && this.y >= en.y-3 && this.y <= en.y+3 +20) {
										return true;
									}
									//下点
									if(this.x  >= en.x -3&& this.x  <= en.x +3+30 && this.y +20>= en.y-3 && this.y +20<= en.y+3 +20) {
										return true;
									}
								 }						
							 }
						 }
					  break;
			case 3:
					//本坦克向右上
					//取出所有敌人坦克
					for(int i = 0; i < enemyList.size(); i++) {
						//取出一个敌人坦克
						Enemy en =  enemyList.get(i);
						//如果不是自己
						if(en !=this) {
							//如果敌人坦克向上或向下
							if(en.direction == 0 || en.direction ==1) {
									//我的坦克矩形右竖变上点
									if(this.x +30>= en.x -3&& this.x+30 <= en.x +20+3 && this.y >= en.y -3&& this.y <= en.y +30+3) {
										return true;
									}
									//下点
									if(this.x +30 >= en.x-3 && this.x + 30 <= en.x +20+3 && this.y +20 >= en.y -3&& this.y  +20<= en.y +30+3) {
										return true;
									}
							}
							//如果敌人向左或向右
							if(en.direction == 2 || en.direction ==3) {
								//上点
								if(this.x +30 >= en.x -3&& this.x +30+3<= en.x +30 && this.y >= en.y -3&& this.y <= en.y +20+3) {
									return true;
								}
								//下点
								if(this.x +30 >= en.x-3 && this.x + 30 +3<= en.x +30 && this.y +20>= en.y -3&& this.y +20<= en.y +20+3) {
									return true;
								}
							}						
						}
					}
					  break;
		}
		return isTouch;
	}
	
	@Override
	public void run() {
		// 敌人坦克随机自由移动
		while(true) {
			
			 switch(this.direction) {
			  	//正在向上走 让它继续在走两步 再随机变化
			  	case 0 :
			  		for(int i = 0; i < 30; i++) {
			  			//保证坦克不出边界
			  			if(y > 10 && !this.isTouchOtherEnemy()) 
			  				y-=speed;
			  			
			  			try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				  		
			  		}		  			
			  		break;

			  	case 1 :	  		
			  		for(int i = 0; i < 30; i++) {
			  			if( y < 300  && !this.isTouchOtherEnemy()) 
			  				y+=speed;
			  			try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			  				  		
			  		}
			  			break;
						
			 	case 2 :
			 		for(int i = 0; i < 30; i++) {
			 			if(x >10  && !this.isTouchOtherEnemy()) 
			 				x-=speed;
			  			try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		 			  			
			 		}
			  	   break;
			  	   
			  	case 3 :
			  		for(int i = 0; i < 30; i++) {
			  			if(x<400  && !this.isTouchOtherEnemy()) 
			  				x+=speed;
			  			try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();					
		  			    }			  	     	
			  		}
			  		break;
			  }
			  	
			//run里每次休眠20*50ms=1s 对敌人发射子弹来说间隔太小
	  		//间隔2s比较合理
	  		this.times++;
			if(times % 2 ==0) {
				//判断是否需要给敌人坦克加入新子弹 子弹死前最多加5颗
					if(isLive) {
						if(shotList.size() <5) {
							//System.out.println("enemy.shotList.size() <5");
							Shot shot =null;
							//没有子弹
							//添加
							switch(direction) {
								case 0 :
									//创建一颗子弹
									shot = new Shot(x + 10, y,0); 
									//把子弹加入到向量（还没画出 画出去myPane函数）
									shotList.add(shot);
									break;
								case 1 :
									shot = new Shot(x + 10, y + 30,1);
									shotList.add(shot);
									break;
								case 2 :
									shot = new Shot(x - 5 , y + 10,2);
									shotList.add(shot);
									break;
								case 3 :
									shot = new Shot(x + 30, y + 10,3);
									shotList.add(shot);
									break;
							}
							//启动子弹线程
							Thread t = new Thread (shot);
							t.start();
			             }
					}
		     	}	
				 
			 //让坦克随机产生一个方向
			this.setDirection((int)(Math.random() * 4));
				//  this.direction = (int)(Math.random() * 4);
		    //判断敌人坦克是否死亡,死亡后退出线程
				  if(this.isLive == false)   
					   break;
		}
    }
	
}
	
	
