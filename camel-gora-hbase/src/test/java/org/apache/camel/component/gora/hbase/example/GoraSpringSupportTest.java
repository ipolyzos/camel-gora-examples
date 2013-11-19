/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.camel.component.gora.hbase.example;

import org.apache.avro.util.Utf8;
import org.apache.camel.EndpointInject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.component.gora.GoraAttribute;
import org.apache.camel.component.gora.generated.Pageview;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Testing the operation through the use of Spring XML.
 *
 * <b>NOTE:</b> Many of the tests are based on the data-set of the "Gora-Tutorial" and
 * need to run manually.
 *
 * @author ipolyzos
 */
@SuppressWarnings("deprecation")
///@Ignore("These tests and should run manually")
public class GoraSpringSupportTest extends CamelSpringTestSupport {

    /**
     * Mock Endpoint used for verification
     * of the results.
     */
    @EndpointInject(uri = "mock:result")
    private MockEndpoint mock;


    /**
     * Test the Camel-Gora  CreateSchema operation.
     *
     * @throws Exception
     */
    @Test
    public void createSchemaShouldReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:createSchema", ExchangePattern.InOnly, null, new HashMap());

        assertMockEndpointsSatisfied();
    }

    /**
     * Test the Camel-Gora  SchemaExists operation.
     *
     * @throws Exception
     */
    @Test
    public void schemaExistsShouldReturnTrue() throws Exception {

        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived(true);

        template.sendBodyAndHeaders("direct:schemaExist", ExchangePattern.InOnly, null, new HashMap());

        assertMockEndpointsSatisfied();
    }

    /**
     * Test the Camel-Gora Put operation.
     *
     * @throws Exception
     */
    @Test
    public void putShouldReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:put", ExchangePattern.InOnly, getSamplePageview(), new HashMap());

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora Put operation.
     *
     * @throws Exception
     */
    @Test
    public void putCodeShouldReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        final Map<String, Object> headers = new HashMap();

        headers.put("goraOperation","pUt");
        headers.put(GoraAttribute.GORA_KEY.value, 1110);

        template.sendBodyAndHeaders("direct:putCode", ExchangePattern.InOnly, getSamplePageview(), headers);

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora  Get operation.
     *
     * @throws Exception
     */
    @Test
    //@Ignore
    public void getShouldReturnSucessfully() throws Exception {

        mock.expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:get", ExchangePattern.InOnly, null, new HashMap());

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora DeleteByQuery operation.
     *
     * @throws Exception
     */
    @Test
    public void deleteByQueryShouldReturnSucessfully() throws Exception {

        mock.expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:deleteByQuery", ExchangePattern.InOnly, new String(""), new HashMap());

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora  Get operation.
     *
     * @throws Exception
     */
    @Test
    public void getCodeShouldReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        final Map headers = new HashMap();

        headers.put("goraOperation","GeT");
        headers.put(GoraAttribute.GORA_KEY.value, 1110);

        template.sendBodyAndHeaders("direct:getCode", ExchangePattern.InOnly, null, headers);

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora delete operation.
     *
     * @throws Exception
     */
    @Test
    public void deleteCodeShouldReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        template.sendBodyAndHeaders("direct:delete", ExchangePattern.InOnly, null, new HashMap());

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora  delete operation.
     *
     * @throws Exception
     */
    @Test
    public void deleteShouldReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        final Map<String, Object> headers = new HashMap();

        headers.put("goraOperation","deLEtE");
        headers.put(GoraAttribute.GORA_KEY.value, 1110);

        template.sendBodyAndHeaders("direct:deleteCode", ExchangePattern.InOnly, null, headers);

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test multiple puts like 1K for example
     *
     * @throws Exception
     */
    @Test
    public void testingMultiplePuts() throws Exception {

        final int numberOfPuts = 1000;
        final Random idGenerator = new Random();

        for (int i = 0; i < numberOfPuts; i++){
            mock.expectedMessageCount(1);

            final Map<String, Object> headers = new HashMap();
            final long id = idGenerator.nextLong();

            headers.put("goraOperation","pUt");
            headers.put(GoraAttribute.GORA_KEY.value, id);

            template.sendBodyAndHeaders("direct:putCode", ExchangePattern.InOnly, getSamplePageview(), headers);

            assertMockEndpointsSatisfied();
            mock.reset();
        }
    }

    /**
     * Test the Camel-Gora Query operation should return results
     *
     * @throws Exception
     */
    @Test
    public void queryShouldReturnSomeResults() throws Exception {

        mock.expectedMessageCount(1);

        final Map headers = new HashMap();

        headers.put("goraOperation","Query");
        headers.put("keyRangeFrom",new Long(10));
        headers.put("keyRangeTo",new Long(12));

        template.sendBodyAndHeaders("direct:query", ExchangePattern.InOnly, null, headers);

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * Test the Camel-Gora  Delete Schema operation.
     *
     * @throws Exception
     */
    @Test
    public void deleteSchemaCodeReturnSuccessfully() throws Exception {

        mock.expectedMessageCount(1);

        final Map headers = new HashMap();

        headers.put("goraOperation","DeleteSchema");

        template.sendBodyAndHeaders("direct:deleteSchema", ExchangePattern.InOnly,null, headers);

        assertMockEndpointsSatisfied();
        mock.reset();
    }


    /**
     * Test the Camel-Gora SchemaExists operation should return fail if does not exist
     *
     * @throws Exception
     */
    @Test
    public void deleteSchemaCodeReturnShouldNotFailEvenIfDoesNotExist() throws Exception {

        //delete schema first
        final Map headers = new HashMap();
        headers.put("goraOperation","DeleteSchema");
        template.sendBodyAndHeaders("direct:deleteSchema", ExchangePattern.InOnly,null, headers);

        mock.reset();  // reset the mock

        //setup the actual expectation
        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived(false);


        //check if schema exist
        template.sendBodyAndHeaders("direct:deleteSchema", ExchangePattern.InOnly,null, headers);

        assertMockEndpointsSatisfied();
        mock.reset();
    }


    /**
     * Test the Camel-Gora SchemaExists operation should return false after delete.
     *
     * @throws Exception
     */
    @Test
    public void schemaExistsShouldReturnFalseAfterDelete() throws Exception {

        //delete schema first
        final Map headers = new HashMap();
        headers.put("goraOperation","DeleteSchema");
        template.sendBodyAndHeaders("direct:deleteSchema", ExchangePattern.InOnly,null, headers);

        mock.reset();  // reset the mock

        //setup the actual expectation
        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived(false);

        //check if schema exist
        template.sendBodyAndHeaders("direct:schemaExist", ExchangePattern.InOnly, null, new HashMap());

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    /**
     * load spring context
     *
     * @return
     */
    @Override
    protected ClassPathXmlApplicationContext createApplicationContext() {

        return new ClassPathXmlApplicationContext("META-INF/spring/test-camel-context-gora.xml");
    }

    /** ****************************************************************************************** Utility methods ***/

    private static Random random = new Random();

    /**
     * Create a sample Pageview instanse
     * @return
     */
    private static Pageview getSamplePageview() {

        final Pageview sampleInput = new Pageview();

        sampleInput.setHttpMethod(new Utf8("GET"));
        sampleInput.setHttpStatusCode(200);
        sampleInput.setIp(new Utf8(random.nextInt(254)+"."+random.nextInt(254)+"."+
                                    random.nextInt(254)+"."+random.nextInt(254)));
        sampleInput.setReferrer(new Utf8("Referer!"));
        sampleInput.setTimestamp(random.nextLong());
        sampleInput.setUserAgent(new Utf8("UserAgent"));

        return sampleInput;
    }
}
