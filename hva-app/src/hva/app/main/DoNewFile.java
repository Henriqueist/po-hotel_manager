package hva.app.main;


import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.HotelManager;


class DoNewFile extends Command<HotelManager> {
    DoNewFile(HotelManager receiver) {
        super(Label.NEW_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        if(_receiver.changed() && 
        Form.confirm(Prompt.saveBeforeExit())  ){         
            DoSaveFile save = new DoSaveFile(_receiver);
            save.execute();
            
        }
        _receiver.reset();
    }
}




