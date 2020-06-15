package myportfolio.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonLocalDateSerializer extends JsonSerializer<LocalDate>
{

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider arg2)
            throws IOException
    {
        final String dateString = date.format(this.formatter);
        generator.writeString(dateString);
    }
}