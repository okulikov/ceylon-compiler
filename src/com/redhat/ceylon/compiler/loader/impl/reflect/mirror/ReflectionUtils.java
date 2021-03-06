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

package com.redhat.ceylon.compiler.loader.impl.reflect.mirror;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import com.redhat.ceylon.compiler.loader.mirror.AnnotationMirror;
import com.redhat.ceylon.compiler.loader.mirror.TypeParameterMirror;

public class ReflectionUtils {

    public static AnnotationMirror getAnnotation(AnnotatedElement annotated, String type) {
        return getAnnotation(annotated.getDeclaredAnnotations(), type);
    }

    public static AnnotationMirror getAnnotation(Annotation[] annotations, String type) {
        for(Annotation annotation : annotations){
            if(annotation.annotationType().getName().equals(type))
                return new ReflectionAnnotation(annotation);
        }
        return null;
    }

    public static List<TypeParameterMirror> getTypeParameters(GenericDeclaration decl) {
        TypeVariable<?>[] javaTypeParameters = decl.getTypeParameters();
        List<TypeParameterMirror> typeParameters = new ArrayList<TypeParameterMirror>(javaTypeParameters.length);
        for(Type javaTypeParameter : javaTypeParameters)
            typeParameters.add(new ReflectionTypeParameter(javaTypeParameter));
        return typeParameters;
    }


}
