package ehacks.mod.packetlogger;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditPacketBlackList
        extends JFrame
        implements ActionListener {

    private final JButton accept = new JButton("Save");
    private final JButton reset = new JButton("Close");
    private final JTextArea[] areas = new JTextArea[3];
    private final String[] titles = new String[]{"Inbound", "Logged", "Outbound"};
    private final JPanel buttonsPanel = new JPanel();
    private final Gui gui;

    public EditPacketBlackList(Gui gui) {
        super("PacketLogger blacklist");
        this.gui = gui;
        this.setLayout(new BoxLayout(this.getContentPane(), 1));
        this.buttonsPanel.setLayout(new FlowLayout());
        this.construct();
        this.addToLists();
        this.reset.addActionListener(this);
        this.accept.addActionListener(this);
        this.buttonsPanel.add(this.reset);
        this.buttonsPanel.add(this.accept);
        this.add(this.buttonsPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    private void construct() {
        for (int i = 0; i < 3; ++i) {
            this.areas[i] = new JTextArea(10, 50);
            this.areas[i].setFont(gui.FONT);
            JScrollPane scroll = new JScrollPane(this.areas[i]);
            scroll.setBorder(gui.customTitledBorder(this.titles[i], 2));
            this.add(scroll);
        }
        this.addToLists();
    }

    private List<String> getList(int num) {
        switch (num) {
            case 0: {
                return PacketHandler.inBlackList;
            }
            case 1: {
                return PacketHandler.logBlackList;
            }
            case 2: {
                return PacketHandler.outBlackList;
            }
        }
        return null;
    }

    private void addToLists() {
        for (int i = 0; i < 3; ++i) {
            this.areas[i].setText("");
            for (String crit : this.getList(i)) {
                this.areas[i].append(crit + "\n");
            }
        }
    }

    private void acceptList() {
        for (int i = 0; i < 3; ++i) {
            String[] newList;
            this.getList(i).clear();
            for (String newCrit : newList = this.areas[i].getText().split("\n")) {
                if (newCrit.isEmpty()) {
                    continue;
                }
                this.getList(i).add(newCrit);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == this.accept) {
            this.acceptList();
            this.dispose();
        } else if (src == this.reset) {
            PacketHandler.newList();
            this.addToLists();
        }
    }
}
