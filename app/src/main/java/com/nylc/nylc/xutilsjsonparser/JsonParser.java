package com.nylc.nylc.xutilsjsonparser;

import com.alibaba.fastjson.JSON;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by kasim on 2018/4/12.
 */

public class JsonParser implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        return JSON.parseObject(result,resultClass);
    }
}
