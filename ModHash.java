import java.util.Random;

public class ModHash {
	private long a;
	private long b;
	private final long p;
	private final int m;
	
	public ModHash(int m, long p) {
		this.m=m;
		this.p=p;
//		this.a=a;
//		this.b=b;
	}
	public static ModHash GetFunc(int m, long p){
		Random rand = new Random();
//		long a = rand.nextLong(p-1)+1;
//		long b = rand.nextLong(p);
		long a = rand.longs(1,1,p).iterator().next();
		long b = rand.longs(1,0,p).iterator().next();
		ModHash md = new ModHash(m,p);
		md.a = a;
		md.b = b;
		return  md;
	}
	
	public int Hash(long key) {
		return (int)(((this.a*key)+this.b)%(this.p))%(this.m);
	}
}
