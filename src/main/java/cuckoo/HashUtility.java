package cuckoo;
import java.math.BigInteger;
import java.util.Arrays;

public final class HashUtility{
  private static BigInteger FNV64_OFFSET_BASIS = new BigInteger("14695981039346656037");
  private BigInteger FNV64_PRIME = new BigInteger("1099511628211");
  private BigInteger MAX_64_INT = BigInteger.valueOf(2).pow(64);

  /*
    Generate FNV64 hash for data in bytes

    :param data: Data to generate FNV hash for
  */
  public static BigInteger fnv64(String data){
    byte[] byteArray = data.getBytes();
    int[] intArray = new int[byteArray.length];
    BigInteger FNV64_OFFSET_BASIS = new BigInteger("14695981039346656037");
    BigInteger FNV64_PRIME = new BigInteger("1099511628211");
    BigInteger MAX_64_INT = BigInteger.valueOf(2).pow(64);
    BigInteger h = FNV64_OFFSET_BASIS;
    for(int i =0; i<byteArray.length;i++){
      intArray[i] = byteArray[i];
    }
    for(int i =0; i<byteArray.length;i++){

      h = (h.multiply(FNV64_PRIME)).mod(MAX_64_INT);
      h = h.xor(BigInteger.valueOf(intArray[i]));
    }
    return h;
  }

  /*
    Compress the Key and get its fingerprint

    :param data: Data to generate FNV hash for
    :param size: Size of fingerprint
  */
  static BigInteger fingerprint(String data, int size){
    // Hashing data with FNV hash
    BigInteger bigInt = fnv64(data);

    // Compress the hash value to size
    byte b1[];
    b1 = bigInt.toByteArray();

    byte[] subarray = new byte[size ];
    System.arraycopy(b1, 0, subarray, 0, size);
    // Using the compressed hash, create the fingerprint
    BigInteger fingerprint = new BigInteger(subarray);
    return fingerprint;
  }

  /*
    Create HashCode of key with default java
  */
  static BigInteger createHash(String data){

    BigInteger hashCode = BigInteger.valueOf(data.hashCode());
    return hashCode.abs();
  }

}
