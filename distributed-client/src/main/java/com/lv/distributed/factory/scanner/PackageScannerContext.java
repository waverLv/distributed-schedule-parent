package com.lv.distributed.factory.scanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface PackageScannerContext {

    public Set<Class<?>> getTypesAnnotationWith(Class<? extends Annotation> annotationClazz);
}
