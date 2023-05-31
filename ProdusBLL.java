package bll;

import dao.ProdusDAO;
import model.Client;
import model.Produs;

import java.util.List;

public class ProdusBLL {

    private ProdusDAO produsDAO;

    public ProdusBLL()
    {
        this.produsDAO=new ProdusDAO();
    }


    public Produs cautaDupaId(int id) {
        Produs produs = null;
        produs = produsDAO.findById(id);
        if (produs != null)
        {
            return produs;
        }
        else
        {
            System.out.println("Produsul nu a fost gasit!");
            return null;
        }

    }

    public void sterge(Integer id)
    {
        produsDAO.sterge(id);
    }

    public void insert(Produs produs)
    {
        produsDAO.insert(produs);
    }

    public void update(Produs produs)
    {
        produsDAO.update(produs);
    }

    public List<Produs> findAll()
    {
     return produsDAO.findAll();
    }

    public Produs findByDenumire(String nume)
    {
        return produsDAO.findByDenumire(nume);
    }

}
