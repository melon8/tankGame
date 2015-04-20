package tankgame6;
import java.util.*;
import java.io.*;

import javax.sound.sampled.*;




//������������
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
		//����
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


//�����Ͼ���Ϸʱ ����ӷ���Ϊһ������ 
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


//��¼��
class Recorder {
	//��¼ÿ���ж��ٵ���  ���ɾ�̬���ô�ҷ��ʵĶ���ͬһ�� ֱ�Ӵ������Ϳ��Է���
	private static int enNum = 20;
	//�ҵ�����
	private static int myLife = 3;
	//��ɱ���ĵ���
	private static int deadEnNum = 0;
	//���ļ��лָ���¼��
	static Vector<Node> nodeList = new Vector<Node>();
	
	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	private static  FileReader fr = null;
	private static  BufferedReader br = null;
	
	private   Vector<Enemy> enemyList = new Vector<Enemy>();
	
	//��ɶ�ȡ����
	public  Vector<Node> getNodeAndEnNum() {	
		try {
			fr = new FileReader("d:/myTankRecording.txt");
			br = new BufferedReader(fr);
			//�ȶ�ȡ��һ�У�ʣ��̹���������棺̹�����귽��
			String n = "  ";//���ڳ�ʼ����string  �����ж��ٿո���Ϊnull
		    n =  br.readLine();
		    deadEnNum = Integer.parseInt(n);
			while((n=br.readLine()) != null) {
				String[] m = n.split(" ");  //�����ϸ�İ��տո�ĸ�������
				//�϶�ÿ�зֳ�����
		        Node node = new Node(Integer.parseInt(m[0]),
		        		Integer.parseInt(m[1]),Integer.parseInt(m[2]));
				nodeList.add(node);				
				}	
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				//����ȹر�
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
		return nodeList;
	}
	
	//������ٵ��˵������͵���̹�����꣬����
	public  void keepRecordAndEnemy() {
		try {
			//�����ļ���
			fw = new  FileWriter("d:/myTankRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(deadEnNum + "\r\n");
			//���浱ǰ�����ŵĵ���̹������ͷ���(Ҫ�ȴ���ȫ����������)
			for(int i = 0; i < enemyList.size(); i++ ) {
				//ȡ��һ��̹��
				Enemy en = enemyList.get(i);
				if(en.isLive) {
					//��ľͱ���   ע��ֻ��һ���ո�  ���������ո� ��ָ�������Ϊ�����ո�
					String message = en.x + " " +en.y + " " + en.direction;
					//д��
					bw.write(message +"\r\n");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//���ȹ�
				bw.close()
				;fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//���ļ��ж�ȡ,��¼
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
				//����ȹر�
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	//����һ��ٵ��˵��������浽�ļ���
	public  static void keepRecording() {
		try {
			//�����ļ���
			fw = new  FileWriter("d:/myTankRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(deadEnNum + "\r\n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//���ȹ�
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
	this.enemyList = enemyList1;//��̬����������this.
		System.out.print("ok");
	}
	//����һ������ ��̬��������ֱ��ͨ����������
	public static void reduceEnNum() {
		enNum--;		
	}	
	//���������
	public static void  addEnRec() {
		deadEnNum++;
	}
	public static void reduceMyLife(){
		myLife--;
	}
}

//ը���� �����߳� û������ı�
class Bomb {
	//ը������
	int x,y;
	//ը������
	int life = 9;
	boolean isLive;
	public Bomb(int x,int y) {
		this.x = x;
		this.y = y;
	}
	//��������ֵ
	public void lifeDown() {
		if(life > 0) {
			life--;
		}
		else{
			this.isLive = false;
		}
	}
}
//�ӵ��� ����һ��̹�˵�
class Shot implements Runnable {
	int x;
	int y;
	int direction;
	int speed = 1;
	//�Ƿ����
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
		//	System.out.println("�ӵ����� x =" + x  + ",y = " + y);
			//�ӵ���ûȷ��ʲôʱ������if .getBackgrond = Cyan(����)������
			//else if �����ǻ�ɫ ������ ����
			//�жϸ��ӵ��Ƿ�������Ե.
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
}
//̹����
class Tank {
	//location
	int x = 0;//̹������
	int y = 0;
	boolean isLive = true;
	//̹�˷���
	//0����	1����	2����  3����
	int direction = 0;
	
	//����̹�˵��ٶ�  ̹�˵�����
	int speed = 3;//ԭʼ�ٶ�
	//������ɫ
	int color = 0;//hero ���ҵ�̹�ˣ�����ɫ
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

//�ҵ�̹��
class Hero extends Tank {
   
	 //�ӵ� �ĳ����� ��Ȼֻ�� һ���ӵ� �����漰������ ѡVector
	Shot shot = null;
	Vector<Shot> shotList = new Vector<Shot>();
	
	public Hero(int x ,int y) {
		super(x,y);//��Ҫ�и���Ĺ��캯�� ���ø���Ĺ��캯����ʼ������
	}
	
	//���� ̹�˵Ķ���//�ӵ���̹�˷����й�//�ӵ������ﴴ�� �߳̾ʹ����￪ʼ
	public void shotEnemy() {
				
		switch(this.direction) {
			case 0 :
				//����һ���ӵ�
				shot = new Shot(x + 10, y,0); 
				//���ӵ����뵽��������û���� ����ȥmyPane������
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
		//�����ӵ��߳�
		Thread t = new Thread (shot);
		t.start();
		
	}
	//̹�������ƶ�
	public void moveUp() {
		 y -= speed;//��������ı��ٶ� ����ֱ��ͨ��setSpeed����
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
	
//����̹��
class Enemy extends Tank implements Runnable{
	//boolean isLive = true; �ŵ�tank����
	int times;
	
	//����һ������ ���Է���mypanel�����е���̹��
	Vector<Enemy> enemyList = new Vector <Enemy>();
	//����һ����������ŵ��˵��ӵ�
	 Vector<Shot> shotList = new Vector<Shot>();
	 //��������ӵ�Ӧ���ڸոմ�������̹�� �� ����̹���ӵ�������
	 
	public Enemy(int x, int y) {
		super(x, y);//����̳и��๹�캯��
	}
	
	
	//�õ�MyPanel�ĵ���̹������  ÿ������̹�˱����� ��Ҫ���ô˺���
		public void setEts(Vector<Enemy> v)
		{
			this.enemyList=v;
		}
	
	//�ж��Ƿ��ص�
	public boolean isTouchOtherEnemy() {
		boolean isTouch = false;
	
		switch(this.direction) {
			case 0:
						//��̹������
						//ȡ�����е���̹��
						for(int i = 0; i < enemyList.size(); i++) {
							//ȡ��һ������̹��(���ܰ�����̹��)
							Enemy en =  enemyList.get(i);
							//��������Լ�
							if(en !=this) {
								//�������̹�����ϻ�����
								if(en.direction == 0 || en.direction ==1) {
										//�ҵ�̹�˾��ε��Ϻ�����
										if(this.x >= en.x -3 && this.x <= en.x +3+20 && this.y >= en.y -3&& this.y <= en.y +3+30) {
											return true;
										}
										//�ҵ�
										if(this.x +20 >= en.x -3&& this.x + 20 <= en.x+3 +20 && this.y >= en.y -3&& this.y <= en.y +3+30) {
											return true;
										}
								}
								//����������������
								if(en.direction == 2 || en.direction ==3) {
									//���
									if(this.x >= en.x -3&& this.x <= en.x +3+30 && this.y >= en.y -3&& this.y <= en.y+3 +20) {
										return true;
									}
									//�ҵ�
									if(this.x +20 >= en.x -3&& this.x + 20 <= en.x +3+30 && this.y >= en.y-3 && this.y <= en.y +3+20) {
										return true;
									}
								}						
							}
						}
						break;
				
			case 1:		
						//��̹������
						//ȡ�����е���̹��
						for(int i = 0; i < enemyList.size(); i++) {
							//ȡ��һ������̹��
							Enemy en =  enemyList.get(i);
							//��������Լ�
							if(en !=this) {
								//�������̹�����ϻ�����
								if(en.direction == 0 || en.direction ==1) {
										//�ҵ�̹�˽��о��ε��º�����
										if(this.x >= en.x -3&& this.x <= en.x +3+20 && this.y +30>= en.y -3&& this.y+30 <= en.y+3 +30) {
											return true;
										}
										//�ҵ�
										if(this.x +20 >= en.x -3&& this.x + 20 <= en.x +3+20 && this.y +30>= en.y -3&& this.y+30 <= en.y+3 +30) {
											return true;
										}
								}
								//����������������
								if(en.direction == 2 || en.direction ==3) {
									//���
									if(this.x >= en.x -3&& this.x <= en.x +3+3+30 && this.y+30 >= en.y-3 && this.y+30 <= en.y +3+20) {
										return true;
									}
									//�ҵ�
									if(this.x +20 >= en.x -3&& this.x + 30 <= en.x+3 +20 && this.y+30 >= en.y -3&& this.y +30<= en.y +3+20) {
										return true;
									}
								}						
							}
						}
					  break;
			case 2:
						//��̹������
						//ȡ�����е���̹��
						for(int i = 0; i < enemyList.size(); i++) {
							//ȡ��һ������̹��
							Enemy en =  enemyList.get(i);
							//��������Լ�
							if(en !=this) {
								//�������̹�����ϻ�����
								if(en.direction == 0 || en.direction ==1) {
										//�ҵ�̹�˾��ε��������ϵ�
										if(this.x >= en.x -3&& this.x <= en.x +3+20 && this.y >= en.y-3 && this.y <= en.y +3+30) {
											return true;
										}
										// �µ�
										if(this.x  >= en.x -3&& this.x  <= en.x +3+20 && this.y +20 >= en.y -3&& this.y +20 <= en.y +3+30) {
											return true;
										}
								}
								//����������������
								if(en.direction == 2 || en.direction ==3) {
									//�ϵ�
									if(this.x >= en.x -3&& this.x <= en.x +3+30 && this.y >= en.y-3 && this.y <= en.y+3 +20) {
										return true;
									}
									//�µ�
									if(this.x  >= en.x -3&& this.x  <= en.x +3+30 && this.y +20>= en.y-3 && this.y +20<= en.y+3 +20) {
										return true;
									}
								 }						
							 }
						 }
					  break;
			case 3:
					//��̹��������
					//ȡ�����е���̹��
					for(int i = 0; i < enemyList.size(); i++) {
						//ȡ��һ������̹��
						Enemy en =  enemyList.get(i);
						//��������Լ�
						if(en !=this) {
							//�������̹�����ϻ�����
							if(en.direction == 0 || en.direction ==1) {
									//�ҵ�̹�˾����������ϵ�
									if(this.x +30>= en.x -3&& this.x+30 <= en.x +20+3 && this.y >= en.y -3&& this.y <= en.y +30+3) {
										return true;
									}
									//�µ�
									if(this.x +30 >= en.x-3 && this.x + 30 <= en.x +20+3 && this.y +20 >= en.y -3&& this.y  +20<= en.y +30+3) {
										return true;
									}
							}
							//����������������
							if(en.direction == 2 || en.direction ==3) {
								//�ϵ�
								if(this.x +30 >= en.x -3&& this.x +30+3<= en.x +30 && this.y >= en.y -3&& this.y <= en.y +20+3) {
									return true;
								}
								//�µ�
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
		// ����̹����������ƶ�
		while(true) {
			
			 switch(this.direction) {
			  	//���������� ���������������� ������仯
			  	case 0 :
			  		for(int i = 0; i < 30; i++) {
			  			//��֤̹�˲����߽�
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
			  	
			//run��ÿ������20*50ms=1s �Ե��˷����ӵ���˵���̫С
	  		//���2s�ȽϺ���
	  		this.times++;
			if(times % 2 ==0) {
				//�ж��Ƿ���Ҫ������̹�˼������ӵ� �ӵ���ǰ����5��
					if(isLive) {
						if(shotList.size() <5) {
							//System.out.println("enemy.shotList.size() <5");
							Shot shot =null;
							//û���ӵ�
							//���
							switch(direction) {
								case 0 :
									//����һ���ӵ�
									shot = new Shot(x + 10, y,0); 
									//���ӵ����뵽��������û���� ����ȥmyPane������
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
							//�����ӵ��߳�
							Thread t = new Thread (shot);
							t.start();
			             }
					}
		     	}	
				 
			 //��̹���������һ������
			this.setDirection((int)(Math.random() * 4));
				//  this.direction = (int)(Math.random() * 4);
		    //�жϵ���̹���Ƿ�����,�������˳��߳�
				  if(this.isLive == false)   
					   break;
		}
    }
	
}
	
	
