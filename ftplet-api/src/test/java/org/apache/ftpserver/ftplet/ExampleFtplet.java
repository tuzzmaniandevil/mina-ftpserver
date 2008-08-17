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

package org.apache.ftpserver.ftplet;

import java.io.IOException;

/**
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class ExampleFtplet extends DefaultFtplet {

    @Override
    public FtpletEnum onMkdirEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        session.write(new DefaultFtpReply(550, "Error!"));
        return FtpletEnum.RET_SKIP;
    }

    @Override
    public FtpletEnum onMkdirStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        if (session.isSecure() && session.getDataConnection().isSecure()) {
            // all is good
        }
        return null;
    }

}
