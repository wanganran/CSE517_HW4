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
        return hmm.getWeight();
    }
    public static Tag[] predict(Sentence s, WordStat ws){
        HMM hmm=new HMM(ws);
        int[][] pre=hmm.hmm(s);
        Tag[] tags=new Tag[s.getWords().size()];

    }
}
