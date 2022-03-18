package com.sampana.robotapocalypsesampana.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sampana.robotapocalypsesampana.exception.SystemException;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Demilade Oladugba on 3/17/2022
 */
@Service
@Slf4j
public class Utils {

    private final static ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static <T> Response<T> successfulResponse(List<T> list) {
        Response<T> response = new Response<>();
        response.setResponseCode(ResponseCode.Successful.code);
        response.setResponseMessage(ResponseCode.Successful.message);
        response.setModelList(list);
        response.setCount(CollectionUtils.isEmpty(list) ? 0 : list.size());
        return response;
    }

    public static <T> T jsonUnMarshall(String msgBody, Class<T> dataClass) {
        try {
            return mapper.readValue(msgBody, dataClass);
        } catch (Exception ex) {
            log.error("Error unmarshalling json", ex);
            throw new SystemException("Error occurred, please contact the developer");
        }
    }

    public static String jsonMarshal(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception ex) {
            log.error("Error marshalling json", ex);
            throw new SystemException("Error occurred, please contact the developer");
        }
    }
}
