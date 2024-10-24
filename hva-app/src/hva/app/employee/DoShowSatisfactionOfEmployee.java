package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
        addStringField("id",Prompt.employeeKey());    }

    @Override
    protected void execute() throws CommandException {
        try{
            _display.popup(_receiver.showSatisfactionOfEmployee(
                stringField("id")));
        }catch(hva.exceptions.UnknownEmployeeKeyException e){
            throw new UnknownEmployeeKeyException(stringField("id"));
        }
    }

}
