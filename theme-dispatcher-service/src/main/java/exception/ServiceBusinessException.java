package exception;


import com.smarthome.application.restApiData.enums.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceBusinessException extends RuntimeException {

    private final ApiError error;

    public ServiceBusinessException(String code) {
        this.error = ApiError.builder()
                .code(code)
                .build();
    }

    public ServiceBusinessException(final String restClientError, final HttpStatus httpStatus) {
        error = ApiError.builder()
                .code(httpStatus.name())
                .details(restClientError)
                .build();
    }

}
