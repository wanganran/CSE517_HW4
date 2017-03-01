package com.company;

import java.util.List;

/**
 * Created by anranw on 2/28/17.
 */
public class Trainer {
    public static FeatureVec train(List<Sentence> allSentences, WordStat ws){
        final int IT=100;
        int it=0;
        int sid=0;
        HMM hmm=new HMM(ws);
        while(it<IT){
            Sentence s=allSentences.get(sid%allSentences.size());
            if(!hmm.iter(s))
                it++;
            sid++;
        }
        return hmm.getAverageWeight();
    }
    public static Tag[] predict(Sentence s, WordStat ws){
        Tag[] allTags=NERTag.getAllTags();
        HMM hmm=new HMM(ws);
        int[][] pre=hmm.hmm(s);
        Tag[] tags=new Tag[s.getWords().size()];

        tags[tags.length-1]=NERTag.getTag("END");
        for(int i=tags.length-2;i>=0;i--){
            tags[i]=allTags[pre[i+1][tags[i+1].getIndex()]];
        }
        return tags;
    }
}
