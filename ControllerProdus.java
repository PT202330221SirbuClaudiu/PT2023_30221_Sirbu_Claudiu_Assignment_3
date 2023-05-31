package presentation.produs;

import bll.ProdusBLL;
import model.Client;
import model.Produs;
import presentation.Afisare;
import start.CapTabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

public class ControllerProdus {
    ProdusBLL produsBLL;

    public ControllerProdus()
    {
        this.produsBLL=new ProdusBLL();
    }

    public ActionListener cautaDupaId(JTextField idtext) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = Integer.parseInt(idtext.getText());
                Produs produs = null;

                try {
                    DefaultTableModel model=new DefaultTableModel();
                    produs = produsBLL.cautaDupaId(id);

                    if (produs != null) {
                        String[][] date = new String[1][4];

                        // Determinarea automată a valorilor câmpurilor obiectului
                        Class<?> objClass = produs.getClass();
                        Field[] fields = objClass.getDeclaredFields();
                        for (int i = 0; i < fields.length; i++) {
                            fields[i].setAccessible(true); // Asigură accesul la câmpurile private
                            Object value = null;
                            try {
                                value = fields[i].get(produs);
                            } catch (IllegalAccessException ex) {
                                throw new RuntimeException(ex);
                            }
                            date[0][i] = String.valueOf(value);
                        }

                        Object[] columnNames = CapTabel.getColumnNames(produs.getClass());

                        model.setDataVector(date, columnNames);
                        Afisare afisare=new Afisare(model);// actualizează datele și numele coloanelor în modelul de tabel
                    }
                } catch (Exception exception) {
                    throw new NoSuchElementException("Produsul nu poate fii gasit!");
                }
            }
        };

        return actionListener;
    }


    public ActionListener sterge(JTextField idtext)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id=Integer.parseInt(idtext.getText());
                produsBLL.sterge(id);
            }
        };
        return actionListener;
    }

    public ActionListener adauga(JTextField denumire,JTextField cantitate)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume=denumire.getText();
                Integer cantitatev=Integer.parseInt(cantitate.getText());
                Produs produs=new Produs(null,nume,cantitatev);
                produsBLL.insert(produs);
            }
        };
        return actionListener;
    }


    public ActionListener editeaza(JTextField idtext,JTextField numetext,JTextField cantitate)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id=Integer.parseInt(idtext.getText());
                String nume=numetext.getText();
                Integer cantitatev=Integer.parseInt(cantitate.getText());
                Produs produs=new Produs(id,nume,cantitatev);
                produsBLL.update(produs);

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
                    List<Produs> objects = produsBLL.findAll();

                    if (!objects.isEmpty()) {
                        int rowCount = objects.size();
                        int columnCount = objects.get(0).getClass().getDeclaredFields().length;

                        String[][] data = new String[rowCount][columnCount];
                        Object[] columnNames = CapTabel.getColumnNames(objects.get(0).getClass());

                        for (int i = 0; i < rowCount; i++) {
                            Produs object = objects.get(i);
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
