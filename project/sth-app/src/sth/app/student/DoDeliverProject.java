package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.exceptions.SubjectException;
import sth.exceptions.ProjectException;
import sth.exceptions.BadEntryException;

import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {
    private Input<String> _subjName;
    private Input<String> _projName;
    private Input<String> _submission;
  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _subjName=_form.addStringInput(Message.requestDisciplineName());
    _projName=_form.addStringInput(Message.requestProjectName());
    _submission=_form.addStringInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
        _receiver.submitProject(_subjName.value(),_projName.value(),_submission.value());
    }
    catch(SubjectException e){
        throw new NoSuchDisciplineException(_subjName.value());
    }
    catch(ProjectException e){
        throw new NoSuchProjectException(_subjName.value(),_projName.value());
    }
    catch(BadEntryException e){
        e.printStackTrace();
    }
  }
}
