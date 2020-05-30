package io.penguinstats.penguinbotx.entity.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/31 2:23
 * @description ：
 * @modified By： yamika
 * @version:
 */
@Data
public class MatrixResponse implements Serializable {

    private List<Object> matrix;
}
