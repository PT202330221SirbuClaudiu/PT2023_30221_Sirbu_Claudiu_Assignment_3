package presentation;

import javax.swing.*;
import javax.swing.plaf.PanelUI;

public class Pop {
    private JPanel panel1;

    public Pop()
    {
        JFrame frame=new JFrame("Eroare");
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        frame.pack();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
