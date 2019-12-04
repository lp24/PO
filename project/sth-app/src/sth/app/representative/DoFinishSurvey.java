package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.exceptions.SubjectException;
import sth.exceptions.ProjectException;
import sth.exceptions.SurveyException;
import sth.exceptions.BadEntryException;

import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.FinishingSurveyException;

/**
 * 4.5.5. Finish survey.
 */
public class DoFinishSurvey extends Command<SchoolManager> {

    private Input<String> _subjName;
    private Input<String> _projName;

    /**
    * @param receiver
    */
    public DoFinishSurvey(SchoolManager receiver) {
        super(Label.FINISH_SURVEY, receiver);
        _subjName=_form.addStringInput(Message.requestDisciplineName());
        _projName=_form.addStringInput(Message.requestProjectName());    
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();
        try{
            _receiver.endSurvey(_subjName.value(),_projName.value());
        }
        catch(SubjectException e){
            throw new NoSuchDisciplineException(_subjName.value());
        }
        catch(ProjectException e){                
            throw new NoSuchProjectException(_subjName.value(),_projName.value());
        }
        catch(SurveyException e){
            if(e.getEntry().equals("Survey")){
                throw new NoSurveyException(_subjName.value(),_projName.value());
            }
            else if(e.getEntry().equals("SurveyFinishing")){
                throw new FinishingSurveyException(_subjName.value(),_projName.value());
            }
            else{
                e.printStackTrace();
            }
        }
        catch(BadEntryException e){
            e.printStackTrace();
        }
    }

}
