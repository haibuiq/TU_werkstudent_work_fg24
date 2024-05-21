package tud.bp.group32.UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * @author Amar Ademi
 */
public abstract class AbstractModal extends JDialog {
    /**
     * Creates an instance of the AbstractModal,
     * which serves to allow the users to easily
     * choose a new save location for the log file.
     * @param owner The GUI instance to which this modal belongs to.
     */
    public AbstractModal(final Window owner) {
        super(owner);
        initComponents();
        setActionListeners();
    }

    protected abstract String getModalTitle();
    protected abstract String getModalContent();
    protected abstract void browse();
    protected abstract boolean changePath();

    protected final void okButton() {
        if (changePath()) {
            setVisible(false);
        }
    }

    protected final void cancelButton() {
        setVisible(false);
    }

    private void setActionListeners() {
        this.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                okButton();
            }
        });
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cancelButton();
            }
        });
        this.button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                browse();
            }
        });
    }

    protected final void setInputText(final String text) {
        textField1.setText(text);
    }
    protected final String getInputText() {
        return textField1.getText();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY
        // GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amar Ademi
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        buttonBar = new JPanel();
        button1 = new JButton();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setAlwaysOnTop(true);
        setModal(true);
        setResizable(false);
        setTitle(getModalTitle());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        dialogPane.setLayout(new BorderLayout());
        contentPanel.setLayout(new MigLayout(
            "insets dialog,hidemode 3",
            // columns
            "[grow,fill]"
            + "[grow,fill]"
            + "[grow,fill]"
            + "[grow,fill]"
            + "[grow,shrink 0,fill]"
            + "[grow,shrink 0,fill]",
            // rows
            "[grow]"
            + "[grow]"));
        //---- label1 ----
        label1.setText(getModalContent());
        contentPanel.add(label1, "cell 0 0 4 1");
        contentPanel.add(textField1, "cell 0 1 6 1");
        dialogPane.add(contentPanel, BorderLayout.CENTER);
        buttonBar.setLayout(new MigLayout(
            "insets dialog,alignx right",
            // columns
            "[fill]"
            + "[fill]"
            + "[fill]"
            + "[button,fill]"
            + "[button,fill]",
            // rows
            null));
        //---- button1 ----
        button1.setText(
            "Speicherort ausw\u00e4hlen...");
        buttonBar.add(button1, "cell 0 0");
        //---- okButton ----
        okButton.setText("OK");
        buttonBar.add(okButton, "cell 3 0");
        //---- cancelButton ----
        cancelButton.setText("Abbrechen");
        buttonBar.add(cancelButton, "cell 4 0");
        dialogPane.add(buttonBar, BorderLayout.SOUTH);
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization
        // GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY
    // GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amar Ademi
    /**
     * The Dialog window pane.
     */
     private JPanel dialogPane;
    /**
     * The panel containing all the contents.
     */
    private JPanel contentPanel;
    /**
     * The label.
     */
    private JLabel label1;
    /**
     * The input path text box.
     */
    private JTextField textField1;
    /**
     * The footer button bar.
     */
    private JPanel buttonBar;
    /**
     * The browse button.
     */
    private JButton button1;
    /**
     * The OK button.
     */
    private JButton okButton;
    /**
     * The cancel button.
     */
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration
    // GEN-END:variables  @formatter:on
}
