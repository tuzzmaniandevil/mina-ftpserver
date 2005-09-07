// $Id$
/*
 * Copyright 2004 The Apache Software Foundation
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
package org.apache.ftpserver.ftplet;

import java.util.Collection;

/**
 * User manager interface.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public 
interface UserManager extends Component {

    /**
     * Get user by name.
     */
    User getUserByName(String login) throws FtpException;
    
    /**
     * Get all user names in the system.
     */
    Collection getAllUserNames() throws FtpException;
    
    /**
     * Delete the user from the system.
     */
    void delete(String login) throws FtpException;
    
    /**
     * Save user. If a new user, create it else update the existing user.
     */
    void save(User user) throws FtpException;
    
    /**
     * User existance check.
     */
    boolean doesExist(String login) throws FtpException;
    
    /**
     * Authenticate user
     */
    boolean authenticate(String login, String password) throws FtpException;
    
    /**
     * Get admin user name
     */
    String getAdminName() throws FtpException;
}