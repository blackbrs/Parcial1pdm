package ca14080.repasoparcial;

/**
 * Created by root on 05-15-18.
 */

public class Materia {
    private String codmateria;
    private String nommateria;
    private String unidadesval;

    public double getCantidad_materias() {
        return cantidad_materias;
    }

    public void setCantidad_materias(double cantidad_materias) {
        this.cantidad_materias = cantidad_materias;
    }

    private double cantidad_materias;

    public Materia(String codmateria, String nommateria, String unidadesval, double cantidad_materias) {
        this.codmateria = codmateria;
        this.nommateria = nommateria;
        this.unidadesval = unidadesval;
        this.cantidad_materias = cantidad_materias;
    }

    public Materia() {
    }

    public Materia(String codmateria, String nommateria, String unidadesval) {
        this.codmateria = codmateria;
        this.nommateria = nommateria;
        this.unidadesval = unidadesval;
    }

    public String getCodmateria() {
        return codmateria;
    }

    public void setCodmateria(String codmateria) {
        this.codmateria = codmateria;
    }

    public String getNommateria() {
        return nommateria;
    }

    public void setNommateria(String nommateria) {
        this.nommateria = nommateria;
    }

    public String getUnidadesval() {
        return unidadesval;
    }

    public void setUnidadesval(String unidadesval) {
        this.unidadesval = unidadesval;
    }
}