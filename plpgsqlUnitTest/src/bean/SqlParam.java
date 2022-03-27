package bean;

import constant.SystemConstant.ParameterMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SqlParam {
	private String paramName = null;
	private int paramDataType = 0;
	private Object paramData = null;
	private ParameterMode paramMode = null;
}
