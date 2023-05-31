package model;

import java.util.Date;

public record Factura(Integer id, String numeClient, String prenumeClient, String denumireProdus,  Date data,Integer cantitate)
{

}

