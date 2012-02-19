/*
 * Copyright (c) 1998-2012 Caucho Technology -- all rights reserved
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

package com.caucho.mqueue.amqp;

import java.io.IOException;

import com.caucho.mqueue.amqp.AmqpLinkAttach.Role;

/**
 * AMQP link flow
 */
public class AmqpDisposition extends AmqpAbstractComposite {
  public static final int CODE = AmqpConstants.FT_LINK_FLOW;

  private Role _role = Role.SENDER; // ubyte (required)
  private long _first; // uint seq (required)
  private long _last; // uint seq
  private boolean _isSettled;
  private AmqpDeliveryState _state; // delivery-state
  private boolean _isBatchable;
  
  public Role getRole()
  {
    return _role;
  }
  
  public long getFirst()
  {
    return _first;
  }
  
  public long getLast()
  {
    return _last;
  }
  
  public boolean isSettled()
  {
    return _isSettled;
  }
  
  public AmqpDeliveryState getState()
  {
    return _state;
  }
  
  public boolean isBatchable()
  {
    return _isBatchable;
  }
  
  @Override
  public long getDescriptorCode()
  {
    return FT_LINK_FLOW;
  }
  
  @Override
  public void readBody(AmqpReader in, int count)
    throws IOException
  {
    _role = Role.values()[in.readInt()];
    _first = in.readInt();
    _last = in.readInt();
    _isSettled = in.readBoolean();
    _state = in.readObject(AmqpDeliveryState.class);
    _isBatchable = in.readBoolean();
  }
  
  @Override
  public int writeBody(AmqpWriter out)
    throws IOException
  {
    out.writeUbyte(_role.ordinal());
    out.writeUint((int) _first);
    out.writeUint((int) _last);
    out.writeBoolean(_isSettled);
    
    if (_state != null)
      _state.write(out);
    else
      out.writeNull(); // state
    
    out.writeBoolean(_isBatchable);
    
    return 6;
  }

}
