package model;

public class Comanda {

    private Integer id;
    private String numeClient;
    private String prenumeClient;
    private String numeProdus;
    private Integer cantitate;

    public Comanda()
    {

    }

    public Comanda(Integer id,String numeClient,String prenumeClient,String numeProdus,Integer cantitate)
    {
        this.id=id;
        this.cantitate=cantitate;
        this.prenumeClient=prenumeClient;
        this.numeProdus=numeProdus;
        this.numeClient=numeClient;
    }


    public String getPrenumeClient() {
        return prenumeClient;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public Integer getId() {
        return id;
    }

    public void setPrenumeClient(String prenumeClient) {
        this.prenumeClient = prenumeClient;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
