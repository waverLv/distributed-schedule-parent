package com.lv.distributed.factory;

import com.lv.distributed.annotation.SPI;
import com.lv.distributed.loader.ExtensionLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: SpiExtensionFactory
 * @Author: lvminghui
 * @Description: spi扩展工厂
 * @Date: 2022/9/15 14:37
 * @Version: 1.0
 */
public class SpiExtensionFactory implements ExtensionFactory {

    @Override
    public <T> T getExtension(final String key, final Class<T> clazz) {
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
            if (!extensionLoader.getSupportedExtensions().isEmpty()) {
                if(StringUtils.isBlank(key)){
                    return extensionLoader.getDefaultActivate();
                }
                return extensionLoader.getActivate(key);
            }
        }
        return null;
    }

    @Override
    public <T> T getExtension(Class<T> clazz) {
        SPI annotation = clazz.getAnnotation(SPI.class);
        if(null == annotation){
            return null;
        }
        return getExtension(StringUtils.isNotEmpty(annotation.value())? annotation.value() : "default",clazz);
    }

    @Override
    public <T> Map<String,T> getAllExtensionList(Class<T> clazz) {
        Map<String,T> extensions = new HashMap<>();
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
            Set<String> supportedExtensions = extensionLoader.getSupportedExtensions();
            if (!supportedExtensions.isEmpty()) {
                supportedExtensions.forEach(key -> {
                    extensions.put(key,extensionLoader.getActivate(key));
                });
            }
        }
        return extensions;
    }
}
