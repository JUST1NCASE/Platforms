import java.util.ArrayList;
public class Platforms {//written by Justin Schaefer
	/* 
	 **=must
	 *=would be cool 
	 _=we'll see
	 
	 **day/night
	 *platforms with sides
	 obstacles(enemies or walls)
	 *customizable ball
	 second level??
	 */
	
	private static ArrayList<Cloud> clouds=new ArrayList<Cloud>();
	private static ArrayList<Mountain> mountains=new ArrayList<Mountain>();
	
	public static void platforms(int localHighScore)
	{//Start screen
		StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.text(350,600,"Platforms!");
		StdDraw.text(350,500,"Press the space bar to jump, and hold it to jump further. Don't fall :P");
		StdDraw.text(350,475,"This ball is you. You have good taste in ball.");
		StdDraw.text(350,375,"Press the space bar to begin");
		StdDraw.setPenColor();
		StdDraw.text(350,675,"Current Run High Score: "+localHighScore);
	//Initialize Everything!!!!
		int score=0000;
		int next=0;//current or next platform
		double distance=0;//distance traveled
		double speed=-0.5;//modifies how fast the platforms and hills move
		Ball ball=new Ball(350,210,10);
		double grav=0;//gravity applied to ball
		double oldGrav=0;//previous gravity
		double starty=0;//start Y-value of a jump or fall
		int maxSquish=3;
		int cloudCount=initializeCloud();
		double[] x=new double[10];//array of X values for platforms
		double[] y=new double[10];//array of Y values for platforms
		//first few platforms
		x[0]=0;y[0]=200;
		x[1]=700;y[1]=200;
		x[2]=780;y[2]=100;x[3]=1100;y[3]=100;
		Hills.initialize();
	//Draw scenery!
		drawClouds();
		Hills.drawBackHill();
		Hills.drawHill();
		ball.draw(distance);
		StdDraw.setPenRadius(0.004);
		StdDraw.setPenColor();
		StdDraw.line(x[0],y[0],x[1],y[1]);
		while(!StdDraw.isKeyPressed(' ')){
		}
	//clear instructions
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledRectangle(350,475,350,125);
		while(true){
			int last=determineLast(x);
		//if last platform is a 125 units from right edge of screen, add a new one!
			if(x[last]+(110+25*speed)<700){
				newPlatform(last,x,y,speed);//32 frames for highest jump
			}
			next=determineNext(x,next);
		//if it's just too bright out
			if(cloudCount<7){
				cloudCount=newCloud(cloudCount);
			}
		//check if you're running out of hills, then make some more
			Hills.newBackHill();
			Hills.newHill();
			newMountain(distance);
		//Do art!!(draw the stuff)
			drawMountains();
			drawClouds();
			drawScore(score,localHighScore);
			Hills.drawBackHill();
			Hills.drawHill();
			drawPlat(x,y);
			ball.draw(distance);
		//Keep track of distance traveled and show off the pretty picture
			distance+=.2;
			StdDraw.show(20);
			StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
			speed=(distance-250)/500;
		//Scoot stuff over
			moveMountains(speed);
			cloudCount=moveClouds(speed);
			movePlat(ball.getX(),ball.getY(),x,y,speed);
			Hills.moveHills(speed);
		//LOTS of ball physics
			//make it squish if it's jumping or landing
			if(ball.isSquishing()){
				ball.squish();
			}
			if(ball.getSquish()>maxSquish){
				ball.unsquish();
			}
			if(!ball.isSquishing()){
				ball.unsquish();
			}
			//check if it's rolling on the platform (x[next] is the front of the platform)
			if(ball.getY()==y[next]+10&&x[next]<=ball.getX()){
				ball.setState(BallStates.Rolling);
				grav=0;
			}
			//check if the ball is not jumping and is not on the platform
			if((Math.abs(ball.getY()-10-y[next]))>=1&&ball.getState()!=BallStates.Jumping&&ball.getState()!=BallStates.Floating){
				if(ball.getState()==BallStates.Falling){
					//do nothing
				}
				else{
					ball.setState(BallStates.Falling);
					starty=ball.getY();
					ball.unsquish();
				}
			}
			//you can only jump when you're rolling
			if(ball.getState()==BallStates.Rolling){
				if(StdDraw.isKeyPressed(' ')){
					maxSquish=2;
					ball.squish();
					ball.setState(BallStates.Jumping);
					starty=ball.getY();
				}
			}
			//this is just here so that the ball can start in the middle
			if(ball.getX()>100){ball.moveX(-5+speed);}
			//jumping physics! yay! gravity w00t!1!!
			if(ball.getState()==BallStates.Jumping){
				if(ball.getY()>=starty+30&&!StdDraw.isKeyPressed(' ')){
						ball.setState(BallStates.Floating);
						starty=ball.getY();
						oldGrav=grav;
				}
				else if(ball.getY()-starty<65){grav=5;}
				else if(ball.getY()-starty<80){grav=4;}
				else if(ball.getY()-starty<95){grav=3;}
				else if(ball.getY()-starty<105){grav=2;}
				else if(ball.getY()-starty<110){grav=1;}
				else{
					ball.setState(BallStates.Falling);
					starty=ball.getY();
				}
			}
			if(ball.getState()==BallStates.Floating){
				if(ball.getY()==starty){grav=oldGrav-1;}
				else if(ball.getY()-starty<15*oldGrav/5.0){grav=oldGrav-2;}
				else if(ball.getY()-starty<25*oldGrav/5.0){grav=oldGrav-3;}
				else if(ball.getY()-starty<30*oldGrav/5.0){grav=oldGrav-4;}
				else if(ball.getY()-starty>=30*oldGrav/5.0){grav=oldGrav-5;}
				if(grav==0){
					ball.setState(BallStates.Falling);
					starty=ball.getY();
				}
			}
			//AAAAAAAAAAAAAAHHHHHHHHhhhhhhhhh......
			if(ball.getState()==BallStates.Falling){
				if(starty-ball.getY()<5){grav=-1;}
				else if(starty-ball.getY()<10){grav=-2;}
				else if(starty-ball.getY()<25){grav=-3;}
				else if(starty-ball.getY()<45){grav=-4;}
				else if(starty-ball.getY()<120){grav=-5;}
				else if(starty-ball.getY()>=120){grav=-(starty-ball.getY())/24;}
			}
			//makes the ball land on the platform... or get impaled
			if(ball.getX()>=x[next]&&ball.getY()>=y[next]&&ball.getY()+grav<=y[next]+10&&grav<0){
				ball.moveY(-(ball.getY()-y[next])+10);
				ball.squish();
				maxSquish=(int)Math.abs(grav);
				if(maxSquish>20){
					maxSquish=9;
				}else if(maxSquish>15){
					maxSquish=8;
				}else if(maxSquish>10){
					maxSquish=7;
				}else if(maxSquish>5){
					maxSquish=6;
				}
			}else if(ball.getX()>=x[next]&&ball.getY()-9<y[next]&&ball.getY()+9>=y[next]){
				ball.setState(BallStates.Deflating);
				break;
			}else{//otherwise just apply gravity
				ball.moveY(grav);
			}
			//you dead :'(
			if(ball.getY()+15<=0){break;}
			score=(int)distance;
		}
		if(ball.getState()==BallStates.Deflating){
			grav=-((ball.getY()+14-y[next])/10.0);
			for(int i=0;i<10;i++){
				StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
				ball.deflate();
				ball.moveX(5.0/ball.getDeflate());
				ball.moveY(grav);
				drawMountains();
				drawClouds();
				Hills.drawBackHill();
				Hills.drawHill();
				drawPlat(x,y);
				ball.draw(distance);
				drawScore(score,localHighScore);
				StdDraw.show(20);
				cloudCount=moveClouds(-1);
			}
		}
		while(StdDraw.hasNextKeyTyped()){
			StdDraw.nextKeyTyped();
		}
		//Game over screen	
		while(!StdDraw.hasNextKeyTyped()){
			StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
			drawMountains();
			drawClouds();
			Hills.drawBackHill();
			Hills.drawHill();
			drawPlat(x,y);
			ball.draw(distance);
			drawScore(score,localHighScore);
			StdDraw.setPenColor(StdDraw.BOOK_RED);
			StdDraw.text(350,400,"You Lose!");
			StdDraw.text(350,375,"Press space to try again.");
			StdDraw.text(350,350,"Press any other key to quit.");
			StdDraw.show(20);
			//cloudCount=moveClouds(-1);
			if(cloudCount<7){
				cloudCount=newCloud(cloudCount);
			}
		}
		if(StdDraw.isKeyPressed(' ')){
			for(int i=mountains.size()-1;i>=0;i--){
				mountains.remove(i);
			}
			for(int i=clouds.size()-1;i>=0;i--){
				clouds.remove(i);
			}
			if(score>=localHighScore){
				platforms(score);
			}else{
				platforms(localHighScore);
			}
		}else{
			System.exit(0);
		}
	}
	
	public static void drawPlat(double[] x,double[] y)
	{
		StdDraw.setPenRadius(0.004);
			StdDraw.setPenColor();
			for(int i=0;i<(x.length/2.0);i++){
				StdDraw.line(x[2*i],y[2*i],x[2*i+1],y[2*i+1]);
			}
	}
	
	public static void newPlatform(int l,double[] x,double[] y,double speed)
	{
		double prevy=y[l];
		if(l>=9){l-=10;}
		x[l+1]=700;
		y[l+1]=Math.random()*prevy+200;
		if(y[l+1]>prevy+100){y[l+1]=prevy+100;}
		if(y[l+1]>=590){y[l+1]=590;}
		if(l>=8){l-=10;}
		x[l+2]=Math.random()*(375+50*speed)+780;
		y[l+2]=y[l+1];
	}
	
	public static void movePlat(double ballx,double bally,double[] x,double[] y,double speed)
	{
		StdDraw.setPenRadius(0.008);
		for(int i=0;i<x.length/2.0;i++){
			x[2*i]-=5+speed;
			x[2*i+1]-=5+speed;
		}
	}
	
	public static int determineNext(double[] x,int n)
	{
		if(x[n]<=100&&x[n+1]>100){
			return n;
		}
		if(x[n]>=100){
			return n;
		}
		if(n+2>=x.length){
			n=n-x.length;
		}
		n+=2;
		return n;
	}
	
	public static int determineLast(double[] x)
	{
		int l=1;
		for(int i=0;i<x.length/2.0;i++){
			if(x[2*i+1]>=x[l]){l=2*i+1;}
		}
		return l;
	}
	
	public static int initializeCloud(){
		int count=0;
		Cloud one=new Cloud("1",475,570,2);
		clouds.add(one);
		count+=one.getSize();
		Cloud two=new Cloud("2",130,415,3);
		clouds.add(two);
		count+=two.getSize();
		Cloud three=new Cloud("3",190,660,1);
		clouds.add(three);
		count+=three.getSize();
		Cloud four=new Cloud("4",660,325,1);
		clouds.add(four);
		count+=four.getSize();
		return count;
	}
	
	public static void drawClouds(){
		for(Cloud cloud: clouds){
			cloud.draw();
		}
	}
	
	public static int moveClouds(double speed){
		int count=0;
		for(Cloud cloud: clouds){
			cloud.move(speed);
			if(cloud.getRight()<=0){
				count-=cloud.getSize();
			}else{
				count+=cloud.getSize();
			}
		}
		return count;
	}
	
	public static int newCloud(int count){
		for(int i=clouds.size()-1;i>=0;i--){
			if(clouds.get(i).getRight()<=0){
				clouds.remove(i);
			}
		}
		 int size=(int)(Math.random()*3+1);
		double bot=Math.random()*380+300;
		double center=700;
		String name=""+Math.random()*10;
		if(size==1){center+=25;}
		if(size==2){center+=50;}
		if(size==3){center+=75;}
		Cloud next=new Cloud(name,center,bot,size);
		clouds.add(next);
		count+=size;
		return count;
	}
	
	public static void drawMountains(){
		for(Mountain mountain: mountains)
			mountain.draw();
	}
	
	public static void newMountain(double dist){
		int count=(int)Math.floor(dist/500);
		if(mountains.size()<count){
			if(mountains.size()==0){
				if(dist%500<=.4){
					char n=(char)(97+mountains.size());
					String name=""+n;
					Mountain next=new Mountain(name);
					mountains.add(next);
				}
			}else{
				if(Math.abs(mountains.get(0).getRight()-(1060-50*mountains.size()))<=5){
					char n=(char)(97+mountains.size());
					String name=""+n;
					Mountain next=new Mountain(name);
					mountains.add(next);
				}	
			}	
		}
	}
	
	public static void moveMountains(double speed){
		for(int i=mountains.size()-1;i>=0;i--){
			mountains.get(i).move(speed);
			if(mountains.get(i).getRight()<=0){
				mountains.remove(i);
			}
		}
	}
	
	public static void drawScore(int score,int high){
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.text(350,650,"Score: "+score);
		StdDraw.setPenColor();
		StdDraw.text(350,675,"Current Run High Score: "+high);
	}
	
	public static void main(String args[]){
		StdDraw.setCanvasSize(700,700);
		StdDraw.setScale(0,700);
		platforms(0000);
	}
	
}
