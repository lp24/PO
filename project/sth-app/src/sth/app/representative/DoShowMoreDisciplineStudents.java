package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

import sth.exceptions.BadEntryException;

/**
 * 4.5.6. Show discipline surveys.
 */
public class DoShowMoreDisciplineStudents extends Command<SchoolManager> {

    /**
    * @param receiver
    */
    public DoShowMoreDisciplineStudents(SchoolManager receiver) {
        super(Label.SHOW_MORE_SUBJECT_STUDENTS, receiver);
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException {
        try{
            _display.add(_receiver.showMoreStudentsSubject());
            _display.display();
        }
        catch(BadEntryException e){
            e.printStackTrace();
        }
    }
}
 
