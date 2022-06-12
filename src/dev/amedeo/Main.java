/**
 * Project: HashComparisonUtility
 * File: Main.java
 * Date: Apr. 2, 2021
 * Time: 9:44:53 a.m.
 */
package dev.amedeo;

import java.awt.EventQueue;

import dev.amedeo.ui.MainFrame;

/**
 * @author Amedeo
 *
 */
public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
