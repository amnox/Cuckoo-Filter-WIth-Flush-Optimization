package cuckoo;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

/*
  Bucket class: representing a single bucket of the filter
*/
public class Bucket{
    int[] bucket;
    int size;
    int pointer;

    public Bucket(int size){
      this.pointer = 0;
      this.size = size;
      this.bucket = new int[size];
    }

    /*
      Print the bucket
    */
    public void printBucket(){
      for (int i = 0; i<this.bucket.length;i++){
        System.out.print(this.bucket[i]);
        System.out.print(", ");
      }
      System.out.println("");

    }

    /*
      Check if the bucket contains specified items
      :param item: Integer value of the item to check
    */
    public boolean contains(int item) {
      for (int i = 0; i<this.bucket.length;i++){
        if(this.bucket[i]==item){
          return true;
        }
      }
      return false;
    }
    public int getItemIndex(int item) {
      for (int i = 0; i<this.bucket.length;i++){
        if(this.bucket[i]==item){
          return i;
        }
      }
      return -1;
    }

    /*
      Try to insert an item
      :param item: Integer value of the item to check
    */
    public boolean insert(int item){
      if(this.pointer >= this.size){
        return false;
      }

      this.bucket[this.pointer] = item;
      ++this.pointer;
      return true;
    }

    /*
      Try to delete an item
      :param item: Integer value of the item to check
    */
    public boolean delete(int item){


      if (this.contains(item)){
        this.bucket[this.getItemIndex(item)]=0;
        --this.pointer;
        return true;
      } else{
        return false;
      }
    }

    /*
      Swap a random entry from the bucket with incoming value
      :param item: Integer value of the item to check
    */
    public int swap(int item){
      Random random = new Random();
      int index = random.nextInt(this.bucket.length);
      int swapped = this.bucket[index];
      this.bucket[index] = item;

      return swapped;
    }
}
