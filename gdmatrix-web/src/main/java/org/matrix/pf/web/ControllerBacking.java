/*
 * GDMatrix
 *  
 * Copyright (C) 2020, Ajuntament de Sant Feliu de Llobregat
 *  
 * This program is licensed and may be used, modified and redistributed under 
 * the terms of the European Public License (EUPL), either version 1.1 or (at 
 * your option) any later version as soon as they are approved by the European 
 * Commission.
 *  
 * Alternatively, you may redistribute and/or modify this program under the 
 * terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation; either  version 3 of the License, or (at your option) 
 * any later version. 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *    
 * See the licenses for the specific language governing permissions, limitations 
 * and more details.
 *    
 * You should have received a copy of the EUPL1.1 and the LGPLv3 licenses along 
 * with this program; if not, you may find them at: 
 *    
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * http://www.gnu.org/licenses/ 
 * and 
 * https://www.gnu.org/licenses/lgpl.txt
 */
package org.matrix.pf.web;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.matrix.web.WebUtils;
import org.matrix.web.ReturnStack;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;
import org.santfeliu.faces.beansaver.Savable;
import org.santfeliu.faces.menu.model.MenuItemCursor;
import org.santfeliu.web.UserSessionBean;

/**
 *
 * @author blanquepa
 */
@Named("controllerBacking")
public class ControllerBacking extends WebBacking implements Savable
{
  private PageHistory pageHistory = new PageHistory();  
  private ReturnStack<ReturnStackEntry> returnStack = new ReturnStack();
  
  public static ControllerBacking getCurrentInstance()
  {
    return WebUtils.getInstance(ControllerBacking.class);
  }  

  //Return stack
  public ReturnStack getReturnStack()
  {
    return returnStack;
  }

  public void setReturnStack(ReturnStack returnStack)
  {
    this.returnStack = returnStack;
  }
  
  public boolean isReturnStackEmpty()
  {
    return returnStack.isEmpty();
  }
  
  //Page history
  public PageHistory getPageHistory()
  {
    return pageHistory;
  }

  public void setPageHistory(PageHistory pageHistory)
  {
    this.pageHistory = pageHistory;
  }
    
  //Controller commands  
  public String show(String objectTypeId, String objectId, Integer tabIndex, 
    String pageId)
  {
    String outcome;
     
    ObjectBacking targetObjectBacking = getObjectBacking(objectTypeId);
    targetObjectBacking.setObjectId(objectId); 
         
    //PageHistory
    MenuItemCursor currentMenuItem = UserSessionBean.getCurrentInstance()
      .getMenuModel().getSelectedMenuItem();    
    MenuItemCursor targetMenuItem =
      MenuTypesCache.getInstance().get(currentMenuItem, objectTypeId);
    String targetMid = targetMenuItem.getMid();
    String oldTypeId = getObjectTypeId(currentMenuItem);   
    pageHistory.visit(targetMid, objectId, oldTypeId);  
    
    targetObjectBacking.setTabIndex(tabIndex);           
    
    PageBacking targetPageBacking;
    if (!StringUtils.isBlank(objectId))
    {
      Tab tab = targetObjectBacking.getCurrentTab();      
      if (pageId != null)
      {
        targetPageBacking = WebUtils.getBackingFromAction(tab.getAction());
        outcome = targetPageBacking.show(pageId);
      }
      else
        outcome = tab.executeAction();     
    }
    else //SearchBacking
    {
      targetPageBacking = targetObjectBacking.getSearchBacking();
      outcome = targetPageBacking.show();
    }
    
    targetMenuItem.select();
    
    return outcome;      
  }
  
  public String show(String objectTypeId, String objectId)
  {
    return show(objectTypeId, objectId, 0, null);
  }  
  
  public String show(String objectId)
  {    
    String objectTypeId = getObjectTypeId();
    return show(objectTypeId, objectId);    
  }  
  
  public String show(String objectTypeId, String objectId, String tabTypeId, 
    String pageId)
  { 
    ObjectBacking targetObjectBacking = getObjectBacking(objectTypeId);
    Tab tab = targetObjectBacking.getTab(tabTypeId);
     
    return show(objectTypeId, objectId, tab.getIndex(), pageId);   
  }
  
  public String show(String objectTypeId, String objectId, String pageId)
  {
    return show(objectTypeId, objectId, 0, pageId);
  }
     
  public String search(String targetObjectTypeId, String valueBinding)
  { 
    MenuItemCursor currentMenuItem = getSelectedMenuItem();    

    //Push entry to ReturnStack
    ObjectBacking objectBacking = getObjectBacking(currentMenuItem);
    String returnTypeId = getObjectTypeId();    
    String returnObjectId = objectBacking.getObjectId();
    Tab currentTab = objectBacking.getCurrentTab();
    Integer returnTabIndex = currentTab.getIndex();
    String returnPageId = null; //TODO
    ReturnStackEntry entry = new ReturnStackEntry(returnTypeId, 
      returnObjectId, returnTabIndex, returnPageId, valueBinding);
    returnStack.push(entry);    

    //Manage page history
    MenuItemCursor targetMenuItem = 
      MenuTypesCache.getInstance().get(currentMenuItem, targetObjectTypeId);    
    pageHistory.visit(targetMenuItem.getMid(), null, returnTypeId);
    
    ObjectBacking targetObjectBacking = getObjectBacking(targetMenuItem); 
    SearchBacking searchPage = targetObjectBacking.getSearchBacking();
       
    targetMenuItem.select();
        
    return searchPage.show();
  }  
  
  public String select(String selectedObjectId)
  {
    if (returnStack.isEmpty()) return null;

    ReturnStackEntry entry = returnStack.pop();
    String returnTypeId = entry.getReturnTypeId();
    String returnObjectId = entry.getObjectId();
    Integer returnTabIndex = entry.getTabIndex();
    String returnPageId = entry.getPageId();
    String valueBinding = entry.getValueBinding(); 
    setValueBinding(valueBinding, selectedObjectId);
        
    return show(returnTypeId, returnObjectId, returnTabIndex, returnPageId);
  }

  public ObjectBacking getObjectBacking(String typeId)
  {
    MenuItemCursor currentMenuItem = UserSessionBean.getCurrentInstance()
      .getMenuModel().getSelectedMenuItem();
    MenuItemCursor mic = 
      MenuTypesCache.getInstance().get(currentMenuItem, typeId);
    
    if (!mic.isNull())
      return getObjectBacking(mic);  
    else
      return null;
  }
  
  public ObjectBacking getObjectBacking()
  {
    return toObjectBacking(getCurrentBacking());
  } 
  
  public ObjectBacking getObjectBacking(MenuItemCursor mic)
  {
    return toObjectBacking(WebUtils.getBacking(mic));
  }  
  
  //Private methods  
  private WebBacking getCurrentBacking()
  {
    MenuItemCursor mic =
      UserSessionBean.getCurrentInstance().getMenuModel().getSelectedMenuItem();
    return WebUtils.getBacking(mic);
  }

  private ObjectBacking toObjectBacking(WebBacking backing)
  {
    if (backing instanceof ObjectBacking)
      return (ObjectBacking) backing;
    else if (backing instanceof PageBacking)
      return ((PageBacking) backing).getObjectBacking(); 
    else
      return null;
  }  
  
  private void setValueBinding(String valueBinding, String value)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    ValueBinding vb = 
      context.getApplication().createValueBinding(valueBinding);
    vb.setValue(context, value);
  }   
  
}
