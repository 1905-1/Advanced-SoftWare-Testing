package name.ealen.interfaces.dto;

public class ResponseMsg {
    public final static int OK = 1;
    public final static int ERROR = -1;
    public final static int EMPTY = 0;
    public int status;
    public Object data;
    public Object msg;

    public ResponseMsg(int status, Object data, Object msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
