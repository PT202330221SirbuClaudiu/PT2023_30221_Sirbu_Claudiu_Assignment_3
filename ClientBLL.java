package bll;

import dao.ClientDAO;
import model.Client;

import java.util.List;

public class ClientBLL {
    private ClientDAO clientDAO;

    public ClientBLL()
    {
        this.clientDAO=new ClientDAO();
    }

    public Client cautaDupaId(int id) {
        Client client = null;
        client = clientDAO.findById(id);
        if (client != null)
        {
            return client;
        }
        else
        {
            System.out.println("Clientul nu a fost gasit!");
            return null;
        }

    }


    public void sterge(Integer id)
    {
        clientDAO.sterge(id);
    }

    public void insert(Client client)
    {
        clientDAO.insert(client);
    }

    public void update(Client client)
    {
        clientDAO.update(client);
    }

    public List<Client> findAll()
    {
    List<Client> clients=clientDAO.findAll();
    return clients;
    }
}

