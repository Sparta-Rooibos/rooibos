package sparta.rooibos.hub.application.service.exception;

import org.springframework.http.HttpStatus;
import sparta.rooibos.hub.application.service.exception.custom.HubErrorCode;

public class BusinessHubException extends RuntimeException {
    private final HubErrorCode hubErrorCodee;

    public BusinessHubException(HubErrorCode hubErrorCodee) {
        super(hubErrorCodee.getMessage());
        this.hubErrorCodee = hubErrorCodee;
    }

    public HttpStatus getStatus() {
        return hubErrorCodee.getStatus();
    }

    public String getErrorCode() {
        return hubErrorCodee.getCode();
    }

    public String getMessage() {
        return hubErrorCodee.getMessage();
    }
}
