public class Cloud{
	/**Identifier number*/
	private String number;
	/**X coordinate of the center*/
	private double centerX;
	/**Y coordinate of the flat bottom*/
	private double bottomY;
	/**Highest Y value of the cloud*/
	private double topPoint;
	/**Highest X value of the cloud*/
	private double rightPoint;
	/**How large the cloud is*/
	private int size;
	/**Speed of the cloud*/
	private int speed;
	/**Creates a cloud labeled by number, with center (x,y) of either size 1, 2, or 3 from smallest to largest.*/
	public Cloud(String number,double centerX,double bottomY,int size){
		this.number=number;
		this.centerX=centerX;
		this.bottomY=bottomY;
		this.size=size;
		if(size<1){size=1;}
		if(size>3){size=3;}
		if(size==1){
			topPoint=bottomY+15;
			rightPoint=centerX+25;
			speed=8;
		}
		if(size==2){
			topPoint=bottomY+30;
			rightPoint=centerX+50;
			speed=4;
		}
		if(size==3){
			topPoint=bottomY+45;
			rightPoint=centerX+75;
			speed=2;
		}
	}
	public String getNumber(){
		return number;
	}
	public double getCenter(){
		return centerX;
	}
	public double getBottom(){
		return bottomY;
	}
	public double getRight(){
		return rightPoint;
	}
	public double getTop(){
		return topPoint;
	}
	public int getSize(){
		return size;
	}
	public void draw(){
		StdDraw.setPenColor(StdDraw.WHITE);
		if(size==1){
			filledHalfCircle(centerX-16,bottomY,9);
			filledHalfCircle(centerX-3,bottomY,15);
			filledHalfCircle(centerX+13,bottomY,8);
			filledHalfCircle(centerX+22,bottomY,3);
		}
		if(size==2){
			filledHalfCircle(centerX-32,bottomY,18);
			filledHalfCircle(centerX-6,bottomY,30);
			filledHalfCircle(centerX+26,bottomY,16);
			filledHalfCircle(centerX+44,bottomY,6);
		}
		if(size==3){
			filledHalfCircle(centerX-48,bottomY,27);
			filledHalfCircle(centerX-9,bottomY,45);
			filledHalfCircle(centerX+39,bottomY,24);
			filledHalfCircle(centerX+66,bottomY,9);
		}
	}
	
	public void move(){
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		if(size==1){
			filledHalfCircle(centerX-16,bottomY,10);
			filledHalfCircle(centerX-3,bottomY,16);
			filledHalfCircle(centerX+13,bottomY,9);
			filledHalfCircle(centerX+22,bottomY-1,5);
		}
		if(size==2){
			filledHalfCircle(centerX-32,bottomY,19);
			filledHalfCircle(centerX-6,bottomY,31);
			filledHalfCircle(centerX+26,bottomY,17);
			filledHalfCircle(centerX+44,bottomY-1,8);
		}
		if(size==3){
			filledHalfCircle(centerX-48,bottomY,28);
			filledHalfCircle(centerX-9,bottomY,46);
			filledHalfCircle(centerX+39,bottomY,25);
			filledHalfCircle(centerX+66,bottomY-1,11);
		}
		double diff=rightPoint-centerX;
		centerX-=speed;
		rightPoint=centerX+diff;
	}
	/**Draws a half circle, at specified center, of specified radius, from 0 to 180 degrees*/
	public static void filledHalfCircle(double x,double y,double radius){
		double[] xs=new double[19];
		double[] ys=new double[19];
		for(int i=0;i<19;i++){
			xs[i]=Math.cos(Math.PI*i*10/180)*radius+x;
			ys[i]=Math.sin(Math.PI*i*10/180)*radius+y;
		}
		StdDraw.filledPolygon(xs,ys);
	}
	/**Draws a half circle starting at specified center, of specified radius, at specified angle*/
	public static void filledHalfCircle(double x,double y,double radius,double angle){
		double[] xs=new double[19];
		double[] ys=new double[19];
		for(int i=0;i<19;i++){
			xs[i]=Math.cos(Math.PI*(i*10+angle)/180)*radius+x;
			ys[i]=Math.sin(Math.PI*(i*10+angle)/180)*radius+y;
		}
		StdDraw.filledPolygon(xs,ys);
	}
	
	public static void main(String args[]){
		Cloud one=new Cloud("one",700,300,1);
		Cloud two=new Cloud("two",700,400,2);
		Cloud three=new Cloud("three",700,200,3);
		StdDraw.setCanvasSize(600,600);
		StdDraw.setScale(0,600);
		StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.setPenColor();
		filledHalfCircle(100,200,50);
		filledHalfCircle(100,100,50,25);
		one.draw();
		two.draw();
		three.draw();
		StdDraw.show(15);
		while(true){
			one.draw();
			two.draw();
			three.draw();
			one.move();
			two.move();
			three.move();
			one.draw();
			two.draw();
			three.draw();
			StdDraw.show(15);
		}
	}
}
