import java.util.ArrayList;
public class Platforms {//written by Justin Schaefer
	
	private static ArrayList<Cloud> clouds=new ArrayList<Cloud>();
	
	public static void platforms(String localHighScore)
	{//Start screen
		StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.text(350,600,"Platforms!");
		StdDraw.text(350,500,"Press the space bar to jump. Don't fall :P");
		StdDraw.text(350,475,"This ball is you. You have good taste in ball.");
		StdDraw.text(350,375,"Press any key to begin");
		StdDraw.setPenColor();
		StdDraw.text(350,675,"Current Run High Score: "+localHighScore);
	//Initialize Everything!!!!
		String score="0000";
		int next=0;//current or next platform
		double distance=0;//distance traveled
		double ballx=350;//ball X coordinate
		double bally=210;//ball Y coordinate
		BallStates state=BallStates.Rolling;
		double grav=0;//gravity applied to ball
		double starty=0;//start Y-value of a jump or fall
		int cloudCount=initializeCloud();
		int popCounter=0;//counts the number of frames the ball has been deflating for
		double[] x=new double[10];//array of X values for platforms
		double[] y=new double[10];//array of Y values for platforms
		//first few platforms
		x[0]=0;y[0]=200;
		x[1]=700;y[1]=200;
		x[2]=800;y[2]=100;x[3]=1100;y[3]=100;
		Hills.initialize();
	//Draw scenery!
		drawClouds();
		Hills.drawBackHill();
		Hills.drawHill();
		drawBall(ballx,bally,distance,popCounter);
		StdDraw.setPenRadius(0.004);
		StdDraw.setPenColor();
		StdDraw.line(x[0],y[0],x[1],y[1]);
		while(!StdDraw.hasNextKeyTyped()){
			//wait for key press
		}
		char j=StdDraw.nextKeyTyped();
	//clear instructions
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledRectangle(350,475,350,125);
		while(true){
			int last=determineLast(x);
		//if last platform is a 125 units from right edge of screen, add a new one!
			if(x[last]+125<700){
				newPlatform(last,x,y);
			}
			next=determineNext(x,next);
		//if it's just too bright out
			if(cloudCount<7){
				cloudCount=newCloud(cloudCount);
			}
		//check if you're running out of hills, then make some more
			Hills.newBackHill();
			Hills.newHill();
		//Do art!!(draw the stuff)	
			drawClouds();
			drawScore(score,localHighScore);;
			Hills.drawBackHill();
			Hills.drawHill();
			drawPlat(x,y);
			drawBall(ballx,bally,distance,popCounter);
		//Keep track of distance traveled and show off the pretty picture
			distance+=.15;
			StdDraw.show(15);
		//Scoot stuff over
			cloudCount=moveClouds(cloudCount);
			movePlat(ballx,bally,x,y);
			Hills.moveHills();
		//LOTS of ball physics
			//check if it's rolling on the platform (x[next] is the front of the platform)
			if((Math.abs(bally-10-y[next]))<1&&x[next]<=ballx){
				state=BallStates.Rolling;
				grav=0;
			}
			//check if the ball is not jumping and is not on the platform
			if((Math.abs(bally-10-y[next]))>=1&&state!=BallStates.Jumping){
				if(state==BallStates.Falling||state==BallStates.Deflating){
					//do nothing
				}
				else{
					state=BallStates.Falling;
					starty=bally;
				}
			}
			//you can only jump when you're rolling
			if(state==BallStates.Rolling){
				if(StdDraw.hasNextKeyTyped()){
					j=StdDraw.nextKeyTyped();
					if(j==' '){
						state=BallStates.Jumping;
						starty=bally;
					}
				}
			}else{
				if(StdDraw.hasNextKeyTyped()){
					j=StdDraw.nextKeyTyped();
					//this takes in all keys pressed while in the air
				}
			}
			//this is just here so that the ball can start in the middle
			if(ballx>=150){ballx-=5;}
			//jumping physics! yay! gravity w00t!1!!
			//(gravity determined by distance from start)
			if(state==BallStates.Jumping){
				if(bally>=starty+110){
					state=BallStates.Falling;
					starty=bally;
				}
				else if(bally-starty<65){grav=5;}
				else if(bally-starty<80){grav=4;}
				else if(bally-starty<95){grav=3;}
				else if(bally-starty<105){grav=2;}
				else if(bally-starty<110){grav=1;}
			}
			//AAAAAAAAAAAAAAHHHHHHHHhhhhhhhhh......
			if(state==BallStates.Falling){
				if(starty-bally<5){grav=-1;}
				else if(starty-bally<10){grav=-2;}
				else if(starty-bally<25){grav=-3;}
				else if(starty-bally<45){grav=-4;}
				else if(starty-bally<120){grav=-5;}
				else if(starty-bally>=120){grav=-(starty-bally)/24;}
			}
			//makes the ball land on the platform... or get impaled
			if(ballx>=x[next]&&bally>=y[next]&&bally+grav<y[next]+10&&grav<0){
				bally=y[next]+10;
			}else if(ballx>=x[next]&&bally-9<y[next]&&bally+9>=y[next]){
				state=BallStates.Deflating;
				grav=-(bally+10-y[next])/5.0;
				popCounter++;
				bally+=grav;
				if(popCounter>=5){
					break;
				}
			}else{//otherwise just apply gravity
				bally+=grav;
			}
			//you dead :'(
			if(bally+15<=0){break;}
			score=newScore(distance);
		}
	//Game over screen
		drawClouds();
		for(int i=clouds.size()-1;i>=0;i--){
			clouds.remove(i);
		}
		Hills.drawBackHill();
		Hills.drawHill();
		drawPlat(x,y);
		drawBall(ballx,bally,distance,popCounter);
		drawScore(score,localHighScore);
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.text(350,400,"You Lose!");
		StdDraw.text(350,375,"Press space to try again.");
		StdDraw.text(350,350,"Press any other key to quit.");
		StdDraw.show();
		while(!StdDraw.hasNextKeyTyped()){
			//wait for key press
		}
		j=StdDraw.nextKeyTyped();
		if(j==' '){
			score=compareScore(score,localHighScore);
			platforms(score);
		}else{
			System.exit(0);
		}
	}
	
	public static void drawBall(double ballx,double bally,double dist,int popCounter){
		StdDraw.setPenColor(StdDraw.RED);
		if(popCounter==0){
			StdDraw.filledCircle(ballx,bally,10);
			dist=(dist+105)*70;
			while(dist>=360){dist-=360;}
			dist=(360-dist)*Math.PI/180;
			StdDraw.setPenColor(StdDraw.ORANGE);
			StdDraw.filledCircle(Math.cos(dist)*6+ballx,Math.sin(dist)*6+bally,1.5);
		}else{
			StdDraw.filledEllipse(ballx,bally,11-(popCounter*2),10+popCounter);
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
	
	public static void newPlatform(int l,double[] x,double[] y)
	{
		double prevy=y[l];
		if(l>=9){l-=10;}
		x[l+1]=700;
		y[l+1]=Math.random()*prevy+200;
		if(y[l+1]>prevy+100){y[l+1]=prevy+100;}
		if(y[l+1]>=590){y[l+1]=590;}
		if(l>=8){l-=10;}
		x[l+2]=Math.random()*300+800;
		y[l+2]=y[l+1];
	}
	
	public static void movePlat(double ballx,double bally,double[] x,double[] y)
	{
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledCircle(ballx,bally,11);
		StdDraw.setPenRadius(0.008);
		for(int i=0;i<x.length/2.0;i++){
			StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
			StdDraw.line(x[2*i],y[2*i],x[2*i+1],y[2*i+1]);
			x[2*i]-=5;
			x[2*i+1]-=5;
		}
	}
	
	public static int determineNext(double[] x,int n)
	{
		if(x[n]<=145&&x[n+1]>145){
			return n;
		}
		if(x[n]>=145){
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
		Cloud one=new Cloud("one",445,570,2);
		clouds.add(one);
		count+=one.getSize();
		Cloud two=new Cloud("two",160,415,3);
		clouds.add(two);
		count+=two.getSize();
		Cloud three=new Cloud("three",200,660,1);
		clouds.add(three);
		count+=three.getSize();
		Cloud four=new Cloud("four",650,325,1);
		clouds.add(four);
		count+=four.getSize();
		return count;
	}
	
	public static void drawClouds(){
		for(Cloud cloud: clouds){
			cloud.draw();
		}
	}
	public static int moveClouds(int count){
		for(Cloud cloud: clouds){
			cloud.move();
			if(cloud.getRight()<=0){
				count-=cloud.getSize();
			}
		}
		return count;
	}
	public static int newCloud(int count){
		for(int i=clouds.size()-1;i>=0;i--){
			if(clouds.get(i).getRight()<=0){
				int size=(int)(Math.random()*3+1);
				double bot=Math.random()*380+300;
				double center=700;
				String name=clouds.get(i).getNumber();
				clouds.remove(i);
				if(size==1){center+=25;}
				if(size==2){center+=50;}
				if(size==3){center+=75;}
				Cloud next=new Cloud(name,center,bot,size);
				clouds.add(next);
				count+=size;
			}
		}
		return count;
	}
	public static String newScore(double dist){
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledRectangle(350,675,250,175);
		char ones=48;
		char tens=48;
		char hunds=48;
		char thous=48;
		while(dist/1000.0>=1){
			thous++;
			dist-=1000;
		}
		while(dist/100.0>=1){
			hunds++;
			dist-=100;
		}
		while(dist/10.0>=1){
			tens++;
			dist-=10;
		}
		while(dist>=1){
			ones++;
			dist-=1;
		}
		String s=""+thous+hunds+tens+ones;
		return s;
	}
	
	public static String compareScore(String score,String high){
		for(int i=0;i<score.length();i++){
			if(score.charAt(i)>high.charAt(i)){
				return score;
			}
			if(score.charAt(i)<high.charAt(i)){
				return high;
			}
		}
		return score;
	}
	
	public static void drawScore(String score,String high){
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.text(350,650,"Score: "+score);
		StdDraw.setPenColor();
		StdDraw.text(350,675,"Current Run High Score: "+high);
	}
	
	public static void main(String args[]){
		StdDraw.setCanvasSize(700,700);
		StdDraw.setScale(0,700);
		platforms("0000");
	}
	
}
