package hva;

import java.io.Serializable;

public abstract class  Employee implements Serializable {
    private String _id;
    private String _name;


    public Employee(String id, String name){
        _id=id;
        _name=name;

    }

    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    /**
     * Calculates the employee's satisfaction level.
     *
     * @return the employee's satisfaction as a floating-point value
     */
    public abstract float satisfaction();

}
