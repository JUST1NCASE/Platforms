import java.util.ArrayList;

public class Mountain {
	private double[] mtnX={700,780,790,835,847,855,863,875,882,895,915,930,938,948,1060};
	private double[] mtnY={-10,335,305,510,450,465,435,480,465,510,400,440,420,440,-10};
	private double[] snow1X={835,847,855,863,875,882,895,865};
	private double[] snow1Y={510,450,465,435,480,465,510,647};
	private double[] snow2X={930,938,948,939};
	private double[] snow2Y={440,420,440,460};
	
	private String number;
	
	public Mountain(String number){
		this.number=number;
	}
	
	public String getNumber(){
		return number;
	}
	
	public double getRight(){
		return mtnX[14];
	}
	
	public void draw(){
		StdDraw.setPenRadius();
		StdDraw.setPenColor();
		StdDraw.polygon(mtnX,mtnY);
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.filledPolygon(mtnX,mtnY);
		StdDraw.polygon(snow1X,snow1Y);
		StdDraw.polygon(snow2X,snow2Y);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledPolygon(snow1X,snow1Y);
		StdDraw.filledPolygon(snow2X,snow2Y);
	}
	
	public void move(double speed){
		for(int i=0;i<mtnX.length;i++){
			mtnX[i]-=1+speed;
			if(i<snow1X.length){
				snow1X[i]-=1+speed;
			}
			if(i<snow2X.length){
				snow2X[i]-=1+speed;
			}
		}
	}
	
	public static void main(String[] args){
		ArrayList<Mountain> mountains=new ArrayList<Mountain>();
		StdDraw.setCanvasSize(700,700);
		StdDraw.setScale(0,700);
		StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
		Mountain p=new Mountain("pp");
		mountains.add(p);
		int count=0;
		while(count>=0){
			if(p.getRight()==1010){
				Mountain q=new Mountain("qq");
				mountains.add(q);
			}
			for(Mountain mountain: mountains){
				mountain.draw();
			}
			StdDraw.show(15);
			StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
			for(Mountain mountain: mountains){
				mountain.move(0);
			}
			count+=.15;
		}
	}
}
