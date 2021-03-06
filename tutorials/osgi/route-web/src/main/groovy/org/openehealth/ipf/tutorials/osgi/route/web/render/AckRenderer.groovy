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
package org.openehealth.ipf.tutorials.osgi.route.web.render

import org.openehealth.ipf.platform.camel.flow.PlatformMessage
import org.openehealth.ipf.platform.camel.flow.PlatformMessageRenderer
/**
 * @author Martin Krasser
 */
class AckRenderer implements PlatformMessageRenderer {

     @Override
     String render(PlatformMessage message) {
         message.exchange.in.getBody(String.class).replaceAll('\r', '\n')
     }
    
    
}
