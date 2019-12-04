package sth.app.student;

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
 * 4.4.2. Answer survey.
 */
public class DoAnswerSurvey extends Command<SchoolManager> {

    private Input<String> _subjName;
    private Input<String> _projName;
    private Input<Integer> _hours;
    private Input<String> _comment;
  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _subjName=_form.addStringInput(Message.requestDisciplineName());
    _projName=_form.addStringInput(Message.requestProjectName());
    _hours=_form.addIntegerInput(Message.requestProjectHours());
    _comment=_form.addStringInput(Message.requestComment());
  }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();
        try{
            _receiver.submitSurvey(_subjName.value(),_projName.value(),_hours.value(),_comment.value());
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
