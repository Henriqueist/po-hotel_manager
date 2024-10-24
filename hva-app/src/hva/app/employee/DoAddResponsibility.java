package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.NoResponsibilityException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);
        addStringField("id",Prompt.employeeKey());
        addStringField("responsability",Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _receiver.addResponsability(stringField("id"),stringField(
                "responsability"));
        }catch(hva.exceptions.UnknownEmployeeKeyException e){
            throw new UnknownEmployeeKeyException(stringField("id"));
        }catch(hva.exceptions.NoResponsibilityException e){
            throw new NoResponsibilityException(stringField("id"),
            stringField("responsability"));
        }
    }

}
