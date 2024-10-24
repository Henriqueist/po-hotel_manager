package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("id",Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _display.popup(_receiver.showSatisfactionOfAnimal(
                stringField("id")));
        }catch(hva.exceptions.UnknownAnimalKeyException e){
            throw new UnknownAnimalKeyException(stringField("id"));
        }

    }

}
