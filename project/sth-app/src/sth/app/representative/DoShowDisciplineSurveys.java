package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.exceptions.SubjectException;
import sth.exceptions.BadEntryException;

import sth.app.exceptions.NoSuchDisciplineException;

/**
 * 4.5.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

    private Input<String> _subjName;
    /**
    * @param receiver
    */
    public DoShowDisciplineSurveys(SchoolManager receiver) {
        super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
        _subjName=_form.addStringInput(Message.requestDisciplineName());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        _form.parse();
        try{
            _display.add(_receiver.showSubjectSurveys(_subjName.value()));
            _display.display();
        }
        catch(SubjectException e){
            throw new NoSuchDisciplineException(_subjName.value());
        }
        catch(BadEntryException e){
            e.printStackTrace();
        }
    }
}
