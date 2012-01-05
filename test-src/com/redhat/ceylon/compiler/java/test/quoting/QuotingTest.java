/*
 * Copyright Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the authors tag. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License version 2.
 * 
 * This particular file is subject to the "Classpath" exception as provided in the 
 * LICENSE file that accompanied this code.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License,
 * along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package com.redhat.ceylon.compiler.java.test.quoting;

import org.junit.Test;

import com.redhat.ceylon.compiler.java.test.CompilerTest;

public class QuotingTest extends CompilerTest {
    
    //
    // Packages
    
    @Test
    public void testKeywordInPackage(){
        compareWithJavaSource("assert/KeywordInPackage");
    }
    
    @Test
    public void testTwoKeywordsInPackage(){
        compareWithJavaSource("assert/transient/TwoKeywordsInPackage");
    }
    
    @Test
    public void testKeywordInClassValue(){
        compareWithJavaSource("assert/KeywordInClassValue");
    }
    
    @Test
    public void testKeywordInToplevelValue(){
        compareWithJavaSource("assert/KeywordInToplevelValue");
    }
    
    @Test
    public void testKeywordInClassGetter(){
        compareWithJavaSource("assert/KeywordInClassGetter");
    }
    
    @Test
    public void testKeywordInToplevelGetter(){
        compareWithJavaSource("assert/KeywordInToplevelGetter");
    }
    
    @Test
    public void testKeywordInClassMethod(){
        compareWithJavaSource("assert/KeywordInClassMethod");
    }
    
    @Test
    public void testKeywordInToplevelMethod(){
        compareWithJavaSource("assert/KeywordInToplevelMethod");
    }
    
    @Test
    public void testKeywordInToplevelObject(){
        compareWithJavaSource("assert/KeywordInToplevelObject");
    }
    
    @Test
    public void testKeywordInInnerClassContainer(){
        // Temporary until #298 is fixed
        String prop = "ceylon.typechecker.warnings";
        String orig = System.getProperty(prop);
        try {
            System.setProperty(prop, "warn");
            compareWithJavaSource("assert/KeywordInInnerClassContainer");
        } finally {
            System.setProperty(prop, orig);
        }
    }
}