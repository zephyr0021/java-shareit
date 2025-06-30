package ru.practicum.shareit.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Component
public class OffsetDateTimeToLocalDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

    @Value("${app.timezone}")
    private String timezone;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDateTime ldt = value.atZoneSameInstant(zoneId).toLocalDateTime();
        gen.writeString(FORMATTER.format(ldt));
    }
}