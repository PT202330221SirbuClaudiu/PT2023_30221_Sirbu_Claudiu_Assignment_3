package presentation.facturi;

import javax.swing.*;

public class FacturiView {
    private JButton afiseazaButton;
    private JButton cautaButton;
    private JTextField textField1;
    private JPanel panou;

    public FacturiView()
    {
        JFrame frame=new JFrame("Facturi");
        frame.setContentPane(panou);
        frame.pack();
        frame.setVisible(true);
        frame.pack();
        frame.setSize(700,450);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ControllerFacturi controllerFacturi=new ControllerFacturi();
        cautaButton.addActionListener(controllerFacturi.cauta(textField1));
        afiseazaButton.addActionListener(controllerFacturi.afiseazaTot());
    }
}
