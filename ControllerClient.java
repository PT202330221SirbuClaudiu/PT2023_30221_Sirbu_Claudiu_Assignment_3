package presentation.client;

import bll.ClientBLL;
import model.Client;
import presentation.Afisare;
import start.CapTabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

public class ControllerClient {

private  ClientBLL clientBLL;

public ControllerClient()
{
    this.clientBLL=new ClientBLL();
}

public ActionListener cautaDupaId(JTextField idtext) {
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer id = Integer.parseInt(idtext.getText());
            Client client = null;

            try {
                DefaultTableModel model=new DefaultTableModel();
                client = clientBLL.cautaDupaId(id);

                if (client != null) {
                    String[][] date = new String[1][4];

                    // Determinarea automată a valorilor câmpurilor obiectului
                    Class<?> objClass = client.getClass();
                    Field[] fields = objClass.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true); // Asigură accesul la câmpurile private
                        Object value = null;
                        try {
                            value = fields[i].get(client);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                        date[0][i] = String.valueOf(value);
                    }

                    Object[] columnNames = CapTabel.getColumnNames(client.getClass());

                    model.setDataVector(date, columnNames);
                    Afisare afisare=new Afisare(model);// actualizează datele și numele coloanelor în modelul de tabel
                }
            } catch (Exception exception) {
                throw new NoSuchElementException("Clientul nu poate fii gasit!");
            }
        }
    };

   return actionListener;
    }


    public ActionListener stergeDupaId(JTextField idtext)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id=Integer.parseInt(idtext.getText());
                clientBLL.sterge(id);
            }
        };
        return actionListener;
    }

    public ActionListener adaugaClient(JTextField numetext,JTextField prenumetext,JTextField varstatext)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume=numetext.getText();
                String prenume=prenumetext.getText();
                Integer varsta=Integer.parseInt(varstatext.getText());
                Client client=new Client(null,nume,prenume,varsta);
                clientBLL.insert(client);
            }
        };
        return actionListener;
    }

    public ActionListener editeazaClient(JTextField idtext,JTextField numetext,JTextField prenumetext,JTextField varstatext)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id=Integer.parseInt(idtext.getText());
                String nume=numetext.getText();
                String prenume=prenumetext.getText();
                Integer varsta=Integer.parseInt(varstatext.getText());
              Client client=new Client(id,nume,prenume,varsta);
               clientBLL.update(client);

            }
        };
        return actionListener;
    }



    public ActionListener veziTot() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = new DefaultTableModel();
                    List<Client> objects = clientBLL.findAll();

                    if (!objects.isEmpty()) {
                        int rowCount = objects.size();
                        int columnCount = objects.get(0).getClass().getDeclaredFields().length;

                        String[][] data = new String[rowCount][columnCount];
                        Object[] columnNames = CapTabel.getColumnNames(objects.get(0).getClass());

                        for (int i = 0; i < rowCount; i++) {
                            Client object = objects.get(i);
                            Field[] fields = object.getClass().getDeclaredFields();
                            for (int j = 0; j < columnCount; j++) {
                                fields[j].setAccessible(true); // Asigură accesul la câmpurile private
                                Object value = null;
                                try {
                                    value = fields[j].get(object);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                                data[i][j] = String.valueOf(value);
                            }
                        }

                        model.setDataVector(data, columnNames);
                        Afisare afisare = new Afisare(model); // actualizează datele și numele coloanelor în modelul de tabel
                    }
                } catch (Exception exception) {
                    throw new NoSuchElementException("Nu s-au putut obține datele din baza de date!");
                }
            }
        };

        return actionListener;
    }



}

