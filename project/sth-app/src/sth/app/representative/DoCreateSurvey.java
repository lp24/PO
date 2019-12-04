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
import sth.app.exceptions.DuplicateSurveyException;
/**
 * 4.5.1. Create survey.
 */
public class DoCreateSurvey extends Command<SchoolManager> {

    private Input<String> _subjName;
    private Input<String> _projName;
    
    /**
    * @param receiver
    */
    public DoCreateSurvey(SchoolManager receiver) {
        super(Label.CREATE_SURVEY, receiver);
        _subjName=_form.addStringInput(Message.requestDisciplineName());
        _projName=_form.addStringInput(Message.requestProjectName());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();
        try{
            _receiver.createSurvey(_subjName.value(),_projName.value());
        }
        catch(SubjectException e){
            throw new NoSuchDisciplineException(_subjName.value());
        }
        catch(ProjectException e){
            throw new NoSuchProjectException(_subjName.value(),_projName.value());
        }
        catch(SurveyException e){
            throw new DuplicateSurveyException(_subjName.value(),_projName.value());
        }
        catch(BadEntryException e){
            e.printStackTrace();
        }
    }
}
