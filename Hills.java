public class Hills {
	//create arrays for hill/background hill centers and radii
	private static double[] hill=new double[16];
	private static double[] rad=new double[16];
	private static double[] backhill=new double[16];
	private static double[] backrad=new double[16];
	
	public static void initialize(){
		hill[0]=70; hill[1]=140; hill[2]=260;
		hill[3]=320; hill[4]=365; hill[5]=420;
		hill[6]=555; hill[7]=630; hill[8]=710;
		hill[9]=780; hill[10]=835; hill[11]=950;
		hill[12]=1092; hill[13]=1147; hill[14]=1250;
		hill[15]=1385;
		rad[0]=80;
		for(int i=1;i<hill.length;i++)
			rad[i]=hill[i]-hill[i-1]+32;
		for(int i=0;i<hill.length;i++){
			backhill[i]=hill[i]+25;
			backrad[i]=rad[i]+145;
		}
	}
	
	public static void drawHill(){
		StdDraw.setPenColor(StdDraw.GREEN);
		for(int i=0;i<hill.length;i++)
			StdDraw.filledCircle(hill[i],0,rad[i]);
	}
	
	public static void newHill(){
		int l=0;//determines the furthest right hill
		for(int i=0;i<hill.length;i++){
			if(hill[i]>=hill[l]){
				l=i;
			}
		}
		int n=l+1;//n indicates next hill
		if(hill[l]+1/2.0*(rad[l])<800){
			if(n>hill.length-1){
				n-=hill.length;
			}
			hill[n]=800;
		}
	}
	
	public static void drawBackHill(){
		StdDraw.setPenColor(100,255,40);
		for(int i=0;i<backhill.length;i++)
			StdDraw.filledCircle(backhill[i],0,backrad[i]);
	}
	
	public static void newBackHill(){
		int l=0;
		for(int i=0;i<backhill.length;i++){
			if(backhill[i]>=backhill[l]){
				l=i;
			}
		}
		if(backhill[l]+1/2.0*backrad[l]<900){
			int n=l+1;
			if(n>backhill.length-1){n-=backhill.length;}
			backhill[n]=900;
		}
	}
	
	public static void moveHills(){
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		for(int i=0;i<backhill.length;i++){
			StdDraw.filledCircle(backhill[i],0,backrad[i]+1);
			backhill[i]-=3;
		}
		for(int i=0;i<hill.length;i++){
			StdDraw.filledCircle(hill[i],0,rad[i]+1);
			hill[i]-=5;
		}
	}
}
