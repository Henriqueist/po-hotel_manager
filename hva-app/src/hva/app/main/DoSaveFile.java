package hva.app.main;

import hva.HotelManager;
import hva.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.IOException;
import java.io.FileNotFoundException;
//FIXME import other classes if needed

class DoSaveFile extends Command<HotelManager> {
    DoSaveFile(HotelManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
    }

    @Override
    protected final void execute() {
    	try{ 
            if(_receiver.getFilename() == null){ 
                String filename = 
                Form.requestString(Prompt.newSaveAs());
                _receiver.saveAs(filename);
            }
            else 
                _receiver.save();
        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(MissingFileAssociationException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
    }
}
