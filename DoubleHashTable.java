import java.util.Random;

public class DoubleHashTable extends OAHashTable {
	
	private final ModHash doubleHash;
	
	public DoubleHashTable(int m, long p) {
		super(m);
		this.hashFunc = ModHash.GetFunc(m, p);
		this.doubleHash = ModHash.GetFunc(m-1, p);
		
	}
	
	@Override
	public int Hash(long x, int i) {
		long firstHashVal = this.hashFunc.Hash(x);
		long secHashVal = this.doubleHash.Hash(x) + 1;		// make sure it never returns 0
		long res = (firstHashVal + (i * secHashVal));
		res %= (this.m);
//		if (res < 0)
//			System.out.println("BLEEP-BLEEP " + firstHashVal + " " + i + " " + secHashVal + " " + (firstHashVal + (i * secHashVal)) + " " + res);
		return (int) res;
	}
	
}
