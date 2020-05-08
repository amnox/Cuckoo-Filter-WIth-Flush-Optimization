package cuckoo;
import java.math.BigInteger;
import cuckoo.CuckooFilter;
import cuckoo.OCFUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class OCF {

  CuckooFilter mCuckooFilter;
  ArrayList<String> collection;
  String mode;
  float minOccupancy;
  float maxOccupancy;
  float mid;
  OCFUtils mOCFUtils;

  private void resetFilter(int capacity,int bucketSize, int fingerprintSize, int maxDisplacements, ArrayList<String> collection){
    this.mCuckooFilter = new CuckooFilter(capacity, bucketSize, fingerprintSize,maxDisplacements);
    for (String coll : collection) {
         mCuckooFilter.insert(coll);
    }
  }

  public boolean sanityCheck(){
    for(String loc:this.collection){
      if(mCuckooFilter.contains(loc)==false){
        return false;
      }
    }
    return true;
  }

  public OCF(CuckooFilter mCuckooFilter, float minOccupancy, float maxOccupancy, String mode){
    this.collection = new ArrayList<String>();
    this.minOccupancy = minOccupancy;
    this.maxOccupancy = maxOccupancy;
    this.mode = mode;
    this.mCuckooFilter = mCuckooFilter;

    if (this.mode.equals("EOF")){
      float mid = (this.maxOccupancy+this.minOccupancy)/2;
      this.mid = mid;
      float kMax = mid + (maxOccupancy - mid)/4;
      float kMin = mid - (mid - minOccupancy)/4;
      this.mOCFUtils = new OCFUtils(kMin, kMax);
    }
  }

  public boolean insert(String item){
    switch(this.mode)
    {
      case "PRE":
        return insertPRE(item);
      case "EOF":
        return insertEOF(item);
      default:
        return insertPRE(item);
    }
  }

  public boolean delete(String item){
    if(this.collection.contains(item)==false){
      return false;
    }
    switch(this.mode)
    {
      case "PRE":
        return deletePRE(item);
      case "EOF":
        return deleteEOF(item);
      default:
        return deletePRE(item);
    }
  }

  public boolean contains(String item){
    return this.mCuckooFilter.contains(item);
  }

  private boolean insertPRE(String item){
    if(this.mCuckooFilter.getOccupancy()>this.maxOccupancy){
      int capacity=this.mCuckooFilter.getCapacity();
      capacity+=capacity;
      this.collection.add(item);
      resetFilter(capacity, 4, 3,500,this.collection);
      return true;
    }


    if (this.mCuckooFilter.insert(item)){
      this.collection.add(item);
      return true;
    }
    return false;
  }

  public boolean deletePRE(String item){
    if(this.mCuckooFilter.getOccupancy()<this.minOccupancy){
      int capacity=this.mCuckooFilter.getCapacity();
      capacity=(int)(capacity - capacity/10);
      int deadEntry = this.collection.indexOf(item);
      this.collection.remove(deadEntry);
      resetFilter(capacity, 4, 3,500,this.collection);
      return true;
    }

    int deadEntry = this.collection.indexOf(item);
    this.collection.remove(deadEntry);

    return this.mCuckooFilter.delete(item);
  }

  public boolean insertEOF(String item){
    if(this.mCuckooFilter.getOccupancy()>this.maxOccupancy){
      int capacity=this.mCuckooFilter.getCapacity();
      //float mid = (this.maxOccupancy+this.minOccupancy)/2;
      capacity=(int)((float)capacity/(float)this.mid);
      this.collection.add(item);
      resetFilter(capacity, 4, 3,500,this.collection);
      return true;
    }

    if (this.mCuckooFilter.insert(item)){
      this.collection.add(item);
      return true;
    }
    return false;
  }

  public boolean deleteEOF(String item){
    if(this.mCuckooFilter.getOccupancy()<this.minOccupancy){
      int capacity=this.mCuckooFilter.getCapacity();
      float mid = (this.maxOccupancy+this.minOccupancy)/2;
      capacity=(int)((float)capacity/(float)(mid+1));
      int deadEntry = this.collection.indexOf(item);
      this.collection.remove(deadEntry);
      resetFilter(capacity, 4, 3,500,this.collection);
      return true;
    }

    int deadEntry = this.collection.indexOf(item);
    this.collection.remove(deadEntry);

    return this.mCuckooFilter.delete(item);
  }

}
