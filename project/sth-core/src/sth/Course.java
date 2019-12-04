package sth;

import java.io.Serializable;
import java.util.HashMap;

import sth.exceptions.BadEntryException;
import sth.exceptions.SubjectException;
import sth.exceptions.ProjectException;
import sth.exceptions.SurveyException;

class Course implements Serializable{
    private static final int MAX_REPRESENTATIVES=7;
    private String _name;
    private HashMap <String, Subject> _subjects	= new HashMap<String, Subject>();
    private HashMap<Integer,User> _representatives 	= new HashMap<Integer,User>();

    //Constructor
    Course(String name){
        _name=name;
    }
    
    String getName(){
        return _name;
    }
	   
    //SUBJECTS
    boolean isSubject(String name){
        return _subjects.containsKey(name);
    }
    
    Subject getSubject(String name)throws SubjectException{
        if(isSubject(name)){
            return _subjects.get(name);
        }
        else{
            throw new SubjectException();
        }
    }

    void addSubject(Subject subj) throws BadEntryException{
		if(!isSubject(subj.getName())){
			_subjects.put(subj.getName(),subj);
			for(User u:_representatives.values()){
                subj.addObserver(u);
            }
		}
		else{
			throw new BadEntryException("subject");
		}
    }
	
	//REPRESENTATIVE
    boolean isRepresentative(User user){
        return _representatives.containsKey(user.getId());
    }
	
	private boolean represFullCap(){
        return _representatives.size()==MAX_REPRESENTATIVES;
    }
    
    //Becoming (and stop being) a representative implies an update of the notifications to receive of surveys on non-frequented subjects
	void addRepresentative(User user) throws BadEntryException{
		if (!represFullCap()){
			if(!isRepresentative(user)){
				_representatives.put(user.getId(),user);
				for(Subject subj:_subjects.values()){
                    subj.addObserver(user);
                }
			}
			else{
                throw new BadEntryException("User" + user.getId()+"already is representative of"+_name);
            }
        }
        else{
            throw new BadEntryException("Maximum Representatives in Course"+ getName());
        }
    }
	
	void rmvRepresentative(User user) throws BadEntryException{
		if(isRepresentative(user)){
			_representatives.remove(user.getId());
			for(Subject subj:_subjects.values()){
                if(!subj.isStudent(user.getId())){
                    subj.removeObserver(user);
                }
            }
		}
		else{
			throw new BadEntryException("User"+ user.getId()+"is not representative of"+_name);
		}
    }
	

    void createSurvey(String subjName,String projName)throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).createSurvey(projName);
    }

    void cancelSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).cancelSurvey(projName);
    }
    
    void openSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).openSurvey(projName);
    }
 
    void closeSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).closeSurvey(projName);
    }
        
    void endSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).endSurvey(projName);
    }
        
    String showSubjectSurveys(String subjName)throws SubjectException{
        return getSubject(subjName).showAllSurveys();
    }
    
    String showMoreStudentsSubject(){
        int max=0;
        String maxSubj="";
        for(Subject s: _subjects.values()){
            if(s.getStudentNumber()>max){
                max=s.getStudentNumber();
                maxSubj=s.getName();
            }
        }
        return "max Subject is" + maxSubj + "with" +max + "students\n";
    }
                
}
    
