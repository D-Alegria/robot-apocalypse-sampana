package com.sampana.robotapocalypsesampana.util;

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

    public static <T> Response<T> successfulResponse(List<T> list) {
        Response<T> response = new Response<>();
        response.setResponseCode(ResponseCode.Successful.code);
        response.setResponseMessage(ResponseCode.Successful.message);
        response.setModelList(list);
        response.setCount(CollectionUtils.isEmpty(list) ? 0 : list.size());
        return response;
    }
}
