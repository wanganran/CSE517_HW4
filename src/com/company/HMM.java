package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anranw on 2/27/17.
 */
public class HMM {
    private FeatureVec w;
    private FeatureFactory factory;
    private Tag[] allTags;

    public HMM(WordStat ws){
        w=new FeatureVec();
        this.factory=new FeatureFactory(ws);
        allTags=NERTag.getAllTags();
    }

    public int[][] hmm(Sentence s){
        double[][] dp=new double[s.getWords().size()][allTags.length];
        int[][] pre=new int[s.getWords().size()][allTags.length];

        //init
        Tag BEGIN=NERTag.getTag("BEGIN");
        for(int i=0;i<allTags.length;i++)
        for(Tag t:allTags){
            dp[0][t.getIndex()]=w.mult(factory.getFeatures(s,0,(NERTag)t,(NERTag)BEGIN));
        }

        for(int i=1;i<s.getWords().size();i++){
            for(int j=0;j<allTags.length;j++){
                double max=Double.NEGATIVE_INFINITY;
                int p=0;
                for(int k=0;k<allTags.length;k++){
                    double r=dp[i-1][k]+w.mult(factory.getFeatures(s, i, (NERTag)(allTags[j]), (NERTag)(allTags[k])));
                    if(r>max){
                        max=r;
                        p=k;
                    }
                }
                pre[i][j]=p;
                dp[i][j]=max;
            }
        }
        return pre;
    }

    private FeatureVec getPhi(Sentence s, Tag[] tags){
        FeatureVec result=new FeatureVec();
        for(int i=0;i<s.getWords().size();i++){
            result=result.add(factory.getFeatures(s, i, (NERTag)tags[i],(NERTag)(i==0?NERTag.getTag("BEGIN"):(NERTag)tags[i-1])));
        }
        return result;
    }

    public boolean iter(Sentence s){
        int[][] prePredicted=hmm(s);
        Tag[] tagPredicted=new Tag[s.getWords().size()];
        tagPredicted[tagPredicted.length-1]=NERTag.getTag("END");
        for(int i=tagPredicted.length-2;i>=0;i--){
            tagPredicted[i]=allTags[prePredicted[i+1][tagPredicted[i+1].getIndex()]];
        }

        //check tag are the same
        boolean eq=true;
        Tag[] groundtruth=new Tag[s.getWords().size()];
        for(int i=0;i<s.getWords().size();i++) {
            Tag t = s.getWords().get(i).getNerTag();
            groundtruth[i]=t;
            if (!t.equals(tagPredicted[i])) {
                eq = false;
            }
        }

        //if not the same
        if(!eq){
            FeatureVec delta=getPhi(s, groundtruth).minus(getPhi(s, tagPredicted));
            w=w.add(delta);
            return false;
        }
        else
            return true;
    }
    public FeatureVec getWeight(){
        return w;
    }
}
