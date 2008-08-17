/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ftpserver.main;

import org.apache.ftpserver.FtpServer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

/**
 * This class is the starting point for the FtpServer when it is started using
 * the command line mode.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class CommandLine {

    /**
     * The purpose of this class is to allow the final user to start the
     * FtpServer application. Because of that it has only <code>static</code>
     * methods and cannot be instanced.
     */
    private CommandLine() {
    }

    /**
     * This method is the FtpServer starting point when running by using the
     * command line mode.
     * 
     * @param args
     *            The first element of this array must specify the kind of
     *            configuration to be used to start the server.
     */
    public static void main(String args[]) {

        try {

            // get configuration
            FtpServer server = getConfiguration(args);
            if (server == null) {
                return;
            }

            // start the server
            server.start();
            System.out.println("FtpServer started");

            // add shutdown hook if possible
            addShutdownHook(server);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add shutdown hook.
     */
    private static void addShutdownHook(final FtpServer engine) {

        // create shutdown hook
        Runnable shutdownHook = new Runnable() {
            public void run() {
                System.out.println("Stopping server...");
                engine.stop();
            }
        };

        // add shutdown hook
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(shutdownHook));
    }

    /**
     * Print the usage message.
     */
    private static void usage() {
        System.err
                .println("Usage: java org.apache.ftpserver.main.CommandLine [OPTION] [CONFIGFILE]");
        System.err
                .println("Starts FtpServer using the default configuration of the ");
        System.err.println("configuration file if provided.");
        System.err.println("");
        System.err
                .println("      --default              use the default configuration, ");
        System.err
                .println("                             also used if no command line argument is given ");
        System.err.println("  -?, --help                 print this message");
    }

    /**
     * Get the configuration object.
     */
    private static FtpServer getConfiguration(String[] args) throws Exception {

        FtpServer server = null;
        if (args.length == 0) {
            System.out.println("Using default configuration");
            server = new FtpServer();
        } else if ((args.length == 1) && args[0].equals("-default")) {
            // supported for backwards compatibility, but not documented
            System.out
                    .println("The -default switch is deprecated, please use --default instead");
            System.out.println("Using default configuration");
            server = new FtpServer();
        } else if ((args.length == 1) && args[0].equals("--default")) {
            System.out.println("Using default configuration");
            server = new FtpServer();
        } else if ((args.length == 1) && args[0].equals("--help")) {
            usage();
        } else if ((args.length == 1) && args[0].equals("-?")) {
            usage();
        } else if (args.length == 1) {
            System.out.println("Using XML configuration file " + args[0]
                    + "...");
            XmlBeanFactory bf = new XmlBeanFactory(new FileSystemResource(
                    args[0]));
            if (bf.containsBean("server")) {
                server = (FtpServer) bf.getBean("server");
            } else {
                String[] beanNames = bf.getBeanNamesForType(FtpServer.class);
                if (beanNames.length == 1) {
                    server = (FtpServer) bf.getBean(beanNames[0]);
                } else if (beanNames.length > 1) {
                    System.out
                            .println("Using the first server defined in the configuration, named "
                                    + beanNames[0]);
                    server = (FtpServer) bf.getBean(beanNames[0]);
                } else {
                    System.err
                            .println("XML configuration does not contain a server configuration");
                }

            }
        } else {
            usage();
        }

        return server;
    }
}