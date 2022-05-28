import java.util.Random;

public class QPHashTable extends OAHashTable {

	public QPHashTable(int m, long p) {
		super(m);
		this.hashFunc = ModHash.GetFunc(m, p);
	}
	
	@Override
	public int Hash(long x, int i) {
		int hashVal = this.hashFunc.Hash(x);
		return (hashVal+(i*i))%(this.m);
	}
}
