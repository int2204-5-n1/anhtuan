import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class DictionaryManagement {
    public static Dictionary dict = new Dictionary();
    public static int dictsize=0;
    void insertFromCommandline(){
        try {
            Scanner inp = new Scanner(System.in);
            System.out.println("So luong tu vung: ");
            dictsize = inp.nextInt();
            inp.nextLine();
            for (int i = 0; i < dictsize; i++) {
                String s ;
                Word word = new Word();
                System.out.println("Tu moi");
                s = inp.nextLine();
                word.setWord_target(s);
                System.out.println("Nghia");
                s = inp.nextLine();
                word.setWord_explain(s);
                dict.dictionary.add(word);
                System.out.println("Successful word");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    void insertFromFile() {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader("C:\\Users\\OS\\Desktop\\dictionary.txt"));
            String s = br.readLine();
            while(s != null){
                Word word = new Word();
                word.setWord_target(s.split("\t")[0]);
                word.setWord_explain(s.split("\t")[1]);
                dict.dictionary.add(word);
                s = br.readLine();
                dictsize++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void DictionaryLookup(){
        Scanner inp = new Scanner(System.in);
        String target = new String();
         {
            System.out.println("Tra từ: ");
            target = inp.nextLine();
            for (Word word : dict.dictionary) {
                if (word.getWord_target().equalsIgnoreCase(target)) {
                    System.out.println("Nghia cua tu: " + word.getWord_explain());
                    return;
                }
            }
        }
        System.out.println("Not found");
    }

}

