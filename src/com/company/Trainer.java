package com.company;

import java.util.List;

/**
 * Created by anranw on 2/28/17.
 */
public class Trainer {
    private static FeatureVec omit;
    public static FeatureVec train(List<Sentence> allSentences, WordStat ws){
        final int IT=20;
        int it=0;
        int sid=0;
        HMM hmm=new HMM(ws);
        //omit=Trainer.getOmit(allSentences, ws, 3);
        //hmm.setOmit(omit);

        while(sid<allSentences.size()*IT){
            Sentence s=allSentences.get(sid%allSentences.size());
            if(!hmm.iter(s)) {
                it++;
                System.out.println("Iter: "+it+", Epoch: "+(sid/(double)allSentences.size())+", W size: "+hmm.getCurrentWeight().getNonZeros().size());
            }
            sid++;
        }
        return hmm.getAverageWeight();
    }
    public static Tag[] predict(Sentence s, WordStat ws, FeatureVec w){
        Tag[] allTags=NERTag.getAllTags();
        HMM hmm=new HMM(ws);
        hmm.setW(w);
        //hmm.setOmit(omit);
        int[][] pre=hmm.hmm(s);
        Tag[] tags=new Tag[s.getWords().size()];

        tags[tags.length-1]=NERTag.getTag("END");
        for(int i=tags.length-2;i>=0;i--){
            tags[i]=allTags[pre[i+1][tags[i+1].getIndex()]];
        }
        return tags;
    }
    public static FeatureVec getOmit(List<Sentence> sentences, WordStat ws, int Thres) {
        FeatureFactory featureFactory = new FeatureFactory(ws);
        FeatureVec total = new FeatureVec();
        for (Sentence s : sentences) {
            for (int i = 0; i < s.getWords().size(); i++)
                total = total.add(featureFactory.getFeatures(s, i, i == 0 ? (NERTag) NERTag.getTag("BEGIN") : s.getWords().get(i - 1).getNerTag(), s.getWords().get(i).getNerTag()));
        }

        FeatureVec result = new FeatureVec();
        for (int i : total.getNonZeros()) {
            if (total.get(i) > Thres)
                result.set(i, 1);
        }
        return result;
    }
}
