package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.NoSuchPersonIdException;
import sth.app.exceptions.NoSuchPersonException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {
    private Input<String> _filename;
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _filename=_form.addStringInput(Message.openFile());

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  
  @Override
  public final void execute() throws NoSuchPersonException {
    _form.parse();
    try {
        _display.add(_receiver.Open(_filename.value()));
        _display.display();
    } 
    catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } 
    catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    catch(NoSuchPersonIdException e){
        throw new NoSuchPersonException(e.getId());
    }
  }
}
