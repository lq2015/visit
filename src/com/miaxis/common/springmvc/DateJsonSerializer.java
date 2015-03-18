package com.miaxis.common.springmvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 日期类型格式化,在要进行格式化的字段上标注 @JsonSerialize，并指定要使用的格式化类对象
 * @JsonSerialize(using=DateJsonSerializer.class)
 * @Column(name="BIRTHDAY")
 * public Date getBirthday() {....
 *
 */
public class DateJsonSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date arg0, JsonGenerator jgen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(arg0);
        jgen.writeString(formattedDate);
	}
}