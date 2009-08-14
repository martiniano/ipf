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
package org.openehealth.ipf.platform.camel.ihe.xds.iti14.audit;

import org.openehealth.ipf.commons.ihe.atna.AuditorManager;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.cxf.audit.ItiAuditDataset;
import org.openhealthtools.ihe.atna.auditor.XDSRegistryAuditor;
import org.openhealthtools.ihe.atna.auditor.codes.rfc3881.RFC3881EventCodes.RFC3881EventOutcomeCodes;

/**
 * Server audit strategy for ITI-14.
 * 
 * @author Dmytro Rud
 */
public class Iti14ServerAuditStrategy extends Iti14AuditStrategy {

    public static final String[] NECESSARY_AUDIT_FIELDS = new String[] {
        "ClientIpAddress", 
        "ServiceEndpointUrl", 
        "SubmissionSetUuid",
        "PatientId"};

    
    public Iti14ServerAuditStrategy(boolean allowIncompleteAudit) {
        super(true, allowIncompleteAudit);
    }

    @Override
    public void doAudit(RFC3881EventOutcomeCodes eventOutcome, ItiAuditDataset auditDataset) {
        XDSRegistryAuditor auditor = AuditorManager.getRegistryAuditor();
        auditor.auditRegisterDocumentSetEvent(
                eventOutcome,
                auditDataset.getClientIpAddress(),  // Must be set to something, otherwise schema is broken
                auditDataset.getClientIpAddress(),
                auditDataset.getServiceEndpointUrl(),
                auditDataset.getSubmissionSetUuid(),
                auditDataset.getPatientId());
    }

    @Override
    public String[] getNecessaryAuditFieldNames() {
        return NECESSARY_AUDIT_FIELDS;
    }
}
