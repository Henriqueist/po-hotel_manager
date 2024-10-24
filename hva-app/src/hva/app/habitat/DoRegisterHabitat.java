package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        addStringField("id", Prompt.habitatKey());
        addStringField("name", Prompt.habitatName());
        addIntegerField("area",Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        try{                
            _receiver.registerHabitat(stringField("id"),stringField(
                "name"),integerField("area"));
        }catch (hva.exceptions.DuplicateHabitatKeyException e){
            throw new DuplicateHabitatKeyException(stringField("id"));
        }
    }

}
