package model;

public class Produs {

    private Integer id;
    private String denumire;
    private Integer cantitate;

    public Produs()
    {

    }

    public Produs(Integer id,String numeProdus,Integer cantitate)
    {
        this.id=id;
        this.denumire=numeProdus;
        this.cantitate=cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumeProdus(String numeProdus) {
        this.denumire = numeProdus;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public String getNumeProdus() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() {
        return denumire;
    }
}
