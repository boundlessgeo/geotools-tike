/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.gui.swing.contexttree.popup;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import org.geotools.gui.swing.contexttree.ContextTreeNode;
import org.geotools.gui.swing.propertyedit.ContextCRSPropertyPanel;
import org.geotools.gui.swing.propertyedit.ContextGeneralPanel;
import org.geotools.gui.swing.propertyedit.JPropertyDialog;
import org.geotools.gui.swing.propertyedit.PropertyPane;
import org.geotools.map.MapContext;

/**
 * Default popup control for property page of MapContext, use for JContextTreePopup 
 * 
 * @author Johann Sorel 
 *
 * @source $URL$
 */
public class ContextPropertyItem extends JMenuItem implements TreePopupItem{
    
    private MapContext context;
    
    /** 
     * Creates a new instance of DefaultContextPropertyPop 
     */
    public ContextPropertyItem() {
        super( BUNDLE.getString("properties")  );
        init();
    }
    
    private void init(){
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<PropertyPane> lst = new ArrayList<PropertyPane>();
                lst.add(new ContextGeneralPanel());
                lst.add(new ContextCRSPropertyPanel());
                JPropertyDialog.showDialog(lst, context);                
            }
        }
        );
    }
            
    public boolean isValid(TreePath[] selection) {
        if (selection.length == 1) {
            ContextTreeNode node = (ContextTreeNode) selection[0].getLastPathComponent();            
            return ( node.getUserObject() instanceof MapContext ) ;
        }
        return false;
    }

    public Component getComponent(TreePath[] selection) {
        ContextTreeNode node = (ContextTreeNode) selection[0].getLastPathComponent();  
        context = (MapContext) node.getUserObject() ;
        
        return this;
    }
    
}
