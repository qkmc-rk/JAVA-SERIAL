package org.cat.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.cat.dao.ReadAndInsertData;
import org.cat.jni.SerialPort.SerialPort;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class MainAvtivity extends JFrame {

	/**
	 * serialportConnection UI
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField IPField;
	private JTextField DatabaseField;
	private JTextField PortField;
	private JTextField UsernameField;
	private JTextField PasswordField;
	ReadAndInsertData raid = new ReadAndInsertData();
	SerialPort sp = new SerialPort();
	
	
	/**
	 * 主函数
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainAvtivity frame = new MainAvtivity();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainAvtivity() {
		setTitle("Mr.Ruan qkmc@outlook.com");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\cat\\Desktop\\\u65B0\u5EFA\u6587\u4EF6\u5939\\icon.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 587, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setBounds(10, 20, 265, 385);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel IPLabel = new JLabel("\u670D\u52A1\u5668IP:");
		IPLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		IPLabel.setBounds(10, 104, 59, 15);
		panel.add(IPLabel);
		
		JLabel mainlabel1 = new JLabel("\u6570\u636E\u5E93\u64CD\u4F5C");
		mainlabel1.setBounds(90, 26, 80, 22);
		mainlabel1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		panel.add(mainlabel1);
		
		JLabel DatabaseLabel = new JLabel("\u5E93\u540D:");
		DatabaseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		DatabaseLabel.setBounds(10, 142, 59, 15);
		panel.add(DatabaseLabel);
		
		JLabel PortLabel = new JLabel("\u7AEF\u53E3\u53F7:");
		PortLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		PortLabel.setBounds(10, 180, 59, 15);
		panel.add(PortLabel);
		
		JLabel UsernameLabel = new JLabel("\u7528\u6237\u540D:");
		UsernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		UsernameLabel.setBounds(10, 221, 59, 15);
		panel.add(UsernameLabel);
		
		JLabel PassworldLabel = new JLabel("\u5BC6\u7801:");
		PassworldLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		PassworldLabel.setBounds(10, 260, 59, 15);
		panel.add(PassworldLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		panel_1.setForeground(Color.ORANGE);
		panel_1.setBounds(295, 20, 265, 385);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel SPortLabel = new JLabel("\u4E32\u53E3\u53F7:");
		SPortLabel.setBounds(-75, 59, 198, 15);
		panel_1.add(SPortLabel);
		SPortLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel label = new JLabel("\u6CE2\u7279\u7387:");
		label.setBounds(-75, 87, 198, 15);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("\u6821\u9A8C\u4F4D:");
		label_1.setBounds(-75, 115, 198, 15);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(label_1);
		
		JComboBox<String> ParitycomboBox = new JComboBox<String>();
		ParitycomboBox.setBounds(151, 112, 64, 21);
		ParitycomboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"0"}));
		panel_1.add(ParitycomboBox);
		
		JComboBox<String> BaudRatiocomboBox = new JComboBox<String>();
		BaudRatiocomboBox.setBounds(151, 84, 64, 21);
		BaudRatiocomboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"4800", "9600", "19200", "38400", "57600", "115200"}));
		panel_1.add(BaudRatiocomboBox);
		
		JLabel label_2 = new JLabel("\u505C\u6B62\u4F4D:");
		label_2.setBounds(-75, 143, 198, 15);
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(label_2);
		
		JComboBox<String> StopBitscomboBox = new JComboBox<String>();
		StopBitscomboBox.setBounds(151, 140, 64, 21);
		StopBitscomboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "1.5", "2"}));
		panel_1.add(StopBitscomboBox);
		
		JLabel label_3 = new JLabel("\u6570\u636E\u4F4D:");
		label_3.setBounds(-75, 174, 198, 15);
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(label_3);
		
		JComboBox<String> BytesizeComboBox = new JComboBox<String>();
		BytesizeComboBox.setBounds(151, 171, 64, 21);
		BytesizeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"8", "7", "6", "9"}));
		panel_1.add(BytesizeComboBox);
		
		JTextArea ReadPortTextField = new JTextArea();
		ReadPortTextField.setBounds(1, 1, 199, 164);
		panel_1.add(ReadPortTextField);
		ReadPortTextField.setForeground(new Color(143, 188, 143));
		ReadPortTextField.setEditable(false);
		ReadPortTextField.setFont(new Font("Monospaced", Font.PLAIN, 13));
		ReadPortTextField.setRows(8);
		ReadPortTextField.setBackground(UIManager.getColor("CheckBox.background"));
		ReadPortTextField.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(ReadPortTextField);
		scrollPane.setBounds(25, 213, 218, 128);
		panel_1.add(scrollPane);
		
		IPField = new JTextField();
		IPField.setBounds(79, 101, 142, 21);
		panel.add(IPField);
		IPField.setColumns(10);
		
		DatabaseField = new JTextField();
		DatabaseField.setColumns(10);
		DatabaseField.setBounds(79, 139, 142, 21);
		panel.add(DatabaseField);
		
		PortField = new JTextField();
		PortField.setColumns(10);
		PortField.setBounds(79, 177, 142, 21);
		panel.add(PortField);
		
		UsernameField = new JTextField();
		UsernameField.setColumns(10);
		UsernameField.setBounds(79, 218, 142, 21);
		panel.add(UsernameField);
		
		JLabel TipInfoLabel = new JLabel("");
		TipInfoLabel.setForeground(new Color(50, 205, 50));
		TipInfoLabel.setBounds(47, 285, 178, 70);
		panel.add(TipInfoLabel);
		
		PasswordField = new JTextField();
		PasswordField.setColumns(10);
		PasswordField.setBounds(79, 257, 142, 21);
		panel.add(PasswordField);
		
		JButton ConnectButton = new JButton("\u5EFA\u7ACB\u8FDE\u63A5");
		ConnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			//打开数据库接口,进行存数据操作
				//首先获得数据库连接
				String ip=IPField.getText();
				String port = PortField.getText();
				String database = DatabaseField.getText();
				String username = UsernameField.getText();
				String pwd = PasswordField.getText();
				if(ip.equals("")||port.equals("") || database.equals("") || username.equals("") || pwd.equals("")){
					JOptionPane.showMessageDialog(null, "请填写完整的数据!");
				}
				else if(ReadAndInsertData.getConnection(ip, port, database, username, pwd)){
					JOptionPane.showMessageDialog(null, "连接数据库成功!");
					TipInfoLabel.setText("数据库连接状态正常");
				}
			}
		});
		ConnectButton.setBounds(89, 352, 93, 23);
		panel.add(ConnectButton);
		
		JLabel mainlabel2 = new JLabel("\u4E32\u53E3\u64CD\u4F5C");
		mainlabel2.setBounds(107, 21, 64, 22);
		mainlabel2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		panel_1.add(mainlabel2);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(151, 56, 64, 21);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {}));
		panel_1.add(comboBox);
		
		JButton button = new JButton("\u8BFB\u53D6\u4E32\u53E3");
		button.setBounds(95, 352, 93, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 		//读取串口按钮,点击后读取数据,并显示数据
				if(ReadAndInsertData.conn == null){
					JOptionPane.showMessageDialog(null, "数据库未成功连接,无法操作!");
					return;
				}	
				int port = Integer.parseInt((comboBox.getSelectedItem().toString().substring(3)));
				System.out.println(port);
				sp.getHandle(port);
				ReadPortTextField.setText(ReadPortTextField.getText()+"\n"+"成功获取串口资源");
				int Baud = Integer.parseInt((String) BaudRatiocomboBox.getSelectedItem());
				int _byte = Integer.parseInt((String)BytesizeComboBox.getSelectedItem());
				int _par = Integer.parseInt((String)ParitycomboBox.getSelectedItem());
				int _stop = Integer.parseInt((String)StopBitscomboBox.getSelectedItem());
				System.out.println("BaudRatio:"+Baud);
				sp.startPort(Baud,_par,_stop,_byte);
				ReadPortTextField.setText(ReadPortTextField.getText()+"\n"+"成功取得串口句柄\n");
				//下面就是不停地读取数据了
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String portData = null;
						while(true){
							portData = sp.readPort();
							ReadPortTextField.setText(ReadPortTextField.getText()+portData+"\n");
							/*if(portData.equals("read serial port failed!")){
								actionPerformed(e);
							}else{
								
							}*/
							if(ReadPortTextField.getText().length()>100)
								ReadPortTextField.setText(portData);
							//存入数据库操作
							try {
								String temp = "0";
								String hum = "0";
								temp = portData.substring(0, 2);
								hum = portData.substring(3,5);
								System.out.println(temp+"    "+hum);
								PreparedStatement pstmt = ReadAndInsertData.conn.prepareStatement("insert into tempandhum(temp,hum)values(?,?)");
								pstmt.setDouble(1, Double.parseDouble(temp));
								pstmt.setDouble(2, Double.parseDouble(hum));
								pstmt.executeUpdate();
								
								//查询是否需要加热
								pstmt = ReadAndInsertData.conn.prepareStatement("select * from ishit");
								ResultSet rs = pstmt.executeQuery();
								if(rs.next()){
									pstmt = ReadAndInsertData.conn.prepareStatement("delete from ishit");
									pstmt.executeUpdate();
									//发送数据给串口,通知加热信号
								sp.sendDatatoP04();
										//ReadPortTextField.setText(ReadPortTextField.getText()+"\n"+"成功发送信号!\n");
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						/*不停读取数据结束*/
					}
				}).start();
					
				
				
			}
		});
		panel_1.add(button);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 49, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u6587    \u4EF6");
		menuBar.add(mnNewMenu);
		
		JMenuItem CloseMenuItem = new JMenuItem("\u5173\u95ED\u5BA2\u6237\u7AEF");
		mnNewMenu.add(CloseMenuItem);
		
		//使用自定义按钮退出程序,关闭数据库,安全
		CloseMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ReadAndInsertData.conn != null){
					try {
						ReadAndInsertData.conn.close();
						System.exit(0);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else{
					System.exit(0);
				}
			}
		});
		
		
		
		
		
		
		
		
		//用于初始化一些必须数据

		int len = sp.getPorts().length;
		int[] a = new int[len];
		a = sp.getPorts();
		for(int i = 0;i<len;i++)
			comboBox.addItem("COM"+a[i]);
	}
}
