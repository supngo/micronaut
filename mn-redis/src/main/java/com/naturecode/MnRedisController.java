package com.naturecode;

import io.micronaut.http.annotation.*;

@Controller("/mnRedis")
public class MnRedisController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}