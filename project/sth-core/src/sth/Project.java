package sth;

import java.io.Serializable;

import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map;

import sth.exceptions.SurveyException;
import sth.exceptions.ProjectException;



class Project implements Serializable{

    private String  _name;
    private String  _description;
    private boolean _open = true;
    private Survey  _survey = null;
    private TreeMap<Integer, String> _submissions = new TreeMap<Integer, String>();
    private HashSet<Integer> _canAnswerSurvey =new HashSet<Integer>();

    Project(String name, String description){
        _name=name;
        _description=description;
    }
    
    void close(Subject subj)throws SurveyException{
        _open=false;
        if(hasSurvey()){
            try{
                openSurvey(subj);                
            }
            catch(SurveyException e){
                _open=true;
                throw e;
            }
        }
    }
    
    private boolean isClosed(){
        return !_open;
    }
    
    void submit(int id, String submission) throws ProjectException{
        if(_open){
            _submissions.put(id,submission);
            _canAnswerSurvey.add(id);
        }
        else{
            throw new ProjectException();
        }
    }
    
    private int submissionNr(){
        return _submissions.size();
    }
    
    String seeSubmissions(){
        String submissions=" - "+_name;
        for(Map.Entry<Integer,String> entry : _submissions.entrySet()) {
            Integer id = entry.getKey();
            String  comment = entry.getValue();
            submissions+="\n* "+id+" - "+comment;
        }
        return submissions;
    }
    
    
    //SURVEYS
    
    boolean hasSurvey(){
        return _survey!=null;
    }
    
    void createSurvey()throws ProjectException, SurveyException{
        if(!hasSurvey()){
            if(!isClosed()){
                _survey=new Survey();
            }
            else{
                throw new ProjectException();
            }
        }
        else{
            throw new SurveyException("Survey");
        }
    }
    
    void removeSurvey(){
        _survey=null;
    }
    
    void cancelSurvey()throws SurveyException{
        if(!hasSurvey()){
            throw new SurveyException("Survey");
        }
        _survey.cancelSurvey(this);
    
    }
    void openSurvey (Subject subj)throws SurveyException{        
        if(!hasSurvey()){
            throw new SurveyException("Survey");
        }
        if(!isClosed()){
            throw new SurveyException("SurveyOpening");
        }
        _survey.openSurvey();
        subj.notify("Pode preencher inquérito do projecto "+_name+" da disciplina "+subj.getName());
    }
    
    void closeSurvey ()throws SurveyException{       
        if(!hasSurvey()){
            throw new SurveyException("Survey");
        }
        _survey.closeSurvey();
    }
    
    void endSurvey (Subject subj)throws SurveyException{        
        if(!hasSurvey()){
            throw new SurveyException("Survey");
        }
        _survey.endSurvey();
        _canAnswerSurvey.clear(); 
        subj.notify("Resultados do inquérito do projecto "+_name+" da disciplina "+subj.getName());
    }
    
    void submitSurvey(int hours, String comment, int id)throws ProjectException, SurveyException{
        if(_submissions.containsKey(id)){
            if(hasSurvey()){
                if(_canAnswerSurvey.contains(id)){
                    _survey.submitSurvey(hours, comment);
                    _canAnswerSurvey.remove(id);
                }
                else{}//ANSWERING TWICE-DO NOTHING?
            }
            else{
                throw new SurveyException("Survey");
            }
        }
        else{
            throw new ProjectException();
        }
    }

    String seeSurveyResultsStudent(User user)throws ProjectException, SurveyException{
        if(!_submissions.containsKey(user.getId())){
            throw new ProjectException();
        }
        if(!hasSurvey()){
            throw new SurveyException("Survey");
        } 
        return _name + _survey.getSurveyResults("ALUNO", submissionNr());
    }
    
    String seeSurveyResultsProfessor()throws SurveyException{
        if(!hasSurvey()){
            throw new SurveyException("Survey");
        } 
        return _name + _survey.getSurveyResults("DOCENTE", submissionNr());
    }
    String seeSurveyResultsRepresentative(){
        if(!hasSurvey()){
            return "";
        }
        else{
            return _name + _survey.getSurveyResults("DELEGADO", submissionNr());
        }
    } 
    
    String seeOpen(){
        if(isClosed()){
            return "\n" + _name + " is closed";
        }
        else{
            return "\n" + _name;
        }
    }
}
    
