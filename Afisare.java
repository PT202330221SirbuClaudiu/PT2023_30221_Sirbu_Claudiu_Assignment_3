package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class Afisare {


    public Afisare(DefaultTableModel model) {
        JPanel panou=new JPanel();
        JFrame frame = new JFrame("Afisare");
        frame.setContentPane(panou);
        frame.setVisible(true);


        JTable table1;
        table1 = new JTable(model);
        JScrollPane area = new JScrollPane(table1);

        area.setBounds(0, 0, 400, 400);
        panou.setLayout(null);
        panou.add(area);

        frame.pack();

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}

