/* Based on the javac task from apache-ant-1.7.1.
 * The license in that file is as follows:
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or
 *   more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information regarding
 *   copyright ownership.  The ASF licenses this file to You under
 *   the Apache License, Version 2.0 (the "License"); you may not
 *   use this file except in compliance with the License.  You may
 *   obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an "AS
 *   IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied.  See the License for the specific language
 *   governing permissions and limitations under the License.
 *
 */

/*
 * Copyright Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the authors tag. All rights reserved.
 */

package com.redhat.ceylon.ant;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.types.Commandline;

public class Ceylond extends LazyTask {

    private static final String FAIL_MSG = "Documentation failed; see the ceylond error output for details.";
    private static final FileFilter ARTIFACT_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return true;
        }
    };

    private List<ModuleAndVersion> modules = new LinkedList<ModuleAndVersion>();
    private File executable;
    private boolean includeNonShared;
    private boolean includeSourceCode;
    private String user;
    private String pass;

    /**
     * Sets the user name for the output module repository (HTTP only)
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets the password for the output module repository (HTTP only)
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Include even non-shared declarations
     */
    public void setIncludeNonShared(boolean includeNonShared){
        this.includeNonShared = includeNonShared;
    }
    
    /**
     * Include source code in the documentation
     */
    public void setIncludeSourceCode(boolean includeSourceCode){
        this.includeSourceCode = includeSourceCode;
    }
    
    /**
     * Adds a module to compile
     * @param module the module name to compile
     */
    public void addModule(ModuleAndVersion module){
        modules.add(module);
    }
    
    @Override
    protected File getArtifactDir(String version, Module module) {
        File outModuleDir = new File(getOut(), module.toDir().getPath()+"/"+version +"/module-doc");
        return outModuleDir;
    }
    
    @Override
    protected FileFilter getArtifactFilter() {
        return ARTIFACT_FILTER;
    }

    /**
     * Executes the task.
     * @exception BuildException if an error occurs
     */
    @Override
    public void execute() throws BuildException {
        checkParameters();
        
        document();
    }

    /**
     * Set ceylond executable depending on the OS.
     */
    public void setExecutable(String executable) {
        this.executable = new File(Util.getScriptName(executable));
	}

    /**
     * Check that all required attributes have been set and nothing silly has
     * been entered.
     * 
     * @exception BuildException if an error occurs
     */
    protected void checkParameters() throws BuildException {
        // this will check that we have one
        getCeylond();
    }

    /**
     * Perform the compilation.
     */
    private void document() {    
        if (filterModules(modules)) {
            log("Everything's up to date");
            return;
        }
        
        Commandline cmd = new Commandline();
        cmd.setExecutable(getCeylond());
        if(user != null){
            cmd.createArgument().setValue("-user");
            cmd.createArgument().setValue(user);
        }
        if(pass != null){
            cmd.createArgument().setValue("-pass");
            cmd.createArgument().setValue(pass);
        }
        
        cmd.createArgument().setValue("-out");
        cmd.createArgument().setValue(getOut().getAbsolutePath());
        
        for (File src : getSrc()) {
            cmd.createArgument().setValue("-src");
            cmd.createArgument().setValue(src.getAbsolutePath());
        }
        
        for(Rep rep : getRepositories()){
            // skip empty entries
            if(rep.url == null || rep.url.isEmpty())
                continue;
            cmd.createArgument().setValue("-rep");
            cmd.createArgument().setValue(Util.quoteParameter(rep.url));
        }
        
        if(includeSourceCode)
            cmd.createArgument().setValue("-source-code");
        if(includeNonShared)
            cmd.createArgument().setValue("-non-shared");
        // modules to document
        for (Module module : modules) {
            log("Adding module: "+module, Project.MSG_VERBOSE);
            cmd.createArgument().setValue(module.toSpec());
        }

        try {
            Execute exe = new Execute(new LogStreamHandler(this, Project.MSG_INFO, Project.MSG_WARN));
            exe.setAntRun(getProject());
            exe.setWorkingDirectory(getProject().getBaseDir());
            log("Command line " + Arrays.toString(cmd.getCommandline()), Project.MSG_VERBOSE);
            exe.setCommandline(cmd.getCommandline());
            exe.execute();
            if (exe.getExitValue() != 0)
                throw new BuildException(FAIL_MSG, getLocation());
        } catch (IOException e) {
            throw new BuildException("Error running Ceylon compiler", e, getLocation());
        }
    }

    /**
     * Tries to find a ceylonc compiler either user-specified or detected
     */
    private String getCeylond() {
        return Util.findCeylonScript(this.executable, "ceylond", getProject());
    }
}
