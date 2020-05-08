package cuckoo;

public class OCFUtils{
  float kMin;
  float kMax;
  float mid;
  float alpha;
  String sessionType = null;
  int oldCapacity;
  int oldTime;

  public OCFUtils(float kMin, float kMax){
    this.kMin = kMin;
    this.kMax = kMax;
  }

  public float getKMin(){
    return this.kMin;
  }

  public float getKMax(){
    return this.kMax;
  }

  public boolean startSession(String sessionType){
    if (this.sessionType==null){
      this.sessionType = sessionType;
      return true;
    }
    return false;
  }

}
