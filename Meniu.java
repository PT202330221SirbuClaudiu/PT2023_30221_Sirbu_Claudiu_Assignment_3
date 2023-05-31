package presentation.meniu;

import javax.swing.*;

public class Meniu {
    private JButton produseButton;
    private JButton clientButton;
    private JButton comenziButton;
    private JPanel panou;
    private JButton facturiButton;

    public Meniu()
    {
        JFrame frame=new JFrame("Meniu");
        frame.setContentPane(panou);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(400,500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ControllerMeniu c = new ControllerMeniu();

        clientButton.addActionListener(c.deschideClient());
        comenziButton.addActionListener(c.deschideComenzi());
        produseButton.addActionListener(c.deshchideProduse());
        facturiButton.addActionListener(c.deschideFacturi());
    }


}
