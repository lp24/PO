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
import sth.app.exceptions.OpeningSurveyException;

/**
 * 4.3.2. Close project.
 */
public class DoCloseProject extends Command<SchoolManager> {

    Input<String> _subjName;
    Input<String> _projName;
  /**
   * @param receiver
   */
  public DoCloseProject(SchoolManager receiver) {
    super(Label.CLOSE_PROJECT, receiver);
    _subjName=_form.addStringInput(Message.requestDisciplineName());
    _projName=_form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
        _receiver.closeProject(_subjName.value(),_projName.value());
    }
    catch(SubjectException e){
        throw new NoSuchDisciplineException(_subjName.value());
    }
    catch(ProjectException e){
        throw new NoSuchProjectException(_subjName.value(),_projName.value());
    }
    catch(SurveyException e){
        throw new OpeningSurveyException(_subjName.value(),_projName.value());
    }
    catch(BadEntryException e){
        e.printStackTrace();
    }
  }
}
