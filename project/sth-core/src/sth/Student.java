package sth;
import java.util.TreeMap;
import sth.exceptions.BadEntryException;
import sth.exceptions.SubjectException;
import sth.exceptions.ProjectException;
import sth.exceptions.SurveyException;

class Student extends User{
    private static final int MAX_SUBJECTS	  = 6;
    private Course 	_course 				  = null;
    private TreeMap<String,Subject> _subjects = new TreeMap<String,Subject>(StringComparator.INSTANCE);
    
	//Constructor
    Student(int id, int cellphone,String name){
        super(id, cellphone, name);
    }
    
    @Override
	boolean isStudent(){
		return true;
	}
	
    //COURSE
	Course getCourse(){
        return _course;
    } 
	
    void setCourse(Course c)throws BadEntryException{
		if(_course==null){
			_course=c;
			
		}	
		else if (c!=_course){
			throw new BadEntryException("Student nr" + getId() + "with different Courses");
		}
    }
    
    //SUBJECT
    private boolean fullSubjectCap(){
        return _subjects.size()==MAX_SUBJECTS;
    }

    private boolean isSubject(Subject s){
        return _subjects.containsKey(s.getName());
        
    }
    
    Subject getSubject(String subjName) throws SubjectException{
        if(_subjects.containsKey(subjName)){
            return _subjects.get(subjName);
        }
        else{
            throw new SubjectException();
        }
    }
	
    void addSubject(Subject s) throws BadEntryException{
		if(!fullSubjectCap()){
			if(!isSubject(s)){
                if(!s.fullStudentCap()){
                    _subjects.put(s.getName(),s);
                    s.addStudent(this);
                }
                else{
                    throw new BadEntryException("Subject" +s.getName()+ "has reached maximum Students");
                }
            }
			else{
				throw new BadEntryException("Student nr" +getId()+ "already has Subject"+s.getName());
			}
        }
		else{
			throw new BadEntryException("Student nr"+getId()+ "has reached maximum Subjects");
		}
    }
    
    
    //PORTAL ESTUDANTE
	
    @Override
    void submitProject(String subjName, String projName, String submission)throws SubjectException, ProjectException{
        getSubject(subjName).submitProject(projName, getId(), submission);
    }
        
    @Override
    void submitSurvey(String subjName, String projName, int hours, String comment)throws SubjectException, ProjectException, SurveyException{
        getSubject(subjName).submitSurvey(projName,hours,comment,getId());
    }
    
    @Override
    String seeSurveyResults(String subjName, String projName) throws SubjectException, ProjectException, SurveyException{
        return getSubject(subjName).seeSurveyResultsStudent(projName, this);
    }
    
    @Override 
    String seeOpenProjects(String subjName) throws SubjectException{
        return getSubject(subjName).seeOpenProjects();
    }
    
    
    //REPRESENTATIVE 
    @Override
	boolean isRepresentative(){
        return _course.isRepresentative(this);
    }
		
	void becomerepresentative() throws BadEntryException{
        _course.addRepresentative(this);
	}
	
	void removerepresentative() throws BadEntryException{
        _course.rmvRepresentative(this);
	}	
	

	//PORTAL DELEGADO
    @Override
    void createSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getCourse().createSurvey(subjName,projName);
    }
    
    @Override
    void cancelSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getCourse().cancelSurvey(subjName,projName);
    }
    
    @Override
    void openSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getCourse().openSurvey(subjName,projName);
    }   
    
    @Override
    void closeSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getCourse().closeSurvey(subjName,projName);
    }
        
    @Override
    void endSurvey(String subjName, String projName)throws SubjectException, ProjectException, SurveyException{
        getCourse().endSurvey(subjName,projName);
    }
        
    @Override
    String showSubjectSurveys(String subjName)throws SubjectException{
        return  getCourse().showSubjectSurveys(subjName);
    }
    
    @Override
    String showMoreStudentsSubject(){
        return getCourse().showMoreStudentsSubject();
    }
    
    //TO STRING
    String getTypeString(){
        if(isRepresentative()){
			return "DELEGADO";
		}
		else{		
			return "ALUNO";
		}
    }
        
	String getSubjectString(){
		String subjs="";
		for(Subject s:_subjects.values()){
			subjs+="* "+_course.getName()+" - "+s.getName()+"\n";
		}
		return subjs;
	}
        
}
