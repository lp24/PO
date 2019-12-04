package sth;

import java.util.HashMap;
import java.util.TreeMap;
import java.io.*;

import sth.exceptions.BadEntryException;
import sth.exceptions.SubjectException;
import sth.exceptions.NoSuchPersonIdException;

/**
 * School implementation.
 */
class School implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;
    private static final int DEFAULT_MAX_SUBJECT_CAPACITY=200;
    private String _name;
    private int _currentId=10000;
    private HashMap <String,  Course>  _schoolCourses = new HashMap<String, Course>();
    private TreeMap <Integer, User>    _schoolUsers   = new TreeMap<Integer, User>();       
    
    School(String name){
        _name=name;
    }
	
    private void setCurrentId(int id){
        _currentId=id;
    }
    
    //USER
    
    private boolean isUser(int id){
        return _schoolUsers.containsKey(id);
    }

    User getUser(int id)throws NoSuchPersonIdException{
        if(isUser(id)){
            return _schoolUsers.get(id);
        }
        else{
            throw new NoSuchPersonIdException(id);
        }
    }
    
    private void addUser(User u) throws BadEntryException{
		if(!isUser(u.getId())){
			_schoolUsers.put(u.getId(),u);
		}
		else{
			throw new BadEntryException(String.valueOf(u.getId()));
		}
    }
    
    //COURSE

    private Course getCourse(String name){
        return _schoolCourses.get(name);
    }
    
    private boolean isCourse(String name){        
        return _schoolCourses.containsKey(name);
    }

    private void addCourse(Course c) throws BadEntryException{
		if(!isCourse(c.getName())){
			_schoolCourses.put(c.getName(),c);
		}
		else{
			throw new BadEntryException(c.getName());
		}
    }
  
   /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
    void importFile(String filename) throws IOException, BadEntryException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        User u=null;
        boolean isDelegado=false;
        
        while ((line = reader.readLine()) != null){
            String[] fields = line.split("\\|"); 
            
            if(fields[0].equals("FUNCIONÁRIO")){
                u=new Functionary(Integer.parseInt(fields[1]),Integer.parseInt(fields[2]),fields[3]);
                addUser(u);
            }
            else if(fields[0].equals("ALUNO")){
                u=new Student(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]),fields[3]);
                addUser(u);
            }
            
            else if(fields[0].equals("DELEGADO")){
                u=new Student(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]),fields[3]);
                addUser(u);
                isDelegado=true;                
            }
       
            else if(fields[0].equals("DOCENTE")){
				u=new Professor(Integer.parseInt(fields[1]),Integer.parseInt(fields[2]),fields[3]);
                addUser(u);
            }
			
            else if (fields[0].startsWith("#")){              //# CourseString|SubjectString
                if(u.getId()>=_currentId){
                    setCurrentId(u.getId()+1);
                }
                String courseName=fields[0].substring(2);
				String subjName=fields[1];
				Course course;
				Subject subj;
				//Course
                if(!isCourse(courseName)){
                    course = new Course(courseName);
                    addCourse(course);
                }
                else{
                    course = getCourse(courseName);
                }
                
                if(isDelegado){
                    course.addRepresentative(u);
                    isDelegado=false;
                 }

				//Subject
                try{
                    subj=course.getSubject(subjName);
                }
                catch(SubjectException e){
                    subj=new Subject(subjName,DEFAULT_MAX_SUBJECT_CAPACITY);
                    course.addSubject(subj);
                }
       
                if(u.isStudent()){
					((Student)u).setCourse(course);
                    ((Student)u).addSubject(subj);
                }
				
				else if(u.isProfessor()){
					((Professor)u).addSubject(subj, course);
				}
				
				else{
					throw new BadEntryException("Corrupted File");
				}
            }
			else{
				throw new BadEntryException("Corrupted File");
			}
        }
		reader.close();
    }
    
    String getAllUsers(){
        String AllUsers="";
		for (User u: _schoolUsers.values()){
            AllUsers+=u.toString();
        }
        return AllUsers;
    }
    
    String searchPerson(String name){    
        TreeMap <String, User> _searched   = new TreeMap<String, User>(StringComparator.INSTANCE);       
        for (User u: _schoolUsers.values()){
            if(u.getName().contains(name)){
                _searched.put(u.getName(),u);
            }                
        }
        String s="";
        for (User u: _searched.values()){
            s+=u.toString();
        }
        return s;
    }
}
