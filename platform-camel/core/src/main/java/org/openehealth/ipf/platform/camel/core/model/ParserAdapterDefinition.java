/*
 * Copyright 2008 the original author or authors.
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
package org.openehealth.ipf.platform.camel.core.model;

import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.RouteContext;
import org.openehealth.ipf.commons.core.modules.api.Parser;
import org.openehealth.ipf.platform.camel.core.adapter.ParserAdapter;
import org.openehealth.ipf.platform.camel.core.adapter.ProcessorAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Martin Krasser
 */
@Metadata(label = "ipf,eip,transformation")
@XmlRootElement(name = "parse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParserAdapterDefinition extends ProcessorAdapterDefinition {

    private Parser parser;
    
    private String parserBean;
    
    public ParserAdapterDefinition(Parser parser) {
        this.parser = parser;
    }
    
    public ParserAdapterDefinition(String parserBean) {
        this.parserBean = parserBean;
    }
    
    @Override
    public String toString() {
        return "ParserAdapter[" + getOutputs() + "]";
    }

    @Override
    public String getShortName() {
        return "parserAdapter";
    }

    @Override
    protected ProcessorAdapter doCreateProcessor(RouteContext routeContext) {
        if (parserBean != null) {
            parser = routeContext.lookup(parserBean, Parser.class);
        }
        return new ParserAdapter(parser);
    }

}
