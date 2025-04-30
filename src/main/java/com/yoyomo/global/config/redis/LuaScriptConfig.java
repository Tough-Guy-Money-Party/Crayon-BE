package com.yoyomo.global.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class LuaScriptConfig {

    private static final String MAIL_LUA_SCRIPT_PATH = "scripts/mail_limit.lua";

    @Bean
    public RedisScript<Boolean> mailLimitScript() {
        Resource scriptResource = new ClassPathResource(MAIL_LUA_SCRIPT_PATH);
        return RedisScript.of(scriptResource, Boolean.class);
    }
}
