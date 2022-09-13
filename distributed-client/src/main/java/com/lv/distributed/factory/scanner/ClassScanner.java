package com.lv.distributed.factory.scanner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @ProjectName: ClassScanner
 * @Author: lvminghui
 * @Description: 类扫描
 * @Date: 2022/8/11 10:44
 * @Version: 1.0
 */
public class ClassScanner {

    private final String basePackage;
    private final boolean recursive;
    private final Predicate<String> packagePredicate;
    private final Predicate<Class> classPredicate;

    public ClassScanner(String basePackage,boolean recursive,Predicate<String> packagePredicate,Predicate<Class> classPredicate){
        this.basePackage = basePackage;
        this.recursive = recursive;
        this.packagePredicate  = packagePredicate;
        this.classPredicate = classPredicate;
    }

    public Set<Class<?>> doScanAllClasses() throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new LinkedHashSet<>();
        String packageName = basePackage;
        if(packageName.endsWith(".")){
            packageName = packageName.substring(0,packageName.lastIndexOf("."));
        }
        String basePackageFilePath = packageName.replace(".", "/");
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(basePackageFilePath);
        while(resources.hasMoreElements()){
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            if("file".equals(protocol)){
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                doScanPackageClassesByFile(classes,packageName,filePath);
            }else if("jar".equals(protocol)){
                doScanPackageClassesByJar(packageName,url,classes);
            }
        }
        return classes;
    }

    private void doScanPackageClassesByJar(String basePackage, URL url, Set<Class<?>> classes) throws IOException, ClassNotFoundException {
        String packageName = basePackage;
        String basePackageFilePath = packageName.replace(".", "/");
        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()){
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            if(!name.startsWith(packageName) || jarEntry.isDirectory()){
                continue;
            }
            if(!recursive &&  name.lastIndexOf("/") != basePackageFilePath.length()){
                continue;
            }
            if(null != packagePredicate){
                String jarPackageName = name.substring(0, name.lastIndexOf("/")).replace("/", ".");
                if(!packagePredicate.test(jarPackageName)){
                    continue;
                }
            }
            String className = name.replace("/", ".");
            className = className.substring(0, className.length() - 6);
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
            if(null == classPredicate || classPredicate.test(clazz)){
                classes.add(clazz);
            }
        }

    }

    private void doScanPackageClassesByFile(Set<Class<?>> classes, String packageName, String filePath) throws ClassNotFoundException {
        File dir = new File(filePath);
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] dirFiles = dir.listFiles((FileFilter) file -> {
            String filename = file.getName();
            if (file.isDirectory()) {
                if (!recursive) {
                    return false;
                }
                if (packagePredicate != null) {
                    return packagePredicate.test(packageName + "." + filename);
                }
                return true;
            }
            return filename.endsWith(".class");
        });
        if(null == dirFiles){
            return ;
        }
        for(File file : dirFiles){
            if(file.isDirectory()){
                doScanPackageClassesByFile(classes,packageName+"."+file.getName(),file.getAbsolutePath());
            }else{
                String classname = file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + classname);
                if(null == classPredicate || classPredicate.test(clazz)){
                    classes.add(clazz);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClassScanner scanner = new ClassScanner("com.qianlima.lv.duplicatesurvey.ocr",false,null,null);
        Set<Class<?>> classes = scanner.doScanAllClasses();
        System.out.println(classes.toString());
    }
}
