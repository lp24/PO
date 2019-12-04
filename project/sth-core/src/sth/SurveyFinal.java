package sth;
 
import sth.exceptions.SurveyException;

class SurveyFinal extends SurveyState{
 
    SurveyFinal(Survey survey){
        super(survey);
    }
    
    void cancel(Project p)throws SurveyException{
        throw new SurveyException("SurveyFinished");
    }
    
    void open()throws SurveyException{
        throw new SurveyException("SurveyOpening");
    }
    
    void close()throws SurveyException{
        throw new SurveyException("SurveyClosing");
    }
    
    void end(){
    }
    
    void submit(int hours, String comment)throws SurveyException{
        throw new SurveyException("Survey");
    }
    
    String results(String user, int projSubmissionsNr){
        int media=_survey.getAverageHours();
        int nrRespostas=_survey.nrRespostas();
        
        if(user.equals("DOCENTE")){
            int max=_survey.getMaxHours();
            int min=_survey.getMinHours();
            return "\n * Número de submissões: "+projSubmissionsNr+
                   "\n * Número de respostas: " +nrRespostas+
                   "\n * Tempos de resolução (horas) (mínimo, médio, máximo): "+min+", "+media+", "+max;
        }
        
        else if(user.equals("DELEGADO")){
            return " - "+nrRespostas+" respostas - "+media+" horas";
        }
        
        else{ //ALUNO
            return "\n * Número de respostas: "+nrRespostas+
                   "\n * Tempo médio (horas): "+media;
        }
    }       
}
     
