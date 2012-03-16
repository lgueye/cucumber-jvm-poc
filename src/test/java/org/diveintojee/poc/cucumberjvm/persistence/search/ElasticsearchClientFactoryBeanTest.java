package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * User: lgueye Date: 12/03/12 Time: 10:49
 */
public class ElasticsearchClientFactoryBeanTest {

  private ElasticsearchClientFactoryBean underTest;

  @Before
  public void before() {
    underTest = new ElasticsearchClientFactoryBean();
  }


  @Test
  public void fromAddressListShouldReturnNullWithEmptyInput() {

    // Variables
    String transportAddresses;
    List<InetSocketTransportAddress> result;

    // Given
    transportAddresses = null;

    // When
    result = underTest.fromAddressList(transportAddresses);

    // Then
    assertNull(result);

    // Given
    transportAddresses = EMPTY;

    // When
    result = underTest.fromAddressList(transportAddresses);

    // Then
    assertNull(result);

  }

  @Test(expected = IllegalArgumentException.class)
  public void fromAddressListShouldThrowIllegalArgumentExceptionWithNonIntegerPort() {

    // Variables
    String transportAddresses;
    List<InetSocketTransportAddress> result;

    // Given
    transportAddresses = "a:b";

    // When
    result = underTest.fromAddressList(transportAddresses);

  }

  @Test(expected = IllegalArgumentException.class)
  public void fromAddressListShouldThrowIllegalArgumentExceptionWithNegativePort() {

    // Variables
    String transportAddresses;
    List<InetSocketTransportAddress> result;

    // Given
    transportAddresses = "a:0";

    // When
    result = underTest.fromAddressList(transportAddresses);

  }

  @Test
  public void fromAddressListShouldSucceed() {

    // Variables
    String transportAddresses;
    List<InetSocketTransportAddress> result;

    // Given
    transportAddresses = "    a.b.c:50,    168.15.23.10:51  ";

    // When
    result = underTest.fromAddressList(transportAddresses);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    InetSocketTransportAddress inetSocketTransportAddress0 = result.get(0);
    assertEquals(50, inetSocketTransportAddress0.address().getPort());
    assertEquals("a.b.c", inetSocketTransportAddress0.address().getHostName());
    InetSocketTransportAddress inetSocketTransportAddress1 = result.get(1);
    assertEquals(51, inetSocketTransportAddress1.address().getPort());
    assertEquals("168.15.23.10", inetSocketTransportAddress1.address().getHostName());

  }

}
