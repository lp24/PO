package sth;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Comparator;
import java.io.Serializable;
import sth.exceptions.ProjectException;
import sth.exceptions.SurveyException;


class Subject implements Serializable, Observado{
    private int 	_maxStudents;
	private String 	_name;
    private TreeMap<Integer, Student> _students  = new TreeMap<Integer, Student>();
    private TreeMap<String, Project>  _projects  = new TreeMap<String, Project>();
    private HashSet<Observador>       _observers = new HashSet<Observador>();
    
	//Constructor
    Subject(String name, int maxcap){
        _maxStudents=maxcap;
        _name=name;
    }
    
    String getName(){
        return _name;
    }
	
	//ADD or RMV OBSERVERS
    public void addObserver(Observador o){
        _observers.add(o);
    }
    
    public void removeObserver(Observador o){
        _observers.remove(o);
    }
    
    public void notify(String message){
        for(Observador o:_observers){
            o.update(message);
        }
    }
        
	
	//STUDENTS
    boolean isStudent(int id){   
        return _students.containsKey(id);
    }
    
    boolean fullStudentCap(){
        return _students.size()==_maxStudents;
    }
    
    void addStudent(Student s){
		_students.put(s.getId(),s);
		addObserver(s);
	}
	
	String seeAllStudents(){
		String students="";
		for(Student s:_students.values()){
			students+=s.toString();
		}
		return students;
	}


	//PROJECTS 

	private boolean isProject(String projName){
        return _projects.containsKey(projName);
    }
	
    private Project getProject(String projName) throws ProjectException{
        if(isProject(projName)){
            return _projects.get(projName);
        }
        else{
            throw new ProjectException();
        }
    }
    
	void createProject(String projName)throws ProjectException{
        if(!isProject(projName)){
            _projects.put(projName, new Project(projName, "description"));
        }
        else{
            throw new ProjectException();
        }
    }
    
    void closeProject(String projName)throws ProjectException, SurveyException{
        getProject(projName).close(this);
    }
    
    void submitProject(String projName, int id, String submission) throws ProjectException{
        getProject(projName).submit(id,submission);
    } 
    
    String seeProjSubmissions(String projName)throws ProjectException{
        return _name+getProject(projName).seeSubmissions();
    }
    
    String seeOpenProjects(){
        String openProjects="";
        for (Project p:_projects.values()){
            openProjects+=p.seeOpen();
        }
        if(openProjects.equals("")){
            return "no openProjects\n";
        }
        return openProjects;
    }
    
    int getStudentNumber(){
        return _students.size();
    }
            
            
    
    //SURVEYS
    void createSurvey(String projName) throws ProjectException, SurveyException{
        getProject(projName).createSurvey();
    }
    
    void cancelSurvey(String projName) throws ProjectException, SurveyException{
        getProject(projName).cancelSurvey();
    }
    
    void openSurvey  (String projName) throws ProjectException, SurveyException{
        getProject(projName).openSurvey(this);
    }		
    
    void closeSurvey (String projName) throws ProjectException, SurveyException{
        getProject(projName).closeSurvey();
    }
    
    void endSurvey   (String projName) throws ProjectException, SurveyException{
        getProject(projName).endSurvey(this);
    }
    
    void submitSurvey(String projName, int hours, String comment, int id)throws ProjectException, SurveyException{
        getProject(projName).submitSurvey(hours, comment, id);
    }
    
    String seeSurveyResultsStudent(String projName, User user)throws ProjectException, SurveyException{
        return _name + " - "+getProject(projName).seeSurveyResultsStudent(user);
    }
    String seeSurveyResultsProfessor(String projName)throws ProjectException, SurveyException{
        return _name + " - "+getProject(projName).seeSurveyResultsProfessor();
    }
    
    String showAllSurveys(){
        String surveys="";
        for(Project proj:_projects.values()) {
            if(proj.hasSurvey()){
                surveys=surveys+_name+" - "+proj.seeSurveyResultsRepresentative()+"\n";
            }
        }
        return surveys;
    }        
}
