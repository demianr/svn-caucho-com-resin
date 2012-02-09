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

package com.caucho.mqueue.queue;

import com.caucho.mqueue.journal.JournalFileItem;

/**
 * Interface for the transaction log.
 * 
 * MQueueJournal is not thread safe. It is intended to be used by a
 * single thread.
 */
public class JournalQueueEntry extends JournalFileItem
{
  private static final byte []EMPTY_BUFFER = new byte[0];
  
  private MQJournalQueueSubscriber _subscriber;
  
  JournalQueueEntry(int index)
  {
    super(index);
  }
  
  public void initAck(long sequence, MQJournalQueueSubscriber sub)
  {
    long id = 3;
    
    init('A', id, sequence, EMPTY_BUFFER, 0, 0, null, null);
    
    _subscriber = sub;
  }
  
  public void initSubscribe(MQJournalQueueSubscriber subscriber)
  {
    setCode('S');
    
    _subscriber = subscriber;
  }
  
  public void initUnsubscribe(MQJournalQueueSubscriber subscriber)
  {
    setCode('U');
    
    _subscriber = subscriber;
  }
  
  public MQJournalQueueSubscriber getSubscriber()
  {
    return _subscriber;
  }
  
  public void clear()
  {
    _subscriber = null;
  }
}