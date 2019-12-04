package sth;
import java.io.Serializable;
import java.util.ArrayList;
import sth.exceptions.SurveyException;

class Survey implements Serializable{
    private ArrayList<SurveyAnswer> _answers = new ArrayList<SurveyAnswer>();
    private SurveyState _state = new SurveyCreated(this);
    private int _minSurveyHours;
    private int _maxSurveyHours;
    
    private class SurveyAnswer implements Serializable{
        private int _hours;
        private String _comment;
    
        SurveyAnswer(int hours,String comment){
            _hours=hours;
            _comment=comment;
        }
    
        private int getHours(){
            return _hours;
        }
        
        private String getComment(){
            return _comment;
        }
    } 
    
    boolean noAnswers(){
        return _answers.isEmpty();
    }
    
    void addAnswer(int hours, String comment){
        if(noAnswers()){
            _maxSurveyHours=hours;
            _minSurveyHours=hours;
        }
        else{
            if(hours>_maxSurveyHours){
                _maxSurveyHours=hours;
            }
            if(hours<_minSurveyHours){
                _minSurveyHours=hours;
            }
        }  
        _answers.add(new SurveyAnswer(hours,comment));

    }
    
    int getMinHours(){
        return _minSurveyHours;
    }
    int getMaxHours(){
        return _maxSurveyHours;
    }
    int getAverageHours(){
        if(noAnswers()){
            return 0;
        }
        int soma=0;
        for (SurveyAnswer a:_answers){
            soma+=a.getHours();
        }
        return soma/nrRespostas();
    }
    int nrRespostas(){
        return _answers.size();
    }
        
    //SURVEY    
    void setState(SurveyState state){
        _state=state;
    }   
    
    void cancelSurvey(Project p)throws SurveyException{_state.cancel (p);}
    void openSurvey  ()throws SurveyException{_state.open   ();}
    void closeSurvey ()throws SurveyException{_state.close  ();}
    void endSurvey   ()throws SurveyException{_state.end    ();}    
    void submitSurvey(int hours, String comment)throws SurveyException{ _state.submit(hours,comment) ;}    
    String getSurveyResults(String user, int projSubmissionsNr){return _state.results(user, projSubmissionsNr);}
}

