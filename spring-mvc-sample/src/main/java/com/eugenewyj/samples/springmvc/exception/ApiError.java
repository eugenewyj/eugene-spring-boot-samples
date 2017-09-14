package com.eugenewyj.samples.springmvc.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @since 2017/9/14
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@Data
class ApiError {
    private HttpStatus        status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime     timestamp;
    private String            message;
    private String            debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    abstract class ApiSubError {
    }

    class ApiValidationError extends ApiSubError {
        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        public ApiValidationError(String object, String message) {
            this.object = object;
            this.message = message;
        }
    }
}

class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object o) {
        return o.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(Object o, Class<?> aClass) {
        return idFromValue(o);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
