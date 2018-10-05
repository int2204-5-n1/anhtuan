import java.io.IOException;
import java.util.*;
public class DictionaryCommandline {
    void showAllWords(){
        int index = 1;
        String format = "%-3s%-19s%-19s";
        System.out.format(format,"No" , "Từ" ,"Nghĩa");
        System.out.println();
        for(Word word:DictionaryManagement.dict.dictionary){
            System.out.format(format,index, word.getWord_target(), word.getWord_explain());
            System.out.println();
            index++;
        }
    }

    void dictionaryBasic(){
        DictionaryManagement basic = new DictionaryManagement();
        basic.insertFromCommandline();
        showAllWords();
    }

    void dictionaryAdvanced(){
        DictionaryManagement advanced = new DictionaryManagement();
        advanced.insertFromFile();
        showAllWords();
        advanced.DictionaryLookup();
    }

    public static void main(String args[]){
        DictionaryCommandline run = new DictionaryCommandline();
        //run.dictionaryBasic();
        /*DictionaryManagement test = new DictionaryManagement();
        test.insertFromCommandline();
        test.insertFromFile();
        run.showAllWords();
        System.out.println(DictionaryManagement.dictsize);*/
        run.dictionaryAdvanced();
    }
}


