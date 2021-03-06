
## `ch-pidd` component

The ch-pidd component provides interfaces for actors of the *Provider Information Delta Download* transaction (CH-PIDD),
which is described in the [National extensions to the IHE Technical Framework](https://www.bag.admin.ch/dam/bag/de/dokumente/nat-gesundheitsstrategien/strategie-ehealth/gesetzgebung-elektronisches-patientendossier/gesetze/SR%20816.111.1_ergaenzung-1-Anhang-5.pdf.download.pdf/SR%20816.111.1_Ergaenzung%201%20Anhang%205_DE.pdf),
Section 1.10.

### Actors

The transaction defines the following actors:

![CH-PIDD actors](images/ch-pidd.png)

Producer side corresponds to the *Provider Information Consumer* actor.
Consumer side corresponds to the *Provider Information Directory* actor.

### Dependencies

In a Maven-based environment, the following dependency must be registered in `pom.xml`:

```xml
    <dependency>
        <groupId>org.openehealth.ipf.platform-camel</groupId>
        <artifactId>ipf-platform-camel-ihe-hpd</artifactId>
        <version>${ipf-version}</version>
    </dependency>
```

### Endpoint URI Format

#### Producer

The endpoint URI format of `ch-pidd` component producers is:

```
ch-pidd://hostname:port/path/to/service[?parameters]
```

where *hostname* is either an IP address or a domain name, *port* is a port number, and *path/to/service*
represents additional path elements of the remote service.
URI parameters are optional and control special features as described in the corresponding section below.

#### Consumer

The endpoint URI format of `ch-pidd` component consumers is:

```
ch-pidd:serviceName[?parameters]
```

The resulting URL of the exposed IHE Web Service endpoint depends on both the configuration of the [deployment container]
and the serviceName parameter provided in the Camel endpoint URI.

For example, when a Tomcat container on the host `eHealth.server.org` is configured in the following way:

```
port = 8888
contextPath = /IHE
servletPath = /hpd/*
```

and serviceName equals to `chPiddService`, then the ch-pidd consumer will be available for external clients under the URL
`http://eHealth.server.org:8888/IHE/hpd/chPiddService`

Additional URI parameters are optional and control special features as described in the corresponding section below.

### Data Types

The CH-PIDD component produces and consumes objects based on the [DSMLv2](https://www.oasis-open.org/standards#dsmlv2) data model:

* Request message -- [`DownloadRequest`](../apidocs/org/openehealth/ipf/commons/ihe/hpd/stub/chpidd/DownloadRequest.html)
* Response message -- [`DownloadResponse`](../apidocs/org/openehealth/ipf/commons/ihe/hpd/stub/chpidd/DownloadResponse.html)

### Example

This is an example on how to use the component on the consumer side:

```java
    from("ch-pidd:chPiddService?audit=false")
      .process(myProcessor)
      // process the incoming request and create a response
```


### Basic Common Component Features

* ATNA Auditing is not defined for CH-PIDD
* [Message validation]

### Basic Web Service Component Features

* [Secure transport]
* [File-Based payload logging]

### Advanced Web Service Component Features

* [Handling Protocol Headers]
* [Deploying custom CXF interceptors]
* [Handling automatically rejected messages]
* [Using CXF features]



[Message validation]: ../ipf-platform-camel-ihe/messageValidation.html

[deployment container]: ../ipf-platform-camel-ihe-ws/deployment.html
[Secure Transport]: ../ipf-platform-camel-ihe-ws/secureTransport.html
[File-Based payload logging]: ../ipf-platform-camel-ihe-ws/payloadLogging.html

[Handling Protocol Headers]: ../ipf-platform-camel-ihe-ws/protocolHeaders.html
[Deploying custom CXF interceptors]: ../ipf-platform-camel-ihe-ws/customInterceptors.html
[Handling automatically rejected messages]: ../ipf-platform-camel-ihe-ws/handlingRejected.html
[Using CXF features]: ../ipf-platform-camel-ihe-ws/cxfFeatures.html




