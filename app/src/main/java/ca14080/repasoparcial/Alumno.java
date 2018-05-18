package ca14080.repasoparcial;

/**
 * Created by root on 05-15-18.
 */

public class Alumno {

    private String carnet;
    private String nombre;
    private String apellido;
    private String sexo;
    private Integer matganadas;

    public Alumno() {
    }

    public Alumno(String carnet, String nombre, String apellido, String sexo, Integer matganadas) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.matganadas = matganadas;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getMatganadas() {
        return matganadas;
    }

    public void setMatganadas(Integer matganadas) {
        this.matganadas = matganadas;
    }
}
