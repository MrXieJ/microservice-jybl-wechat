/**
 * @copyright @2015-2016 Abel-Studio.All Rights Reserved
 */
package scut.jiayibilin.wechat.entity;

/**
 * @description
 * @author Jie
 */
public class QRParamsException extends Exception {
	private static final long serialVersionUID = 8837582301762730656L;
	public QRParamsException()  {}                //用来创建无参数对象
    public QRParamsException(String message) {        //用来创建指定参数对象
        super(message);                             //调用超类构造器
    }
}
