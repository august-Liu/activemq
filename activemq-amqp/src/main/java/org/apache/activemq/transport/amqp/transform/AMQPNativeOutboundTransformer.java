/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.transport.amqp.transform;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;

/**
* @author <a href="http://hiramchirino.com">Hiram Chirino</a>
*/
public class AMQPNativeOutboundTransformer extends OutboundTransformer {

    public AMQPNativeOutboundTransformer(JMSVendor vendor) {
        super(vendor);
    }

    @Override
    public EncodedMessage transform(Message msg) throws Exception {
        if( msg == null )
            return null;
        if( !(msg instanceof BytesMessage) )
            return null;
        try {
            if( !msg.getBooleanProperty(prefixVendor + "NATIVE") ) {
                return null;
            }
        } catch (MessageFormatException e) {
            return null;
        }
        return transform(this, (BytesMessage) msg);
    }

    static EncodedMessage transform(OutboundTransformer options, BytesMessage msg) throws JMSException {
        long messageFormat;
        try {
            messageFormat = msg.getLongProperty(options.prefixVendor + "MESSAGE_FORMAT");
        } catch (MessageFormatException e) {
            return null;
        }
        byte data[] = new byte[(int) msg.getBodyLength()];
        msg.readBytes(data);
        return new EncodedMessage(messageFormat, data, 0, data.length);
    }

}
