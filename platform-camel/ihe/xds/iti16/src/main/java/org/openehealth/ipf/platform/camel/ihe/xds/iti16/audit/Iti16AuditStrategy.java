/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.ipf.platform.camel.ihe.xds.iti16.audit;

import org.openehealth.ipf.platform.camel.ihe.xds.commons.cxf.audit.ItiAuditDataset;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.cxf.audit.ItiAuditStrategy;

/**
 * Audit strategy for ITI-16.
 * 
 * @author Dmytro Rud
 */
abstract public class Iti16AuditStrategy extends ItiAuditStrategy {

    public Iti16AuditStrategy(boolean serverSide, boolean allowIncompleteAudit) {
        super(serverSide, allowIncompleteAudit);
    }

    @Override
    public void enrichDataset(Object pojo, ItiAuditDataset genericAuditDataset) {
        // no additional steps necessary
    }

    @Override
    public boolean needSavePayload() {
        return true;
    }
}
