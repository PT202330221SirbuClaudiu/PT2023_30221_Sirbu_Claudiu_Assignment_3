package presentation.produs;

import javax.swing.*;

public class ProdusView {
    private JTextField textField1;
    private JButton cautaButton;
    private JButton stergeButton;
    private JButton adaugaButton;
    private JButton editeazaButton;
    private JButton veziTotButton;
    private JPanel panou;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;


    public ProdusView()
    {
        JFrame frame=new JFrame("Produse");
        frame.setContentPane(panou);
        frame.pack();
        frame.setVisible(true);

        frame.pack();

        frame.setSize(700,450);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ControllerProdus controllerProdus=new ControllerProdus();
        cautaButton.addActionListener(controllerProdus.cautaDupaId(textField1));
        stergeButton.addActionListener(controllerProdus.sterge(textField2));
        adaugaButton.addActionListener(controllerProdus.adauga(textField3,textField4));
        editeazaButton.addActionListener(controllerProdus.editeaza(textField5,textField6,textField7));
        veziTotButton.addActionListener(controllerProdus.veziTot());
    }
}
