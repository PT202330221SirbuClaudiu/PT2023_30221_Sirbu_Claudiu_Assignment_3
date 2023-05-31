package presentation.facturi;

import bll.FacturaBLL;
import model.Factura;
import presentation.Afisare;
import start.CapTabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

public class ControllerFacturi {
    FacturaBLL facturaBLL;

   public ControllerFacturi()
    {
        this.facturaBLL=new FacturaBLL();
    }

    public ActionListener cauta(JTextField idtext) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = Integer.parseInt(idtext.getText());
                Factura factura = null;

                try {
                    DefaultTableModel model=new DefaultTableModel();
                    factura = facturaBLL.cautaDupaId(id);

                    if (factura != null) {
                        String[][] date = new String[1][10];

                        // Determinarea automată a valorilor câmpurilor obiectului
                        Class<?> objClass = factura.getClass();
                        Field[] fields = objClass.getDeclaredFields();
                        for (int i = 0; i < fields.length; i++) {
                            fields[i].setAccessible(true); // Asigură accesul la câmpurile private
                            Object value = null;
                            try {
                                value = fields[i].get(factura);
                            } catch (IllegalAccessException ex) {
                                throw new RuntimeException(ex);
                            }
                            date[0][i] = String.valueOf(value);
                        }

                        Object[] columnNames = CapTabel.getColumnNames(factura.getClass());

                        model.setDataVector(date, columnNames);
                        Afisare afisare=new Afisare(model);// actualizează datele și numele coloanelor în modelul de tabel
                    }
                } catch (Exception exception) {
                    throw new NoSuchElementException("Factura nu poate fii gasita!");
                }
            }
        };

        return actionListener;
    }


    public ActionListener afiseazaTot() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = new DefaultTableModel();
                    List<Factura> objects = facturaBLL.findAll();

                    if (!objects.isEmpty()) {
                        int rowCount = objects.size();
                        int columnCount = objects.get(0).getClass().getDeclaredFields().length;

                        String[][] data = new String[rowCount][columnCount];
                        Object[] columnNames = CapTabel.getColumnNames(objects.get(0).getClass());

                        for (int i = 0; i < rowCount; i++) {
                            Factura object = objects.get(i);
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
