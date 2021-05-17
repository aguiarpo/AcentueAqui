package com.pac.acentueaqui.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetailsWithFieldMessage extends ErrorDetails {
    private String fieldMessage;
    private String field;

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developmentMessage;
        private String field;
        private String fieldMessage;

        private Builder() {
        }

        public static ErrorDetailsWithFieldMessage.Builder newBuilder() {
            return new ErrorDetailsWithFieldMessage.Builder();
        }


        public ErrorDetailsWithFieldMessage.Builder title(String title) {
            this.title = title;
            return this;
        }

        public ErrorDetailsWithFieldMessage.Builder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorDetailsWithFieldMessage.Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ErrorDetailsWithFieldMessage.Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorDetailsWithFieldMessage.Builder developmentMessage(String developmentMessage) {
            this.developmentMessage = developmentMessage;
            return this;
        }

        public ErrorDetailsWithFieldMessage.Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ErrorDetailsWithFieldMessage.Builder field(String field) {
            this.field = field;
            return this;
        }

        public ErrorDetailsWithFieldMessage build() {
            ErrorDetailsWithFieldMessage constraintViolationDetail = new ErrorDetailsWithFieldMessage();
            constraintViolationDetail.setStatus(status);
            constraintViolationDetail.setDevelopmentMessage(developmentMessage);
            constraintViolationDetail.setTitle(title);
            constraintViolationDetail.setTimestamp(timestamp);
            constraintViolationDetail.setDetail(detail);
            constraintViolationDetail.setField(field);
            constraintViolationDetail.setFieldMessage(fieldMessage);
            return constraintViolationDetail;
        }
    }
}