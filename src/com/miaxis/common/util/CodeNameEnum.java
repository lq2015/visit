package com.miaxis.common.util;

/**
 * 支持代码加名称的枚举类
 * 
 * @author Fan.YuFeng
 * 
 */
public abstract class CodeNameEnum<T> {
	public T getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	protected CodeNameEnum(T code, String name) {
		this.code = code;
		this.name = name;
	}

	private T code = null;
	private String name = null;
}
