package ca14080.repasoparcial;

/**
 * Created by root on 05-15-18.
 */

public class Nota {

    private String codamateria;
    private String carnet;
    private String ciclo;
    private Double notafinal;

    public Nota() {
    }

    public Nota(String codamateria, String carnet, String ciclo, Double notafinal) {
        this.codamateria = codamateria;
        this.carnet = carnet;
        this.ciclo = ciclo;
        this.notafinal = notafinal;
    }

    public String getCodamateria() {
        return codamateria;
    }

    public void setCodamateria(String codamateria) {
        this.codamateria = codamateria;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Double getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(Double notafinal) {
        this.notafinal = notafinal;
    }
}
