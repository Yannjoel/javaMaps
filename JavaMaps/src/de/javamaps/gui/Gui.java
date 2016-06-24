package de.javamaps.gui;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

public class Gui extends JPanel implements ActionListener {
	public void Gui() {
		JFrame Window = new JFrame("Bestes Mapprogramm ever!!!");
		Window.setSize(400, 400);
		Window.add(new JLabel("Hier könnte ihre werbung stehen"));
		Window.setVisible(true);
		/*
		 * JDialog popup = new JDialog(); // Titel wird gesetzt popup.setTitle(
		 * "Mein JDialog Beispiel"); popup.setSize(200,200); popup.add(new
		 * JLabel("Fehler! SIe haben vierus")); popup.setModal(true);
		 * popup.setVisible(true);
		 */
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

		JComboBox<String> petList = new JComboBox<String>(petStrings);
		petList.setSelectedIndex(4);
		petList.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
