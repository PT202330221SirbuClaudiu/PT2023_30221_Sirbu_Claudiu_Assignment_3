package bll;

import dao.FacturaDAO;
import model.Client;
import model.Factura;

import java.util.List;

public class FacturaBLL {
    private FacturaDAO facturaDAO;
    public FacturaBLL()
    {
        this.facturaDAO=new FacturaDAO();
    }

    public Factura cautaDupaId(Integer id)
    {
        return facturaDAO.findById(id);
    }
    public List<Factura> findAll()
    {
        List<Factura> facturi=facturaDAO.findAll();
        return facturi;
    }
    public void insert(Factura factura)
    {
        facturaDAO.insert(factura);
    }
}
