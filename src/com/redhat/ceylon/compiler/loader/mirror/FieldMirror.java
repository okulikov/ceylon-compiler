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
package com.redhat.ceylon.compiler.loader.mirror;


/**
 * Represents a field.
 *
 * @author Stéphane Épardaud <stef@epardaud.fr>
 */
public interface FieldMirror extends AnnotatedMirror {

    /**
     * Returns the field name
     */
    String getName();

    /**
     * Returns true if this field is static
     */
    boolean isStatic();

    /**
     * Returns true if this field is public
     */
    boolean isPublic();

    /**
     * Returns true if this field is protected
     */
    boolean isProtected();
    
    /**
     * Returns true if this field is default (package) access.
     */
    boolean isDefaultAccess();
    
    /**
     * Returns true if this field is final
     */
    boolean isFinal();

    /**
     * Returns the type of this field 
     */
    TypeMirror getType();
}
