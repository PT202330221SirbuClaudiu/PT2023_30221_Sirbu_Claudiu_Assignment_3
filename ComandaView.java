package presentation.comanda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ComandaView {
    private JTextField textField1;
    private JTextField textField2;
    private JButton cautaButton;
    private JButton stergeButton;
    private JButton adaugaButton;
    private JButton editeazaButton;
    private JButton veziTotButton;
    private JPanel panou;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;


    public ComandaView()
    {
        JFrame frame=new JFrame("Comenzi");
        frame.setContentPane(panou);
        frame.pack();
        frame.setVisible(true);

        frame.pack();

        frame.setSize(740,450);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ControllerComanda controllerComanda=new ControllerComanda();

        cautaButton.addActionListener(controllerComanda.cauta(textField1));
        stergeButton.addActionListener(controllerComanda.sterge(textField2));
        adaugaButton.addActionListener(controllerComanda.adauga(textField3,textField4,textField5,textField6));
        veziTotButton.addActionListener(controllerComanda.veziTot());
        editeazaButton.addActionListener(controllerComanda.editeaza(textField7,textField8,textField9,textField10,textField11));
    }

}