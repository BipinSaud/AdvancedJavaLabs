package labs.lab6_swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailSenderApp extends JFrame {

    private JTextField senderField, receiverField, subjectField;
    private JTextArea messageArea;
    private JButton sendButton;

    public EmailSenderApp() {
        setTitle("Email Sender");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for inputs
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Sender Email:"));
        senderField = new JTextField();
        inputPanel.add(senderField);

        inputPanel.add(new JLabel("Receiver Email:"));
        receiverField = new JTextField();
        inputPanel.add(receiverField);

        inputPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);

        inputPanel.add(new JLabel("Message:"));
        messageArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        add(sendButton, BorderLayout.SOUTH);

        // Action listener for button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simulated email sending
                String sender = senderField.getText();
                String receiver = receiverField.getText();
                String subject = subjectField.getText();
                String message = messageArea.getText();

                if (sender.isEmpty() || receiver.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // In real code, use JavaMail API here
                    JOptionPane.showMessageDialog(null, "Email Sent Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmailSenderApp().setVisible(true);
        });
    }
}
