package sth;
import java.util.ArrayList;
import java.io.Serializable;
import sth.exceptions.BadEntryException;
import sth.exceptions.SubjectException;
import sth.exceptions.ProjectException;
import sth.exceptions.SurveyException;

abstract class User implements Observador, Serializable{
    private String 	_name;
    private int 	_id;
    private int		_cellphone;
    private ArrayList<String> _notifications= new ArrayList<String>();

    //Constructor
    User(int id, int cell,String name){
        _id			= id;
        _cellphone	= cell;
		_name		= name;
    }
    
    int getId(){
        return _id;
    }
	
	String getName(){
        return _name;
    }
    
    void setCell(int cell){
        _cellphone=cell;
    }
	
	//LOGIN
	boolean isProfessor(){
		return false;
	}
	boolean isStudent(){
		return false;
	}
	boolean isFunctionary(){
		return false;
	}
	
	boolean isRepresentative(){
        return false;
    }
		
	//PROFESSOR methods
	
	
	String seeSubjStudents(String subjName) throws BadEntryException, SubjectException{
        throw new BadEntryException("user");
    }
    
    void createProject(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException{
        throw new BadEntryException("user");
    }
    
    void closeProject(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        throw new BadEntryException("user");
    }
    
    String seeProjSubmissions(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException{
        throw new BadEntryException("user");
    }
    
    String seeSurveyResults(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        throw new BadEntryException("user");
    }
    
    //STUDENT methods
    
    
    void submitProject(String subjName, String projName, String submission)throws BadEntryException, SubjectException, ProjectException{
        throw new BadEntryException("user");
    }
    
    void submitSurvey(String subjName, String projName, int hours, String comment)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        throw new BadEntryException("user");
    }
    
    String seeOpenProjects(String subjName) throws BadEntryException, SubjectException{
        throw new BadEntryException("user");
    }
    
    //REPRESENTATIVE methods
    
    
    void createSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        throw new BadEntryException("user");
    }
    
    void cancelSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        throw new BadEntryException("user");
    }
    
    void openSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{         
        throw new BadEntryException("user");
    }   
    
    void closeSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{              
        throw new BadEntryException("user");
    }
    
    void endSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{    
        throw new BadEntryException("user");
    }
    
    String showSubjectSurveys(String subjName)throws BadEntryException, SubjectException{        
        throw new BadEntryException("user");
    }
    
 
    
    String showMoreStudentsSubject() throws BadEntryException{
        throw new BadEntryException("user");
    }
    
    //NOTIFICATIONS    
    public void update(String message){
        _notifications.add(message);
    }
    
    String showNotifications(){
        String notifications="";
        for (String s:_notifications){
            notifications=notifications+s+"\n";
        }
        _notifications.clear();
        return notifications;
    }
    
    //ToString    
    @Override
	public String toString(){
		return(getTypeString()+"|"+_id+"|"+_cellphone+"|"+_name+"\n"+getSubjectString());
	}
	
	abstract String getTypeString();
	abstract String getSubjectString();
}
    
