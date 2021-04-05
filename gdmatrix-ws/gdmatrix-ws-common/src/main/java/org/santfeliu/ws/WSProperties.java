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
package org.santfeliu.ws;

import org.santfeliu.util.MatrixConfig;

/**
 *
 * @author realor
 */
public class WSProperties
{
  protected String endpointName;
  protected Class clazz;
  
  public WSProperties(String endpointName, Class clazz)
  {
    this.endpointName = endpointName;
    this.clazz = clazz;
  }

  public boolean getBoolean(String name, boolean defaultValue)
  {
    String value = getString(name);
    if (value == null) return defaultValue;
    return Boolean.valueOf(value);
  }
  
  public int getInteger(String name, int defaultValue)
  {
    String value = getString(name);
    if (value == null) return defaultValue;
    return Integer.parseInt(value);
  }
  
  public String getString(String name, String defaultValue)
  {    
    String value = getString(name);
    return value == null ? defaultValue : value;
  }

  public String getString(String name)
  {    
    String value = MatrixConfig.getProperty(endpointName + "." + name);
    if (value == null)
    {
      value = MatrixConfig.getProperty(clazz.getName() + "." + name);
    }
    return value;
  }
}
