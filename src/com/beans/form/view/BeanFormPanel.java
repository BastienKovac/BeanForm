package com.beans.form.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.beans.form.model.Student;
import com.beans.form.model.BeanFormException;
import com.beans.form.model.BeanUtils;
import com.beans.form.model.LoadedBeanModel;
import com.beans.form.view.components.ComponentFactory;

public class BeanFormPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private LoadedBeanModel beanModel;
	
	private Map<JLabel, JComponent> beanComponents;
	
	
	public BeanFormPanel(LoadedBeanModel beanModel) {
		super();
		this.beanModel = beanModel;
		this.beanComponents = new HashMap<>();
		buildPanel();
	}
	
	private void buildPanel() {
		Border in = BorderFactory.createEmptyBorder(3, 3, 3, 3);
		Border out = BorderFactory.createTitledBorder(beanModel.getOriginalClass() + " Form");
		setBorder(BorderFactory.createCompoundBorder(out, in));
		buildComponents();
		buildLayout();
	}
	
	private void buildComponents() {
		for (String fieldName : beanModel.getDeclaredFields()) {
			JComponent component = ComponentFactory.getComponentFor(beanModel, fieldName);
			if (component != null) {
				beanComponents.put(new JLabel(BeanUtils.formatFieldName(fieldName)), component);
			}			
		}
	}
	
	private void buildLayout() {
		GroupLayout gLayout = new GroupLayout(this);
		gLayout.setAutoCreateContainerGaps(true);
		gLayout.setAutoCreateGaps(true);
		Group horizontalGroup = gLayout.createSequentialGroup();
		
		Group firstColumn = gLayout.createParallelGroup(Alignment.TRAILING);
		Group secondColumn = gLayout.createParallelGroup(Alignment.LEADING);
		for (Entry<JLabel, JComponent> componentEntry : beanComponents.entrySet()) {
			if (!(componentEntry.getValue() instanceof JCheckBox)) {
				firstColumn.addComponent(componentEntry.getKey());
			}
			secondColumn.addComponent(componentEntry.getValue());
		}
		horizontalGroup.addGroup(firstColumn);
		horizontalGroup.addGroup(secondColumn);
		gLayout.setHorizontalGroup(horizontalGroup);
		
		Group verticalGroup = gLayout.createSequentialGroup();
		for (Entry<JLabel, JComponent> componentEntry : beanComponents.entrySet()) {
			Group rowGroup = gLayout.createParallelGroup(Alignment.BASELINE);
			if (!(componentEntry.getValue() instanceof JCheckBox)) {
				rowGroup.addComponent(componentEntry.getKey());
			}
			rowGroup.addComponent(componentEntry.getValue());
			verticalGroup.addGroup(rowGroup);
		}
		gLayout.setVerticalGroup(verticalGroup);
		
		setLayout(gLayout);
	}
	
	public static void main(String[] args) {
		Student ex = new Student();
		LoadedBeanModel loadedExample = new LoadedBeanModel();
		try {
			loadedExample.loadBean(ex.getClass());
		} catch (BeanFormException e) {
			System.err.println(e.getMessage());
		}
		JPanel pane = new BeanFormPanel(loadedExample);
		JFrame frame = new JFrame("Test");
		frame.setContentPane(pane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
