package com.demo.gateway;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import java.util.Optional;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtService jwtService;
	private final LoadBalancerClient loadBalancerClient;
    
    @Bean
	public RouterFunction<ServerResponse> routes() {
		return route()
			.before(this::extractJwtInfo)
			.route(path("/api/auth/**"), request -> http(serviceUrl("auth-api")).handle(request))
			.route(path("/api/usuarios/**"), request -> http(serviceUrl("usuario-api")).handle(request))
			.route(path("/api/locais/**"), request -> http(serviceUrl("local-api")).handle(request))
			.route(path("/api/eventos/**"), request -> http(serviceUrl("evento-api")).handle(request))
			.route(path("/api/inscricoes/**"), request -> http(serviceUrl("inscricao-api")).handle(request))
			.build();
	}

	private String serviceUrl(String serviceName) {
		return loadBalancerClient.choose(serviceName).getUri().toString();
	}

    private ServerRequest extractJwtInfo(ServerRequest request) {
        Optional<String> token = extractTokenJWT(request);
        if(token.isEmpty()) return request;

        Optional<TokenInfo> info = jwtService.extractInfo(token.get());
        if(info.isEmpty()) return request;

        return ServerRequest.from(request)
            .header("X-User-Id", info.get().userId())
            .header("X-User-Roles", info.get().roles())
            .build();
	}

    private Optional<String> extractTokenJWT(ServerRequest request) {
        String authHeader = request.headers().firstHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
