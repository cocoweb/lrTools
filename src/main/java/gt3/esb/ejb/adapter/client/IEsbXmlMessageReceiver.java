/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package gt3.esb.ejb.adapter.client;

/**
 * <pre>
 * 模拟应用集成平台EJB3.0服务接口。
 * </pre>
 * 
 * @author hushengping hushengping@foresee.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface IEsbXmlMessageReceiver {

	/**
	 * 接收请求报文
	 * 
	 * @param xml
	 *            请求报文
	 * @return 返回报文
	 */
	public String receiveMessageXML(String xml);
}
