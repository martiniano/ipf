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

package org.openehealth.ipf.commons.ihe.core.atna.event;

import org.openehealth.ipf.commons.audit.AuditContext;
import org.openehealth.ipf.commons.audit.codes.EventActionCode;
import org.openehealth.ipf.commons.audit.codes.EventOutcomeIndicator;
import org.openehealth.ipf.commons.audit.codes.ParticipantObjectTypeCode;
import org.openehealth.ipf.commons.audit.codes.ParticipantObjectTypeCodeRole;
import org.openehealth.ipf.commons.audit.event.DataExportBuilder;
import org.openehealth.ipf.commons.audit.model.TypeValuePairType;
import org.openehealth.ipf.commons.audit.types.EventType;
import org.openehealth.ipf.commons.audit.types.ParticipantObjectIdType;
import org.openehealth.ipf.commons.audit.types.PurposeOfUse;
import org.openehealth.ipf.commons.ihe.core.atna.AuditDataset;

import java.util.Collections;
import java.util.List;

/**
 * Builder for building IHE-specific DataExport events.
 * It automatically sets the AuditSource, local and remote ActiveParticipant and a Human Requestor
 * and provides methods for adding patient IDs.
 *
 * @author Christian Ohr
 * @since 3.5
 */
public class PHIExportBuilder<T extends PHIExportBuilder<T>> extends IHEAuditMessageBuilder<T, DataExportBuilder> {

    public PHIExportBuilder(AuditContext auditContext,
                            AuditDataset auditDataset,
                            EventType eventType,
                            PurposeOfUse... purposesOfUse) {
        this(auditContext, auditDataset, EventActionCode.Read, eventType, purposesOfUse);
    }

    public PHIExportBuilder(AuditContext auditContext,
                            AuditDataset auditDataset,
                            EventActionCode eventActionCode,
                            EventType eventType,
                            PurposeOfUse... purposesOfUse) {
        this(auditContext, auditDataset, auditDataset.getEventOutcomeIndicator(),
                auditDataset.getEventOutcomeDescription(),
                eventActionCode, eventType, purposesOfUse);
    }

    public PHIExportBuilder(AuditContext auditContext,
                            AuditDataset auditDataset,
                            EventOutcomeIndicator eventOutcomeIndicator,
                            String eventOutcomeDescription,
                            EventActionCode eventActionCode,
                            EventType eventType,
                            PurposeOfUse... purposesOfUse) {
        super(auditContext, new DataExportBuilder(
                eventOutcomeIndicator,
                eventOutcomeDescription,
                eventActionCode,
                eventType,
                purposesOfUse));

        // First the source, then the destination
        if (auditDataset.isServerSide()) {
            setRemoteParticipant(auditDataset);
            addHumanRequestor(auditDataset);
            setLocalParticipant(auditDataset);
        } else {
            setLocalParticipant(auditDataset);
            addHumanRequestor(auditDataset);
            setRemoteParticipant(auditDataset);
        }
    }

    public T setPatient(String patientId) {
        if (patientId != null) {
            delegate.addPatientParticipantObject(patientId, null,
                    Collections.emptyList(), null);
        }
        return self();
    }

    public T addExportedEntity(
            String objectId,
            ParticipantObjectIdType participantObjectIdType,
            ParticipantObjectTypeCodeRole participantObjectTypeCodeRole,
            List<TypeValuePairType> details) {
        return addExportedEntity(
                objectId,
                participantObjectIdType,
                ParticipantObjectTypeCode.System,
                participantObjectTypeCodeRole,
                details);
    }

    public T addExportedEntity(
            String objectId,
            ParticipantObjectIdType participantObjectIdType,
            ParticipantObjectTypeCode participantObjectTypeCode,
            ParticipantObjectTypeCodeRole participantObjectTypeCodeRole,
            List<TypeValuePairType> details) {
        delegate.addParticipantObjectIdentification(
                participantObjectIdType,
                null,
                null,
                details,
                objectId,
                participantObjectTypeCode,
                participantObjectTypeCodeRole,
                null,
                null);
        return self();
    }

    @Override
    public void validate() {
        super.validate();
    }
}
