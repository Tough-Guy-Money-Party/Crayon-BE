package com.yoyomo.global.config.healthCheck;


import com.yoyomo.global.config.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.netty.http.server.HttpServerState;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HealthCheckController {

    @GetMapping("health-check")
    public ResponseDto<String> checkHealthStatus() {
        return new ResponseDto<>();
    }
}
