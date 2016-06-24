package de.javamaps.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class DhMaps extends JPanel implements ActionListener  {
	public static void main(String args[]) {
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
		  petList.addActionListener();
		 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
