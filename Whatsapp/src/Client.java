import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Font;
import javax.swing.*;

public class Client extends JFrame implements ActionListener {

	JPanel p1;
	JTextField t1;
	JButton b1;
	static JTextArea a1;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	Boolean typing;

	Client() {
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(7, 94, 84));
		p1.setBounds(0, 0, 550, 70);
		add(p1);
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);

		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});

		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/2.png"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);
		JLabel l3 = new JLabel("vivek");
		l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 10, 100, 20);
		p1.add(l3);
		JLabel l4 = new JLabel("Active now");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 35, 100, 20);
		p1.add(l4);

		Timer t = new Timer(1, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!typing) {
					l4.setText("Active Now");

				}
			}

		});
		t.setInitialDelay(2000);

		t1 = new JTextField();
		t1.setBounds(5, 660, 310, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		add(t1);

		t1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				l4.setText("Typing...");
				t.stop();

			}

			public void keyReleased(KeyEvent ke) {
				typing = false;
				if (!t.isRunning()) {
					t.start();
				}
			}
		});

		b1 = new JButton("Send");
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
		b1.setBounds(320, 660, 120, 40);
		b1.setBackground(new Color(7, 94, 84));
		b1.addActionListener(this);
		add(b1);

		a1 = new JTextArea();
		a1.setBounds(5, 75, 500, 580);
		a1.setBackground(Color.WHITE);
		a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		a1.setEditable(false);
		a1.setLineWrap(true);
		a1.setWrapStyleWord(true);
		add(a1);

		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/video.png"));
		Image i8 = i7.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel l5 = new JLabel(i9);
		l5.setBounds(300, 20, 35, 30);
		p1.add(l5);
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icon/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel l6 = new JLabel(i12);
		l6.setBounds(350, 20, 35, 30);
		p1.add(l6);
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel l7 = new JLabel(i15);
		l7.setBounds(410, 20, 15, 30);
		p1.add(l7);

		setLayout(null);
		setSize(450, 700);
		setLocation(1100, 200);

		setUndecorated(true);
		setVisible(true);
	}

	public static void main(String args[]) {
		new Client().setVisible(true);

		try {
			s = new Socket("127.0.0.1", 6001);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			String msginput = "";
			msginput = din.readUTF();
			a1.setText(a1.getText() + "\n" + msginput);
		} catch (Exception e) {

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String out = t1.getText();
			sendTextToFile(out);
			a1.setText(a1.getText() + "\n\t\t\t" + out);
			dout.writeUTF(out);
			t1.setText("");
		} catch (Exception ae) {

		}

	}

	private void sendTextToFile(String message) {
		try {
			FileWriter f = new FileWriter("chat.txt", true);
			BufferedWriter buffer = new BufferedWriter(f);
			PrintWriter writer = new PrintWriter(buffer);
			writer.println("Vivek : " + message);
			buffer.close();

		} catch (Exception e) {

		}

	}
}
