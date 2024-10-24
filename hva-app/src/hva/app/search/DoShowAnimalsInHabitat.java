package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
        addStringField("id", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _display.popup(_receiver.showAnimalsInHabitat(
                stringField("id")));
        }catch (hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(stringField("id"));
        }
    }

}
