package com.neuedu.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {


    //h获取缓存对象LoadingCache
    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder().
            initialCapacity(1000).//设置缓存项
            maximumSize(1000).//设置最大缓存项
            expireAfterAccess(12,TimeUnit.HOURS).//设置过期时间
            build(new CacheLoader<String, String>() {
                //当key 不存在时，调用该方法
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    /**
     * 想缓存添加键值对
     */
    public  static void  put (String key,String value){
        loadingCache.put(key, value);
    }
    /**
     * 获取缓存的值
     */

    public static String get(String key){
        try {
           String value =  loadingCache.get(key);
           if (value.equals("null")){
               return  null;
           }
           return value;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
