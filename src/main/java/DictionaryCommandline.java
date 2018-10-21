import java.io.IOException;
import java.util.*;

public class DictionaryCommandline {
    void showAllWords(){
        int index = 1;
        String format = "%-3s%-19s%-19s";
        System.out.format(format,"No" , "Từ" ,"Nghĩa");
        System.out.println();
        for(Word word:DictionaryManagement.dict.dictionary){
            System.out.format(format,index, word, word.getWord_explain());
            System.out.println();
            index++;
        }
    }

    void dictionaryBasic(){
        System.out.println("------Dictionary Basic---------");
        DictionaryManagement basic = new DictionaryManagement();
        basic.insertFromCommandline();
        showAllWords();
    }

    void dictionaryAdvanced(){
        System.out.println("---------Dictionary Advanced---------");
        DictionaryManagement advanced = new DictionaryManagement();
        advanced.insertFromFile();
        showCommands();
        Scanner inp = new Scanner(System.in);
        String ctrl = "";
        while(!ctrl.equals("-end")){
            ctrl = inp.nextLine();
            if(ctrl.equals("-show"))
                showAllWords();
            if(ctrl.equals("-search"))
                dictionarySearcher();
            if(ctrl.equals("-look"))
                advanced.DictionaryLookup();
            if(ctrl.equals("-export"))
                advanced.dictionaryExportToFile();
            if(ctrl.equals("-del"))
                advanced.DeleteWord();
            if(ctrl.equals("-add"))
                advanced.AddWord();
            if(ctrl.equals("-adj"))
                advanced.AdjustWord();
        }
    }

    void dictionarySearcher(){
        boolean found = false;
        Scanner inp = new Scanner(System.in);
        String target = "";
        while(!target.equals("-stop")) {
            System.out.println();
            System.out.println("Tra từ: ");
            target = inp.nextLine();
            System.out.println("Tu bat dau voi " + target +": ");
            for (Word word : DictionaryManagement.dict.dictionary) {
                if (word.getWord_target().startsWith(target)) {
                    found = true;
                    String format = "%-19s%-19s";
                    System.out.format(format, word.getWord_target(), word.getWord_explain());
                    System.out.println();
                    break;
                }
            }
            if (!found) {
                System.out.println("Not found.");
            }
        }
        System.out.println("Stop DictionarySearcher.");
    }

    void showCommands(){
        System.out.println("-show: Show all words.");
        System.out.println("-look: Dictionary Lookup.");
        System.out.println("-search:Dictionary Searcher");
        System.out.println("-stop:Stop looking/searching");
        System.out.println("-adj: Adjust words");
        System.out.println("-add: Add words.");
        System.out.println("-del: Delete words");
        System.out.println("-end: Exit.");
    }

}


