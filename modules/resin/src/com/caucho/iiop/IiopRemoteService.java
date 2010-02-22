/*
 * Copyright (c) 1998-2000 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.iiop;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;

abstract public class IiopRemoteService {
  /**
   * Returns the context class loader.
   */
  abstract public ClassLoader getClassLoader();

  /**
   * Returns the home API class.
   */
  abstract public ArrayList<Class> getHomeAPI();

  /**
   * Returns the object API class.
   */
  abstract public ArrayList<Class> getObjectAPI();

  /**
   * Returns the invoked API class.
   */
  public Class getRemoteInterface()
  {
    return null;
  }

  /**
   * Returns true for 3.0 when multiple 2.1/3.0 interfaces are available.
   */
  public boolean isEJB3()
  {
    return false;
  }

  /**
   * Returns the home object.
   */
  abstract public Object getHome();

  /**
   * Returns the object interface.
   */
  abstract public Object getObject(String local)
    throws NoSuchObjectException;
}