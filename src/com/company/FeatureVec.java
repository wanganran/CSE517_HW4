package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by anranw on 2/26/17.
 */
public class FeatureVec {
    private Map<Integer, Double> nonZeros=new HashMap<Integer, Double>();
    public FeatureVec(){
    }
    public void set(int i, double v){
        if(v==0)nonZeros.remove(i);
        else nonZeros.put(i, v);
    }
    public double get(int i){
        if(nonZeros.containsKey(i))return nonZeros.get(i);
        else return 0;
    }
    public Set<Integer> getNonZeros(){
        return nonZeros.keySet();
    }
    //operations
    public double mult(FeatureVec vec){
        Set<Integer> nz=vec.getNonZeros();
        double result=0;
        if(nz.size()<=nonZeros.size()) {
            for (int i : nz) {
                if(nonZeros.containsKey(i)){
                    result+=nonZeros.get(i)*vec.get(i);
                }
            }
            return result;
        }
        else{
            for(int i:nonZeros.keySet()){
                if(nz.contains(i))
                    result+=nonZeros.get(i)*vec.get(i);
            }
            return result;
        }
    }

    public FeatureVec add(FeatureVec vec){
        FeatureVec result=new FeatureVec();
        result.nonZeros=new HashMap<>(this.nonZeros);
        for(int i:vec.nonZeros.keySet())
            result.set(i, result.get(i)+vec.get(i));
        return result;
    }
    public FeatureVec minus(FeatureVec vec){
        FeatureVec result=new FeatureVec();
        result.nonZeros=new HashMap<>(this.nonZeros);
        for(int i:vec.nonZeros.keySet())
            result.set(i, result.get(i)-vec.get(i));
        return result;
    }
}
