package com.smart.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum {

    EQUIPMENT_NOT_FOUND(404,"设备没找到"),
    EQUIPMENT_INSERT_ERROR(500,"设备添加失败"),
    UPDATE_EQUIPMENT_ERROR(500,"修改设备失败"),
    DELETE_EQUIPMENT_ERROR(500,"删除设备信息失败"),
    BOARD_INIT_FAIL(500,"外部传感器板初始化错误"),
    LIGHT_OUT_OF_RANGE(500,"光照强度超出范围"),
    LIGHT_SENSOR_FAULT(500,"光敏传感器故障"),
    TEMP_HUM_NOT_FOUND(404,"温度和湿度数据不是最新的"),
    AIR_PRESSURE_EXCEPTION(500,"气压计异常"),
    SENSOR_DATA_NOT_FOUND(404,"传感器数据没找到"),
    HISTORY_DATA_NOT_FOUND(404,"历史数据没找到"),
    SAVE_THRESHOLD_ERROR(500,"阈值信息保存失败"),
    THRESHOLD_NOT_FOUND(404,"阈值信息没找到"),
    THRESHOLD_DELETE_FAIL(500,"删除阈值信息失败"),
    CONTEXTUAL_MODEL_NOT_FOUND(404,"情景模式没找到"),
    USER_NOT_FOUND(404,"用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(500,"用户名或密码错误"),
    INSERT_USER_INVALID(500,"添加用户信息错误"),
    DELETE_USER_ERROR(500,"删除用户失败"),
    ROLE_NOT_FOUND(404,"角色信息没找到"),
    DELETE_ROLE_ERROR(500,"删除角色信息失败"),
    PERMISSION_NOT_FOUND(404,"权限信息没找到"),
    ROLE_ADD_ERROR(500,"角色添加错误"),
    PERMISSION_ADD_ERROR(500,"权限信息添加错误"),
    PERMISSION_DELETE_ERROR(500,"权限信息删除错误"),
    EXTERNAL_EQUIPMENT_ERROR(404,"外部设备错误"),

    ;
    private Integer status;
    private String msg;
}
