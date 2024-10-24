package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        addStringField("idHabitat",Prompt.habitatKey());
        addStringField("idTree",Prompt.treeKey());
        addStringField("nameTree",Prompt.treeName());
        addIntegerField("ageTree",Prompt.treeAge());
        addIntegerField("difficultyClean",Prompt.treeDifficulty());
        addOptionField("typeTree",Prompt.treeType(),"PERENE",
        "CADUCA");
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _display.popup(_receiver.addTreeToHabitat(stringField(
                "idHabitat"),
            stringField("idTree"),stringField("nameTree"),
            integerField("ageTree"),integerField("difficultyClean"),
            stringField("typeTree")));
        }catch(hva.exceptions.DuplicateTreeKeyException e){
            throw new DuplicateTreeKeyException(stringField(
                "idTree"));
        }
        catch(hva.exceptions.UnknownHabitatKeyException e){
            throw new UnknownHabitatKeyException(stringField(
                "idHabitat"));
        }
    }

}
