package bll;


import dao.ComandaDAO;
import dao.ProdusDAO;

import model.Comanda;
import model.Produs;

import java.util.List;

public class ComandaBLL {

    private ComandaDAO comandaDAO;
    private ProdusDAO produsDAO;

    public ComandaBLL()
    {
        this.comandaDAO=new ComandaDAO();
        this.produsDAO=new ProdusDAO();
    }

    public Comanda cautaDupaId(int id) {
        Comanda comanda = null;
        comanda = comandaDAO.findById(id);
        if (comanda != null)
        {
            return comanda;
        }
        else
        {
            System.out.println("Clientul nu a fost gasit!");
            return null;
        }
    }


    public void sterge(Integer id)
    {
        comandaDAO.sterge(id);
    }

    public void insert(Comanda comanda)
    {
        comandaDAO.insert(comanda);
    }

    public List<Comanda> findAll()
    {
        List<Comanda> comandas=comandaDAO.findAll();
        return comandas;
    }
    public void update(Comanda comanda)
    {
        comandaDAO.update(comanda);
    }

    public Produs findByName(String nume)
    {
     return produsDAO.findByName(nume);
    }
}
