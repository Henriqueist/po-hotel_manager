package hva.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import hva.HotelManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.exceptions.UnavailableFileException;
import hva.app.exceptions.*;

class DoOpenFile extends Command<HotelManager> {
    DoOpenFile(HotelManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        if(_receiver.getHotel() == null){}
        else if(_receiver.changed() && 
        Form.confirm(Prompt.saveBeforeExit())){
            DoSaveFile save = new DoSaveFile(_receiver);
            save.execute();                

        }
        try{
            _receiver.load(Form.requestString(Prompt.openFile()));
        }
        catch(FileNotFoundException e){ 
            _display.popup(Message.fileNotFound());}
        catch(ClassNotFoundException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}

    }

}
