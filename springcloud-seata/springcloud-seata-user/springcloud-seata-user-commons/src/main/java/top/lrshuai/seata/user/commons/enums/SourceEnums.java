package top.lrshuai.seata.user.commons.enums;

public enum SourceEnums {
    SHOP(1,"购物"),
    TRANSFER(2,"转账"),

    ;

    private Integer code;
    private String remark;
    SourceEnums(Integer code, String remark){
        this.code=code;
        this.remark=remark;
    }
}
