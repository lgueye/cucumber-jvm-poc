package org.diveintojee.poc.cucumberjvm.persistence.search;

import com.google.common.base.Splitter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(ElasticsearchClientFactoryBean.BEAN_ID)
public class ElasticsearchClientFactoryBean extends AbstractFactoryBean<Client> {

  public static final String BEAN_ID = "ElasticsearchClientFactoryBean";
  public final String SERVICES_LIST_SEPARATOR = ",";
  public final String HOST_PORT_SEPARATOR = ":";

  private Client client;

  @Value("classpath:elasticsearch/mappings/classified.json")
  private Resource mapping;

  @Value("${search.engine.remote.nodes}")
  private String transportAddresses;

  /**
   * This abstract method declaration mirrors the method in the FactoryBean interface, for a
   * consistent offering of abstract template methods.
   *
   * @see org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  public Class<?> getObjectType() {
    return Client.class;
  }

  /**
   * Template method that subclasses must override to construct the object returned by this factory.
   * <p>Invoked on initialization of this FactoryBean in case of a singleton; else, on each {@link
   * #getObject()} call.
   *
   * @return the object returned by this factory
   * @throws Exception if an exception occured during object creation
   * @see #getObject()
   */
  @Override
  protected Client createInstance() throws Exception {

    if (StringUtils.isEmpty(this.transportAddresses)) {

      localNodeClient();

    } else {

      remoteNodeClient();

    }

    // Create index if not exist
    createIndexIfNeeded();

    // Configure mappings
    updateMapping();

    return client;

  }

  protected List<InetSocketTransportAddress> fromAddressList(String transportAddresses) {

    if (StringUtils.isEmpty(transportAddresses)) {
      return null;
    }

    Iterable<String>
        servicesIterable =
        Splitter.on(SERVICES_LIST_SEPARATOR).trimResults().omitEmptyStrings()
            .split(transportAddresses);

    Iterator<String> networkServicesIterator = servicesIterable.iterator();

    List<InetSocketTransportAddress> networkAddresses = new ArrayList<InetSocketTransportAddress>();

    while (networkServicesIterator.hasNext()) {

      String service = networkServicesIterator.next();

      Iterable<String>
          serviceIterable =
          Splitter.on(HOST_PORT_SEPARATOR).trimResults().omitEmptyStrings().split(
              service);

      Iterator<String> serviceIterator = serviceIterable.iterator();

      String host = null;

      int port = -1;

      if (serviceIterator.hasNext()) {
        host = serviceIterator.next();
      }

      if (serviceIterator.hasNext()) {
        String portAsString = serviceIterator.next();
        try {
          port = Integer.valueOf(portAsString);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              "Positive integer value expected for port, got " + portAsString);
        }
      }

      if (port <= 0) {
        throw new IllegalArgumentException("Positive integer value expected for port, got " + port);
      }

      networkAddresses.add(new InetSocketTransportAddress(host, port));

    }

    return networkAddresses;

  }

  /**
   * Destroy the singleton instance, if any.
   *
   * @see #destroyInstance(Object)
   */
  @Override
  public void destroy() throws Exception {
    this.client.close();
  }


  private void localNodeClient() {
    this.client = NodeBuilder.nodeBuilder() //
        .local(true).data(true) //
        .settings(ImmutableSettings.settingsBuilder() //
                      .loadFromClasspath("elasticsearch/elasticsearch.yml")) //
        .node().client();
  }

  private void remoteNodeClient() {
    this.client = new TransportClient();

    List<InetSocketTransportAddress> networkAddresses = fromAddressList(this.transportAddresses);

    if (CollectionUtils.isEmpty(networkAddresses)) {
      throw new IllegalStateException(
          "Unable to parse network addresses from '" + this.transportAddresses + "'");
    }

    for (InetSocketTransportAddress transportAddress : networkAddresses) {
      ((TransportClient) this.client).addTransportAddress(transportAddress);
    }

  }

  private void createIndexIfNeeded() {

    // Create index if not exist
    if (!client.admin().indices().prepareExists(Indices.CLASSIFIED).execute().actionGet()
        .exists()) {
      client.admin().indices().prepareCreate(Indices.CLASSIFIED).execute().actionGet();
    }

  }

  private void updateMapping() throws IOException {
    // Configure mappings
    Writer writer = new StringWriter();
    IOUtils.copy(mapping.getInputStream(), writer, "UTF-8");
    client.admin().indices().preparePutMapping(Indices.CLASSIFIED).setSource(writer.toString())
        .setType(Indices.Types.CLASSIFIED).execute().actionGet();
  }

}
