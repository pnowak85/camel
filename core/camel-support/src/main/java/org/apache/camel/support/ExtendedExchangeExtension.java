/*
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

package org.apache.camel.support;

import java.util.List;
import java.util.Map;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeExtension;
import org.apache.camel.SafeCopyProperty;
import org.apache.camel.spi.Synchronization;
import org.apache.camel.spi.UnitOfWork;

public class ExtendedExchangeExtension implements ExchangeExtension {
    private final AbstractExchange exchange;
    private Boolean errorHandlerHandled;
    private boolean failureHandled;
    private Endpoint fromEndpoint;
    private String fromRouteId;
    private boolean streamCacheDisabled;
    private boolean redeliveryExhausted;
    private String historyNodeId;
    private String historyNodeSource;
    private String historyNodeLabel;
    private boolean transacted;
    private boolean notifyEvent;
    private boolean interruptable = true;
    private boolean interrupted;
    private AsyncCallback defaultConsumerCallback; // optimize (do not reset)

    ExtendedExchangeExtension(AbstractExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void setFromEndpoint(Endpoint fromEndpoint) {
        this.fromEndpoint = fromEndpoint;
    }

    @Override
    public Endpoint getFromEndpoint() {
        return fromEndpoint;
    }

    @Override
    public void setFromRouteId(String fromRouteId) {
        this.fromRouteId = fromRouteId;
    }

    public String getFromRouteId() {
        return fromRouteId;
    }

    /**
     * Is stream caching disabled on the given exchange
     */
    public boolean isStreamCacheDisabled() {
        return this.streamCacheDisabled;
    }

    /**
     * Used to force disabling stream caching which some components can do in special use-cases.
     */
    public void setStreamCacheDisabled(boolean streamCacheDisabled) {
        this.streamCacheDisabled = streamCacheDisabled;
    }

    @Override
    public void addOnCompletion(Synchronization onCompletion) {
        this.exchange.addOnCompletion(onCompletion);
    }

    @Override
    public boolean isErrorHandlerHandledSet() {
        return errorHandlerHandled != null;
    }

    @Override
    public Boolean getErrorHandlerHandled() {
        return this.errorHandlerHandled;
    }

    @Override
    public void setErrorHandlerHandled(Boolean errorHandlerHandled) {
        this.errorHandlerHandled = errorHandlerHandled;
    }

    @Override
    public boolean isErrorHandlerHandled() {
        return this.errorHandlerHandled;
    }

    @Override
    public boolean isRedeliveryExhausted() {
        return this.redeliveryExhausted;
    }

    @Override
    public void setRedeliveryExhausted(boolean redeliveryExhausted) {
        this.redeliveryExhausted = redeliveryExhausted;
    }

    @Override
    public void handoverCompletions(Exchange target) {
        this.exchange.handoverCompletions(target);
    }

    @Override
    public List<Synchronization> handoverCompletions() {
        return this.exchange.handoverCompletions();
    }

    @Override
    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.exchange.setUnitOfWork(unitOfWork);
    }

    @Override
    public void copyInternalProperties(Exchange target) {
        this.exchange.copyInternalProperties(target);
    }

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.exchange.setProperties(properties);
    }

    @Override
    public void setHistoryNodeId(String historyNodeId) {
        this.historyNodeId = historyNodeId;
    }

    @Override
    public String getHistoryNodeId() {
        return this.historyNodeId;
    }

    @Override
    public String getHistoryNodeSource() {
        return this.historyNodeSource;
    }

    @Override
    public void setHistoryNodeSource(String historyNodeSource) {
        this.historyNodeSource = historyNodeSource;
    }

    @Override
    public String getHistoryNodeLabel() {
        return this.historyNodeLabel;
    }

    @Override
    public void setHistoryNodeLabel(String historyNodeLabel) {
        this.historyNodeLabel = historyNodeLabel;
    }

    @Override
    public boolean isNotifyEvent() {
        return this.notifyEvent;
    }

    @Override
    public void setNotifyEvent(boolean notifyEvent) {
        this.notifyEvent = notifyEvent;
    }

    @Override
    public Map<String, Object> getInternalProperties() {
        return this.exchange.getInternalProperties();
    }

    @Override
    public boolean containsOnCompletion(Synchronization onCompletion) {
        return this.exchange.containsOnCompletion(onCompletion);
    }

    @Override
    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    public boolean isTransacted() {
        return transacted;
    }

    @Override
    public void setInterruptable(boolean interruptable) {
        this.interruptable = interruptable;
    }

    @Override
    public boolean isInterrupted() {
        return this.interrupted;
    }

    @Override
    public void setInterrupted(boolean interrupted) {
        if (interruptable) {
            this.interrupted = interrupted;
        }
    }

    @Override
    public <T> T getInOrNull(Class<T> type) {
        return this.exchange.getInOrNull(type);
    }

    @Override
    public AsyncCallback getDefaultConsumerCallback() {
        return this.defaultConsumerCallback;
    }

    @Override
    public void setDefaultConsumerCallback(AsyncCallback callback) {
        this.defaultConsumerCallback = callback;
    }

    @Override
    public void setSafeCopyProperty(String key, SafeCopyProperty value) {
        this.exchange.setSafeCopyProperty(key, value);
    }

    @Override
    public <T> T getSafeCopyProperty(String key, Class<T> type) {
        return this.exchange.getSafeCopyProperty(key, type);
    }

    @Override
    public boolean isFailureHandled() {
        return this.failureHandled;
    }

    @Override
    public void setFailureHandled(boolean failureHandled) {
        this.failureHandled = failureHandled;
    }

    public void reset() {
        setHistoryNodeId(null);
        setHistoryNodeLabel(null);
        setTransacted(false);
        setNotifyEvent(false);
        setInterrupted(false);
        setInterruptable(true);
        setRedeliveryExhausted(false);
        setErrorHandlerHandled(null);
        setStreamCacheDisabled(false);
    }
}
