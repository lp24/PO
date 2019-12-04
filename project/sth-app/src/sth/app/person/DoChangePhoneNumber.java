package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

/**
 * 4.2.2. Change phone number.
 */
public class DoChangePhoneNumber extends Command<SchoolManager> {

    Input<Integer> _cell;

    /**
    * @param receiver
    */
    public DoChangePhoneNumber(SchoolManager receiver) {
        super(Label.CHANGE_PHONE_NUMBER, receiver);
        _cell = _form.addIntegerInput(Message.requestPhoneNumber());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {
        _form.parse();        
        _receiver.changeCell(_cell.value());
        _display.add(_receiver.getCurrentUser());
        _display.display();
    }
}