//Shamelessly stolen from http://codereview.stackexchange.com/questions/25461/simple-chat-room-swing-gui
//Should only be used for demo.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainGUI {

    String      appName     = "Colt Chat v0.1";
    MainGUI     mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatBox;
    JTextField  usernameChooser;
    JFrame      preFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainGUI mainGUI = new MainGUI();
                mainGUI.preDisplay();
            }
        });
    }

    public void preDisplay() {
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        usernameChooser = new JTextField(15);
        JLabel chooseUsernameLabel = new JLabel("Pick a username:");
        usernameChooser.addActionListener(new enterServerButtonListener());
        JButton enterServer = new JButton("Enter Chat Server");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        // preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(400, 400);
        preFrame.setVisible(true);

    }

    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();
        messageBox.addActionListener(new sendMessageButtonListener());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	messageBox.requestFocus();
            }}); 

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                chatBox.append("<" + username + ">:  " + messageBox.getText()
                        + "\n");
               
                String response = "";
                String searchValue = doStringSearch(messageBox.getText());
                if (searchValue.equals("N/A")){
                	chatBox.append("<ChatBot>: Nothing found!\n");
                }
                else {
                	
                	try {
                		response = getResponse(searchValue);
                	} catch (IOException e) {
					// TODO Auto-generated catch block
                		e.printStackTrace();
                	}
                	messageBox.setText("");
                	chatBox.append("<ChatBot>: " + response + "\n");
                }
            }
            messageBox.requestFocusInWindow();
        }

		private String doStringSearch(String text) {
			String[] tokens = text.split("\\s+");
			for (int i = 0; i < tokens.length; i++){
				if (tokens[i].equals("slow")){
					return "slow";
				}
				if (tokens[i].equals("internet")){
					return "internet";
				}
				if (tokens[i].equals("word")){
					return "word";
				}
				if (tokens[i].equals("setup")){
					return "setup";
				}
			}
			return "N/A";
		}

		private String getResponse(String text) throws IOException {
			// TODO Auto-generated method stub
			//System.out.println(text);
			String currentDirectory = new java.io.File( "." ).getCanonicalPath();
			String workingDirectory = currentDirectory + "\\files";
			File folder = new File(workingDirectory);
			File[] allFiles = folder.listFiles();
			String response = "";
			for (int i = 0; i < allFiles.length; i++){
				response += parseFile(allFiles[i], text);
			}
			return response;
		}

		private String parseFile(File file, String text) throws IOException {
			// TODO Auto-generated method stub
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			String response = "";
			while ((line = br.readLine()) != null){
				//split into word tokens
				//String lineNonAlphanumeric = line.toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
				//System.out.println(lineNonAlphanumeric);
				String[] lineTokens = line.split("\\s+");
				//System.out.println(lineTokens.length);
				boolean printLine = false;
				
				for (int i = 0; i < lineTokens.length; i++){
					//System.out.println(lineTokens[i]);
					if (lineTokens[i].equals(text)){
						//System.out.println("Yay");
						printLine = true;
					}
				}
				if (printLine == true){
					response += line;
					//System.out.println(line);
					//System.out.println(response.indexOf(':'));
					response = response.substring(response.indexOf(':')+2);
					break;
					//System.out.println(line);
				}
				
//				if (line.toLowerCase().contains(keyword)){
//					System.out.println(line);
//				}
			}
			return response;
			
		}
    }

    String  username;

    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1) {
                System.out.println("No!");
            } else {
                preFrame.setVisible(false);
                display();
            }
        }

    }
}