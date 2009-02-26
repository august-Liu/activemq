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
package org.apache.activemq.store.kahadb.perf;

import java.io.File;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.perf.SimpleQueueTest;
import org.apache.activemq.store.kahadb.TempKahaDBStore;

/**
 * @version $Revision: 712224 $
 */
public class TempKahaStoreQueueTest extends SimpleQueueTest {

    protected void configureBroker(BrokerService answer,String uri) throws Exception {
        File dataFileDir = new File("target/test-amq-data/perfTest/temp-amqdb");
        dataFileDir.mkdirs();
        answer.setDeleteAllMessagesOnStartup(true);
               
         TempKahaDBStore adaptor = new TempKahaDBStore();
         adaptor.setDirectory(dataFileDir);
         
        
        answer.setDataDirectoryFile(dataFileDir);
        answer.setPersistenceAdapter(adaptor);
        answer.addConnector(uri);
    }

}
