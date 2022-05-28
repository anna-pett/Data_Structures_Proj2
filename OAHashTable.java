public abstract class OAHashTable implements IHashTable {
	
	private HashTableElement [] table;
	private static HashTableElement deleted = new HashTableElement(-1,0); 
	protected int m;
	protected ModHash hashFunc;
	
	public OAHashTable(int m) {
		this.m=m;
		this.table = new HashTableElement[m];
		// TODO add to constructor as needed
	}
	
	
	@Override
	public HashTableElement Find(long key) {
		for(int i=0; i<this.m;i++) {
			int index = this.Hash(key, i);
			if(this.table[index]==null)
				return null;
			if(this.table[index].GetKey()==key)
				return this.table[index];
		}
		return null;
	}
	
	@Override
	public void Insert(HashTableElement hte) throws TableIsFullException,KeyAlreadyExistsException {
		long key=hte.GetKey();
		int firstDeleted = -1;
		for(int i=0; i<this.m;i++) {
			int index = this.Hash(key, i);
			
			if(this.table[index]==deleted && firstDeleted==-1)//save first deleted index, if exists
				firstDeleted=index;
			if(this.table[index]==null && firstDeleted==-1){//first index empty, no deleted index before
				this.table[index]=hte;
				return;
			}
			if(this.table[index]==null && firstDeleted!=-1){//we found deleted before, and key doesnt exist in table
				this.table[firstDeleted]=hte;
				return;
			}
			if(this.table[index].GetKey()==hte.GetKey())
				throw new KeyAlreadyExistsException(hte);
		}
		if(firstDeleted!=-1)//we ended the sequence without finding key
			this.table[firstDeleted]=hte;
		else throw new TableIsFullException(hte);//no room for key in the sequence
	}
	
	@Override
	public void Delete(long key) throws KeyDoesntExistException {
		for(int i=0; i<this.m;i++) {
			int index = this.Hash(key, i);
			
			if(this.table[index]==null)
				throw new KeyDoesntExistException(key);
			if(this.table[index].GetKey()==key) {
				this.table[index]=deleted;
				return;
			}
		}
		throw new KeyDoesntExistException(key);
	}
	
	/**
	 * 
	 * @param x - the key to hash
	 * @param i - the index in the probing sequence
	 * @return the index into the hash table to place the key x
	 */
	public abstract int Hash(long x, int i);
}
