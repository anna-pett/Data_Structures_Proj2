import java.util.Random;

public class ModHash {
	private long a;
	private long b;
	private final long p;
	private final int m;
	
	public ModHash(int m, long p) {
		this.m = m;
		this.p = p;
	}
	public static ModHash GetFunc(int m, long p){
		Random rand = new Random();
		long a = rand.longs(1,1,p).iterator().next();
		long b = rand.longs(1,0,p).iterator().next();
		ModHash md = new ModHash(m,p);
		md.a = a;
		md.b = b;
		return  md;
	}
	
	public int Hash(long key) {
		long res = (long)this.a * key;
		res += (long)this.b;
//		if (res < 0)
//			System.out.println("BLEEP " + res);
		res %= this.p;
		res %= (long)this.m;
//		long tmp = (((this.a * key) + this.b) % (this.p)) % ((long)this.m);
//		int res = (int) tmp;
//		if (res < 0)
//			System.out.println("BLEEP " + res);
		return (int) res;
	}
}
