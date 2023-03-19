import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.text.*;

public class Frontend implements ActionListener {

    private Document doc;
    private SimpleAttributeSet attr;
    private JList list;
    private JTextField txt;
    private JButton but;
    private MySocket sock;
    private String name;
    private DefaultListModel<String> lm;

    public Frontend(String name, MySocket sock) {
        this.sock = sock;
        this.name = name;
        lm = new DefaultListModel<>();
        list = new JList<>(lm);
    }

    public void actionPerformed(ActionEvent event) {
        String text = txt.getText();
        txt.setText("");
        if (text.length() > 0) {
            try {
                sock.print(text);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                doc.insertString(doc.getLength(), text + "\n", attr);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String text) {
        String msg[] = text.split("> ", 2);
        if (msg[1].equals("SALIR")) {
            try {
                doc.insertString(doc.getLength(), msg[0] + " -> " + "salió." + "\n", attr);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < lm.size(); i++) {
                if (lm.get(i).contains(msg[0])) {
                    lm.remove(i);
                    break;
                }
            }
        } else {
            if (!lm.contains(msg[0])) {
                lm.addElement(msg[0]);
                List<String> lista = new ArrayList<>();
                for (int i = 0; i < lm.size(); i++) {
                    lista.add((String) lm.get(i));
                }
                Collections.sort(lista);
                lm.removeAllElements();
                for (String s : lista) {
                    lm.addElement(s);
                }
            }
            try {
                doc.insertString(doc.getLength(), msg[0] + " -> " + msg[1] + "\n", attr);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void crear_frontend(String name) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame(name);
        JPanel out = new JPanel();
        out.setLayout(new BoxLayout(out, BoxLayout.LINE_AXIS));
        JTextPane pane = new JTextPane();
        pane.setEditable(false);
        attr = new SimpleAttributeSet();
        doc = pane.getStyledDocument();
        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setPreferredSize(new Dimension(100, 400));
        out.add(scrollPane, BorderLayout.CENTER);
        JScrollPane jscroll = new JScrollPane(list);
        out.add(jscroll);
        JPanel inp = new JPanel();
        inp.setLayout(new BoxLayout(inp, BoxLayout.LINE_AXIS));
        txt = new JTextField(30);
        but = new JButton("Enviar");
        txt.addActionListener(this);
        but.addActionListener(this);
        inp.add(txt);
        inp.add(but);
        frame.add(out, BorderLayout.CENTER);
        frame.add(inp, BorderLayout.PAGE_END);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(450, 250));
        frame.setVisible(true);
    }

}