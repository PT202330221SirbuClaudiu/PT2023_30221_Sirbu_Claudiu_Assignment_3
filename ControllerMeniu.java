package presentation.meniu;

import presentation.facturi.FacturiView;
import presentation.client.Client;
import presentation.comanda.ComandaView;
import presentation.produs.ProdusView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMeniu {

   public ActionListener deschideClient() {
       ActionListener a = new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Client clientView=new Client();
           }
       };
       return a;
   }

    public ActionListener deschideComenzi() {
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComandaView comandaView=new ComandaView();
            }
        };
        return a;
    }

    public ActionListener deshchideProduse() {
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProdusView produsView=new ProdusView();
            }
        };
        return a;
    }

    public ActionListener deschideFacturi() {
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturiView facturiView=new FacturiView();
            }
        };
        return a;
    }
}
