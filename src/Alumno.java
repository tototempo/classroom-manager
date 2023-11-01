import java.util.Date;

public class Alumno extends Persona{

    private String legajo;

    public Alumno(String nombre, String apellido, int dni, Date  fechaDeNacimeinto, int edad){
        super(nombre, apellido, dni, fechaDeNacimeinto, edad);
    }

    public void setLegajo(String legajo){
        this.legajo = legajo;
    }

    @Override
    public String getLegajo(){
        return this.legajo;
    }
  
}