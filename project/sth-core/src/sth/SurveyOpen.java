package sth;
import sth.exceptions.SurveyException;
 
class SurveyOpen extends SurveyState{
 
    SurveyOpen(Survey survey){
        super(survey);
    }
    
    void cancel(Project p)throws SurveyException{
        if(_survey.noAnswers()){
            p.removeSurvey();
        }
        else{
            throw new SurveyException("SurveyNotEmpty");
        }        
    }
    
    void open()throws SurveyException{
        throw new SurveyException("SurveyOpening");
    }
    
    void close(){
        _survey.setState(new SurveyClosed(_survey));
    }
    
     void end()throws SurveyException{
        throw new SurveyException("SurveyFinishing");
    }
    
    void submit(int hours, String comment){
        _survey.addAnswer(hours,comment);
    }
    
    String results(String user, int projSubmissionsNr){
        return " (aberto)";
    }
}
    
