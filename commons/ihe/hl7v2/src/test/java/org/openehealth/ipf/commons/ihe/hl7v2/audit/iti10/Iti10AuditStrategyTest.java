/*
 * Copyright 2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.openehealth.ipf.commons.ihe.hl7v2.audit.iti10;

import org.junit.Test;
import org.openehealth.ipf.commons.audit.codes.EventActionCode;
import org.openehealth.ipf.commons.audit.codes.EventIdCode;
import org.openehealth.ipf.commons.audit.codes.EventOutcomeIndicator;
import org.openehealth.ipf.commons.audit.model.AuditMessage;
import org.openehealth.ipf.commons.ihe.hl7v2.audit.Hl7v2AuditorTestBase;
import org.openehealth.ipf.commons.ihe.hl7v2.audit.QueryAuditDataset;

import static org.junit.Assert.assertNotNull;

/**
 * @author Christian Ohr
 */
public class Iti10AuditStrategyTest extends Hl7v2AuditorTestBase {

    @Test
    public void testServerSide() {
        testRequest(true);
    }

    @Test
    public void testClientSide() {
        testRequest(false);
    }

    private void testRequest(boolean serverSide) {
        Iti10AuditStrategy strategy = new Iti10AuditStrategy(serverSide);
        QueryAuditDataset auditDataset = getHl7v2AuditDataset(strategy);
        AuditMessage auditMessage = makeAuditMessage(strategy, auditContext, auditDataset);

        assertNotNull(auditMessage);
        auditMessage.validate();
        assertCommonV2AuditAttributes(auditMessage,
                EventOutcomeIndicator.Success,
                EventIdCode.PatientRecord,
                serverSide ? EventActionCode.Update : EventActionCode.Read,
                serverSide,
                true);
    }

    private QueryAuditDataset getHl7v2AuditDataset(Iti10AuditStrategy strategy) {
        QueryAuditDataset auditDataset = strategy.createAuditDataset();
        auditDataset.setEventOutcomeIndicator(EventOutcomeIndicator.Success);
        // auditDataset.setLocalAddress(SERVER_URI);
        auditDataset.setRemoteAddress(CLIENT_IP_ADDRESS);
        auditDataset.setMessageControlId(MESSAGE_ID);
        auditDataset.setPatientIds(PATIENT_IDS);
        auditDataset.setSendingFacility(SENDING_FACILITY);
        auditDataset.setSendingApplication(SENDING_APPLICATION);
        auditDataset.setReceivingFacility(RECEIVING_FACILITY);
        auditDataset.setReceivingApplication(RECEIVING_APPLICATION);
        auditDataset.setPayload(QUERY_PAYLOAD);
        return auditDataset;
    }
}
