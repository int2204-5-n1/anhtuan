public class Word {
    private String word_target,word_explain;

    public String getWord_explain() {
        return word_explain;
    }

    public String getWord_target(){
        return word_target;
    }

    public void setWord_explain(String word_explain) {
        if(word_explain == null){
            this.word_explain = "";
        } else {
            this.word_explain = word_explain;
        }
    }

    public void setWord_target(String word_target) {
        if(word_target == null){
            this.word_target = "";
        }else{
            this.word_target = word_target;
        }
    }
}
