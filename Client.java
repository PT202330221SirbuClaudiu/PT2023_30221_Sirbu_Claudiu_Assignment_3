package presentation.client;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Client {
    private JButton cautaButton;
    private JTextField textField2;
    private JPanel panou;
    private JButton stergeButton;
    private JTextField textField1;
    private JButton adaugaButton;
    private JButton editeazaButton;
    private JButton veziTotButton;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;


    public Client()
    {
        JFrame frame=new JFrame("Client");
        frame.setContentPane(panou);
        frame.pack();
        frame.setVisible(true);

        frame.pack();

        frame.setSize(700,450);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ControllerClient controllerClient=new ControllerClient();
        cautaButton.addActionListener(controllerClient.cautaDupaId(textField2));
        stergeButton.addActionListener(controllerClient.stergeDupaId(textField1));
        adaugaButton.addActionListener(controllerClient.adaugaClient(textField3,textField4,textField5));
        editeazaButton.addActionListener(controllerClient.editeazaClient(textField9,textField6,textField7,textField8));
        veziTotButton.addActionListener(controllerClient.veziTot());
    }
}
