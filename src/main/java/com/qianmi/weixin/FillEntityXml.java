package com.qianmi.weixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qianmi.weixin.exception.WeixinException;
import com.qianmi.weixin.message.MessageBase;

/**
 * 对象\xml映射
 */
public class FillEntityXml {

    /**
     * xml内容转对象
     * @param msg
     * @param entity
     * @return
     * @throws WeixinException
     */
    public static MessageBase convertXmlToEntity(String msg, MessageBase entity) throws WeixinException {
        XmlMapper mapper = new XmlMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.readValue(msg, entity.getClass());
        } catch (Exception e) {
            throw new WeixinException("xml内容转entity对象错误", e);
        }
    }

    /**
     * 对象转xml内容
     * @param entity
     * @return
     * @throws WeixinException
     */
    public static String convertEntityToXml(MessageBase entity) throws WeixinException {
        XmlMapper mapper = new XmlMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new WeixinException("entity对象转xml内容错误", e);
        }
    }

}
