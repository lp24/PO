package sth.app.teaching;

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

/**
 * 4.3.5. Show survey results.
 */
public class DoShowSurveyResults extends Command<SchoolManager> {

    Input<String> _subjName;
    Input<String> _projName;
    /**
    * @param receiver
    */
    
    public DoShowSurveyResults(SchoolManager receiver) {
        super(Label.SHOW_SURVEY_RESULTS, receiver);
        _subjName=_form.addStringInput(Message.requestDisciplineName());
        _projName=_form.addStringInput(Message.requestProjectName());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();
        try{
            _display.add(_receiver.seeSurveyResults(_subjName.value(),_projName.value()));
            _display.display();
        }
        catch(SubjectException e){
            throw new NoSuchDisciplineException(_subjName.value());
        }
        catch(ProjectException e){   
            throw new NoSuchProjectException(_subjName.value(),_projName.value());
        }
        catch(SurveyException e){            
            throw new NoSurveyException(_subjName.value(),_projName.value());
        }
        catch(BadEntryException e){
            e.printStackTrace();
        }
    }
}
