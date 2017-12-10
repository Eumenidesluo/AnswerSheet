package me.eumenides.interceptor;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Eumenides on 2017/11/30.
 */
@Data
public class SessionData implements Serializable {
    private Integer userCode;
}
