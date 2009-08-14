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
package org.openehealth.ipf.platform.camel.ihe.mllp.commons

import org.openehealth.ipf.modules.hl7dsl.MessageAdapter
import org.openehealth.ipf.modules.hl7.HL7v2Exception;


/**
 * @author Dmytro Rud
 */
class ValidationUtils {

    /**
     * Performs transaction-specific acceptance test of the given request message.
     * @param msg
     *          {@link MessageAdapter} representing the message.
     * @param config
     *          Transaction-specific endpoint configuration.
     * @throws MllpAcceptanceException
     *          When the message cannot be accepted.
     */
     static void checkRequestAcceptance(
             MessageAdapter msg, 
             MllpEndpointConfiguration config) throws MllpAcceptanceException 
     {
         def s = msg.MSH[9][1].value
         if(s != config.allowedMessageType) {
             throw new MllpAcceptanceException("Invalid message type ${s}", 200)
         }

         s = msg.MSH[9][2].value 
         if( ! (s in config.allowedTriggerEvents)) {
             throw new MllpAcceptanceException("Invalid trigger event ${s}", 201)
         }

         s = msg.MSH[12].value 
         if(s != config.hl7Version) {
             throw new MllpAcceptanceException("Invalid HL7 version ${s}", 202)
         }
     }
     
     
     /**
      * Performs transaction-agnostic acceptance test of the given response message.
      * @param msg
      *          {@link MessageAdapter} representing the message.
      * @throws MllpAcceptanceException
      *          When the message cannot be accepted.
      */
     static void checkResponseAcceptance(MessageAdapter msg) {
         if( ! (['AA', 'AR', 'AE'].contains(msg.MSA[1]?.value))) {
             throw new MllpAcceptanceException("Bad response: missing or invalid MSA segment")
         }
     }
}
