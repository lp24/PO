package sth;

import java.io.*;
import sth.exceptions.*;

/**
 * The façade class.
 */
public class SchoolManager {
	private static final String DEFAULT_SCHOOL_NAME="school";
    private User _logged;
    private School _school;
    private boolean _changedSchool=true;
    private String _filename=null;
	

    public SchoolManager(){
        _school=new School(DEFAULT_SCHOOL_NAME);
    }
    
    public boolean hasFilename(){
        return _filename!=null;
    }
      
  /**
   * @param datafile
   * @throws ImportFileException
   * @throws InvalidCourseSelectionException
   */
    public void importFile(String datafile) throws ImportFileException{
        try {
			_school.importFile(datafile);
        }
		catch (IOException | BadEntryException e) {
			throw new ImportFileException(e);
		}
    }

  /**
   * @param id
   * @throws NoSuchPersonIdException
   */
    public void login(int id) throws NoSuchPersonIdException {
        _logged=_school.getUser(id);
    }
    

  /**
   * @return true when the currently logged in person is an administrative
   */
    public boolean hasAdministrative() {
        return _logged.isFunctionary();  
    }

  /**
   * @return true when the currently logged in person is a professor
   */
    public boolean hasProfessor() {
        return _logged.isProfessor();
    }

  /**
   * @return true when the currently logged in person is a student
   */

    public boolean hasStudent() {
        return _logged.isStudent();
    }

  /**
   * @return true when the currently logged in person is a representative
   */
    public boolean hasRepresentative() {
        return _logged.isRepresentative();
    }

  
    /* *** MENU PRINCIPAL *** */
    
    
    public String Open(String filename) throws NoSuchPersonIdException, IOException, FileNotFoundException, ClassNotFoundException{
        ObjectInputStream in =
            new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
        School school=(School)in.readObject();
        in.close();
        School save=_school;
        _school=school;
        try{ 
            login(_logged.getId());
            _filename=filename;
            _changedSchool=true;
            return _logged.showNotifications();
        }
        catch(NoSuchPersonIdException e){
            _school=save;
            throw new NoSuchPersonIdException(_logged.getId());
        }            
    }   
    
    public void saveAs(String filename) throws IOException{
        if(!_changedSchool){
            return;
        }
        _filename=filename;
        ObjectOutputStream out =
            new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
        out.writeObject(_school);
        out.close(); 
        _changedSchool=false;
    }
    
    public void save()throws IOException{saveAs(_filename);}
    
    /* *** PORTAL PESSOAL *** */
    
    
    public String getCurrentUser(){
        return _logged.toString();
    }
        
    public void changeCell(int cell){
        _logged.setCell(cell);
        _changedSchool=true;
    }
    
    public String seeAllUsers(){
		return _school.getAllUsers();
    }
    
    public String searchPerson(String name){
		return _school.searchPerson(name);
    }
    
        /* *** PORTAL DOCENTE *** */

    
    public void createProject(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException{      
        _logged.createProject(subjName,projName);
        _changedSchool=true;
    }

    public void closeProject(String subjName, String projName)throws  BadEntryException, SubjectException, ProjectException, SurveyException{ 
        _logged.closeProject(subjName,projName);
        _changedSchool=true;
    }   
    
    public String seeSubjectStudents(String subjName) throws BadEntryException, SubjectException{
        return _logged.seeSubjStudents(subjName);
    }
    
    public String seeProjectSubmissions(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException{
        return _logged.seeProjSubmissions(subjName,projName);
    }
    
    public String seeSurveyResults(String subjName, String projName) throws BadEntryException, SubjectException, ProjectException, SurveyException{ //Student and Prof
        return _logged.seeSurveyResults(subjName,projName);
    }
        
        /* *** PORTAL ESTUDANTE *** */

        
    public void submitProject(String subjName, String projName, String _submission) throws BadEntryException, SubjectException, ProjectException{
        _logged.submitProject(subjName, projName, _submission);
        _changedSchool=true;
    }
    
    public void submitSurvey(String subjName, String projName, int hours, String comment)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        _logged.submitSurvey(subjName,projName,hours,comment);
        _changedSchool=true;
    }
    
    public String seeOpenProjects(String subjName)throws BadEntryException, SubjectException{
        return _logged.seeOpenProjects(subjName);
    }
    
        /* *** PORTAL DELEGADO *** */

    
    public void createSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        _logged.createSurvey(subjName,projName);
        _changedSchool=true;
    }
    
    public void cancelSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        _logged.cancelSurvey(subjName,projName);
        _changedSchool=true;
    }
    
    public void openSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        _logged.openSurvey(subjName,projName);
        _changedSchool=true;
    }   
    
    public void closeSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        _logged.closeSurvey(subjName,projName);
        _changedSchool=true;
    }
    
    public void endSurvey(String subjName, String projName)throws BadEntryException, SubjectException, ProjectException, SurveyException{
        _logged.endSurvey(subjName,projName);
        _changedSchool=true;
    }
    
    public String showSubjectSurveys(String subjName)throws BadEntryException, SubjectException{
        return _logged.showSubjectSurveys(subjName);
    }
    
    public String showMoreStudentsSubject()throws BadEntryException {
        return _logged.showMoreStudentsSubject();
    }
    
}
