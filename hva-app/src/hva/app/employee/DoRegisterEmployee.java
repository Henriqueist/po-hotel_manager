package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.DuplicateEmployeeKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        addStringField("id",Prompt.employeeKey());
        addStringField("name",Prompt.employeeName());
        addOptionField("type",Prompt.employeeType(),"VET","TRT");    
    }

    @Override
    protected void execute() throws CommandException {
        try{
            _receiver.registerEmployee(stringField("id"),stringField(
                "name"),stringField("type"));
        }catch(hva.exceptions.DuplicateEmployeeKeyException e){
            throw new DuplicateEmployeeKeyException(stringField(
                "id"));
        }
    }

}
