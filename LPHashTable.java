import java.util.Random;

public class LPHashTable extends OAHashTable {

	public LPHashTable(int m, long p) {
		super(m);
		this.hashFunc = ModHash.GetFunc(m, p);
	}
	
	@Override
	public int Hash(long x, int i) {
		int hashVal = this.hashFunc.Hash(x);
		return (hashVal + i) % (this.m);
	}
	
}
