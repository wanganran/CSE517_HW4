package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by wanganran on 3/1/17.
 */
public class Output {
    public static void writeFile(File f, List<Sentence> sentences, List<Tag[]> tags) throws IOException{
        FileWriter writer=new FileWriter(f);
        for(int i=0;i<sentences.size();i++){
            Sentence s=sentences.get(i);
            for(int j=0;j<s.getWords().size()-1;j++) {
                Word w = s.getWords().get(j);
                writer.write(w.getWord() + "\t" + w.getPosTag().toString() + "\t" + w.getSCTag().toString() + "\t" + w.getNerTag().toString() + "\t" + tags.get(i)[j].toString());
            }
        }
        writer.close();
    }
}
