import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame implements MessageSender{
    private JFrame MainFrame;

    private JPanel toWrite = new JPanel();
    private JPanel toSee = new JPanel();

    private JTextField field = new JTextField();
    private JButton button = new JButton("Enter");
    private DefaultListModel dlm =new DefaultListModel<String>();
    private JList<String> list = new JList<String>(dlm);

    private BorderLayout brl = new BorderLayout();
    private BoxLayout bxl = new BoxLayout(toWrite,BoxLayout.X_AXIS);
    private JScrollPane scrollPane;
    private Network network;
    public MainFrame() throws IOException {

        toWrite.setLayout(bxl);
        toWrite.add(field);
        toWrite.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessangeers("user", field.getText());
                network.sendMessage(field.getText());
                field.setText("");
            }
        });

        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessangeers("user", field.getText());
                network.sendMessage(field.getText());
                field.setText("");
            }
        });
        scrollPane = new JScrollPane();
        toSee.setLayout(new BorderLayout());
        toSee.add(list, BorderLayout.SOUTH);
        toSee.add(scrollPane,BorderLayout.CENTER);
        MainFrame= new JFrame("My new chat");
        MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        MainFrame.setBounds(200,200,400,400);
        MainFrame.setLayout(brl);
        MainFrame.add(toSee,BorderLayout.CENTER);
        MainFrame.add(toWrite,"South");
        try {
            network = new Network("localhost",7777,this);
        } catch (IOException ex){
            ex.printStackTrace();
            System.exit(-1);
        }

        MainFrame.setVisible(true);



    }
    @Override
    public void sendMessangeers(String name, String msg){
        dlm.add(0,name);
        dlm.add(1,msg);
    }
}