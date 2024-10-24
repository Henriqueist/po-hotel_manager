package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("id",Prompt.habitatKey());  
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _display.popup(_receiver.showAllTreesInHabitat(
                stringField("id")));
        }catch (hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(stringField("id"));
        }
    }

}
