public class Ball {
	private double x;
	private double y;
	private double radius;
	private BallStates state;
	private int deflateCounter;
	private int squish;
	private boolean squishing;
	
	public Ball(double x,double y,double radius){
		this.x=x;
		this.y=y;
		this.radius=radius;
		state=BallStates.Rolling;
		deflateCounter=0;
		squish=0;
		squishing=false;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRadius() {
		return radius;
	}

	public BallStates getState() {
		return state;
	}

	public int getDeflate() {
		return deflateCounter;
	}
	
	public int getSquish(){
		return squish;
	}
	
	public boolean isSquishing(){
		return squishing;
	}

	public void moveX(double distance){
		x+=distance;
	}
	
	public void moveY(double distance){
		y+=distance;
	}
	
	public void draw(double dist){
		StdDraw.setPenColor(StdDraw.RED);
		if(squish!=0){
			StdDraw.filledEllipse(x,y-squish,radius+squish,radius-squish);
			dist=(dist+105)*70;
			while(dist>=360){dist-=360;}
			dist=(360-dist)*Math.PI/180;
			StdDraw.setPenColor(StdDraw.ORANGE);
			StdDraw.filledEllipse(Math.cos(dist)*.6*(radius+squish)+x,Math.sin(dist)*.6*(radius-squish)+y-squish,1.5*(radius+squish)/10,1.5*(radius-squish)/10);
		}else if(deflateCounter==0){
			StdDraw.filledCircle(x,y,radius);
			dist=(dist+105)*70;
			while(dist>=360){dist-=360;}
			dist=(360-dist)*Math.PI/180;
			StdDraw.setPenColor(StdDraw.ORANGE);
			StdDraw.filledCircle(Math.cos(dist)*6+x,Math.sin(dist)*6+y,1.5);
		}else{
			StdDraw.filledEllipse(x,y,11-(deflateCounter),10+deflateCounter*.5);
		}
	}
	
	public void setState(BallStates state){
		this.state=state;
	}
	
	public void deflate(){
		deflateCounter++;
	}
	
	public void squish(){
		squish++;
		squishing=true;
	}
	
	public void unsquish(){
		squish--;
		if(squish<0){squish=0;}
		squishing=false;
	}
}
