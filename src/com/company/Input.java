package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by anranw on 2/26/17.
 */
public class Input {
    private WordStat ws=new WordStat();
    public WordStat getWordStat(){
        return ws;
    }
    public List<Sentence> readFile(File f) throws IOException{
        Scanner scanner=new Scanner(f);
        List<Sentence> result=new ArrayList<Sentence>();
        List<Word> currentSentence=new ArrayList<Word>();
        while(scanner.hasNextLine()){
            String line=scanner.nextLine();
            if(line.trim().equals("")){
                //add END
                Word w=new Word("!!!END!!!", (POSTag)(POSTag.getTag("END")), (SCTag)(SCTag.getTag("END")), (NERTag)(NERTag.getTag("END")));
                currentSentence.add(w);
                ws.add(w);
                //next sentence
                result.add(new Sentence(currentSentence));
                currentSentence=new ArrayList<Word>();
            }
            else{
                String[] wds=line.split("(\\t| )+");
                assert(wds.length==4);
                Word w=new Word(wds[0], (POSTag)(POSTag.getTag(wds[1])), (SCTag)(SCTag.getTag(wds[2])), (NERTag)(NERTag.getTag(wds[3])));
                ws.add(w);
                currentSentence.add(w);
            }
        }
        if(!currentSentence.isEmpty()){
            result.add(new Sentence(currentSentence));
        }
        //add BEGIN to NERTag
        NERTag.getTag("BEGIN");
        return result;
    }
}
