package sth;
import sth.exceptions.SurveyException;
 
class SurveyCreated extends SurveyState{
    SurveyCreated(Survey survey){
        super(survey);
    }
    
    void cancel(Project p){
        p.removeSurvey();
    }
    
    void open()throws SurveyException{
        _survey.setState(new SurveyOpen(_survey));
    }
    
    void close()throws SurveyException{
        throw new SurveyException("SurveyClosing");
    }
    
     void end()throws SurveyException{
        throw new SurveyException("SurveyFinishing");
    }
    
    void submit(int hours, String comment)throws SurveyException{
        throw new SurveyException("Survey");
    }
    
    String results(String user, int projSubmissionsNr){
        return " (por abrir)";
    }

}
    
