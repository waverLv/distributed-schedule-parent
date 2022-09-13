package com.lv.distributed.factory.scanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @ProjectName: PackageScannerContext
 * @Author: lvminghui
 * @Description: 包扫描
 * @Date: 2022/8/11 10:44
 * @Version: 1.0
 */
public class DefaultPackageScannerContext extends AbstractPackageScannerContext {


    public DefaultPackageScannerContext(String basePackage) throws IOException, ClassNotFoundException {
       super(basePackage,true,null,null);
    }
    public DefaultPackageScannerContext(String basePackage, boolean recursive, Predicate<String> packagePredicate, Predicate<Class> classPredicate) throws IOException, ClassNotFoundException {
       super(basePackage,recursive,packagePredicate,classPredicate);
    }


    /**
     * 获取标注指定注解的所有类
     * @param annotationClazz
     * @return
     */
    public Set<Class<?>> getTypesAnnotationWith(Class<? extends Annotation> annotationClazz){
        Set<Class<?>> classesWithAnnotation = new LinkedHashSet<>();
        Set<Class<?>> allClasses = getAllClasses();
        allClasses.forEach(clazz -> {
            if(clazz.isAnnotationPresent(annotationClazz)) {
                classesWithAnnotation.add(clazz);
            }
        });
        return classesWithAnnotation;

    }

}
