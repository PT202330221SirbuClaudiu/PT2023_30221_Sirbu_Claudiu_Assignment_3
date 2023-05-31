package presentation.comanda;

import bll.ComandaBLL;
import bll.FacturaBLL;
import bll.ProdusBLL;
import bll.validators.ValidatorCantitate;
import model.Client;
import model.Comanda;
import model.Factura;
import model.Produs;
import presentation.Afisare;
import start.CapTabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class ControllerComanda {

    private ComandaBLL comandaBLL;
    private ProdusBLL produsBLL;
    private FacturaBLL facturaBLL;
    public ControllerComanda()
    {
        this.produsBLL=new ProdusBLL();
        this.comandaBLL=new ComandaBLL();
        this.facturaBLL=new FacturaBLL();
    }



    public ActionListener cauta(JTextField idtext) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = Integer.parseInt(idtext.getText());
                Comanda comanda = null;

                try {
                    DefaultTableModel model=new DefaultTableModel();
                    comanda = comandaBLL.cautaDupaId(id);

                    if (comanda != null) {
                        String[][] date = new String[1][10];

                        // Determinarea automată a valorilor câmpurilor obiectului
                        Class<?> objClass = comanda.getClass();
                        Field[] fields = objClass.getDeclaredFields();
                        for (int i = 0; i < fields.length; i++) {
                            fields[i].setAccessible(true); // Asigură accesul la câmpurile private
                            Object value = null;
                            try {
                                value = fields[i].get(comanda);
                            } catch (IllegalAccessException ex) {
                                throw new RuntimeException(ex);
                            }
                            date[0][i] = String.valueOf(value);
                        }

                        Object[] columnNames = CapTabel.getColumnNames(comanda.getClass());

                        model.setDataVector(date, columnNames);
                        Afisare afisare=new Afisare(model);// actualizează datele și numele coloanelor în modelul de tabel
                    }
                } catch (Exception exception) {
                    throw new NoSuchElementException("Comanda nu poate fii gasit!");
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
                comandaBLL.sterge(id);
            }
        };
        return actionListener;
    }


    public ActionListener adauga(JTextField textField3,JTextField textField4,JTextField textField5,JTextField textField6)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume=textField3.getText();
                String prenume=textField4.getText();
                String denumire=textField5.getText();
                Integer cantitate=Integer.parseInt(textField6.getText());
                Comanda comanda=new Comanda(null,nume,prenume,denumire,cantitate);
                try{
                    ValidatorCantitate validatorCantitate=new ValidatorCantitate(cantitate);
                    Produs produs=produsBLL.findByDenumire(denumire);
                    validatorCantitate.validate(produs);
                    comandaBLL.insert(comanda);
                    Factura factura=new Factura(null,nume,prenume,denumire,new Date(),cantitate);
                    facturaBLL.insert(factura);
                    produs.setCantitate(produs.getCantitate()-cantitate);
                    produsBLL.update(produs);
                }
                catch (Error error)
                {
                    System.out.println("Nu sunt produse pe stoc");
                }

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
                    List<Comanda> objects = comandaBLL.findAll();

                    if (!objects.isEmpty()) {
                        int rowCount = objects.size();
                        int columnCount = objects.get(0).getClass().getDeclaredFields().length;

                        String[][] data = new String[rowCount][columnCount];
                        Object[] columnNames = CapTabel.getColumnNames(objects.get(0).getClass());

                        for (int i = 0; i < rowCount; i++) {
                            Comanda object = objects.get(i);
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

    public ActionListener editeaza(JTextField id,JTextField nume,JTextField prenume,JTextField denumire,JTextField cantitate)
    {
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer idv=Integer.parseInt(id.getText());
                String numev=nume.getText();
                String prenumev=prenume.getText();
                String denumirev=denumire.getText();
                Integer cantitatev=Integer.parseInt(cantitate.getText());
               Comanda comanda=new Comanda(idv,numev,prenumev,denumirev,cantitatev);
                comandaBLL.update(comanda);

            }
        };
        return actionListener;
    }


}





