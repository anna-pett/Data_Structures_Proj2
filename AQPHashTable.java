import java.util.Random;

public class AQPHashTable extends OAHashTable {

	public AQPHashTable(int m, long p) {
		super(m);
		this.hashFunc = ModHash.GetFunc(m, p);
	}
	
	@Override
	public int Hash(long x, int i) {
		int hashVal = this.hashFunc.Hash(x);
		int sign = (int) Math.pow(-1,i);
		return Math.floorMod(hashVal+(sign*(i*i)),(this.m));
	}
}
