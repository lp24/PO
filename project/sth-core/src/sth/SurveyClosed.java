package sth;
import sth.exceptions.SurveyException;

class SurveyClosed extends SurveyState{
    SurveyClosed(Survey survey){
        super(survey);
    }
    
    void cancel(Project p){
        _survey.setState(new SurveyOpen(_survey));
    }
    
    void open(){
        _survey.setState(new SurveyOpen(_survey));
    }
    
    void close(){
    }
    
    void end(){
        _survey.setState(new SurveyFinal(_survey));
    }
    
    void submit(int hours, String comment)throws SurveyException{
        throw new SurveyException("Survey");
    }
    
    String results(String user, int projSubmissionsNr){
        return " (fechado)";
    }
}
    
