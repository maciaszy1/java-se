package pl.woodbase.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.TableModel;

import pl.woodbase.dao.SqlConn;

public class GUImain implements ActionListener{
	
	private String bmgUrl = "jdbc:sqlserver://WEEKE-W2K12;" +  
			   "databaseName=woodbase;user=sa;password=Weekedbp1;";
	
	private String abdUrl = "jdbc:sqlserver://WEEKE-W2K12;" +  
			   "databaseName=woodbase_abd;user=sa;password=Weekedbp1;";
	
	private JFrame mainFrame = new JFrame("Woodbase_bmg import");
	private SqlConn sqlConn = new SqlConn();
	private JButton addButton = new JButton("Dodaj");
	private JButton deleteButton = new JButton("Usuñ");
	private JButton importButton = new JButton("Importuj");
	private JButton searchButton = new JButton("Szukaj:");
	private JButton refreshButton = new JButton("Odœwie¿");
	private JButton changeDbButton = new JButton("Zmieñ bazê");
	private JTextField searchText = new JTextField(10);
	private JToolBar toolBar = new JToolBar();
	//JPanel toolPanel = new JPanel();
	private TableModel model = sqlConn.getTable(); 
	private JTable dataTable = new JTable(model);
	private JScrollPane tablePane = new JScrollPane(dataTable,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public GUImain() {
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(640,400);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(toolBar, BorderLayout.PAGE_START);
		addButton.addActionListener(this);
		refreshButton.addActionListener(this);
		deleteButton.addActionListener(this);
		searchButton.addActionListener(this);
		changeDbButton.addActionListener(this);
		importButton.addActionListener(this);
		toolBar.add(addButton);
		toolBar.add(deleteButton);
		toolBar.add(refreshButton);
		toolBar.add(importButton);
		toolBar.add(changeDbButton);
		toolBar.add(searchButton);
		toolBar.add(searchText);
		mainFrame.add(tablePane, BorderLayout.CENTER);
		mainFrame.setVisible(true);	
	}
	
	public void refresh() {
		
		dataTable.setModel(sqlConn.getTable());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addButton) {
			GUIadd guiAdd = new GUIadd();
		}
		if(e.getSource() == refreshButton) {
			refresh();
		}
		if(e.getSource() == deleteButton) {
			
			int[] selected = dataTable.getSelectedRows();
			
			for(int i =0; i <= selected.length -1; i++ ) {
				sqlConn.deleteRecord((String)dataTable.getValueAt(selected[i],0),
						(String)dataTable.getValueAt(selected[i],1));
				
			}
			
			refresh();
		}
		if(e.getSource() == searchButton) {
			
			if(searchText.getText().equals("")) {
				refresh();
			}else {
				dataTable.setModel(sqlConn.getTable(Integer.parseInt(searchText.getText())));
			}
			
		}
		if(e.getSource() == changeDbButton ) {
			
			if(sqlConn.getConnectionUrl() == bmgUrl) {
				
				sqlConn.setConnectionUrl(abdUrl);
				mainFrame.setTitle("Woodbase_abd import");
				refresh();	
			}else if(sqlConn.getConnectionUrl() == abdUrl) {
				
				sqlConn.setConnectionUrl(bmgUrl);
				mainFrame.setTitle("Woodbase_bmg import");
				refresh();
			}	
		}
		if(e.getSource() == importButton) {
			
			GUIimport importFrame = new GUIimport();
		}
		
		
	}
	
}
