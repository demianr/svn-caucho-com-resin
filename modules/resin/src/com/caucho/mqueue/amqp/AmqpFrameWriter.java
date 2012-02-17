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

import com.caucho.vfs.TempBuffer;
import com.caucho.vfs.WriteStream;

public class AmqpFrameWriter extends AmqpBaseWriter {
  private WriteStream _os;
  private TempBuffer _head;
  private byte []_buffer;
  private int _offset;
  
  public AmqpFrameWriter(WriteStream os)
  {
    _os = os;
    
    _head = TempBuffer.allocate();
    _buffer = _head.getBuffer();
  }
  
  public void startFrame(int type)
  {
    _buffer[5] = (byte) type;
    _buffer[4] = 0x02; // doff
    _offset = 8;
  }
  
  public void finishFrame()
    throws IOException
  {
    if (_offset < 0)
      throw new IllegalStateException();
    
    _buffer[0] = (byte) (_offset >> 24);
    _buffer[1] = (byte) (_offset >> 16);
    _buffer[2] = (byte) (_offset >> 8);
    _buffer[3] = (byte) (_offset);
    
    _os.write(_buffer, 0, _offset);
    _os.flush();
    
    _offset = 0;
  }
  
  @Override
  public void write(int ch)
  {
    _buffer[_offset++] = (byte) ch;
  }

  @Override
  public int getOffset()
  {
    return _offset;
  }
 
  @Override
  public void writeByte(int offset, int value)
  {
    _buffer[offset] = (byte) value;
  }
}