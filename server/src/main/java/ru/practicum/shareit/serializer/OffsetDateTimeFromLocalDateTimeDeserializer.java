package ru.practicum.shareit.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class OffsetDateTimeFromLocalDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Value("${app.timezone}")
    private String timezone;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getText();
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, FORMATTER);
        ZoneId zoneId = ZoneId.of(timezone);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime.toOffsetDateTime();
    }
}