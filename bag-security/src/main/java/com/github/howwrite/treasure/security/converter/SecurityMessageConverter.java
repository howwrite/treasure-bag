package com.github.howwrite.treasure.security.converter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import com.github.howwrite.treasure.security.config.SecuritySwitch;
import com.github.howwrite.treasure.security.helper.DecryptionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SecurityMessageConverter extends FastJsonHttpMessageConverter {
    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return readType(getType(type, contextClass), inputMessage);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return readType(getType(clazz, null), inputMessage);
    }

    private Object readType(Type type, HttpInputMessage inputMessage) {
        try (InputStream in = inputMessage.getBody()) {
            byte[] buffer = in.readAllBytes();
            String requestBody = new String(buffer);
            String afterDecode = URLDecoder.decode(requestBody, StandardCharsets.UTF_8);
            String afterDecrypt = DecryptionHelper.decrypt(afterDecode);
            String[] split = StringUtils.split(afterDecrypt, "|", 2);
            validTimestamp(inputMessage, split[0], split[1]);
            return JSON.parseObject(split[1], type, getFastJsonConfig().getDateFormat(),
                    getFastJsonConfig().getReaderFilters(), getFastJsonConfig().getReaderFeatures());
        } catch (JSONException ex) {
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex, inputMessage);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex, inputMessage);
        } catch (HttpMessageNotReadableException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new HttpMessageNotReadableException("read param error while reading input message", ex, inputMessage);
        }
    }

    private void validTimestamp(HttpInputMessage inputMessage, String timestamp, String requestBody) {
        if (StringUtils.isBlank(timestamp) || !StringUtils.isNumeric(timestamp)) {
            throw new HttpMessageNotReadableException("请检查系统时间", inputMessage);
        }
        long timestampLong = Long.parseLong(timestamp);
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(timestampLong - currentTimeMillis) > (SecuritySwitch.securityValidityPeriodMinute.calValue() * 60 * 1000)) {
            throw new HttpMessageNotReadableException("请校准系统时间", inputMessage);
        }
        int signatureResult = 501;
        for (int i = 0; i < requestBody.length(); i++) {
            char c = requestBody.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                signatureResult += c;
            }
        }
        if (signatureResult % 10 != timestampLong % 10) {
            throw new HttpMessageNotReadableException("请校准系统时间！", inputMessage);
        }
    }
}
