/**  
 * jQuery EasyUI 1.3.2  
 *   
 * Copyright (c) 2009-2013 www.jeasyui.com. All rights reserved.  
 *   
 * Licensed under the GPL or commercial licenses To use it on other terms please  
 * contact us: jeasyui@gmail.com http://www.gnu.org/licenses/gpl.txt  
 * http://www.jeasyui.com/license_commercial.php  
 *   
 */  
(function($) {   
    function init(target) {   
        $(target).addClass("validatebox-text");   
    };   
    function destroyBox(target) {   
        var data = $.data(target, "validatebox");   
        data.validating = false;   
        var tip = data.tip;   
        if (tip) {   
            tip.remove();   
        }   
        $(target).unbind();   
        $(target).remove();   
    };   
    function bindEvents(target) {   
        var box = $(target);   
        var validatebox = $.data(target, "validatebox");   
        box.unbind(".validatebox").bind("click.validatebox", function() {   
                    validatebox.validating = true;   
                    validatebox.value = undefined;   
        (function   () {   
                        if (validatebox.validating) {   
                            if (validatebox.value != box.val()) {   
                                validatebox.value = box.val();   
                                if (validatebox.timer) {   
                                    clearTimeout(validatebox.timer);   
                                }   
                                validatebox.timer = setTimeout(function() {   
                                            $(target).validatebox("validate");   
                                        }, validatebox.options.delay);   
                            } else {   
                                showTip(target);   
                            }   
                            setTimeout(arguments.callee, 200);   
                        }   
                    })();   
                }).bind("blur.validatebox", function() {   
                    if (validatebox.timer) {   
                        clearTimeout(validatebox.timer);   
                        validatebox.timer = undefined;   
                    }   
                    validatebox.validating = false;   
                    removeTip(target);   
                }).bind("mouseenter.validatebox", function() {   
                    if (box.hasClass("validatebox-invalid")) {   
                        createTipToShow(target);   
                    }   
                }).bind("mouseleave.validatebox", function() {   
                    if (!validatebox.validating) {   
                        removeTip(target);   
                    }   
                });   
    };   
    /**
     * 兼容easyui原提示方式，和poshytip提示方式  
     * @param {Object} target  
     */  
    function createTipToShow(target){   
        if($(target).poshytip){   
            createTipToShow_poshy(target);   
        }else{   
            createTipToShow_easyui(target);   
        }   
    }   
    function createTipToShow_poshy(target) {   
        //alert("createTipToShow_poshy")   
        var validatebox = $.data(target, "validatebox");   
        var message = validatebox.message;   
        var offsetX = 8,offsetY = 5;   
        var alignX = validatebox.options.tipPositionX || validatebox.options.tipPosition;   
        var alginY = validatebox.options.tipPositionY || 'center';   
        if ($(target).hasClass('combo-text')) {   
            if(alignX=="right") offsetX = offsetX + 20;   
        }   
        if(!validatebox.tip){   
            validatebox.tip = true;   
            if(!$(target).data('poshytip')){   
                $(target).poshytip({   
                    className: validatebox.options.className,   
                    showOn: 'none',   
                    alignTo: 'target',   
                    alignX: alignX,   
                    alignY: alginY,   
                    offsetX: offsetX,   
                    offsetY: offsetY   
                });   
            }   
        }   
        showTip(target);   
    };   
    function createTipToShow_easyui(target){   
        var message = $.data(target, "validatebox").message;   
        var tip = $.data(target, "validatebox").tip;   
        if (!tip) {   
            tip = $("<div class=\"validatebox-tip\">" + "<span class=\"validatebox-tip-content\">" + "</span>" + "<span class=\"validatebox-tip-pointer\">" + "</span>" + "</div>").appendTo("body");   
            $.data(target, "validatebox").tip = tip;   
        }   
        tip.find(".validatebox-tip-content").html(message);   
        showTip_easyui(target);   
    };   
    /**
     * 兼容easyui原提示方式，和poshytip提示方式  
     * @param {Object} target  
     */  
    function showTip(target){   
        if($(target).poshytip){   
            showTip_poshy(target);   
        }else{   
            showTip_easyui(target);   
        }   
    };   
    function showTip_poshy(target){   
        var data = $.data(target, "validatebox");   
        var message = data.message;   
        if (!data) {   
            return;   
        }   
        if(data.tip){   
            if(data.messageChg){   
                data.messageChg = false;   
                $(target).poshytip('show').poshytip('update',message,false);   
            }else{   
                $(target).poshytip('show');   
            }   
        }   
    };   
    function showTip_easyui(target){   
        var data = $.data(target, "validatebox");   
        if (!data) {   
            return;   
        }   
        var tip = data.tip;   
        if (tip) {   
            var box = $(target);   
            var pointer = tip.find(".validatebox-tip-pointer");   
            var content = tip.find(".validatebox-tip-content");   
            tip.show();   
            tip.css("top", box.offset().top - (content._outerHeight() - box._outerHeight()) / 2);   
            if (data.options.tipPosition == "left") {   
                tip.css("left", box.offset().left - tip._outerWidth());   
                tip.addClass("validatebox-tip-left");   
            }   
            else {   
                tip.css("left", box.offset().left + box._outerWidth());   
                tip.removeClass("validatebox-tip-left");   
            }   
            pointer.css("top", (content._outerHeight() - pointer._outerHeight()) / 2);   
        }   
    };   
    function removeTip(target){   
        if($(target).poshytip){   
            removeTip_poshy(target);   
        }else{   
            removeTip_easyui(target);   
        }   
    }   
    function removeTip_poshy(target){   
        $.data(target, "validatebox").tip = false;   
        $(target).poshytip("hide");   
        //$(target).poshytip("destroy");   
    };   
    function removeTip_easyui(target){   
        var tip = $.data(target, "validatebox").tip;   
        if (tip) {   
            tip.remove();   
            $.data(target, "validatebox").tip = null;   
        }   
    };   
    function validate(target) {   
        var data = $.data(target, "validatebox");   
        var opts = data.options;   
        var tip = data.tip;   
        var box = $(target);   
        var value = box.val();   
        function setMessage(msg) {   
            data.messageChg = true;   
            data.message = msg;   
        };   
        function doValidate(validType) {   
            var result = /([a-zA-Z_]+)(.*)/.exec(validType);   
            var rule = opts.rules[result[1]];   
            if (rule && value) {   
                var param = eval(result[2]);   
                if (!rule["validator"](value, param)) {   
                    box.addClass("validatebox-invalid");   
                    var message = rule["message"];   
                    if (param) {   
                        for (var i = 0; i < param.length; i++) {   
                            message = message.replace(   
                                    new RegExp("\\{" + i + "\\}", "g"), param[i]);   
                        }   
                    }   
                    setMessage(opts.invalidMessage || message);   
                    if (data.validating) {   
                        createTipToShow(target);   
                    }   
                    return false;   
                }   
            }   
            return true;   
        };   
        if (opts.required) {   
            if (value == "") {   
                box.addClass("validatebox-invalid");   
                setMessage(opts.missingMessage);   
                if (data.validating) {   
                    createTipToShow(target);   
                }   
                return false;   
            }   
        }   
        if (opts.validType) {   
            if (typeof opts.validType == "string") {   
                if (!doValidate(opts.validType)) {   
                    return false;   
                }   
            } else {   
                for (var i = 0; i < opts.validType.length; i++) {   
                    if (!doValidate(opts.validType[i])) {   
                        return false;   
                    }   
                }   
            }   
        }   
        box.removeClass("validatebox-invalid");   
        removeTip(target);   
        return true;   
    };   
    $.fn.validatebox = function(options, params) {   
        if (typeof options == "string") {   
            return $.fn.validatebox.methods[options](this, params);   
        }   
        options = options || {};   
        return this.each(function() {   
                    var state = $.data(this, "validatebox");   
                    if (state) {   
                        $.extend(state.options, options);   
                    } else {   
                        init(this);   
                        $.data(this, "validatebox", {   
                                    options : $.extend(   
                                                    {},   
                                                    $.fn.validatebox.defaults,   
                                                    $.fn.validatebox.parseOptions(this),   
                                                    options)   
                                });   
                    }   
                    bindEvents(this);   
                });   
    };   
    $.fn.validatebox.methods = {   
        destroy : function(jq) {   
            return jq.each(function() {   
                        destroyBox(this);   
                    });   
        },   
        validate : function(jq) {   
            return jq.each(function() {   
                        validate(this);   
                    });   
        },   
        isValid : function(jq) {   
            return validate(jq[0]);   
        }   
    };   
    $.fn.validatebox.parseOptions = function(target) {   
        var t = $(target);   
        return $.extend({}, $.parser.parseOptions(target, ["validType",   
                                "missingMessage", "invalidMessage",   
                                "tipPosition", {   
                                    delay : "number"  
                                }]), {   
                    required : (t.attr("required") ? true : undefined)   
                });   
    };   
    $.fn.validatebox.defaults = {   
        required : false,   
        validType : null,   
        delay : 200,   
        missingMessage : "该输入项为必输项.",   
        invalidMessage : null,   
        tipPosition : "right",   
        rules : {   
            email : {   
                validator : function(value) {   
                    return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i   
                            .test(value);   
                },   
                message : "请输入有效的电子邮件地址."  
            },   
            url : {   
                validator : function(value) {   
                    return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i   
                            .test(value);   
                },   
                message : "请输入有效的URL地址."  
            },   
            minLength: {
                validator: function(value, param){
                    return value.length >= param[0];
                },
                message: '请输入至少（2）个字符.'
            },
            length : {   
                validator : function(value, param) {   
                    var len = $.trim(value).length;   
                    return len >= param[0] && len <= param[1];   
                },   
                message : "输入内容长度必须介于{0}和{1}之间."  
            },   
            remote : {   
                validator : function(value, param) {   
                    var data = {};   
                    data[param[1]] = value;   
                    var isValidate  = $.ajax({   
                                url : param[0],   
                                dataType : "json",   
                                data : data,   
                                async : false,   
                                cache : false,   
                                type : "post"  
                            }).responseText;   
                    return isValidate  == "true";   
                },   
                message : "请修正该字段."  
            },
            idcard : {// 验证身份证 
                validator : function(value) { 
                    return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
                }, 
                message : '身份证号码格式不正确' 
            },
            phone : {// 验证电话号码 
                validator : function(value) { 
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
                }, 
                message : '格式不正确,请使用下面格式:020-88888888' 
            }, 
            mobile : {// 验证手机号码 
                validator : function(value) { 
                    return /^(13|15|18)\d{9}$/i.test(value); 
                }, 
                message : '手机号码格式不正确' 
            }, 
            chinese : {// 验证中文 
                validator : function(value) { 
                    return /^[\Α-\￥]+$/i.test(value); 
                }, 
                message : '请输入中文' 
            }, english : {// 验证英语 
                validator : function(value) { 
                    return /^[A-Za-z]+$/i.test(value); 
                }, 
                message : '请输入英文' 
            }, 
            unnormal : {// 验证是否包含空格和非法字符 
                validator : function(value) { 
                    return /.+/i.test(value); 
                }, 
                message : '输入值不能为空和包含其他非法字符' 
            }, 
            username : {// 验证用户名 
                validator : function(value) { 
                    return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
                }, 
                message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）' 
            }, 
            faxno : {// 验证传真 
                validator : function(value) { 
//                    return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value); 
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
                }, 
                message : '传真号码不正确' 
            }, 
            zip : {// 验证邮政编码 
                validator : function(value) { 
                    return /^[1-9]\d{5}$/i.test(value); 
                }, 
                message : '邮政编码格式不正确' 
            }, 
            ip : {// 验证IP地址 
                validator : function(value) { 
                    return /d+.d+.d+.d+/i.test(value); 
                }, 
                message : 'IP地址格式不正确' 
            }, 
            name : {// 验证姓名，可以是中文或英文 
                    validator : function(value) { 
                        return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
                    }, 
                    message : '请输入姓名' 
            },same:{ 
                validator : function(value, param){ 
                    if($("#"+param[0]).val() != "" && value != ""){ 
                        return $("#"+param[0]).val() == value; 
                    }else{ 
                        return true; 
                    } 
                }, 
                message : '两次输入的密码不一致！'    
            },
            integer : {// 验证整数 
                validator : function(value) { 
                    return /^[+]?[1-9]+\d*$/i.test(value); 
                }, 
                message : '请输入整数' 
            }, 
            age : {// 验证年龄
                validator : function(value) { 
                    return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value); 
                }, 
                message : '年龄必须是0到120之间的整数' 
            }, 
            qq : {// 验证QQ,从10000开始 
                validator : function(value) { 
                    return /^[1-9]\d{4,9}$/i.test(value); 
                }, 
                message : 'QQ号码格式不正确' 
            }
        }   
    };   
})(jQuery);  