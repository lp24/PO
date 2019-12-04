package sth;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;

import sth.exceptions.BadEntryException;
import sth.exceptions.SubjectException;
import sth.exceptions.ProjectException;
import sth.exceptions.SurveyException;


class Professor extends User{
    private HashMap<String,HashMap<String,Subject>> _subjects = new HashMap<String,HashMap<String,Subject>>(); //SubjName->{Course Names->Subject}
  
	//Constructor
    Professor(int id, int cellphone,String name){
        super(id, cellphone, name);
    }
	
    @Override
	boolean isProfessor(){
		return true;
	}
	
    private Subject getSubject(String subjName)throws SubjectException{ //Returns first Subject (NOT ORDERED) 
        if(_subjects.containsKey(subjName)){
            return _subjects.get(subjName).entrySet().iterator().next().getValue();
        }
        else{
            throw new SubjectException();
        }
    }
    
    private boolean givesSubject(Subject subj, Course course){
        return _subjects.containsKey(subj.getName()) && _subjects.get(subj.getName()).containsKey(course.getName());
    }
        
       
	void addSubject(Subject subj, Course course) throws BadEntryException{
        if(givesSubject(subj,course)){
            throw new BadEntryException("subject");
        }    
        if(!_subjects.containsKey(subj.getName())){
            _subjects.put(subj.getName(),new HashMap<String, Subject>());
        }           
        _subjects.get(subj.getName()).put(course.getName(),subj);
        subj.addObserver(this);
    }
	
    //PORTAL DOCENTE
    
	@Override
	String seeSubjStudents(String subjName) throws SubjectException{
        return getSubject(subjName).seeAllStudents();
	}
	
	@Override
    void createProject(String subjName, String projName)throws SubjectException, ProjectException{
        getSubject(subjName).createProject(projName);
    }
    
    @Override
    void closeProject(String subjName, String projName) throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).closeProject(projName);
    }
    

    @Override
    String seeProjSubmissions(String subjName, String projName)throws SubjectException, ProjectException{
        return getSubject(subjName).seeProjSubmissions(projName);
    }
    
    @Override
    String seeSurveyResults(String subjName, String projName) throws SubjectException, ProjectException, SurveyException{
        return getSubject(subjName).seeSurveyResultsProfessor(projName);
    }
        
	//TO STRING
	String getTypeString(){
        return "DOCENTE";
    }
    
    String getSubjectString(){
        int somaDisciplinas=0;
        TreeSet<String> subjByCourse = new TreeSet<String>(StringComparator.INSTANCE);
		for(HashMap<String, Subject> map:_subjects.values()){
            for(Map.Entry<String,Subject> entry : map.entrySet()) {
                somaDisciplinas+=1;
                subjByCourse.add(entry.getKey()+" - "+ entry.getValue().getName());
            }
        }  
        String subjs="";
        for(String s:subjByCourse){
			subjs+="* "+s+"\n";
		}
		return subjs;// + somaDisciplinas + "Disciplinas";
	}	
}
