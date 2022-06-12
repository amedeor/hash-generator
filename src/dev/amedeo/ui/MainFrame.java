/**
 * Project: HashComparisonUtility
 * File: MainFrame.java
 * Date: Apr. 2, 2021
 * Time: 9:46:09 a.m.
 */
package dev.amedeo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.commons.codec.digest.DigestUtils;

import net.miginfocom.swing.MigLayout;

/**
 * @author Amedeo
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField filePathTextField;
	private JLabel chooseFileLabel;
	private JComboBox comboBox;
	private JTextField hashTextField;
	private JButton generateHashButton;

	File selectedFile;
	private JMenuBar menuBar;
	private JMenu aboutMenu;
	private JMenuItem aboutMenuItem;
	private JButton compareHashButton;
	private JTextField comparingHashTextField;
	private JLabel hashValidationLabel;
	private String finalHash;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(595, 389);
		setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		aboutMenu = new JMenu("Help");
		menuBar.add(aboutMenu);

		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, String.format("%s%n%s", "Hash Generator", "By Amedeo"), "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		aboutMenu.add(aboutMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][][]"));

		JFileChooser fileChooser = new JFileChooser();

		chooseFileLabel = new JLabel("Choose a file:");
		chooseFileLabel.setForeground(Color.BLACK);
		contentPane.add(chooseFileLabel, "cell 0 0");

		filePathTextField = new JTextField();
		contentPane.add(filePathTextField, "flowx,cell 0 1,growx");
		filePathTextField.setColumns(10);

		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(MainFrame.this);

				if (returnValue == JFileChooser.APPROVE_OPTION)
				{
					selectedFile = fileChooser.getSelectedFile();
					if (selectedFile != null) {
						filePathTextField.setText(selectedFile.getAbsolutePath());
					}
				}
			}
		});
		contentPane.add(browseButton, "cell 0 1");

		String[] hashTypeArray = { "MD5", "SHA-1", "SHA-256", "SHA-512" };
		comboBox = new JComboBox(hashTypeArray);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String hashTypeName = (String) comboBox.getSelectedItem();
			}
		});
		contentPane.add(comboBox, "cell 0 2,growx");

		generateHashButton = new JButton("Generate hash");
		generateHashButton.addActionListener(new ActionListener() {
			String generatedHash = null;
			@Override
			public void actionPerformed(ActionEvent e) {
						String hashType = (String) comboBox.getSelectedItem();
						if (hashType.equals("MD5")) {
							try {
								InputStream inputStream = Files.newInputStream(Paths.get(filePathTextField.getText()));

								SwingWorker<String, Void> swingWorker = new SwingWorker<String, Void>() {
									@Override
									public String doInBackground() {
										try {
											generatedHash = DigestUtils.sha1Hex(inputStream);
										} catch (Exception e) {

										}
										return generatedHash;
									}

									@Override public void done() {
										try {
											finalHash = get();
										} catch (InterruptedException | ExecutionException e)
										{

										}
									}
								};
								swingWorker.execute();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							hashTextField.setText(finalHash);
						} else if (hashType.equals("SHA-1")) {
							try {
								InputStream inputStream = Files.newInputStream(Paths.get(filePathTextField.getText()));
								generatedHash = DigestUtils.sha1Hex(inputStream);
							} catch (IOException e1) {
							}
							hashTextField.setText(generatedHash);
						} else if (hashType.equals("SHA-256")) {
							try {
								InputStream inputStream = Files.newInputStream(Paths.get(filePathTextField.getText()));
								generatedHash = DigestUtils.sha256Hex(inputStream);
							} catch (IOException e1) {
							}
							hashTextField.setText(generatedHash);
						} else if (hashType.equals("SHA-512")) {
							try {
								InputStream inputStream = Files.newInputStream(Paths.get(filePathTextField.getText()));
								generatedHash = DigestUtils.sha512Hex(inputStream);
							} catch (IOException e1) {
							}
							hashTextField.setText(generatedHash);
						}
					}

				});

		contentPane.add(generateHashButton, "cell 0 4,alignx center");

		hashValidationLabel = new JLabel("");
		hashValidationLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(hashValidationLabel, "cell 0 9,alignx center");

		hashTextField = new JTextField();
		contentPane.add(hashTextField, "cell 0 5,growx");
		hashTextField.setColumns(10);

		compareHashButton = new JButton("Compare hash");
		compareHashButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String generatedHash = hashTextField.getText();
				String hashToCompare = comparingHashTextField.getText();

				if (generatedHash.equals(hashToCompare)) {
					hashValidationLabel.setForeground(Color.green);
					hashValidationLabel.setText("Hashes match");
				} else if (!generatedHash.equals(hashToCompare)) {
					hashValidationLabel.setForeground(Color.red);
					hashValidationLabel.setText("Hashes DO NOT match");
				}

			}
		});
		contentPane.add(compareHashButton, "cell 0 7,alignx center");

		comparingHashTextField = new JTextField();
		contentPane.add(comparingHashTextField, "cell 0 8,growx");
		comparingHashTextField.setColumns(10);
	}
}
