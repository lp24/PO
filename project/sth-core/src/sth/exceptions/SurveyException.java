package sth.exceptions;

public class SurveyException extends Exception{
    String _cause;
    public SurveyException(String s) {
        _cause=s;
    }
    public String getEntry(){
        return _cause;
    }
}  
 
