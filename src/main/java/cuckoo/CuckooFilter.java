package cuckoo;
import java.math.BigInteger;
import cuckoo.Bucket;
import cuckoo.HashUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CuckooFilter {
  int capacity;
  int bucketSize;
  int fingerprintSize;
  int maxDisplacements;
  int size;
  List<Bucket> buckets;
  public static int BigIntegerToInt(BigInteger mBigInteger){
    return mBigInteger.intValue();
  }

  public void printBuckets(){
    for (Bucket bucket : this.buckets) {
         bucket.printBucket();  //prints all buckets
    }
  }

  /*
    Initialize the Filter
    :param capacity: Size of Cuckoo Filter
    :param bucketSize: Number of entries in an individual bucket
    :param fingerprintSize: Fingerprint Size
    :param maxDisplacements: Maximum number of evictions before filter is considered full
  */
  public CuckooFilter(int capacity, int bucketSize, int fingerprintSize, int maxDisplacements){
    this.capacity = capacity;
    this.bucketSize = bucketSize;
    this.fingerprintSize = fingerprintSize;
    this.maxDisplacements = maxDisplacements;
    this.size = 0;
    this.buckets =new ArrayList<Bucket>(this.capacity);
    for(int i = 0; i<this.capacity;i++){
      Bucket mBucket = new Bucket(this.bucketSize);
      this.buckets.add(mBucket);
    }

    /*for (Bucket bucket : this.buckets) {
         bucket.printBucket();  //prints all buckets
    }*/
  }

  private int getIndex(String item){
    BigInteger hashCode = HashUtility.createHash(item);
    BigInteger mod = BigInteger.valueOf(this.capacity);
    return this.BigIntegerToInt(hashCode.mod(mod));
  }

  /*
    Get alternate index in b2 with fingerprintSize

    :param index: item's first index, with h1.
    :param fingerprint: Item's fingerprint.

  */
  private int getAlternateIndex(int index, BigInteger fingerprint){
    BigInteger hashCode = HashUtility.createHash(fingerprint.toString());
    BigInteger mod = BigInteger.valueOf(this.capacity);
    BigInteger bigIndex = BigInteger.valueOf(index);
    BigInteger mAlternateIndex = (bigIndex.xor(hashCode)).mod(mod);
    return BigIntegerToInt(mAlternateIndex);
    /*
      alt_index = (index ^ hashutils.hash_code(fingerprint)) % self.capacity
      return alt_index
    */

  }

  /*
    Try to insert an intem into the Filter
    Return false if cycle detected

    :params item: The key to be inserted
  */
  public boolean insert(String item){
    BigInteger fingerprint = HashUtility.fingerprint(item, this.fingerprintSize);
    int i = this.getIndex(item);
    int j = this.getAlternateIndex(i, fingerprint);
    int intFinger = this.BigIntegerToInt(fingerprint);
    if(this.buckets.get(i).insert(intFinger) |  this.buckets.get(j).insert(intFinger)){
      ++this.size;
      return true;
    }
    Random random = new Random();
    int evictionIndex = random.nextBoolean() ? i : j;
    for (int k = 0; k<=this.maxDisplacements;k++){
      int f = this.buckets.get(evictionIndex).swap(intFinger);
      evictionIndex = getAlternateIndex(evictionIndex,BigInteger.valueOf(f));
      if(this.buckets.get(evictionIndex).insert(f)){
        ++this.size;
        return true;
      }
    }
    System.out.println(item);
    return false;
  }

  /*
    Check if the filter contains the item.

    :param item: Item to check its presence in the filter.
  */
  public boolean contains(String item){
    BigInteger fingerprint = HashUtility.fingerprint(item, this.fingerprintSize);
    int i = this.getIndex(item);
    int j = this.getAlternateIndex(i, fingerprint);
    int intFinger = this.BigIntegerToInt(fingerprint);
    return this.buckets.get(i).contains(intFinger) | this.buckets.get(j).contains(intFinger);
  }

  /*
    Delete an item from the filter.

    To delete an item safely, it must have been previously inserted.
    Otherwise, deleting a non-inserted item might unintentionally remove
    a real, different item that happens to share the same fingerprint.

    :param item: Item to delete from the filter.
  */
  public boolean delete(String item){
    BigInteger fingerprint = HashUtility.fingerprint(item, this.fingerprintSize);
    int i = this.getIndex(item);
    int j = this.getAlternateIndex(i, fingerprint);
    int intFinger = this.BigIntegerToInt(fingerprint);
    if (this.buckets.get(i).delete(intFinger) | this.buckets.get(j).delete(intFinger)){
      --this.size;
      return true;
    }
    return false;
  }
}
