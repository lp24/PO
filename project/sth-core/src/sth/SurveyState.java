package sth;
import java.io.Serializable;
import sth.exceptions.SurveyException;

abstract class SurveyState implements Serializable{
    protected Survey _survey;
    
    SurveyState(Survey survey){
        _survey=survey;
    }
    
    abstract void cancel(Project p)throws SurveyException;
    
    abstract void open()throws SurveyException;
    
    abstract void close()throws SurveyException;
    
    abstract void end()throws SurveyException;
    
    abstract void submit(int hours, String comment)throws SurveyException;
    
    abstract String results(String user, int projSubmissionsNr);
}
    
