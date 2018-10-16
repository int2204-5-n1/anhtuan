import java.io.*;
import java.util.*;

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
            br.readLine();
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
                if( br != null)
                    br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void DictionaryLookup(){
        try {
            boolean found = false;
            Scanner inp = new Scanner(System.in);
            String target = "";
            while (!target.equals("-stop")) {
                System.out.println("Tra từ: ");
                target = inp.nextLine();
                for (Word word : dict.dictionary) {
                    if (word.getWord_target().equalsIgnoreCase(target)) {
                        System.out.println("Nghia cua tu: " + word.getWord_explain());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Not found");
                }
            }
            System.out.println("Stop DictionaryLookup");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void DeleteWord(){
        try{
            boolean found = false;
            System.out.println("-----DeleteWord-----");
            Scanner inp = new Scanner(System.in);
            String target = "";
            while(!target.equals("-stop")){
                System.out.println("Delete: ");
                target = inp.nextLine();
                for(Word word: dict.dictionary){
                    if(word.getWord_target().equalsIgnoreCase(target)){
                        dict.dictionary.remove(word);
                        found = true;
                        System.out.println("Successfully removed " + target);
                        break;
                    }
                }
                if(!found){
                    System.out.println("Not found.");
                }
            }
            System.out.println("Stop delete.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void AddWord(){
        try{
            boolean found = false;
            System.out.println("-----AddWord-----");
            Scanner inp = new Scanner(System.in);
            String target = "";
            while(!target.equals("-stop")) {
                System.out.println("Add: ");
                target = inp.nextLine();
                for (Word word : dict.dictionary) {
                    if (word.getWord_target().equalsIgnoreCase(target)) {
                        found = true;
                        System.out.println(target + "existed.");
                        break;
                    }
                }

                if (!found &&!target.equals("-stop")){
                    Word word = new Word();
                    word.setWord_target(target);
                    System.out.println("Nghĩa: ");
                    word.setWord_explain(inp.nextLine());
                    dict.dictionary.add(word);
                    System.out.println("Successfully added.");
                }
            }
            System.out.println("Stop delete.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void AdjustWord(){
        try{
            boolean found = false;
            System.out.println("-----AdjustWord-----");
            Scanner inp = new Scanner(System.in);
            String target = "";
            while(!target.equals("-stop")){
                System.out.println("Tu can sua: ");
                target = inp.nextLine();
                for(Word word: dict.dictionary){
                    if(word.getWord_target().equalsIgnoreCase(target)){
                        found = true;
                        System.out.println("Nghia moi: ");
                        String s = inp.nextLine();
                        word.setWord_explain(s);
                        break;
                    }
                }
                if(!found){
                    System.out.println("Not found.");
                }
            }
            System.out.println("Stop adjust.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void dictionaryExportToFile(){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
            fw = new FileWriter("C:\\Users\\OS\\Desktop\\dictionary.txt");
            bw = new BufferedWriter(fw);
            bw.write("dictionary");
            bw.newLine();
            for(Word word: dict.dictionary){
                bw.write(word.getWord_target()+"\t"+word.getWord_explain());
                bw.newLine();
            }
            System.out.println("Successfully exported.");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}

