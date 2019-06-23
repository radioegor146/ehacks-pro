package ehacks.mod.packetlogger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gui
        extends JFrame
        implements ActionListener,
        WindowListener,
        KeyListener {

    public final Font FONT = new Font("Lucida Console", 0, 12);
    public final JCheckBox logInPackets = new JCheckBox("Log");
    public final JCheckBox logOutPackets = new JCheckBox("Log");
    public final JCheckBox moreInfo = new JCheckBox("Detailed info");
    private final JButton editBlackList = new JButton("Edit blacklist");
    private final JCheckBox onTop = new JCheckBox("Always on top");
    private final JToolBar packetLogPanel = new JToolBar();
    private final JToolBar logPanel = new JToolBar();
    private final JPanel inPackets = new JPanel();
    private final JPanel outPackets = new JPanel();
    private final JPanel packetLoggerConfig = new JPanel();
    private final JTextArea logTextArea = new JTextArea(18, 18);
    private final JScrollPane logScroll = new JScrollPane(this.logTextArea, 22, 31);

    public Gui() {
        super("PacketLogger window");
        this.configurate();
        this.inPackets.add(this.logInPackets);
        this.packetLoggerConfig.add(this.moreInfo);
        this.packetLoggerConfig.add(this.editBlackList);
        this.packetLoggerConfig.add(this.onTop);
        this.outPackets.add(this.logOutPackets);
        this.packetLogPanel.add(this.inPackets);
        this.packetLogPanel.add(this.packetLoggerConfig);
        this.packetLogPanel.add(this.outPackets);
        this.packetLogPanel.setMaximumSize(new Dimension(10000, 100));
        this.logPanel.add(this.logScroll);
        this.add(this.packetLogPanel);
        this.add(this.logPanel);
        this.pack();
    }

    private void configurate() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.inPackets.setBorder(this.customTitledBorder("Inbound packets", 2));
        this.packetLoggerConfig.setBorder(this.customTitledBorder("Packet logger settings", 2));
        this.outPackets.setBorder(this.customTitledBorder("Outbound packets", 2));
        this.logScroll.setBorder(this.customTitledBorder("Log"));
        this.logTextArea.setFont(this.FONT);
        this.logTextArea.setLineWrap(true);
        this.logTextArea.setWrapStyleWord(true);
        this.editBlackList.addActionListener(this);
        this.logInPackets.addActionListener(this);
        this.logOutPackets.addActionListener(this);
        this.onTop.addActionListener(this);
        this.addWindowListener(this);
        this.setLayout(new BoxLayout(this.getContentPane(), 1));
    }

    public Border customTitledBorder(String title) {
        return this.customTitledBorder(title, 1);
    }

    public Border customTitledBorder(String title, int align) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleJustification(align);
        return border;
    }

    public void logMessage(Object mess) {
        this.logTextArea.append((this.logTextArea.getText().isEmpty() ? "" : "\n") + new SimpleDateFormat("'['HH:mm:ss'] '").format(new Date()) + " " + (mess != null ? mess : "null"));
        this.logTextArea.setCaretPosition(this.logTextArea.getText().length());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == this.editBlackList) {
            new EditPacketBlackList(this);
        } else if (src == this.onTop) {
            this.setAlwaysOnTop(this.onTop.isSelected());
        } else if ((src == this.logInPackets || src == this.logOutPackets) && (this.logInPackets.isSelected() || this.logOutPackets.isSelected())) {
            this.logMessage(" =================== NEW LOG SESSION ===================");
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}
