package myportfolio.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;

@Log
public class JsonUtils
{
    public static <T> T readValue(String content, Class<T> valueType)
    {
        try
        {
            return new ObjectMapper().readValue(content, valueType);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
