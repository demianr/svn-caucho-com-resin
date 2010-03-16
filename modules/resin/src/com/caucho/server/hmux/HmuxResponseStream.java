/*
 * Copyright (c) 1998-2010 Caucho Technology -- all rights reserved
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

package com.caucho.server.hmux;

import java.io.IOException;

import com.caucho.server.http.ResponseStream;
import com.caucho.vfs.WriteStream;

public class HmuxResponseStream extends ResponseStream {
  private final HmuxRequest _request;

  HmuxResponseStream(HmuxRequest request,
                     HmuxResponse response,
                     WriteStream next)
  {
    super(response);

    if (request == null)
      throw new NullPointerException();

    _request = request;
  }

  //
  // implementations
  //

  @Override
  protected byte []getNextBuffer()
  {
    return _request.getNextBuffer();
  }

  @Override
  protected int getNextStartOffset()
  {
    return _request.getNextStartOffset();
  }

  @Override
  protected int getNextBufferOffset()
    throws IOException
  {
    return _request.getNextBufferOffset();
  }

  @Override
  protected void setNextBufferOffset(int offset)
    throws IOException
  {
    _request.setNextBufferOffset(offset);
  }

  @Override
  protected byte []writeNextBuffer(int offset)
    throws IOException
  {
    return _request.writeNextBuffer(offset);
  }

  @Override
  public void flushNext()
    throws IOException
  {
    _request.flushNext();
  }

  @Override
  protected void closeNext()
    throws IOException
  {
    // only flush buffer because the 'Q' still needs to
    // be written.
    _request.flushNextBuffer();
  }

  @Override
  protected void writeTail()
    throws IOException
  {
    _request.writeTail();
  }
}