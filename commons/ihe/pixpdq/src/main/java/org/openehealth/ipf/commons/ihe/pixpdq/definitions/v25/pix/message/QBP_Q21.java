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
package org.openehealth.ipf.commons.ihe.pixpdq.definitions.v25.pix.message;

import org.openehealth.ipf.commons.ihe.pixpdq.definitions.v25.pix.segment.QPD;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.RCP;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.log.HapiLogFactory;

/**
 * <p>Represents a QBP_Q21 message structure (see chapter 3.3.56). This structure contains the
 * following elements: </p>
 * 0: MSH (Message Header) <b></b><br>
 * 1: SFT (Software Segment) <b>optional repeating</b><br>
 * 2: QPD (Query Parameter Definition) <b></b><br>
 * 3: RCP (Response Control Parameter) <b></b><br>
 * 4: DSC (Continuation Pointer) <b>optional </b><br>
 */
@SuppressWarnings("serial")
public class QBP_Q21 extends AbstractMessage {

    /**
     * Creates a new QBP_Q21 Group with custom ModelClassFactory.
     */
    public QBP_Q21(ModelClassFactory factory) {
        super(factory);
        init();
    }

    /**
     * Creates a new QBP_Q21 Group with DefaultModelClassFactory.
     */
    public QBP_Q21() {
        super(new DefaultModelClassFactory());
        init();
    }

    private void init() {
        try {
            add(MSH.class, true, false);
            add(QPD.class, true, false);
            add(RCP.class, true, false);
        } catch (HL7Exception e) {
            HapiLogFactory.getHapiLog(getClass()).error("Unexpected error creating QBP_Q21 - this is probably a bug in the source code generator.", e);
        }
    }

    /**
     * Returns MSH (Message Header) - creates it if necessary
     */
    public MSH getMSH() {
        try {
            return (MSH) get("MSH");
        } catch (HL7Exception e) {
            HapiLogFactory.getHapiLog(getClass()).error("Unexpected error accessing data - this is probably a bug in the source code generator.", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Returns QPD (Query Parameter Definition) - creates it if necessary
     */
    public QPD getQPD() {
        try {
            return (QPD) get("QPD");
        } catch (HL7Exception e) {
            HapiLogFactory.getHapiLog(getClass()).error("Unexpected error accessing data - this is probably a bug in the source code generator.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns RCP (Response Control Parameter) - creates it if necessary
     */
    public RCP getRCP() {
        try {
            return (RCP) get("RCP");
        } catch (HL7Exception e) {
            HapiLogFactory.getHapiLog(getClass()).error("Unexpected error accessing data - this is probably a bug in the source code generator.", e);
            throw new RuntimeException(e);
        }
    }
}