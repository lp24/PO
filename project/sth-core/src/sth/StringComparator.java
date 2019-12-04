package sth;
import java.text.Collator;
import java.util.Locale;
import java.util.Comparator;
import java.io.Serializable;


class StringComparator implements Serializable, Comparator<String>{
    static final StringComparator INSTANCE = new StringComparator();
    
    private StringComparator(){}
    
    @Override
    public int compare(String s1,String s2){
        return Collator.getInstance(Locale.ENGLISH).compare(s1,s2);
    }
}
