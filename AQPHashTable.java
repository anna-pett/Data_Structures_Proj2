import java.util.Random;

public class AQPHashTable extends OAHashTable {

	public AQPHashTable(int m, long p) {
		super(m);
		this.hashFunc = ModHash.GetFunc(m, p);
	}
	
	@Override
	public int Hash(long x, int i) {
		int hashVal = this.hashFunc.Hash(x);
		int sign = i%2==0? 1 : -1;
		int res = (hashVal + (sign * (i*i))) % (this.m);
		return res<0? res + this.m : res;
	}
}
