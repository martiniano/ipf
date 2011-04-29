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
package org.openehealth.ipf.platform.camel.ihe.hl7v2.intercept;

import org.apache.camel.Processor;
import org.openehealth.ipf.platform.camel.ihe.hl7v2.Hl7v2ConfigurationHolder;


/**
 * Camel interceptor interface for HL7v2 transactions.
 * @author Dmytro Rud
 */
public interface Hl7v2Interceptor extends Processor, Hl7v2ConfigurationHolder {

    /**
     * Name of the Camel message header where a copy of the original request
     * message (as a {@link org.openehealth.ipf.modules.hl7dsl.MessageAdapter} instance) will be saved.
     */
    public static final String ORIGINAL_MESSAGE_ADAPTER_HEADER_NAME = "pixPdqOriginalMessageAdapter";

    
    /**
     * Name of the Camel message header where a copy of the original request
     * message (as a {@link String} instance) will be saved.  
     */
    public static final String ORIGINAL_MESSAGE_STRING_HEADER_NAME  = "pixPdqOriginalMessageString";
    

    /**
     * Returns the processor instance wrapped by this interceptor.
     */
    public Processor getWrappedProcessor();
    
}
