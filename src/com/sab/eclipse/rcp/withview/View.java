package com.sab.eclipse.rcp.withview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;


public class View extends ViewPart {
	public static final String ID = "com.sab.eclipse.rcp.withview.view";

	@Inject IWorkbench workbench;
	
	private TableViewer viewer;
	
	private class StringLabelProvider extends ColumnLabelProvider {
		
		// getText method is used from super class ColumnLabelProvider

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

	}

//	@Override
//	public void createPartControl(Composite parent) {
//		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
//		viewer.getTable().setLinesVisible(true);
//
//		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
//		column.setLabelProvider(new StringLabelProvider());
//
//		viewer.getTable().getColumn(0).setWidth(200);
//		
//		viewer.setContentProvider(ArrayContentProvider.getInstance());
//		
//		// Provide the input to the ContentProvider
//		viewer.setInput(createDataModel());
//	}
	
	 public void createPartControl(Composite parent) {
	        GridLayout layout = new GridLayout(2, false);
	        parent.setLayout(layout);
	        Label searchLabel = new Label(parent, SWT.NONE);
	        searchLabel.setText("Search: ");
	        final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
	        searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
	                | GridData.HORIZONTAL_ALIGN_FILL));
	        createViewer(parent);
	    }

	 
	 private void createViewer(Composite parent) {
	        viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
	                | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	        createColumns(parent, viewer);
	        final Table table = viewer.getTable();
	        table.setHeaderVisible(true);
	        table.setLinesVisible(true);

	        viewer.setContentProvider(new ArrayContentProvider());
	        // Get the content for the viewer, setInput will call getElements in the
	        // contentProvider
	        viewer.setInput(createDataModel());
	        
	     // Create a menu manager and create context menu
	        MenuManager menuManager = new MenuManager();
	        Menu menu = menuManager.createContextMenu(viewer.getTable());
	        // set the menu on the SWT widget
	        viewer.getTable().setMenu(menu);
	        // register the menu with the framework
	        getSite().registerContextMenu(menuManager, viewer);

	        // make the selection available to other views
	        getSite().setSelectionProvider(viewer);
	        // Set the sorter for the table

	        // Layout the viewer
	        GridData gridData = new GridData();
	        gridData.verticalAlignment = GridData.FILL;
	        gridData.horizontalSpan = 2;
	        gridData.grabExcessHorizontalSpace = true;
	        gridData.grabExcessVerticalSpace = true;
	        gridData.horizontalAlignment = GridData.FILL;
	        viewer.getControl().setLayoutData(gridData);
	        
	    }

	// This will create the columns for the table
	    private void createColumns(final Composite parent, final TableViewer viewer) {
	        String[] titles = { "First name", "Last name", "Age"};
	        int[] bounds = { 100, 100, 100 };

	        // First column is for the first name
	        TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	                Person p = (Person) element;
	                return p.getFirstName();
	            }
	        });

	        // Second column is for the last name
	        col = createTableViewerColumn(titles[1], bounds[1], 1);
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	                Person p = (Person) element;
	                return p.getLastName();
	            }
	        });

	        // now the gender
	        col = createTableViewerColumn(titles[2], bounds[2], 2);
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	                Person p = (Person) element;
	                return Integer.toString(p.getAge());
	            }
	        });


	    }
	    
	    
	    private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
	        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
	                SWT.NONE);
	        final TableColumn column = viewerColumn.getColumn();
	        column.setText(title);
	        column.setWidth(bound);
	        column.setResizable(true);
	        column.setMoveable(true);
	        return viewerColumn;

	    }
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private List<String> createInitialDataModel() {
		return Arrays.asList("One", "Two", "Three");
	}
	
	
	public static List<Person> createDataModel() {
		List<Person> persons = new ArrayList<Person>();
		
	    Person p1 = new Person("Steve", "Large", 43);
		persons.add(p1);
		
		Person p2 = new Person("John", "Volkens", 35);
		persons.add(p2);
		
		Person p3 = new Person("Kessler", "Parker", 29);
		persons.add(p3);
		
		Person p4 = new Person("Michael", "Dent", 40);
		persons.add(p4);
		
		Person p5 = new Person("Jonathan", "Cox", 33);
		persons.add(p5);
		
		Person p6 = new Person("Kris", "Klindworth", 33);
		persons.add(p6);
		
		Person p7 = new Person("Beth", "Nepote", 44);
		persons.add(p7);
		
		Person p8 = new Person("Sindhu", "Chitikela", 27);
		persons.add(p8);
		
		Person p9 = new Person("Mike", "Barns", 33);
		persons.add(p9);
		
		Person p10 = new Person("Sabir", "Pasha", 32);
		persons.add(p10);
		
		return persons;
	}
		
}