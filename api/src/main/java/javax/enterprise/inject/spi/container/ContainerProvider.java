/*
 * JBoss, Home of Professional Open Source
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.enterprise.inject.spi.container;

import java.util.ServiceLoader;

/**
 * Provide static access to Java SE container initialization.
 * Will load {@link Container} implementation with {@link ServiceLoader}.
 *
 *
 * @see Container
 * @author Antoine Sabot-Durand
 * @since 2.0
 */
public class ContainerProvider {

    private static volatile Container containerBuilder = null;

    /**
     * To prevent instantiation
     */
    private ContainerProvider() {
    }

    /**
     *
     * Give access to {@link Container} initialization
     *
     * @throws IllegalStateException if called in a Java EE container
     * @return the Container to configure.
     */
    public static Container getContainer() {
        if(containerBuilder == null)
            containerBuilder = findContainerBuilder();
        return containerBuilder;
    }

    private static Container findContainerBuilder() {
        ServiceLoader<Container> loader;

        loader = ServiceLoader.load(Container.class, ContainerProvider.class.getClassLoader());
        if (!loader.iterator().hasNext()) {
            throw new IllegalStateException("Unable to locate Container");
        }
        return loader.iterator().next();
    }
}
