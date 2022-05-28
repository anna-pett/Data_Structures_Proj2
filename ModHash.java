import java.util.Random;

public class ModHash {
	private long a;
	private long b;
	private long p;
	private int m;
	
	public ModHash(int m, long p, long a, long b) {
		this.m=m;
		this.p=p;
		this.a=a;
		this.b=b;
	}
	public static ModHash GetFunc(int m, long p){
		Random rand = new Random(); 
		long a = rand.nextLong(p-1)+1;
		long b = rand.nextLong(p);
		return new ModHash(m,p,a,b);
	}
	
	public int Hash(long key) {
		return (int)(((this.a*key)+this.b)%(this.p))%(this.m);
	}
}
