package sparta.rooibos.route.application.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RouteErrorCode {

    ROUTE_NOT_FOUND(HttpStatus.NO_CONTENT, "ROU-01", "해당 경로를 찾을 수 없습니다."),
    NO_CONNECTED_PATH(HttpStatus.NO_CONTENT, "ROU-02", "허브 간 이어진 경로가 없습니다."),
    FAIL_TO_CREATE_PATH(HttpStatus.INTERNAL_SERVER_ERROR, "ROU-03", "경로를 생성하던 중 에러가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}