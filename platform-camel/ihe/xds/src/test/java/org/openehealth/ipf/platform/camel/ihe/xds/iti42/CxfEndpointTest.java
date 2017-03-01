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
package org.openehealth.ipf.platform.camel.ihe.xds.iti42;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openehealth.ipf.commons.ihe.ws.JaxWsClientFactory;
import org.openehealth.ipf.commons.ihe.ws.JaxWsRequestClientFactory;
import org.openehealth.ipf.commons.ihe.ws.JaxWsRequestServiceFactory;
import org.openehealth.ipf.commons.ihe.ws.JaxWsServiceFactory;
import org.openehealth.ipf.commons.ihe.ws.server.JettyServer;
import org.openehealth.ipf.commons.ihe.ws.server.ServletServer;
import org.openehealth.ipf.commons.ihe.xds.core.SampleData;
import org.openehealth.ipf.commons.ihe.xds.core.audit.XdsAuditDataset;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.EbXMLFactory;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.EbXMLRegistryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.EbXMLSubmitObjectsRequest;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLFactory30;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLRegistryResponse30;
import org.openehealth.ipf.commons.ihe.xds.core.ebxml.ebxml30.EbXMLSubmitObjectsRequest30;
import org.openehealth.ipf.commons.ihe.xds.core.requests.RegisterDocumentSet;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorCode;
import org.openehealth.ipf.commons.ihe.xds.core.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Response;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Severity;
import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.lcm.SubmitObjectsRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rs.RegistryResponseType;
import org.openehealth.ipf.commons.ihe.xds.core.transform.requests.RegisterDocumentSetTransformer;
import org.openehealth.ipf.commons.ihe.xds.core.transform.responses.ResponseTransformer;
import org.openehealth.ipf.commons.ihe.xds.core.validate.requests.SubmitObjectsRequestValidator;
import org.openehealth.ipf.commons.ihe.xds.core.validate.responses.RegistryResponseValidator;
import org.openehealth.ipf.commons.ihe.xds.iti42.Iti42PortType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.openehealth.ipf.commons.ihe.xds.XDS_B.Interactions.ITI_42;

public class CxfEndpointTest {
    private final EbXMLFactory factory = new EbXMLFactory30();

    private final RegisterDocumentSetTransformer reqTransformer =
        new RegisterDocumentSetTransformer(factory);

    private final ResponseTransformer respTransformer =
        new ResponseTransformer(factory);

    private int port;
    private JettyServer server;

    @Before
    public void setUp() throws IOException, URISyntaxException {
        port = ServletServer.getFreePort();
        server = new JettyServer();
        server.setContextResource(getClass().getResource("/cxf-context.xml").toURI().toString());
        server.setPort(port);
        server.setContextPath("");
        server.setServletPath("/*");
        server.setServlet(new CXFServlet());

        server.start();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        runRequestAndExpectFailure();

        JaxWsServiceFactory<XdsAuditDataset> serviceFactory = new JaxWsRequestServiceFactory<>(
                ITI_42.getWsTransactionConfiguration(), "/iti-42", null, null, null);
        ServerFactoryBean factory = serviceFactory.createServerFactory(MyIti42.class);
        Server serviceServer = factory.create();

        runRequestAndExpectFailure();

        serviceServer.start();
        Assert.assertEquals(Status.SUCCESS, runRequest().getStatus());

        serviceServer.stop();
        runRequestAndExpectFailure();

        //serviceServer = factory.create();
        serviceServer.start();
        Assert.assertEquals(Status.SUCCESS, runRequest().getStatus());

        serviceServer.stop();
    }

    private void runRequestAndExpectFailure() {
        try {
            runRequest();
            Assert.fail("Expected Exception: " + RuntimeException.class);
        }
        catch (RuntimeException e) {
            // ok
        }
    }

    private Response runRequest() {
        JaxWsClientFactory clientFactory = new JaxWsRequestClientFactory<>(
                ITI_42.getWsTransactionConfiguration(),
                "http://localhost:" + port + "/iti-42",
                null, null, null, null, null);

        Iti42PortType client = (Iti42PortType) clientFactory.getClient();
        RegisterDocumentSet request = SampleData.createRegisterDocumentSet();
        EbXMLSubmitObjectsRequest ebXMLReq = reqTransformer.toEbXML(request);
        SubmitObjectsRequest rawReq = (SubmitObjectsRequest) ebXMLReq.getInternal();
        RegistryResponseType rawResp = client.documentRegistryRegisterDocumentSetB(rawReq);
        EbXMLRegistryResponse30 ebXMLResp = new EbXMLRegistryResponse30(rawResp);
        return respTransformer.fromEbXML(ebXMLResp);
    }

    public static class MyIti42 implements Iti42PortType {
        private final EbXMLFactory factory = new EbXMLFactory30();

        private final RegisterDocumentSetTransformer reqTransformer =
            new RegisterDocumentSetTransformer(factory);

        private final ResponseTransformer respTransformer =
            new ResponseTransformer(factory);

        private final SubmitObjectsRequestValidator reqValidator =
            new SubmitObjectsRequestValidator();

        private final RegistryResponseValidator respValidator =
            new RegistryResponseValidator();

        @Override
        public RegistryResponseType documentRegistryRegisterDocumentSetB(SubmitObjectsRequest rawReq) {

            EbXMLSubmitObjectsRequest30 ebXMLReq = new EbXMLSubmitObjectsRequest30(rawReq);
            reqValidator.validate(ebXMLReq, ITI_42);
            RegisterDocumentSet request = reqTransformer.fromEbXML(ebXMLReq);

            Response response = new Response(Status.SUCCESS);
            if (!request.getSubmissionSet().getEntryUuid().equals("submissionSet01")) {
                response.setStatus(Status.FAILURE);
                response.setErrors(Arrays.asList(new ErrorInfo(ErrorCode.REGISTRY_ERROR, "unexpected value", Severity.ERROR, null, null)));
            }

            EbXMLRegistryResponse ebXMLResp = respTransformer.toEbXML(response);
            respValidator.validate(ebXMLResp, ITI_42);
            return (RegistryResponseType) ebXMLResp.getInternal();
        }
    }
}
