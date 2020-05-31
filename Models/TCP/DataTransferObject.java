package Models.TCP;

import java.io.Serializable;

import Models.TCP.Enums.RequestTypeEnum;

public class DataTransferObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private RequestTypeEnum requestType;
    private Object payload;

    public DataTransferObject(RequestTypeEnum requestType, Object payload) {
        this.requestType = requestType;
        this.payload = payload;
    }

    public RequestTypeEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}