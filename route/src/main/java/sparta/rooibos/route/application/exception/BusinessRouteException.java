package sparta.rooibos.route.application.exception;

import org.springframework.http.HttpStatus;
import sparta.rooibos.route.application.exception.custom.RouteErrorCode;

public class BusinessRouteException extends RuntimeException {

    private final RouteErrorCode routeErrorCode;

    public BusinessRouteException(RouteErrorCode routeErrorCode) {
        super(routeErrorCode.getMessage());
        this.routeErrorCode = routeErrorCode;
    }

    public HttpStatus getStatus() {
        return routeErrorCode.getStatus();
    }

    public String getErrorCode() {
        return routeErrorCode.getCode();
    }

    public String getMessage() {
        return routeErrorCode.getMessage();
    }
}
