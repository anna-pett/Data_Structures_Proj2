import java.util.Random;

public class DoubleHashTable extends OAHashTable {
	
	private ModHash doubleHash;
	
	public DoubleHashTable(int m, long p) {
		super(m);
		this.hashFunc = ModHash.GetFunc(m, p);
		this.doubleHash = ModHash.GetFunc(m, p);
		
	}
	
	@Override
	public int Hash(long x, int i) {
		int firstHashVal = this.hashFunc.Hash(x);
		int secHashVal = this.doubleHash.Hash(x);
		return (firstHashVal + (i * secHashVal)) % (this.m);
	}
	
}
